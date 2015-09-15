package cn.iyowei.stockai.crawler.analyse.management;

import cn.iyowei.stockai.crawler.analyse.resolve.Pipeline;
import org.apache.http.HttpResponse;

/**
 * Created by vick on 15-9-14.
 */
public class PipelineManagement implements ResponseManagement {

    private Pipeline pipeline;

    public PipelineManagement(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Override
    public void manage(HttpResponse response) {
        pipeline.handle(response);
    }
}
