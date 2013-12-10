package FIX.messageHandlers;

import machine.Machine;
import message.*;
import order.StandardOrder;
import quickfix.*;
import util.Log;

public class MachineInputHandler extends FixMessageHandler {

    // need to create message to add to machine
    private Machine machine = Machine.INSTANCE;


    public void onCreate(SessionID sessionID) {
        Log.INSTANCE.logInfo("******* Session created" + sessionID);

    }

    public void onLogon(SessionID sessionID) {
        Log.INSTANCE.logInfo("******* Logon " + sessionID);

    }

    public void onLogout(SessionID sessionID) {
        Log.INSTANCE.logInfo("******* Logoout " + sessionID);
    }

    public void toAdmin(Message message, SessionID sessionID) {
        Log.INSTANCE.logInfo("******* GeneralMessage " + message + "" + sessionID);

    }

    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        Log.INSTANCE.logInfo("******* GeneralMessage from Admin " + message + "" + sessionID);
    }

    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        Log.INSTANCE.logInfo("******* GeneralMessage to App " + message + "" + sessionID);

    }

    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        Log.INSTANCE.logInfo("******* GeneralMessage from App " + message + "" + sessionID);
        createMessageFromFix(message);
    }


    private GeneralMessage createMessageFromFix(Message fixMessage) {
        GeneralMessage newMessage = null;
        if (fixMessage.isAdmin()) {
            return null;
        }
        String messageType = "";
        try {
            messageType = fixMessage.getString(35);
        } catch (FieldNotFound fieldNotFound) {
            Log.INSTANCE.logError("Message Type Field not found");
            return null;
        }
        switch (messageType) {
            case "0":
                newMessage = null;
                break;

            case "D":
                newMessage = createOrderMessageFromFix(fixMessage);
                break;
            //TODO  more message types
        }

        return newMessage;

    }

    private GeneralMessage<StandardOrder> createOrderMessageFromFix(Message fixMessage) {

        String CLordID = null;
        try {
            CLordID = fixMessage.getString(11);
            String RIC = fixMessage.getString(55);
            String Side = fixMessage.getString(54);
            String Price = fixMessage.getString(99);
            String Quantity = fixMessage.getString(38);
            String Instructions = "None";
            String ClientID = "1";

            Address address = Address.createAddress("127.0.0.1", null);
            RoutingDetails routingDetails = new RoutingDetails(null, address);
            //TODO need to tue Address to sender comp ID to allow return
            StringBuilder sb = new StringBuilder();
            sb.append("EX1|").append(CLordID).append("|").append(ClientID).append("|").append(RIC).append("|").append(Side).append("|");
            sb.append(Price).append("|").append(Quantity).append("|").append(Instructions);

            // private final String binaryMessage = "Exchange|ClordID|ClientID|RIC|Side|Price|Quantity|Instructions";

            StandardOrder newOrder = SimpleFormatOrderTranslator.createSimpleOrder(sb.toString());
            GeneralMessage<StandardOrder> newMessage = new SimpleOrderMessage(newOrder, routingDetails);
            return newMessage;

        } catch (FieldNotFound fieldNotFound) {
            Log.INSTANCE.logError(" Field not found");
            return null;
        }

    }


}
