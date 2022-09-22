package pers.etherealss.demo.infrastructure.core.repository.annotation;

import java.lang.annotation.*;

/**
 * 定义该注解，使bean对象的成员变量与对应表中的字段相对应
 * @author yohoyes
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DbField {

    /**
     * 该成员变量对应的数据库表中的字段名
     */
    String value();

    /**
     * 决定插入的时候是否需要插入该变量
     */
    boolean insertIgnore() default false;

    /**
     * 决定更改的时候是否需要更改该变量
     */
    boolean update() default true;
}
