package cn.iyowei.stockai.data.core;

/**
 * 个股信息详情
 * Created by vick on 15-9-17.
 */
public class Stock extends StockBrief {

    private Double price;
    private Double lastPrice;
    private Double topPrice;
    private Double bottomPrice;
    private Double openPrice;

    private Double gap; // 涨跌额
    private Double rate; // 涨跌幅

    private Integer tradeAmount; // 交易量

    private Double pe; // 市盈率

    public Stock(Double price, Double lastPrice, Double topPrice, Double bottomPrice, Double openPrice, Double gap, Double rate, Integer tradeAmount, Double pe) {
        this.price = price;
        this.lastPrice = lastPrice;
        this.topPrice = topPrice;
        this.bottomPrice = bottomPrice;
        this.openPrice = openPrice;
        this.gap = gap;
        this.rate = rate;
        this.tradeAmount = tradeAmount;
        this.pe = pe;
    }
}
