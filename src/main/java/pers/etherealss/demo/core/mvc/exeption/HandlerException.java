package pers.etherealss.demo.core.mvc.exeption;

/**
 * @author wtk
 * @description
 * @date 2021-09-16
 */
public class HandlerException extends Exception {

    public HandlerException() {
        super("Handler异常");
    }

    public HandlerException(String message) {
        super("Handler异常：" + message);
    }
}
