package com.reda.engine.center.factory;

import com.reda.engine.center.component.GroovyScript;
import com.reda.engine.center.exception.RuleException;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class GroovyInstanceFactory implements IGroovyInstanceFactory {
    @Autowired
    private Binding groovyBinding;
    private GroovyShell groovyShell;
    @Autowired
    private AutowireCapableBeanFactory beanFactory;
    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;


    @Override
    @PostConstruct
    public void init() {
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader(this.getClass().getClassLoader());
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setSourceEncoding("utf-8");
        compilerConfiguration.setScriptBaseClass(GroovyScript.class.getName());
        groovyShell = new GroovyShell(groovyClassLoader, groovyBinding, compilerConfiguration);
    }

    @Override
    public void registerRule(String scriptContent, String ruleName) {
        Script groovy = groovyShell.parse(scriptContent);
        defaultListableBeanFactory.registerSingleton(ruleName, groovy);
        beanFactory.autowireBean(groovy);
    }

    @Override
    public void execute(String ruleName) throws RuleException {
        Script rule = (Script) defaultListableBeanFactory.getSingleton(ruleName);
        if (rule != null) {
            rule.run();
        } else {
            throw new RuleException("this ruleName is not exists in Groovy BeanFactory");
        }
    }

    @Override
    public Object execute(String ruleName,Object Param) throws RuleException {
        Script rule = (Script) defaultListableBeanFactory.getSingleton(ruleName);
        if (rule != null) {
            return rule.run();
        } else {
            throw new RuleException("this ruleName is not exists in Groovy BeanFactory");
        }
    }

    @Override
    public void destroyBean(String ruleName) {
        defaultListableBeanFactory.destroySingleton(ruleName);
    }
}
