package book;

import net.jcip.annotations.NotThreadSafe;
import order.MatchableOrder;
import order.Side;
import prices.PriceData;

import java.util.Comparator;
import java.util.Iterator;

// assumed that handled by a single thread
@NotThreadSafe
public class StandardPriceLevel implements PriceLevel {
    private final double price;
    //    private int lastItem=0;
    private int liquidity = 0;
    private final Side side;
    private MatchableOrder[] orders;      // note some elements may be null
    private final Comparator comparator;
    private final Iterator<MatchableOrder> iterator;


    public StandardPriceLevel(double price, int size, Side side, Comparator comparator) {
        this.side = side;

        if (size == 0) {
            throw new IllegalArgumentException("Size must be greater than zero");
        }
        //this.ordinal = ordinal;
        this.price = price;
        orders = new MatchableOrder[size];
        this.comparator = comparator;
        iterator = new PriceLevelIterator();
    }


    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public Side getSide() {
        return side;
    }

    private void increaseSize() {
        // resize and copy across orders
        int newSize = orders.length * 2;
        MatchableOrder[] newOrders = new MatchableOrder[newSize];
        System.arraycopy(orders, 0, newOrders, 0, orders.length);
        orders = newOrders;
    }

    private void compactLevel() {
        // ?? don't bother
//
//        int liveCount = 0;
//        Arrays.sort(orders,comparator);
//        for(int ii=0;ii<orders.length;ii++){
//            if(orders[ii]!=null){
//                liveCount++;
//            }
//            else{
//                break;
//            }
//        }
//


    }

    @Override
    public MatchableOrder[] getOrders() {
        return orders;
    }

    @Override
    public int getOrderCount() {
        int count = 0;
        for (int ii = 0; ii < orders.length; ii++) {
            if (orders[ii] != null) {
                count++;
            }
        }
        return count;
    }

    @Override
    public PriceData getPriceDataForLevel() {

//        int quantity  = 0;
//        for(MatchableOrder matchableOrder : getOrders()){
//            quantity = quantity + matchableOrder.getOrder().getOpenQuantity();
//        }


        // if liquidity works use that
        PriceData priceData = new PriceData(this.getPrice(), liquidity, getSide());
        return priceData;
    }


    @Override
    public void addOrder(MatchableOrder item) {

        int lastItem = -1;
        // start at end , move back
        for (int ii = orders.length - 1; ii >= 0; ii--) {
            // first find last order
            if (orders[ii] != null) {
                lastItem = ii;
                if (lastItem == orders.length - 1) {
                    // structure needs to expand
                    increaseSize();

                }
                break;
            }
        }
        // accept runtime error if resize didn't work
        orders[lastItem + 1] = item;
        liquidity = liquidity + (item.getOrder().getOpenQuantity());
    }


    @Override
    public void removeOrder(MatchableOrder order) {
        // remove order, then adjust liquidity
        for (int ii = 0; ii < orders.length; ii++) {
            MatchableOrder thisOrder = orders[ii];
            if ((thisOrder != null) && (thisOrder.equals(order))) {
                orders[ii] = null;
                adjustLiquidity(-1 * (order.getOrder().getOpenQuantity()));
                break;
            }
        }
    }


    @Override
    public Iterator<MatchableOrder> iterator() {
        return new PriceLevelIterator();
    }


    @Override
    public MatchableOrder getTopOrder() {
        for (MatchableOrder item : orders) {
            if (item != null) {
                return item;
            }
        }
        return null;
    }

    @Override
    public int getLiquidity() {
        return liquidity;
    }


    private void adjustLiquidity(int delta) {
        liquidity += delta;
    }


    private class PriceLevelIterator implements Iterator<MatchableOrder> {

        int currentItem = 0;

        @Override
        public boolean hasNext() {
            for (int ii = currentItem; ii < orders.length - 1; ii++) {
                if (orders[ii + 1] != null) {
                    return true;
                }
            }
            // no items
            return false;
        }

        @Override
        public MatchableOrder next() {

            if (currentItem < orders.length) {
                MatchableOrder order = orders[currentItem];
                currentItem++;
                return order;
            } else {
                return null;
            }
        }

        @Override
        public void remove() {
            throw new IllegalStateException("Not allowed to remove from this Iterator");
        }

    }


}
