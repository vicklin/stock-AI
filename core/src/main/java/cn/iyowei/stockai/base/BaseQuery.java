package cn.iyowei.stockai.base;

import cn.iyowei.stockai.page.PageQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseQuery extends PageQuery implements java.io.Serializable {

    public static final int DEFAULT_PAGE_SIZE = 100;
    /**
     * 未删除
     */
    public static final Integer ISDELETED_NO_VALUE = 0;
    /**
     * 删除
     */
    public static final Integer ISDELETED_YES_VALUE = 1;
    /**
     * 返回数据类型标识，1为返回key-value形式的数据
     */
    public static final Integer DATATYPE_KEY_VALUE = 1;
    /**
     * 返回数据类型标识，2为返回简单的数据，单次查询，没有关联查询
     */
    public static final Integer DATATYPE_SIMPLE = 2;
    private static final long serialVersionUID = -360860474471966681L;
    protected static Logger logger = LoggerFactory.getLogger(BaseQuery.class);

    static {
        System.out.println("BaseQuery.DEFAULT_PAGE_SIZE=" + DEFAULT_PAGE_SIZE);
    }

    /**
     * 主键，全局唯一 db_column: resourceid
     */
    private String resourceid;

    /**
     * 版本号 db_column: version, start from 1
     */
    private Integer version;

    /**
     * 是否删除 0 未删除 1 删除 db_column: isDeleted,默认0
     */
    private Integer isDeleted = ISDELETED_NO_VALUE;

    private Boolean allowEmpty;

    /**
     * 用于模糊查询名字
     */
    private String fuzzyName;

    /**
     * 返回数据类型，1为返回key-value形式的数据，其他为完整数据
     */
    private Integer dataType;

    public BaseQuery() {
        setPageSize(DEFAULT_PAGE_SIZE);
    }

    public String getResourceid() {
        return resourceid;
    }

    public void setResourceid(String resourceid) {
        this.resourceid = resourceid;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getIsDeleted() {
        if (isDeleted == null) {
            isDeleted = ISDELETED_NO_VALUE;
        }
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getAllowEmpty() {
        if (allowEmpty == null) {
            return false;
        }
        return allowEmpty;
    }

    public void setAllowEmpty(Boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }

    public String getFuzzyName() {
        return this.fuzzyName;
    }

    public void setFuzzyName(String fuzzyName) {
        this.fuzzyName = fuzzyName;
    }

    public String getFuzzyQueryName() {
        return "%" + this.fuzzyName + "%";
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }
}
