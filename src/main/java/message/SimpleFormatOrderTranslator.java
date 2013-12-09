package message;

import client.Client;
import client.StandardClient;
import execution.ExecutionType;
import instruments.Instrument;
import instruments.StandardInstrument;
import machine.Machine;
import order.*;

import java.util.Map;

public class SimpleFormatOrderTranslator {

    private static final String DELIIMITER = "\\|";
    private static final int EXCHANGEFIELDINDEX = 0;
    private static final int CLIENTORDERIDFIELDINDEX = 1;
    private static final int CLIENTFIELDINDEX = 2;
    private static final int INSTRUMENTFIELDINDEX = 3;
    private static final int SIDEFIELDINDEX = 4;
    private static final int PRICEFIELDINDEX = 5;
    private static final int QUANTITYFIELDINDEX = 6;

    private static final int VALIDNUMBERTOKENS = 8;


    // private final String binaryMessage = "Exchange|ClordID|ClientID|RIC|Side|Price|Quantity|Instructions";
    //TODO allow for auction  and instructions

    public static SimpleOrderMessage createOrderMessageFromString(String inputString) {

        String[] tokens = tokenizeInput(inputString);
        if (tokens.length != VALIDNUMBERTOKENS) {
            return null;
        }
        String exchange = tokens[EXCHANGEFIELDINDEX];
        StandardOrder newOrder = createSimpleOrder(tokens);
        // TODO allow inter machine routing
        RoutingDetails routingDetails = RoutingDetails.createRoutingDetails(null, exchange);
        SimpleOrderMessage newMessage = new SimpleOrderMessage(newOrder, routingDetails);
        return newMessage;

    }

    public static StandardOrder createSimpleOrder(String input) {
        String[] tokens = tokenizeInput(input);
        if (tokens.length != 8) {
            return null;
        }
        StandardOrder newOrder = createSimpleOrder(tokens);
        return newOrder;
    }


    private static StandardOrder createSimpleOrder(String[] tokens) {
        final Map<Long, Client> clientMap = Machine.INSTANCE.getClientMap();
        final Map<String, Instrument> instrumentMap = Machine.INSTANCE.getInstrumentMap();
        Client client = clientMap.get(Long.parseLong(tokens[CLIENTFIELDINDEX]));
        // find the instrument
        Instrument instrument = instrumentMap.get(tokens[INSTRUMENTFIELDINDEX]);
        StandardOrder newOrder = createStandardOrder(tokens[CLIENTORDERIDFIELDINDEX], client, instrument,
                Side.valueOf(tokens[SIDEFIELDINDEX]), Double.parseDouble(tokens[PRICEFIELDINDEX]), Integer.parseInt(tokens[QUANTITYFIELDINDEX]));
        return newOrder;

    }

    private static String[] tokenizeInput(String input) {
        String[] tokens = input.split(DELIIMITER);
        return tokens;
    }

    private static StandardOrder createStandardOrder(String clordID, Client client, Instrument instrument, Side side, Double price, Integer quantity) {
        OrderStub stub = new OrderStub(OrderOrigin.ALGO, instrument, side, clordID, client, ExecutionType.CONTINUOUS, quantity);
        StandardOrder newOrder = (price == 0) ? new StandardMarketOrder(stub) : new StandardLimitOrder(stub, price);
        return newOrder;
    }


    public static boolean validateOrder(String orderString) {
        return true;
    }


    public static StandardOrder createDummyOrder() {
        Instrument instrument = new StandardInstrument("BTX", "BTX Equity", null, 100, 1, 1);
        Client client = new StandardClient(1, "Test Client");

        String clientOrderID = "12345ABC";
        OrderStub stub = new OrderStub(OrderOrigin.ALGO, instrument, Side.SELL, clientOrderID, client, ExecutionType.CONTINUOUS, 102);
        return new StandardMarketOrder(stub);

    }

}
