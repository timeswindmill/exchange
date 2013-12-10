package FIX.builder;

import client.Client;
import client.StandardClient;
import execution.ExecutionType;
import instruments.Instrument;
import instruments.StandardInstrument;
import order.*;
import org.junit.Test;

public class FixMessageBuilderTest {
    Instrument vodInstrument = new StandardInstrument("VOD.L", "Vodafone", null, 100, 1, 0.1);
    Instrument bacInstrument = new StandardInstrument("BAC.L", "Barclays", null, 100, 1, 1);
    // now create single market order
    Client client = new StandardClient(1, "Test Client");


    @Test
    public void testCreateNewOrderMessage() throws Exception {


        String clientOrderID = "12345ABC";
        OrderStub stub = new OrderStub(OrderOrigin.ALGO, bacInstrument, Side.SELL, clientOrderID, client, ExecutionType.CONTINUOUS, 100);
        MatchableOrder newOrder = new StandardMatchableOrder(new StandardMarketOrder(stub));
        FixMessageBuilder fixMessageBuilder = new FixMessageBuilder();
        System.out.println("FIX is " + fixMessageBuilder.createNewOrderMessage(newOrder.getOrder()));


    }

    @Test
    public void testCreateNewOrderMessage1() throws Exception {

    }

    @Test
    public void testCreateExecReportMessage() throws Exception {

    }

    @Test
    public void testCreateExecReportMessage1() throws Exception {

    }
}
