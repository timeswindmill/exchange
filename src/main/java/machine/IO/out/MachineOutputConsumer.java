package machine.IO.out;

import dataStructures.jobExchanger.DataArray;
import dataStructures.jobExchanger.DataElement;
import dataStructures.jobExchanger.StandardConsumer;

public class MachineOutputConsumer extends StandardConsumer {
    public MachineOutputConsumer(DataArray dataArray) {
        super(dataArray);
    }

    @Override
    public void consumeElement(DataElement element) {

    }
}
