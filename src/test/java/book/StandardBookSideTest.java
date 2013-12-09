package book;

import book.out.PriceReporter;
import client.Client;
import client.StandardClient;
import execution.ExecutionType;
import instruments.Instrument;
import instruments.StandardInstrument;
import order.*;
import org.junit.Assert;
import org.junit.Test;

public class StandardBookSideTest {

    Instrument vodInstrument = new StandardInstrument("VOD.L", "Vodafone", null, 100, 1, 0.1);
    Instrument bacInstrument = new StandardInstrument("BAC.L", "Barclays", null, 100, 1, 1);
    // now create single market order
    Client client = new StandardClient(1, "Test Client");
    //    String BacReportFile = "/home/laurence/Work/Dev/Java/MatchingProjects/Exchange/data/priceReport/BAC";
//    String VodReportFile = "/home/laurence/Work/Dev/Java/MatchingProjects/Exchange/data/priceReport/VOD";
    PriceReporter priceReporter = PriceReporter.createMockPriceReporter();

    @Test
    public void testGetPrices() throws Exception {
        //TODO
    }

    @Test
    public void testAddOrder() throws Exception {
        StandardBook book = new StandardBook(bacInstrument, priceReporter);
        StandardBookSide sbs = StandardBookSide.createStandardBookSide(book, Side.BUY);
        Assert.assertNotNull(sbs);

        Side side = sbs.getSide();
        Assert.assertNotNull(side);


        String clientOrderID = "12345ABC";
        OrderStub stub = new OrderStub(OrderOrigin.ALGO, bacInstrument, Side.SELL, clientOrderID, client, ExecutionType.CONTINUOUS, 100);
        MatchableOrder newOrder = new StandardMatchableOrder(new StandardMarketOrder(stub));

        sbs.addOrder(newOrder);


    }

    @Test
    public void testRemoveOrder() throws Exception {
        StandardBook book = new StandardBook(bacInstrument, priceReporter);

        StandardBookSide sbs = StandardBookSide.createStandardBookSide(book, Side.BUY);
        Assert.assertNotNull(sbs);

        Side side = sbs.getSide();
        Assert.assertNotNull(side);


        String clientOrderID = "12345ABC";
        OrderStub stub = new OrderStub(OrderOrigin.ALGO, bacInstrument, Side.SELL, clientOrderID, client, ExecutionType.CONTINUOUS, 100);
        MatchableOrder newOrder = new StandardMatchableOrder(new StandardMarketOrder(stub));

        sbs.addOrder(newOrder);

        for (int ii = 0; ii < 103; ii++) {
            String clientOrderID1 = "12345ABC" + ii;
            double px = 90 + (ii / 10);
            OrderStub stub1 = new OrderStub(OrderOrigin.ALGO, bacInstrument, Side.SELL, clientOrderID1, client, ExecutionType.CONTINUOUS, 100 + ii);
            MatchableOrder newOrder1 = new StandardMatchableOrder(new StandardLimitOrder(stub1, px));
            sbs.addOrder(newOrder1);
        }


        sbs.removeOrder(newOrder);
        OrderStub stub2 = new OrderStub(OrderOrigin.ALGO, bacInstrument, Side.SELL, clientOrderID, client, ExecutionType.CONTINUOUS, 100);
        MatchableOrder newOrder2 = new StandardMatchableOrder(new StandardLimitOrder(stub2, 500));
        boolean notFound = false;
        try {
            sbs.removeOrder(newOrder2);
        } catch (StandardBookSide.BookSideException e) {
            notFound = true;
        }
        Assert.assertTrue(notFound);


    }

