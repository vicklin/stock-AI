package cn.iyowei.stockai.web.resolver;

import cn.iyowei.stockai.util.json.JsonUtils;
import cn.iyowei.stockai.util.HttpServletUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理controller层异常
 *
 * @author Vick.liu (vicklin123@gmail.com)
 */
public class WebAndJsonMappingExceptionResolver extends SimpleMappingExceptionResolver {

    /**
     * 错误信息最大长度，如果大于这个长度，则判断为系统错误信息，直接显示:"Sorry, 404"。否则显示此错误信息
     */
    protected static final int MESSAGE_MAX_SIZE = 100;

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
                                              Object handler, Exception ex) {

        logger.error("Web Exception:", ex);

        // Expose ModelAndView for chosen error view.
        String viewName = determineViewName(ex, request);
        if (viewName != null) {
            // Apply HTTP status code for error views, if specified.
            // Only apply it if we're processing a top-level request.

            if (isAjaxRequest(request)) {
                //applyStatusCodeIfPossible(request, response, 200);
                Integer statusCode = determineStatusCode(request, viewName);
                applyStatusCodeIfPossible(request, response, statusCode);
                Map<String, Object> resultMap = new HashMap<String, Object>(2);
                resultMap.put("statusCode", statusCode);
                resultMap.put("message", getErrorMessage(ex));
                String json = JsonUtils.mapToJson(resultMap);
                response.setStatus(statusCode);
                System.out.println("before response.getStatus() : " + response.getStatus());
                HttpServletUtils.render(response, "application/json", json);
                System.out.println("after response.getStatus() : " + response.getStatus());
                logger.error("ajax Request Exception:" + getErrorMessage(ex));
                return null;
            } else {
                Integer statusCode = determineStatusCode(request, viewName);
                if (statusCode != null) {
                    applyStatusCodeIfPossible(request, response, statusCode);
                }
                logger.error("sync Request Exception:" + getErrorMessage(ex));
                return getModelAndView(viewName, ex, request);
            }
        } else {
            return null;
        }
    }

    /**
     * 获取错误信息，如果错误信息大于 MESSAGE_MAX_SIZE 则直接显示“”，防止向用户界面抛出系统错误信息
     *
     * @param e 异常
     * @return
     * @notice 仅用于向用户输出信息，切勿用于写日志
     */
    protected Object getErrorMessage(Exception e) {
        return (null == e.getMessage() || e.getMessage().length() > MESSAGE_MAX_SIZE) ? "" : e.getMessage();
    }

    /**
     * 判断是否是异步请求
     *
     * @param request
     * @return
     */
    private boolean isAjaxRequest(HttpServletRequest request) {
        return (request.getHeader("accept").indexOf("application/json") > -1 || (request
                .getHeader("X-Requested-With") != null && request
                .getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1));
    }
}