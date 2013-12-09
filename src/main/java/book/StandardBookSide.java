package book;

import book.comparator.PriceLevelComparator;
import order.MatchableOrder;
import order.OrderPriceTimeComparator;
import order.Side;
import prices.PriceData;

import java.util.Arrays;
import java.util.Iterator;

public class StandardBookSide implements BookSide {

    private final StandardBook book;
    private final Side side;
    private StandardPriceLevel[] priceLevels;
    private double bestPrice;

    private static final int PRICE_LEVEL_SIZE = 10;
    private static final int DEFAULT_NUMBER_PRICE_LEVELS = 10;

    public StandardBookSide(StandardBook book, Side side) {
        this.book = book;
        this.side = side;


        double referencePrice = book.getInstrument().getReferencePrice();
        double tickSize = book.getInstrument().getTickSize();
        priceLevels = new StandardPriceLevel[DEFAULT_NUMBER_PRICE_LEVELS];
        priceLevels[0] = new StandardPriceLevel(0, PRICE_LEVEL_SIZE, side, new OrderPriceTimeComparator());

        for (int ii = 1; ii < DEFAULT_NUMBER_PRICE_LEVELS; ii++) {
            // for buy side we go from high to low
            double price = 0;
            double nextPriceStep = ii * tickSize;
            if (side == Side.BUY) {
                price = referencePrice - nextPriceStep;
            } else {
                price = referencePrice + nextPriceStep;
            }


            priceLevels[ii] = new StandardPriceLevel(price, PRICE_LEVEL_SIZE, side, new OrderPriceTimeComparator());
        }

    }


    @Override
    public Side getSide() {
        return side;
    }

    //TODO depth needed ?
    @Override
    public PriceData[] getPrices(double startLevel, int depth) {
        PriceData[] prices = new PriceData[priceLevels.length];
        for (int ii = 0; ii < priceLevels.length; ii++) {
            PriceLevel priceLevel = priceLevels[ii];
            PriceData priceData = priceLevel.getPriceDataForLevel();
            prices[ii] = priceData;
        }
        return prices;
    }

    @Override
    public double getBestPrice() {
        return bestPrice;
    }


    @Override
    public PriceLevel[] getPriceLevels() {
        return priceLevels;
    }

    @Override
    public int getOrderCount() {
        int count = 0;
        for (PriceLevel level : priceLevels) {
            count += level.getOrderCount();
        }
        return count;
    }


    @Override
    public long getLiquidity(double startingPrice) {

        long liquidity = 0;
        // add market orders
        liquidity = liquidity + priceLevels[0].getLiquidity();
        int startLevel = 1;
        for (int ii = startLevel; ii > priceLevels.length - 1; ii++) {
            if (side.equals(Side.BUY)) {
                if ((priceLevels[ii].getPrice() >= startingPrice)) {
                    liquidity = liquidity + priceLevels[ii].getLiquidity();
                } else {
                    break;
                }
            } else {
                if ((priceLevels[ii].getPrice() <= startingPrice)) {
                    liquidity = liquidity + priceLevels[ii].getLiquidity();
                } else {
                    break;
                }
            }

        }
        return liquidity;

    }


    @Override
    public Iterator<MatchableOrder> iterator() {
        return new BookSideIterator(this);
    }

    @Override
    public void addOrder(MatchableOrder matchableOrder) {

        double price = matchableOrder.getOrder().getPrice();
        if (price == 0) {
            //market order  add to first price level
            PriceLevel requiredLevel = priceLevels[0];
            requiredLevel.addOrder(matchableOrder);
        } else {
            PriceLevel requiredLevel = findPriceLevelForPrice(price);
            if (requiredLevel == null) {
                requiredLevel = createPriceLevelForPrice(price);
            }
            requiredLevel.addOrder(matchableOrder);
        }
        if (price != 0) {
            if (side.equals(Side.BUY)) {
                if (price > bestPrice) {
                    bestPrice = price;
                }
            } else {
                if ((price < bestPrice) || (bestPrice == 0)) {
                    bestPrice = price;
                }
            }
        }
    }

    @Override
    public void removeOrder(MatchableOrder order) throws BookSideException {
        PriceLevel level = findPriceLevelForPrice(order.getOrder().getPrice());
        if (level != null) {
            level.removeOrder(order);
        } else {
            throw new BookSideException("Unable to find price level for order");
        }
    }


    private void resortSide() {
        Arrays.sort(priceLevels, new PriceLevelComparator());
    }


    private PriceLevel findPriceLevelForPrice(double price) {
        PriceLevel[] allLevels = getPriceLevels();
        // market orders are in first price level
        if (price == 0) {
            return allLevels[0];
        }

        for (PriceLevel level : allLevels) {
            if (level.getPrice() == price) {
                return level;
            }
        }
        return null;
    }


    private PriceLevel createPriceLevelForPrice(double price) {
        // price level not found so create a new one
        int numberLevels = priceLevels.length;
        StandardPriceLevel newLevel = new StandardPriceLevel(price, PRICE_LEVEL_SIZE, side, new OrderPriceTimeComparator());
        StandardPriceLevel[] newLevels = new StandardPriceLevel[numberLevels + 1];
        System.arraycopy(priceLevels, 0, newLevels, 0, numberLevels);
        newLevels[newLevels.length - 1] = newLevel;
        priceLevels = newLevels;
        // now re sort array
        resortSide();

        return newLevel;
    }


    public static StandardBookSide createStandardBookSide(StandardBook book, Side side) {
        StandardBookSide sbs = new StandardBookSide(book, side);
        return sbs;
    }


    private class BookSideIterator implements Iterator<MatchableOrder> {
        private int currentOrdinal = 0;
        private PriceLevel currentPriceLevel;
        private Iterator<MatchableOrder> priceLevelIterator;

        private final BookSide bookSide;

        public BookSideIterator(BookSide bookSide) {
            this.bookSide = bookSide;
            currentPriceLevel = bookSide.getPriceLevels()[0];
            priceLevelIterator = currentPriceLevel.iterator();
        }


        @Override
        public boolean hasNext() {
            if (priceLevelIterator.hasNext()) {
                return true;
            } else {
                setNextPriceLevel();
                if (priceLevelIterator != null) {
                    return priceLevelIterator.hasNext();
                }
            }
            return false;
        }

        @Override
        public MatchableOrder next() {
            MatchableOrder nextOrder = priceLevelIterator.next();
            if (nextOrder != null) {
                return nextOrder;
            } else {
                setNextPriceLevel();
                return priceLevelIterator.next();
            }

        }

        @Override
        public void remove() {
            throw new IllegalStateException("Not allowed to remove from this Iterator");
        }


        private void setNextPriceLevel() {
            PriceLevel[] priceLevels = bookSide.getPriceLevels();
            if (currentOrdinal + 1 >= priceLevels.length) {
                currentPriceLevel = null;
                priceLevelIterator = null;
            } else {
                currentPriceLevel = bookSide.getPriceLevels()[currentOrdinal + 1];
                priceLevelIterator = currentPriceLevel.iterator();

            }
        }


    }

    public class BookSideException extends Exception {

        public BookSideException(String s) {
            super(s);
        }
    }

}

