package cn.iyowei.stockai.util.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 重写org.springframework.beans.BeanUtils，过滤属性的null值
 * <p/>
 * 因为使用原来的org.springframework.beans.BeanUtils时，在进行修改操作中只需要对model中某一项进行修改，
 * 那么一般我们在页面上只提交model的ID及需要修改项的值，这个时候使用BeanUtils.copyProperties会将其他的null绑定到pojo中去，
 * 这是不符合需求的，所以对一些属性的null进行过滤
 *
 * @author hui 2014-12-29
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    private static final String METHOD_PREFIX_IS = "is";
    private static final String METHOD_PREFIX_GET = "get";
    /**
     * @see java.lang.reflect.Modifier#toString(int)
     */
    private static final int MODIFIER_PROTECTED = 4;
    protected static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    /**
     * Copy the property values of the given source bean into the target bean.
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the properties match. Any bean properties that the
     * source bean exposes but the target bean does not will silently be ignored.
     * <p>This is just a convenience method. For more complex transfer needs,
     * consider using a full BeanWrapper.
     *
     * @param source the source bean
     * @param target the target bean
     * @throws BeansException if the copying failed
     * @see BeanWrapper
     */
    public static void copyProperties(Object source, Object target) throws BeansException {
        copyProperties(source, target, null, (String[]) null);
    }

    /**
     * Copy the property values of the given source bean into the given target bean,
     * only setting properties defined in the given "editable" class (or interface).
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the properties match. Any bean properties that the
     * source bean exposes but the target bean does not will silently be ignored.
     * <p>This is just a convenience method. For more complex transfer needs,
     * consider using a full BeanWrapper.
     *
     * @param source   the source bean
     * @param target   the target bean
     * @param editable the class (or interface) to restrict property setting to
     * @throws BeansException if the copying failed
     * @see BeanWrapper
     */
    public static void copyProperties(Object source, Object target, Class<?> editable) throws BeansException {
        copyProperties(source, target, editable, (String[]) null);
    }

    /**
     * Copy the property values of the given source bean into the given target bean,
     * ignoring the given "ignoreProperties".
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the properties match. Any bean properties that the
     * source bean exposes but the target bean does not will silently be ignored.
     * <p>This is just a convenience method. For more complex transfer needs,
     * consider using a full BeanWrapper.
     *
     * @param source           the source bean
     * @param target           the target bean
     * @param ignoreProperties array of property names to ignore
     * @throws BeansException if the copying failed
     * @see BeanWrapper
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) throws BeansException {
        copyProperties(source, target, null, ignoreProperties);
    }

    /**
     * Copy the property values of the given source bean into the given target bean.
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the properties match. Any bean properties that the
     * source bean exposes but the target bean does not will silently be ignored.
     *
     * @param source           the source bean
     * @param target           the target bean
     * @param editable         the class (or interface) to restrict property setting to
     * @param ignoreProperties array of property names to ignore
     * @throws BeansException if the copying failed
     * @see BeanWrapper
     */
    private static void copyProperties(Object source, Object target, Class<?> editable, String... ignoreProperties)
            throws BeansException {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
                        "] not assignable to Editable class [" + editable.getName() + "]");
            }
            actualEditable = editable;
        }
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            // 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
                            if (value != null) {
                                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                    writeMethod.setAccessible(true);
                                }
                                writeMethod.invoke(target, value);
                            }
                        } catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }

    /**
     * 将对象转成map
     *
     * @param source
     * @param nullAble    是否允许属性为空，如果true，则属性为空的字段也会put到map中
     * @param ignoreProps
     * @return
     * @brief
     * @author - Vick.liu (vicklin123@gmail.com)
     */
    public static Map<String, Object> toMap(Object source, boolean nullAble, String... ignoreProps) {
        List<String> ignoreList = new ArrayList<String>(ignoreProps.length + 1);
        for (String prop : ignoreProps) {
            ignoreList.add(prop);
        }
        Map<String, Object> condition = new HashMap<String, Object>();
        Class<?> clazz = source.getClass();
        Field[] fields = null;
        List<Field> fieldList = new ArrayList<Field>();
        while (clazz != Object.class) {// 向上获取父类的属性
            fields = clazz.getDeclaredFields();
            fieldList.addAll(Arrays.asList(fields));
            clazz = clazz.getSuperclass();
        }
        fields = fieldList.toArray(new Field[fieldList.size()]);
        String property;
        Object value;
        Method getter;
        try {
            for (Field field : fields) {
                property = field.getName();
                if (!ignoreList.contains(property) && field.getModifiers() <= MODIFIER_PROTECTED) {// 判断是否需要忽略，判断是否是public:1/private:2/protected:4的（不是public不需要转）
                    getter = getReadMethod(source.getClass(), field);
                    value = getter.invoke(source);
                    if (nullAble || null != value) {// 如果不允许为空，则不放入map
                        condition.put(property, value);
                    }
                }
            }
        } catch (SecurityException e) {
            logger.error(e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage());
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage());
        }
        return condition;
    }

    /**
     * 将对象转成map， 默认不允许属性为空（null的属性不会put到map中）
     *
     * @param source
     * @param ignoreProps
     * @return
     * @brief
     * @author - Vick.liu (vicklin123@gmail.com)
     */
    public static Map<String, Object> toMap(Object source, String... ignoreProps) {
        return toMap(source, false, ignoreProps);
    }

    /**
     * 获取getter
     *
     * @param clazz
     * @param props
     * @return
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @brief
     * @author - Vick.liu (vicklin123@gmail.com)
     */
    private static Method getReadMethod(Class<?> clazz, Field field) throws SecurityException, NoSuchMethodException {
        String props = field.getName();
        if (field.getType().equals(boolean.class)) {
            return clazz.getMethod(METHOD_PREFIX_IS + props.substring(0, 1).toUpperCase() + props.substring(1,
                    props.length()));
        }
        return clazz.getMethod(METHOD_PREFIX_GET + props.substring(0, 1).toUpperCase() + props.substring(1,
                props.length()));
    }
}