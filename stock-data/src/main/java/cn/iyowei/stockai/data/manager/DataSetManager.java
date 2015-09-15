package cn.iyowei.stockai.data.manager;

import cn.iyowei.stockai.data.core.StockTuple;
import cn.iyowei.stockai.data.reader.DataReader;
import cn.iyowei.stockai.data.writer.DataWriter;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * stock data operator
 * Created by vick on 15-9-15.
 */
public class DataSetManager {

    private DataReader reader;

    private DataWriter writer;

    DataSetManager(DataReader dataReader, DataWriter dataWriter) {
        this.reader = dataReader;
        this.writer = dataWriter;
    }

    public List<StockTuple> listUnion(String set1, Collection<String> targetSets) {
        return reader.listUnion(set1, targetSets);
    }

    public List<StockTuple> listIntersect(String set1, Collection<String> targetSets) {
        return reader.listIntersect(set1, targetSets);
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
