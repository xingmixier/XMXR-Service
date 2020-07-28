package xmxrProject.genServer.common.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @Aether 行米希尔
 * @Date 2020/7/12 20:05
 */
public class C_StreamUtil {

    public static void closeStream(Closeable... closeables) {
        try {
            for (Closeable closeable : closeables) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
