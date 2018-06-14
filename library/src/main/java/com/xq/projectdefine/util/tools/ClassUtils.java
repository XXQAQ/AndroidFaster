package com.xq.projectdefine.util.tools;

import android.text.TextUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;

/**
 * 类工具
 *
 * @author mty
 * @date 2013-6-10下午8:00:46
 */
public class ClassUtils {

    public static <T> T newInstance(Class<T> claxx) throws Exception {
        Constructor<?>[] cons = claxx.getDeclaredConstructors();
        for (Constructor<?> c : cons) {
            Class[] cls = c.getParameterTypes();
            if (cls.length == 0) {
                c.setAccessible(true);
                return (T) c.newInstance();
            } else {
                Object[] objs = new Object[cls.length];
                for (int i = 0; i < cls.length; i++) {
                    objs[i] = getDefaultPrimiticeValue(cls[i]);
                }
                c.setAccessible(true);
                return (T) c.newInstance(objs);
            }
        }
        return null;
    }

    public static Object getFieldValue(final Object obj, final String fieldName) throws IllegalAccessException {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }

        Object result = null;

        result = field.get(obj);

        return result;
    }

    public static void setFieldValue(final Object obj, final String fieldName, final Object value) throws IllegalAccessException {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }

        field.set(obj, value);
    }

    public static Field getAccessibleField(final Object obj, final String fieldName) {
        if (obj != null && !TextUtils.isEmpty(fieldName)) {
            for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
                try {
                    Field field = superClass.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    return field;
                } catch (NoSuchFieldException e) {//NOSONAR
                    // Field不在当前类定义,继续向上转型
                    continue;// new add
                }
            }
        }
        return null;
    }

    public static Object invokeMethod(Object owner, String methodName, Object... args) throws Exception {

        Class<?> ownerClass = owner.getClass();

        Class<?>[] argsClass = new Class[args.length];

        if (args != null)
        {
            for (int i = 0, j = args.length; i < j; i++) {
                if (args[i].getClass() == Integer.class) { //一般的函数都是 int 而不是Integer
                    argsClass[i] = int.class;
                }
                else if (args[i].getClass() == Float.class) { //一般的函数都是 int 而不是Integer
                    argsClass[i] = float.class;
                }
                else if (args[i].getClass() == Double.class) { //一般的函数都是 int 而不是Integer
                    argsClass[i] = double.class;
                }
                else {
                    argsClass[i] = args[i].getClass();
                }
            }
        }

        Method method = ownerClass.getDeclaredMethod(methodName, argsClass);
        method.setAccessible(true);
        return method.invoke(owner, args);
    }

    public static Object invokeStaticMethod(Class ownerClass, String methodName, Object... args) throws Exception {

        Class<?>[] argsClass = new Class[args.length];

        if (args != null)
        {
            for (int i = 0, j = args.length; i < j; i++) {
                if (args[i].getClass() == Integer.class) { //一般的函数都是 int 而不是Integer
                    argsClass[i] = int.class;
                }
                else if (args[i].getClass() == Float.class) { //一般的函数都是 int 而不是Integer
                    argsClass[i] = float.class;
                }
                else if (args[i].getClass() == Double.class) { //一般的函数都是 int 而不是Integer
                    argsClass[i] = double.class;
                }
                else {
                    argsClass[i] = args[i].getClass();
                }
            }
        }

        Method method = ownerClass.getDeclaredMethod(methodName, argsClass);
        method.setAccessible(true);
        return method.invoke(null, args);
    }

    public static boolean isBaseDataType(Class<?> clazz) {
        return clazz.isPrimitive() || clazz.equals(String.class) || clazz.equals(Boolean.class)
                || clazz.equals(Integer.class) || clazz.equals(Long.class) || clazz.equals(Float.class)
                || clazz.equals(Double.class) || clazz.equals(Byte.class) || clazz.equals(Character.class)
                || clazz.equals(Short.class) || clazz.equals(Date.class) || clazz.equals(byte[].class)
                || clazz.equals(Byte[].class);
    }

    public static Object getDefaultPrimiticeValue(Class clazz) {
        if (clazz.isPrimitive()) {
            return clazz == boolean.class ? false : 0;
        }
        return null;
    }

    public static boolean isCollection(Class claxx) {
        return Collection.class.isAssignableFrom(claxx);
    }

    public static boolean isArray(Class claxx) {
        return claxx.isArray();
    }

}
