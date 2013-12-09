package message;

public class Address {


    private final String machineIP;
    private final String exchangeName;


    public Address(String machineIP, String exchangeName) {

        this.machineIP = (machineIP == null) ? "localhost" : machineIP;
        this.exchangeName = exchangeName;
    }

    public String getMachineIP() {
        return machineIP;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public static Address createAddress(String machineIP, String exchangeName) {
        Address newAddress = new Address(machineIP, exchangeName);
        return newAddress;
    }


}
