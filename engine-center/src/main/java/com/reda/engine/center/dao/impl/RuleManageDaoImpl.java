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
    private static final Logger log = LoggerFactory.getLogger(AbstractRedisBaseClient.class);

    String TABLE_NAME = "rule_table";

    @Override
    public void insert(Rule rule) {
        Entity entity = new Entity().parseBean(rule, true, true);
        entity.setTableName(TABLE_NAME);
        try {
            use.insert(entity);
        } catch (SQLException throwables) {
            log.error("保存报错！", throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Rule> selectAll() {
        Entity entity = new Entity();
        entity.setTableName(TABLE_NAME);
        try {
            return use.findAll(entity, Rule.class);
        } catch (SQLException throwables) {
            log.error("查询报错！", throwables.getMessage());
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Rule rule) {
        try {
            use.del(TABLE_NAME, "uuid", rule.getUuid());
        } catch (SQLException throwables) {
            log.error("删除报错！", throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(Rule rule) {
        Entity record = new Entity();
        record.setTableName(TABLE_NAME);
        Entity where = new Entity().parseBean(rule, true, true);
        try {
            use.update(record, where);
        } catch (SQLException throwables) {
            log.error("修改报错！", throwables.getMessage());
            throwables.printStackTrace();
        }
    }
}
