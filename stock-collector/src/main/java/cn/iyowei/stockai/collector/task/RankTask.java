package cn.iyowei.stockai.collector.task;

import cn.iyowei.stockai.collector.link.JrjLinkAssembler;
import cn.iyowei.stockai.collector.link.QueryType;
import cn.iyowei.stockai.collector.resolver.JsonpResolver;
import cn.iyowei.stockai.collector.resolver.business.jrj.JrjStockResolver;
import cn.iyowei.stockai.crawler.Crawler;
import cn.iyowei.stockai.crawler.analyse.management.PipelineManager;
import cn.iyowei.stockai.crawler.analyse.management.ResultManager;
import cn.iyowei.stockai.crawler.analyse.resolve.ConsoleResolver;
import cn.iyowei.stockai.data.manager.DataSetProxy;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuguanglin on 15/10/4.
 */
public class RankTask {

    private Logger logger = Logger.getLogger(RankTask.class);

    @Autowired
    private DataSetProxy proxy;


    public void run() {
        logger.info("start rank task");
        queryFivePL();
//        queryHourPL();
        queryPL();
        queryShake();
        queryTradeAmount();
        queryTradeRate();
        logger.info("finish rank task");
    }


    /**
     * 成交量排行
     */
    public void queryTradeAmount() {
        int pageNo = 1;
        int pageSize = 50;
        String name = QueryType.RankProp.TRADE_AMOUNT.value();
        ResultManager manager = new PipelineManager().pipeline(new JsonpResolver()).pipeline(new JrjStockResolver(proxy, name)).pipeline(new ConsoleResolver());
        String url = JrjLinkAssembler.queryTradeAmount(QueryType.RankOrder.FALL, pageNo, pageSize);
        Crawler c = new Crawler();
        c.setUrl(url).crawl(manager);
    }

    /**
     * 振幅排行
     */
    public void queryShake() {
        int pageNo = 1;
        int pageSize = 50;
        String name = QueryType.RankProp.SHAKE.value();
        ResultManager manager = new PipelineManager().pipeline(new JsonpResolver()).pipeline(new JrjStockResolver(proxy, name)).pipeline(new ConsoleResolver());
        String url = JrjLinkAssembler.queryShake(QueryType.RankOrder.FALL, pageNo, pageSize);
        Crawler c = new Crawler();
        c.setUrl(url).crawl(manager);
    }

    /**
     * 5分钟涨跌幅
     */
    public void queryFivePL() {
        int pageNo = 1;
        int pageSize = 50;
        String name = QueryType.RankProp.FIVE_PL.value();
        ResultManager manager = new PipelineManager().pipeline(new JsonpResolver()).pipeline(new JrjStockResolver(proxy, name)).pipeline(new ConsoleResolver());
        String url = JrjLinkAssembler.queryFivePL(QueryType.RankOrder.FALL, pageNo, pageSize);
        Crawler c = new Crawler();
        c.setUrl(url).crawl(manager);
    }

    /**
     * 60分钟涨跌幅
     */
    public void queryHourPL() {
        int pageNo = 1;
        int pageSize = 50;
        String name = QueryType.RankProp.HOUR_PL.value();
        ResultManager manager = new PipelineManager().pipeline(new JsonpResolver()).pipeline(new JrjStockResolver(proxy, name)).pipeline(new ConsoleResolver());
        String url = JrjLinkAssembler.queryHourPL(QueryType.RankOrder.FALL, pageNo, pageSize);
        Crawler c = new Crawler();
        c.setUrl(url).crawl(manager);
    }

    /**
     * 涨跌幅
     */
    public void queryPL() {
        int pageNo = 1;
        int pageSize = 50;
        String name = QueryType.RankProp.PL.value();
        ResultManager manager = new PipelineManager().pipeline(new JsonpResolver()).pipeline(new JrjStockResolver(proxy, name)).pipeline(new ConsoleResolver());
        String url = JrjLinkAssembler.queryPL(QueryType.RankOrder.FALL, pageNo, pageSize);
        Crawler c = new Crawler();
        c.setUrl(url).crawl(manager);
    }

    /**
     * 换手率
     */
    public void queryTradeRate() {
        int pageNo = 1;
        int pageSize = 50;
        String name = QueryType.RankProp.TRADE_RATE.value();
        ResultManager manager = new PipelineManager().pipeline(new JsonpResolver()).pipeline(new JrjStockResolver(proxy, name)).pipeline(new ConsoleResolver());
        String url = JrjLinkAssembler.queryTradeRate(QueryType.RankOrder.FALL, pageNo, pageSize);
        Crawler c = new Crawler();
        c.setUrl(url).crawl(manager);
    }

}

