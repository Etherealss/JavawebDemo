package pers.etherealss.demo.infrastructure.core.repository.annotation;

import java.lang.annotation.*;

/**
 * 定义id
 * @author yohoyes
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DbFieldId {
}
