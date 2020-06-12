package com.reda.engine.center.dao;

import com.reda.engine.center.entity.Rule;

import java.sql.SQLException;
import java.util.List;

public interface RuleManageDao {
    void insert(Rule rule) ;

    List<Rule> selectAll() ;

    void delete(Rule rule) ;

    void update(Rule rule) ;
}
