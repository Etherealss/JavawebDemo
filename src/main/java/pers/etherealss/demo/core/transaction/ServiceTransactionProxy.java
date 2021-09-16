package pers.etherealss.demo.core.transaction;

import pers.etherealss.demo.utils.JdbcUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author wtk
 * @description 动态代理（代理模式）
 * @date 2021-09-14
 */
public class ServiceTransactionProxy implements InvocationHandler {
    /**
     * 构造方法私有化，提供静态方法创建对象
     * @param toBeProxy 要代理的事物对象
     * @param <T>
     * @return 代理对象
     */
    public static <T> ServiceTransactionProxy create(T toBeProxy) {
        return new ServiceTransactionProxy(toBeProxy);
    }

    /** 要代理的对象 */
    private final Object serviceRunner;

    /**
     * 构造方法私有化，传入要代理的事物对象
     */
    private ServiceTransactionProxy(Object serviceRunner) {
        this.serviceRunner = serviceRunner;
    }

    /**
     * 反射调用Service方法，代理了获取连接、事务回滚、关闭连接的等工作
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object value;
        try {
            // 将受代理的事务执行
            // 创建并获取数据库连接，并设置为手动提交，开启事务
            JdbcUtil.beginTransaction();
            // 执行对应service方法，在service中会再次获取上面创建的连接
            // 获取service执行的结果
            value = method.invoke(serviceRunner, args);
        } catch (Exception ex) {
            // 出现异常，事务回滚
            System.err.println("Service出现异常了！回滚");
            JdbcUtil.rollbackTransaction();
            throw ex;
        } finally {
            // 事务提交
            JdbcUtil.commitTransaction();
            // 事务完毕，结束事务，释放连接
            JdbcUtil.closeTransaction();
        }
        return value;
    }
}
