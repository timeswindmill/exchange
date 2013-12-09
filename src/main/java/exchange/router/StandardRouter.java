package exchange.router;

import book.StandardBook;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import instruments.SymbolType;
import order.StandardMatchableOrder;
import order.StandardOrder;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StandardRouter {

    private final ExecutorService exec;
    private final Disruptor<StandardMatchableOrder> disruptor;
    private RouterStatus status = RouterStatus.STOPPED;
    private RingBuffer<StandardMatchableOrder> ringBuffer;


    private final Map<String, StandardBook> books;

    private final Distributor distributor;

    private StandardRouter(RouterConfig config, Map<String, StandardBook> books) {
        this.books = books;
        distributor = new Distributor();
        exec = Executors.newCachedThreadPool();
        disruptor = new Disruptor<StandardMatchableOrder>(StandardMatchableOrder.EVENT_FACTORY, 1024, exec);
        disruptor.handleEventsWith(distributor);
        // TODO add journaling ?

    }


    public void startRouter() {
        ringBuffer = disruptor.start();
        status = RouterStatus.RUNNING;
    }

    public RouterStatus getStatus() {
        return status;
    }

    public static StandardRouter createRouter(RouterConfig config, Map<String, StandardBook> books) {

        StandardRouter router = new StandardRouter(config, books);
        // TODO any specific config stuff here
        return router;
    }


    public void addOrder(StandardOrder order) {
        if (status == RouterStatus.RUNNING) {
            long seq = ringBuffer.next();
            StandardMatchableOrder nextOrder = ringBuffer.get(seq);
            nextOrder.setOrder(order);
            ringBuffer.publish(seq);
        } else {
            // TODO error ?
        }

    }


    private class Distributor implements EventHandler<StandardMatchableOrder> {


        @Override
        public void onEvent(StandardMatchableOrder matchableOrder, final long sequence, final boolean endOfBatch) throws Exception {

            String bookName = matchableOrder.getOrder().getInstrument().getSymbol(SymbolType.RIC);
            StandardBook book = books.get(bookName);
            if (book == null) {
                throw new Exception("Book missing for instrument : " + bookName);
            }
            book.receiveOrder(matchableOrder);
        }
    }
}
