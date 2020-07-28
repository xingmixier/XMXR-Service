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
    public static String get_RFC3339_DateString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date());
    }

    public static Date get_RFC3339_Date(String dateString) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(dateString);
    }
}
