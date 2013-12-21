package machine.IO.in;

import dataStructures.jobExchanger.DataArray;
import dataStructures.jobExchanger.DataElement;
import dataStructures.jobExchanger.StandardConsumer;
import exchange.Exchange;
import message.Address;
import message.GeneralMessage;
import message.RoutingDetails;
import message.translators.SimpleFormatOrderTranslator;
import order.StandardOrder;

import java.util.List;


public class MachineInputConsumer extends StandardConsumer {
    private final List<Exchange> exchangeList;

    public MachineInputConsumer(final DataArray dataArray, final List<Exchange> exchangeList) {
        super(dataArray);
        this.exchangeList = exchangeList;
    }


    private void routeInputToExchange(String message) {
        //TODO allow sending to other machines
        // convert input to message to find routing
        GeneralMessage<StandardOrder> inputMessage = SimpleFormatOrderTranslator.createOrderMessageFromString(message);
        RoutingDetails routingDetails = inputMessage.getRoutingDetails();
        Address address = routingDetails.getDestinationAddress();
        String exchangeName = address.getExchangeName();
        StandardOrder newOrder = inputMessage.getMessage();
        for (Exchange exchange : exchangeList) {
            if (exchange.getExchangeName().equals(exchangeName)) {
                exchange.getRouter().addOrder(newOrder);
                break;
            }
        }


    }

    @Override
    public void consumeElement(DataElement element) {
        routeInputToExchange(element.getPayload().toString());
    }
}
