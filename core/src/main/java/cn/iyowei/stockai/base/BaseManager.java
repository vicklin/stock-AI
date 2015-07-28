package cn.iyowei.stockai.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @author badqiu
 */
@Transactional
public abstract class BaseManager<E, PK extends Serializable> {

    protected Log log = LogFactory.getLog(getClass());

    protected abstract IEntityDao<E, PK> getEntityDao();

    @Transactional(readOnly = true)
    public E getById(PK id) throws DataAccessException {
        return getEntityDao().getById(id);
    }

    @Transactional(readOnly = true)
    public List<E> findListByIds(List<PK> ids) throws DataAccessException {
        return getEntityDao().findListByIds(ids);
    }

    @Transactional(readOnly = true)
    public List<E> findAll() throws DataAccessException {
        return getEntityDao().findAll();
    }

    /**
     * 根据id检查是否插入或是更新数据
     */
    public void saveOrUpdate(E entity) throws DataAccessException {
        getEntityDao().saveOrUpdate(entity);
    }

    /**
     * 插入数据
     */
    public void save(E entity) throws DataAccessException {
        getEntityDao().save(entity);
    }

    public void update(E entity) throws DataAccessException {
        getEntityDao().update(entity);
    }

    public void deleteById(PK id) throws DataAccessException {
        getEntityDao().deleteById(id);
    }

    /**
     * 删除数据, true为物理删除，false为逻辑删除
     */
    public void deleteById(PK id, boolean isDelDate) throws DataAccessException {
        getEntityDao().deleteById(id, isDelDate);
    }

    /**
     * 批量删除数据，逻辑删除
     */
    public void deleteByIds(List<PK> ids) throws DataAccessException {
        getEntityDao().batchDelete(ids);
    }

    /**
     * 批量删除数据, true为物理删除，false为逻辑删除
     */
    public void deleteByIds(List<PK> ids, boolean isDelDate) throws DataAccessException {
        getEntityDao().batchDelete(ids, isDelDate);
    }

    /**
     * 批量保存数据
     */
    public void batchSave(List<E> entitys) throws DataAccessException {
        getEntityDao().batchSave(entitys);
    }

    /**
     * 批量修改数据，不适用只有主键的关系表
     */
    public void batchUpdate(List<E> entitys) throws DataAccessException {
        getEntityDao().batchUpdate(entitys);
    }
}
