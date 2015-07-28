package cn.iyowei.stockai.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * http servlet工具类型，如获取IP,host等信息
 *
 * @author Marco.hu(hzg139@163.com)
 */
public final class HttpServletUtils {

    protected static Logger logger = LoggerFactory.getLogger(HttpServletUtils.class);

    /**
     * 获取客户端IP地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 绕过JSP直接输出，用于如AJAX等请求的情况。
     * eg.
     * render("text/plain", "hello", "encoding:GBK");
     * render("text/plain", "hello", "no-cache:false");
     * render("text/plain", "hello", "encoding:GBK", "no-cache:false");
     *
     * @param contentType
     * @param content
     * @param headers     可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true.
     */
    public static void render(HttpServletResponse response, final String contentType, String content,
                              final String... headers) {
        try {
            // 分析headers参数
            String encoding = "UTF-8";
            boolean noCache = true;
            for (String header : headers) {
                String headerName = StringUtils.substringBefore(header, ":");
                String headerValue = StringUtils.substringAfter(header, ":");

                if (StringUtils.equalsIgnoreCase(headerName, "encoding")) {
                    encoding = headerValue;
                } else if (StringUtils.equalsIgnoreCase(headerName, "no-cache")) {
                    noCache = Boolean.parseBoolean(headerValue);
                } else
                    throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
            }

            // 设置headers参数
            String fullContentType = contentType + ";charset=" + encoding;
            response.setContentType(fullContentType);
            if (noCache) {
                response.addHeader("Cache-Control", "no-store, no-cache, must-revalidate");
                response.addHeader("Cache-Control", "post-check=0, pre-check=0");
                response.setHeader("Pragma", "No-cache");
                response.setDateHeader("Expires", 0);
            }
            System.err.println(content);
            response.getWriter().write(content);
            response.getWriter().flush();

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                content = null;
                response.getWriter().close();
            } catch (IOException e) {
                logger.error("绕过JSP直接输出，用于如AJAX等请求的情况关闭PrintWriter出错:{}" + e.fillInStackTrace());
            }
        }
    }

    /**
     * base url,eg. http://172.18.80.113:8081/iclass
     *
     * @param request
     * @return
     */
    public static String getBaseUrl(HttpServletRequest request) {
        return getDomain(request) + request.getContextPath();
    }

    /**
     * domain,eg. http://172.18.80.113:8081/
     *
     * @param request
     * @return
     */
    public static String getDomain(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }

    /**
     * download root url,eg. http://172.18.80.113:8081/edu/attachs/
     *
     * @param request
     * @return
     */
    public static String getDownloadUrl(HttpServletRequest request) {
        return getDomain(request) + AppPropsReader.get("storage.virtualPath");
    }

    /**
     * 获取请求头来源域
     * <p/>
     * 如果referer为空，则返回null；否则返回来源的域
     * <p/>
     * eg.<br/>
     * a.referer=http://172.18.80.113:8081/edu/attachs/ 则返回172.18.80.113:8081 <br/>
     * b.referer=http://gz.baidu.com/topic/ gz.baidu.com <br/>
     *
     * @param request
     * @return
     */
    public static String getRefererDomain(HttpServletRequest request) {
        String referer = request.getHeader("referer");
        if (null == referer) {
            return null;
        }
        referer = StringUtils.isEmpty(referer) ? "unknown domain" : referer;
        referer = referer.replace("http://", "").replace("https://", "");
        if (referer.contains("/")) {
            referer = referer.substring(0, referer.indexOf("/"));
        }
        return referer;
    }

}
