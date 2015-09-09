package cn.iyowei.stockai.crawler.resolver;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by vick on 15-9-9.
 */
public class MultiTypeResolver implements HttpResponseResolver {

    public <T> T resolve(HttpResponse response, ResponseType type) throws IOException {
        T target;
        switch (type) {
            case JSON:
                target = (T) resolveJson(response);
                break;
            case JSONP:
                target = (T) resolveJsonP(response);
                break;
            case XML:
                target = (T) resolveXml(response);
                break;
            case HTML:
                target = (T) resolveHtml(response);
                break;
            case TEXT:
                target = (T) resolveText(response);
                break;
            default:
                target = null;
                break;
        }
        return target;
    }

    public String resolveJson(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity);
    }

    public String resolveJsonP(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        return jsonP2Json(EntityUtils.toString(entity));
    }

    public String resolveXml(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        return null;
    }

    public String resolveHtml(HttpResponse response) throws IOException {
        return null;
    }

    public String resolveText(HttpResponse response) throws IOException {
        return null;
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
