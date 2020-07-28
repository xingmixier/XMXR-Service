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

    public static String trimText(String string) {
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

    public static String trimBegin(String string) {
        int len = string.length();
        int st = 0;
        char[] val = string.toCharArray();
        while ((st < len) && (val[st] <= ' ')) {
            st++;
        }

        return string.substring(st, len);
    }

    public static String trimEnd(String string) {
        int len = string.length();
        int st = 0;
        char[] val = string.toCharArray();

        while ((st < len) && (val[len - 1] <= ' ')) {
            len--;
        }
        return string.substring(st, len);
    }


    public static boolean isBlank(String s) {
        char cs[] = s.toCharArray();
        for (char c : cs) {
            if (c > 32 && c != 12288) {
                return false;
            }
        }
        return true;
    }

    public static String removeRN(String s) {
        s = s.replace('\r', ' ').replace('\n', ' ');
        return s;
    }

    public static String removeT(String s) {
        s = s.replace('\t', ' ');
        return s;
    }


    /**
     * csv 解析 输出
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        InputStream is = new FileInputStream(new File("D:\\_TEST_FILES\\zbxx.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String str = "";
        while ((str = br.readLine()) != null) {
            sb.append(str).append("\r\n");
        }
        int start = -1;
        int end = -1;
        List<String[]> sss = new ArrayList<>();
        String[] ss = new String[24];
        int index = 0;


        for (int p = 0; p < sb.length(); p++) {
            char c = sb.charAt(p);
            if (c == '"') {
                if (start == -1) {
                    start = p;
                }
                if (end == -1 && start != p) {
                    end = p;
                }
                if (start != -1 && end != -1) {
                    String s = sb.substring(start, end + 1);
                    start = -1;
                    end = -1;
                    if (index == 24) {
                        index = 0;
                        sss.add(ss);
                        ss = new String[24];
                    }
                    ss[index++] = s;
                }
            }
        }



        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = null;
        StringBuilder sb3 = null;
        for(int i = 0 ; i < sss.size() ;i ++){
            String [] strings = sss.get(i);
            sb2 = new StringBuilder();
            for(int ii = 0 ; ii < strings.length ; ii++ ){
                sb3 = new StringBuilder();
                sb3.append("\"");
                String s = new String(strings[ii]);
                sb3.append(s);
                C_StringBuilderUtil.replace(sb3,"\r","`r");
                C_StringBuilderUtil.replace(sb3,"\n","`n");
                C_StringBuilderUtil.replace(sb3,",","``");
                C_StringBuilderUtil.replace(sb3,"\"","`^");
                System.out.println(sb3);
                System.out.println("============================================================================");
                sb3.append("\"");
                sb2.append(sb3);
                if(ii < strings.length -1){
                    sb2.append(",");
                }
            }
            sb1.append(sb2);
            sb1.append("\r\n");
        }

        FileWriter fw = new FileWriter("D:\\_TEST_FILES\\zb.csv");
        String out  =new String(sb1);
        fw.write(out);
        fw.close();

    }


}
