package message;

public class RoutingDetails {


    private final Address destinationAddress;
    private final Address senderAddress;

    public RoutingDetails(Address destinationAddress, Address senderAddress) {
        this.destinationAddress = destinationAddress;
        this.senderAddress = senderAddress;
    }


    public Address getDestinationAddress() {
        return destinationAddress;
    }

    public Address getSenderAddress() {
        return senderAddress;
    }

    public static RoutingDetails createRoutingDetails(String machineIP, String exchangeName) {
        Address address = Address.createAddress(machineIP, exchangeName);
        RoutingDetails newRoutingDetails = new RoutingDetails(address, null);
        return newRoutingDetails;
    }
}
