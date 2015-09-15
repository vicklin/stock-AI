package cn.iyowei.stockai.crawler;


import cn.iyowei.stockai.BaseSpringTest;
import cn.iyowei.stockai.util.json.JsonUtils;
import cn.iyowei.stockai.vo.dto.StockQuotationDto;
import cn.iyowei.stockai.web.crawler.StockCrawler;
import cn.iyowei.stockai.web.crawler.vo.QueryType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

/**
 * Created by liuguanglin on 15/9/13.
 */
public class StockCralwerTest extends BaseSpringTest {

    @Autowired
    private StockCrawler crawler;

    @Test
    public void testCrawl() throws IOException {
        List<StockQuotationDto> list = crawler.queryFivePL(QueryType.RankOrder.RISE, 0, 100);
        System.out.println(JsonUtils.toString(list));
    }
}
