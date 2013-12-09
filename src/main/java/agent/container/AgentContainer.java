package agent.container;

import agent.agent.Agent;
import agent.agent.AgentConfig;
import agent.agent.ArbitrageAgent;
import agent.agent.MarketFillAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AgentContainer<T extends Agent> {

    List<T> agents = new ArrayList<T>();
    ExecutorService taskExecutor;


    public AgentContainer(AgentConfig config) {
        // setup agents according to the config
        for (Agent agent : agents) {
            //TODO use static method
            //   agent.setupAgent(config);
        }
    }


    public List<T> getAgents() {
        return agents;
    }


    public void startAllAgents() throws InterruptedException {
        taskExecutor = Executors.newFixedThreadPool(agents.size());
        taskExecutor.invokeAll(agents);
    }

    public void stopAllAgents() {
        for (Agent agent : agents) {
            agent.stopAgent();
        }
    }


    public static AgentContainer createArbitrageContainer(AgentConfig config) {
        AgentContainer<ArbitrageAgent> agentContainer = new AgentContainer<ArbitrageAgent>(config);
        return agentContainer;
    }


    public static AgentContainer createMarketFillContainer(AgentConfig config) {
        AgentContainer<MarketFillAgent> agentContainer = new AgentContainer<MarketFillAgent>(config);
        return agentContainer;
    }


}
