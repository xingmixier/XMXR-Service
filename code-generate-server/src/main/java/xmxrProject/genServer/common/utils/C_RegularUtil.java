package xmxrProject.genServer.common.utils;

/**
 * @FileName: C_RegularUtil.java
 * @Author: 行米希尔
 * @Date: 2020/7/31 10:58
 * @Description:
 */
public class C_RegularUtil {

    /**
     * 判断 字符串 是否是一个 数字
     * @param string
     * @return
     */
    public boolean isNumber(String string){
        return string.matches("\"-?[0-9]+.？[0-9]*\"");
    }

    public boolean isChineseText(String string){
        return string.matches("^[\\u4e00-\\u9fa5]$");
    }

    public boolean isBlank(String string){
        return string.matches("\\n\\s*\\r");
    }

}
