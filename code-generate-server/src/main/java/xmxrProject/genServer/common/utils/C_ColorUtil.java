package xmxrProject.genServer.common.utils;

import java.util.Random;

public class C_ColorUtil {
    private static char[] chars = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };
    private static final int MAX = 16777215;

    public static String getRandom16Color() {
        StringBuilder sb = new StringBuilder();
        sb.append("#");
        for (int i = 0; i < 6; i++) {
            int x = new Random().nextInt(16);
            sb.append(chars[x]);
        }
        return sb.toString();
    }


    public static String[] getProgressive16Color(int size) {
        int interval = Math.round(MAX / (size));
        String[] colors = new String[size];
        for (int i = 0; i < size; i++) {
            StringBuilder s = new StringBuilder();
            String color = "000000" + Integer.toHexString(interval * i);
            color = color.substring(color.length() - 6);
            colors[i] = "#" + color.toUpperCase();
        }
        return colors;
    }


}
