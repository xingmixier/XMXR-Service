package xmxrProject.genServer.mods.codeGenerate.service;

import xmxrProject.genServer.common.utils.C_CodeBuilder;
import xmxrProject.genServer.common.utils.C_ListUtil;
import xmxrProject.genServer.common.utils.C_ZipUtil;
import xmxrProject.genServer.common.utils.C_StreamUtil;
import xmxrProject.genServer.mods.codeGenerate.entity.ClassField;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Aether 行米希尔
 * @Date 2020/7/12 19:57
 */
@Service
public class CodeGenerateService {

    public File getSrcZip(String srcDir) {
        File zipFile = null;
        FileOutputStream fos = null;
        try {
            zipFile = new File("D:\\_TEST_FILES\\src.zip");
            fos = new FileOutputStream(zipFile);
            C_ZipUtil.toZip(srcDir, fos);
            return zipFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            C_StreamUtil.closeStream(fos);
            if (zipFile == null) {
                throw new RuntimeException("zipFile = null");
            }
            return zipFile;
        }
    }


    public static void codeGen() throws Exception {
        String str = CodeGenerateService.class.getResource("/").getPath();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(str+"/templates/mapper.template"))));
        StringBuilder sb = new StringBuilder();
        while ((str = br.readLine())!=null)
            sb.append(str).append("\r\n");
        Map<String,Object> result = new HashMap<>();
        result.put("className","user");
        result.put("ClassName","User");
        result.put("tableName","t_user");
        ClassField classField1 = new ClassField("","private","String","name","用户名","Name","u_name","like");
        ClassField classField2 = new ClassField("","private","String","pwd","密码","Pwd","u_pwd","=");
        List<ClassField> fields = C_ListUtil.toArrayList(classField1,classField2);
        result.put("fields",fields);
        C_CodeBuilder c_codeBuilder = new C_CodeBuilder(sb,result);
        str = c_codeBuilder.generateCode();
        System.out.println(str);
    }

    public static void main(String[] args) throws Exception {
        codeGen();
    }


}
