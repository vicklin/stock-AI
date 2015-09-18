package cn.iyowei.stockai.data.core;

/**
 * 个股简要信息
 * Created by vick on 15-9-17.
 */
public class StockBrief {

    // 600123.SH
    private String code;

    // 600123
    private String stockCode;

    private String shortName;

    private String spelling;

    // 0-SH; 1-SZ
    private Integer market;

    private String suspend;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getSpelling() {
        return spelling;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public String getSuspend() {
        return suspend;
    }

    public void setSuspend(String suspend) {
        this.suspend = suspend;
    }
}
