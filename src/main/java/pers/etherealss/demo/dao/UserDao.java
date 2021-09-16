package pers.etherealss.demo.dao;

import pers.etherealss.demo.pojo.po.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author wtk
 * @description
 * @date 2021-09-14
 */
public interface UserDao {

    User selectOne(User user) throws Exception;

    int updateOne(User user) throws Exception;

    List<User> selectObjectList(User user) throws Exception;
}
