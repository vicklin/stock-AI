package cn.iyowei.stockai.base;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.io.Serializable;

/**
 * redis
 */
public abstract class BaseRedisDao<E, PK extends Serializable> extends
        JdbcDaoSupport implements IEntityDao<E, PK> {


}
