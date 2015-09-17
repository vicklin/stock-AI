package cn.iyowei.stockai.collector.resolver.business;

import cn.iyowei.stockai.crawler.analyse.resolve.Pipeline;
import cn.iyowei.stockai.data.manager.DataSetProxy;

/**
 * Created by vick on 15-9-17.
 */
public class JrjStockResolver implements Pipeline {

    private DataSetProxy proxy;

    @Override
    public Object handle(String json, Object lastResult) {

        return null;
    }
}
