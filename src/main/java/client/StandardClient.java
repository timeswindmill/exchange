package client;

public class StandardClient implements Client {

    private final int clientID;
    private final String clientName;

    public StandardClient(int ID, String name) {
        clientID = ID;
        clientName = name;
    }

    @Override
    public long getClientId() {
        return clientID;
    }

    @Override
    public String getClientName() {
        return clientName;
    }
}
