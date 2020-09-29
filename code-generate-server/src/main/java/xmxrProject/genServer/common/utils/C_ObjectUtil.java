package xmxrProject.genServer.common.utils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Objects;

/**
 * @FileName: C_ObjectUtil.java
 * @Author: 行米希尔
 * @Date: 2020/7/27 15:05
 * @Description:
 */
public class C_ObjectUtil {



    public static <O>O toNotNull(O target,O defaultValue){
        return target == null ? defaultValue : target;
    }
    public static <O>O toNotNull(O target,Class<O> targetClass) throws IllegalAccessException, InstantiationException {
        if(target != null){
            return target;
        }
        switch (targetClass.getTypeName()){
            case "byte":
            case "java.lang.Byte":          return (O) Byte.valueOf((byte) 1);
            case "short":
            case "java.lang.Short":         return (O) Short.valueOf((short) 1);
            case "int":
            case "java.lang.Integer":       return (O) Integer.valueOf(1);
            case "long":
            case "java.lang.Long":          return (O) Long.valueOf(1L);
            case "char":
            case "java.lang.Character":     return (O) Character.valueOf('\u0000');
            case "boolean":
            case "java.lang.Boolean":       return (O) Boolean.FALSE;
            case "float":
            case "java.lang.Float":         return (O) Float.valueOf(1.0f);
            case "double":
            case "java.lang.Double":        return (O) Double.valueOf(1.0);
            default:                        return targetClass.newInstance();
        }
    }

    public static void canNotNull(Object... objects){
        for(Object obj : objects){
            Objects.requireNonNull(obj);
        }
    }




}
