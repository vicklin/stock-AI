package cn.iyowei.stockai.crawler;

import cn.iyowei.stockai.crawler.analyse.management.ResultManager;
import cn.iyowei.stockai.crawler.download.Downloader;

/**
 * Created by vick on 15-9-14.
 */
public class Crawler {

    private String url;

    public Crawler() {
    }

    public Crawler(String url) {
        this.url = url;
    }

    public void crawl(ResultManager manager) {
        try {
            String html = new Downloader().get(url);
            manager.manage(html);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Crawler setUrl(String url) {
        this.url = url;
        return this;
    }

}
