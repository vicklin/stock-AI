package cn.iyowei.stockai.data.writer;

import cn.iyowei.stockai.data.core.RedisKey;
import cn.iyowei.stockai.data.core.Stock;
import cn.iyowei.stockai.data.core.StockBrief;
import cn.iyowei.stockai.data.core.StockTuple;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

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

    public void updateBrief(List<StockBrief> list) {
        Map<String, StockBrief> map = new HashMap<String, StockBrief>();
        for (StockBrief s : list) {
            if (!map.containsKey(s.getCode())) {
                map.put(s.getCode(), s);
            }
        }
        redisTemplate.boundHashOps(RedisKey.BRIEF).putAll(map);
    }

    public void updateQuotation(List<Stock> list) {
        Map<String, Stock> map = new HashMap<String, Stock>();
        for (Stock s : list) {
            if (!map.containsKey(s.getCode())) {
                map.put(s.getCode(), s);
            }
        }
        redisTemplate.boundHashOps(RedisKey.QUO).putAll(map);
    }
}
