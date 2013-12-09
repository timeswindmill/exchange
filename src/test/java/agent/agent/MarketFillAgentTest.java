package agent.agent;

import exchange.ExchangeConfig;
import order.Side;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MarketFillAgentTest {

    @Test
    public void testSetupAgent() throws Exception {

        ExchangeConfig config = ExchangeConfig.createTestExchangeConfigs()[0];

        List<String> inputFiles = new ArrayList<>();
        inputFiles.add(config.getInputFileName());
        MarketFillAgent mfa = MarketFillAgent.createAgent(new AgentConfig());


        Assert.assertNotNull(mfa);


    }

    @Test
    public void testSendSingleOrder() throws Exception {

        ExchangeConfig config = ExchangeConfig.createTestExchangeConfigs()[0];
        List<String> inputFiles = new ArrayList<>();
        inputFiles.add(config.getInputFileName());
        MarketFillAgent mfa = MarketFillAgent.createAgent(new AgentConfig());

        Assert.assertNotNull(mfa);

        String order1 = "x1|ABC1235" + "|1|BTX|" + Side.BUY + "|0|450|none";
        mfa.sendOrder(order1);


    }

    @Test
    public void testScript() throws Exception {

        ExchangeConfig config = ExchangeConfig.createTestExchangeConfigs()[0];
        List<String> inputFiles = new ArrayList<>();
        inputFiles.add(config.getInputFileName());
        MarketFillAgent mfa = MarketFillAgent.createAgent(new AgentConfig());

        Assert.assertNotNull(mfa);

        mfa.performScript();


    }


}
