package pers.etherealss.demo.infrastructure.core.mvc;

import pers.etherealss.demo.infrastructure.core.mvc.exeption.BeanException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wtk
 * @description Bean指的是一类Java对象
 * 在这里，Bean指的就是实例化的Controller对象，它们是单例的，在需要的时候就从这里获取
 * 所以这个类有创造和存储两个功能
 * @date 2021-09-14
 */
public class BeanFactory {

    /**
     * 保存所有Bean对象的类
     */
    private static final Map<String, Object> CACHE = new ConcurrentHashMap<>();

    public static Object getBean(String name) {
        return getBean(name, null, null);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return getBean(name, requiredType, null);
    }

    /**
     * 单例模式获取bean对象，比如Controller对象
     * 如果没有则会通过requiredType实例化，没有提供requiredType则会报错
     * @param name
     * @param requiredType 用于实例化对象
     * @param args
     * @param <T>
     * @return
     * @throws Throwable
     */
    public static <T> T getBean(String name, Class<T> requiredType, Object[] args) {
        // 从缓存中尝试获取单例
        T sharedInstance = (T) CACHE.get(name);
        // 如果能获取到，就直接返回
        if (sharedInstance != null) {
            return sharedInstance;
        }
        // 获取不到对象，则需要创建对象
        // 此处没有提供创建对象的class参数，报错
        if (requiredType == null) {
            throw new BeanException("该对象尚未创建，请提供对象的class参数以供初始化");
        }
        try {
            sharedInstance = requiredType.getDeclaredConstructor().newInstance();
            CACHE.putIfAbsent(name, sharedInstance);
            // 避免并发导致重复创建对象。
            // 如果直接返回 sharedInstance，而后续线程并发更新了 CACHE 里的对象，
            // 那就相当于有了两个“单例”：sharedInstance 和 CACHE里的对象
            // 而 CACHE.putIfAbsent 具有原子性，可以保证从头到尾只有一个对象进入 CACHE
            // 直接返回 CACHE 里的对象可以保证只有一个对象被外界使用到
            return (T) CACHE.get(name);
        } catch (Exception e) {
            throw new BeanException("通过反射调用构造器构造对象失败");
        }
    }
}
