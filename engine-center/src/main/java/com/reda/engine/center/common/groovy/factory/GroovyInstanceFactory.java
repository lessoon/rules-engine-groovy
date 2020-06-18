package com.reda.engine.center.common.groovy.factory;

import com.reda.engine.center.common.HttpClientUtil;
import com.reda.engine.center.common.ServiceRegisterClient;
import com.reda.engine.center.common.groovy.GroovyScriptExecute;
import com.reda.engine.center.dao.RuleManageDao;
import com.reda.engine.center.entity.Rule;
import com.reda.engine.center.common.groovy.exception.RuleException;
import groovy.lang.*;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GroovyInstanceFactory implements IGroovyInstanceFactory {
    private static final Logger log = LoggerFactory.getLogger(GroovyInstanceFactory.class);

    private final Map<String, GroovyObject> ruleObjectMap = new ConcurrentHashMap<>();

    @Autowired
    private Binding groovyBinding;
    private GroovyShell groovyShell;
    @Autowired
    private AutowireCapableBeanFactory beanFactory;
    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;
    @Autowired
    private RuleManageDao ruleManageDao;

    @Override
    @PostConstruct
    public void init() {
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader(this.getClass().getClassLoader());
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setSourceEncoding("utf-8");
        compilerConfiguration.setScriptBaseClass(GroovyScriptExecute.class.getName());
        groovyShell = new GroovyShell(groovyClassLoader, groovyBinding, compilerConfiguration);

        // 在项目启动时将 数据库中的脚本加载到 defaultListableBeanFactory 容器中
        List<Rule> rules = null;
        try {
            Rule rule = new Rule();
            rule.setRuleGroupName("规则组1");
            rules = ruleManageDao.selectByCondition(rule);
            rules.forEach(rule1 -> {
                String ruleName = rule1.getRuleName();
                String ruleContext = rule1.getRuleBody();
                registerRule(ruleContext, ruleName);
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            log.error("查询规则失败！");
        }

    }

    @Override
    public void registerRule(String scriptContent, String ruleName) {
//        Script groovy = groovyShell.parse(scriptContent);
//        defaultListableBeanFactory.registerSingleton(ruleName, groovy);
//        beanFactory.autowireBean(groovy);
//
//        // 将Groovy实例化到 (提供实例化的对象可以直接invokeMethod方法)
//        ClassLoader cl = GroovyInstanceFactory.class.getClassLoader();
//        GroovyClassLoader grvyCl = new GroovyClassLoader(cl);
//        Class groovyClass = grvyCl.parseClass(scriptContent);
//        try {
//            GroovyObject grvyObj = (GroovyObject) groovyClass.newInstance();
//            ruleObjectMap.put(ruleName, grvyObj);
//        } catch (InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                grvyCl.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        // 注册成功后将 通知给业务系统
        ServiceRegisterClient.registerRuleToService(ruleName);
    }

    @Override
    public void updateRule(String scriptContent, String ruleName) {
//        Script groovy = groovyShell.parse(scriptContent);
//        defaultListableBeanFactory.registerSingleton(ruleName, groovy);
//        beanFactory.autowireBean(groovy);
//
//        // 将Groovy实例化到 (提供实例化的对象可以直接invokeMethod方法)
//        ClassLoader cl = GroovyInstanceFactory.class.getClassLoader();
//        GroovyClassLoader grvyCl = new GroovyClassLoader(cl);
//        Class groovyClass = grvyCl.parseClass(scriptContent);
//        try {
//            GroovyObject grvyObj = (GroovyObject) groovyClass.newInstance();
//            ruleObjectMap.put(ruleName, grvyObj);
//        } catch (InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                grvyCl.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        // 注册成功后将 通知给业务系统
        ServiceRegisterClient.updateRuleToService(ruleName);
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

    /**
     * 带参调用 提供调用指定方法以及传参
     *
     * @param ruleName 规则名称
     * @param param    参数
     * @return 返回值
     * @throws RuleException RuleException 业务异常
     */
    @Override
    public Object execute(String ruleName, String methodName, Object param) throws RuleException {
        GroovyObject groovyObject = ruleObjectMap.get(ruleName);
        if (groovyObject != null) {
            return groovyObject.invokeMethod(methodName, param);
        } else {
            throw new RuleException("this ruleName is not exists in Groovy BeanFactory");
        }
    }

    @Override
    public void destroyBean(String ruleName) {
        defaultListableBeanFactory.destroySingleton(ruleName);
    }
}
