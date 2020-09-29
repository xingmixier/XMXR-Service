package xmxrProject.genServer.common.utils;

import java.io.File;
import java.io.IOException;

/**
 * @FileName: C_TXTUtil.java
 * @Author: 行米希尔
 * @Date: 2020/9/1 10:04
 * @Description:
 */
public class C_TXTUtil {




    public static void main(String[] args) throws IOException {
        File f = new File("C:\\Users\\Administrator\\Desktop\\临时2.txt");
        String txt = C_IOUtil.readTxt(f);
        String [] ss = "QWERTYUIOPASDFGHJKLZXCVBNM".split("");
        for(String s : ss){
            txt = txt.replaceAll(s," "+s);
        }
        C_IOUtil.writeTxt(f,txt);
    }

}//end
