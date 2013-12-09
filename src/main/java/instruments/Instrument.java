package instruments;

import java.io.Serializable;

public interface Instrument extends Serializable {

    public String getSymbol(SymbolType type);

    public String getName();

    public Instrument getUnderlying();

    public double getReferencePrice();

    public long getMulitiplier();

    public double getTickSize();

}
