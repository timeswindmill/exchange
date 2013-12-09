package book.out;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import execution.ExecutionReport;
import instruments.Instrument;
import order.Order;
import util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReportQueue {

    private final Instrument instrument;
    private final ExecutorService exec;
    private final Disruptor<ExecutionReport> disruptor;
    private RingBuffer<ExecutionReport> ringBuffer;

    private ReportQueue(Instrument instrument) {
        this.instrument = instrument;
        exec = Executors.newCachedThreadPool();
        disruptor = new Disruptor<ExecutionReport>(ExecutionReport.EVENT_FACTORY, 1024, exec);

    }

    private ReportQueue(Instrument instrument, ExecutorService exec, Disruptor<ExecutionReport> disruptor) {
        this.instrument = instrument;
        this.exec = exec;
        this.disruptor = disruptor;

    }

    public void startReportQueue(boolean startJournal) {
        // Preallocate RingBuffer with 1024 ValueEvents
        final ReportDBHandler handler = ReportDBHandler.createReportDBHandler(instrument);
        final ReportJournalHandler reportJournalHandler = new ReportJournalHandler();

        //disruptor.handleEventsWith(handler,reportJournalHandler);
        //TODO re instate journal
        if (startJournal) {
            disruptor.handleEventsWith(handler).then(reportJournalHandler);
        } else {
            disruptor.handleEventsWith(handler);
        }

        ringBuffer = disruptor.start();


    }


    public void addExecution(Order order, int fillAmount, double price, long executionTime) {

        long seq = ringBuffer.next();
        System.out.println("Sequence is " + seq);
        ExecutionReport nextReport = ringBuffer.get(seq);
        nextReport.resetExecutionReport(order, fillAmount, price, executionTime);
        ringBuffer.publish(seq);

    }


    public static ReportQueue createReportQueue(Instrument instrument) {
        ReportQueue reportQueue = new ReportQueue(instrument);
        return reportQueue;
    }

    public static ReportQueue createMockReportQueue(final boolean printOutput) {
        ReportQueue reportQueue = new ReportQueue(null, null, null) {
            public void addExecution(Order order, int fillAmount, double price, long executionTime) {
                if (printOutput) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(order.toString());
                    sb.append("fill amount : " + fillAmount + "\n");
                    sb.append("price : " + price + "\n");
                    sb.append("execution time :" + executionTime + "\n");

                    Log.INSTANCE.logInfo(sb.toString());
                }
            }

            public void startReportQueue(boolean startJournal) {
                // dont
            }
        };
        return reportQueue;
    }


}
