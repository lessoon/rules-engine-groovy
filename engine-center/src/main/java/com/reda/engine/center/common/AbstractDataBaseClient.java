package com.reda.engine.center.common;

import cn.hutool.db.Db;
import cn.hutool.db.ds.DSFactory;
import cn.hutool.setting.Setting;

/**
 * 功能概述：
 *
 * @author Lidu
 * @version 1.0
 * <p>Date: 2020-06-12 10-20
 * <p>Copyright: Copyright(c)2019 RedaFlight.com All Rights Reserved
 * @since JDK 1.7
 */
public abstract class AbstractDataBaseClient {

    public static Db use;

    static {
        //自定义数据库Setting，更多实用请参阅Hutool-Setting章节
        Setting setting = new Setting("db/db.setting");
        //获取指定配置，第二个参数为分组，用于多数据源，无分组情况下传null
        DSFactory ds = DSFactory.create(setting);
        use = new Db(ds.getDataSource());
    }

}
