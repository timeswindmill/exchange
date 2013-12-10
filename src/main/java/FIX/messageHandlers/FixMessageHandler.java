package FIX.messageHandlers;

import quickfix.*;

public abstract class FixMessageHandler {

    public void onCreate(SessionID sessionID) {
        System.out.println("******* Session created" + sessionID);

    }

    public void onLogon(SessionID sessionID) {
        System.out.println("******* Logon " + sessionID);

    }

    public void onLogout(SessionID sessionID) {
        System.out.println("******* Logoout " + sessionID);
    }

    public void toAdmin(Message message, SessionID sessionID) {
        System.out.println("******* GeneralMessage " + message + "" + sessionID);

    }

    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        System.out.println("******* GeneralMessage from Admin " + message + "" + sessionID);

    }

    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        System.out.println("******* GeneralMessage to App " + message + "" + sessionID);

    }

    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        System.out.println("******* GeneralMessage from App " + message + "" + sessionID);


    }


}
