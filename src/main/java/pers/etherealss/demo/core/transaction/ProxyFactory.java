package pers.etherealss.demo.core.transaction;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author wtk
 * @description 生产代理对象的工厂
 * @date 2021-09-14
 */
@SuppressWarnings("unchecked")
public class ProxyFactory {
    /**
     * 获取代理后的对象
     * @param clazz        类对象
     * @param proxyWrapper 包裹执行对象的实现接口
     * @param <T>          泛型
     * @return 创建一个被代理后的对象
     */
    private static <T> T getInstance(Class<T> clazz, InvocationHandler proxyWrapper) {
        //JDK动态代理
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                clazz.getInterfaces(),
                proxyWrapper
        );
    }

    /**
     * 获取事务的代理对象
     * @param toBeProxy 要代理的对象
     * @param <T>
     * @return 被代理后的对象
     */
    public static <T> T getProxyForTransaction(T toBeProxy) {
        // 传入要代理的对象，获取被代理后的对象
        return (T) getInstance(toBeProxy.getClass(),
                ServiceTransactionProxy.create(toBeProxy));
    }

    /**
     * 获取事务的代理对象
     * @param clazz 要代理的对象类型
     * @param <T>
     * @return 被代理后的对象
     */
    public static <T> T getProxyForTransaction(Class<T> clazz)
            throws IllegalAccessException, InstantiationException {
        return getInstance(clazz, ServiceTransactionProxy.create(clazz.newInstance()));
    }
}
