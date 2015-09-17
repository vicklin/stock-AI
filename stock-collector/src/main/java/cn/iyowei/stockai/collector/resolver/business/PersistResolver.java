package cn.iyowei.stockai.collector.resolver.business;

import cn.iyowei.stockai.crawler.analyse.resolve.Resolver;
import cn.iyowei.stockai.data.manager.DataSetProxy;
import cn.iyowei.stockai.util.json.JsonUtils;


/**
 * Created by vick on 15-9-17.
 */
public class PersistResolver implements Resolver {

    private DataSetProxy proxy; // autowired

    @Override
    public Object handle(String html, Object lastResult) {
        System.out.println(JsonUtils.toString(lastResult));
        return null;
    }
}
