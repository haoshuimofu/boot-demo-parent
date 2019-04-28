package com.demo.boot.dao.codegen.rules;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.internal.rules.BaseRules;
import org.mybatis.generator.internal.rules.ConditionalModelRules;

/**
 * 自定义修改table的生成rules
 *
 * @Author wude
 * @Create 2019-04-09 14:04
 */
public class SimpleConditionalModelRules extends BaseRules {

    private ConditionalModelRules conditionalModelRules;

    public SimpleConditionalModelRules(IntrospectedTable introspectedTable) {
        // super.isModelOnly = false; final不能修改
        super(introspectedTable);
        conditionalModelRules = new ConditionalModelRules(introspectedTable);
    }


    @Override
    public boolean generateExampleClass() {
        return false;
    }

    @Override
    public boolean generatePrimaryKeyClass() {
        return conditionalModelRules.generatePrimaryKeyClass();
    }

    @Override
    public boolean generateBaseRecordClass() {
        return conditionalModelRules.generateBaseRecordClass();
    }

    /**
     * 不单独生成ModelWithBLOBs类
     *
     * @return
     */
    @Override
    public boolean generateRecordWithBLOBsClass() {
        return false;
    }
}