package exchange.in;

import com.higherfrequencytrading.chronicle.Excerpt;
import com.higherfrequencytrading.chronicle.impl.IndexedChronicle;
import exchange.ExchangeConfig;
import exchange.StandardExchange;

public class testListener {

    private ExchangeConfig config = ExchangeConfig.createTestExchangeConfigs()[0];

    public static void main(String args[]) {
        testListener tm = new testListener();
        tm.runTest();
    }


    public void runTest() {


        StandardExchange standardExchange = StandardExchange.createExchange(config);

        standardExchange.startRouterAndQueues();


//            SimpleBinaryListener sbl = new SimpleBinaryListener();
//            ExchangeInput ei = ExchangeInput.createExchangeInput(filename,sbl);
//            ei.startChronicle();
//
        try {
            writeMessages();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }


    }

    public void writeMessages() throws Exception {
        IndexedChronicle ic = new IndexedChronicle(config.getInputFileName());
        Excerpt excerpt = ic.createExcerpt();

        for (int ii = 0; ii < 4; ii++) {
            String side = "BUY";
            if (ii % 2 == 0) {
                ;
                side = "SELL";
            }

            excerpt.startExcerpt(40);
            String order1 = "x1|ABC123" + ii + "|1|BTX|" + side + "|0|450|none";
            excerpt.writeLong(System.nanoTime());
            excerpt.writeUTF(order1);
            excerpt.finish();

        }

        ic.close();


    }


}
