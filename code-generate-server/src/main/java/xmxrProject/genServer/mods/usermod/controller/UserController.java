package xmxrProject.genServer.mods.usermod.controller;

import xmxrProject.genServer.common.baseMod.BaseController;
import xmxrProject.genServer.common.webResult.Result;
import xmxrProject.genServer.mods.usermod.entity.TestUser;
import xmxrProject.genServer.mods.usermod.entity.User;
import xmxrProject.genServer.mods.usermod.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.TextUI;
import java.util.List;

/**
 * @Aether 行米希尔
 * @Date 2020/7/11 16:42
 */
@CrossOrigin
@RestController
@RequestMapping("/user/")
public class UserController extends BaseController<User> {

    @Autowired
    private UserService userService;

    @Autowired
    private TestUser textUser ;

    /**
     * 添加一条user数据
     *
     * @param user     user 对象数据
     * @param request  请求对象
     * @param response 响应对象
     * @return 结果 json 字符串
     */
    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public String addUser(User user, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            userService.insert(user);
            result.setResult(0, "添加成功", null, user);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(1, "添加失败", e.getLocalizedMessage(), user);
        } finally {
            return result.toJson();
        }
    }

    /**
     * 前端示例 [{"pkId":1,"id":"1","name":"zs","pwd":"123"},{"pkId":2,"id":"2","name":"ls","pwd":"456"},{"pkId":3,"id":"3","name":"ww","pwd":"789"}] postman - post - body- raw
     * 批量user数据
     *
     * @param users    user 对象集合数据
     * @param request  请求对象
     * @param response 响应对象
     * @return 结果 json 字符串
     */
    @RequestMapping(value = "addUsers", method = RequestMethod.POST)
    public String addUsers(@RequestBody List<User> users, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            userService.insertList(users);
            result.setResult(0, "添加成功", null, users);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(1, "添加失败", e.getLocalizedMessage(), users);
        } finally {
            logger.info("addUsers : " + result);
            return result.toJson();
        }
    }

    /**
     * 获取 一条 user 数据
     *
     * @param user     查询条件
     * @param request  请求对象
     * @param response 响应对象
     * @return 结果 json 字符串
     */
    @RequestMapping(value = "getUser")
    public String getUser(User user, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            user = userService.select(user);
            result.setResult(0, "查询成功", null, user);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(1, "查询失败", e.getLocalizedMessage(), user);
        } finally {
            return result.toJson();
        }
    }

    /**
     * 获取 所有 user 数据
     *
     * @param user     查询条件
     * @param request  请求对象
     * @param response 响应对象
     * @return 结果 json 字符串
     */
    @RequestMapping(value = "getUsers")
    public String getUsers(User user, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            List<User> users = userService.selectList(user);
            result.setResult(0, "查询成功 找到" + users.size() + "条数据", null, users);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(1, "查询失败", e.getLocalizedMessage(), user);
        } finally {
            return result.toJson();
        }
    }

    /**
     * 更新 user 数据
     *
     * @param user     更新 数据 和 条件
     * @param request  请求对象
     * @param response 响应对象
     * @return 结果 json 字符串
     */
    @RequestMapping(value = "updateUser")
    public String updateUser(User user, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            int i = userService.update(user);
            result.setResult(0, i + "条数据被修改", null, user);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(1, "修改失败", e.getLocalizedMessage(), user);
        } finally {
            return result.toJson();
        }
    }

    /**
     * 删除 user 数据
     *
     * @param user     删除 条件
     * @param request  请求对象
     * @param response 响应对象
     * @return 结果 json 字符串
     */
    @RequestMapping(value = "deleteUser")
    public String deleteUser(User user, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            int i = userService.delete(user);
            result.setResult(0, i + "条数据被删除", null, user);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(1, "删除失败", e.getLocalizedMessage(), user);
        } finally {
            return result.toJson();
        }
    }

    /**
     * 删除 user 表 全部数据
     *
     * @param request  请求对象
     * @param response 响应对象
     * @return 结果 json 字符串
     */
    @RequestMapping(value = "deleteAllUser")
    public String deleteAllUser(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            int i = userService.deleteAll();
            result.setResult(0, i + "条数据被删除", null, null);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(1, "删除失败", e.getLocalizedMessage(), null);
        } finally {
            return result.toJson();
        }
    }

}
