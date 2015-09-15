package cn.iyowei.stockai.data.core;

import org.springframework.data.redis.core.DefaultTypedTuple;

/**
 * Created by liuguanglin on 15/9/12.
 */
public class StockTuple extends DefaultTypedTuple<String> {

    /**
     * Constructs a new <code>DefaultTypedTuple</code> instance.
     *
     * @param value
     * @param score
     */
    public StockTuple(String value, Double score) {
        super(value, score);
    }
}
