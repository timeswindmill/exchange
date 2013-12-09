package book;

import client.Client;
import client.StandardClient;
import execution.ExecutionType;
import instruments.Instrument;
import instruments.StandardInstrument;
import junit.framework.Assert;
import order.*;
import org.junit.Test;

import java.util.Comparator;
import java.util.Iterator;

public class StandardPriceLevelTest {

    private MatchableOrder getSimpleOrder() {
        Instrument instrument = new StandardInstrument("VOD.L", "Vodafone", null, 100, 1, 1);
        Client client = new StandardClient(1, "Test Client");

        String clientOrderID = "12345ABC";
        OrderStub stub = new OrderStub(OrderOrigin.ALGO, instrument, Side.BUY, clientOrderID, client, ExecutionType.CONTINUOUS, 100);

        return new StandardMatchableOrder(new StandardMarketOrder(stub));

    }


    @Test
    public void testGetPrice() throws Exception {
        Comparator comparator = new OrderPriceTimeComparator();
        StandardPriceLevel standardPriceLevel = new StandardPriceLevel(99.3, 147, Side.BUY, comparator);
        Assert.assertNotNull(standardPriceLevel);
        Assert.assertEquals(99.3, standardPriceLevel.getPrice());

    }

    @Test
    public void testCompactLevel() throws Exception {
        //TODO
    }

    @Test
    public void testGetItems() throws Exception {
        Comparator comparator = new OrderPriceTimeComparator();
        StandardPriceLevel standardPriceLevel = new StandardPriceLevel(99.3, 147, Side.BUY, comparator);
        Assert.assertNotNull(standardPriceLevel);
        MatchableOrder order = getSimpleOrder();

        MatchableOrder[] items = standardPriceLevel.getOrders();
        Assert.assertEquals(147, items.length);


    }

    @Test
    public void testAddItem() throws Exception {
        Instrument instrument = new StandardInstrument("VOD.L", "Vodafone", null, 100, 1, 1);
        Client client = new StandardClient(1, "Test Client");

        String clientOrderID = "12345ABC";
        OrderStub stub = new OrderStub(OrderOrigin.ALGO, instrument, Side.SELL, clientOrderID, client, ExecutionType.CONTINUOUS, 102);
        MatchableOrder item = new StandardMatchableOrder(new StandardMarketOrder(stub));

        Comparator comparator = new OrderPriceTimeComparator();
        StandardPriceLevel standardPriceLevel = new StandardPriceLevel(99.3, 17, Side.SELL, comparator);
        Assert.assertNotNull(standardPriceLevel);

        standardPriceLevel.addOrder(item);

        MatchableOrder[] items = standardPriceLevel.getOrders();
        Assert.assertEquals(17, items.length);

    }

    @Test
    public void testRemoveItem() throws Exception {
        Instrument instrument = new StandardInstrument("VOD.L", "Vodafone", null, 100, 1, 1);
        Client client = new StandardClient(1, "Test Client");

        String clientOrderID = "12345ABC";
        OrderStub stub = new OrderStub(OrderOrigin.ALGO, instrument, Side.SELL, clientOrderID, client, ExecutionType.CONTINUOUS, 102);
        MatchableOrder item = new StandardMatchableOrder(new StandardMarketOrder(stub));

        Comparator comparator = new OrderPriceTimeComparator();
        StandardPriceLevel standardPriceLevel = new StandardPriceLevel(99.3, 17, Side.SELL, comparator);
        Assert.assertNotNull(standardPriceLevel);

        standardPriceLevel.addOrder(item);

        standardPriceLevel.removeOrder(item);

        MatchableOrder[] items = standardPriceLevel.getOrders();
        for (MatchableOrder order : items) {
            Assert.assertNull(order);
        }
        // more done in next test

    }


