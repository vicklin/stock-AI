package cn.iyowei.stockai.collector.resolver.business.jrj;

import cn.iyowei.stockai.collector.resolver.business.jrj.vo.Column;
import cn.iyowei.stockai.collector.resolver.business.jrj.vo.Hqa;
import cn.iyowei.stockai.crawler.analyse.resolve.Resolver;
import cn.iyowei.stockai.data.core.StockQuo;
import cn.iyowei.stockai.data.manager.DataSetProxy;
import cn.iyowei.stockai.util.json.JsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 针对金融界网站的返回结果进行处理
 * Created by vick on 15-9-17.
 */
public class JrjStockResolver implements Resolver {

    private DataSetProxy proxy;

    public JrjStockResolver(DataSetProxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public Object handle(String html, Object jsonResult) {
        String json = jsonResult.toString().replace("Summary", "summary").replace("HqData", "hqData").replace("Column", "column");
        Hqa hqa = JsonUtils.toBean(json, Hqa.class);

        List<Object> list = hqa.getHqData();
        List<Column> resultList = new ArrayList<Column>();
        List<StockQuo> stockList = new ArrayList<StockQuo>();
        for (Object obj : list) {
            String[] arr = obj.toString().replaceAll("\"", "").replaceAll("\\[", "").replaceAll("]", "").split(",");
            Column column = new Column();
            StockQuo q = new StockQuo();
            column.setId(arr[0].trim());
            column.setCode(arr[1].trim());

            q.setCode(arr[1].trim());

            column.setName(arr[2].trim());

            column.setLcp(Double.valueOf(arr[3])); //  lcp
            q.setLastPrice(Double.valueOf(arr[3])); //  lcp

            column.setStp(Double.valueOf(arr[4])); //  stp

            column.setNp(Double.valueOf(arr[5])); //  np
            q.setPrice(Double.valueOf(arr[5]));

            column.setTa(Double.valueOf(arr[6])); //  ta
            q.setTradeAmount(Double.valueOf(arr[6])); //  ta

            column.setTm(Double.valueOf(arr[7])); //  tm
            q.setTradeMoney(Double.valueOf(arr[7])); //  tm

            column.setHlp(Double.valueOf(arr[8])); //  hlp
            q.setGap(Double.valueOf(arr[8])); //  hlp

            column.setPl(Double.valueOf(arr[9])); //  pl
            q.setRate(Double.valueOf(arr[9])); //  pl

            column.setSl(Double.valueOf(arr[10])); //  sl
            q.setShake(Double.valueOf(arr[10])); //  sl

            column.setCat(Double.valueOf(arr[11])); //  cat
            q.setLb(Double.valueOf(arr[11])); //  cat

            column.setCot(Double.valueOf(arr[12])); //  cot
            q.setWb(Double.valueOf(arr[12])); //  cot

            column.setTr(Double.valueOf(arr[13])); //  tr
            q.setTradeRate(Double.valueOf(arr[13])); //  tr

            column.setApe(Double.valueOf(arr[14])); //  ape
            q.setPe(Double.valueOf(arr[14])); //  ape

            column.setMin5pl(Double.valueOf(arr[15])); //  min5pl
            q.setMin5pl(Double.valueOf(arr[15])); //  min5pl

            resultList.add(column);
            stockList.add(q);
        }

        proxy.updateQuotation(stockList);

        return resultList;
    }
}
