package dataStructures.jobExchanger;

public class StandardProcessor implements Processor {

    private final DataArray dataArray;
    private final int arraySize;
    private int threadID;
    private int currentPosition;


    public StandardProcessor(DataArray dataArray) {
        this.dataArray = dataArray;
        this.arraySize = dataArray.getSize();
        threadID = (int) Thread.currentThread().getId();
        currentPosition = calculateStartingPosition();
    }


    @Override
    public void putDataElement(DataElement element) {
        while (true) {
            if (dataArray.addElement(element, currentPosition)) {
                break;
            }
            currentPosition++;
            if (currentPosition >= arraySize) {
                currentPosition = 0;
            }
        }

    }

    private int calculateStartingPosition() {

        return threadID % arraySize;
    }

}
