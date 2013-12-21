package order;

import client.Client;
import execution.ExecutionInstruction;
import execution.ExecutionType;
import instruments.Instrument;
import quickfix.SessionID;

import java.util.Date;
import java.util.Set;

public abstract class StandardOrder implements Order {

    private final OrderStub orderStub;

    private OrderStatus orderStatus;
    private double price;
    private int openQuantity;
    private int executedQuantity;
    private double avgExecutedPrice;
    private double lastExecutedPrice;
    private int lastExecutedQuantity;
    private long lastUpdatedTime;


    public StandardOrder(OrderStub orderStub) {

        this.orderStub = orderStub;
        this.orderStatus = OrderStatus.NEW;
        this.openQuantity = orderStub.getInitialQuantity();
        executedQuantity = 0;
        avgExecutedPrice = 0;
        lastExecutedPrice = 0;
        lastExecutedQuantity = 0;
        lastUpdatedTime = new Date().getTime();
    }

    @Override
    public SessionID getSessionID() {
        return orderStub.getSessionID();
    }

    @Override
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    @Override
    public Side getOrderSide() {
        return orderStub.getSide();
    }

    @Override
    public long getOrderID() {
        return orderStub.getOrderID();
    }

    @Override
    public String getClientOrderID() {
        return orderStub.getClientOrderID();
    }

    @Override
    public Client getClient() {
        return orderStub.getClient();
    }

    @Override
    public long getEntryTime() {
        return orderStub.getEntryTime();
    }


    @Override
    public ExecutionType getExecutionType() {
        return orderStub.getExecutionType();
    }

    @Override
    public Set<ExecutionInstruction> getExecutionInstructions() {
        return orderStub.getExecutionInstructions();
    }

    @Override
    public int getInitialQuantity() {
        return orderStub.getInitialQuantity();
    }


    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public int getOpenQuantity() {
        return openQuantity;
    }

    @Override
    public int getExecutedQuantity() {
        return executedQuantity;
    }

    @Override
    public double getAvgExecutedPrice() {
        return avgExecutedPrice;
    }

    @Override
    public double getLastExecutedPrice() {
        return lastExecutedPrice;
    }

    @Override
    public int getLastExecutedQuantity() {
        return lastExecutedQuantity;
    }

    @Override
    public long getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    @Override
    public OrderOrigin getOrderOrigin() {
        return orderStub.getOrderOrigin();
    }

    @Override
    public Instrument getInstrument() {
        return orderStub.getInstrument();
    }

    @Override
    public void addExecutionInstruction(ExecutionInstruction newInstruction) {
        if (validateExecutionInstructions(newInstruction)) {
            orderStub.getExecutionInstructions().add(newInstruction);
        }
    }

    @Override
    public void replaceExecutionInstructions(Set<ExecutionInstruction> newInstructions) {
        orderStub.getExecutionInstructions().clear();
        orderStub.getExecutionInstructions().addAll(newInstructions);
    }


    private boolean validateExecutionInstructions(ExecutionInstruction newInstruction) {
        // TODO need validation and test
        return true;
    }


    @Override
    public void changeOrderSize(int amount) {
        // TODO change corresponding open amount
        //    ???
    }

    @Override
    public void changePrice(double price) {
        this.price = price;
    }

    @Override
    public void cancelOrder() {
        orderStatus = OrderStatus.CANCELLED;
        lastUpdatedTime = new Date().getTime();

    }

    @Override
    public void executeOrder(int amount, double price) {
        openQuantity = openQuantity - amount;
        assert (openQuantity >= 0);
        lastExecutedPrice = price;
        lastExecutedQuantity = amount;
        lastUpdatedTime = System.currentTimeMillis();
        double newExecutedPrice = avgExecutedPrice * executedQuantity + (amount * price) / amount;
        avgExecutedPrice = newExecutedPrice;
        executedQuantity += amount;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order  :" + getOrderID() + "\n");
        sb.append("Open " + getOpenQuantity() + "\n");
        sb.append("Side " + getOrderSide() + "\n");
        sb.append("Status " + getOrderStatus() + "\n");

        return sb.toString();

    }

}
