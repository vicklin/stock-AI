package cn.iyowei.stockai.util;

import cn.iyowei.stockai.util.bean.ObjectUtils;
import org.apache.commons.lang.enums.EnumUtils;

/**
 * 自定义型枚举工具
 *
 * @author vick
 */
public class ExEnumUtils extends EnumUtils {

    /**
     * 如果t是blank，则返回default Value,
     *
     * @param t
     * @param defaultValue
     * @return
     * @notice blank含义：
     * 参考{@link ObjectUtils}
     */
    public static <T> T defaultIfBlank(T t, T defaultValue) {
        if (ObjectUtils.isNotBlank(t)) {
            return t;
        }
        return defaultValue;
    }

    /**
     * 如果t是empty，则返回default Value,
     *
     * @param t
     * @param defaultValue
     * @return
     * @notice blank的意思参考StringUtils.isEmpty(t);
     */
    public static <T> T defaultIfEmpty(T t, T defaultValue) {
        if (ObjectUtils.isNotEmpty(t)) {
            return t;
        }
        return defaultValue;
    }

    /**
     * 判断t是否在repository中，如果t为null或者t不在repository则返回defaultValue
     *
     * @param t
     * @param defaultValue
     * @param repository
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T defaultIfNotExist(T t, T defaultValue, T... repository) {
        if (ObjectUtils.isBlank(t)) {
            return defaultValue;
        }
        for (T t2 : repository) {
            if (t.equals(t2)) {
                return t;
            }
        }
        return defaultValue;
    }

}
