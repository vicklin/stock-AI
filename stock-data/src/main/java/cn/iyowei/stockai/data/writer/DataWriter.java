package cn.iyowei.stockai.data.writer;

import cn.iyowei.stockai.data.core.StockTuple;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by vick on 15-9-15.
 */
public class DataWriter {

    private RedisTemplate redisTemplate;

    BoundZSetOperations getOps(String key) {
        return this.redisTemplate.boundZSetOps(key);
    }

    public void save(String setName, Set<StockTuple> targets) {
        getOps(setName).add(targets);
    }

    public void save(String setName, StockTuple target) {
        getOps(setName).add(target.getValue(), target.getScore());
    }

    public void remove(String setName) {
        getOps(setName).removeRangeByScore(0, Double.MAX_VALUE);
    }

    public void remove(String setName, StockTuple target) {
        getOps(setName).remove(target.getValue());
    }

    public void remove(String setName, Set<StockTuple> targets) {
        Set<Object> values = new HashSet<Object>();
        for (StockTuple st : targets) {
            values.add(st.getValue());
        }
        getOps(setName).remove(values);
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
