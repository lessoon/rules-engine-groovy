package com.reda.engine.center.factory;

import com.reda.engine.center.exception.RuleException;
import org.springframework.stereotype.Service;

public interface IGroovyInstanceFactory {
    void init();

    void registerRule(String scriptContent, String ruleName);

    void execute(String ruleName) throws RuleException;

    Object execute(String ruleName,Object Param) throws RuleException;

    void destroyBean(String ruleName);
}
