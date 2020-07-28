package xmxrProject.genServer.mods.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.function.BiConsumer;

/**
 * @FileName: test.java
 * @Author: 行米希尔
 * @Date: 2020/7/22 11:36
 * @Description:
 */
public class test {

    public static void main(String[] args) {
        writeTest();
    }

    public static void writeTest(){
        int h = "123awead".hashCode();
        int x = h >>> 16;
        System.out.println(h);
        System.out.println(x);
        String s  = Integer.toBinaryString(h);
        String s1 = Integer.toBinaryString(x);
        System.out.println(s);
        System.out.println(s1);
        System.out.println(h^x);
        HashMap map = new HashMap<>();
        map.put("1","2");
        map.size();
        System.out.println(1 << 30);
        System.out.println(0x7fffffff);
    }
}
