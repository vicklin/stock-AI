package cn.iyowei.stockai.collector;

import cn.iyowei.stockai.collector.link.JrjLinkAssembler;
import cn.iyowei.stockai.collector.link.QueryType;
import cn.iyowei.stockai.collector.resolver.JsonpResolver;
import cn.iyowei.stockai.collector.resolver.business.jrj.JrjStockResolver;
import cn.iyowei.stockai.crawler.Crawler;
import cn.iyowei.stockai.crawler.analyse.management.PipelineManager;
import cn.iyowei.stockai.crawler.analyse.management.ResultManager;
import cn.iyowei.stockai.crawler.analyse.resolve.ConsoleResolver;
import cn.iyowei.stockai.data.manager.DataSetProxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by vick on 15-9-17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/applicationContext-*.xml"
})
public class MainTest {

    @Autowired
    private DataSetProxy proxy;

    @Test
    public void test() {
        String name = "test";
        Crawler c = new Crawler();
        String url = "http://q.jrjimg.cn/?q=cn|s|sa&n=hqa&c=id,name,code,stp,np,tm,hlp,cat,cot,ape,lcp,p4_pl,min5pl,tr,pl,ta,sl&o=pl,d&p=0050&_dc=1442473529798";
        ResultManager manager = new PipelineManager().pipeline(new JsonpResolver())
                .pipeline(new JrjStockResolver(proxy, name)).pipeline(new ConsoleResolver());
        c.setUrl(url).crawl(manager);
    }

    @Test
    public void testJrj() {
        int pageNo = 1;
        int pageSize = 20;
        Crawler c = new Crawler();
        ResultManager manager = new PipelineManager().pipeline(new JsonpResolver())
                .pipeline(new JrjStockResolver(proxy, QueryType.RankProp.FIVE_PL.value())).pipeline(new ConsoleResolver());
        c.setUrl(JrjLinkAssembler.queryFivePL(QueryType.RankOrder.FALL, pageNo, pageSize)).crawl(manager);
    }
}