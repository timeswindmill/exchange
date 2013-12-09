package util;

import util.concurrency.AtomicSequence;

import java.util.Date;

public enum IDGenerator {


    INSTANCE;

    AtomicSequence sequence = new AtomicSequence(new Date().getTime());

    public long getNextID() {
        return sequence.getAndIncrement();
    }


}
