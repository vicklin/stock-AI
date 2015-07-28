package cn.iyowei.stockai.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Id;


/**
 * 实体的公共数据的父类
 */
@JsonIgnoreProperties({"version", "isDeleted"})
public class BaseEntity implements java.io.Serializable {

    /**
     * 版本号，默认为1
     */
    public static final Integer VERSION_DEFAULT_VALUE = 1;
    /**
     * 未删除
     */
    public static final Integer ISDELETED_NO_VALUE = 0;
    /**
     * 删除
     */
    public static final Integer ISDELETED_YES_VALUE = 1;
    protected static final String DATE_FORMAT = "yyyy-MM-dd";
    protected static final String TIME_FORMAT = "HH:mm:ss";
    protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
    private static final long serialVersionUID = -7200095849148417467L;
    /**
     * 主键，全局唯一 db_column: resourceid
     */
    private String resourceid;

    /**
     * 版本号 db_column: version, start from 1
     */
    private Integer version = VERSION_DEFAULT_VALUE;

    /**
     * 是否删除 0 未删除 1 删除 db_column: isDeleted,默认0
     */
    private Integer isDeleted = ISDELETED_NO_VALUE;

    @Id
    @Column(name = "resourceid")
    public String getResourceid() {
        return resourceid;
    }

    public void setResourceid(String resourceid) {
        this.resourceid = resourceid;
    }

    @Column(name = "version")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Column(name = "isDeleted")
    public Integer getIsDeleted() {
        if (isDeleted == null) {
            isDeleted = ISDELETED_NO_VALUE;
        }
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "BaseEntity [resourceid=" + resourceid + ", version=" + version
                + ", isDeleted=" + isDeleted + "]";
    }
}
