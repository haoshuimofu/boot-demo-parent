package com.demo.boot.dao.codegen.rules;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.internal.rules.ConditionalModelRules;

/**
 * 自定义修改table的生成rules
 *
 * @Author wude
 * @Create 2019-04-09 14:04
 */
public class SimpleConditionalModelRules extends ConditionalModelRules {

    public SimpleConditionalModelRules(IntrospectedTable introspectedTable) {
        // super.isModelOnly = false; final不能修改
        super(introspectedTable);
    }


    @Override
    public boolean generateExampleClass() {
        return super.generateExampleClass();
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