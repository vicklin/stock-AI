package cn.iyowei.stockai.annotation;

import cn.iyowei.stockai.config.AliasType;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KeyValue {

    String key();

    String value();

    AliasType alias() default AliasType.KEY_VALUE;
}
