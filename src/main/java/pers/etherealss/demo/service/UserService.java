package pers.etherealss.demo.service;

import pers.etherealss.demo.pojo.po.User;

/**
 * @author wtk
 * @description
 * @date 2021-09-14
 */
public interface UserService {

    User login(Long id, String password) throws Exception;

    User getUser(Long id) throws Exception;

    void update(User user) throws Exception;
}
