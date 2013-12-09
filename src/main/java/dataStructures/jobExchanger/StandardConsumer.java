package dataStructures.jobExchanger;

public abstract class StandardConsumer implements Consumer {

    private final DataArray dataArray;
    private final int arraySize;
    private int threadID;
    private int currentPosition;
    private boolean stop = false;


    public StandardConsumer(DataArray dataArray) {
        this.dataArray = dataArray;
        arraySize = dataArray.getSize();

    }


//    @Override
//    public DataElement consumeElement(DataElement element) {
//        return null;
//    }

    @Override
    public void startConsumer() {
        Thread myThread = new Thread(this);
        threadID = (int) myThread.getId();
        myThread.start();

    }


    @Override
    public void run() {
        currentPosition = calculateStartingPosition();
        while (!stop) {
            if (currentPosition >= arraySize) {
                currentPosition = 0;
            }
            DataElement element = dataArray.consumeElement(currentPosition);
            if (element != null) {
                consumeElement(element);
            }
            currentPosition++;
        }

    }


    private int calculateStartingPosition() {

        return threadID % arraySize;
    }


}
