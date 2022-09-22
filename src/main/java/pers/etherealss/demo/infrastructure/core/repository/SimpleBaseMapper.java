package pers.etherealss.demo.infrastructure.core.repository;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import pers.etherealss.demo.infrastructure.utils.DbSqlUtil;
import pers.etherealss.demo.infrastructure.utils.MapUtil;
import pers.etherealss.demo.infrastructure.utils.ReflectUtil;
import pers.etherealss.demo.infrastructure.utils.JdbcUtil;

import java.math.BigInteger;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 所有Dao类的公有父类，实现了CRUD的sql语句拼装，可以省去DAO类的常见CRUD方法
 * @param <T>
 * @author yohoyes
 */
public abstract class SimpleBaseMapper<T> implements BaseMapper<T> {

    /**
     * 获取表名（运用了模板方法模式的设计思想）
     * @return
     */
    public abstract String getTableName();

    /**
     * 拼接查询条件，由子类实现（模板方法模式）
     * @param po
     * @return
     */
    public abstract String getQueryCondition(T po);

    /**
     * 改
     * @param object
     * @return
     */
    @Override
    public int updateOne(T object) throws SQLException, IllegalAccessException {
        QueryRunner queryRunner = new QueryRunner(JdbcUtil.getDataSource());
        // {}是占位符，其中的需要表示索引
        String base = "update {0} {1} where {2}";
        // 参数列表
        List<Object> params = new ArrayList<>();
        // 用于update操作的sql片段，形如 set xxx = aaa , xxx = bbb
        String sqlFragment4Update = ReflectUtil.getSqlFragment(object, params);
        // 用于update操作的sql片段，形如 where xxx = yyy
        String queryCondition4Update = getQueryCondition(object);
        // 将参数插入到对应的{}占位符中，执行完毕后就是一条完整的sql语句
        String sql = MessageFormat.format(base, getTableName(),
                sqlFragment4Update, queryCondition4Update);
        // 通过queryRunner执行sql语句，返回参数
        return queryRunner.update(sql, params.toArray());
    }

    @Override
    public int deleteOne(int id) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JdbcUtil.getDataSource());
        String base = "delete from {0} where id = ?";
        String sql = MessageFormat.format(base, getTableName());
        return queryRunner.update(sql, new Object[]{id});
    }

    @Override
    public int deleteOne(T po) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JdbcUtil.getDataSource());
        String base = "delete from {0} where {1}";
        String sql = MessageFormat.format(base, getTableName(), getQueryCondition(po));
        return queryRunner.update(sql);
    }

    /**
     * 查
     * @param object
     * @return
     */
    @Override
    public T selectOne(T object) throws SQLException, IllegalAccessException, InstantiationException {
        String base = "select * from {0} where {1} ";
        String realSql = MessageFormat.format(base, getTableName(), getQueryCondition(object));
        return selectOne(object, realSql);
    }

    @Override
    public T selectOne(T object, String sql) throws SQLException, InstantiationException, IllegalAccessException {
        QueryRunner queryRunner = new QueryRunner(JdbcUtil.getDataSource());
        // 将sql查询结果保存在map中
        Map<String, Object> query = queryRunner.query(sql, new MapHandler());
        if (query == null) {
            return null;
        }
        // 通过反射，将map中的数据存到实体对象中
        object = MapUtil.modelMapper(object, ReflectUtil.getAllFields(object), query);
        return object;
    }

    /**
     * 查一堆
     * @param object
     * @return
     */
    @Override
    public List<T> selectObjectList(T object) throws IllegalAccessException, SQLException, InstantiationException {
        String base = "select * from {0} where {1}";
        String realSql = MessageFormat.format(base, getTableName(), getQueryCondition(object));
        return select(object, realSql);
    }

    @Override
    public List<T> select(T object, String sql) throws IllegalAccessException, InstantiationException, SQLException {
        QueryRunner queryRunner = new QueryRunner(JdbcUtil.getDataSource());
        List<T> res;
        List<Map<String, Object>> query = queryRunner.query(sql, new MapListHandler());
        if (query == null) {
            return null;
        }
        res = MapUtil.modelMapperForList(object, ReflectUtil.getAllFields(object), query);
        return res;
    }

    @Override
    public int insertOne(T object) throws SQLException {
        String base = "insert into {0} ({1}) values ({2})";
        List<Object> list = new ArrayList<>();
        String sql = MessageFormat.format(base, getTableName(), ReflectUtil.getSqlForInsert(object, list), DbSqlUtil.getQuestionForInsert(list.size()));
        QueryRunner queryRunner = new QueryRunner(JdbcUtil.getDataSource());
        BigInteger bigInteger;
        Object[] insert = queryRunner.insert(sql, new ArrayHandler(), list.toArray());
        if (insert.length >= 1) {
            bigInteger = (BigInteger) insert[0];
        } else {
            return 0;
        }
        return bigInteger.intValue();
    }
}
