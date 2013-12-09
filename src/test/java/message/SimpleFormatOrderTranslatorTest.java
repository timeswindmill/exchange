package message;

import machine.Machine;
import machine.RunConfig;
import order.StandardOrder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SimpleFormatOrderTranslatorTest {

    @Before
    public void setupMachine() {
        Machine.setupMachine(RunConfig.UNITTEST);
    }


    @Test
    public void testCreateOrderFromString() throws Exception {
        // private final String binaryMessage = "Exchange|ClordID|ClientID|RIC|Side|Price|Quantity|Instructions";

        String message = "x1|ABC123|1|BTX|BUY|0|450|none";
        StandardOrder order = SimpleFormatOrderTranslator.createSimpleOrder(message);

        Assert.assertEquals("ABC123", order.getClientOrderID());
        Assert.assertEquals(0.0, order.getPrice(), 0);


        String message2 = "x1|ABC124|1|BTX|BUY|0|450|none";
        StandardOrder order2 = SimpleFormatOrderTranslator.createSimpleOrder(message2);
        Assert.assertEquals("ABC124", order2.getClientOrderID());
        Assert.assertEquals(0.0, order2.getPrice(), 0);


    }


//    public Map<Long,Client> getClients(){
//        Map<Long,Client> clients = new HashMap<>();
//        clients.put(1L, new StandardClient(1, "Client 1"));
//        clients.put(2L, new StandardClient(2, "Client 2"));
//
//        return clients;
//    }
//
//
//    public Map<String,Instrument> getInstruments(){
//        //TODO  read config from file / db
//        Map<String,Instrument> instruments = new HashMap<>();
//
//        instruments.put("AAX", new StandardInstrument("AAX", "AAX Equity", null, 53.7, 1, 0.1)) ;
//        instruments.put("BTX",new StandardInstrument("BTX","BTX Equity",null,98.7,1,0.1)) ;
//        instruments.put("CDX", new StandardInstrument("CDX","CDX Equity",null,127.3,1,0.1)) ;
//
//        return instruments;
//
//    }
//


}
