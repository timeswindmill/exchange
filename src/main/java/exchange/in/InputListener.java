package exchange.in;

import com.higherfrequencytrading.chronicle.Excerpt;

public interface InputListener {
    public void handleEvent(Excerpt excerpt);

    public void publishEvent(String message);

}
