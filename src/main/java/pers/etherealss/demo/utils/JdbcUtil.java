package pers.etherealss.demo.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author wtk
 * @description
 * @date 2021-09-14
 */
public class JdbcUtil {
    /** 数据库DataSource */
    private static DataSource ds = null;
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    static {
        InputStream is = null;
        Properties pros;
        try {
            pros = new Properties();
            is = JdbcUtil.class.getClassLoader().getResourceAsStream("druid.properties");
            pros.load(is);
            ds = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.close(is);
        }
    }

    public static DataSource getDataSource() {
        return ds;
    }

    /**
     * 获取数据库连接
     * @return Connection 数据库的连接
     */
    public static Connection getConnection() throws Exception {
        // 从threadLocal中获取连接
        Connection conn = threadLocal.get();
        //如果是第一次，就创建一个连接
        if (conn == null) {
            conn = ds.getConnection();
            //添加到本地的线程变量
            threadLocal.set(conn);
        }
        return conn;
    }

    /**
     * 开启事务
     * setAutoCommit总的来说就是保持数据的完整性，一个系统的更新操作可能要涉及多张表，需多个SQL语句进行操作
     * 循环里连续的进行插入操作，如果你在开始时设置了：conn.setAutoCommit(false);
     * 最后才进行conn.commit(),这样你即使插入的时候报错，修改的内容也不会提交到数据库，
     * @throws Exception
     */
    public static Connection beginTransaction() throws Exception {
        Connection conn = getConnection();
        //关闭自动提交，开启事务
        conn.setAutoCommit(false);
        return conn;
    }

    /**
     * 提交事务
     * @throws Exception
     */
    public static void commitTransaction() throws Exception {
        Connection conn = getConnection();
        if (conn != null) {
            conn.commit();
        }
    }

    /**
     * 回滚事务
     * @throws Exception
     */
    public static void rollbackTransaction() throws Exception {
        Connection conn = getConnection();
        if (conn != null) {
            conn.rollback();
        }
    }

    /**
     * 结束事务，关闭连接
     * @throws Exception
     */
    public static void closeTransaction() throws Exception {
        Connection conn = getConnection();
        if (conn != null) {
            conn.close();
        }
        threadLocal.remove();
    }

    /**
     * 开启事务，用于测试，在正式代码中不要使用
     * @throws Exception
     */
    public static Connection beginTransactionForTest() throws Exception {
        Connection conn = getConnection();
        //关闭自动提交，开启事务
        conn.setAutoCommit(true);
        return conn;
    }
}
