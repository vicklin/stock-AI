package cn.iyowei.stockai.web.crawler;

import cn.iyowei.stockai.util.json.JsonUtils;
import cn.iyowei.stockai.web.crawler.vo.Hqa;
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
import java.util.Collections;
import java.util.List;

/**
 * Created by vick on 15-7-28.
 */
public class FiveGapCrawler extends BaseJsonCrawler {

    private static final Logger log = LoggerFactory.getLogger(FiveGapCrawler.class);

    private String uri = "http://q.jrjimg.cn/?q=cn|s|sa&c=s,ta,tm,sl,cot,cat,ape,min5pl&n=hqa&o=min5pl,d&p=1020&_dc=1438083218923";

    @Override
    public void crawl(String uri) throws UnsupportedEncodingException {
        String result = get(uri);
        List<Object> objs = analysis(result, Hqa.class);
        System.out.println(objs.size());
    }

    @Override
    public <T> List<T> analysis(String json, Class clazz) {
        log.info("analysis:" + json);

        String str = json.substring(8, json.length() - 2);
        str = str.replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", "")
                .replaceAll(":", "\":\"").replaceAll(",", "\",\"")
                .replaceAll("\\{", "\\{\"").replaceAll("}", "\"}")
                .replaceAll("\\[", "\\[\"").replaceAll("]", "\"]")
                .replaceAll("\"\"", "\"")
                .replaceAll("]\"", "]").replaceAll("\"\\[", "\\[")
                .replaceAll("}\"", "}").replaceAll("\"\\{", "\\{")
                .replace("Summary", "summary")
                .replace("HqData", "hqData")
                .replace("Column", "column");

        Hqa hqa = JsonUtils.jsonToBean(str, Hqa.class);
        return Collections.emptyList();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        FiveGapCrawler crawler = new FiveGapCrawler();
        crawler.crawl(crawler.uri);
    }

}
