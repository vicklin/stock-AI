package cn.iyowei.stockai.crawler;

import cn.iyowei.stockai.crawler.analyse.management.ResponseManager;
import cn.iyowei.stockai.crawler.download.Downloader;

/**
 * Created by vick on 15-9-14.
 */
public class Crawler {

    private String url;

    private ResponseManager manager;

    public Crawler() {
    }

    public Crawler(String url) {
        this.url = url;
    }

    public void crawl() {
        try {
            String html = new Downloader().get(url);
            manager.manage(html);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Crawler setManager(ResponseManager manager) {
        this.manager = manager;
        return this;
    }

    public Crawler setUrl(String url) {
        this.url = url;
        return this;
    }

}
