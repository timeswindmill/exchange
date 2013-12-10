package FIX.fixEngine;

import FIX.messageHandlers.FixMessageHandler;
import quickfix.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SimpleFixEngine {

    private final Acceptor acceptor;

    private SimpleFixEngine(Acceptor acceptor) {
        this.acceptor = acceptor;
    }

    public void startFixEngine() throws ConfigError {
        acceptor.start();
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
        }
        acceptor.stop();
    }


    public static SimpleFixEngine createSimpleFixEngine(String propertiesFileName, FixMessageHandler handler) {
        SimpleFixEngine fixEngine;

        try {
            SessionSettings settings = new SessionSettings(new FileInputStream(propertiesFileName));
            MessageStoreFactory storeFactory = new FileStoreFactory(settings);
            LogFactory logFactory = new FileLogFactory(settings);
            MessageFactory messageFactory = new DefaultMessageFactory();
            FixApplication application = new FixApplication(handler);

            Acceptor acceptor = new SocketAcceptor
                    (application, storeFactory, settings, logFactory, messageFactory);
            fixEngine = new SimpleFixEngine(acceptor);

        } catch (ConfigError configError) {
            return null;
        } catch (FileNotFoundException e) {
            return null;
        }
        return fixEngine;
    }


}
