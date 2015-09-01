package cn.iyowei.stockai.web.crawler;

import cn.iyowei.stockai.util.json.JsonUtils;
import cn.iyowei.stockai.vo.dto.StockQuotationDto;
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
import java.util.ArrayList;
import java.util.LinkedList;
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

    public List<StockQuotationDto> crawl(String path) throws IOException {
        String result = get(path);
        List<StockQuotationDto> quotes = analysis(result, Hqa.class);
        return quotes;
    }

    public List<StockQuotationDto> analysis(String jsStr, Class clazz) {
        log.info("analysis:" + jsStr);
        String json = JsonUtils.jsStrToJsonStr(jsStr.substring(8, jsStr.length() - 2))
                .replace("Summary", "summary")
                .replace("HqData", "hqData")
                .replace("Column", "column");

        Hqa hqa = JsonUtils.jsonToBean(json, Hqa.class);
        LinkedList<Object> list = hqa.getHqData();
        List<StockQuotationDto> stockQuotationDtoList = new ArrayList<StockQuotationDto>();
        for (Object obj : list) {
            String[] arr = obj.toString().replaceAll("\"", "").replaceAll("\\[", "").replaceAll("]", "").split(",");
            StockQuotationDto dto = new StockQuotationDto();
            dto.setId(arr[0]);
            dto.setCode(arr[1]);
            dto.setName(arr[2]);
            dto.setLcp(Double.valueOf(arr[3])); //  lcp
            dto.setStp(Double.valueOf(arr[4])); //  stp
            dto.setNp(Double.valueOf(arr[5])); //  np
            dto.setTa(Double.valueOf(arr[6])); //  ta
            dto.setTm(Double.valueOf(arr[7])); //  tm
            dto.setHlp(Double.valueOf(arr[8])); //  hlp
            dto.setPl(Double.valueOf(arr[9])); //  pl
            dto.setSl(Double.valueOf(arr[10])); //  sl
            dto.setCat(Double.valueOf(arr[11])); //  cat
            dto.setCot(Double.valueOf(arr[12])); //  cot
            dto.setTr(Double.valueOf(arr[13])); //  tr
            dto.setApe(Double.valueOf(arr[14])); //  ape
            dto.setMin5pl(Double.valueOf(arr[15])); //  min5pl
            stockQuotationDtoList.add(dto);
        }
        return stockQuotationDtoList;
    }
}
