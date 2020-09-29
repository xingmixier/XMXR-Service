package xmxrProject.genServer;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xmxrProject.genServer.common.utils.C_StringUtil;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 基础类
 */
public interface $XMXR extends Serializable {
    long serialVersionUID = 333L;



    default String println() throws IllegalAccessException {
        Class cls = this.getClass();
        String className, fieldName, fieldString;
        int start, end;
        StringBuilder sb = new StringBuilder();
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
        return sb.toString();
    }

    default String toJson() {
        return JSON.toJSONString(this);
    }

    default Object c_get(String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Class cls = this.getClass();
        Field field = cls.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(this);
    }

    default void c_set(String fieldName, Object newField) throws NoSuchFieldException, IllegalAccessException {
        Class cls = this.getClass();
        Field field = cls.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(this, newField);
    }


}
