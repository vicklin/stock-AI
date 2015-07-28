package cn.iyowei.stockai.config;

import cn.iyowei.stockai.annotation.KeyValue;

/**
 * key-value的Json数据别名的类型
 *
 * @author chenyonghui
 * @see KeyValue
 */
public enum AliasType {

    /**
     * {"key":"a", "value":"b"}
     */
    KEY_VALUE,

    /**
     * {"id":"a", "name":"b"}
     */
    ID_NAME,

    /**
     * {"a":"a","b":"b"}
     */
    REUSE
}
