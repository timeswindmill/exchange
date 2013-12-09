package machine.IO.in;

import dataStructures.jobExchanger.DataArray;
import dataStructures.jobExchanger.DataElement;
import dataStructures.jobExchanger.Processor;
import dataStructures.jobExchanger.StandardProcessor;
import exchange.Exchange;
import message.Message;

import java.util.List;

public class MachineInput {

    private final static int NUMBER_CONSUMERS = 5;
    //TODO have machine config in a file
    private final int inputArraySize = 100;
    private final DataArray dataArray = new DataArray(inputArraySize);
    private final Processor processor = new StandardProcessor(dataArray);
    private final MachineInputConsumer[] machineInputConsumers = new MachineInputConsumer[NUMBER_CONSUMERS];


    public MachineInput(List<Exchange> exchangeList) {
        for (int ii = 0; ii < machineInputConsumers.length; ii++) {
            machineInputConsumers[ii] = new MachineInputConsumer(dataArray, exchangeList);
        }
    }

    public void startConsumers() {
        for (MachineInputConsumer machineInputConsumer : machineInputConsumers) {
            machineInputConsumer.startConsumer();
        }
    }


    public void acceptMessage(Message message) {
        processor.putDataElement(new DataElement<Message>(message));
    }

}
