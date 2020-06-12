package com.reda.engine.center.service.impl;

import com.reda.engine.center.common.AbstractRedisBaseClient;
import com.reda.engine.center.dao.RuleManageDao;
import com.reda.engine.center.entity.Rule;
import com.reda.engine.center.exception.RuleException;
import com.reda.engine.center.factory.GroovyInstanceFactory;
import com.reda.engine.center.factory.IGroovyInstanceFactory;
import com.reda.engine.center.service.RuleManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.UUID;

@Service
public class RuleManageServiceImpl implements RuleManageService {
    private static final Logger log = LoggerFactory.getLogger(AbstractRedisBaseClient.class);

    @Autowired
    private RuleManageDao ruleManageDao;

    @Autowired
    private IGroovyInstanceFactory groovyInstanceFactory;

    private static Jedis jedis;

    @Override
    public List<Rule> findAllRule() {
        return ruleManageDao.selectAll();
    }

    @Override
    public void createRule(Rule rule) throws RuleException {
        if (AbstractRedisBaseClient.exists(rule.getRuleName())) {
            log.error("duplicate rule：rename or check out ！");
            throw new RuleException("duplicate rule：rename or check out ！");
        }
        rule.setUuid(UUID.randomUUID().toString().trim().replaceAll("-", ""));
        ruleManageDao.insert(rule);
        groovyInstanceFactory.registerRule(rule.getRuleBody(), rule.getRuleName());
        AbstractRedisBaseClient.set(rule.getRuleName(), rule.getRuleBody());

    }

    @Override
    public void updateRule(Rule rule) {
        try {
            ruleManageDao.update(rule);
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
        if (jedis.exists(rule.getRuleName())) {
            jedis.del(rule.getRuleName());
        }
    }

}

