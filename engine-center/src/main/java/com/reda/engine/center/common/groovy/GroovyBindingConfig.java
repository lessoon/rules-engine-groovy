package com.reda.engine.center.common.groovy;

import groovy.lang.Binding;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class GroovyBindingConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Bean("groovyBinding")
    public Binding groovyBinding() {
        Map<String, Object> beanMap = applicationContext.getBeansOfType(Object.class);
        //如果不需要对bean做过滤，直接用beanMap构造Binding对象即可
        return new Binding(beanMap);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
