package pers.etherealss.demo.repository;

import org.junit.Test;
import pers.etherealss.demo.dao.UserDao;
import pers.etherealss.demo.dao.impl.UserDaoImpl;
import pers.etherealss.demo.pojo.po.User;

import java.util.List;

/**
 * @author yohoyes
 */
public class UserMapperTest {
    private UserDao mapper = new UserDaoImpl();

    @Test
    public void insertTest() {

    }

    @Test
    public void selectTest() throws Exception {
        User user = new User();
        List<User> users = mapper.selectObjectList(user);
        users.forEach(System.out::println);
    }

    @Test
    public void deleteTest() {

    }

    @Test
    public void updateTest() {

    }
}
