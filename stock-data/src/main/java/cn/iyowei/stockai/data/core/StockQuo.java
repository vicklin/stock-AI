package cn.iyowei.stockai.data.core;

import java.io.Serializable;

/**
 * 个股价格信息
 * Created by vick on 15-9-20.
 */
public class StockQuo implements Serializable {

    private String code;

    private Double price;
    private Double lastPrice;
    private Double topPrice;
    private Double bottomPrice;
    private Double openPrice;

    private Double gap; // 涨跌额
    private Double rate; // 涨跌幅

    private Double tradeAmount; // 成交量

    private Double tradeMoney; // 成交金额

    private Double pe; // 市盈率

    private Double shake; // 振幅

    private Double lb; // 量比

    private Double wb; // 委比

    private Double tradeRate; // 换手率

    private Double min5pl; //15 五分钟涨幅


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Double getTopPrice() {
        return topPrice;
    }

    public void setTopPrice(Double topPrice) {
        this.topPrice = topPrice;
    }

    public Double getBottomPrice() {
        return bottomPrice;
    }

    public void setBottomPrice(Double bottomPrice) {
        this.bottomPrice = bottomPrice;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Double getGap() {
        return gap;
    }

    public void setGap(Double gap) {
        this.gap = gap;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(Double tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public Double getPe() {
        return pe;
    }

    public void setPe(Double pe) {
        this.pe = pe;
    }

    public Double getTradeMoney() {
        return tradeMoney;
    }

    public void setTradeMoney(Double tradeMoney) {
        this.tradeMoney = tradeMoney;
    }

    public Double getShake() {
        return shake;
    }

    public void setShake(Double shake) {
        this.shake = shake;
    }

    public Double getLb() {
        return lb;
    }

    public void setLb(Double lb) {
        this.lb = lb;
    }

    public Double getWb() {
        return wb;
    }

    public void setWb(Double wb) {
        this.wb = wb;
    }

    public Double getTradeRate() {
        return tradeRate;
    }

    public void setTradeRate(Double tradeRate) {
        this.tradeRate = tradeRate;
    }

    public Double getMin5pl() {
        return min5pl;
    }

    public void setMin5pl(Double min5pl) {
        this.min5pl = min5pl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
