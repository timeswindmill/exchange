package order;

public enum OrderStatus {

    NEW((char) 48), PARTIALLY_FILLED((char) 49), FILLED((char) 50), DONE_FOR_DAY((char) 51), CANCELLED((char) 52),
    REPLACED((char) 53), PENDING_CANCEL((char) 54), STOPPED((char) 55), REJECTED((char) 56), SUSPENDED((char) 57),
    PENDING_NEW((char) 65), CALCULATED((char) 66), EXPIRED((char) 67), ACCEPTED_FOR_BIDDING((char) 68),
    PENDING_REPLACE((char) 69), CLOSED((char) 70);

    private char tag;

    OrderStatus(Character status) {
        tag = status;
    }


    public char getTag() {
        return tag;
    }


}
