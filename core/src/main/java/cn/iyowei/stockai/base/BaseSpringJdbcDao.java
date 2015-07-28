package cn.iyowei.stockai.base;

import cn.iyowei.stockai.jdbc.dialect.Dialect;
import cn.iyowei.stockai.jdbc.sqlgenerator.CacheSqlGenerator;
import cn.iyowei.stockai.jdbc.sqlgenerator.SpringNamedSqlGenerator;
import cn.iyowei.stockai.jdbc.sqlgenerator.SqlGenerator;
import cn.iyowei.stockai.jdbc.sqlgenerator.metadata.Column;
import cn.iyowei.stockai.jdbc.sqlgenerator.metadata.MetadataCreateUtils;
import cn.iyowei.stockai.jdbc.sqlgenerator.metadata.Table;
import cn.iyowei.stockai.jdbc.support.OffsetLimitResultSetExtractor;
import cn.iyowei.stockai.page.Page;
import cn.iyowei.stockai.page.PageQuery;
import cn.iyowei.stockai.util.SqlRemoveUtils;
import cn.iyowei.stockai.util.SqlUtils;
import cn.iyowei.stockai.util.bean.ObjectUtils;
import cn.iyowei.stockai.util.bean.PropertyUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.incrementer.AbstractSequenceMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.DB2SequenceMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Spring的JDBC基类
 *
 * @author badqiu
 */