    @Test
    public void testGetOrderCount() throws Exception {
        Instrument instrument = new StandardInstrument("VOD.L", "Vodafone", null, 100, 1, 1);
        Client client = new StandardClient(1, "Test Client");

        String clientOrderID = "12345ABC";
        OrderStub stub = new OrderStub(OrderOrigin.ALGO, instrument, Side.SELL, clientOrderID, client, ExecutionType.CONTINUOUS, 102);
        MatchableOrder item = new StandardMatchableOrder(new StandardMarketOrder(stub));

        Comparator comparator = new OrderPriceTimeComparator();
        StandardPriceLevel standardPriceLevel = new StandardPriceLevel(99.3, 17, Side.SELL, comparator);
        Assert.assertNotNull(standardPriceLevel);
        Assert.assertEquals(0, standardPriceLevel.getOrderCount());

        standardPriceLevel.addOrder(item);
        Assert.assertEquals(1, standardPriceLevel.getOrderCount());

        MatchableOrder[] items = createSomeOrders(17, Side.SELL);
        for (MatchableOrder newItem : items) {
            standardPriceLevel.addOrder(newItem);
        }
        Assert.assertEquals(18, standardPriceLevel.getOrderCount());
        standardPriceLevel.removeOrder(item);
        Assert.assertEquals(17, standardPriceLevel.getOrderCount());
        standardPriceLevel.removeOrder(items[3]);
        Assert.assertEquals(16, standardPriceLevel.getOrderCount());

        standardPriceLevel.removeOrder(items[0]);
        Assert.assertEquals(15, standardPriceLevel.getOrderCount());


    }


    @Test
    public void testGetTopItem() throws Exception {
        Comparator comparator = new OrderPriceTimeComparator();
        StandardPriceLevel standardPriceLevel = new StandardPriceLevel(99.3, 17, Side.BUY, comparator);
        Assert.assertNotNull(standardPriceLevel);
        MatchableOrder[] items = createSomeOrders(17, Side.BUY);
        for (MatchableOrder item : items) {
            standardPriceLevel.addOrder(item);
        }
        MatchableOrder item = standardPriceLevel.getTopOrder();
        Assert.assertNotNull(item);
        Assert.assertEquals(item, items[0]);

    }

    @Test
    public void testOrderingOfItems() throws Exception {
        Comparator comparator = new OrderPriceTimeComparator();
        StandardPriceLevel standardPriceLevel = new StandardPriceLevel(101, 17, Side.BUY, comparator);
        Assert.assertNotNull(standardPriceLevel);

        Instrument instrument = new StandardInstrument("VOD.L", "Vodafone", null, 100, 1, 1);
        Client client = new StandardClient(1, "Test Client");

        String clientOrderID = "12345ABC";

        OrderStub stub1 = new OrderStub(OrderOrigin.ALGO, instrument, Side.BUY, clientOrderID, client, ExecutionType.CONTINUOUS, 100);
        Thread.sleep(1000);

        OrderStub stub2 = new OrderStub(OrderOrigin.ALGO, instrument, Side.BUY, clientOrderID, client, ExecutionType.CONTINUOUS, 100);
        Thread.sleep(1000);

        OrderStub stub3 = new OrderStub(OrderOrigin.ALGO, instrument, Side.BUY, clientOrderID, client, ExecutionType.CONTINUOUS, 100);

        StandardMatchableOrder order1 = new StandardMatchableOrder(new StandardMarketOrder(stub1));
        StandardMatchableOrder order2 = new StandardMatchableOrder(new StandardMarketOrder(stub2));
        StandardMatchableOrder order3 = new StandardMatchableOrder(new StandardMarketOrder(stub3));

        Assert.assertTrue(comparator.compare(order1.getOrder(), order2.getOrder()) > 0);
        Assert.assertTrue(comparator.compare(order2.getOrder(), order3.getOrder()) > 0);

        StandardMatchableOrder order4 = new StandardMatchableOrder(new StandardLimitOrder(stub1, 100));
        StandardMatchableOrder order5 = new StandardMatchableOrder(new StandardLimitOrder(stub2, 100));

        Assert.assertTrue(comparator.compare(order4.getOrder(), order5.getOrder()) > 0);
        Assert.assertTrue(comparator.compare(order1.getOrder(), order5.getOrder()) > 0);


    }

