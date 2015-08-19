package cn.iyowei.stockai.web.crawler;

import cn.iyowei.stockai.util.json.JsonUtils;
import cn.iyowei.stockai.vo.dto.StockQuotationDto;
import cn.iyowei.stockai.web.crawler.vo.Hqa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by vick on 15-7-28.
 */
public class FiveGapCrawler extends BaseJsonCrawler {

    private static final Logger log = LoggerFactory.getLogger(FiveGapCrawler.class);

    private String uri = "http://q.jrjimg.cn/?q=cn|s|sa&c=s,ta,tm,sl,cot,cat,ape,min5pl&n=hqa&o=min5pl,d&p=1020&_dc=1438083218923";

    public static void main(String[] args) throws UnsupportedEncodingException {
        FiveGapCrawler crawler = new FiveGapCrawler();
        crawler.crawl(crawler.uri);
    }

    @Override
    public void crawl(String uri) throws UnsupportedEncodingException {
        String result = get(uri);
        List<StockQuotationDto> quotes = analysis(result, Hqa.class);
        System.out.println(quotes.size());
    }

    @Override
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
            dto.setLcp(Double.valueOf(arr[3])); //  lcp)
            dto.setStp(Double.valueOf(arr[4])); //  stp)
            dto.setNp(Double.valueOf(arr[5])); //  np)
            dto.setTa(Double.valueOf(arr[6])); //  ta)
            dto.setTm(Double.valueOf(arr[7])); //  tm) ;
            dto.setHlp(Double.valueOf(arr[8])); //  hlp);
            dto.setPl(Double.valueOf(arr[9])); //  pl) ;
            dto.setSl(Double.valueOf(arr[10])); //  sl) ;
            dto.setCat(Double.valueOf(arr[11])); //  cat);
            dto.setCot(Double.valueOf(arr[12])); //  cot);
            dto.setTr(Double.valueOf(arr[13])); //  tr);
            dto.setApe(Double.valueOf(arr[14])); //  ape) ;
            dto.setMin5pl(Double.valueOf(arr[15])); //  min5pl);
            stockQuotationDtoList.add(dto);
            System.out.println(dto.toString());
        }
        return stockQuotationDtoList;
    }

}




















