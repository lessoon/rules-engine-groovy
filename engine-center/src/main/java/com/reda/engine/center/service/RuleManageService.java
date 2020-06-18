package com.reda.engine.center.service;

import com.reda.engine.center.entity.Rule;
import com.reda.engine.center.common.groovy.exception.RuleException;

import java.util.List;

public interface RuleManageService {
    List<Rule> findAllRule();

    void createRule(Rule rule) throws RuleException;

    void updateRule(Rule rule);

    void runRule(Rule rule) throws RuleException;

    Object runRule(Rule rule,Object param) throws RuleException;

    void deleteRule(Rule rule);

    void stopRule(Rule rule);
}
