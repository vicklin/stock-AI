package cn.iyowei.stockai.util.collection;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Collection工具
 *
 * @author - Vick.liu (vicklin123@gmail.com)
 */
public class CollectionUtils extends org.apache.commons.collections.CollectionUtils {

    /**
     * 把collection转换成string，每个值中间加上指定的分隔符
     * eg.list:["abc","edf","dddd"]--->abc|edf|dddd
     *
     * @param collection
     * @param separator  分隔符
     * @param adaptor    获取值的适配器
     * @return
     * @author - Vick.liu (vicklin123@gmail.com)
     */
    public static <T, E> String collectionToString(Collection<T> collection, String separator,
                                                   CollectionAdaptor<T, E> adaptor) {
        StringBuffer sb = new StringBuffer();
        Iterator<T> it = collection.iterator();
        int count = 1, length = collection.size();
        while (it.hasNext()) {
            sb.append(adaptor.getValue(it.next()).toString());
            if (count < length) {
                sb.append(separator);
            }
            count++;
        }
        return sb.toString();
    }

    /**
     * 筛选两个Collection，去除其相同的部分(comparator.equals返回true)
     *
     * @param firstCollection
     * @param secondCollection
     * @param comparator
     * @return 相同部分Collection（实例来自firstCollection）
     * @author - Vick.liu (vicklin123@gmail.com)
     */
    public static <T> Collection<T> screen(Collection<T> firstCollection, Collection<T> secondCollection,
                                           cn.iyowei.stockai.util.collection.Comparator<T> comparator) {
        Collection<T> duplicateCollection = searchDuplicate(firstCollection, secondCollection, comparator);
        removeAll(firstCollection, duplicateCollection, comparator);
        removeAll(secondCollection, duplicateCollection, comparator);
        return duplicateCollection;
    }

    /**
     * 筛选两个Collection，去除其相同的部分(T.equals返回true)
     *
     * @param c1
     * @param c2
     * @return
     * @brief 使用此方法需要equals方法正确检测两个对象是否相同。如果不重写原有equals方法，可采用装饰者模式为其加上新的hash和equals方法
     * @author - Vick.liu (vicklin123@gmail.com)
     */
    public static <T> Collection<T> screen(Collection<T> c1, Collection<T> c2) {
        Collection<T> duplicateCollection = getNewInstance(c1);
        duplicateCollection.addAll(c1);
        c1.removeAll(c2);
        duplicateCollection.removeAll(c1);
        c2.removeAll(duplicateCollection);
        return duplicateCollection;
    }

    /**
     * 查找两个Collection中相同的部分
     *
     * @param firstCollection
     * @param secondCollection
     * @param comparator
     * @return firstCollection中相同部分
     * <p/>
     * 这意味这可以使用firstCollection.removeAll(duplicateCollection) 但是<strong>不能<
     * /strong>使用secondCollection.removeAll(duplicateCollection )，因为引用仅来自firstCollection.
     * <p/>
     * 需要删除secondCollection中重复部分可调用 {@link #removeAll(Collection, Collection, cn.iyowei.stockai.util.collection.Comparator)}
     * @author - Vick.liu (vicklin123@gmail.com)
     */
    public static <T> Collection<T> searchDuplicate(Collection<T> firstCollection, Collection<T> secondCollection,
                                                    cn.iyowei.stockai.util.collection.Comparator<T> comparator) {
        if (null == firstCollection || null == secondCollection) {
            throw new IllegalArgumentException(
                    "Argument can not be null: firstCollection=" + firstCollection + ";secondCollection=" + secondCollection);
        }
        Collection<T> duplicateCollection = getNewInstance(firstCollection);
        for (T o1 : firstCollection) {
            for (T o2 : secondCollection) {
                if (comparator.equals(o1, o2)) {
                    if (!contains(duplicateCollection, o1, comparator)) {// 防止重复添加
                        duplicateCollection.add(o1);
                    }
                    break;
                }
            }
        }
        return duplicateCollection;
    }

    /**
     * 从srcCollection中删除所有removeCollection（comparator.equals返回true则删除）
     *
     * @param srcCollection
     * @param removeCollection
     * @param comparator
     * @return
     * @author - Vick.liu (vicklin123@gmail.com)
     */
    public static <T> Collection<T> removeAll(Collection<T> srcCollection, Collection<T> removeCollection,
                                              cn.iyowei.stockai.util.collection.Comparator<T> comparator) {
        for (Iterator<T> removeIterator = removeCollection.iterator(); removeIterator.hasNext(); ) {
            remove(srcCollection, removeIterator.next(), comparator);
        }
        return srcCollection;
    }

