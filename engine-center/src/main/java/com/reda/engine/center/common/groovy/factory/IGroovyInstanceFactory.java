package com.reda.engine.center.common.groovy.factory;

import com.reda.engine.center.common.groovy.exception.RuleException;

public interface IGroovyInstanceFactory {
    void init();

    void registerRule(String scriptContent, String ruleName);

    void updateRule(String scriptContent, String ruleName);

    void execute(String ruleName) throws RuleException;

    Object execute(String ruleName, String methodName, Object Param) throws RuleException;

    void destroyBean(String ruleName);
}
