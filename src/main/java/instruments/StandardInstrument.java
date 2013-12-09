package instruments;

public class StandardInstrument implements Instrument {

    private final String symbol;
    private final String name;
    private final Instrument underlying;
    private final double referencePrice;
    private final long multiplier;
    private final double tickSize;

    public StandardInstrument(String symbol, String name, Instrument underlying,
                              double referencePrice, long multiplier, double tickSize) {
        this.symbol = symbol;
        this.name = name;
        this.tickSize = tickSize;
        this.underlying = (underlying == null) ? this : underlying;
        this.referencePrice = referencePrice;
        this.multiplier = multiplier;
    }

    @Override
    public String getSymbol(SymbolType type) {
        return symbol;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Instrument getUnderlying() {
        return underlying;
    }

    @Override
    public double getReferencePrice() {
        return referencePrice;
    }

    @Override
    public long getMulitiplier() {
        return multiplier;
    }

    @Override
    public double getTickSize() {
        return tickSize;
    }
}
