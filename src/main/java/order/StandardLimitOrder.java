package order;

public class StandardLimitOrder extends StandardOrder {
    public StandardLimitOrder(OrderStub stub, double price) {
        super(stub);
        changePrice(price);
    }

    @Override
    public OrderType getOrderType() {
        return OrderType.LIMIT;
    }

    @Override
    public void changePrice(double price) {
        super.changePrice(price);
    }

}
