package xmxrProject.genServer.common.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Aether 行米希尔
 * @Date 2020/7/11 11:30
 */
public class C_StringUtil {

    /**
     * 格式化字符串 去除前方换行 与后方空格 和 换行
     *
     * @param string
     * @return
     */

    public  String trimText(String string) {
        int len = string.length();
        int st = 0;
        char[] val = string.toCharArray();
        while ((st < len) && (val[st] < ' ')) {
            st++;
        }
        while ((st < len) && (val[len - 1] <= ' ')) {
            len--;
        }
        return string.substring(st, len);
    }

    public  String trimBegin(String string) {
        int len = string.length();
        int st = 0;
        char[] val = string.toCharArray();
        while ((st < len) && (val[st] <= ' ')) {
            st++;
        }

        return string.substring(st, len);
    }

    public  String trimEnd(String string) {
        int len = string.length();
        int st = 0;
        char[] val = string.toCharArray();

        while ((st < len) && (val[len - 1] <= ' ')) {
            len--;
        }
        return string.substring(st, len);
    }


    public  boolean isBlank(String s) {
        char cs[] = s.toCharArray();
        for (char c : cs) {
            if (c > 32 && c != 12288) {
                return false;
            }
        }
        return true;
    }

    public  void canNotBlank(String ...strings){
        C_ObjectUtil.canNotNull(strings);
        for(String string : strings){
            if(isBlank(string)){
                throw new RuntimeException("字符串不能为空");
            }
        }
    }

    public  String removeRN(String s) {
        s = s.replace('\r', ' ').replace('\n', ' ');
        return s;
    }

    public  String removeT(String s) {
        s = s.replace('\t', ' ');
        return s;
    }


    /**
     * csv 解析 输出
     * @param args
     * @throws Exception
     */
    public  void main(String[] args) throws Exception {
        InputStream is = new FileInputStream(new File("D:\\_TEST_FILES\\1.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String str = "";
        while ((str = br.readLine()) != null) {
            sb.append("'").append(str).append("'").append(",");
        }
        FileWriter fw = new FileWriter("D:\\_TEST_FILES\\2.txt");
        fw.write(sb.toString());
        fw.close();
    }





}
