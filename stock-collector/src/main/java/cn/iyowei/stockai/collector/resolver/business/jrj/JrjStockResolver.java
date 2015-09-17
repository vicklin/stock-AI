package cn.iyowei.stockai.collector.resolver.business.jrj;

import cn.iyowei.stockai.collector.resolver.business.jrj.vo.Column;
import cn.iyowei.stockai.collector.resolver.business.jrj.vo.Hqa;
import cn.iyowei.stockai.crawler.analyse.resolve.Pipeline;
import cn.iyowei.stockai.util.json.JsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vick on 15-9-17.
 */
public class JrjStockResolver implements Pipeline {

    @Override
    public Object handle(String html, Object jsonResult) {
        String json = jsonResult.toString().replace("Summary", "summary").replace("HqData", "hqData").replace("Column", "column");
        Hqa hqa = JsonUtils.toBean(json, Hqa.class);

        List<Object> list = hqa.getHqData();
        List<Column> resultList = new ArrayList<Column>();
        for (Object obj : list) {
            String[] arr = obj.toString().replaceAll("\"", "").replaceAll("\\[", "").replaceAll("]", "").split(",");
            Column dto = new Column();
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
            resultList.add(dto);
        }
        return resultList;
    }
}
