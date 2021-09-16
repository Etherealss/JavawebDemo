package pers.etherealss.demo.core.mvc.exeption;

/**
 * @author wtk
 * @description
 * @date 2021-09-14
 */
public class BeanException extends RuntimeException {

    public BeanException() {
        super("【构造Bean异常】");
    }

    public BeanException(String message) {
        super("【构造Bean异常】" + message);
    }
}
