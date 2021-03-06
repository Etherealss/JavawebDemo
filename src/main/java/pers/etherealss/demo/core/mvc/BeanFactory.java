package pers.etherealss.demo.core.mvc;

import pers.etherealss.demo.core.mvc.exeption.BeanException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wtk
 * @description Bean指的是一类Java对象，源于Spring框架
 * 在这里，Bean指的就是实例化的Controller对象，它们是单例的，在需要的时候就从这里获取
 * 所以这个类有创造和存储两个功能
 *
 * @date 2021-09-14
 */
public class BeanFactory {

    /**
     * 保存所有Bean对象的类
     */
    private static final Map<String, Object> CACHE = new HashMap<>();

    public static Object getBean(String name) {
        return getBean(name, null, null);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return getBean(name, requiredType, null);
    }

    /**
     * 单例模式获取bean对象，比如Controller对象
     * 如果没有则会通过requiredType实例化，没有提供requiredType则会报错
     * 涉及到了单例模式的双重检查
     * @param name
     * @param requiredType
     * @param args
     * @param <T>
     * @return
     * @throws Throwable
     */
    public static <T> T getBean(String name, Class<T> requiredType, Object[] args) {
        T sharedInstance = null;
        // 从缓存中获取对象（加锁）
        synchronized (CACHE) {
            sharedInstance = (T) CACHE.get(name);
        }
        // 如果能获取到，就直接返回
        if (sharedInstance != null) {
            return sharedInstance;
        }
        // 获取不到对象，则需要创建对象
        // 此处没有提供创建对象的class参数，报错
        if (requiredType == null) {
            throw new BeanException("该对象尚未创建，请提供对象的class参数以供初始化");
        }
        // 有提供参数，则通过反射创建对象。这里用到了单例模式的双重检查
        synchronized (CACHE) {
            // 在你获取到锁之后，可能对象已经创建好了，需要再次判断，没有对象则创建
            if (CACHE.get(name) == null) {
                try {
                    sharedInstance = requiredType.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new BeanException("通过反射调用构造器构造对象失败");
                }
                CACHE.put(name, sharedInstance);
            }
        }
        return sharedInstance;
    }
}
