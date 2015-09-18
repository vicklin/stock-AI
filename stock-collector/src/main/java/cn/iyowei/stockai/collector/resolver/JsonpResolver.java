package cn.iyowei.stockai.collector.resolver;

import cn.iyowei.stockai.crawler.analyse.resolve.Resolver;

/**
 * 把jsonp的返回转换成json格式string
 * Created by vick on 15-9-17.
 */
public class JsonpResolver implements Resolver {

    @Override
    public Object handle(String html, Object lastResult) {
        String json = jsonP2Json(html);
        return json;
    }

    private String jsonP2Json(String str) {
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
}