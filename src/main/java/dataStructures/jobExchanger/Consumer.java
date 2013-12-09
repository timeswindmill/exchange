package dataStructures.jobExchanger;

public interface Consumer extends Runnable {

    public void consumeElement(DataElement element);

    public void startConsumer();

}
