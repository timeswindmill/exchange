package order;

import client.Client;
import client.StandardClient;
import execution.ExecutionType;
import instruments.Instrument;
import instruments.StandardInstrument;
import junit.framework.Assert;
import org.junit.Test;

public class StandardOrderTest {

    @Test
    public void createMarketOrder() throws Exception {

        {
            Instrument instrument = new StandardInstrument("VOD.L", "Vodafone", null, 100, 1, 0.5);
            Client client = new StandardClient(1, "Test Client");

            String clientOrderID = "12345ABC";

            OrderStub stub = new OrderStub(OrderOrigin.ALGO, instrument, Side.BUY, clientOrderID, client, ExecutionType.CONTINUOUS, 100);

            Order newOrder = new StandardMarketOrder(stub);

            Assert.assertNotNull(newOrder);
            Assert.assertNotNull(newOrder.getClient());
            Assert.assertNotNull(newOrder.getExecutionInstructions());
            Assert.assertNotNull(newOrder.getInstrument());


            Assert.assertEquals(OrderType.MARKET, newOrder.getOrderType());
            Assert.assertEquals(clientOrderID, newOrder.getClientOrderID());
            Assert.assertEquals(0.0, newOrder.getPrice());

            Assert.assertTrue(newOrder.getOrderID() > 0);


        }
    }

    @Test
    public void createLimitOrder() throws Exception {

        {
            Instrument instrument = new StandardInstrument("VOD.L", "Vodafone", null, 100, 1, 0.5);
            Client client = new StandardClient(1, "Test Client");

            String clientOrderID = "12345ABC";

            OrderStub stub = new OrderStub(OrderOrigin.ALGO, instrument, Side.BUY, clientOrderID, client, ExecutionType.CONTINUOUS, 200);

            Order newOrder = new StandardLimitOrder(stub, 100);

            Assert.assertNotNull(newOrder);
            Assert.assertNotNull(newOrder.getClient());
            Assert.assertNotNull(newOrder.getExecutionInstructions());
            Assert.assertNotNull(newOrder.getInstrument());


            Assert.assertEquals(OrderType.LIMIT, newOrder.getOrderType());
            Assert.assertEquals(clientOrderID, newOrder.getClientOrderID());
            Assert.assertEquals(100.0, newOrder.getPrice());
            Assert.assertEquals(200, newOrder.getOpenQuantity());

            Assert.assertTrue(newOrder.getOrderID() > 0);


        }


    }

    @Test
    public void executeMarketOrder() throws Exception {
        Instrument instrument = new StandardInstrument("VOD.L", "Vodafone", null, 100, 1, 0.5);
        Client client = new StandardClient(1, "Test Client");

        String clientOrderID = "12345ABC";

        OrderStub stub = new OrderStub(OrderOrigin.ALGO, instrument, Side.BUY, clientOrderID, client, ExecutionType.CONTINUOUS, 100);

        Order newOrder = new StandardMarketOrder(stub);

        //partial fill @market
        newOrder.executeOrder(50, 0);

    }


}
