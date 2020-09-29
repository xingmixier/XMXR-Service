package xmxrProject.genServer;

import xmxrProject.genServer.common.utils.C_ClassUtil;
import xmxrProject.genServer.mods.usermod.entity.User;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @FileName: CodeTemp.java
 * @Author: 行米希尔
 * @Date: 2020/9/11 14:29
 * @Description:
 */
public class CodeTemp {

    private static Map<String ,Object> params;

    public static String out() throws Exception {
        return """
                package xmxrProject.genServer.mods."""+v("className")+"""
                mod.controller;

                import xmxrProject.genServer.common.baseMod.BaseController;
                import xmxrProject.genServer.common.webResult.Result;
                import xmxrProject.genServer.mods."""+v("className")+"""
                mod.entity."""+v("ClassName")+"""
                ;
                import xmxrProject.genServer.mods."""+v("className")+"""
                mod.service."""+v("ClassName")+"""
                Service;
                import org.springframework.beans.factory.annotation.Autowired;
                import org.springframework.web.bind.annotation.RequestMapping;
                import org.springframework.web.bind.annotation.RestController;
                import org.springframework.web.bind.annotation.CrossOrigin;

                import javax.servlet.http.HttpServletRequest;
                import javax.servlet.http.HttpServletResponse;
                import java.util.List;

                /**
                 *"""+v("ClassName")+"""
                controller
                 * @Aether"""+v("Aether")+"""
                
                 * @Date """+v("createTime")+"""
                
                 */
                @CrossOrigin
                @RestController
                @RequestMapping("/"""+v("className")+"""
                /")
                public class """+v("ClassName")+"""
                Controller extends BaseController<"""+v("ClassName")+"""
                > {

                    @Autowired
                    private """+v("ClassName")+"""
                Service """+v("className")+"""
                Service;

                    /**
                     * 添加一条"""+v("className")+"""
                数据
                     * @param """+v("className")+"""
                 """+v("className")+"""
                 对象数据
                     * @param request 请求对象
                     * @param response 响应对象
                     * @return 结果 json 字符串
                     */
                    @RequestMapping(value = "add
                 """+v("ClassName")+"""
                ")
                    public String  add"""+v("ClassName")+"""
                ("""+v("ClassName")+"""
                 """+v("className")+"""
                      , HttpServletRequest request , HttpServletResponse response){
                        Result result = new Result() ;
                        try {"""+v("className")+"""
                Service.insert("""+v("className")+"""
                );
                            result.setResult(0,"添加成功",null,"""+v("className")+"""
                );
                        }catch (Exception e){
                            e.printStackTrace();
                            result.setResult(1,"添加失败",e.getLocalizedMessage(),"""+v("className")+"""
                );
                        }finally {
                            logger.info("add"""+v("ClassName")+"""
                 : "+result);
                            return result.toJson();
                        }
                    }



                """;
    }

    

    private String opn(Logic logic){
        return logic.logicCode(params);
    }
    private static String v(String name) throws Exception {
        Object var ;
        String [] strs = name.split("\\.");
        if( strs.length <= 0){
            return "!#ERROR#!";
        }else if(strs.length == 1){
            return params.get(name).toString();
        }else {
            var = params.get(strs[0]);
            for (int i = 1; i < strs.length; i++) {
                if(var instanceof Map){
                    var = ((Map)var).get(strs[i]);
                }else {
                    var = C_ClassUtil.getFieldValue(var,strs[i]);
                }
            }
            return var.toString();
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(out());
    }

    static {
        params = new HashMap<>();
        params.put("Aether", "行米希尔");
        params.put("createTime", new Date());
        params.put("className", "user");
        params.put("ClassName", "User");
        
    }

}//end
interface Logic{
    String logicCode(Map<String,Object> params);
}
