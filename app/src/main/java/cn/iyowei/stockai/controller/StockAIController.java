package cn.iyowei.stockai.controller;

import cn.iyowei.stockai.base.BaseRestSpringController;
import cn.iyowei.stockai.model.Stock;
import cn.iyowei.stockai.rest.DataMap;
import cn.iyowei.stockai.service.StockAIService;
import cn.iyowei.stockai.vo.query.StockAIQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 股票-标签Controller
 *
 * @author vicklin123@gmail.com
 * @version 1.0
 * @since 1.0
 */

@RestController
@RequestMapping("/stockai")
public class StockAIController extends BaseRestSpringController {

    @Autowired
    private StockAIService stockAIService;

    /**
     * binder用于bean属性的设置
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    /**
     * 增加了@ModelAttribute的方法可以在本controller方法调用前执行,可以存放一些共享变量,如枚举值,或是一些初始化操作
     */
    @ModelAttribute
    public void init(ModelMap model) {
        model.put("now", new Date(System.currentTimeMillis()));
    }

    /**
     * 搜索
     * <ul>
     * <li>通过股票名字查询标签</li>
     * <li>通过标签名字查询股票</li>
     * <li>通过标签名字查询标签</li>
     * </ul>
     */
    @RequestMapping
    public DataMap list(StockAIQuery query) {
        DataMap dataMap = new DataMap();
        try {
            Map<String, Set> searchResult = stockAIService.search(query);
            dataMap.addAttribute(DATA, searchResult);
            dataMap.addAttribute(STATUS_CODE, HttpStatus.OK.value());
            dataMap.addAttribute(MESSAGE, HttpStatus.OK.getReasonPhrase());
        } catch (Exception e) {
            logger.error("StockAIController exception:", e);
            dataMap.addAttribute(STATUS_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return dataMap;
    }


    /**
     * 通过简称/代码/名称查询Stock
     */
    @RequestMapping("/{key}")
    public DataMap searchByKey(@PathVariable String key) {
        DataMap dataMap = new DataMap();
        try {
            List<Stock> stocks = stockAIService.findStocksByKey(key);
            dataMap.addAttribute(DATA, stocks);
            dataMap.addAttribute(STATUS_CODE, HttpStatus.OK.value());
            dataMap.addAttribute(MESSAGE, HttpStatus.OK.getReasonPhrase());
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.addAttribute(STATUS_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return dataMap;
    }


}
