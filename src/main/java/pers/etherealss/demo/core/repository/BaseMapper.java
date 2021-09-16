package pers.etherealss.demo.core.repository;

import java.sql.SQLException;
import java.util.List;

/**
 * 所有dao层的公共接口
 * 负责定义一些通用的接口
 * @param <T> 利用泛型，这样可以继承的子类可以针对性的处理对应的pojo类
 * @author yohoyes
 */
public interface BaseMapper<T> {

    /**
     * 新增
     * @param object
     */
    int insertOne(T object) throws SQLException;

    /**
     * 更新
     * @param object
     */
    int updateOne(T object) throws SQLException, IllegalAccessException;

    /**
     * 根据Id删除一行
     * @param id
     */
    int deleteOne(int id) throws SQLException;

    /**
     * 根据条件删除
     * @param po 包装好的pojo对象
     */
    int deleteOne(T po) throws SQLException;

    /**
     * 根据id获取
     * @param object
     * @return
     */
    T selectOne(T object) throws SQLException, IllegalAccessException, InstantiationException;

    /**
     * 根据id获取, 执行对应的sql语句
     * @param object
     * @param sql
     * @return
     */
    T selectOne(T object,String sql) throws SQLException, InstantiationException, IllegalAccessException;

    /**
     * 根据传入的是sql语句获取
     * @param object
     * @param sql
     * @return
     */
    List<T> select(T object, String sql) throws IllegalAccessException, InstantiationException, SQLException;

    /**
     *根据对象获取一堆数据并返回
     * @param po
     * @return
     */
    List<T> selectObjectList(T po) throws IllegalAccessException, SQLException, InstantiationException;
}
