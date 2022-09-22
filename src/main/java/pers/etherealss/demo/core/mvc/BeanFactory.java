package pers.etherealss.demo.core.mvc;

import pers.etherealss.demo.core.mvc.exeption.BeanException;

import java.util.HashMap;
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
            // ConcurrentHashMap保证线程安全
            CACHE.putIfAbsent(name, sharedInstance);
            return sharedInstance;
        } catch (Exception e) {
            throw new BeanException("通过反射调用构造器构造对象失败");
        }
    }
}
