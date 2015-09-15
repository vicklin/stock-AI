package cn.iyowei.stockai.util.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class JsonUtils {

    protected static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String toString(Object bean) {
        try {
            return objectMapper.writeValueAsString(bean);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static <T> T toBean(String string, Class<T> beanClass) {
        try {
            return objectMapper.readValue(string, beanClass);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public static <T> T convert(Object value, Class<T> beanClass) {
        try {
            return objectMapper.convertValue(value, beanClass);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public static String jsStrToJsonStr(String str) {
        str = str.trim();
        int start = str.indexOf("=") + 1;
        char endChar = str.charAt(str.length() - 1);
        int end = str.length();
        if (';' == endChar) {
            end = end - 1;
        }
        str = str.substring(start, end);

        return str.replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", "")
                .replaceAll(":", "\":\"").replaceAll(",", "\",\"")
                .replaceAll("\\{", "\\{\"").replaceAll("}", "\"}")
                .replaceAll("\\[", "\\[\"").replaceAll("]", "\"]")
                .replaceAll("\"\"", "\"")
                .replaceAll("]\"", "]").replaceAll("\"\\[", "\\[")
                .replaceAll("}\"", "}").replaceAll("\"\\{", "\\{");
    }

    public static void main(String[] args) {
        String str = "var abc={a:1,b:'apple',c:[1,2,3]};";
        System.out.println(jsStrToJsonStr(str));
    }
}
