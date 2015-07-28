package cn.iyowei.stockai.util.bean;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * @author badqiu
 */
public class ObjectUtils {

    public static boolean isNullOrEmptyString(Object o) {
        if (o == null)
            return true;
        if (o instanceof String) {
            String str = (String) o;
            if (str.length() == 0)
                return true;
        }
        return false;
    }

    /**
     * 可以用于判断 Map,Collection,String,Array是否为空
     *
     * @param o
     * @return
     */
    @SuppressWarnings("all")
    public static boolean isEmpty(Object o) {
        if (o == null)
            return true;

        if (o instanceof String) {
            if (((String) o).length() == 0) {
                return true;
            }
        } else if (o instanceof Collection) {
            if (((Collection) o).isEmpty()) {
                return true;
            }
        } else if (o.getClass().isArray()) {
            if (Array.getLength(o) == 0) {
                return true;
            }
        } else if (o instanceof Map) {
            if (((Map) o).isEmpty()) {
                return true;
            }
        } else {
            return false;
        }

        return false;
    }

    /**
     * 可以用于判断 Map,Collection,String,Array是否不为空
     *
     * @param c
     * @return
     */
    public static boolean isNotEmpty(Object c) throws IllegalArgumentException {
        return !isEmpty(c);
    }

    /**
     * 可以用于判断 Map,Collection,String,Array是否不为空
     *
     * @param c
     * @return
     * @notice 增加string的blank判断功能
     */
    public static boolean isNotBlank(Object c) throws IllegalArgumentException {
        return !isBlank(c);
    }

    /**
     * 可以用于判断 Map,Collection,String,Array是否为空
     *
     * @param c
     * @return
     * @notice 增加string的blank判断功能
     */
    public static boolean isBlank(Object c) {
        if (isEmpty(c)) {
            return true;
        }

        if (c instanceof String) {
            String str = (String) c;
            int strLen = str.length();
            for (int i = 0; i < strLen; i++) {
                if ((Character.isWhitespace(str.charAt(i)) == false)) {
                    return false;
                }
            }
        }
        return false;
    }
}
