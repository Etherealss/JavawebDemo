package pers.etherealss.demo.controller;

import com.alibaba.fastjson.JSONObject;
import pers.etherealss.demo.infrastructure.factory.ServiceProxyFactory;
import pers.etherealss.demo.infrastructure.core.mvc.annotation.ServletHandler;
import pers.etherealss.demo.infrastructure.core.mvc.enums.RequestType;
import pers.etherealss.demo.pojo.po.User;
import pers.etherealss.demo.pojo.vo.ApiMsg;
import pers.etherealss.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author wtk
 * @description 示例的Controller。类上的ServletHandler表示 /users 的请求会由这个类处理
 * @date 2021-08-27
 */
@ServletHandler("/users")
public class UserController {

    private UserService userService = ServiceProxyFactory.getUserService();

    /**
     * ServletHandler注解（类上和方法上都有）的value参数表示:
     * /users 请求下的 /login 请求会由这个方法处理
     * type参数限定了该方法只能处理POST请求
     *
     * 方法参数固定为HttpServletRequest req, HttpServletResponse resp, JSONObject params
     * 原因见DispatcherController 里面的doInvoke方法
     *
     * 方法返回值建议为 {@code ApiMsg<User>}
     *
     * @param req
     * @param resp
     * @param params 可以获取前端传来的请求参数
     * @return
     * @throws Exception
     */
    @ServletHandler(value = "/login", type = RequestType.POST)
    public ApiMsg<User> login(HttpServletRequest req, HttpServletResponse resp, JSONObject params) throws Exception {
        Long id = params.getLong("id");
        String password = params.getString("password");

        // 参数都在params，获取的参数需要进行判空检查。
        // 否则最后可能会在DAO层才被发现错误，会降低效率。
        if (id == null || "".equals(password)) {
            return ApiMsg.paramsError("请求参数缺失");
        }
        System.out.println("id = " + id + ", password = " + password);
        User user = userService.login(id, password);
        ApiMsg<User> msg = new ApiMsg<>(user);
        return msg;
    }

    @ServletHandler(value = "/getUser", type = RequestType.GET)
    public ApiMsg<User> getUser(HttpServletRequest req, HttpServletResponse resp, JSONObject params) throws Exception {
        Long id = params.getLong("id");
        User user = userService.getUser(id);
        ApiMsg<User> msg = new ApiMsg<>(user);
        return msg;
    }
}
