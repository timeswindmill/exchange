package book.out;

import com.higherfrequencytrading.chronicle.Excerpt;
import com.higherfrequencytrading.chronicle.impl.IndexedChronicle;
import order.Side;
import prices.PriceData;
import prices.event.PriceEvent;
import prices.event.PriceEventType;
import util.Log;

import java.io.IOException;

//TODO allow cross machine reporter
public class PriceReporter {

    private final IndexedChronicle indexedChronicle;
    private final Excerpt excerpt;


    private PriceReporter(String dataFileName) throws IOException {
        indexedChronicle = new IndexedChronicle(dataFileName);
        excerpt = indexedChronicle.createExcerpt();
    }

    private PriceReporter() {
        indexedChronicle = null;
        excerpt = null;
    }


    public void reportBestPriceChange(double newPrice, Side side) {
        // zero quantity - don't need for this price change
        PriceData[] priceData = new PriceData[]{new PriceData(newPrice, 0, side)};
        PriceEvent priceEvent = new PriceEvent(PriceEventType.BESTPRICECHANGE, priceData);
        excerpt.startExcerpt(50);
        excerpt.writeLong(System.nanoTime());
        // TODO write small object here
        excerpt.writeObject(priceEvent);
        excerpt.finish();

        indexedChronicle.close();

    }


    public static PriceReporter createPriceReporter(String reportFileName) {
        try {
            PriceReporter priceReporter = new PriceReporter(reportFileName);
            return priceReporter;
        } catch (IOException e) {
            Log.INSTANCE.logError("Error creating price reporter " + e);
            return null;
        }


    }

    public static PriceReporter createMockPriceReporter() {
        PriceReporter priceReporter = new PriceReporter() {
            public void reportBestPriceChange(double newPrice, Side side) {

            }
        };
        return priceReporter;


    }

}
