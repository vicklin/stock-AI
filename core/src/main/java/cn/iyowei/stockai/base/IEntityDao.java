package cn.iyowei.stockai.base;

import org.springframework.dao.DataAccessException;

import java.io.Serializable;
import java.util.List;

/**
 */
public interface IEntityDao<E, PK extends Serializable> {

    public E getById(PK id) throws DataAccessException;

    public List<E> findListByIds(List<PK> ids) throws DataAccessException;

    public void deleteById(PK id) throws DataAccessException;

    public void deleteById(PK id, boolean isDelDate) throws DataAccessException;

    /**
     * 插入数据
     */
    public void save(E entity) throws DataAccessException;

    /**
     * 更新数据
     */
    public void update(E entity) throws DataAccessException;

    /**
     * 根据id检查是否插入或是更新数据
     */
    public void saveOrUpdate(E entity) throws DataAccessException;

    public List<E> findAll() throws DataAccessException;

    /**
     * 批量保存数据
     */
    public void batchSave(List<E> entitys) throws DataAccessException;

    /**
     * 批量修改数据
     */
    public void batchUpdate(List<E> entitys) throws DataAccessException;

    /**
     * 批量删除数据
     */
    public void batchDelete(List<PK> ids) throws DataAccessException;

    /**
     * 批量删除数据, true为物理删除，false为逻辑删除
     */
    public void batchDelete(List<PK> ids, boolean isDelDate) throws DataAccessException;
}
