package xmxrProject.genServer.common.utils;

import java.beans.IntrospectionException;
import java.util.Date;

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
            case "java.lang.Byte":          return (O) new Byte((byte)1);
            case "short":
            case "java.lang.Short":         return (O)new Short((short)1);
            case "int":
            case "java.lang.Integer":       return (O) new Integer(1);
            case "long":
            case "java.lang.Long":          return (O) new Long(1L);
            case "char":
            case "java.lang.Character":     return (O) new Character('\u0000');
            case "boolean":
            case "java.lang.Boolean":       return (O) new Boolean(false);
            case "float":
            case "java.lang.Float":         return (O) new Float(1.0);
            case "double":
            case "java.lang.Double":        return (O) new Double(1.0);
            default:                        return targetClass.newInstance();
        }
    }

}
