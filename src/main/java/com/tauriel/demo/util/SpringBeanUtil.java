package com.tauriel.demo.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author bluecrush
 * @project SmartWeb
 * @description Spring对象管理
 * @create 2018-04-02 下午2:16
 */
@Component
@ComponentScan
public class SpringBeanUtil implements ApplicationContextAware {

    /** Field description */
    private static ApplicationContext applicationContext;

    /**
     * 获取Spring上下文
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringBeanUtil.applicationContext == null) {
            SpringBeanUtil.applicationContext = applicationContext;
        }
    }

    /**
     * 通过类名获得bean对象
     *
     * @param clazz
     * @param <T>
     * @return T
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过bean名获得bean对象
     *
     * @param name
     * @return Object
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过bean名和类型获得bean对象
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return T
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
