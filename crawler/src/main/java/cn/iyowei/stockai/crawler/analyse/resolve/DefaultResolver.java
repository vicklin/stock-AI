package cn.iyowei.stockai.crawler.analyse.resolve;

/**
 * Created by vick on 15-9-14.
 */
public class DefaultResolver implements Pipeline {

    @Override
    public Object handle(String html, Object lastResult) {
        System.out.println(html);
        System.out.println(lastResult);
        return "哈哈";
    }
}
