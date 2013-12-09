package util.test;

import com.higherfrequencytrading.chronicle.impl.IndexedChronicle;
import com.higherfrequencytrading.chronicle.tcp.gw.SocketGateway;
import com.higherfrequencytrading.chronicle.tools.WaitingThread;

import java.io.IOException;
import java.net.InetSocketAddress;

public class GatewayTest {

    SocketGateway gateway;
    String inboundPath = "/home/laurence/Work/Dev/Java/MatchingProjects/Exchange/data/gatewayin";
    String outboundPath = "/home/laurence/Work/Dev/Java/MatchingProjects/Exchange/data/gatewayout";

    public static void main(String... args) {
        GatewayTest gt = new GatewayTest();

        gt.runTest();
    }

    private GatewayTest() {
//        public SocketGateway(final InetSocketAddress address, @NotNull Chronicle outbound, @NotNull Chronicle inbound, @NotNull WaitingThread waitingThread) {
        WaitingThread thread = new WaitingThread(1, "SocketGateway ", false);
        try {
            gateway = new SocketGateway(new InetSocketAddress("127.0.0.1", 19321),
                    new IndexedChronicle(outboundPath), new IndexedChronicle(inboundPath), thread);
        } catch (IOException e) {
            System.out.println("Error " + e);
        }
    }

    private void runTest() {
        gateway.run();
    }


}
