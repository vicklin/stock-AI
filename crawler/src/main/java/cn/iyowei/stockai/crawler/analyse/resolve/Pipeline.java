package cn.iyowei.stockai.crawler.analyse.resolve;

import org.apache.http.HttpResponse;

/**
 * Created by vick on 15-9-14.
 */
public abstract class Pipeline {

    private Pipeline nextPipeline;

    public abstract void handle(HttpResponse response);

    Pipeline next() {
        return nextPipeline;
    }

}
