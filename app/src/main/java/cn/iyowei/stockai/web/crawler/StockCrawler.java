package cn.iyowei.stockai.web.crawler;

import cn.iyowei.stockai.vo.dto.StockQuotationDto;

import java.io.IOException;
import java.util.List;

/**
 * {@see http://summary.jrj.com.cn/gpph/sa_tr_d.shtml}
 * Created by vick on 15-7-28.
 */
public class StockCrawler extends BaseJsonCrawler {

    private static final String TPL_HOLDER = "{rank}";

    private String fiveGapUrl = "http://q.jrjimg.cn/?q=cn|s|sa&c=s,ta,tm,sl,cot,cat,ape,min5pl&n=hqa&o=min5pl,{rank}&p=1020&_dc=1438083218923";
    private String changeRateUrl = "http://q.jrjimg.cn/?q=cn|s|sa&c=s,ta,tm,sl,cot,cat,ape&n=hqa&o=tr,{rank}&p=1020&_dc=1441792200036";

    /**
     * 5分钟涨跌幅
     *
     * @param rank
     * @return
     * @throws IOException
     */
    public List<StockQuotationDto> queryFiveGap(Rank rank) throws IOException {
        return super.crawl(template(this.fiveGapUrl, rank));
    }

    /**
     * 换手率
     *
     * @param rank
     * @return
     * @throws IOException
     */
    public List<StockQuotationDto> queryChangeRate(Rank rank) throws IOException {
        return super.crawl(template(this.changeRateUrl, rank));
    }

    public static void main(String[] args) throws IOException {
        StockCrawler crawler = new StockCrawler();
        crawler.queryFiveGap(Rank.RISE);
    }

    private String template(String tpl, Rank rank) {
        return tpl.replace(TPL_HOLDER, rank.value());
    }

    public enum Rank {
        // 涨
        RISE("d"),
        // 跌
        FALL("a");
        private String value;

        Rank(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }

}




















