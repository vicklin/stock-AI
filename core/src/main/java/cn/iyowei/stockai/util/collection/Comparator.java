package cn.iyowei.stockai.util.collection;

/**
 * 对比两个实体类，如果相同则返回true，否则返回false（相当于另外equals方法）
 *
 * @param <T>
 * @author - Vick.liu (vicklin123@gmail.com)
 * @brief 供{@link cn.jsfund.stocktag.CollectionUtils.iclass.util.CollectionUtil}使用
 */
public interface Comparator<T> {
    boolean equals(T o1, T o2);
}
