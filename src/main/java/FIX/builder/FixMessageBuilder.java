package FIX.builder;


import execution.ExecutionReport;
import instruments.SymbolType;
import order.Order;
import order.OrderStatus;
import order.OrderType;
import order.Side;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;


public class FixMessageBuilder {
    private AtomicInteger seqNum = new AtomicInteger(12);
    private AtomicInteger clorId = new AtomicInteger(129672);
    private AtomicInteger ordId = new AtomicInteger(81729);
    private AtomicInteger execId = new AtomicInteger(97218);
    private String target = "ADMIN";
    public final static String SOH = "|";

    private String fixBase1 = "8=FIX.4.2\u00019=136\u000135=D\u000134=4\u000149=BANZAI" +
            "\u000152=20110203-08:20:16.783\u000156=ADMIN\u000111=1296721216785\u000121=1\u000138=100" +
            "\u000140=2\u000144=10\u000154=2\u000155=VOD\u000159=0\u000160=20110203-08:20:16.783" +
            "\u000110=122\u0001";


    private String fixBase2 = "8=FIX.4.2\u00019=136\u000135=D\u000134=4\u000149=BANZAI" +
            "\u000152=20110203-08:20:16.783\u000156=ADMIN\u000111=1296721216785\u000121=1\u000138=100" +
            "\u000140=2\u000144=10\u000154=2\u000155=VOD\u000159=0\u000160=20110203-08:20:16.783" +
            "\u000110=122\u0001";


    private String fixBase3 = "8=FIX.4.2\u00019=136\u000135=D\u000134=4\u000149=BANZAI" +
            "\u000152=20110203-08:20:16.783\u000156=ADMIN\u000111=1296721216785\u000121=1\u000138=100" +
            "\u000140=2\u000144=10\u000154=2\u000155=VOD\u000159=0\u000160=20110203-08:20:16.783" +
            "\u000110=122\u0001";


    public FixMessageBuilder() {

    }

    private String getSeqNum() {
        int nextNum = seqNum.getAndIncrement();
        return new Integer(nextNum).toString();
    }

    private String getClordId() {
        int nextNum = clorId.getAndIncrement();
        return new Integer(nextNum).toString();
    }

    private String getExecId() {
        int nextNum = execId.getAndIncrement();
        return new Integer(nextNum).toString();
    }

    private String getOrdId() {
        int nextNum = ordId.getAndIncrement();
        return new Integer(nextNum).toString();
    }

    private String getOrderType(OrderType orderType) {
        String type = "";
        switch (orderType) {

            case LIMIT:
                type = "2";
                break;
            case MARKET:
                type = "1";
                break;
            case STOP:
                type = "3";
                break;

        }

        return type;
    }

    private String getSideString(Side side) {
        String sideString = "";
        switch (side) {

            case BUY:
                sideString = "1";
                break;

            case SELL:
                sideString = "2";
                break;

        }
        return sideString;
    }


    public String createNewOrderMessage(Order order) {

        String msg = createNewOrderMessage(order.getClient().getClientName(), order.getInstrument().getSymbol(SymbolType.RIC),
                order.getOrderSide(), order.getOrderType(), order.getInitialQuantity(), order.getPrice());
        return msg;

    }

    public String createNewOrderMessage(String client, String symbol, Side side, OrderType orderType, double quantity, double price) {
        Date time = new Date();
        String timeString = String.format("%1$tY%1$tm%1$td-%1$tH:%1$tM:%1$tS.%1$tL", time);
        String orderTypeString = getOrderType(orderType);


        StringBuilder sb = new StringBuilder();
        sb.append("8=FIX.4.2");
        //+ SOH + "19=136 "+ SOH +"35=D" + SOH + "34=" + getSeqNum() + "\u000149=CLIENT1");
        sb.append(SOH);
        sb.append("9=136");
        sb.append(SOH);
        sb.append("35=D");
        sb.append(SOH);
        sb.append("34=" + getSeqNum());
        sb.append(SOH);
        sb.append("49=" + client);
        sb.append(SOH);
        sb.append("52=" + timeString);
        sb.append(SOH);
        sb.append("56=" + target);
        sb.append(SOH);
        sb.append("11=" + getClordId());
        sb.append(SOH);
        sb.append("21=1");
        sb.append(SOH);
        sb.append("38=" + quantity);
        sb.append(SOH);
        sb.append("40=" + orderTypeString);
        sb.append(SOH);
        sb.append("44=" + price);
        sb.append(SOH);
        sb.append("54=" + getSideString(side));
        sb.append(SOH);
        sb.append("55=" + symbol);
        sb.append(SOH);
        sb.append("59=0");
        sb.append(SOH);

        // Thread.currentThread().wait(13);

        String time2String = String.format("%1$tY%1$tm%1$td-%1$tH:%1$tM:%1$tS.%1$tL", new Date());

        sb.append("60=" + time2String);
        sb.append(SOH);
        int length = sb.length() + 6;
        sb.append("10=" + length);
        sb.append(SOH);
        return sb.toString();

    }

