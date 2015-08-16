package cn.iyowei.stockai.service;

import cn.iyowei.stockai.dao.StockDao;
import cn.iyowei.stockai.model.Stock;
import cn.iyowei.stockai.vo.query.StockAIQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public Map<String, Set> serach(StockAIQuery query) {
        Assert.hasText(query.getKey());
        Map<String, Set> map = new HashMap<String, Set>();
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
}
