package order;

import java.io.Serializable;

public interface MatchableOrder<T extends Order> extends Serializable {

    public T getOrder();
//    public double getKey();


}
