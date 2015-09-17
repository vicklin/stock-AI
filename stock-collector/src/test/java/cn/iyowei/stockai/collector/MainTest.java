package cn.iyowei.stockai.collector;

import cn.iyowei.stockai.collector.resolver.JsonpResolver;
import cn.iyowei.stockai.crawler.Crawler;
import cn.iyowei.stockai.crawler.analyse.management.PipelineManager;

/**
 * Created by vick on 15-9-17.
 */
public class MainTest {

    public static void main(String[] args) {
        Crawler c = new Crawler();
        String url = "http://q.jrjimg.cn/?q=cn|s|sa&n=hqa&c=id,name,code,stp,np,tm,hlp,cat,cot,ape,lcp,p4_pl,min5pl,tr,pl,ta,sl&o=pl,d&p=0050&_dc=1442473529798";
        c.setUrl(url).setManager(new PipelineManager().pipeline(new JsonpResolver())).crawl();
    }
}