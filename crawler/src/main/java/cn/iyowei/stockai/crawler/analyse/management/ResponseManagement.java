package cn.iyowei.stockai.crawler.analyse.management;

import org.apache.http.HttpResponse;

/**
 * Created by vick on 15-9-14.
 */
public interface ResponseManagement {

    void manage(HttpResponse response);

}
