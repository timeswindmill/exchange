package junk;

public class MachineInputHandler { //extends FixMessageHandler {

//    // need to create message to add to machine
//    private Machine machine = Machine.INSTANCE;
//
//
//    public void onCreate(SessionID sessionID) {
//        Log.INSTANCE.logInfo("******* Session created" + sessionID);
//
//    }
//
//    public void onLogon(SessionID sessionID) {
//        Log.INSTANCE.logInfo("******* Logon " + sessionID);
//
//    }
//
//    public void onLogout(SessionID sessionID) {
//        Log.INSTANCE.logInfo("******* Logoout " + sessionID);
//    }
//
//    public void toAdmin(Message message, SessionID sessionID) {
//        Log.INSTANCE.logInfo("******* GeneralMessage " + message + "" + sessionID);
//
//    }
//
//    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
//        Log.INSTANCE.logInfo("******* GeneralMessage from Admin " + message + "" + sessionID);
//    }
//
//    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
//        Log.INSTANCE.logInfo("******* GeneralMessage to App " + message + "" + sessionID);
//
//    }
//
//    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
//        Log.INSTANCE.logInfo("******* GeneralMessage from App " + message + "" + sessionID);
//        createMessageFromFix(message);
//    }
//
//
//    private GeneralMessage createMessageFromFix(Message fixMessage) {
//
//
//        GeneralMessage newMessage = null;
//        if (fixMessage.isAdmin()) {
//            return null;
//        }
//        String messageType = "";
//        try {
//            messageType = fixMessage.getHeader().getString(MsgType.FIELD);
//        } catch (FieldNotFound fieldNotFound) {
//            Log.INSTANCE.logError("Message Type Field not found");
//            return null;
//        }
//        switch (messageType) {
//            case MsgType.HEARTBEAT:
//                newMessage = null;
//                break;
//
//            case MsgType.ORDER_SINGLE:
//                newMessage = createOrderMessageFromFix(fixMessage);
//                break;
//            //TODO  more message types
//        }
//
//        return newMessage;
//
//    }

//    private GeneralMessage<StandardOrder> createOrderMessageFromFix(Message fixMessage) {
//        Address address = Address.createAddress("127.0.0.1", null);
//        RoutingDetails routingDetails = new RoutingDetails(null, address, fixSession);
//        //TODO need to tue Address to sender comp ID to allow return
//        StandardOrder newOrder = SimpleFormatOrderTranslator.createSimpleOrder(fixMessage);
//        GeneralMessage<StandardOrder> newMessage = new SimpleOrderMessage(newOrder, routingDetails);
//        return newMessage;
//
//    }
//

}
