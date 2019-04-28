package com.demo.boot.dao.codegen;

import com.demo.boot.dao.codegen.generator.DemoXmlMapperGenerator;
import com.demo.boot.dao.codegen.rules.SimpleConditionalModelRules;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;
import org.mybatis.generator.internal.rules.Rules;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.List;

/**
 * 修改Mybatis3 IntrospectedTable实现
 *
 * @Author wude
 * @Create 2017-06-14 10:29
 */
public class DemoIntrospectedTableMyBatis3Impl extends IntrospectedTableMyBatis3Impl {

    public DemoIntrospectedTableMyBatis3Impl() {
        super();
    }

    /**
     * <p>修改IntrospectedTable.initialize, 主要是修改rules</p>
     * See {@link IntrospectedTable#initialize()}
     */
    @Override
    public void initialize() {
        super.initialize();
        // 用自定义的Rules
        rules = new SimpleConditionalModelRules(this);
    }

    /**
     * <p>这里在table添加column的时候设置一下generatedAlways</p>
     * <p>目的：针对自增长列设置generatedAlways=true，这样自增长列就不会出现在insert语句中了</p>
     *
     * @param introspectedColumn
     */
    @Override
    public void addColumn(IntrospectedColumn introspectedColumn) {
        if (introspectedColumn.isAutoIncrement()) {
            introspectedColumn.setGeneratedAlways(true);
        }
        super.addColumn(introspectedColumn);
    }

    /**
     * 直接设置modelType-conditional
     * <p>作用类似在generatorConfig.xml中针对具体表设置<table tableName="t_users" domainObjectName="User" modelType="conditional"/></p>
     * <p>各种ModelType定义区别参考@See {@link org.mybatis.generator.config.ModelType}</p>
     * <p>{@link com.demo.boot.dao.codegen.DemoIntrospectedTableMyBatis3Impl#calculateJavaModelGenerators(java.util.List, org.mybatis.generator.api.ProgressCallback)}</p>
     *
     * @param rules
     */
    @Override
    public void setRules(Rules rules) {
        SimpleConditionalModelRules realRules = new SimpleConditionalModelRules(this);
        super.setRules(realRules);
    }

    /**
     * Calculate xml mapper generator.
     *
     * @param javaClientGenerator the java client generator
     * @param warnings            the warnings
     */
    @Override
    protected void calculateXmlMapperGenerator(AbstractJavaClientGenerator javaClientGenerator, List<String> warnings, ProgressCallback progressCallback) {
        // 自定义XmlMapperGenerator实现
        this.xmlMapperGenerator = new DemoXmlMapperGenerator();
        initializeAbstractGenerator(xmlMapperGenerator, warnings, progressCallback);
    }

    /**
     * 将ModelMapper接口修改成ModelDao
     */
    @Override
    protected void calculateJavaClientAttributes() {
        if (this.context.getJavaClientGeneratorConfiguration() != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.calculateJavaClientImplementationPackage());
            sb.append('.');
            sb.append(this.fullyQualifiedTable.getDomainObjectName());
            sb.append("DAOImpl");
            this.setDAOImplementationType(sb.toString());
            sb.setLength(0);
            sb.append(this.calculateJavaClientInterfacePackage());
            sb.append('.');
            sb.append(this.fullyQualifiedTable.getDomainObjectName());
            sb.append("DAO");
            this.setDAOInterfaceType(sb.toString());
            sb.setLength(0);
            sb.append(this.calculateJavaClientInterfacePackage());
            sb.append('.');
            if (StringUtility.stringHasValue(this.tableConfiguration.getMapperName())) {
                sb.append(this.tableConfiguration.getMapperName());
            } else {
                sb.append(this.fullyQualifiedTable.getDomainObjectName());
                sb.append("Dao");
            }

            this.setMyBatis3JavaMapperType(sb.toString());
            sb.setLength(0);
            sb.append(this.calculateJavaClientInterfacePackage());
            sb.append('.');
            if (StringUtility.stringHasValue(this.tableConfiguration.getSqlProviderName())) {
                sb.append(this.tableConfiguration.getSqlProviderName());
            } else {
                sb.append(this.fullyQualifiedTable.getDomainObjectName());
                sb.append("SqlProvider");
            }

            this.setMyBatis3SqlProviderType(sb.toString());
        }
    }

}


