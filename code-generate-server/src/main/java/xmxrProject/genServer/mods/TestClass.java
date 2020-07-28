package xmxrProject.genServer.mods;

import xmxrProject.genServer.common.utils.C_CodeBuilder;
import xmxrProject.genServer.mods.codeGenerate.entity.ClassField;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Aether 行米希尔
 * @Date 2020/7/11 21:46
 */
public class TestClass {


    public static void main(String[] args) throws IOException {

        Map<String, Object> result = new HashMap<>();

        result.put("className", "user");
        result.put("ClassName", "User");
        result.put("tableName", "user");
        result.put("tableNote", "用户表");
        result.put("Aether", "行米希尔");
        result.put("createTime", new SimpleDateFormat("yyyy年MM月dd日  HH时mm分ss秒").format(new Date()));

        List<ClassField> classFields = new ArrayList<>();
        classFields.add(new ClassField("", "private", "String", "id", "主键", "Id", "id", "="));
        classFields.add(new ClassField("", "private", "String", "name", "用户名", "Name", "name", "like"));
        classFields.add(new ClassField("", "private", "String", "pwd", "密码", "Pwd", "pwd", "like"));
        result.put("fields", classFields);

        String classPath = C_CodeBuilder.class.getClassLoader().getResource("").getPath();
        String mmuBanDiZhi = "/templates/mapper.template";
        File file = new File(classPath + mmuBanDiZhi);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String text;
        StringBuilder sb = new StringBuilder();
        while ((text = br.readLine()) != null)
            sb.append(text).append("\r\n");
        br.close();
        C_CodeBuilder codeGenerateService = new C_CodeBuilder(sb, result);
        String outText = codeGenerateService.generateCode();
        System.out.println(outText);
    }
}