    /**
     * 从srcCollection中删除所有removeT（comparator.equals返回true则删除）
     *
     * @param srcCollection
     * @param removeT
     * @param comparator
     * @return
     * @author - Vick.liu (vicklin123@gmail.com)
     */
    public static <T> Collection<T> remove(Collection<T> srcCollection, T removeT, cn.iyowei.stockai.util.collection.Comparator<T> comparator) {
        for (Iterator<T> srcIterator = srcCollection.iterator(); srcIterator.hasNext(); ) {
            if (comparator.equals(srcIterator.next(), removeT)) {
                srcIterator.remove();
            }
        }
        return srcCollection;
    }

    /**
     * 查collection中是否包含t
     *
     * @param collection
     * @param t
     * @param comparator
     * @return
     * @author - Vick.liu (vicklin123@gmail.com)
     */
    public static <T> boolean contains(Collection<T> collection, T t, cn.iyowei.stockai.util.collection.Comparator<T> comparator) {
        for (T t2 : collection) {
            if (comparator.equals(t, t2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将collection-T转换collection-E成所要的东西
     *
     * @param collection
     * @param adaptor
     * @return
     * @author - Vick.liu (vicklin123@gmail.com)
     */
    @SuppressWarnings({
            "unchecked", "rawtypes"})
    public static <T, E> Collection<E> convert(Collection<T> collection, CollectionAdaptor<T, E> adaptor) {
        Collection c = null;
        c = getNewInstance(collection);
        for (T t : collection) {
            c.add(adaptor.getValue(t));
        }
        return c;
    }

    // ---------------private method ------------------------//
    /*
     * 获取collection的实例化，（可能抛出运行时异常）
	 * @param c
	 * @return
	 * @author  - Vick.liu (vicklin123@gmail.com)
	 */
    @SuppressWarnings("unchecked")
    private static <T> Collection<T> getNewInstance(Collection<T> c) {
        Collection<T> duplicateCollection = null;
        try {
            duplicateCollection = c.getClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Collection can not be instance:" + c + "\n" + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Collection can not be access:" + c + "\n" + e.getMessage());
        }
        return duplicateCollection;
    }


    /**
     * 根据对象列表和对象的某个属性返回属性的List集合
     *
     * @param objList      对象列表
     * @param propertyName 要操作的属性名称
     * @return <pre>
     * 	指定属性的List集合;
     * 	如果objList为null或者size等于0抛出 IllegalArgumentException异常;
     *  如果propertyName为null抛出 IllegalArgumentException异常
     * </pre>
     */
    public static <T, PK> List<PK> getPropertyList(List<T> objList, String propertyName) {
        if (objList == null)
            throw new IllegalArgumentException("objList must not be null");
        if (propertyName == null || "".equals(propertyName)) {
            throw new IllegalArgumentException("No propertyName specified for bean class '" + objList.get(0).getClass() + "'");
        }
        PropertyUtilsBean p = new PropertyUtilsBean();
        List<PK> propList = new LinkedList<PK>();
        for (int i = 0; i < objList.size(); i++) {
            T obj = objList.get(i);
            try {
                propList.add((PK) p.getProperty(obj, propertyName));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return propList;
    }

    /**
     * 将List列表中的对象的某个属性封装成一个Map对象，key值是属性名，value值是对象列表中对象属性值的列表
     *
     * @param objList      对象列表
     * @param propertyName 属性名称,可以是一个或者多个
     * @return 返回封装了属性名称和属性值列表的Map对象，如果参数非法则抛出异常信息
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public static <T, PK> Map<String, List<PK>> getPropertiesMap(List<T> objList, String... propertyName) {
        if (objList == null)
            throw new IllegalArgumentException("objList must not be null");
        if (propertyName == null || propertyName.length == 0) {
            throw new IllegalArgumentException("No propertyName specified for bean class '" + objList.get(0).getClass() + "'");
        }
        Map<String, List<PK>> maps = new HashMap<String, List<PK>>();
        for (int i = 0; i < propertyName.length; i++) {
            maps.put(propertyName[i], (List<PK>) getPropertyList(objList, propertyName[i]));
        }
        return maps;
    }

}
