package cn.iyowei.stockai.web.resolver;

import cn.iyowei.stockai.web.annotation.SessionUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 自定义方法参数处理器，处理Controller层中带有@SessionUser注解的方法参数。
 * <p/>
 * 当方法中某个参数带有@SessionUser注解，则从request中获取tokenId并向Session系统获取用户信息，装配到参数对象中
 *
 * @author Vick.liu (vicklin123@gmail.com)
 * @version 1.0
 */
public class SessionUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 判断该Resolver适用，如果某个参数带有@SessionUser注解，则适用
     */
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(SessionUser.class)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 从request中获取tokenId并向Session系统获取用户信息，装配到参数对象中
     */
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//		SessionUser ann = parameter.getParameterAnnotation(SessionUser.class);
        String tokenId = StringUtils.trimToEmpty(webRequest.getParameter("tokenId"));
        return null;
    }


}
