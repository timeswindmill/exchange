package exchange.in;

import com.higherfrequencytrading.chronicle.Excerpt;
import com.higherfrequencytrading.chronicle.impl.IndexedChronicle;
import util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExchangeInput implements Runnable {


    private final IndexedChronicle indexedChronicle;
    private final Excerpt excerpt;
    private final List<InputListener> inputListeners;


    private ExchangeInput(String dataFileName) throws IOException {
        indexedChronicle = new IndexedChronicle(dataFileName);
        excerpt = indexedChronicle.createExcerpt();
        inputListeners = new ArrayList<InputListener>();
    }

    private ExchangeInput() throws IOException {
        indexedChronicle = null;
        excerpt = null;
        inputListeners = new ArrayList<InputListener>();
    }


    public void addListener(InputListener inputListener) {
        inputListeners.add(inputListener);
    }

    public void startChronicle() {
        Thread listenerThread = new Thread(this);
        listenerThread.start();
    }

    @Override
    public void run() {
        while (true) {
            while (excerpt.nextIndex()) {
                for (InputListener inputListener : inputListeners) {
                    inputListener.handleEvent(excerpt);
                }

            }

        }
    }


    public static ExchangeInput createExchangeInput(String dataFileName) {
        try {

            ExchangeInput newExchangeInput = new ExchangeInput(dataFileName);
            return newExchangeInput;
        } catch (IOException e) {
            Log.INSTANCE.logError("Unable to create Exchange Input");
            return null;
        }
    }

    public static ExchangeInput createMockExchangeInput() {
        try {

            ExchangeInput newExchangeInput = new ExchangeInput() {
                public void startChronicle() {
                    // dont
                }

                @Override
                public void run() {

                }


            };
            return newExchangeInput;
        } catch (IOException e) {
            Log.INSTANCE.logError("Unable to create Exchange Input");
            return null;
        }
    }

}
