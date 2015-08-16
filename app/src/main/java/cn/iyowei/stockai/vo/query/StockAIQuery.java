package cn.iyowei.stockai.vo.query;

import cn.iyowei.stockai.base.BaseQuery;

/**
 * @author vicklin123@gmail.com
 * @version 1.0
 * @since 1.0
 */

/**
 * 股票标签搜索query
 */
public class StockAIQuery extends BaseQuery {

    private String key; // 搜索关键字
    private Integer start = 0; // 默认从第0个开始取
    private Integer end = -1; // 默认取全部
    private Integer order = 1; // 0:升序, 1:降序

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
