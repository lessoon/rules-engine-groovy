package com.reda.engine.center.dao;

import com.reda.engine.center.entity.Rule;

import java.sql.SQLException;
import java.util.List;

public interface RuleManageDao {
    void insert(Rule rule) throws SQLException;

    List<Rule> selectAll() throws SQLException;

    List<Rule> selectByCondition(Rule rule) throws SQLException;

    void delete(Rule rule) throws SQLException;

    void update(Rule rule) throws SQLException;
}
