package cn.iyowei.stockai.data.manager;

import cn.iyowei.stockai.data.core.StockTuple;
import cn.iyowei.stockai.data.reader.DataReader;
import cn.iyowei.stockai.data.writer.DataWriter;

import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.Set;

/**
 * stock data operator
 * Created by vick on 15-9-15.
 */
public class DataSetManager extends Observable {

    private DataReader reader;

    private DataWriter writer;

    DataSetManager(DataReader dataReader, DataWriter dataWriter) {
        this.reader = dataReader;
        this.writer = dataWriter;
    }

    public List<StockTuple> listUnion(String set1, Collection<String> targetSets) {
        List<StockTuple> list = reader.listUnion(set1, targetSets);
        checkThreshold(list, 1); // FIXME 此处应该有对threshold的type以及对应的threshold值进行规范。。
        return list;
    }

    public List<StockTuple> listIntersect(String set1, Collection<String> targetSets) {
        List<StockTuple> list = reader.listIntersect(set1, targetSets);
        checkThreshold(list, 2); // FIXME 此处应该有对threshold的type以及对应的threshold值进行规范。。
        return list;
    }

    /**
     * FIXME
     * 1.规范各种数据类型对应的阀值
     * 2.重构list获取，类型应该是可配的
     * 3.超阀值机制重新定制
     *
     * @param list
     * @param i
     */
    private void checkThreshold(List<StockTuple> list, int i) {
        DataThreshold dt = new DataThreshold(i, 100);
        dt.setAmount(list.size());
        if (dt.isUnderThreshold()) {
            setChanged();
            notifyObservers(dt);
        }
    }

    public int count(String setName) {
        return reader.count(setName);
    }

    public List<StockTuple> list(String setName, long start, long limit) {
        return reader.list(setName, start, limit);
    }

    public List<StockTuple> list(String setName) {
        return reader.list(setName);
    }

    public void save(String setName, Set<StockTuple> targets) {
        writer.save(setName, targets);
    }

    public void save(String setName, StockTuple target) {
        writer.save(setName, target);
    }

    public void remove(String setName) {
        writer.remove(setName);
    }

    public void remove(String setName, StockTuple target) {
        writer.remove(setName, target);
    }

    public void remove(String setName, Set<StockTuple> targets) {
        writer.remove(setName, targets);
    }

}
