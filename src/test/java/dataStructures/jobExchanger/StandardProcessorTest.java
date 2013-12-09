package dataStructures.jobExchanger;

import org.junit.Assert;
import org.junit.Test;

public class StandardProcessorTest {


    @Test
    public void testProcessor() throws Exception {

        DataArray array = new DataArray(20);

        Assert.assertNotNull(array);
        DataElement element = new DataElement("test");
        Processor processor = new StandardProcessor(array);
        processor.putDataElement(element);


    }


    @Test
    public void testMultiThreadProcessor() throws Exception {

        final DataArray array = new DataArray(20);
        Assert.assertNotNull(array);

        Thread thread1 = createThread(array);
        Thread thread2 = createThread(array);

        thread1.start();
        thread2.start();


    }


    @Test
    public void testBigMultiThreadProcessor() throws Exception {

        final DataArray array = new DataArray(200);
        Assert.assertNotNull(array);

        for (int ii = 0; ii < 3; ii++) {
            Thread thread = createThread(array);
            thread.start();
        }
    }

    private Thread createThread(final DataArray array) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                DataElement element = new DataElement("test");
                Processor processor = new StandardProcessor(array);
                processor.putDataElement(element);

            }
        });

        return thread;
    }

}
