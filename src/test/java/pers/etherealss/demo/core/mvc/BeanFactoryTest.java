package pers.etherealss.demo.core.mvc;

import junit.framework.TestCase;
import pers.etherealss.demo.controller.UserController;
import pers.etherealss.demo.infrastructure.core.mvc.BeanFactory;

public class BeanFactoryTest extends TestCase {

    public void testGetBean() {
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> {
                UserController userController = BeanFactory.getBean("userController", UserController.class);
                System.out.println(userController);
                UserController userController2 = (UserController) BeanFactory.getBean("userController");
                System.out.println(userController2);
            });
            thread.start();
        }
    }

    public void testTestGetBean() {
    }

    public void testTestGetBean1() {
    }

    public void testTestGetBean2() {
    }
}