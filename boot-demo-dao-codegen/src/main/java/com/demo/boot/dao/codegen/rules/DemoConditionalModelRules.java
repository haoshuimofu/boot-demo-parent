package com.demo.boot.dao.codegen.rules;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.internal.rules.ConditionalModelRules;

/**
 * 自定义修改table的生成rules
 * <p>参考：Introspected.initialize，无法自定义</p>
 *
 * @Author wude
 * @Create 2019-04-09 14:04
 */
@Deprecated
public class DemoConditionalModelRules extends ConditionalModelRules {

    public DemoConditionalModelRules(IntrospectedTable introspectedTable) {
        super(introspectedTable);
    }

    @Override
    public boolean generateExampleClass() {
        return false;
    }
}