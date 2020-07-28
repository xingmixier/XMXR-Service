package xmxrProject.genServer.common.utils;

import xmxrProject.genServer.mods.usermod.entity.User;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @FileName: CSVUtil.java
 * @Author: 行米希尔
 * @Date: 2020/7/23 17:30
 * @Description:
 */
public class C_CSVUtil {

    public static String toCsvLine(Object obj) throws IllegalAccessException {
        Class cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        for(int i =0 ; i < fields.length  ; i++){
            Field field = fields[i];
            field.setAccessible(true);
            Object o =field.get(obj);
            String value = o.toString();
            value = reStr(value);
            sb.append("\"").append(value).append("\"");
            if(i<fields.length-1){
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static String toCSVText(List list) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        for(Object o : list){
            sb.append(toCsvLine(o)).append("\r\n");
        }
        return sb.toString();
    }

    public static String getCSVHead(Class cls){
        Field[] fields = cls.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < fields.length ; i++){
            Field field = fields[i];
            String value = field.getName();
            value = reStr(value);
            sb.append("\"").append(value).append("\"");
            if(i<fields.length-1){
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static String reStr(String str){
        return str.replace(",","，")
                .replace("\"","`^")
                .replace("\r","`r")
                .replace("\n","`n");
    }

    public static void main(String[] args) throws IllegalAccessException, IOException {
        StringBuilder sb = new StringBuilder();
        List<User> users = C_ListUtil.toArrayList(
          new User("1","zs","123"),
        new User("2","ls","456"),
        new User("3","ww","789")
        );
        sb.append(getCSVHead(User.class))
                .append(toCSVText(users));
        FileWriter fileWriter = new FileWriter("D:\\_TEST_FILES\\123.csv");
        fileWriter.write(sb.toString());
        fileWriter.close();
    }
}
