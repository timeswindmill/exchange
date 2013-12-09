package dataStructures.jobExchanger;

import org.junit.Assert;

import java.util.concurrent.atomic.AtomicInteger;

public class StandardConsumerTest {


    public void smallThroughPutTest() throws Exception {
        final AtomicInteger count = new AtomicInteger();
        DataArray<String> array = new DataArray<>(200);

        Assert.assertNotNull(array);

        StandardConsumer standardConsumer = new StandardConsumer(array) {
            public void consumeElement(DataElement element) {
                count.getAndIncrement();

            }
        };

        standardConsumer.startConsumer();

        for (int ii = 0; ii < 100; ii++) {
            Thread thread = createThread(array);
            thread.start();
        }
        System.out.println("Count is " + count.get());


    }

    private Thread createThread(final DataArray array) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                DataElement<String> element = new DataElement("test");
                Processor processor = new StandardProcessor(array);
                processor.putDataElement(element);

            }
        });

        return thread;
    }


}
