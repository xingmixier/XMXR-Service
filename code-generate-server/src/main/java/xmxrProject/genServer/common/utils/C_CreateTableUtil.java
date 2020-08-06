package xmxrProject.genServer.common.utils;


import xmxrProject.genServer.common.enums.TestEnum;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class C_CreateTableUtil {


    public final int TXT = 0;
    public final int EXCEL = 1;

    private String FILE_PATH = "D:\\_TEST_FILES\\biao.txt";
    private String ENGINE = "InnoDB";
    private String CHARACTER = "utf8mb4";
    private String COLLATE = "utf8mb4_general_ci";
    private String TABLE_NAME = "TABLENAME";
    private String TABLE_COMMENT = "TABLECOMMENT";
    private int FIELD_LENGTH = 18;
    private int FILE_TYPE = 0;
    private int EXCEL_HEAD_ROW = 0;

    private int COUNT = 0;


    public String getSql(String filePath, String tableName, String tableComment, int fileType, int headRow) {
        FILE_PATH = filePath;
        TABLE_NAME = tableName;
        TABLE_COMMENT = tableComment;
        FILE_TYPE = fileType;
        EXCEL_HEAD_ROW = headRow;
        return getSql();

    }


    public String[] getFields4CN4Txt(String filePath) {
        BufferedReader br = null;
        String[] strings = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String strLine = br.readLine();
            strings = strLine.split("\t");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return strings;
    }

    private String[] getFields4CN4Excel(String filePath) {

        return null;
    }

    public String[] getFields4CN(String filePath) {
        switch (FILE_TYPE) {
            case TXT:
                return getFields4CN4Txt(filePath);
            case EXCEL:
                return getFields4CN4Excel(filePath);
            default:
                return null;
        }
    }


    private String removeFuHao(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                continue;
            }
            s = s.replace(c, '_');
        }
        return s;
    }

    private String[] getResultText(String[] text) {
        String url = "http://fy.iciba.com/ajax.php";
        String value = null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length; i++) {
            sb.append(text[i]);
            if (i < (text.length - 1)) {
                sb.append("!!!!!!");
            }
        }
        System.out.println(sb.toString());
        Map<String, String> map = new HashMap<>();
        map.put("a", "fy");
        map.put("f", "zh");
        map.put("t", "en-US");
        map.put("w", sb.toString());
        Map<String, Object> result = C_HttpUtils.doGet(url, map);
        value = (String) ((Map<String, Object>) result.get("content")).get("out");
        System.out.println(value);
        return value.split("!!!");
    }

    private String createTableSql(String[] CNtexts, String[] ENtexts) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE `").append(TABLE_NAME).append("`  (");
        for (int i = 0; i < ENtexts.length; i++) {
            sb.append("`")
                    .append(ENtexts[i])
                    .append("`")
                    .append(" VARCHAR(50) ")
                    .append(" CHARACTER SET ")
                    .append(CHARACTER)
                    .append(" COLLATE ")
                    .append(COLLATE)
                    .append(" NULL DEFAULT NULL ")
                    .append(" COMMENT ")
                    .append("'")
                    .append(CNtexts[i])
                    .append("'");
            if (i < (ENtexts.length - 1)) {
                sb.append(",");
            }
        }
        sb.append(" ) ENGINE = ").append(ENGINE)
                .append(" CHARACTER SET = ")
                .append(CHARACTER)
                .append(" COLLATE = ")
                .append(COLLATE)
                .append(" COMMENT = ")
                .append("'")
                .append(TABLE_COMMENT)
                .append("'")
                .append(" ROW_FORMAT = Compact;");

        return sb.toString();
    }


    private String[] formatENTexts(String[] enTexts) {

        for (int i = 0; i < enTexts.length; i++) {
//            System.out.println("start-->   "+enTexts[i]);
            enTexts[i] = enTexts[i].trim();
            enTexts[i] = removeFuHao(enTexts[i]);
            enTexts[i] = enTexts[i].replace(' ', '_');
            while (enTexts[i].indexOf("__") != -1) {
                enTexts[i] = enTexts[i].replace("__", "_");
            }
            while (enTexts[i].charAt(0) == '_') {
                enTexts[i] = enTexts[i].substring(1, enTexts[i].length());
            }
            while (enTexts[i].charAt(enTexts[i].length() - 1) == '_') {
                enTexts[i] = enTexts[i].substring(0, enTexts[i].length() - 1);
            }
            enTexts[i] = enTexts[i].substring(0, 1).toLowerCase() + enTexts[i].substring(1);
            if (enTexts[i].length() > 48) {
                enTexts[i] = enTexts[i].substring(0, FIELD_LENGTH);
            }
            for (int ii = 0; ii < enTexts.length; ii++) {
                if (ii == i) {
                    continue;
                }
                if (enTexts[i].equals(enTexts[ii])) {
                    enTexts[i] = enTexts[i] + "_OTHER_" + (COUNT++);
                }
            }
//            System.out.println("end-->   "+enTexts[i]);
        }
        return enTexts;
    }

    private String[] formatCNTexts(String[] cnTexts) {
        for (int i = 0; i < cnTexts.length; i++) {
            cnTexts[i] = cnTexts[i].trim();
        }
        return cnTexts;
    }


    public String getSql() {
        COUNT = 0;
        String[] CNTexts = getFields4CN(FILE_PATH);
        System.out.println(CNTexts.length);
        CNTexts = formatCNTexts(CNTexts);
        String[] ENTexts = getResultText(CNTexts);
        System.out.println(ENTexts.length);
        ENTexts = formatENTexts(ENTexts);
        for (int i = 0; i < CNTexts.length; i++) {
            System.out.println(ENTexts[i] + " --> " + CNTexts[i]);
        }
        return createTableSql(CNTexts, ENTexts);
    }


    public static void main(String[] args) {
        C_CreateTableUtil c = new C_CreateTableUtil();
        String begin = "dfhx_";
        String sql = c.getSql("D:\\_TEST_FILES\\biao.txt", begin+"hxydmx", "核销用电明细", c.TXT, 1);
        System.out.println(sql);
    }
}
