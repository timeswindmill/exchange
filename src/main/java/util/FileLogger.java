package util;

import com.higherfrequencytrading.chronicle.Excerpt;
import com.higherfrequencytrading.chronicle.impl.IndexedChronicle;

import java.io.IOException;

public enum FileLogger {


    INSTANCE;


    private final String chroniclePath = ("/home/laurence/temp/log.dat");
    //TODO get from properties file
    private IndexedChronicle indexedChronicle;
    private Excerpt excerpt;

    FileLogger() {

        try {
            indexedChronicle = new IndexedChronicle(chroniclePath);
            excerpt = indexedChronicle.createExcerpt();


        } catch (IOException e) {
            System.out.println(e);

        }

    }


    public void writeToLog(String message) throws Exception {
        excerpt.startExcerpt(50);
        //       excerpt.writeLong(System.nanoTime());

        excerpt.writeUTF(message);
        excerpt.finish();

        indexedChronicle.close();

    }


}
