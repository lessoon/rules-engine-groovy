package com.reda.engine.center.dao.impl;

import cn.hutool.db.Entity;
import com.reda.engine.center.common.AbstractDataBaseClient;
import com.reda.engine.center.common.AbstractRedisBaseClient;
import com.reda.engine.center.dao.RuleManageDao;
import com.reda.engine.center.entity.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.*;

@Repository
public class RuleManageDaoImpl extends AbstractDataBaseClient implements RuleManageDao {
    private static final Logger log = LoggerFactory.getLogger(RuleManageDaoImpl.class);

    String TABLE_NAME = "rule_table";

    @Override
    public void insert(Rule rule) throws SQLException {
        Entity entity = new Entity().parseBean(rule, false, true);
        entity.setTableName(TABLE_NAME);
        use.insert(entity);
    }

    @Override
    public List<Rule> selectAll() throws SQLException {
        Entity entity = new Entity();
        entity.setTableName(TABLE_NAME);
        return use.findAll(entity, Rule.class);
    }

    @Override
    public List<Rule> selectByCondition(Rule rule) throws SQLException {
        Entity entity = new Entity().parseBean(rule);
        entity.setTableName(TABLE_NAME);
        return use.findAll(entity, Rule.class);
    }

    @Override
    public void delete(Rule rule) throws SQLException {
        use.del(TABLE_NAME, "uuid", rule.getUuid());

    }

    @Override
    public void update(Rule rule) throws SQLException {
        rule.setUuid(UUID.randomUUID().toString().trim().replace("-", ""));
        Entity record = new Entity().parseBean(rule, false, true);
        record.setTableName(TABLE_NAME);
        Entity where = new Entity();
        where.set("ruleName", rule.getRuleName());
        where.setTableName(TABLE_NAME);
        use.del(where);
        use.insert(record);
    }
}
