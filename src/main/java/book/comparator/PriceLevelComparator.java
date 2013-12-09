package book.comparator;

import book.PriceLevel;
import order.Side;

import java.util.Comparator;

public class PriceLevelComparator implements Comparator<PriceLevel> {

    @Override
    public int compare(PriceLevel o1, PriceLevel o2) {
        // allow fpr market orders
        if (o1.getPrice() == 0) {
            return -1;
        }
        if (o2.getPrice() == 0) {
            return 1;
        }

        if (o1.getPrice() == o2.getPrice()) {
            return 0;
        }
        if (o1.getSide() == Side.SELL) {
            if (o1.getPrice() < o2.getPrice()) {
                return 1;
            }
            return -1;
        } else {
            if (o1.getPrice() > o2.getPrice()) {
                return 1;
            }
            return -1;

        }

    }

}



