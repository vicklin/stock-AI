/**
 * 由于{@link net.sf.log4jdbc.RdbmsSpecifics}并不是public类，所以DataSourceSpyInterceptor只能放在同名包下
 */
package net.sf.log4jdbc;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.sql.Connection;

/**
 * 数据拦截器，用于打印sql
 *
 * @author - Vick.liu (vicklin123@gmail.com)
 */
public class DataSourceSpyInterceptor implements MethodInterceptor {

    private RdbmsSpecifics rdbmsSpecifics = null;

    private RdbmsSpecifics getRdbmsSpecifics(Connection conn) {
        if (rdbmsSpecifics == null) {
            rdbmsSpecifics = DriverSpy.getRdbmsSpecifics(conn);
        }
        return rdbmsSpecifics;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object result = invocation.proceed();
        if (SpyLogFactory.getSpyLogDelegator().isJdbcLoggingEnabled()) {
            if (result instanceof Connection) {
                Connection conn = (Connection) result;
                return new ConnectionSpy(conn, getRdbmsSpecifics(conn));
            }
        }
        return result;
    }

}