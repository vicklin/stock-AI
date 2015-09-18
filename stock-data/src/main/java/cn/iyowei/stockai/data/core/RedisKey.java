package cn.iyowei.stockai.data.core;

/**
 * Created by vick on 15-9-18.
 */
public enum RedisKey {
    //行情
    QUO("quo"),
    // 简要信息
    BRIEF("brief"),
    // stp
    STP("stp"),
    // 最新价格
    NEW_PRICE("np"),
    // 成交额
    TRADE_MONEY("tm"),
    // 涨跌额
    HLP("hlp"),
    // 量比
    CAT("cat"),
    // 委比
    COT("cot"),
    // pe
    APE("ape"),
    // 昨收
    LAST_CLOSE_PRICE("lcp"),
    // 60分钟涨跌幅
    HOUR_PL("p4_pl"),
    // 5分钟涨跌幅
    FIVE_PL("min5pl"),
    // 换手率
    TRADE_RAGE("tr"),
    // 涨跌幅
    PL("pl"),
    // 成交量
    TRADE_AMOUNT("ta"),
    // 振幅
    SHAKE("sl");

    private String key;

    RedisKey(String key) {
        this.key = key;
    }

    public String value() {
        return this.key;
    }
}
