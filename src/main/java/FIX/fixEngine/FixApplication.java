package FIX.fixEngine;

import FIX.fixEngine.messageHandlers.FixMessageHandler;
import quickfix.*;

public class FixApplication implements Application {


    private final FixMessageHandler fixMessageHandler;


    public FixApplication(FixMessageHandler fixMessageHandler) {

        this.fixMessageHandler = fixMessageHandler;
    }

    @Override
    public void onCreate(SessionID sessionID) {
        //fixMessageHandler.onCreate(sessionID);
    }

    @Override
    public void onLogon(SessionID sessionID) {
        // fixMessageHandler.onLogon(sessionID);
    }

    @Override
    public void onLogout(SessionID sessionID) {
        //fixMessageHandler.onLogout(sessionID);
    }

    @Override
    public void toAdmin(Message message, SessionID sessionID) {
        // fixMessageHandler.toAdmin(message, sessionID);

    }

    @Override
    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        // fixMessageHandler.fromAdmin(message, sessionID);
    }

    @Override
    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        //fixMessageHandler.toApp(message, sessionID);
    }

    @Override
    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        //fixMessageHandler.fromApp(message, sessionID);

    }


}
