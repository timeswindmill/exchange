package message;

import order.StandardOrder;

public class SimpleOrderMessage implements GeneralMessage<StandardOrder> {

    private final StandardOrder order;
    private final RoutingDetails routingDetails;

    public SimpleOrderMessage(StandardOrder order, RoutingDetails routingDetails) {
        this.order = order;
        this.routingDetails = routingDetails;
    }


    @Override
    public StandardOrder getMessage() {
        return order;
    }

    @Override
    public RoutingDetails getRoutingDetails() {
        return routingDetails;
    }


}
