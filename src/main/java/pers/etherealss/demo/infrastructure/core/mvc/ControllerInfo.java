package pers.etherealss.demo.infrastructure.core.mvc;

import lombok.Data;

import java.util.List;

/**
 * @author wtk
 * @description Controller所有的API信息
 * @date 2021-09-14
 */
@Data
public class ControllerInfo<T> {

    /**
     * 保存在BeanFactory的hashMap中的bean名称（key）
     */
    private String beanName;

    /**
     * controller的类对象
     */
    private Class<T> controllerClass;
    /**
     * Controller类本身的API信息
     */
    private HandlerInfo controllerHandlerInfo;
    /**
     * Controller的方法的API信息
     */
    private List<HandlerInfo> methodHandlerInfos;

    public ControllerInfo(String beanName, Class<T> controllerClass, HandlerInfo controllerHandlerInfo, List<HandlerInfo> methodHandlerInfos) {
        this.beanName = beanName;
        this.controllerClass = controllerClass;
        this.controllerHandlerInfo = controllerHandlerInfo;
        this.methodHandlerInfos = methodHandlerInfos;
    }

    public ControllerInfo() {
    }
}
