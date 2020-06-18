package com.reda.engine.center.common;

import cn.hutool.db.nosql.redis.RedisDS;
import com.reda.engine.center.common.groovy.exception.RuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * 功能概述：
 *
 * @author Lidu
 * @version 1.0
 * <p>Date: 2020-06-12 11-10
 * <p>Copyright: Copyright(c)2019 RedaFlight.com All Rights Reserved
 * @since JDK 1.7
 */
public abstract class AbstractRedisBaseClient {
    private static final Logger log = LoggerFactory.getLogger(AbstractRedisBaseClient.class);

    private AbstractRedisBaseClient() {

    }

    static {
        JEDIS = RedisDS.create().getJedis();
    }

    static final Jedis JEDIS;


    public static void set(String key, String value) throws RuleException {
        if (!"OK".equals(JEDIS.set(key, value))) {
            log.error("reids set fail!");
            throw new RuleException("reids set fail!");
        }
    }

    public static boolean exists(String key) {
        return JEDIS.exists(key);
    }

    public static boolean del(String key) {
        return JEDIS.del(key) == 1L;
    }

}
