package client;

import java.io.Serializable;

public interface Client extends Serializable {

    public long getClientId();

    public String getClientName();


}
