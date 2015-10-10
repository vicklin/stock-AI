package cn.iyowei.stockai.collector.link;

/**
 * Created by liuguanglin on 15/9/9.
 */
public class QueryType {

    public static final String props;

    // 构造需要获取的属性列（根据可排序的列）
    static {
        StringBuffer sb = new StringBuffer();
        RankProp[] prs = RankProp.values();
        int i = 1;
        for (RankProp p : prs) {
            sb.append(p.value());
            if (i++ < prs.length) {
                sb.append(",");
            }
        }
        props = sb.toString();
    }

    // order
    public enum RankOrder {
        // 涨
        RISE("d"),
        // 跌
        FALL("a");
        private String value;

        RankOrder(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }


    // props
    public enum RankProp {
        ID("id"),
        NAME("name"),
        CODE("code"),
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
        TRADE_RATE("tr"),
        // 涨跌幅
        PL("pl"),
        // 成交量
        TRADE_AMOUNT("ta"),
        // 振幅
        SHAKE("sl");
        private String value;

        RankProp(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }
}
