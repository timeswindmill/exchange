package message;

public interface GeneralMessage<T> {
    public T getMessage();

    public RoutingDetails getRoutingDetails();

}
