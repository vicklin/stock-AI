package cn.iyowei.stockai.web.crawler;

import java.util.List;

/**
 * Created by vick on 15-7-28.
 */
public class FiveGaoCrawler extends BaseJsonCrawler {
    private String path = "http://q.jrjimg.cn/?q=cn|s|sa&c=s,ta,tm,sl,cot,cat,ape,min5pl&n=hqa&o=min5pl,d&p=1020&_dc=1438083218923";

    @Override
    public void crawl(String path) {

    }

    @Override
    public <T> List<T> analysis() {
        return null;
    }
}
