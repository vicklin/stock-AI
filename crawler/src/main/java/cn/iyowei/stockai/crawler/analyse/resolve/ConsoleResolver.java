package cn.iyowei.stockai.crawler.analyse.resolve;

/**
 * Created by liuguanglin on 15/10/4.
 */
public class ConsoleResolver implements Resolver {
    @Override
    public Object handle(String html, Object lastResult) {
        System.out.println("in ConsoleResolver");
        System.out.println("html:");
        System.out.println(html);
        System.out.println("lastResult:");
        System.out.println(lastResult);
        System.out.println("leave ConsoleResolver");
        return lastResult;
    }
}
