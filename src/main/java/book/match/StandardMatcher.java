package book.match;

import book.BookSide;
import book.PriceLevel;
import book.StandardBookSide;
import book.out.ReportQueue;
import execution.ExecutionInstruction;
import order.MatchableOrder;
import order.Order;
import order.OrderType;
import order.Side;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StandardMatcher implements Matcher {

    private final BookSide buySide;
    private final BookSide sellSide;
    private final int matcherID;
    private final ReportQueue reportQueue;

    private int remainingLiquidity = 0;

    public StandardMatcher(BookSide buySide, BookSide sellSide, int matcherID, ReportQueue reportQueue) {
        this.buySide = buySide;
        this.sellSide = sellSide;
        this.matcherID = matcherID;
        this.reportQueue = reportQueue;
    }

    public void matchItem(MatchableOrder order) {
        Order thisOrder = order.getOrder();
        int openQuantity = thisOrder.getOpenQuantity();
        remainingLiquidity = openQuantity;

        // here we go
        List<MatchableOrder> matchingOrders = getMatchingOrders(order);
        Set executionInstructions = thisOrder.getExecutionInstructions();
        if ((executionInstructions.contains(ExecutionInstruction.FOK)) || (executionInstructions.contains(ExecutionInstruction.IOC))) {
            // check some filling occurred
            if (remainingLiquidity > 0) {
                return;
            }

        }

        // now go through orders and apply updates
        // first matched orders
        for (MatchableOrder matchedOrder : matchingOrders) {
            int quantityLeft = thisOrder.getOpenQuantity();
            if (quantityLeft <= 0) {
                break;
            }

            fillOrder(order, matchedOrder);
            if (matchedOrder.getOrder().getOpenQuantity() <= 0) {
                try {
                    removeOrderFromBook(matchedOrder);
                } catch (StandardBookSide.BookSideException e) {
                    //TODO logging
                    System.out.println("Error removing order " + e);

                }
            }

        }
        // if we still have liquidity add order to book
        if (thisOrder.getOpenQuantity() > 0) {
            BookSide orderBookSide = thisOrder.getOrderSide() == Side.BUY ? buySide : sellSide;
            orderBookSide.addOrder(order);
        }

        return;
    }


    //TODO need to adjust liquidity

    public long getAvailableLiquidity(MatchableOrder order) {

        double startPrice = order.getOrder().getPrice();
        return getThisSide(order).getLiquidity(startPrice);

    }

    public void cancelOrder(MatchableOrder order) {

        order.getOrder().cancelOrder();
        try {
            removeOrderFromBook(order);
        } catch (StandardBookSide.BookSideException e) {
            //TODO logging
            System.out.println("Error removing order " + e);
        }
        return;
    }


    public void addOrderToBook(MatchableOrder order) {

        BookSide thisSide = getThisSide(order);
        thisSide.addOrder(order);

    }

    public void removeOrderFromBook(MatchableOrder order) throws StandardBookSide.BookSideException {

        BookSide thisSide = getThisSide(order);
        thisSide.removeOrder(order);

    }

    private BookSide getThisSide(MatchableOrder order) {
        BookSide thisSide = (order.getOrder().getOrderSide() == Side.BUY) ? buySide : sellSide;
        return thisSide;
    }

    private void fillOrder(MatchableOrder matchOrder1, MatchableOrder matchOrder2) {
        Order order1 = matchOrder1.getOrder();
        Order order2 = matchOrder2.getOrder();
        int openQuantity = order1.getOpenQuantity();
        if (openQuantity <= 0) {
            // no more fills
            return;
        }
        int fillAmount = openQuantity < order2.getOpenQuantity() ? openQuantity : order2.getOpenQuantity();
        long executionTime = System.currentTimeMillis();
        double executionPrice;
        if (order1.getOrderType() == OrderType.MARKET && order2.getOrderType() == OrderType.MARKET) {
            executionPrice = calculateMarketExecutionPrice();
        } else {
            executionPrice = (order1.getPrice() != 0) ? order1.getPrice() : order2.getPrice();
        }

        //update orders
        order1.executeOrder(fillAmount, executionPrice);
        order2.executeOrder(fillAmount, executionPrice);
        // now add to reporting queue , execution reports created there

        reportQueue.addExecution(order1, fillAmount, executionPrice, executionTime);
        reportQueue.addExecution(order2, fillAmount, executionPrice, executionTime);


    }

    private double calculateMarketExecutionPrice() {

        double buyPrice = buySide.getPriceLevels()[1].getPrice();
        double sellPrice = sellSide.getPriceLevels()[1].getPrice();
        //TODO check for crossed ?
        return (buyPrice + sellPrice) / 2;
    }


    public List<MatchableOrder> getMatchingOrders(MatchableOrder matchOrder1) {
        List<MatchableOrder> matchingOrders = new ArrayList<MatchableOrder>();
        Order order1 = matchOrder1.getOrder();
        double orderPrice = order1.getPrice();
        BookSide otherSide = order1.getOrderSide() == Side.BUY ? sellSide : buySide;

        remainingLiquidity = order1.getOpenQuantity();
        // go through price levels
        for (PriceLevel level : otherSide.getPriceLevels()) {
            if (remainingLiquidity <= 0) {
                break;
            }
            if (comparePrice(order1.getOrderSide(), orderPrice, level.getPrice()) == false) {
                break;
            }
            //we are at correct price level, so see if any orders
            for (MatchableOrder order : level.getOrders()) {
                if (order != null) {
                    matchingOrders.add(order);
                    remainingLiquidity = remainingLiquidity - order.getOrder().getOpenQuantity();
                    if (remainingLiquidity <= 0) {
                        break;
                    }
                }
            }

        }

        // now we have all matching orders
        return matchingOrders;

    }

    @Override
    public int getMatcherID() {
        return matcherID;
    }

    private boolean comparePrice(Side side, double price1, double price2) {
        // Side is side of price1
        if ((price1 == 0) || (price2 == 0)) {
            return true;
        }
        if (side == Side.BUY) {
            if (price1 >= price2) {
                return true;
            } else {
                return false;
            }
        } else {
            if (price1 <= price2) {
                return true;
            } else {
                return false;
            }

        }
    }


}
