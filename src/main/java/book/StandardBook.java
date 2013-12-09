package book;

import book.match.Matcher;
import book.match.StandardMatcher;
import book.out.PriceReporter;
import book.out.ReportQueue;
import instruments.Instrument;
import instruments.SymbolType;
import order.MatchableOrderComparator;
import order.Side;
import order.StandardMatchableOrder;

import java.util.Comparator;


public class StandardBook {


    private final Instrument instrument;
    private final Matcher matcher;
    private final ReportQueue reportQueue;
    private final StandardBookSide buySide;
    private final StandardBookSide sellSide;
    private final PriceReporter priceReporter;
    private final Comparator comparator = new MatchableOrderComparator();

    public StandardBook(Instrument instrument, PriceReporter priceReporter) {
        this.instrument = instrument;
        this.priceReporter = priceReporter;

        reportQueue = ReportQueue.createReportQueue(instrument);

        buySide = StandardBookSide.createStandardBookSide(this, Side.BUY);
        sellSide = StandardBookSide.createStandardBookSide(this, Side.SELL);

        matcher = new StandardMatcher(buySide, sellSide, 1, reportQueue);
    }


    public void startReportQueue(boolean startJournal) {
        //TODO ? report status
        reportQueue.startReportQueue(startJournal);
    }


    public void receiveOrder(StandardMatchableOrder newOrder) {
        matcher.matchItem(newOrder);
    }

    public void reportBestPriceChange(Side side, double newPrice) {

        priceReporter.reportBestPriceChange(newPrice, side);


    }

    protected Comparator getComparator() {
        return comparator;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public String getBookName() {
        return instrument.getSymbol(SymbolType.RIC);
    }

    public Matcher getMatcher() {
        return matcher;
    }

}