    @Test
    public void bigOrderTest() throws Exception {
        StandardBook book = new StandardBook(bacInstrument, priceReporter);
        StandardBookSide sbs = StandardBookSide.createStandardBookSide(book, Side.SELL);
        Assert.assertNotNull(sbs);


        for (int ii = 0; ii < 103; ii++) {
            String clientOrderID = "12345ABC" + ii;
            double px = 90 + (ii / 10);
            OrderStub stub = new OrderStub(OrderOrigin.ALGO, bacInstrument, Side.SELL, clientOrderID, client, ExecutionType.CONTINUOUS, 100 + ii);
            MatchableOrder newOrder = new StandardMatchableOrder(new StandardLimitOrder(stub, px));
            sbs.addOrder(newOrder);
        }
        // now add in order below 90
        double px = 89;
        OrderStub stub89 = new OrderStub(OrderOrigin.ALGO, bacInstrument, Side.SELL, "89Ordera", client, ExecutionType.CONTINUOUS, 700);
        MatchableOrder newOrder89 = new StandardMatchableOrder(new StandardLimitOrder(stub89, px));
        sbs.addOrder(newOrder89);

        // ensure market orders are first in list
        PriceLevel[] levels = sbs.getPriceLevels();
        Assert.assertEquals(0.0, levels[0].getPrice(), 0.0);

        PriceLevel level1 = levels[1];
        PriceLevel levelLast = levels[levels.length - 1];
        Assert.assertTrue(level1.getPrice() > levelLast.getPrice());


    }


    @Test
    public void testCreateStandardBookSide() throws Exception {
        StandardBook book = new StandardBook(vodInstrument, priceReporter);
        StandardBookSide sbs = StandardBookSide.createStandardBookSide(book, Side.BUY);
        Assert.assertNotNull(sbs);
        Side side = sbs.getSide();
        Assert.assertNotNull(side);
        {
            // ensure market orders are first in list
            PriceLevel[] levels = sbs.getPriceLevels();
            Assert.assertEquals(0.0, levels[0].getPrice(), 0.0);

            PriceLevel level1 = levels[1];
            PriceLevel levelLast = levels[levels.length - 1];
            Assert.assertTrue(level1.getPrice() > levelLast.getPrice());
        }
        {
            // now do for sell side
            StandardBookSide sbsSell = StandardBookSide.createStandardBookSide(book, Side.SELL);
            Assert.assertNotNull(sbsSell);
            Side side2 = sbsSell.getSide();
            Assert.assertNotNull(side2);

            // ensure market orders are first in list
            PriceLevel[] levels = sbsSell.getPriceLevels();
            Assert.assertEquals(0.0, levels[0].getPrice(), 0.0);
            PriceLevel level1 = levels[1];
            PriceLevel levelLast = levels[levels.length - 1];
            Assert.assertTrue(level1.getPrice() < levelLast.getPrice());
        }


    }

    @Test
    public void testLiquidity() throws Exception {
        StandardBook book = new StandardBook(vodInstrument, priceReporter);
        StandardBookSide sbs = StandardBookSide.createStandardBookSide(book, Side.BUY);
        Assert.assertNotNull(sbs);
        Side side = sbs.getSide();
        Assert.assertNotNull(side);

        // aim for 100 @ 90, 200 @ 89 , 300 @ 88

        OrderStub stub90 = new OrderStub(OrderOrigin.ALGO, vodInstrument, Side.BUY, "123", client, ExecutionType.CONTINUOUS, 100);
        MatchableOrder newOrder90 = new StandardMatchableOrder(new StandardLimitOrder(stub90, 90));

        OrderStub stub89 = new OrderStub(OrderOrigin.ALGO, vodInstrument, Side.BUY, "124", client, ExecutionType.CONTINUOUS, 200);
        MatchableOrder newOrder89 = new StandardMatchableOrder(new StandardLimitOrder(stub89, 89));

        OrderStub stub88 = new OrderStub(OrderOrigin.ALGO, vodInstrument, Side.BUY, "125", client, ExecutionType.CONTINUOUS, 300);
        MatchableOrder newOrder88 = new StandardMatchableOrder(new StandardLimitOrder(stub88, 88));

        sbs.addOrder(newOrder90);
        sbs.addOrder(newOrder89);
        sbs.addOrder(newOrder88);

    }

