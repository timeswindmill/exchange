package FIX.fixEngine;

import FIX.messageHandlers.FixMessageHandler;
import FIX.messageHandlers.MachineInputHandler;
import org.junit.Test;

public class SimpleFixEngineTest {

    @Test
    public void testCreateSimpleFixEngine() throws Exception {
        String fileName = "/home/laurence/Work/Dev/Java/MatchingProjects/Exchange/src/main/resources/FixSettings.dat";
        FixMessageHandler handler = new MachineInputHandler();

        SimpleFixEngine simpleFixEngine = SimpleFixEngine.createSimpleFixEngine(fileName, handler);

        simpleFixEngine.startFixEngine();


    }
}
