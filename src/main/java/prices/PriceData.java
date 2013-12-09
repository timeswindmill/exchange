package prices;

import order.Side;

public class PriceData {

    private final double price;
    private final int quantity;
    private final Side side;


    public PriceData(double price, int quantity, Side side) {
        this.price = price;
        this.quantity = quantity;
        this.side = side;
    }

    public Side getSide() {
        return side;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }


}
