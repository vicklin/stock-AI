package cn.iyowei.stockai.data.reader;

import cn.iyowei.stockai.data.core.StockTuple;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by vick on 15-9-15.
 */
public class DataReader {

    private static final String ACTION_INTERSECT = "intersect";
    private static final String ACTION_UNION = "union";

    private RedisTemplate redisTemplate;

    BoundZSetOperations getOps(String key) {
        return this.redisTemplate.boundZSetOps(key);
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

    public List<StockTuple> listUnion(String set1, Collection<String> targetSets) {
        String tempSetKey = genTempSetKey(set1, targetSets, ACTION_UNION);
        getOps(set1).unionAndStore(targetSets, tempSetKey);
        BoundZSetOperations tempSetOps = getOps(tempSetKey);
        Set<StockTuple> tempSet = tempSetOps.rangeByScore(0, Double.MAX_VALUE);
        tempSetOps.expire(5, TimeUnit.SECONDS);
        return new ArrayList<StockTuple>(tempSet);
    }

    public List<StockTuple> listIntersect(String set1, Collection<String> targetSets) {
        String tempSetKey = genTempSetKey(set1, targetSets, ACTION_INTERSECT);
        getOps(set1).intersectAndStore(targetSets, tempSetKey);
        BoundZSetOperations tempSetOps = getOps(tempSetKey);
        Set<StockTuple> tempSet = tempSetOps.rangeByScore(0, Double.MAX_VALUE);
        tempSetOps.expire(5, TimeUnit.SECONDS);
        return new ArrayList<StockTuple>(tempSet);
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

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
