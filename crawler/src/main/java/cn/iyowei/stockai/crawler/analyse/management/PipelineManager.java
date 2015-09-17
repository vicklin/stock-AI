package cn.iyowei.stockai.crawler.analyse.management;

import cn.iyowei.stockai.crawler.analyse.resolve.Pipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vick on 15-9-14.
 */
public class PipelineManager implements ResponseManager {

    private List<Pipeline> pipelines = new ArrayList<Pipeline>();

    @Override
    public void manage(String html) {
        Object lastResult = null;
        for (Pipeline p : pipelines) {
            lastResult = p.handle(html, lastResult); // 链式调用，并把上一个调用的返回结果交给下一个
        }
    }

    public PipelineManager pipeline(Pipeline p) {
        this.pipelines.add(p);
        return this;
    }

}
