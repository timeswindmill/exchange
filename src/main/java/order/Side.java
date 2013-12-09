package order;

public enum Side {

    BUY((char) 49), SELL((char) 50), BUY_MINUS((char) 51), SELL_PLUS((char) 52),
    SELL_SHORT((char) 53), SELL_SHORT_EXEMPT((char) 54), UNDISCLOSED((char) 55),
    CROSS((char) 56), CROSS_SHORT((char) 57), CROSS_SHORT_EXEMPT((char) 65),
    AS_DEFINED((char) 66), OPPOSITE((char) 67), SUBSCRIBE((char) 68),
    REDEEM((char) 69), LEND((char) 70), BORROW((char) 71);


    private char tag;

    Side(Character side) {
        tag = side;
    }


    public char getTag() {
        return tag;
    }


}
