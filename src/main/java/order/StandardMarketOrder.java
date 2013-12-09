package order;

public class StandardMarketOrder extends StandardOrder {
    public StandardMarketOrder(OrderStub orderStub) {
        // apply some defaults
        super(orderStub);
    }


    @Override
    public OrderType getOrderType() {
        return OrderType.MARKET;
    }


    @Override
    public void changePrice(double price) {
        // can't
    }


}
