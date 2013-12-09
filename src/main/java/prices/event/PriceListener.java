package prices.event;

import com.higherfrequencytrading.chronicle.Excerpt;

public interface PriceListener {
    public void handleEvent(Excerpt excerpt);

    public void publishEvent(PriceEvent event);
}
