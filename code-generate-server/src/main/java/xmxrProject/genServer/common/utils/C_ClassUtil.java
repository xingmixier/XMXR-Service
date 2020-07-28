package xmxrProject.genServer.common.utils;

import xmxrProject.genServer.mods.usermod.entity.TestUser;
import xmxrProject.genServer.mods.usermod.entity.User;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: C_ClassUtil.java
 * @Author: 行米希尔
 * @Date: 2020/7/24 15:10
 * @Description:
 */
public class C_ClassUtil  {


    public static List<String> copyFields(Object sourceObj , Object targetObj )   {
        List<String> noSuchFields = new ArrayList<>();
        Field [] targetFields = targetObj.getClass().getDeclaredFields();
        Class sourceClass = sourceObj.getClass();
        String fieldName;
        Object value;
        Field sourceField;
        for(Field targetField : targetFields){
            targetField.setAccessible(true);
            fieldName = targetField.getName();
            try {
                sourceField = sourceClass.getDeclaredField(fieldName);
                sourceField.setAccessible(true);
                value = sourceField.get(sourceObj);
                targetField.set(targetObj,value);
            } catch (NoSuchFieldException e) {
                noSuchFields.add(fieldName);
            } catch (IllegalAccessException e) {
                noSuchFields.add(fieldName);
            }
        }
        if(noSuchFields.size() > 0){
            System.err.println("复制失败的属性名:"+noSuchFields+" 请检查名称与类型是否对应 ");
        }
        return noSuchFields;
    }

}
