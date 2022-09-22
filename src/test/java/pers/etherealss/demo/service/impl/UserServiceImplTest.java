package pers.etherealss.demo.service.impl;

import junit.framework.TestCase;
import pers.etherealss.demo.infrastructure.factory.ServiceProxyFactory;
import pers.etherealss.demo.pojo.po.User;
import pers.etherealss.demo.service.UserService;

public class UserServiceImplTest extends TestCase {

    UserService userService = ServiceProxyFactory.getUserService();

    public void testLogin() throws Exception{
        User login = userService.login(1L, "123");
        System.out.println(login);
    }

    public void testUpdate() throws Exception {
        User user1 = userService.login(1L, "123");
        user1.setEmail("111");
        userService.update(user1);
    }
}