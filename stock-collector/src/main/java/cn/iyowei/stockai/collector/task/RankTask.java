package cn.iyowei.stockai.collector.task;

import cn.iyowei.stockai.collector.resolver.JsonpResolver;
import cn.iyowei.stockai.collector.resolver.business.jrj.JrjStockResolver;
import cn.iyowei.stockai.crawler.Crawler;
import cn.iyowei.stockai.crawler.analyse.management.PipelineManager;
import cn.iyowei.stockai.crawler.analyse.management.ResultManager;
import cn.iyowei.stockai.crawler.analyse.resolve.ConsoleResolver;
import cn.iyowei.stockai.data.manager.DataSetProxy;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuguanglin on 15/10/4.
 */
public class RankTask {

    @Autowired
    private DataSetProxy proxy;

    public void run() {
        Crawler c = new Crawler();
        String url = "http://q.jrjimg.cn/?q=cn|s|sa&n=hqa&c=id,name,code,stp,np,tm,hlp,cat,cot,ape,lcp,p4_pl,min5pl,tr,pl,ta,sl&o=pl,d&p=0050&_dc=1442473529798";
        ResultManager manager = new PipelineManager().pipeline(new JsonpResolver()).pipeline(new JrjStockResolver(proxy)).pipeline(new ConsoleResolver());
        c.setUrl(url).crawl(manager);
    }
}

