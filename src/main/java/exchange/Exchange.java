package exchange;

import book.StandardBook;
import exchange.in.ExchangeInput;
import exchange.router.StandardRouter;

import java.util.Collection;
import java.util.Map;

public interface Exchange {
    // exchange contains order books
    public String getExchangeName();

    public void startRouterAndQueues();

    // TODO move agents to machine
    public void startAgents();

    public void stopAgents();

    public StandardRouter getRouter();

    public Collection<StandardBook> getBooks();

    public Map<String, StandardBook> getBookMap();

    public ExchangeInput getExchangeInput();

    public ExchangeConfig getExchangeConfig();
}
