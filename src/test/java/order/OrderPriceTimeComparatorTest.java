package order;

import client.Client;
import client.StandardClient;
import execution.ExecutionType;
import instruments.Instrument;
import instruments.StandardInstrument;
import org.junit.Assert;
import org.junit.Test;

public class OrderPriceTimeComparatorTest {
    @Test
    public void testComparePrice() throws Exception {

        OrderPriceTimeComparator comp = new OrderPriceTimeComparator();
        {

            Instrument instrument = new StandardInstrument("VOD.L", "Vodafone", null, 100, 1, 0.5);
            Client client = new StandardClient(1, "Test Client");

            String clientOrderID = "12345ABC";

            OrderStub stub1 = new OrderStub(OrderOrigin.ALGO, instrument, Side.BUY, clientOrderID, client, ExecutionType.CONTINUOUS, 200);

            // buy order so higher price is better
            Order newOrder1 = new StandardLimitOrder(stub1, 100);
            Order newOrder2 = new StandardLimitOrder(stub1, 200);


            Assert.assertTrue(comp.compare(newOrder1, newOrder2) < 0);
        }
        // now test other side
        {

            Instrument instrument = new StandardInstrument("VOD.L", "Vodafone", null, 100, 1, 0.5);
            Client client = new StandardClient(1, "Test Client");

            String clientOrderID = "12345ABC";

            OrderStub stub1 = new OrderStub(OrderOrigin.ALGO, instrument, Side.SELL, clientOrderID, client, ExecutionType.CONTINUOUS, 200);

            // sell order so lower price is better
            Order newOrder1 = new StandardLimitOrder(stub1, 100);
            Order newOrder2 = new StandardLimitOrder(stub1, 200);

            Assert.assertTrue(comp.compare(newOrder1, newOrder2) > 0);
        }
        // now test same price but earlier order
        {

            Instrument instrument = new StandardInstrument("VOD.L", "Vodafone", null, 100, 1, 0.5);
            Client client = new StandardClient(1, "Test Client");

            String clientOrderID = "12345ABC";

            OrderStub stub1 = new OrderStub(OrderOrigin.ALGO, instrument, Side.SELL, clientOrderID, client, ExecutionType.CONTINUOUS, 200);
            Order newOrder1 = new StandardLimitOrder(stub1, 100);
            Thread.sleep(1000);
            OrderStub stub2 = new OrderStub(OrderOrigin.ALGO, instrument, Side.SELL, clientOrderID, client, ExecutionType.CONTINUOUS, 200);
            Order newOrder2 = new StandardLimitOrder(stub2, 100);

            Assert.assertTrue(comp.compare(newOrder1, newOrder2) > 0);
        }

        // now test opposite sides
        {

            Instrument instrument = new StandardInstrument("VOD.L", "Vodafone", null, 100, 1, 0.5);
            Client client = new StandardClient(1, "Test Client");

            String clientOrderID = "12345ABC";

            OrderStub stub1 = new OrderStub(OrderOrigin.ALGO, instrument, Side.BUY, clientOrderID, client, ExecutionType.CONTINUOUS, 200);
            Order newOrder1 = new StandardLimitOrder(stub1, 100);
            OrderStub stub2 = new OrderStub(OrderOrigin.ALGO, instrument, Side.SELL, clientOrderID, client, ExecutionType.CONTINUOUS, 200);
            Order newOrder2 = new StandardLimitOrder(stub2, 100);

            Assert.assertTrue(comp.compare(newOrder1, newOrder2) == 0);
        }


    }
}
