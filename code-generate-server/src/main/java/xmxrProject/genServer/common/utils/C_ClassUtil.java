package xmxrProject.genServer.common.utils;

import xmxrProject.genServer.$XMXR;
import xmxrProject.genServer.mods.usermod.entity.User;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @FileName: C_ClassUtil.java
 * @Author: 行米希尔
 * @Date: 2020/7/24 15:10
 * @Description:
 */
public class C_ClassUtil implements $XMXR {








    /**
     * 复制 对象同名属性
     *  该方法会根据属性名称进行属性注入,不关注属性类型
     * @param sourceObj 源对象
     * @param targetObj 目标对象
     * @return
     */
    public static List<String> copyFieldsByFieldName(Object sourceObj, Object targetObj) throws IllegalAccessException, NoSuchFieldException {
        C_ObjectUtil.canNotNull(sourceObj,targetObj);
        List<String> noSuchFields = new ArrayList<>();
        String fieldName;
        Object value;
        for(Field sourceField : getAllFields(sourceObj)){
            fieldName = sourceField.getName();
            value = getFieldValue(sourceObj,fieldName);
            try {
                setFieldValue(targetObj,fieldName,value);
            }catch (NoSuchFieldException e){
                noSuchFields.add(fieldName);
            }
        }
        return noSuchFields;
    }

    /**
     * 根据属性名称获取属性
     * 如果子类存在 改名称属性 则返回该属性
     * 如果子类不存在 该名称属性 则去父类查找 直到找到 或 子类与所有父类都没有改名称属性
     * 没有找到返回null
     *
     * @param obj
     * @param fieldName
     * @return
     * @throws NoSuchFieldException
     */
    public static Field getField(Object obj, String fieldName ) throws NoSuchFieldException {
        C_ObjectUtil.canNotNull(obj,fieldName);
        Class cls = obj.getClass();
        Field field = null;
        while (cls != null) {
            try {
                field = cls.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
            }
            cls = cls.getSuperclass();
        }
        if (field == null) {
            throw new NoSuchFieldException(fieldName + "属性不存在");
        }
        return field;
    }

    /**
     *  获取对象属性值
     * @param obj   对象
     * @param fieldName 属性名称
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static <T> T getFieldValue(Object obj, String fieldName) throws IllegalAccessException, NoSuchFieldException {
        C_ObjectUtil.canNotNull(obj,fieldName);
        Object value = null;
        Field field = getField(obj, fieldName);
        field.setAccessible(true);
        value = field.get(obj);
        return (T) value;
    }

    /**
     * 设置属性值
     * @param obj 对象
     * @param fieldName 属性名称
     * @param value 属性值
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void setFieldValue(Object obj , String fieldName , Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = getField(obj,fieldName);
        field.setAccessible(true);
        field.set(obj,value);
    }


    public static List<Field> getFields(Object obj){
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(obj.getClass().getDeclaredFields())) ;
        return fields;
    }

    /**
     *  获取 对象 中的 一个方法
     * @param obj   对象
     * @param methodName 方法名
     * @param classes   参数类型
     * @return 方法对象
     * @throws NoSuchMethodException
     */
    public static Method getMethod(Object obj, String methodName,Class ...classes) throws NoSuchMethodException {
        C_ObjectUtil.canNotNull(obj,methodName);
        Method method = null;
        Class cls = obj.getClass();
        while (cls != null) {
            try {
                method = cls.getDeclaredMethod(methodName,classes);
            } catch (NoSuchMethodException e) {
            }
            cls = cls.getSuperclass();
        }
        if (method == null) {
            throw new NoSuchMethodException("方法名称 ："+methodName+" 参数类型："+Arrays.toString(classes)+" 的方法不存在");
        }
        return method;
    }

    /**
     *  执行 一个方法 方法
     * @param obj   对象
     * @param method    方法对象
     * @param args   参数
     * @param <T>
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <T>T invokeMethod(Object obj ,Method method , Object ... args) throws InvocationTargetException, IllegalAccessException {
        C_ObjectUtil.canNotNull(obj,method);
        Object value;
        method.setAccessible(true);
        value =  method.invoke(obj,args);
        return (T) value;
    }

    /**
     *  获取类所有属性 包括父类属性
     * @param obj   对象
     * @return
     */
    public static List<Field> getAllFields(Object obj){
        C_ObjectUtil.canNotNull(obj);
        List<Field> fields = new LinkedList<>();
        Class cls = obj.getClass();
        while (cls != null){
            Field [] fs = cls.getDeclaredFields();
            fields.addAll(Arrays.asList(fs));
            cls = cls.getSuperclass();
        }
        return fields;
    }

    /**
     *  获取类所有方法 包括父类方法
     * @param obj   对象
     * @return
     */
    public static List<Method> getAllMethods(Object obj){
        C_ObjectUtil.canNotNull(obj);
        List<Method> methods = new LinkedList<>();
        Class cls = obj.getClass();
        while (cls != null){
            Method [] ms = cls.getDeclaredMethods();
            methods.addAll(Arrays.asList(ms));
            cls = cls.getSuperclass();
        }
        return methods;
    }

    /**
     *  获取 类的 getter 方法
     * @param obj   对象
     * @param fieldName 属性名
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <T>T getFieldValue4GetMethod(Object obj , String fieldName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        C_ObjectUtil.canNotNull(fieldName,obj);
        String methodName = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1,fieldName.length());
        Method method =  getMethod(obj,methodName);
        return invokeMethod(obj,method);
    }

    /**
     *  获取类的 setter 方法
     * @param obj   对象
     * @param fieldName 属性名
     * @param objects   值
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void setFieldValue4SetMethod(Object obj , String fieldName,Object...objects) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        C_ObjectUtil.canNotNull(fieldName,obj);
        String methodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1,fieldName.length());
        Class [] classes = new Class[objects.length];
        for(int i = 0; i < objects.length; i ++){
            classes[i] = objects[i].getClass();
        }
        Method method =  getMethod(obj,methodName,classes);
        invokeMethod(obj,method,objects);
    }



}
