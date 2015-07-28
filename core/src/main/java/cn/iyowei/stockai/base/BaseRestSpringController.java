package cn.iyowei.stockai.base;

import cn.iyowei.stockai.config.ACK;
import cn.iyowei.stockai.util.ConvertRegisterHelper;
import cn.iyowei.stockai.util.HttpServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

/**
 * 标准的rest方法列表
 * <pre>
 * /userinfo                => index()
 * /userinfo/new            => add()  注意: 不使用/userinfo/add => add()的原因是ad会被一些浏览器当做广告URL拦截
 * /userinfo/{id}           => show()
 * /userinfo/{id}/edit      => edit()
 * /userinfo        POST    => create()
 * /userinfo/{id}   PUT     => update()
 * /userinfo/{id}   DELETE  => delete()
 * /userinfo        DELETE  => batchDelete()
 * </pre>
 *
 * @author badqiu
 */
public class BaseRestSpringController {

    public static final String CONTENT_TYPE_JSON = "content-type=application/json";
    /**
     * 返回的数据
     */
    public static final String DATA = "data";
    /**
     * 返回的提示信息（一般为错误信息）
     */
    public static final String MESSAGE = "message";
    /**
     * 此处的值尽可能使用{@link org.springframework.http.HttpStatus} 的value
     * <p> 如：dataMap.addAttribute(STATUS_CODE, HttpStatus.OK.value());
     * <p> 当 {@link org.springframework.http.HttpStatus} 无法表示的时候，使用{@link ACK}的业务代码表示
     */
    public static final String STATUS_CODE = "statusCode"; // 不适用ACK，因为有ACK这个类,
    protected static Logger logger = LoggerFactory.getLogger(BaseRestSpringController.class);

    static {
        //注册converters
        ConvertRegisterHelper.registerConverters();
    }

    /**
     * 直接输出文本.
     *
     * @param response
     * @param text
     * @param headers
     */
    protected void renderText(HttpServletResponse response, String text, String... headers) {
        HttpServletUtils.render(response, "text/plain", text, headers);
    }

    /**
     * 直接输出HTML.
     *
     * @param response
     * @param html
     * @param headers
     */
    protected void renderHtml(HttpServletResponse response, String html, String... headers) {
        HttpServletUtils.render(response, "text/html", html, headers);
    }

    /**
     * 直接输出XML.
     *
     * @param response
     * @param xml
     * @param headers
     */
    protected void renderXml(HttpServletResponse response, String xml, String... headers) {
        HttpServletUtils.render(response, "text/xml", xml, headers);
    }

    /**
     * 直接输出JSON，返回状态缺省执行成功
     *
     * @param response
     * @param string   json字符串
     * @param headers
     */
    protected void renderJson(HttpServletResponse response, String string, String... headers) {
        HttpServletUtils.render(response, "application/json", string, headers);
    }

    /**
     * 直接输出Excel
     *
     * @param response
     * @param string
     * @param headers
     */
    protected void renderExcel(HttpServletResponse response, String string, String... headers) {
        HttpServletUtils.render(response, "application/ms-excel", string, headers);
    }

    /**
     * 直接输出为pdf文档.
     *
     * @param response
     * @param string
     * @param headers
     */
    protected void renderPdf(HttpServletResponse response, String string, String... headers) {
        HttpServletUtils.render(response, "application/pdf", string, headers);
    }
}
