package cn.iyowei.stockai.data.manager;

import cn.iyowei.stockai.data.core.StockTuple;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * stock data operator proxy
 * Created by vick on 15-9-15.
 */
public class DataSetProxy {

    private DataSetManager manager;

    // ------------ read ----------------//
    public List<StockTuple> intersect(String set1, Collection<String> targetSets) {
        return manager.intersect(set1, targetSets);
    }

    public int count(String setName) {
        return manager.count(setName);
    }

    public List<StockTuple> list(String setName, long start, long limit) {
        return manager.list(setName, start, limit);
    }

    public List<StockTuple> list(String setName) {
        return manager.list(setName);
    }

    // ------------ write ----------------//
    public void save(String setName, Set<StockTuple> targets) {
        manager.save(setName, targets);
    }

    public void save(String setName, StockTuple target) {
        manager.save(setName, target);
    }

    public void remove(String setName) {
        manager.remove(setName);
    }

    public void remove(String setName, StockTuple target) {
        manager.remove(setName, target);
    }

    public void remove(String setName, Set<StockTuple> targets) {
        manager.remove(setName, targets);
    }

    public void setManager(DataSetManager manager) {
        this.manager = manager;
    }
}
