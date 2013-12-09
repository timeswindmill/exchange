package order;

import java.util.Comparator;

public class OrderPriceTimeComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        Side side1 = o1.getOrderSide();
        Side side2 = o2.getOrderSide();
        if (side1 != side2) {
            return 0;
        }
        if (o1.getPrice() == o2.getPrice()) {
            if (o1.getEntryTime() < o2.getEntryTime()) {
                return 1;
            } else {
                return -1;
            }
        } else {
            // check for market orders
            if (o1.getPrice() == 0)
                return 1;
            if (o2.getPrice() == 0)
                return -1;

            switch (side1) {
                case BUY:
                case BUY_MINUS:
                case BORROW:
                    if (o1.getPrice() > o2.getPrice()) {
                        return 1;
                    } else {
                        return -1;
                    }

                default:
                    if (o1.getPrice() < o2.getPrice()) {
                        return 1;
                    } else {
                        return -1;
                    }
            }


        }

    }


}
