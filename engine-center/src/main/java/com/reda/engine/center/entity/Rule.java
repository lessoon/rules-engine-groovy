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
    private String uuid;
    private String ruleName;
    private String ruleBody;
    private String ruleStatus;
    private String createBy;
    private Date createDate;
    private Date updateDate;
}
