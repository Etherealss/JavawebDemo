package pers.etherealss.demo.listener;

import lombok.extern.slf4j.Slf4j;
import pers.etherealss.demo.core.mvc.ControllerScanner;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author wtk
 * @description
 * @date 2021-08-27
 */
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
