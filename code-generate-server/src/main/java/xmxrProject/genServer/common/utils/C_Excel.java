package xmxrProject.genServer.common.utils;

import org.apache.poi.ss.usermodel.CellType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @FileName: C_Excel.java
 * @Author: 行米希尔
 * @Date: 2020/7/15 22:41
 * @Description:
 */
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface C_Excel {

    /**
     * 表头
     * @return
     */
    String title ();

    /**
     * 合并行数
     * @return
     */
    int rowSpan () default 1;

    /**
     * 合并列数
     * @return
     */
    int colSpan () default 1;

    /**
     * 单元格类型
     * @return
     */
    CellType cellType () default CellType.STRING;

    /**
     * 是否为日期
     * @return
     */
    boolean isDate () default  false;

    /**
     * 日期格式
     * @return
     */
    String dateFormat () default  "yyyy/MM/dd";
}
