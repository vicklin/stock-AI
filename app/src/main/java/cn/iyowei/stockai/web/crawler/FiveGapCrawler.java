package cn.iyowei.stockai.web.crawler;

import cn.iyowei.stockai.vo.dto.StockQuotationDto;

import java.io.IOException;
import java.util.List;

/**
 * Created by vick on 15-7-28.
 */
public class FiveGapCrawler extends BaseJsonCrawler {

    private String uri = "http://q.jrjimg.cn/?q=cn|s|sa&c=s,ta,tm,sl,cot,cat,ape,min5pl&n=hqa&o=min5pl,d&p=1020&_dc=1438083218923";

    public static void main(String[] args) throws IOException {
        FiveGapCrawler crawler = new FiveGapCrawler();
        crawler.crawl(crawler.uri);
    }

    public List<StockQuotationDto> query() throws IOException {
        return super.crawl(this.uri);
    }

}




















