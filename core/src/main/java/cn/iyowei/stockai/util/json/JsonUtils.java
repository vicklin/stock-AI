package cn.iyowei.stockai.util.json;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提供一个Java对象转换Json数据格式，或从Json对象转换为Java数据类型(目前只提供到javabean和List)的工具类.
 * <p/>
 *
 * @author Marco.hu(hzg139@163.com)
 */
public class JsonUtils {

    protected static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static JsonBinder binder = JsonBinder.buildNonDefaultBinder();

    /**
     * List to json
     *
     * @param list
     * @return
     */
    public static String listToJson(List<?> list) {
        return binder.toJson(list);
    }

    /**
     * map to json
     *
     * @param map
     * @return
     */
    public static String mapToJson(Map<?, ?> map) {
        return binder.toJson(map);
    }

    /**
     * obj to json
     *
     * @param obj
     * @return
     */
    public static String objectToJson(Object obj) {
        return binder.toJson(obj);
    }

    /**
     * boolean to json
     *
     * @param bool
     * @return
     */
    public static String booleanToJson(Boolean bool) {
        return binder.toJson(bool);
    }

    /**
     * json to string list
     *
     * @param listString
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<String> jsonToStringList(String listString) {
        List<String> liststr = null;
        try {
            liststr = binder.getMapper().readValue(listString, List.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liststr;
    }

    /**
     * json to list obj
     *
     * @param beanStr
     * @return
     */
    public static List<Object> jsonToBean(String beanStr) {
        List<Object> listObj = null;
        try {
            listObj = binder.getMapper().readValue(beanStr, new TypeReference<List<Object>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listObj;
    }

    /**
     * json to map
     *
     * @param mapStr
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonToMap(String mapStr) {
        return binder.fromJson(mapStr, HashMap.class);
    }

    /**
     * json to bean
     *
     * @param <T>
     * @param jsonStr
     * @param clazz
     * @return
     */
    public static <T> T jsonToBean(String jsonStr, Class<T> clazz) {
        return binder.fromJson(jsonStr, clazz);
    }

    public static String jsStrToJsonStr(String str) {
        return str.replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", "")
                .replaceAll(":", "\":\"").replaceAll(",", "\",\"")
                .replaceAll("\\{", "\\{\"").replaceAll("}", "\"}")
                .replaceAll("\\[", "\\[\"").replaceAll("]", "\"]")
                .replaceAll("\"\"", "\"")
                .replaceAll("]\"", "]").replaceAll("\"\\[", "\\[")
                .replaceAll("}\"", "}").replaceAll("\"\\{", "\\{");
    }
}
