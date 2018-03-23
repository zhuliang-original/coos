package top.coos.tool.field;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class FieldTool {
    public static boolean isWrapClass(Class clz) {
        try {
            return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    public static Object convertBean2Bean(Object from, Object to) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(to.getClass());
            PropertyDescriptor[] ps = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor p : ps) {
                Method getMethod = p.getReadMethod();
                Method setMethod = p.getWriteMethod();
                if (getMethod != null && setMethod != null) {
                    try {
                        Object result = getMethod.invoke(from);
                        setMethod.invoke(to, result);
                    } catch (Exception e) {
                        // 如果from没有此属性的get方法，跳过
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return to;
    }

    /**
     * 不支持to继承(to是其他bean的子类)
     */
    @SuppressWarnings("unchecked")
    public static Object coverBean2Bean(Object from, Object to) {
        Class fClass = from.getClass();
        Class tClass = to.getClass();
        // 拿to所有属性（如果有继承，父类属性拿不到）
        Field[] cFields = tClass.getDeclaredFields();
        try {
            for (Field field : cFields) {
                String cKey = field.getName();
                // 确定第一个字母大写
                cKey = cKey.substring(0, 1).toUpperCase() + cKey.substring(1);

                Method fMethod;
                Object fValue;
                try {
                    fMethod = fClass.getMethod("get" + cKey);// public方法
                    fValue = fMethod.invoke(from);// 取getfKey的值
                } catch (Exception e) {
                    // 如果from没有此属性的get方法，跳过
                    continue;
                }

                try {
                    // 用fMethod.getReturnType()，而不用fValue.getClass()
                    // 为了保证get方法时，参数类型是基本类型而传入对象时会找不到方法
                    Method cMethod = tClass.getMethod("set" + cKey, fMethod.getReturnType());
                    cMethod.invoke(to, fValue);
                } catch (Exception e) {
                    // 如果to没有此属性set方法，跳过
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return to;
    }

    // public static void setModelValue(SupportModel model, String name, Object
    // value) throws Exception {
    //
    // List<Field> fields = getClassFields(model.getClass());
    // for (Field field : fields) {
    // if (name.equals(field.getName())) {
    // field.setAccessible(true);
    // if (field.getType().equals(List.class)) {
    //
    // } else if (field.getType().getSuperclass() != null
    // && field.getType().getSuperclass().equals(SupportModel.class)) {
    // Object o = field.getType().newInstance();
    // System.out.println(o.getClass());
    // fullToSupportModel((SupportModel) o, JSONObject.fromObject(value));
    // field.set(model, o);
    // } else {
    // field.set(model, value);
    // }
    // }
    // }
    // }

    /**
     * 获取类实例的属性值
     * 
     * @param clazz
     *            类名
     * @param includeParentClass
     *            是否包括父类的属性值
     * @return 类名.属性名=属性类型
     */
    public static List<Field> getClassFields(Class clazz) {
        List<Field> fs = new ArrayList<Field>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            fs.add(field);
        }
        if (clazz.getSuperclass() != Object.class)
            getParentClassFields(fs, clazz.getSuperclass());
        return fs;
    }

    /**
     * 获取类实例的父类的属性值
     * 
     * @param map
     *            类实例的属性值Map
     * @param clazz
     *            类名
     * @return 类名.属性名=属性类型
     */
    private static List<Field> getParentClassFields(List<Field> fs, Class clazz) {
        if (clazz == null) {
            return fs;
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            boolean has = false;
            for (Field f : fs) {
                if (f.getName().toUpperCase().equals(field.getName().toUpperCase())) {
                    has = true;
                }
            }
            if (!has) {
                fs.add(field);
            }
        }
        if (clazz.getSuperclass() == Object.class) {
            return fs;
        }
        getParentClassFields(fs, clazz.getSuperclass());
        return fs;
    }

    /**
     * 获取类实例的方法
     * 
     * @param clazz
     *            类名
     * @param includeParentClass
     *            是否包括父类的方法
     * @return List
     */
    public static List<Method> getMothds(Class clazz) {
        List<Method> list = new ArrayList<Method>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            list.add(method);
        }
        if (clazz.getSuperclass() != null) {
            getParentClassMothds(list, clazz.getSuperclass());
        }
        return list;
    }

    /**
     * 获取类实例的父类的方法
     * 
     * @param list
     *            类实例的方法List
     * @param clazz
     *            类名
     * @return List
     */
    private static List<Method> getParentClassMothds(List<Method> list, Class clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            list.add(method);
        }
        if (clazz.getSuperclass() == Object.class) {
            return list;
        }
        getParentClassMothds(list, clazz.getSuperclass());
        return list;
    }
}
