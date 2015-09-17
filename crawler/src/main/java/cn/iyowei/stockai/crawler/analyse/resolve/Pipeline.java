package cn.iyowei.stockai.crawler.analyse.resolve;

/**
 * Created by vick on 15-9-14.
 */
public interface Pipeline {

    Object handle(String html, Object lastResult);

}
