package xmxrProject.genServer;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xmxrProject.genServer.common.utils.C_StringUtil;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * 基础类
 */
public interface $XMXR extends Serializable {
    public static final long serialVersionUID = 333L;
    default String println() {
        Class cls = this.getClass();
        String className, fieldName, fieldString;
        int start, end;
        StringBuilder sb = new StringBuilder();
        try {
            Field[] fields = cls.getDeclaredFields();
            className = cls.getSimpleName();
            sb.append(className).append(": {");
            for (Field field : fields) {
                field.setAccessible(true);
                fieldName = field.getName();
                fieldString = field.get(this).toString();
                sb.append(fieldName).append(" = ").append(fieldString).append(" , ");
            }
            start = sb.length() - 3;
            end = sb.length();
            sb.delete(start, end);
            sb.append(" }");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    default String toJson() {
        return JSON.toJSONString(this);
    }

    default Object getField(String fieldName) {
        try {
            Class cls = this.getClass();
            Field field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(this);
        } catch (NoSuchFieldException e) {
            System.err.println(fieldName + "不存在");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.err.println(fieldName + "错误");
            e.printStackTrace();
        }
        return null;
    }

    default void setField(String fieldName, Object newField) {
        try {
            Class cls = this.getClass();
            Field field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(this, newField);
        } catch (NoSuchFieldException e) {
            System.err.println(fieldName + "不存在");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.err.println(fieldName + "错误");
            e.printStackTrace();
        }
    }
    default Logger getLogger(){
        return LoggerFactory.getLogger(getClass());
    }

    C_StringUtil stringUtil = new C_StringUtil();
}
