package xmxrProject.genServer.common.utils;

/**
 * @Aether 行米希尔
 * @Date 2020/7/11 11:27
 */
public class C_StringBuilderUtil {
    /**
     * 替换字符串
     *
     * @param sb
     * @param oldString
     * @param newString
     */
    public static void replace(StringBuilder sb, String oldString, String newString) {
        int start = -1;
        int len = oldString.length();
        while ((start = sb.indexOf(oldString,start)) != -1) {
            sb.replace(start, start + len, newString);
        }
    }


}
