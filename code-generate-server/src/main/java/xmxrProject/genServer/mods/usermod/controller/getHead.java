package xmxrProject.genServer.mods.usermod.controller;

import xmxrProject.genServer.common.webResult.LayuiTableHead;
import xmxrProject.genServer.common.webResult.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @FileName: getHead.java
 * @Author: 行米希尔
 * @Date: 2020/7/15 10:17
 * @Description:
 */
@CrossOrigin()
@Controller
public class getHead {
    @ResponseBody
    @RequestMapping("/getTableHead")
    public String getTableHead(){
        Result result = new Result();
        result.setStatus(Result.SUCCESS);
        result.setMsg("getTableHead");
        LayuiTableHead [][]layuiTableHeads = new LayuiTableHead[1][3];
        layuiTableHeads[0][0] = new LayuiTableHead("id","ID","80px",true);
        layuiTableHeads[0][1] = new LayuiTableHead("name","用户名","80px",true);
        layuiTableHeads[0][2] = new LayuiTableHead("pwd","密码","80px",true);
        result.setData(layuiTableHeads);
        return result.toJson();

    }
}
