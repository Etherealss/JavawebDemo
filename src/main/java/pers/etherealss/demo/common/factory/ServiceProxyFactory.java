package pers.etherealss.demo.common.factory;

import pers.etherealss.demo.core.transaction.ProxyFactory;
import pers.etherealss.demo.service.UserService;
import pers.etherealss.demo.service.impl.UserServiceImpl;

/**
 * @author wtk
 * @description 获取代理Service的工厂，涉及工厂模式，隐藏了对象创建过程
 * @date 2021-09-14
 */
public class ServiceProxyFactory {

    /**
     * 获取代理后的用户Service
     * @return 经过代理的用户Service对象
     */
    public static UserService getUserService() {
        return ProxyFactory.getProxyForTransaction(new UserServiceImpl());
    }

}
