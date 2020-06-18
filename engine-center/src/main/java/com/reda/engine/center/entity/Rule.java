package com.reda.engine.center.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author : Zhuang Jialong
 * @description : 规则实体
 * @date : 2020/6/10 4:03 PM
 * @Copyright: Copyright(c)2019 RedaFlight.com All Rights Reserved
 */
@Data

public class Rule {
    /** uuid **/
    private String uuid;
    /** 规则组ID **/
    private String ruleGroupId;
    /** 规则组名称 **/
    private String ruleGroupName;
    /** 规则名称 **/
    private String ruleName;
    /** 规则方法名 **/
    private String ruleMethodName;
    /** 规则体 **/
    private String ruleBody;
    /** 规则状态 **/
    private String ruleStatus;
    /** 创建者 **/
    private String createBy;
    /** 创建时间 **/
    private Date createDate;
    /** 更新时间 **/
    private Date updateBy;
    /** 更新时间 **/
    private Date updateDate;
    /** 参数 **/
    private String param;
}
