package com.reda.engine.center;

import cn.hutool.db.Entity;
import com.reda.engine.center.common.AbstractDataBaseClient;


Entity entity = new Entity();
entity.setTableName("rule_table");
print(AbstractDataBaseClient.use.findAll(entity))
