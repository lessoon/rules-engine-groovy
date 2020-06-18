package com.reda.engine.center.common;

/**
 * 功能概述：
 *
 * @author Lidu
 * @version 1.0
 * <p>Date: 2020-06-17 15-03
 * <p>Copyright: Copyright(c)2020 RedaFlight.com All Rights Reserved
 * @since JDK 1.7
 */
public class ServiceRegisterClient {
    public static final String url_register = "http://127.0.0.1:8080/rule/manager/register";
    public static final String url_update = "http://127.0.0.1:8080/rule/manager/update";

    public static void registerRuleToService(String ruleName){
        HttpClientUtil.sendHttpPost(url_register,ruleName);
    }

    public static void updateRuleToService(String ruleName){
        HttpClientUtil.sendHttpPost(url_update,ruleName);
    }

}
