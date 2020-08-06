package xmxrProject.genServer.common.utils;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class C_CreateTableUtil_Copy {


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
    private static String url = "http://fy.iciba.com/ajax.php";
    private static HashMap<String,String > [] result;
    private int CN_Field_Count;
    private int EN_Field_Count;
    private String [] CNFields ;
    private String [] ENFields ;


    public String getSql(String filePath, String tableName, String tableComment, int fileType, int headRow) throws IOException {
        FILE_PATH = filePath;
        TABLE_NAME = tableName;
        TABLE_COMMENT = tableComment;
        FILE_TYPE = fileType;
        EXCEL_HEAD_ROW = headRow;
        return getSql();

    }

    private String getSql() throws IOException {
        StringBuilder cnFields = new StringBuilder();
        switch (FILE_TYPE){
            case TXT:       getTxtFields(cnFields);               break;
            case EXCEL:     getExcelFields(cnFields);             break;
        }
        getENFields(cnFields);
        return createTableSql();
    }


    private void removeDuplicate(){
        String str1;
        String str2;
        for(int i = 0 ; i < ENFields.length ; i++){
            str1 = ENFields[i];
            for(int ii = 0 ; ii < ENFields.length ; ii ++){
                str2 = ENFields[ii];
                if(str1.equalsIgnoreCase(str2) && i != ii){
                    ENFields[i] = str1+COUNT++;
                }
            }
        }
    }

    private String createTableSql() {
        if(CN_Field_Count != EN_Field_Count){
            for(int i = 0 ; i < (EN_Field_Count >CN_Field_Count?EN_Field_Count :CN_Field_Count) ; i++){
                if(i < CN_Field_Count){
                    System.out.print(CNFields[i]);
                }
                System.out.print("\t\t\t ---> \t\t");
                if(i < EN_Field_Count){
                    System.out.print(ENFields[i]);
                }
                System.out.println();
            }
            return "参数数量不对等 CN_Field_Count："+CN_Field_Count+"    EN_Field_Count："+EN_Field_Count;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE `").append(TABLE_NAME).append("`  (");
        removeDuplicate();
        for (int i = 0; i < ENFields.length; i++) {
            sb.append("`")
                    .append(ENFields[i])
                    .append("`")
                    .append(" VARCHAR(50) ")
                    .append(" CHARACTER SET ")
                    .append(CHARACTER)
                    .append(" COLLATE ")
                    .append(COLLATE)
                    .append(" NULL DEFAULT NULL ")
                    .append(" COMMENT ")
                    .append("'")
                    .append(CNFields[i])
                    .append("'");
            if (i < (ENFields.length - 1)) {
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

    private void getENFields(StringBuilder fields) {
        StringBuilder sb = new StringBuilder();
        String value;
        Map map = new HashMap();
        map.put("a", "fy");
        map.put("f", "zh");
        map.put("t", "en-US");
        map.put("w", fields.toString());
        Map<String, Object> res = C_HttpUtils.doGet(url, map);
        value = (String) ((Map<String, Object>) res.get("content")).get("out");
        String [] enFields = value.split("\\n");
        EN_Field_Count = enFields.length;
        ENFields = new String[EN_Field_Count];
        String enField;
        for(int i = 0 ; i < EN_Field_Count ; i++){
            enField = enFields[i];
            enField = sqlFormat4EN(enField);
            ENFields[i] = enField;
            result[i].put("en",enField);
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
    private String sqlFormat4EN(String enField) {
        enField = enField.trim();
        enField = removeFuHao(enField);
        enField = enField.replace(' ', '_');
        while (enField.indexOf("__")!=-1){
            enField = enField.replace("__","_");
        }
        while (enField.startsWith("_")){
            enField = enField.substring("_".length());
        }
        while (enField.endsWith("_")){
            enField = enField.substring(0,enField.length()- "_".length());
        }
        if(enField.length()>2){
            enField = enField.substring(0, 1).toLowerCase() + enField.substring(1);
        }
        if (enField.length() > FIELD_LENGTH) {
            enField = enField.substring(0, FIELD_LENGTH);
        }
        return enField;
    }

    private void getExcelFields(StringBuilder fields) throws FileNotFoundException {

    }

    private void getTxtFields(StringBuilder fields) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
        String strLine = br.readLine();
        String [] cnFields = strLine.split("\t");
        CN_Field_Count = cnFields.length;
        result = new HashMap[CN_Field_Count];
        String cnField;
        CNFields = new String[CN_Field_Count];
        for(int i = 0 ; i < CN_Field_Count ; i++){
            cnField = cnFields[i];
            cnField = sqlFormat4CN(cnField);
            CNFields[i] = cnField;
            HashMap<String ,String > hashMap = new HashMap();
            hashMap.put("cn",cnField);
            result[i] = hashMap;
            fields.append(cnField).append("\n");
        }
    }

    private String sqlFormat4CN(String cnField) {
        cnField = cnField.trim();
        return cnField;
    }

    public static void main(String[] args) throws Exception {
        C_CreateTableUtil_Copy c = new C_CreateTableUtil_Copy();
        String begin = "hnyd_";
        String sql = c.getSql("D:\\_TEST_FILES\\biao.txt", begin+"dfjsmsakhdc4q", "电费结算模式按客户导出XXX区", c.TXT, 1);
        System.out.println(sql);
    }
}
