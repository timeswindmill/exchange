package order;

import client.Client;
import execution.ExecutionInstruction;
import execution.ExecutionType;
import instruments.Instrument;
import quickfix.SessionID;

import java.io.Serializable;
import java.util.Set;

public interface Order extends Serializable {

    public SessionID getSessionID();

    public OrderType getOrderType();

    public OrderStatus getOrderStatus();

    public Side getOrderSide();

    public long getOrderID();

    public String getClientOrderID();

    public Client getClient();

    public long getEntryTime();

    public ExecutionType getExecutionType();

    public Set<ExecutionInstruction> getExecutionInstructions();

    public double getPrice();

    public int getInitialQuantity();

    public int getOpenQuantity();

    public int getExecutedQuantity();

    public double getAvgExecutedPrice();

    public double getLastExecutedPrice();

    public int getLastExecutedQuantity();

    public long getLastUpdatedTime();

    public OrderOrigin getOrderOrigin();

    public Instrument getInstrument();

    public void addExecutionInstruction(ExecutionInstruction newInstruction);

    public void replaceExecutionInstructions(Set<ExecutionInstruction> newInstructions);

//    public void changeStatus(OrderStatus status);

    public void changeOrderSize(int amount);

    public void changePrice(double price);

    public void cancelOrder();

    public void executeOrder(int amount, double price);


}
