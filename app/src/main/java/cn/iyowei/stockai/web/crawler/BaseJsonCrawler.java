package cn.iyowei.stockai.web.crawler;

import cn.iyowei.stockai.crawler.download.Downloader;
import cn.iyowei.stockai.crawler.exception.DownloadException;
import cn.iyowei.stockai.crawler.resolver.MultiTypeResolver;
import cn.iyowei.stockai.crawler.resolver.ResponseType;
import cn.iyowei.stockai.util.json.JsonUtils;
import cn.iyowei.stockai.vo.dto.StockQuotationDto;
import cn.iyowei.stockai.web.crawler.vo.Hqa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by vick on 15-7-28.
 */
public abstract class BaseJsonCrawler {

    private static final Logger log = LoggerFactory.getLogger(BaseJsonCrawler.class);

    public List<StockQuotationDto> crawl(String path) throws IOException {
        String result;
        try {
            result = Downloader.get(path, new MultiTypeResolver(), ResponseType.JSONP);
        } catch (DownloadException e) {
            return Collections.emptyList();
        }
        List<StockQuotationDto> quotes = analysis(result, Hqa.class);
        return quotes;
    }

    public List<StockQuotationDto> analysis(String json, Class clazz) {
        log.info("analysis:" + json);
        json = json.replace("Summary", "summary").replace("HqData", "hqData").replace("Column", "column");

        Hqa hqa = JsonUtils.toBean(json, Hqa.class);
        LinkedList<Object> list = hqa.getHqData();
        List<StockQuotationDto> stockQuotationDtoList = new ArrayList<StockQuotationDto>();
        for (Object obj : list) {
            String[] arr = obj.toString().replaceAll("\"", "").replaceAll("\\[", "").replaceAll("]", "").split(",");
            StockQuotationDto dto = new StockQuotationDto();
            dto.setId(arr[0].trim());
            dto.setCode(arr[1].trim());
            dto.setName(arr[2].trim());
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
