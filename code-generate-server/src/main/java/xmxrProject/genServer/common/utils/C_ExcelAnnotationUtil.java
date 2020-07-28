package xmxrProject.genServer.common.utils;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @FileName: C_ExcelAnnotationUtil.java
 * @Author: 行米希尔
 * @Date: 2020/7/16 9:00
 * @Description:
 */
public class C_ExcelAnnotationUtil {


    public static <E> List<E> getDataList(String fileName ,InputStream is , Class<E> cls){
        return getDataList(fileName,is,cls,0,1,null,null);
    }

    public static <E> List<E> getDataList(String fileName ,InputStream is , Class<E> cls , Integer headRowIndex , Integer bodyRowStartIndex ,Integer bodyRowEndIndex , Integer [] noBodyRowIndex){
        List<E> dataList = new ArrayList<>();

        C_ExcelUtil ceu  = new C_ExcelUtil(fileName,is);
        ceu.setRowField(headRowIndex,bodyRowStartIndex,bodyRowEndIndex,noBodyRowIndex);


        Map<String,Field> titleFieldMap = new HashMap<>();
        Field[] fields = cls.getDeclaredFields();
        for(Field f : fields){
            C_Excel c_excel = f.getDeclaredAnnotation(C_Excel.class);
            if(c_excel != null && !C_StringUtil.isBlank(c_excel.title())){

            }
        }
        Object [] [] fieldList = new Object[fields.length][2];

        return dataList;
    }
}