    @Test
    public void testIterator() throws Exception {
        Comparator comparator = new OrderPriceTimeComparator();
        int numOrders = 16;
        StandardPriceLevel standardPriceLevel = new StandardPriceLevel(101, numOrders, Side.BUY, comparator);
        Assert.assertNotNull(standardPriceLevel);
        MatchableOrder[] items = createSomeOrders(numOrders, Side.BUY);
        for (MatchableOrder item : items) {
            standardPriceLevel.addOrder(item);
        }
        Iterator<MatchableOrder> iterator = standardPriceLevel.iterator();
        Assert.assertNotNull(iterator);
        Assert.assertTrue(iterator.hasNext());
        for (int ii = 0; ii < numOrders; ii++) {
            MatchableOrder order = iterator.next();
            Assert.assertNotNull(order);
        }
        Assert.assertFalse(iterator.hasNext());

    }

    @Test
    public void testResize() throws Exception {
        Comparator comparator = new OrderPriceTimeComparator();
        int numOrders = 16;
        StandardPriceLevel standardPriceLevel = new StandardPriceLevel(101, numOrders, Side.BUY, comparator);
        Assert.assertNotNull(standardPriceLevel);
        MatchableOrder[] items = createSomeOrders(numOrders, Side.BUY);
        for (MatchableOrder item : items) {
            standardPriceLevel.addOrder(item);
        }
        MatchableOrder[] items2 = createSomeOrders(2, Side.BUY);
        int oldLength = standardPriceLevel.getOrders().length;
        standardPriceLevel.addOrder(items2[0]);
        Assert.assertTrue(standardPriceLevel.getOrders().length > oldLength);

    }


    @Test
    public void testLiquidity() throws Exception {
        Instrument instrument = new StandardInstrument("VOD.L", "Vodafone", null, 100, 1, 1);
        Client client = new StandardClient(1, "Test Client");

        String clientOrderID = "12345ABC";
        OrderStub stub = new OrderStub(OrderOrigin.ALGO, instrument, Side.SELL, clientOrderID, client, ExecutionType.CONTINUOUS, 102);
        MatchableOrder item = new StandardMatchableOrder(new StandardMarketOrder(stub));

        Comparator comparator = new OrderPriceTimeComparator();
        StandardPriceLevel standardPriceLevel = new StandardPriceLevel(99.3, 17, Side.SELL, comparator);
        Assert.assertNotNull(standardPriceLevel);

        standardPriceLevel.addOrder(item);
        Assert.assertEquals(102.0, standardPriceLevel.getLiquidity(), 0.0);

        OrderStub stub33 = new OrderStub(OrderOrigin.ALGO, instrument, Side.SELL, clientOrderID, client, ExecutionType.CONTINUOUS, 33);
        MatchableOrder item2 = new StandardMatchableOrder(new StandardMarketOrder(stub33));
        standardPriceLevel.addOrder(item2);
        Assert.assertEquals(135.0, standardPriceLevel.getLiquidity(), 0.0);

        standardPriceLevel.removeOrder(item2);
        Assert.assertEquals(102.0, standardPriceLevel.getLiquidity(), 0.0);


    }


    private MatchableOrder[] createSomeOrders(int size, Side side) {

        Instrument instrument = new StandardInstrument("VOD.L", "Vodafone", null, 100, 1, 1);
        Client client = new StandardClient(1, "Test Client");

        String clientOrderID = "12345ABC";

        MatchableOrder[] items = new MatchableOrder[size];

        for (int ii = 0; ii < items.length; ii++) {
            OrderStub stub = new OrderStub(OrderOrigin.ALGO, instrument, side, clientOrderID, client, ExecutionType.CONTINUOUS, 100 + ii);
            items[ii] = new StandardMatchableOrder(new StandardMarketOrder(stub));
        }


        return items;

    }


}
