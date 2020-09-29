package xmxrProject.genServer.common.utils;


import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class C_CreateTableUtil2 {




    private static String createSql4Excel(File file, String tableNameStart, String fileName,List<Map> otherColumns, int sheetIndex, int headRow) throws Exception {
        String jdbcType = "VARCHAR(100)";
        String character = "utf8mb4";
        String engine = "InnoDB";
        String collate = "utf8mb4_general_ci";
        List<String> titles = new ArrayList<>();
        C_ExcelUtil excelUtil = new C_ExcelUtil(fileName);
        excelUtil.setSheet(sheetIndex - 1);
        excelUtil.getRow(headRow - 1).forEach(cell -> {
            titles.add(cell.getStringCellValue());
        });
        excelUtil.close();
        return createSql(tableNameStart,fileName, titles, jdbcType, character, engine, collate,null);
    }

    private static String createSql4Txt(File file, String tableNameStart, String separate) throws Exception {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String data = br.readLine();
        br.close();
        List<String > titles = Arrays.asList(data.split(separate));
        String fileName =file.getName();
        String jdbcType = "VARCHAR(100)";
        String character = "utf8mb4";
        String engine = "InnoDB";
        String collate = "utf8mb4_general_ci";
        return createSql(tableNameStart,fileName, titles, jdbcType, character, engine, collate,null);
    }

    private static String createSql(String tableNameStart ,String fileName, List<String> titles, String jdbcType, String character, String engine, String collate,List<Map> otherColumns) throws BadHanyuPinyinOutputFormatCombination {
        int  i = 0;
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        String tableName = C_PinYinUtil.getPinYinLTVS(fileName, "_");
        String columnName = "";
        sb.append("`")
                .append(tableNameStart)
                .append(tableName)
                .append("`(");
        for (String title : titles) {

                columnName = C_PinYinUtil.getPinYinLTVS(title, "_");
            if(columnName.trim().equals("")){
                if(title.matches("^[A-Za-z]*")){
                    columnName = title;
                }else{
                    columnName = title.replaceAll("[^A-Za-z]","_");
                    while (columnName.contains("__")){
                        columnName.replaceAll("__","_");
                    }
                    if(columnName.matches("[A-Za-z]*_$")){
                        columnName = columnName.substring(0,columnName.lastIndexOf("_"));
                    }else if(columnName.matches("^_[A-Za-z]*")){
                        columnName = columnName.substring("_".length()+1);
                    }
                    columnName = columnName.length() < 1 ? "column_"+(++i):columnName;
                }
            }
            sb.append(" `")
                    .append(columnName)
                    .append("` ")

                    .append(jdbcType)
                    .append(" CHARACTER SET ")
                    .append(character)
                    .append(" COLLATE ")
                    .append(collate)
                    .append(" NULL DEFAULT NULL ")
                    .append(" COMMENT ")
                    .append(" '")
                    .append(title)
                    .append("' , ");

        }
        sb.delete(sb.lastIndexOf(","), sb.length());
        sb.append(" ) ENGINE = ").append(engine)
                .append(" CHARACTER SET = ")
                .append(character)
                .append(" COLLATE = ")
                .append(collate)
                .append(" COMMENT = ")
                .append(" '")
                .append(fileName)
                .append("' ")
                .append(" ROW_FORMAT = Compact;");
        return sb.toString();
    }




    public static void main(String[] args) throws Exception {



        File file = new File("D:\\_TEST_FILES\\骚扰数据批量处理模板.txt");
        String jdbcType = "VARCHAR(100)";
        String character = "utf8mb4";
        String engine = "InnoDB";
        String collate = "utf8mb4_general_ci";
        String tableNameStart = "sr_";
        System.out.println(createSql4Txt(file, "sr_", "\t"));
    }

}
