package cn.iyowei.stockai.service;

import cn.iyowei.stockai.dao.StockDao;
import cn.iyowei.stockai.data.core.StockTuple;
import cn.iyowei.stockai.data.manager.DataSetProxy;
import cn.iyowei.stockai.model.Stock;
import cn.iyowei.stockai.vo.dto.StockQuotationDto;
import cn.iyowei.stockai.vo.query.StockAIQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author vicklin123@gmail.com
 * @version 1.0
 * @since 1.0
 */

@Service
@Transactional
public class StockAIService {

    @Autowired
    private StockDao stockDao;

    @Autowired
    private DataSetProxy dataSetProxy;

    /**
     * 搜索
     * <ul>
     * <li>通过股票名字查询标签</li>
     * <li>通过标签名字查询股票</li>
     * <li>通过标签名字查询标签</li>
     * </ul>
     *
     * @param query
     * @return map
     */
    public Map<String, Set> search(StockAIQuery query) {
        Assert.hasText(query.getKey());
        Map<String, Set> map = new HashMap<String, Set>();
        map.put("list", stockDao.findByName(query));
        return map;
    }

    /**
     * 通过简称/代码/名称查询Stock
     *
     * @param key
     * @return
     */
    public List<Stock> findStocksByKey(String key) {
        return null;
    }


    public List<StockTuple> union(String set1, Collection<String> targets) {
        return dataSetProxy.listUnion(set1, targets);
    }


    public List<StockTuple> intersect(String set1, Collection<String> targets) {
        return dataSetProxy.listIntersect(set1, targets);
    }

    public void save(String setName, Collection<StockQuotationDto> c) {
        Set<StockTuple> stockTuples = new HashSet<StockTuple>();
        double score = 0;
        for (StockQuotationDto dto : c) {
            stockTuples.add(new StockTuple(dto.getCode(), score++));
        }
        dataSetProxy.save(setName, stockTuples);
    }

    public List<StockTuple> list(String setName) {
        return dataSetProxy.list(setName);
    }

    public void remove(String setName) {
        dataSetProxy.remove(setName);
    }
}
