package xmxrProject.genServer.common.utils;

import java.io.*;

/**
 * @FileName: C_IOUtil.java
 * @Author: 行米希尔
 * @Date: 2020/9/1 10:06
 * @Description:
 */
public class C_IOUtil {


    public static void closeStream(Closeable... closeables) {
        try {
            for (Closeable closeable : closeables) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileInputStream getFileInputStream(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    public static BufferedInputStream getBufferedInputStream(File file) throws FileNotFoundException {
        return new BufferedInputStream(getFileInputStream(file));
    }

    public static InputStreamReader getInputStreamReader(File file) throws FileNotFoundException {
        return  new InputStreamReader(getFileInputStream(file));
    }

    public static BufferedReader getBufferedReader(File file) throws FileNotFoundException {
        return new BufferedReader(getInputStreamReader(file));
    }

    public static String readTxt(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        String txt = "";
        BufferedReader br = getBufferedReader(file);
        while ((txt = br.readLine() )!= null)
            sb.append(txt).append("\r\n");
        closeStream(br);
        return sb.toString();
    }

    public static FileOutputStream getFileOutputStream(File file) throws FileNotFoundException {
        return new FileOutputStream(file);
    }

    public static BufferedOutputStream getBufferedOutputStream(File file) throws FileNotFoundException {
        return new BufferedOutputStream(getFileOutputStream(file));
    }

    public static OutputStreamWriter getOutputStreamWriter(File file) throws FileNotFoundException {
        return new OutputStreamWriter(getFileOutputStream(file));
    }

    public static BufferedWriter getBufferedWriter(File file) throws FileNotFoundException {
        return new BufferedWriter(getOutputStreamWriter(file));
    }

    public static void writeTxt(File file ,String txt) throws IOException {
        BufferedWriter bw = getBufferedWriter(file);
        bw.write(txt);
        closeStream(bw);
    }



}//end
