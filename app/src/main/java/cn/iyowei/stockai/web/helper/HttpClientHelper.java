package cn.iyowei.stockai.web.helper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by vick on 15-7-31.
 */
public class HttpClientHelper {
    public static String doGet(String uri) {
        String encodedUri = encodeUri(uri);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(encodedUri);
        HttpResponse response;
        String result;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = (null == entity) ? "" : EntityUtils.toString(entity);
        } catch (IOException e) {
            result = "";
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
            }
        }
        return result;
    }

    public static String encodeUri(String uri) {
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
}
