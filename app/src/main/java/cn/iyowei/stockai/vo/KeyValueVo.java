package cn.iyowei.stockai.vo;

import java.io.Serializable;

/**
 * 用于返回固定key->value对象.
 *
 * @author Vick.liu (vicklin123@gmail.com)
 */
public class KeyValueVo implements Serializable {

    private static final long serialVersionUID = -3978999979612443689L;

    // -------fields-------
    private String key;
    private Object value;
    private String remark;

    // -------constructors-------

    public KeyValueVo() {
        super();
    }

    public KeyValueVo(String key) {
        super();
        this.key = key;
    }

    public KeyValueVo(String key, Object value) {
        super();
        this.key = key;
        this.value = value;
    }

    public KeyValueVo(String key, Object value, String remark) {
        super();
        this.key = key;
        this.value = value;
        this.remark = remark;
    }

    // -------setters & getters-------

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
