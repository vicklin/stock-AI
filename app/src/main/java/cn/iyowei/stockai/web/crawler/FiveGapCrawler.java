package cn.iyowei.stockai.web.crawler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by vick on 15-7-28.
 */
public class FiveGapCrawler extends BaseJsonCrawler {
    private String uri = "http://q.jrjimg.cn/?q=cn|s|sa&c=s,ta,tm,sl,cot,cat,ape,min5pl&n=hqa&o=min5pl,d&p=1020&_dc=1438083218923";

    @Override
    public void crawl(String uri) throws UnsupportedEncodingException {
        String encodedUri = encodeUri(uri);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(encodedUri);
        System.out.println(httpGet.getRequestLine());
        HttpResponse response;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            System.out.println(response.getStatusLine());

            if (null != entity) {
                System.out.println(entity.getContentEncoding());
                System.out.println(EntityUtils.toString(entity));
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

    public static void main(String[] args) throws UnsupportedEncodingException {
        FiveGapCrawler crawler = new FiveGapCrawler();
        crawler.crawl(crawler.uri);
    }

    @Override
    public <T> List<T> analysis() {
        return null;
    }
}
