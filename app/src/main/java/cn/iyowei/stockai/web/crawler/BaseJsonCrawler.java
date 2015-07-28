package cn.iyowei.stockai.web.crawler;

import java.util.List;

/**
 * Created by vick on 15-7-28.
 */
public abstract class BaseJsonCrawler {

    public abstract void crawl(String path);

    public abstract <T> List<T> analysis();
}
