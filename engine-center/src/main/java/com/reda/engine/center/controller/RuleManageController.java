package com.reda.engine.center.controller;

import cn.hutool.json.JSONUtil;
import com.reda.engine.center.entity.Rule;
import com.reda.engine.center.common.groovy.exception.RuleException;
import com.reda.engine.center.service.RuleManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//fixme RestController和Controller区别
//restController相当于ResponseBody＋ @Controller的作用
@RestController
//@Controller
@RequestMapping("rule/manage")
public class RuleManageController {

    @Autowired
    private RuleManageService ruleManageService;


    /**
     * 规则创建
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public String findAll() {
        return JSONUtil.toJsonStr(ruleManageService.findAllRule());
    }

    /**
     * 规则创建
     *
     * @param rule 规则信息
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createRule(@RequestBody Rule rule) {
        try {
            ruleManageService.createRule(rule);
            return "success";
        } catch (RuleException e) {
            e.printStackTrace();
            return "fail";
        }
    }

    /**
     * 规则更新
     *
     * @param value 规则ID
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateRule(@RequestBody Rule value) {
        try {
            ruleManageService.updateRule(value);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    /**
     * 运行规则
     *
     * @param value 规则ID
     */
    @RequestMapping(value = "/run", method = RequestMethod.POST)
    public void runRule(@RequestBody Rule value) {
        try {
            ruleManageService.runRule(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 运行规则
     *
     * @param value 规则ID
     */
    @RequestMapping(value = "/runRuleMethod", method = RequestMethod.POST)
    public String runRuleMethod(@RequestBody Rule value) {
        try {
            Object o = ruleManageService.runRule(value, value.getParam());
            return JSONUtil.toJsonStr(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 停止规则
     *
     * @param value 规则ID
     */
    @RequestMapping(value = "/stop", method = RequestMethod.POST)
    public void stopRule(@RequestBody Rule value) {
        ruleManageService.stopRule(value);
    }

    /**
     * 删除规则
     *
     * @param value
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void deleteRule(@RequestBody Rule value) {
        ruleManageService.deleteRule(value);

    }
}
