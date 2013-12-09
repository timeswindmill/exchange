package book;

import order.MatchableOrder;
import order.Side;
import prices.PriceData;

public interface PriceLevel extends Iterable<MatchableOrder> {

    public double getPrice();

    public Side getSide();

    public MatchableOrder[] getOrders();

    public void addOrder(MatchableOrder item);

    public MatchableOrder getTopOrder();

    public int getLiquidity();

    public void removeOrder(MatchableOrder order);

    public int getOrderCount();

    public PriceData getPriceDataForLevel();
}
