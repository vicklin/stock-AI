package cn.iyowei.stockai.util;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * sql工具
 *
 * @author - Vick.liu (vicklin123@gmail.com)
 */
public class SqlUtils {
    /**
     * 匹配sql中是否有sql关键字注入危险
     *
     * @param sql
     * @return
     */
    public static boolean hasInjection(String sql) {
        Pattern p = Pattern.compile("( update )+|( alter )+|( drop )+|( select )+|;+|(\')+|(\\\\)+");
        Matcher m = p.matcher(sql);
        return m.find();
    }

    /**
     * 返回 in (a,b,c...)
     *
     * @param values
     * @return
     */
    public static String buildSqlIn(Integer[] values) {
        StringBuilder querySql = new StringBuilder();
        String val = StringUtils.join(values, "");
        if (hasInjection(val)) {
            throw new IllegalArgumentException("values:[" + val + "] has SQL Injection risk while running buildSqlIn");
        }
        querySql.append(" in (");
        for (int i = 0, len = values.length - 1; i <= len; i++) {
            querySql.append(values[i]);
            if (i < len) {
                querySql.append(",");
            }
        }
        querySql.append(")");
        return querySql.toString();
    }

    /**
     * 返回 in ("a","b","c"...)
     *
     * @param values
     * @return
     */
    public static String buildSqlIn(String[] values) {
        StringBuilder querySql = new StringBuilder();
        String val = StringUtils.join(values, "");
        if (hasInjection(val)) {
            throw new IllegalArgumentException("values:[" + val + "] has SQL Injection risk while running buildSqlIn");
        }
        querySql.append(" in (");
        for (int i = 0, len = values.length - 1; i <= len; i++) {
            querySql.append("'").append(values[i]).append("'");
            if (i < len) {
                querySql.append(",");
            }
        }
        querySql.append(")");
        return querySql.toString();
    }
}
