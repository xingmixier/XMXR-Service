package xmxrProject.genServer.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class C_DateUtil {
    /**
     * RFC3339 时间格式 字符串获取
     *
     * @param date
     * @return
     */
    private static final String datePattens = "yyyy-MM-dd hh:mm:ss";

    public static String get_RFC3339_DateString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date());
    }

    public static Date get_RFC3339_Date(String dateString) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(dateString);
    }

    public static String  formatDate(Date date , String patten){
        return new SimpleDateFormat(patten).format(C_DateUtil.isNullTo0(date));
    }

    private static Date isNullTo0(Date date) {
        return date == null ? new Date(0):date;
    }

    public static String  formatDate(Date date ){
        return new SimpleDateFormat(datePattens).format(C_DateUtil.isNullTo0(date));
    }
}
