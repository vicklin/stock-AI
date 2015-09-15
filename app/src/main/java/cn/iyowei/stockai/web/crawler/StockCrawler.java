package cn.iyowei.stockai.web.crawler;

import cn.iyowei.stockai.vo.dto.StockQuotationDto;
import cn.iyowei.stockai.web.crawler.vo.QueryType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * {@see http://summary.jrj.com.cn/gpph/sa_tr_d.shtml}
 * Created by vick on 15-7-28.
 */
@Component
public class StockCrawler extends BaseJsonCrawler {

    public static final String URL_TPL = "http://q.jrjimg.cn/?q=cn|s|sa&n=hqa&c={props}&o={rankProp},{rankOrder}&p={pageNo}0{pageSize}&_dc={date}";
    private static final String TPL_PROPS = "{props}";
    private static final String TPL_RANK_PROP = "{rankProp}";
    private static final String TPL_RANK_ORDER = "{rankOrder}";
    private static final String TPL_PAGE_SIZE = "{pageSize}";
    private static final String TPL_PAGE_NO = "{pageNo}";
    private static final String TPL_DATE = "{date}";

    public static void main(String[] args) throws IOException {
        StockCrawler crawler = new StockCrawler();
        crawler.queryFivePL(QueryType.RankOrder.RISE, 1, 30);
    }

    /**
     * 成交量排行
     *
     * @param rankOrder
     * @return
     * @throws IOException
     */
    public List<StockQuotationDto> queryTradeAmount(QueryType.RankOrder rankOrder, int pageNo, int pageSize) throws IOException {
        return super.crawl(template(QueryType.props, QueryType.RankProp.TRADE_AMOUNT, rankOrder, pageNo, pageSize));
    }

    /**
     * 振幅排行
     *
     * @param rankOrder
     * @return
     * @throws IOException
     */
    public List<StockQuotationDto> queryShake(QueryType.RankOrder rankOrder, int pageNo, int pageSize) throws IOException {
        return super.crawl(template(QueryType.props, QueryType.RankProp.SHAKE, rankOrder, pageNo, pageSize));

    }

    /**
     * 5分钟涨跌幅
     *
     * @param rankOrder
     * @return
     * @throws IOException
     */
    public List<StockQuotationDto> queryFivePL(QueryType.RankOrder rankOrder, int pageNo, int pageSize) throws IOException {
        return super.crawl(template(QueryType.props, QueryType.RankProp.FIVE_PL, rankOrder, pageNo, pageSize));
    }

    /**
     * 60分钟涨跌幅
     *
     * @param rankOrder
     * @return
     * @throws IOException
     */
    public List<StockQuotationDto> queryHourPL(QueryType.RankOrder rankOrder) throws IOException {
        // FIXME 此处返回的不是StockQuotationDto
        // {@see http://summary.jrj.com.cn/sjtj/drtj.shtml}
        return null;
    }

    /**
     * 涨跌幅
     *
     * @param rankOrder
     * @return
     * @throws IOException
     */
    public List<StockQuotationDto> queryPL(QueryType.RankOrder rankOrder, int pageNo, int pageSize) throws IOException {
        return super.crawl(template(QueryType.props, QueryType.RankProp.PL, rankOrder, pageNo, pageSize));

    }

    /**
     * 换手率
     *
     * @param rankOrder
     * @return
     * @throws IOException
     */
    public List<StockQuotationDto> queryTradeRate(QueryType.RankOrder rankOrder, int pageNo, int pageSize) throws IOException {
        return super.crawl(template(QueryType.props, QueryType.RankProp.TRADE_RAGE, rankOrder, pageNo, pageSize));
    }

    private String template(String props, QueryType.RankProp rankProp, QueryType.RankOrder rankOrder, int pageNo, int pageSize) {
        String uri = URL_TPL.replace(TPL_PROPS, props)
                .replace(TPL_RANK_PROP, rankProp.value())
                .replace(TPL_RANK_ORDER, rankOrder.value())
                .replace(TPL_PAGE_NO, String.valueOf(pageNo))
                .replace(TPL_PAGE_SIZE, String.valueOf(pageSize))
                .replace(TPL_DATE, String.valueOf(System.currentTimeMillis()));
        return uri;
    }

}




















