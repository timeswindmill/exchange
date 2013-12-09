package agent.agent;

import agent.strategy.Strategy;
import order.Side;
import prices.PriceData;
import prices.event.PriceEventType;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MarketFillAgent extends StandardAgent {

    private AtomicInteger IDFountain = new AtomicInteger(113);


    private MarketFillAgent(List<String> exchangeInputFiles) {
        super(exchangeInputFiles);
    }


    @Override
    public Strategy getStrategy() {
        return Strategy.MARKETFILL;
    }


    public static MarketFillAgent createAgent(AgentConfig config) {

        List<String> exchangeInputFiles = config.getExchangeInputFiles();
        MarketFillAgent agent = new MarketFillAgent(exchangeInputFiles);

        return agent;
    }

    @Override
    public void performScript() {

        int id = IDFountain.getAndIncrement();
        Side side = (id % 2 == 0) ? Side.BUY : Side.SELL;
        String newOrder = "ABC" + side + "|1|BTX|" + side + "|0|450|none";
        sendOrder(newOrder);
    }

    @Override
    public void onPriceEvent(PriceEventType type, PriceData data) {


    }

    @Override
    public void sendOrder(String order) {
        //TODO
        // send to all exchanges  i guess
        sendOrderToExchange("x1", order);


    }


}
