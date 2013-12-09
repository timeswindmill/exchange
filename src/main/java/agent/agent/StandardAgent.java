package agent.agent;


import com.higherfrequencytrading.chronicle.Excerpt;
import com.higherfrequencytrading.chronicle.impl.IndexedChronicle;
import message.SimpleFormatOrderTranslator;
import util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class StandardAgent implements Agent {
    private final AtomicBoolean halt = new AtomicBoolean(false);
    private final List<String> exchangeInputFiles;
    private final Map<String, ChronicleUtil> chronicleMap;


    protected StandardAgent(List<String> exchangeInputFiles) {
        this.exchangeInputFiles = exchangeInputFiles;
        this.chronicleMap = new HashMap<>(10);
        setUpMaps();


    }

    private void setUpMaps() {
        //TODO read from config
        ChronicleUtil chronicle = new ChronicleUtil("x1", "/home/laurence/Work/Dev/Java/MatchingProjects/Exchange/data/x1");
        chronicleMap.put(chronicle.getName(), chronicle);

    }


    protected boolean sendOrderToExchange(String exchangeName, String order) {
        boolean sent = false;

        if (!SimpleFormatOrderTranslator.validateOrder(order)) {
            Log.INSTANCE.logError("Error validating order");
            return false;
        }
        ChronicleUtil chronicle = chronicleMap.get(exchangeName);
        if (chronicle != null) {
            sent = chronicle.writeOrderToChronicle(order);
        }

        return sent;
    }


    @Override
    public void stopAgent() {
        halt.set(true);
    }

    @Override
    public void performScript() {

    }

    @Override
    public Integer call() throws Exception {


        while (halt.get() == false) {
            performScript();
        }

        return null;
    }

    private class ChronicleUtil {
        private final IndexedChronicle indexedChronicle;
        private final Excerpt excerpt;
        private final String chronicleName;

        public ChronicleUtil(String chronicleName, String chronicleFileName) {
            this.chronicleName = chronicleName;
            //           chroniclePath  = ("/home/laurence/Work/Dev/Java/MatchingProjects/Exchange/data/journal");
            IndexedChronicle ic = null;
            Excerpt ex = null;
            try {
                ic = new IndexedChronicle(chronicleFileName);
                ex = ic.createExcerpt();
            } catch (IOException e) {
                Log.INSTANCE.logError("Error creating chronicle " + e);
            }
            indexedChronicle = ic;
            excerpt = ex;

        }


        public boolean writeOrderToChronicle(String order) {
            boolean sent = false;

            excerpt.startExcerpt(50);
            excerpt.writeLong(System.nanoTime());
            excerpt.writeUTF(order);
            excerpt.finish();

            //TODO do later ?
            indexedChronicle.close();

            return sent;

        }

        public String getName() {
            return this.chronicleName;
        }

    }

}
