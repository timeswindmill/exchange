package exchange.in;

import com.higherfrequencytrading.chronicle.Excerpt;

public class SimpleBinaryListener implements InputListener {


    @Override
    public void handleEvent(Excerpt excerpt) {
        final long timestamp = excerpt.readLong();
        String orderString = excerpt.readUTF();
        //   System.out.println(System.nanoTime() + " - " + order);
        excerpt.finish();
        //   System.out.println("default listener " + excerpt);
        publishEvent(orderString);
    }

    @Override
    public void publishEvent(String message) {

    }
}