    public String createExecReportMessage(String client, String symbol, Side side, OrderType orderType,
                                          double avgPrice, double cumQuantity, OrderStatus orderStatus) {
        Date time = new Date();
        String timeString = String.format("%1$tY%1$tm%1$td-%1$tH:%1$tM:%1$tS.%1$tL", time);


        StringBuilder sb = new StringBuilder();
        sb.append("8=FIX.4.2");
        sb.append(SOH);
        sb.append("9=129");
        sb.append(SOH);
        sb.append("35=8");
        sb.append(SOH);
        sb.append("34=" + getSeqNum());
        sb.append(SOH);
        sb.append("49=" + target);
        sb.append(SOH);
        sb.append("52=" + timeString);
        sb.append(SOH);
        sb.append("56=" + client);
        sb.append(SOH);
        sb.append("6=" + avgPrice);
        sb.append(SOH);
        sb.append("14=" + cumQuantity);
        sb.append(SOH);
        sb.append("11=" + getClordId());
        sb.append(SOH);
        sb.append("11=" + getExecId());
        sb.append(SOH);
        sb.append("20=0");
        sb.append(SOH);
        sb.append("37=" + getOrdId());
        sb.append(SOH);
        sb.append("37=" + getOrdId());
        sb.append(SOH);
        sb.append("39=0");
        sb.append(SOH);
        sb.append("54=" + getSideString(side));
        sb.append(SOH);
        sb.append("55=" + symbol);
        sb.append(SOH);
        sb.append("150=0");
        sb.append(SOH);
        sb.append("151=0");
        int length = sb.length() + 6;
        sb.append("10=" + length);
        sb.append(SOH);
        return sb.toString();

    }


    public String createExecReportMessage(ExecutionReport executionReport) {
        Date time = new Date();
        String timeString = String.format("%1$tY%1$tm%1$td-%1$tH:%1$tM:%1$tS.%1$tL", time);
        Order order = executionReport.getOrder();


        StringBuilder sb = new StringBuilder();
        sb.append("8=FIX.4.2");
        sb.append(SOH);
        sb.append("9=129");
        sb.append(SOH);
        sb.append("35=8");
        sb.append(SOH);
        sb.append("34=" + getSeqNum());
        sb.append(SOH);
        sb.append("49=" + target);
        sb.append(SOH);
        sb.append("52=" + timeString);
        sb.append(SOH);
        sb.append("56=" + order.getClient().getClientName());
        sb.append(SOH);
        sb.append("6=" + executionReport.getAvgPx());
        sb.append(SOH);
        sb.append("14=" + executionReport.getCumQty());
        sb.append(SOH);
        sb.append("11=" + order.getClientOrderID());
        sb.append(SOH);
        sb.append("11=" + executionReport.getExecReportID());
        sb.append(SOH);
        sb.append("20=0");
        sb.append(SOH);
        sb.append("37=" + order.getOrderID());
        sb.append(SOH);
        sb.append("39=0");
        sb.append(SOH);
        sb.append("54=" + getSideString(order.getOrderSide()));
        sb.append(SOH);
        sb.append("55=" + order.getInstrument().getSymbol(SymbolType.RIC));
        sb.append(SOH);
        sb.append("150=0");
        sb.append(SOH);
        sb.append("151=0");
        int length = sb.length() + 6;
        sb.append("10=" + length);
        sb.append(SOH);
        return sb.toString();

    }


}
