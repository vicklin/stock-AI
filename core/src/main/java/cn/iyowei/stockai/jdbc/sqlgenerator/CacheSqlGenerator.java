package cn.iyowei.stockai.jdbc.sqlgenerator;

import cn.iyowei.stockai.jdbc.sqlgenerator.metadata.Table;

public class CacheSqlGenerator implements SqlGenerator {
    private static final String NULL = new String();
    private SqlGenerator delegate;
    private String columnsSql = NULL;
    private String deleteByPkSql = NULL;
    private String insertSql = NULL;
    private String selectByPkSql = NULL;
    private String updateByPkSql = NULL;
    private String logicDeleteByPkSql = NULL;

    public CacheSqlGenerator(SqlGenerator delegate) {
        super();
        this.delegate = delegate;
    }

    public String getColumnsSql() {
        if (columnsSql == NULL) {
            columnsSql = delegate.getColumnsSql();
        }
        return columnsSql;
    }

    public String getDeleteByPkSql() {
        if (deleteByPkSql == NULL) {
            deleteByPkSql = delegate.getDeleteByPkSql();
        }
        return deleteByPkSql;
    }

    public String getInsertSql() {
        if (insertSql == NULL) {
            insertSql = delegate.getInsertSql();
        }
        return insertSql;
    }

    public String getSelectByPkSql() {
        if (selectByPkSql == NULL) {
            selectByPkSql = delegate.getSelectByPkSql();
        }
        return selectByPkSql;
    }

    @Override
    public String getCountSql() {
        return "select count(*) ";
    }

    public String getUpdateByPkSql() {
        if (updateByPkSql == NULL) {
            updateByPkSql = delegate.getUpdateByPkSql();
        }
        return updateByPkSql;
    }

    public Table getTable() {
        return delegate.getTable();
    }

    public String getColumnsSql(String columnPrefix) {
        return delegate.getColumnsSql(columnPrefix);
    }

    public String getLogicDeleteByPkSql() {
        if (logicDeleteByPkSql == NULL) {
            logicDeleteByPkSql = delegate.getLogicDeleteByPkSql();
        }
        return logicDeleteByPkSql;
    }

}