    @Test
    public void testBestPrice() throws Exception {
        StandardBook book = new StandardBook(vodInstrument, priceReporter);
        StandardBookSide sbs = StandardBookSide.createStandardBookSide(book, Side.BUY);
        Assert.assertNotNull(sbs);
        Side side = sbs.getSide();
        Assert.assertNotNull(side);


        OrderStub stub90 = new OrderStub(OrderOrigin.ALGO, vodInstrument, Side.BUY, "123", client, ExecutionType.CONTINUOUS, 100);
        MatchableOrder newOrder90 = new StandardMatchableOrder(new StandardLimitOrder(stub90, 90));

        OrderStub stub89 = new OrderStub(OrderOrigin.ALGO, vodInstrument, Side.BUY, "124", client, ExecutionType.CONTINUOUS, 200);
        MatchableOrder newOrder89 = new StandardMatchableOrder(new StandardLimitOrder(stub89, 89));

        OrderStub stub88 = new OrderStub(OrderOrigin.ALGO, vodInstrument, Side.BUY, "125", client, ExecutionType.CONTINUOUS, 300);
        MatchableOrder newOrder88 = new StandardMatchableOrder(new StandardLimitOrder(stub88, 88));

        // best price starts as zero
        Assert.assertEquals(0.0, sbs.getBestPrice(), 0.0);

        sbs.addOrder(newOrder88);
        Assert.assertEquals(88.0, sbs.getBestPrice(), 0.0);

        sbs.addOrder(newOrder89);
        Assert.assertEquals(89.0, sbs.getBestPrice(), 0.0);

        sbs.addOrder(newOrder90);
        Assert.assertEquals(90.0, sbs.getBestPrice(), 0.0);

        // now test sell side
        StandardBookSide sbSell = StandardBookSide.createStandardBookSide(book, Side.SELL);
        Assert.assertNotNull(sbSell);
        Side sideSell = sbSell.getSide();
        Assert.assertEquals(Side.SELL, sideSell);

        OrderStub stub101 = new OrderStub(OrderOrigin.ALGO, vodInstrument, Side.BUY, "123", client, ExecutionType.CONTINUOUS, 100);
        MatchableOrder newOrder101 = new StandardMatchableOrder(new StandardLimitOrder(stub101, 101));

        OrderStub stub102 = new OrderStub(OrderOrigin.ALGO, vodInstrument, Side.BUY, "124", client, ExecutionType.CONTINUOUS, 200);
        MatchableOrder newOrder102 = new StandardMatchableOrder(new StandardLimitOrder(stub102, 102));

        OrderStub stub103 = new OrderStub(OrderOrigin.ALGO, vodInstrument, Side.BUY, "125", client, ExecutionType.CONTINUOUS, 300);
        MatchableOrder newOrder103 = new StandardMatchableOrder(new StandardLimitOrder(stub103, 103));

        // best price starts as zero
        Assert.assertEquals(0.0, sbSell.getBestPrice(), 0.0);


        sbSell.addOrder(newOrder103);
        Assert.assertEquals(103.0, sbSell.getBestPrice(), 0.0);

        sbSell.addOrder(newOrder102);
        Assert.assertEquals(102.0, sbSell.getBestPrice(), 0.0);

        sbSell.addOrder(newOrder101);
        Assert.assertEquals(101.0, sbSell.getBestPrice(), 0.0);


    }


    private MatchableOrder[] createSomeOrders(int size) {

        Instrument instrument = new StandardInstrument("BAC.L", "Barclays", null, 100, 1, 1);
        Client client = new StandardClient(1, "Test Client");

        String clientOrderID = "12345ABC";

        MatchableOrder[] items = new MatchableOrder[size];

        for (int ii = 0; ii < items.length; ii++) {
            OrderStub stub = new OrderStub(OrderOrigin.ALGO, instrument, Side.SELL, clientOrderID, client, ExecutionType.CONTINUOUS, 100 + ii);
            items[ii] = new StandardMatchableOrder(new StandardMarketOrder(stub));
        }


        return items;

    }


}
