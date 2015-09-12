package cn.iyowei.stockai.service;

import cn.iyowei.stockai.BaseSpringTest;
import cn.iyowei.stockai.util.json.JsonUtils;
import cn.iyowei.stockai.vo.dto.StockQuotationDto;
import cn.iyowei.stockai.vo.dto.StockTuple;
import cn.iyowei.stockai.web.crawler.StockCrawler;
import cn.iyowei.stockai.web.crawler.vo.QueryType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguanglin on 15/9/13.
 */
public class StockServiceTest extends BaseSpringTest {


    @Autowired
    private StockAIService aiService;

    @Autowired
    private StockCrawler crawler;


    @Test
    public void testSavePL() throws IOException {
        String setName = QueryType.RankProp.PL.value();
        List<StockQuotationDto> list = crawler.queryPL(QueryType.RankOrder.RISE, 0, 50);
        System.out.println(JsonUtils.listToJson(list));
        aiService.save(setName, list);
    }

    @Test
    public void testSaveFivePL() throws IOException {
        String setName = QueryType.RankProp.FIVE_PL.value();
        List<StockQuotationDto> list = crawler.queryFivePL(QueryType.RankOrder.RISE, 0, 50);
        System.out.println(JsonUtils.listToJson(list));
        aiService.save(setName, list);
    }

    @Test
    public void testSaveShake() throws IOException {
        String setName = QueryType.RankProp.SHAKE.value();
        List<StockQuotationDto> list = crawler.queryShake(QueryType.RankOrder.RISE, 0, 50);
        System.out.println(JsonUtils.listToJson(list));
        aiService.save(setName, list);
    }


    @Test
    public void testList() {
        String setName = QueryType.RankProp.FIVE_PL.value();
        List<StockTuple> list2 = aiService.list(setName);
        System.out.println(JsonUtils.listToJson(list2));
    }


    /**
     * 交集
     */
    @Test
    public void testIntersect() {
        List<String> targets = new ArrayList<String>();
        String setName = QueryType.RankProp.FIVE_PL.value();
//        String setName2 = QueryType.RankProp.PL.value();
        String setName3 = QueryType.RankProp.SHAKE.value();
//        targets.add(setName2);
        targets.add(setName3);
        List<StockTuple> list = aiService.intersect(setName, targets);
        System.out.println(JsonUtils.listToJson(list));
    }


    /**
     * 并集
     */
    @Test
    public void testUnion() {
        List<String> targets = new ArrayList<String>();
        String setName = QueryType.RankProp.FIVE_PL.value();
        String setName2 = QueryType.RankProp.PL.value();
        String setName3 = QueryType.RankProp.SHAKE.value();
        targets.add(setName2);
        targets.add(setName3);
        List<StockTuple> list = aiService.union(setName, targets);
        System.out.println(JsonUtils.listToJson(list));
    }

    @Test
    public void testRemove() {
        String setName = QueryType.RankProp.FIVE_PL.value();
        String setName2 = QueryType.RankProp.PL.value();
        String setName3 = QueryType.RankProp.SHAKE.value();
        aiService.remove(setName);
        aiService.remove(setName2);
        aiService.remove(setName3);
    }


}
