package book.match;

import book.StandardBookSide;
import order.MatchableOrder;

import java.util.List;

public interface Matcher {

    public void matchItem(MatchableOrder order);

    public void cancelOrder(MatchableOrder order);

    public long getAvailableLiquidity(MatchableOrder order);

    public void addOrderToBook(MatchableOrder order);

    public void removeOrderFromBook(MatchableOrder order) throws StandardBookSide.BookSideException;

    public List<MatchableOrder> getMatchingOrders(MatchableOrder matchOrder1);

    public int getMatcherID();

}
