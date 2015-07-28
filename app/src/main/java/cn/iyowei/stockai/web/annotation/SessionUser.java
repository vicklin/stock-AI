package cn.iyowei.stockai.web.annotation;

import cn.iyowei.stockai.web.resolver.SessionUserMethodArgumentResolver;

import java.lang.annotation.*;

/**
 * 标注是否是从Session中获取的用户信息，详见{@link SessionUserMethodArgumentResolver}
 *
 * @author Vick.liu (vicklin123@gmail.com)
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SessionUser {
}
