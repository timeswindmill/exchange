package message;

public interface Message<T> {
    public T getMessage();

    public RoutingDetails getRoutingDetails();

}
