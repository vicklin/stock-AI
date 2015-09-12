package cn.iyowei.stockai.dao;

import cn.iyowei.stockai.vo.dto.StockTuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by liuguanglin on 15/9/12.
 */
@Repository
public class StockAIDao {

    private static final String ACTION_INTERSECT = "intersetct";
    private static final String ACTION_UNION = "union";

    @Autowired
    private RedisTemplate redisTemplate;

    BoundZSetOperations getOps(String key) {
        return this.redisTemplate.boundZSetOps(key);
    }


    public List<StockTuple> union(String set1, Collection<String> targetSets) {
        String tempSetKey = genTempSetKey(set1, targetSets, ACTION_UNION);
        getOps(set1).unionAndStore(targetSets, tempSetKey);
        BoundZSetOperations tempSetOps = getOps(tempSetKey);
        Set<StockTuple> tempSet = tempSetOps.rangeByScore(0, Double.MAX_VALUE);
        tempSetOps.expire(5, TimeUnit.SECONDS);
        return new ArrayList<StockTuple>(tempSet);
    }

    public List<StockTuple> intersect(String set1, Collection<String> targetSets) {
        String tempSetKey = genTempSetKey(set1, targetSets, ACTION_INTERSECT);
        getOps(set1).intersectAndStore(targetSets, tempSetKey);
        BoundZSetOperations tempSetOps = getOps(tempSetKey);
        Set<StockTuple> tempSet = tempSetOps.rangeByScore(0, Double.MAX_VALUE);
        tempSetOps.expire(5, TimeUnit.SECONDS);
        return new ArrayList<StockTuple>(tempSet);
    }

    /**
     * 生成临时set的key，根据action:set1-set2-set3....的方式
     *
     * @param set1
     * @param targetSets
     * @param action
     * @return
     */
    private String genTempSetKey(String set1, Collection<String> targetSets, String action) {
        StringBuilder sb = new StringBuilder();
        sb.append(action).append(":").append(set1);
        for (String str : targetSets) {
            sb.append("-").append(str);
        }
        return sb.toString();
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

    public int count(String setName) {
        getOps(setName).count(0, Double.MAX_VALUE);
        return 0;
    }

    public List<StockTuple> list(String setName, long start, long limit) {
        return new ArrayList<StockTuple>(getOps(setName).rangeWithScores(start, limit));
    }


    public List<StockTuple> list(String setName) {
        return new ArrayList<StockTuple>(getOps(setName).rangeByScoreWithScores(0, Double.MAX_VALUE));
    }

}
