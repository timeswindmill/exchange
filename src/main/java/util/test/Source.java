package util.test;

import com.higherfrequencytrading.chronicle.Chronicle;
import com.higherfrequencytrading.chronicle.Excerpt;
import com.higherfrequencytrading.chronicle.impl.IndexedChronicle;
import com.higherfrequencytrading.chronicle.tcp.InProcessChronicleSource;
import com.higherfrequencytrading.chronicle.tools.ChronicleTools;

import java.io.IOException;

public class Source {
    //    static final int RATE = Integer.getInteger("rate", 10); // per milli-second.
//    static final int WARMUP = Integer.getInteger("warmup", 20 * 1000);
//    static final int MESSAGES = Integer.getInteger("messages", 1000 * 1000);
    static final int PORT = Integer.getInteger("port", 19231);
    static final String TMP = System.getProperty("java.io.tmpdir");

    public static void main(String... ignored) throws IOException, InterruptedException {
        String basePath = TMP + "/1-source";
        ChronicleTools.deleteOnExit(basePath);

        Chronicle chronicle = new IndexedChronicle(basePath);
        InProcessChronicleSource source = new InProcessChronicleSource(chronicle, PORT);
        source.busyWaitTimeNS(2 * 1000 * 1000);
        final Excerpt excerpt = source.createExcerpt();
        System.out.println("Allowing connection.");
        Thread.sleep(1000);
        System.out.println("Warming up.");

        System.out.println("Sending messages.");
        for (int j = 0; j < 10; j++) {
            excerpt.startExcerpt(100);
            excerpt.writeUTF("Message " + j);
            excerpt.finish();
            pause(10 * 1000);
        }
        System.out.println("Messages written");
        Thread.sleep(1000);


        source.close();
    }

    private static void pause(int nanos) {
        long end = System.nanoTime() + nanos;
        while (System.nanoTime() < end) {
            // busy wait.
        }
    }


    public static void writeMessage() {

//          excerpt.startExcerpt(1024); // a guess
//        excerpt.writeEnum(MessageType.update);
//        metaData.writeMarshallable(excerpt);
//        update.writeMarshallable(excerpt);
//        excerpt.finish();
    }


}
