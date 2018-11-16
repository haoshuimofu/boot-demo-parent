package com.demo.boot.dao.codegen;

import com.demo.boot.dao.codegen.generator.DemoXmlMapperGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;
import org.mybatis.generator.codegen.mybatis3.model.BaseRecordGenerator;
import org.mybatis.generator.codegen.mybatis3.model.ExampleGenerator;
import org.mybatis.generator.codegen.mybatis3.model.PrimaryKeyGenerator;
import org.mybatis.generator.codegen.mybatis3.model.RecordWithBLOBsGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.List;

/**
 * 自定义Mybatis3实现
 *
 * @Author wude
 * @Create 2017-06-14 10:29
 */
public class DemoIntrospectedTableMyBatis3Impl extends IntrospectedTableMyBatis3Impl {


    @Override
    public void addColumn(IntrospectedColumn introspectedColumn) {
        // 为了让自增长列不出现在Insert语句中
        if (introspectedColumn.isAutoIncrement()) {
            introspectedColumn.setGeneratedAlways(true);
        }
        super.addColumn(introspectedColumn);
    }

    @Override
    protected void calculateJavaModelGenerators(List<String> warnings, ProgressCallback progressCallback) {
        if (this.getRules().generateExampleClass()) {
            AbstractJavaGenerator javaGenerator = new ExampleGenerator();
            this.initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            this.javaModelGenerators.add(javaGenerator);
        }

        if (this.getRules().generatePrimaryKeyClass()) {
            AbstractJavaGenerator javaGenerator = new PrimaryKeyGenerator();
            this.initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            this.javaModelGenerators.add(javaGenerator);
        }

        // 如果表只有复合主键, 除了生成主键类意外, 还要生成空的Model类
//        if (this.getRules().generateBaseRecordClass()) {
        if (true) {
            AbstractJavaGenerator javaGenerator = new BaseRecordGenerator();
            this.initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            this.javaModelGenerators.add(javaGenerator);
        }

        if (this.getRules().generateRecordWithBLOBsClass()) {
            AbstractJavaGenerator javaGenerator = new RecordWithBLOBsGenerator();
            this.initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            this.javaModelGenerators.add(javaGenerator);
        }
    }

    /**
     * Calculate xml mapper generator.
     *
     * @param javaClientGenerator the java client generator
     * @param warnings            the warnings
     */
    @Override
    protected void calculateXmlMapperGenerator(AbstractJavaClientGenerator javaClientGenerator, List<String> warnings, ProgressCallback progressCallback) {
        /*if (javaClientGenerator == null) {
            if (context.getSqlMapGeneratorConfiguration() != null) {
                xmlMapperGenerator = new XMLMapperGenerator();
            }
        } else {
            xmlMapperGenerator = javaClientGenerator.getMatchedXMLGenerator();
        }*/
        // 自定义实现XmlMapperGenerator
        xmlMapperGenerator = new DemoXmlMapperGenerator();
        initializeAbstractGenerator(xmlMapperGenerator, warnings, progressCallback);
    }


    /**
     * 重写IntrospectedTable.calculateJavaClientAttributes方法, 主要是将XxMapper命名为XxDao
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

//    /**
//     * 计算sqlMap xml文件的package, 目的使其package和DAO接口的package保持一致
//     *
//     * @return
//     */
//    @Override
//    protected String calculateSqlMapPackage() {
//        // super.calculateSqlMapPackage();
//        StringBuilder sb = new StringBuilder();
//        SqlMapGeneratorConfiguration config = this.context.getSqlMapGeneratorConfiguration();
//        if (config != null) {
//            /*sb.append(config.getTargetPackage());
//            sb.append(this.fullyQualifiedTable.getSubPackageForClientOrSqlMap(this.isSubPackagesEnabled(config)));
//            if (StringUtility.stringHasValue(this.tableConfiguration.getMapperName())) {
//                String mapperName = this.tableConfiguration.getMapperName();
//                int ind = mapperName.lastIndexOf(46);
//                if (ind != -1) {
//                    sb.append('.').append(mapperName.substring(0, ind));
//                }
//            } else if (StringUtility.stringHasValue(this.fullyQualifiedTable.getDomainObjectSubPackage())) {
//                sb.append('.').append(this.fullyQualifiedTable.getDomainObjectSubPackage());
//            }*/
//
//            // sqlMap所在目录的package和Dao接口的package保持一致
//            if (StringUtility.stringHasValue(this.getDAOInterfaceType())) {
//                int ind = this.getDAOInterfaceType().lastIndexOf(46);
//                if (ind != -1) {
//                    sb.append('.').append(this.getDAOInterfaceType().substring(0, ind));
//                }
//            }
//        }
//        return sb.toString();
//    }

}


