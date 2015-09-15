package cn.iyowei.stockai.crawler;

import cn.iyowei.stockai.crawler.analyse.management.ResponseManagement;
import cn.iyowei.stockai.crawler.download.Downloader;
import org.apache.http.HttpResponse;

/**
 * Created by vick on 15-9-14.
 */
public class Crawler {

    private String url;

    private ResponseManagement responseManagement;

    public void crawl() {
        handle();
    }

    public void crawl(String url) {
        this.url = url;
        handle();
    }

    private void handle() {
        try {
            HttpResponse response = new Downloader().get(url);
            responseManagement.manage(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Crawler setURL(String url) {
        setUrl(url);
        return this;
    }

    public Crawler setRM(ResponseManagement responseManagement) {
        setResponseManagement(responseManagement);
        return this;
    }

    public void setResponseManagement(ResponseManagement responseManagement) {
        this.responseManagement = responseManagement;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
