package pers.etherealss.demo.core.mvc.annotation;

import pers.etherealss.demo.core.mvc.enums.RequestType;

import java.lang.annotation.*;

/**
 * @author wtk
 * @description 用于表示Controller类和方法处理的请求的注解。
 * @date 2021-08-27
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServletHandler {

    /**
     * 等价于urlPatterns注解
     * @return
     */
    String[] value();

    /**
     * Controller或其方法所处理的请求的访问路径
     * 注意，该功能实现时，并没有检查指定的url是否会重复。
     * 在使用时不要填写重复的url，否则可能会跳转到不理想的方法中。
     * 如果觉得有需要，可以自己改进
     * @return
     */
    String[] urlPatterns() default {};

    /**
     * 请求方式，默认支持4种常见的请求方式。HTTP请求一共有8个
     * @return
     */
    String[] type() default {RequestType.GET, RequestType.POST, RequestType.PUT, RequestType.DELETE};

    /**
     * 优先级
     * @return
     */
    int priority() default Integer.MAX_VALUE;
}
