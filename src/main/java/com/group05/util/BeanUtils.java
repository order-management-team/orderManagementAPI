package com.group05.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;

        System.out.println("AuditServiceImpl registrado: " + context.containsBean("auditServiceImpl"));
    }

    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

}
