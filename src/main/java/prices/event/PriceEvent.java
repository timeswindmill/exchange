package prices.event;

import prices.PriceData;

import java.io.Serializable;

public class PriceEvent implements Serializable {

    private final PriceEventType priceEventType;
    private final PriceData[] priceData;


    public PriceEvent(PriceEventType priceEventType, PriceData[] priceData) {
        this.priceEventType = priceEventType;
        this.priceData = priceData;
    }
}
