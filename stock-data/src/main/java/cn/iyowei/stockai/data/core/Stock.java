package cn.iyowei.stockai.data.core;

/**
 * 个股信息详情
 * Created by vick on 15-9-17.
 */
public class Stock {

    private StockBrief brief;
    private StockQuo quo;

    public Stock() {
    }

    public Stock(StockBrief brief, StockQuo quo) {
        this.brief = brief;
        this.quo = quo;
    }

    public StockBrief getBrief() {
        return brief;
    }

    public void setBrief(StockBrief brief) {
        this.brief = brief;
    }

    public StockQuo getQuo() {
        return quo;
    }

    public void setQuo(StockQuo quo) {
        this.quo = quo;
    }
}
