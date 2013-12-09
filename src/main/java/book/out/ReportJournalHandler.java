package book.out;

import com.higherfrequencytrading.chronicle.Excerpt;
import com.higherfrequencytrading.chronicle.impl.IndexedChronicle;
import com.lmax.disruptor.EventHandler;
import execution.ExecutionReport;

import java.io.IOException;

public class ReportJournalHandler implements EventHandler<ExecutionReport> {


    private final String chroniclePath = ("/home/laurence/Work/Dev/Java/MatchingProjects/Exchange/data/journal");
    //TODO get from properties file
    private IndexedChronicle indexedChronicle;
    private Excerpt excerpt;

    public ReportJournalHandler() {

        try {
            indexedChronicle = new IndexedChronicle(chroniclePath);
            excerpt = indexedChronicle.createExcerpt();


        } catch (IOException e) {
            System.out.println(e);

        }

    }


    @Override
    public void onEvent(ExecutionReport event, long sequence, boolean endOfBatch) throws Exception {
        //     System.out.println("Report Handler "+ event);
        excerpt.startExcerpt(50);
        excerpt.writeLong(System.nanoTime());
        // TODO write small object here
        excerpt.writeUTF(event.toString());
        excerpt.finish();

        indexedChronicle.close();

    }
}
