package machine.IO.out;

import dataStructures.jobExchanger.DataArray;
import dataStructures.jobExchanger.Processor;
import dataStructures.jobExchanger.StandardProcessor;
import message.Message;

public class MachineOutput {

    private static final int outputArraySize = 100;
    private static final DataArray dataArray = new DataArray(outputArraySize);


    public static void acceptOutput(Message inMessage) {
        final Processor processor = new StandardProcessor(dataArray);

    }


    public void startMessageAccepter() {

    }


}
