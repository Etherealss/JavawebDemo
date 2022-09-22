package pers.etherealss.demo.service.impl;

import pers.etherealss.demo.dao.UserDao;
import pers.etherealss.demo.infrastructure.factory.DaoFactory;
import pers.etherealss.demo.pojo.po.User;
import pers.etherealss.demo.service.UserService;

/**
 * @author wtk
 * @description
 * @date 2021-09-14
 */
public class UserServiceImpl implements UserService {

    private UserDao dao =  DaoFactory.getUserDao();

    @Override
    public User login(Long id, String password) throws Exception {
        // 检查密码啥的就不写了，都会
        User user = new User();
        user.setId(id);
        return dao.selectOne(user);
    }

    @Override
    public User getUser(Long id) throws Exception {
        User user = new User();
        user.setId(id);
        return dao.selectOne(user);
    }

    @Override
    public void update(User user) throws Exception {
        dao.updateOne(user);
        // 制造异常，检查自动回滚
        int i = 1 / 0;
    }
}
