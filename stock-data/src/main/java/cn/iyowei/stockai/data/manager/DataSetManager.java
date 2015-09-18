package cn.iyowei.stockai.data.manager;

import cn.iyowei.stockai.data.core.Stock;
import cn.iyowei.stockai.data.core.StockBrief;
import cn.iyowei.stockai.data.core.StockTuple;
import cn.iyowei.stockai.data.reader.DataReader;
import cn.iyowei.stockai.data.writer.DataWriter;

import java.util.*;

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

    public List<StockTuple> intersect(String set1, Collection<String> targetSets) {
        List<StockTuple> list = reader.intersect(set1, targetSets);
        checkThreshold(set1, targetSets, list); // FIXME 此处应该有对threshold的type以及对应的threshold值进行规范。。
        return list;
    }

    /**
     * FIXME
     * 1.规范各种数据类型对应的阀值
     * 2.重构list获取，类型应该是可配的
     * 3.超阀值机制重新定制
     *
     * @param list
     */
    private void checkThreshold(String set1, Collection<String> targetSets, List<StockTuple> list) {
        DataThreshold dt = new DataThreshold(set1, targetSets, 100);
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

    public List<Stock> listQuotation(Collection<String> codes) {
        return reader.listQuotation(codes);
    }

    public List<Stock> listStock(Collection<String> codes) {
        List<Stock> list = reader.listQuotation(codes);
        List<StockBrief> briefs = reader.listStockBrief(codes);
        Map<String, StockBrief> map = new HashMap<String, StockBrief>();
        for (StockBrief s : briefs) {
            map.put(s.getCode(), s);
        }
        for (Stock s : list) {
        }
        return list;
    }

    public List<StockBrief> listStockBrief(Collection<String> codes) {
        return reader.listStockBrief(codes);
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

    /**
     * 更新股票基本信息
     *
     * @param list
     */
    public void updateBrief(List<StockBrief> list) {
        writer.updateBrief(list);
    }

    /**
     * 更新行情信息
     *
     * @param list
     */
    public void updateQuotation(List<Stock> list) {
        writer.updateQuotation(list);
    }
}
