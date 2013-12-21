package message;

import quickfix.SessionID;

public class RoutingDetails {


    private final Address destinationAddress;
    private final Address senderAddress;


    private final SessionID fixSessionID;

    public RoutingDetails(Address destinationAddress, Address senderAddress, SessionID fixSessionID) {
        this.destinationAddress = destinationAddress;
        this.senderAddress = senderAddress;
        this.fixSessionID = fixSessionID;
    }


    public Address getDestinationAddress() {
        return destinationAddress;
    }

    public Address getSenderAddress() {
        return senderAddress;
    }

    public SessionID getFixSessionID() {
        return fixSessionID;
    }

    public static RoutingDetails createRoutingDetails(String machineIP, String exchangeName) {
        Address address = Address.createAddress(machineIP, exchangeName);
        RoutingDetails newRoutingDetails = new RoutingDetails(address, null, null);
        return newRoutingDetails;
    }

    public static RoutingDetails createRoutingDetails(String machineIP, String exchangeName, SessionID fixSessionID) {
        Address address = Address.createAddress(machineIP, exchangeName);
        RoutingDetails newRoutingDetails = new RoutingDetails(address, null, fixSessionID);
        return newRoutingDetails;
    }


}
