package dataStructures.jobExchanger;

public class DataElement<T> {

    private T payload;

    public DataElement(T payload) {
        this.payload = payload;
    }

    public T getPayload() {

        return payload;

    }


    public String toString() {
        return payload.toString();
    }


}
