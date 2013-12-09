package agent.container;

import agent.agent.AgentConfig;
import agent.agent.ArbitrageAgent;
import agent.agent.MarketFillAgent;
import org.junit.Assert;
import org.junit.Test;

public class AgentContainerTest {


    @Test
    public void testGetAgents() throws Exception {

    }

    @Test
    public void testStartAllAgents() throws Exception {
        // TODO
    }

    @Test
    public void testStopAllAgents() throws Exception {
        // TODO
    }

    @Test
    public void testCreateArbitrageContainer() throws Exception {
        AgentConfig config = new AgentConfig();
        AgentContainer<ArbitrageAgent> container = AgentContainer.createArbitrageContainer(config);
        Assert.assertNotNull(container);

    }

    @Test
    public void testCreateMarketFillContainer() throws Exception {
        AgentConfig config = new AgentConfig();
        AgentContainer<MarketFillAgent> container = AgentContainer.createMarketFillContainer(config);
        Assert.assertNotNull(container);

    }

    @Test
    public void testMarketAgentsinContainer() throws Exception {
        AgentConfig config = new AgentConfig();
        AgentContainer<MarketFillAgent> container = AgentContainer.createMarketFillContainer(config);
        Assert.assertNotNull(container);


    }


}
