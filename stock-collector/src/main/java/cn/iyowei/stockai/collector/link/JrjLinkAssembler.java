package cn.iyowei.stockai.collector.link;

/**
 * Created by liuguanglin on 15/10/10.
 */
public class JrjLinkAssembler {

    public static final String URL_TPL = "http://q.jrjimg.cn/?q=cn|s|sa&n=hqa&c={props}&o={rankProp},{rankOrder}&p={pageNo}0{pageSize}&_dc={date}";
    private static final String TPL_PROPS = "{props}";
    private static final String TPL_RANK_PROP = "{rankProp}";
    private static final String TPL_RANK_ORDER = "{rankOrder}";
    private static final String TPL_PAGE_SIZE = "{pageSize}";
    private static final String TPL_PAGE_NO = "{pageNo}";
    private static final String TPL_DATE = "{date}";

    private static String template(String props, QueryType.RankProp rankProp, QueryType.RankOrder rankOrder, int pageNo, int pageSize) {
        String uri = URL_TPL.replace(TPL_PROPS, props)
                .replace(TPL_RANK_PROP, rankProp.value())
                .replace(TPL_RANK_ORDER, rankOrder.value())
                .replace(TPL_PAGE_NO, String.valueOf(pageNo))
                .replace(TPL_PAGE_SIZE, String.valueOf(pageSize))
                .replace(TPL_DATE, String.valueOf(System.currentTimeMillis()));
        return uri;
    }


    /**
     * 成交量排行
     *
     * @param rankOrder
     * @return
     */
    public static String queryTradeAmount(QueryType.RankOrder rankOrder, int pageNo, int pageSize) {
        return template(QueryType.props, QueryType.RankProp.TRADE_AMOUNT, rankOrder, pageNo, pageSize);
    }

    /**
     * 振幅排行
     *
     * @param rankOrder
     * @return
     * @
     */
    public static String queryShake(QueryType.RankOrder rankOrder, int pageNo, int pageSize) {
        return template(QueryType.props, QueryType.RankProp.SHAKE, rankOrder, pageNo, pageSize);

    }

    /**
     * 5分钟涨跌幅
     *
     * @param rankOrder
     * @return
     * @
     */
    public static String queryFivePL(QueryType.RankOrder rankOrder, int pageNo, int pageSize) {
        return template(QueryType.props, QueryType.RankProp.FIVE_PL, rankOrder, pageNo, pageSize);
    }

    /**
     * 60分钟涨跌幅
     *
     * @param rankOrder
     * @return
     * @
     */
    public static String queryHourPL(QueryType.RankOrder rankOrder, int pageNo, int pageSize) {
        // FIXME 此处返回的不是StockQuotationDto
        // {@see http://summary.jrj.com.cn/sjtj/drtj.shtml}
        return null;
    }

    /**
     * 涨跌幅
     *
     * @param rankOrder
     * @return
     * @
     */
    public static String queryPL(QueryType.RankOrder rankOrder, int pageNo, int pageSize) {
        return template(QueryType.props, QueryType.RankProp.PL, rankOrder, pageNo, pageSize);

    }

    /**
     * 换手率
     *
     * @param rankOrder
     * @return
     * @
     */
    public static String queryTradeRate(QueryType.RankOrder rankOrder, int pageNo, int pageSize) {
        return template(QueryType.props, QueryType.RankProp.TRADE_RATE, rankOrder, pageNo, pageSize);
    }

}
