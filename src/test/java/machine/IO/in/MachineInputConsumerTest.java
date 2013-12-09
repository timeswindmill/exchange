package machine.IO.in;

import dataStructures.jobExchanger.DataArray;
import dataStructures.jobExchanger.DataElementString;
import exchange.Exchange;
import exchange.ExchangeConfig;
import exchange.StandardExchange;
import machine.Machine;
import machine.RunConfig;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MachineInputConsumerTest {


    // private final String binaryMessage = "Exchange|ClordID|ClientID|RIC|Side|Price|Quantity|Instructions";

    @Before
    public void setupMachine() {
        Machine.setupMachine(RunConfig.UNITTEST);
    }


    @Test
    public void testRouteToExchange() throws Exception {
        ExchangeConfig[] exchangeConfigs = ExchangeConfig.createTestExchangeConfigs();
        List<Exchange> exchangeList = new ArrayList<Exchange>();
        exchangeList.add(StandardExchange.createExchange(exchangeConfigs[0]));

        MachineInputConsumer machineInputConsumer = new MachineInputConsumer(new DataArray(100), exchangeList);

        String inMessage = "x1|ABC1234|1|AAX|BUY|23.6|103|FOK";
        DataElementString dataElement = new DataElementString(inMessage);
        machineInputConsumer.consumeElement(dataElement);


    }


}
