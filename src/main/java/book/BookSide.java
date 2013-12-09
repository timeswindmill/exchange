package book;

import order.MatchableOrder;
import order.Side;
import prices.PriceData;

public interface BookSide extends Iterable<MatchableOrder> {

    public Side getSide();

    public PriceData[] getPrices(double startLevel, int depth);

    public double getBestPrice();

    public void addOrder(MatchableOrder matchableOrder);

    public long getLiquidity(double startingPrice);

    public void removeOrder(MatchableOrder order) throws StandardBookSide.BookSideException;

    public PriceLevel[] getPriceLevels();

    public int getOrderCount();


}

