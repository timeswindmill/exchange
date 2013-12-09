package exchange.router;

import book.StandardBook;
import book.out.PriceReporter;
import client.Client;
import client.StandardClient;
import execution.ExecutionType;
import instruments.Instrument;
import instruments.StandardInstrument;
import order.*;

import java.util.HashMap;
import java.util.Map;

//import exchange.out.disruptorStyle.PriceReportQueue;

public class testMain {
    private Map<String, StandardBook> bookMap = new HashMap<>();
    private Instrument instrument = new StandardInstrument("BAC.L", "Barclays", null, 100, 1, 1);
    //private PriceReportQueue priceReportQueue = new PriceReportQueue();
    //String BacReportFile = "/home/laurence/Work/Dev/Java/MatchingProjects/Exchange/data/priceReport/BAC";
    //String AAXReportFile = "/home/laurence/Work/Dev/Java/MatchingProjects/Exchange/data/priceReport/AAX";
    PriceReporter priceReporter = PriceReporter.createMockPriceReporter();
    private StandardBook bookBAC = new StandardBook(instrument, priceReporter);

    private Instrument instrument2 = new StandardInstrument("AAX", "AAX Equity", null, 100, 1, 1);
    private StandardBook bookAAX = new StandardBook(instrument2, priceReporter);

    private final Client client = new StandardClient(1, "Test Client");

    public static void main(String args[]) {
        testMain tm = new testMain();
        tm.runTest();


    }


    public void runTest() {
        setupBooks();

        StandardRouter router = StandardRouter.createRouter(new RouterConfig(), bookMap);
        router.startRouter();
        for (int ii = 0; ii < 12; ii++) {
            StandardOrder order1 = createMarketOrder(Side.BUY, 200 + ii, ii);
            StandardOrder order2 = createMarketOrder(Side.SELL, ii + 7, ii);
            router.addOrder(order1);
            router.addOrder(order2);

        }


    }

    private StandardOrder createMarketOrder(Side side, int quantity, int key) {
        String clientOrderID = "12345ABC" + key;
        OrderStub stub = new OrderStub(OrderOrigin.ALGO, instrument2, side, clientOrderID, client, ExecutionType.CONTINUOUS, quantity);
        return new StandardMarketOrder(stub);
    }

    public void setupBooks() {

        bookMap.put("BAC.L", bookBAC);
        bookMap.put("AAX", bookAAX);
        bookBAC.startReportQueue(false);
        bookAAX.startReportQueue(false);

    }


}
