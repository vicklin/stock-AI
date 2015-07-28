package cn.iyowei.stockai.model;

/**
 * Created by vick on 15-6-19.
 */
public class Stock {

    private Long id;
    private String name;
    private String pinyin;
    private String code;

    // 5分钟涨幅
    private Double fiveGap;
    // 成交量
    private Integer dealCount;
    // 成交额
    private Double dealAmount;
    // 振幅
    private Double fluctuation;
    // 换手率
    private Double changeRate;
    // 委比
    private Double exchangeRatio;
    // 量比
    private Double quantityRatio;
    // 市盈率
    private Double pe;

    public Stock() {
    }

    public Stock(Long id) {
        this.id = id;
    }

    public Stock(String name, String pinyin, String code) {
        this.name = name;
        this.pinyin = pinyin;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
