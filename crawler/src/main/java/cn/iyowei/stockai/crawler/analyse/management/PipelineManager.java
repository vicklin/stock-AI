package cn.iyowei.stockai.crawler.analyse.management;

import cn.iyowei.stockai.crawler.analyse.resolve.Resolver;

import java.util.ArrayList;
import java.util.List;

/**
 * 管道处理Manger，内部manage使用管道链式调用
 * Created by vick on 15-9-14.
 */
public class PipelineManager implements ResultManager {

    private List<Resolver> resolvers = new ArrayList<Resolver>();

    @Override
    public void manage(String html) {
        Object lastResult = null;
        for (Resolver p : resolvers) {
            lastResult = p.handle(html, lastResult); // 链式调用，并把上一个调用的返回结果交给下一个
        }
    }

    public PipelineManager pipeline(Resolver p) {
        this.resolvers.add(p);
        return this;
    }

}
