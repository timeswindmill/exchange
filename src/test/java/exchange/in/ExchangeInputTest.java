package exchange.in;

import exchange.ExchangeConfig;
import exchange.StandardExchange;
import machine.Machine;
import machine.RunConfig;
import org.junit.Before;
import org.junit.Test;

public class ExchangeInputTest {

    private ExchangeConfig config = ExchangeConfig.createTestExchangeConfigs()[0];

    @Before
    public void setUpMachine() {
        Machine.setupMachine(RunConfig.UNITTEST);
    }


    @Test
    public void testInput() throws Exception {
//        String df = "/home/laurence/Work/Dev/Java/MatchingProjects/Exchange/data/datafile";
//        SimpleBinaryListener sbl = new SimpleBinaryListener();
//        ExchangeInput ei = ExchangeInput.createExchangeInput(df,sbl);
//        ei.startChronicle();
//


    }

    @Test
    public void testExchange() throws Exception {

        StandardExchange standardExchange = StandardExchange.createExchange(config);

        standardExchange.startRouterAndQueues();

    }


}
