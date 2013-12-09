package order;

import com.lmax.disruptor.EventFactory;

public class StandardMatchableOrder implements MatchableOrder<StandardOrder> {

    private StandardOrder order;

    public StandardMatchableOrder(StandardOrder order) {
        this.order = order;
    }

    @Override
    public StandardOrder getOrder() {
        return order;
    }


    // for use with disruptor pattern
    public final static EventFactory<StandardMatchableOrder> EVENT_FACTORY = new EventFactory<StandardMatchableOrder>() {
        public StandardMatchableOrder newInstance() {
            return new StandardMatchableOrder(null);
        }
    };

    public void setOrder(StandardOrder order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof StandardMatchableOrder != true) {
            return false;
        }
        StandardMatchableOrder otherOrder = (StandardMatchableOrder) other;
        if (otherOrder.getOrder().getClientOrderID().equals(order.getClientOrderID())) {
            return true;

        }
        return false;

    }

    public String toString() {
        return order.toString();

    }
}
