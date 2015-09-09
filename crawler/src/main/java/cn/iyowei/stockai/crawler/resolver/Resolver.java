package cn.iyowei.stockai.crawler.resolver;

import org.apache.http.HttpResponse;

import java.io.IOException;

/**
 * Created by vick on 15-9-9.
 */
public interface Resolver {

    <T> T resolve(HttpResponse response, ResponseType type) throws IOException;
}
