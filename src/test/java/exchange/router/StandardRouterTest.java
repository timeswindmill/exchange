package exchange.router;

import book.StandardBook;
import book.out.PriceReporter;
import client.Client;
import client.StandardClient;
import execution.ExecutionType;
import instruments.Instrument;
import instruments.StandardInstrument;
import machine.Machine;
import machine.RunConfig;
import order.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class StandardRouterTest {

    private Map<String, StandardBook> bookMap = new HashMap<>();
    private Instrument instrument = new StandardInstrument("BAC.L", "Barclays", null, 100, 1, 1);
    //    String BacReportFile = "/home/laurence/Work/Dev/Java/MatchingProjects/Exchange/data/priceReport/BAC";
//    String AAXReportFile = "/home/laurence/Work/Dev/Java/MatchingProjects/Exchange/data/priceReport/AAX";
    PriceReporter priceReporter = PriceReporter.createMockPriceReporter();
    private StandardBook bookBAC = new StandardBook(instrument, priceReporter);

    private Instrument instrument2 = new StandardInstrument("AAX", "AAX Equity", null, 100, 1, 1);
    private StandardBook bookAAX = new StandardBook(instrument2, priceReporter);

    private final Client client = new StandardClient(1, "Test Client");

    @Before
    public void setupBooks() {
        Machine.setupMachine(RunConfig.UNITTEST);
        bookMap.put("BAC.L", bookBAC);
        bookMap.put("AAX", bookAAX);
        bookBAC.startReportQueue(false);
        bookAAX.startReportQueue(false);

    }

    @Test
    public void testCreateRouter() throws Exception {

        StandardRouter router = StandardRouter.createRouter(new RouterConfig(), bookMap);
        StandardOrder order1 = createMarketOrder(Side.BUY, 200);
        StandardOrder order2 = createMarketOrder(Side.SELL, 200);

        router.startRouter();
        router.addOrder(order1);
        router.addOrder(order2);


    }

    private StandardOrder createMarketOrder(Side side, int quantity) {
        String clientOrderID = "12345ABC";
        OrderStub stub = new OrderStub(OrderOrigin.ALGO, instrument, side, clientOrderID, client, ExecutionType.CONTINUOUS, quantity);
        return new StandardMarketOrder(stub);
    }

    private StandardOrder createLimitOrder(Side side, int quantity, double price) {
        String clientOrderID = "12345ABC";
        OrderStub stub = new OrderStub(OrderOrigin.ALGO, instrument, side, clientOrderID, client, ExecutionType.CONTINUOUS, quantity);
        return new StandardLimitOrder(stub, price);
    }


}