public abstract class BaseSpringJdbcDao<E, PK extends Serializable> extends
        JdbcDaoSupport implements IEntityDao<E, PK> {

    static final String LIMIT_PLACEHOLDER = ":__limit";
    static final String OFFSET_PLACEHOLDER = ":__offset";
    protected final Log log = LogFactory.getLog(getClass());
    // protected JdbcTemplate jdbcTemplate;
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    // 根据table对象可以创建生成增删改查的sql的工具,在线参考:
    // http://code.google.com/p/rapid-framework/wiki/rapid_sqlgenerator
    Table table = MetadataCreateUtils.createTable(getEntityClass());
    SqlGenerator sqlGenerator = new CacheSqlGenerator(
            new SpringNamedSqlGenerator(table));
    // 用于分页的dialect,在线参考:
    // http://code.google.com/p/rapid-framework/wiki/rapid_dialect
    private Dialect dialect;

    public abstract Class<E> getEntityClass();

    public Dialect getDialect() {
        return dialect;
    }

    public void setDialect(Dialect d) {
        this.dialect = d;
    }

    protected void checkDaoConfig() {
        super.checkDaoConfig();
        if (dialect == null)
            throw new IllegalStateException(
                    "'dialect' property must be not null");
        log.info("use jdbc dialect:" + dialect);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate());
    }

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    public Object getIdentifierPropertyValue(Object entity) {
        try {
            return PropertyUtils.getProperty(entity,
                    getIdentifierPropertyName());
        } catch (Exception e) {
            throw new IllegalStateException(
                    "cannot get property value on entityClass:"
                            + entity.getClass() + " by propertyName:"
                            + getIdentifierPropertyName(), e);
        }
    }

    public void setIdentifierProperty(Object entity, Object id) {
        try {
            if (StringUtils.isBlank(BeanUtils.getProperty(entity, getIdentifierPropertyName()))) {
                BeanUtils.setProperty(entity, getIdentifierPropertyName(), id);
            }
        } catch (Exception e) {
            throw new IllegalStateException("cannot set property value:" + id
                    + " on entityClass:" + entity.getClass()
                    + " by propertyName:" + getIdentifierPropertyName(), e);
        }
    }

    /**
     * 得到全部数据,但执行分页
     *
     * @param pageQuery
     * @return
     */
    public Page<E> findAll(String tableName, final PageQuery pageQuery) {
        return findPageByCondition("select * from " + tableName
                + " /~ order by [sortColumns] ~/", pageQuery);
    }

    public Page<E> findPageByCondition(String querySql, PageQuery pageQuery) {
        return pageQuery(querySql, pageQuery, new BeanPropertyRowMapper<E>(
                getEntityClass()), "select count(*) ");
    }

    public Page<E> pageQuery(String querySql, PageQuery pageQuery,
                             RowMapper<?> rowMapper, String countQuerySelectPrefix) {
        String countQuery = countQuerySelectPrefix
                + SqlRemoveUtils.removeSelect(querySql);
        return pageQuery(querySql, countQuery, pageQuery, rowMapper);
    }

    public Page<E> pageQuery(String querySql, PageQuery pageQuery,
                             String countQueryPrefix, RowMapper<?> rowMapper) {
        String countQuery = countQueryPrefix
                + SqlRemoveUtils.removeSelect(querySql);
        return pageQuery(querySql, countQuery, pageQuery, rowMapper);
    }

    public Page<E> pageQuery(String querySql, String countQuery,
                             final PageQuery pageQuery, RowMapper<?> rowMapper) {
        final int totalCount = queryTotalCount(countQuery, pageQuery);
        int pageSize = pageQuery.getPageSize();
        int page = pageQuery.getPage();
        return pageQuery(querySql, PropertyUtils.describe(pageQuery), totalCount,
                pageSize, page, rowMapper);
    }

    public int queryTotalCount(String countSql, Object query) {
        final int totalCount = getNamedParameterJdbcTemplate().queryForObject(countSql,
                new BeanPropertySqlParameterSource(query), Integer.class);
        return totalCount;
    }

    private Page<E> pageQuery(String sql, Map<String, Object> map, final int totalCount,
                              int pageSize, int pageNumber, RowMapper<?> rowMapper) {
        if (totalCount <= 0) {
            return new Page<E>(pageNumber, pageSize, 0);
        }
        Page<E> page = new Page<E>(pageNumber, pageSize, totalCount);
        List<E> list = pageQuery(sql, map, page.getFirstResult(), pageSize, rowMapper);
        page.setResult(list);
        return page;
    }

    @SuppressWarnings("unchecked")
    public List<E> pageQuery(String sql, final Map<String, Object> paramMap, int startRow,
                             int pageSize, final RowMapper<?> rowMapper) {
        // 支持limit查询
        if (dialect.supportsLimit()) {
            paramMap.put(LIMIT_PLACEHOLDER.substring(1), pageSize);

            // 支持limit及offset.则完全使用数据库分页
            if (dialect.supportsLimitOffset()) {
                paramMap.put(OFFSET_PLACEHOLDER.substring(1), startRow);
                sql = dialect.getLimitString(sql, startRow, OFFSET_PLACEHOLDER,
                        pageSize, LIMIT_PLACEHOLDER);
                startRow = 0;
            } else {
                // 不支持offset,则在后面查询中使用游标配合limit分页
                sql = dialect.getLimitString(sql, 0, null, pageSize,
                        LIMIT_PLACEHOLDER);
            }

            pageSize = Integer.MAX_VALUE;
        }
        return (List<E>) getNamedParameterJdbcTemplate()
                .query(sql, paramMap, new OffsetLimitResultSetExtractor(startRow, pageSize, rowMapper));
    }

    // /// insert with start

    /**
     * 适用sqlserver,mysql 自动生成主键
     */
    protected void insertWithGeneratedKey(Object entity, String insertSql) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getNamedParameterJdbcTemplate().update(insertSql,
                new BeanPropertySqlParameterSource(entity), keyHolder);
        setIdentifierProperty(entity, keyHolder.getKey().longValue());
    }

    protected void insertWithIdentity(Object entity, String insertSql) {
        insertWithGeneratedKey(entity, insertSql);
    }

    protected void insertWithAutoIncrement(Object entity, String insertSql) {
        insertWithIdentity(entity, insertSql);
    }

    protected void insertWithSequence(Object entity,
                                      AbstractSequenceMaxValueIncrementer sequenceIncrementer,
                                      String insertSql) {
        Long id = sequenceIncrementer.nextLongValue();
        setIdentifierProperty(entity, id);
        getNamedParameterJdbcTemplate().update(insertSql,
                new BeanPropertySqlParameterSource(entity));
    }

    protected void insertWithDB2Sequence(Object entity, String sequenceName,
                                         String insertSql) {
        insertWithSequence(entity, new DB2SequenceMaxValueIncrementer(
                getDataSource(), sequenceName), insertSql);
    }

    protected void insertWithOracleSequence(Object entity, String sequenceName,
                                            String insertSql) {
        insertWithSequence(entity, new OracleSequenceMaxValueIncrementer(
                getDataSource(), sequenceName), insertSql);
    }

    protected void insertWithUUID(Object entity, String insertSql) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        setIdentifierProperty(entity, uuid);
        getNamedParameterJdbcTemplate().update(insertSql,
                new BeanPropertySqlParameterSource(entity));
    }

    /**
     * 手工分配ID插入
     *
     * @param entity
     * @param insertSql
     */
    protected void insertWithAssigned(Object entity, String insertSql) {
        getNamedParameterJdbcTemplate().update(insertSql,
                new BeanPropertySqlParameterSource(entity));
    }

    /**
     * @param @param entity
     * @param @param sql    设定文件
     * @return void    返回类型
     * @throws
     * @Title: batchSaveOrUpdate
     * @Description: 批量保存或更新
     */
    public void batchSaveOrUpdate(Object entity, String sql) {
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(entity));
    }

    /**
     * 批量保存
     *
     * @param entitys
     */
    public void batchSave(List<E> entitys) {
        if (CollectionUtils.isNotEmpty(entitys)) {
            for (E entity : entitys) {
                String uuid = UUID.randomUUID().toString().replace("-", "");
                setIdentifierProperty(entity, uuid);
            }
            SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(entitys.toArray());
            getNamedParameterJdbcTemplate().batchUpdate(getSqlGenerator().getInsertSql(), batch);
        }
    }

    /**
     * 批量更新
     *
     * @param entitys
     */
    public void batchUpdate(List<E> entitys) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(entitys.toArray());
        getNamedParameterJdbcTemplate().batchUpdate(getSqlGenerator().getUpdateByPkSql(), batch);
    }

    /**
     * 得到生成增删改查的sql生成工具
     *
     * @return
     */
    public SqlGenerator getSqlGenerator() {
        return sqlGenerator;
    }

    public E getById(PK id) {
        List<E> list = null;
        if (getSqlGenerator().getTable().getPrimaryKeyCount() > 1) {
            list = getNamedParameterJdbcTemplate().query(
                    getSqlGenerator().getSelectByPkSql(),
                    new BeanPropertySqlParameterSource(id),
                    new BeanPropertyRowMapper<E>(getEntityClass()));
        } else if (getSqlGenerator().getTable().getPrimaryKeyCount() == 1) {
            list = getJdbcTemplate().query(
                    getSqlGenerator().getSelectByPkSql(),
                    BeanPropertyRowMapper.newInstance(getEntityClass()), id);
        } else {
            throw new IllegalStateException("not found primary key on table:"
                    + getSqlGenerator().getTable().getTableName());
        }
        return DataAccessUtils.singleResult(list);
    }

    /**
     * 删除数据
     *
     * @param id
     * @param isDelDate true为物理删除，false为逻辑删除
     */
    public void deleteById(PK id, boolean isDelDate) {
        String deleteSql = isDelDate ? getSqlGenerator().getDeleteByPkSql() : getSqlGenerator().getLogicDeleteByPkSql();
        if (getSqlGenerator().getTable().getPrimaryKeyCount() > 1) {
            getNamedParameterJdbcTemplate().update(deleteSql, new BeanPropertySqlParameterSource(id));
        } else if (getSqlGenerator().getTable().getPrimaryKeyCount() == 1) {
            getJdbcTemplate().update(deleteSql, id);
        } else {
            throw new IllegalStateException("not found primary key on table:"
                    + getSqlGenerator().getTable().getTableName());
        }
    }

    /**
     * 逻辑删除数据
     *
     * @param id
     */
    public void deleteById(PK id) {
        deleteById(id, false);
    }

    public void save(E entity) throws DataAccessException {
        //insertWithIdentity(entity, getSqlGenerator().getInsertSql()); // for sqlserver and mysql
        insertWithUUID(entity, getSqlGenerator().getInsertSql()); //uuid
    }

    public void saveOrUpdate(E entity) {
        Object id = getIdentifierPropertyValue(entity);
        if (ObjectUtils.isNullOrEmptyString(id)) {
            save(entity);
        } else {
            update(entity);
        }
    }

    public void update(E entity) {
        String sql = getSqlGenerator().getUpdateByPkSql();
        update(sql, entity);
    }

    public void update(String sql, E entity) {
        getNamedParameterJdbcTemplate().update(sql,
                new BeanPropertySqlParameterSource(entity));
    }

    public List<E> findAll() {
        String sql = "SELECT " + getSqlGenerator().getColumnsSql() + " FROM "
                + getSqlGenerator().getTable().getTableName();
        return (List<E>) getJdbcTemplate().query(sql,
                BeanPropertyRowMapper.newInstance(getEntityClass()));
    }

    public String getIdentifierPropertyName() {
        List<Column> primaryKeyColumns = getSqlGenerator().getTable()
                .getPrimaryKeyColumns();
        if (primaryKeyColumns.isEmpty()) {
            throw new IllegalStateException("not found primary key on table:"
                    + getSqlGenerator().getTable().getTableName());
        }
        return primaryKeyColumns.get(0).getPropertyName();
    }

    /**
     * 通过sql与查询条件，获取数据列表
     *
     * @param querySql
     * @param query
     * @return
     */
    public List<E> findListByCondition(String querySql, BaseQuery query) {
        return getNamedParameterJdbcTemplate().query(querySql, PropertyUtils.describe(query),
                BeanPropertyRowMapper.newInstance(getEntityClass()));
    }

    /**
     * 获取第一个实体对象
     *
     * @param list
     * @return
     */
    public E singleResult(List<E> list) {
        return DataAccessUtils.singleResult(list);
    }

    /**
     * 根据主键ids，获取数据列表
     * <p>通过in("id1","id2")实现
     *
     * @param ids
     * @return
     */
    public List<E> findListByIds(List<PK> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<E>();
        }
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(getSqlGenerator().getColumnsSql("t")).append(" from ")
                .append(getSqlGenerator().getTable().getTableName()).append(" t where t.resourceid ");
        if (ids.get(0) instanceof Integer) {
            sql.append(SqlUtils.buildSqlIn(ids.toArray(new Integer[]{})));
        } else {
            sql.append(SqlUtils.buildSqlIn(ids.toArray(new String[]{})));
        }
        return findListByCondition(sql.toString(), new BaseQuery());
    }

    /**
     * 根据主键ids，批量删除
     *
     * @param ids
     * @param isDelDate true为物理删除，false为逻辑删除
     */
    public void batchDelete(List<PK> ids, boolean isDelDate) {
        String deleteSql = isDelDate ? getSqlGenerator().getDeleteByPkSql() : getSqlGenerator().getLogicDeleteByPkSql();
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(ids.toArray());
        if (getSqlGenerator().getTable().getPrimaryKeyCount() > 1) {
            getNamedParameterJdbcTemplate().batchUpdate(deleteSql, batch);
        } else if (getSqlGenerator().getTable().getPrimaryKeyCount() == 1) {
            List<Object[]> params = new ArrayList<Object[]>();
            for (PK pk : ids) {
                params.add(new Object[]{pk});
            }
            getJdbcTemplate().batchUpdate(deleteSql, params);
        } else {
            throw new IllegalStateException("not found primary key on table:"
                    + getSqlGenerator().getTable().getTableName());
        }
    }

    /**
     * 根据主键ids，批量逻辑删除
     *
     * @param ids
     */
    public void batchDelete(List<PK> ids) {
        batchDelete(ids, false);
    }

}
