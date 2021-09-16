package pers.etherealss.demo.dao.impl;

import pers.etherealss.demo.core.repository.SimpleBaseMapper;
import pers.etherealss.demo.dao.UserDao;
import pers.etherealss.demo.pojo.po.User;

import java.text.MessageFormat;

/**
 * @author wtk
 * @description
 * @date 2021-09-16
 */
public class UserDaoImpl extends SimpleBaseMapper<User> implements UserDao {
    @Override
    public String getTableName() {
        return "user";
    }

    @Override
    public String getQueryCondition(User po) {
        String format = null;
        if (po.getId() != 0) {
            // 如果传入的对象中包含了id属性，则使用id查询
            format = "id = " + po.getId();
        } else if (!"".equals(po.getEmail()) && !"".equals(po.getPassword())) {
            // 若包含了email和password，则用这两者茶韵
            String base = "email = {0} and password = {1}";
            format = MessageFormat.format(base, po.getEmail(), po.getPassword());
        } else {
            // 使用email查询
            String base = "email = {0}";
            format = MessageFormat.format(base, po.getEmail());
        }
        return format;
    }
}
