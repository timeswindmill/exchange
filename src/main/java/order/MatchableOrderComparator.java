package order;

import java.util.Comparator;

public class MatchableOrderComparator implements Comparator<MatchableOrder> {

    public int compare(MatchableOrder o1, MatchableOrder o2) {
        Order order1 = o1.getOrder();
        Order order2 = o2.getOrder();
        Side side1 = order1.getOrderSide();
        Side side2 = order2.getOrderSide();
        if (side1 != side2) {
            return 0;
        }
        if (order1.getPrice() == order2.getPrice()) {
            if (order1.getEntryTime() < order2.getEntryTime()) {
                return 1;
            } else {
                return -1;
            }
        } else {
            // check for market orders
            if (order1.getPrice() == 0)
                return 1;
            if (order2.getPrice() == 0)
                return -1;

            switch (side1) {
                case BUY:
                case BUY_MINUS:
                case BORROW:
                    if (order1.getPrice() > order2.getPrice()) {
                        return 1;
                    } else {
                        return -1;
                    }

                default:
                    if (order1.getPrice() < order2.getPrice()) {
                        return 1;
                    } else {
                        return -1;
                    }
            }


        }

    }


}
