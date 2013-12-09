package agent.agent;

import agent.strategy.Strategy;
import prices.PriceData;
import prices.event.PriceEventType;

import java.util.concurrent.Callable;


public interface Agent extends Callable<Integer> {

    // public void startAgent();

    //    public void setupAgent(AgentConfig config);
    public void stopAgent();

    public void performScript();

    public Strategy getStrategy();

    public void onPriceEvent(PriceEventType type, PriceData data);

    public void sendOrder(String order);
}
