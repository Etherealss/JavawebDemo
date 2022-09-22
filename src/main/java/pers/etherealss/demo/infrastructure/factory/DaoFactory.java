package pers.etherealss.demo.infrastructure.factory;

import pers.etherealss.demo.dao.UserDao;
import pers.etherealss.demo.dao.impl.UserDaoImpl;

/**
 * @author wtk
 * @description Dao工厂
 * @date 2021-09-14
 */
public class DaoFactory {

    public static UserDao getUserDao() {
        return new UserDaoImpl();
    }
}
