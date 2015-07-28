package cn.iyowei.stockai.web.handler;

import cn.iyowei.stockai.exception.UserUnauthorizedException;
import cn.iyowei.stockai.util.AppPropsReader;
import cn.iyowei.stockai.web.config.Constant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 自定义token拦截器，处理token验证的逻辑 2014-12-24
 *
 * @author chenyonghui
 */
public class TokenHandlerInterceptorAdapter extends HandlerInterceptorAdapter {

    private static final String UTF_8 = "UTF-8";
    private static final String IS_VALIDATE_REQUEST = (String) AppPropsReader.get("isValidateRequest");
    protected static Logger logger = LoggerFactory.getLogger(TokenHandlerInterceptorAdapter.class);

    static {
        System.out.println("TokenHandlerInterceptorAdapter start ...");
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        request.setCharacterEncoding(UTF_8);
        response.setCharacterEncoding(UTF_8);

        StringBuilder paramStr = new StringBuilder();
        paramStr.append(StringUtils.trimToEmpty(request.getParameter("tokenId")));
        paramStr.append(StringUtils.trimToEmpty(request.getParameter("timeStamp")));
        paramStr.append(StringUtils.trimToEmpty(request.getParameter("appName")));
        String signature = StringUtils.trimToEmpty(request.getParameter("signature"));
        String currSignature = StringUtils.lowerCase(DigestUtils.md5DigestAsHex(paramStr.toString().getBytes(UTF_8)));
        if (StringUtils.isNotBlank(IS_VALIDATE_REQUEST) && StringUtils.equalsIgnoreCase(IS_VALIDATE_REQUEST, "true")
                && !StringUtils.equalsIgnoreCase(currSignature, signature)) {
            logger.warn("signature error:currSignature=[" + currSignature + "],signature=[" + signature + "]");
            throw new UserUnauthorizedException("User Unauthorized Or Signature is error");
        }

        if (Constant.APP_MODE_DEV.equals(AppPropsReader.getString("appMode"))) {
            logParams(request);
        }

        return super.preHandle(request, response, handler);
    }

    private void logParams(HttpServletRequest request) {
        Set<Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();
        for (Entry<String, String[]> entry : entrySet) {
            StringBuffer sb = new StringBuffer();
            sb.append(entry.getKey() + " :: ");
            for (String val : entry.getValue()) {
                sb.append(val + " ");
            }
            logger.debug(sb.toString());
        }
    }
}
