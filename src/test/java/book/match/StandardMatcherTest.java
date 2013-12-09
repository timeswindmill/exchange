package book.match;

import book.StandardBook;
import book.StandardBookSide;
import book.out.PriceReporter;
import book.out.ReportQueue;
import client.Client;
import client.StandardClient;
import execution.ExecutionType;
import instruments.Instrument;
import instruments.StandardInstrument;
import machine.Machine;
import machine.RunConfig;
import order.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class StandardMatcherTest {
    // need to test
    // market v market
    // limit v limit
    // market v limit
    // walking the book

    private final AtomicInteger key = new AtomicInteger(1);
    private final Instrument instrument = new StandardInstrument("VOD.L", "Vodafone", null, 100, 1, 1);
    private final Client client = new StandardClient(1, "Test Client");
    //String reportFile = "/home/laurence/Work/Dev/Java/MatchingProjects/Exchange/data/priceReport/VOD";
    PriceReporter priceReporter = PriceReporter.createMockPriceReporter();

    StandardBook book = new StandardBook(instrument, priceReporter);

    private final StandardBookSide buySide = StandardBookSide.createStandardBookSide(book, Side.BUY);
    private final StandardBookSide sellSide = StandardBookSide.createStandardBookSide(book, Side.SELL);


    @Before
    public void setUpMachine() {
        Machine.setupMachine(RunConfig.UNITTEST);
    }

    @Test
    public void testMoreMatches() throws Exception {
        ReportQueue reportQueue = ReportQueue.createMockReportQueue(false);
        StandardMatcher matcher = new StandardMatcher(buySide, sellSide, 1, reportQueue);
        // first order so shouldn't match

        MatchableOrder[] buyOrders = createSomeOrders(15, Side.BUY);
        MatchableOrder[] sellOrders = createSomeOrders(15, Side.SELL);

        for (MatchableOrder buyOrder : buyOrders) {
            matcher.matchItem(buyOrder);
        }
        // shouldn't have been any matching
        for (MatchableOrder buyOrder : buyOrders) {
            Assert.assertEquals(100, buyOrder.getOrder().getOpenQuantity());
        }
        for (MatchableOrder sellOrder : sellOrders) {
            matcher.matchItem(sellOrder);
        }


    }

    @Test
    public void testSimpleMatch1() throws Exception {
        MatchableOrder order1 = createMarketOrder(Side.BUY, 100);
        MatchableOrder order2 = createMarketOrder(Side.SELL, 50);
        ReportQueue reportQueue = ReportQueue.createMockReportQueue(false);
        StandardMatcher matcher = new StandardMatcher(buySide, sellSide, 1, reportQueue);
        // first order so shouldn't match
        matcher.matchItem(order1);
        matcher.matchItem(order2);


        Assert.assertEquals(50, order1.getOrder().getOpenQuantity());
        Assert.assertEquals(0, order2.getOrder().getOpenQuantity());

        MatchableOrder order3 = createMarketOrder(Side.SELL, 50);
        matcher.matchItem(order3);
        Assert.assertEquals(0, order1.getOrder().getOpenQuantity());
        Assert.assertEquals(0, order3.getOrder().getOpenQuantity());


    }

    @Test
    public void testMatchLimitOrder() throws Exception {
        ReportQueue reportQueue = ReportQueue.createMockReportQueue(true);
        StandardMatcher matcher = new StandardMatcher(buySide, sellSide, 1, reportQueue);

        MatchableOrder limit102 = createLimitOrder(Side.SELL, 400, 102);
        MatchableOrder limit98 = createLimitOrder(Side.BUY, 400, 98);

        // shouldn't match
        matcher.matchItem(limit102);
        matcher.matchItem(limit98);


        Assert.assertEquals(400, limit102.getOrder().getOpenQuantity());
        Assert.assertEquals(400, limit98.getOrder().getOpenQuantity());

        MatchableOrder limit102b = createLimitOrder(Side.BUY, 100, 102);
        matcher.matchItem(limit102b);

        Assert.assertEquals(300, limit102.getOrder().getOpenQuantity());
        Assert.assertEquals(0, limit102b.getOrder().getOpenQuantity());

    }

    @Test
    public void testLimitMarketOrder() throws Exception {
        ReportQueue reportQueue = ReportQueue.createMockReportQueue(true);
        StandardMatcher matcher = new StandardMatcher(buySide, sellSide, 1, reportQueue);

        MatchableOrder limit102 = createLimitOrder(Side.SELL, 400, 102);
        MatchableOrder limit98 = createLimitOrder(Side.BUY, 400, 98);

        // shouldn't match
        matcher.matchItem(limit102);
        matcher.matchItem(limit98);


        Assert.assertEquals(400, limit102.getOrder().getOpenQuantity());
        Assert.assertEquals(400, limit98.getOrder().getOpenQuantity());

        MatchableOrder market = createMarketOrder(Side.BUY, 500);
        matcher.matchItem(market);

        Assert.assertEquals(0, limit102.getOrder().getOpenQuantity());
        Assert.assertEquals(100, market.getOrder().getOpenQuantity());
        Assert.assertEquals(102.0, market.getOrder().getLastExecutedPrice(), 0.0);

        MatchableOrder market2 = createMarketOrder(Side.SELL, 300);
        matcher.matchItem(market2);

        Assert.assertEquals(0, market.getOrder().getOpenQuantity());
        Assert.assertEquals(0, market2.getOrder().getOpenQuantity());
        Assert.assertEquals(200, limit98.getOrder().getOpenQuantity());
        Assert.assertEquals(100.0, market.getOrder().getLastExecutedPrice(), 0.0);

    }

    @Test
    public void testMatchWholeSide() throws Exception {
        ReportQueue reportQueue = ReportQueue.createMockReportQueue(true);
        StandardMatcher matcher = new StandardMatcher(buySide, sellSide, 1, reportQueue);

        MatchableOrder limit102 = createLimitOrder(Side.SELL, 400, 102);
        MatchableOrder limit103 = createLimitOrder(Side.SELL, 400, 103);
        MatchableOrder limit104 = createLimitOrder(Side.SELL, 400, 104);
        MatchableOrder limit105 = createLimitOrder(Side.SELL, 400, 105);
        MatchableOrder limit106 = createLimitOrder(Side.SELL, 400, 106);
        MatchableOrder limit98 = createLimitOrder(Side.BUY, 400, 98);
        matcher.matchItem(limit102);
        matcher.matchItem(limit103);
        matcher.matchItem(limit104);
        matcher.matchItem(limit105);
        matcher.matchItem(limit106);
        Assert.assertEquals(400, limit102.getOrder().getOpenQuantity());
        Assert.assertEquals(400, limit103.getOrder().getOpenQuantity());
        Assert.assertEquals(400, limit104.getOrder().getOpenQuantity());
        Assert.assertEquals(400, limit105.getOrder().getOpenQuantity());
        Assert.assertEquals(400, limit106.getOrder().getOpenQuantity());

        // now match all of them
        MatchableOrder market1 = createMarketOrder(Side.BUY, 2500);
        matcher.matchItem(market1);
        Assert.assertEquals(0, limit102.getOrder().getOpenQuantity());
        Assert.assertEquals(0, limit103.getOrder().getOpenQuantity());
        Assert.assertEquals(0, limit104.getOrder().getOpenQuantity());
        Assert.assertEquals(0, limit105.getOrder().getOpenQuantity());
        Assert.assertEquals(0, limit106.getOrder().getOpenQuantity());

        Assert.assertEquals(500, market1.getOrder().getOpenQuantity());


    }

    @Test
    public void testGetNextMatchingOrder() throws Exception {

    }


    private MatchableOrder[] createSomeOrders(int size, Side side) {

        MatchableOrder[] items = new MatchableOrder[size];

        for (int ii = 0; ii < items.length; ii++) {
            items[ii] = createMarketOrder(side, 100);
        }
        return items;
    }

    private MatchableOrder createMarketOrder(Side side, int quantity) {
        String clientOrderID = "12345ABC" + key.getAndIncrement();
        OrderStub stub = new OrderStub(OrderOrigin.ALGO, instrument, side, clientOrderID, client, ExecutionType.CONTINUOUS, quantity);
        return new StandardMatchableOrder(new StandardMarketOrder(stub));
    }

    private MatchableOrder createLimitOrder(Side side, int quantity, double price) {
        String clientOrderID = "12345ABC" + key.getAndIncrement();
        OrderStub stub = new OrderStub(OrderOrigin.ALGO, instrument, side, clientOrderID, client, ExecutionType.CONTINUOUS, quantity);
        return new StandardMatchableOrder(new StandardLimitOrder(stub, price));
    }


}
