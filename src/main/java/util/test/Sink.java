package util.test;

import com.higherfrequencytrading.chronicle.Chronicle;
import com.higherfrequencytrading.chronicle.impl.IndexedChronicle;
import com.higherfrequencytrading.chronicle.tcp.InProcessChronicleSink;
import com.higherfrequencytrading.chronicle.tools.ChronicleTools;

import java.io.IOException;

public class Sink {
    static final String HOST3 = System.getProperty("host3", "localhost");
    static final int PORT3 = 19231;
    //    static final int WARMUP = SourceMain.WARMUP;
//    static final int MESSAGES = SourceMain.MESSAGES;
    static final String TMP = System.getProperty("java.io.tmpdir");
    //  public static final int NET_LATENCY = Integer.getInteger("net.latency", 30 * 1000);

    public static void main(String... ignored) throws IOException, InterruptedException {
        String basePath3 = TMP + "/3-sink";

        ChronicleTools.deleteOnExit(basePath3);

        Chronicle chronicle3 = new IndexedChronicle(basePath3);
        InProcessChronicleSink sink3 = new InProcessChronicleSink(chronicle3, HOST3, PORT3);
        final EventsReader sinkReader = new EventsReader(sink3.createExcerpt(), new LatencyEvents());

        while (true) {
            if (!sinkReader.read())
                pause();
        }
    }

    private static void pause() {
        // nothing for now.
    }

    static class LatencyEvents implements Events {
        //       static final TimingStage[] VALUES = TimingStage.values();
        //       final long[][] timings = new long[VALUES.length - 1][SourceMain.MESSAGES];
        //       final long[] endToEndTimings = new long[SourceMain.MESSAGES];
        //       final Differencer[] differencers = {
        //               new VanillaDifferencer(), // same host
        //               NET_LATENCY == 0 ? new VanillaDifferencer() : new RunningMinimum(NET_LATENCY), // source to engine
        //               new VanillaDifferencer(), // same host
        //               new VanillaDifferencer(), // same host
        //               new VanillaDifferencer(), // same host
        //               NET_LATENCY == 0 ? new VanillaDifferencer() : new RunningMinimum(NET_LATENCY), // engine to sink
        //       };
        //       int count = -WARMUP;

        @Override
        public void onMarketData(String msg) {
            System.out.println("received " + msg);
        }
    }
}