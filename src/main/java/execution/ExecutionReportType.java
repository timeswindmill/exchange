package execution;

public enum ExecutionReportType {


    NEW((char) 48), PARTIAL_FILL((char) 49), FILL((char) 50), DONE_FOR_DAY((char) 51), CANCELLED((char) 52), REPLACED((char) 53),
    PENDING_CANCEL((char) 54), STOPPED((char) 55), REJECTED((char) 56), SUSPENDED((char) 57), PENDING_NEW((char) 65),
    CALCULATED((char) 66), EXPIRED((char) 67), RESTATED((char) 68), PENDING_REPLACE((char) 69),
    TRADE((char) 79), TRADE_CORRECT((char) 71), TRADE_CANCEL((char) 72), ORDER_STATUS((char) 73), TRADE_IN_A_CLEARING_HOLD((char) 74),
    TRADE_HAS_BEEN_RELEASED_TO_CLEARING((char) 75), TRIGGERED_OR_ACTIVATED_BY_SYSTEM((char) 76);

    private char tag;

    ExecutionReportType(Character execType) {
        tag = execType;
    }


    public char getTag() {
        return tag;
    }

}
