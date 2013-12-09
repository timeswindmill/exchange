package prices.event;

import com.higherfrequencytrading.chronicle.Excerpt;

public abstract class AbstractPriceListener implements PriceListener {

    @Override
    public void handleEvent(Excerpt excerpt) {
        final long timestamp = excerpt.readLong();
        PriceEvent event = (PriceEvent) excerpt.readObject();
        excerpt.finish();
        publishEvent(event);
    }


}
