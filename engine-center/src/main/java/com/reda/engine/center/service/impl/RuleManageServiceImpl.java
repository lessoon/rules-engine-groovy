package com.reda.engine.center.service.impl;

import com.reda.engine.center.common.AbstractRedisBaseClient;
import com.reda.engine.center.dao.RuleManageDao;
import com.reda.engine.center.entity.Rule;
import com.reda.engine.center.common.groovy.exception.RuleException;
import com.reda.engine.center.common.groovy.factory.IGroovyInstanceFactory;
import com.reda.engine.center.service.RuleManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
public class RuleManageServiceImpl implements RuleManageService {
    private static final Logger log = LoggerFactory.getLogger(RuleManageServiceImpl.class);

    @Autowired
    private RuleManageDao ruleManageDao;

    @Autowired
    private IGroovyInstanceFactory groovyInstanceFactory;

    @Override
    public List<Rule> findAllRule() {
        try {
            return ruleManageDao.selectAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void createRule(Rule rule) throws RuleException {
        if (AbstractRedisBaseClient.exists(rule.getRuleName())) {
            log.error("duplicate rule：rename or check out ！");
            throw new RuleException("duplicate rule：rename or check out ！");
        }
        rule.setUuid(UUID.randomUUID().toString().trim().replace("-", ""));
        try {
            ruleManageDao.insert(rule);
            groovyInstanceFactory.registerRule(rule.getRuleBody(), rule.getRuleName());
            AbstractRedisBaseClient.set(rule.getRuleName(), rule.getRuleBody());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateRule(Rule rule) {
        try {
            ruleManageDao.update(rule);
            groovyInstanceFactory.updateRule(rule.getRuleBody(), rule.getRuleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void runRule(Rule rule) throws RuleException {
        if (AbstractRedisBaseClient.exists(rule.getRuleName())) {
            groovyInstanceFactory.execute(rule.getRuleName());
        } else {
            throw new RuleException("规则不存在！");
        }
    }

    @Override
    public Object runRule(Rule rule, Object param) throws RuleException {
        if (AbstractRedisBaseClient.exists(rule.getRuleName())) {
            return groovyInstanceFactory.execute(rule.getRuleName(),rule.getRuleMethodName(),param);
        } else {
            throw new RuleException("规则不存在！");
        }
    }

    @Override
    public void deleteRule(Rule rule) {
        try {
            ruleManageDao.delete(rule);
            groovyInstanceFactory.destroyBean(rule.getRuleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopRule(Rule rule) {
        if (AbstractRedisBaseClient.exists(rule.getRuleName())) {
            AbstractRedisBaseClient.del(rule.getRuleName());
        }
    }

}

