package cn.iyowei.stockai.web.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by vick on 15-7-28.
 */
public class FiveGapCrawler extends BaseJsonCrawler {

    private static final Logger log = LoggerFactory.getLogger(FiveGapCrawler.class);

    private String uri = "http://q.jrjimg.cn/?q=cn|s|sa&c=s,ta,tm,sl,cot,cat,ape,min5pl&n=hqa&o=min5pl,d&p=1020&_dc=1438083218923";

    public static void main(String[] args) throws IOException {
        FiveGapCrawler crawler = new FiveGapCrawler();
        crawler.crawl(crawler.uri);
    }

}




















