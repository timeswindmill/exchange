package order;

import client.Client;
import execution.ExecutionInstruction;
import execution.ExecutionType;
import instruments.Instrument;
import util.IDGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

public class OrderStub implements Serializable {

    private final OrderOrigin orderOrigin;

    private final Instrument instrument;
    private final Side side;
    private final long orderID;
    private final String clientOrderID;
    private final int initialQuantity;
    private final Client client;
    private final long entryTime = new Date().getTime();
    // TODO could just have name ?
    //private final Exchange exchange;
    private final ExecutionType executionType;
    private final Set<ExecutionInstruction> executionInstructions = EnumSet.noneOf(ExecutionInstruction.class);


    public OrderStub(OrderOrigin orderOrigin, Instrument instrument, Side side,
                     String clientOrderID, Client client, ExecutionType executionType, int quantity) {
        this.orderOrigin = orderOrigin;
        this.instrument = instrument;
        this.side = side;
        this.orderID = getNextOrderID();
        this.clientOrderID = clientOrderID;
        this.client = client;
        this.executionType = executionType;
        this.initialQuantity = quantity;
    }


    private static long getNextOrderID() {
        return IDGenerator.INSTANCE.getNextID();
    }

    public OrderOrigin getOrderOrigin() {
        return orderOrigin;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public Side getSide() {
        return side;
    }

    public long getOrderID() {
        return orderID;
    }

    public String getClientOrderID() {
        return clientOrderID;
    }

    public int getInitialQuantity() {
        return initialQuantity;
    }

    public Client getClient() {
        return client;
    }

    public long getEntryTime() {
        return entryTime;
    }

//    public Exchange getExchange() {
//        return exchange;
//    }

    public ExecutionType getExecutionType() {
        return executionType;
    }

    public Set<ExecutionInstruction> getExecutionInstructions() {
        return executionInstructions;
    }


}
