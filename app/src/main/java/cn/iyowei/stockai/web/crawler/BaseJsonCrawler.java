package cn.iyowei.stockai.web.crawler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by vick on 15-7-28.
 */
public abstract class BaseJsonCrawler {

    private static final Logger log = LoggerFactory.getLogger(BaseJsonCrawler.class);

    public String get(String uri) {
        log.debug("Get:" + uri);
        String encodedUri = encodeUri(uri);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(encodedUri);
        log.debug("getRequestLine", httpGet.getRequestLine());
        HttpResponse response;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            log.debug("getStatusLine", response.getStatusLine());

            if (null != entity) {
                String result = EntityUtils.toString(entity);
                log.debug("getContentEncoding", entity.getContentEncoding());
                log.debug("responseBody", result);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private String encodeUri(String uri) {
        int quoIndex = uri.indexOf("?");
        if (quoIndex < 0 || quoIndex == uri.length()) {
            return uri;
        }
        String url = uri.substring(0, quoIndex);
        String param = uri.substring(quoIndex + 1);
        String encodedUri = uri;
        try {
            encodedUri = url + "?" + URLEncoder.encode(param, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedUri;
    }

    public abstract void crawl(String path) throws IOException;

    public abstract <T> List<T> analysis(String json, Class clazz);
}
