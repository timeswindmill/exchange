package exchange;

import book.StandardBook;
import book.out.PriceReporter;
import exchange.in.ExchangeInput;
import exchange.in.SimpleBinaryListener;
import exchange.router.RouterConfig;
import exchange.router.StandardRouter;
import instruments.Instrument;
import instruments.SymbolType;
import machine.Machine;
import message.SimpleFormatOrderTranslator;
import order.StandardOrder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class StandardExchange implements Exchange {
    private final StandardRouter router;
    private final Map<String, StandardBook> books;
    private final ExchangeConfig config;
    private final String exchangeName;
    private ExchangeInput exchangeInput;


//    private StandardExchange(StandardRouter router, Map<String, StandardBook> books, ExchangeConfig config,
//                             String exchangeName) {
//        this.router = router;
//        this.books = books;
//        this.config = config;
//        this.exchangeName = exchangeName;
//        this.exchangeInput = ExchangeInput.createExchangeInput(config.getInputFileName());
//    }

    private StandardExchange(StandardRouter router, Map<String, StandardBook> books, ExchangeConfig config,
                             String exchangeName, ExchangeInput exchangeInput) {
        this.router = router;
        this.books = books;
        this.config = config;
        this.exchangeName = exchangeName;
        this.exchangeInput = exchangeInput;
    }

//    private StandardExchange(ExchangeConfig config) {
//
//        //TODO get exchange.router config from file
//        RouterConfig routerConfig = new RouterConfig();
//        Map<String, StandardBook> books = new HashMap<>();
//
//        Map<String,Instrument> instrumentMap = Machine.INSTANCE.getInstrumentMap();
//        String reportFileDir = config.getPriceReportDirectory();
//        for (String bookName : config.getBooks()) {
//            Instrument instrument = instrumentMap.get(bookName);
//            PriceReporter priceReporter = PriceReporter.createPriceReporter(reportFileDir+instrument.getSymbol(SymbolType.RIC));
//            StandardBook newBook = new StandardBook(instrument,priceReporter);
//            books.put(newBook.getBookName(), newBook);
//        }
//
//        StandardRouter router = StandardRouter.createRouter(routerConfig, books);
////        this.config = config;
////        this.exchangeName = config.getExchangeName();
//        exchangeInput = ExchangeInput.createExchangeInput(config.getInputFileName(), new ExchangeInputListener());
//        this(router,books,config,config.getExchangeName(),exchangeInput);
//    }

    public static StandardExchange createExchange(ExchangeConfig config) {

        RouterConfig routerConfig = new RouterConfig();
        Map<String, StandardBook> books = new HashMap<>();
        Map<String, Instrument> instrumentMap = Machine.INSTANCE.getInstrumentMap();
        String reportFileDir = config.getPriceReportDirectory();
        for (String bookName : config.getBooks()) {
            Instrument instrument = instrumentMap.get(bookName);
            PriceReporter priceReporter = PriceReporter.createPriceReporter(reportFileDir + instrument.getSymbol(SymbolType.RIC));
            StandardBook newBook = new StandardBook(instrument, priceReporter);
            books.put(newBook.getBookName(), newBook);
        }

        StandardRouter router = StandardRouter.createRouter(routerConfig, books);
        ExchangeInput exchangeInput = ExchangeInput.createExchangeInput(config.getInputFileName());
        StandardExchange exchange = new StandardExchange(router, books, config, config.getExchangeName(), exchangeInput);
        ExchangeInputListener exchangeInputListener = exchange.new ExchangeInputListener();
        exchangeInput.addListener(exchangeInputListener);
        return exchange;

    }

    public static StandardExchange createMockExchange(ExchangeConfig config) {

        RouterConfig routerConfig = new RouterConfig();
        Map<String, StandardBook> books = new HashMap<>();
        Map<String, Instrument> instrumentMap = Machine.INSTANCE.getInstrumentMap();
        for (String bookName : config.getBooks()) {
            Instrument instrument = instrumentMap.get(bookName);
            PriceReporter priceReporter = PriceReporter.createMockPriceReporter();
            StandardBook newBook = new StandardBook(instrument, priceReporter);
            books.put(newBook.getBookName(), newBook);
        }

        StandardRouter router = StandardRouter.createRouter(routerConfig, books);
        ExchangeInput exchangeInput = ExchangeInput.createMockExchangeInput();

        StandardExchange exchange = new StandardExchange(router, books, config, config.getExchangeName(), exchangeInput);

        ExchangeInputListener exchangeInputListener = exchange.new ExchangeInputListener();
        exchange.exchangeInput.addListener(exchangeInputListener);


        return exchange;

    }


    @Override
    public String getExchangeName() {
        return exchangeName;
    }


    @Override
    public void startRouterAndQueues() {

        for (StandardBook book : getBooks()) {
            book.startReportQueue(config.getUseJournal());
        }

        router.startRouter();

        // now start input listener
        exchangeInput.startChronicle();

    }

    @Override
    public void startAgents() {

    }

    @Override
    public void stopAgents() {

    }

    @Override
    public StandardRouter getRouter() {
        return null;
    }

    @Override
    public Collection<StandardBook> getBooks() {
        return books.values();
    }

    @Override
    public Map<String, StandardBook> getBookMap() {
        return books;
    }

    @Override
    public ExchangeInput getExchangeInput() {
        return exchangeInput;
    }

    @Override
    public ExchangeConfig getExchangeConfig() {
        return config;
    }


    private void addOrder(StandardOrder order) {
        // add order to exchange
        // root to appropriate book
        router.addOrder(order);

    }

    private class ExchangeInputListener extends SimpleBinaryListener {
        @Override
        public void publishEvent(String message) {
            StandardOrder order = SimpleFormatOrderTranslator.createSimpleOrder(message);
            if (order != null) {
                addOrder(order);
            }

        }

    }
}
