package com.demo.boot.dao.codegen.generator.xml;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.ResultMapWithoutBLOBsElementGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Iterator;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * BaseColumnListElementGenerator
 *
 * @author wude
 * @create 2018-05-11 13:08
 */
public class ResultMapWithBLOBsElementCustomGenerator extends ResultMapWithoutBLOBsElementGenerator {
    private boolean isSimple;

    public ResultMapWithBLOBsElementCustomGenerator(boolean isSimple) {
        super(isSimple);
        this.isSimple = isSimple;
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("resultMap"); //$NON-NLS-1$
        answer.addAttribute(new Attribute("id", //$NON-NLS-1$
                introspectedTable.getBaseResultMapId()));

        String returnType;
        if (isSimple) {
            returnType = introspectedTable.getBaseRecordType();
        } else {
            if (introspectedTable.getRules().generateBaseRecordClass()) {
                returnType = introspectedTable.getBaseRecordType();
            } else {
                returnType = introspectedTable.getPrimaryKeyType();
            }
        }

        answer.addAttribute(new Attribute("type", //$NON-NLS-1$
                returnType));

        context.getCommentGenerator().addComment(answer);

        if (introspectedTable.isConstructorBased()) {
            addResultMapConstructorElements(answer);
        } else {
            addResultMapElements(answer);
        }

        if (context.getPlugins().sqlMapResultMapWithoutBLOBsElementGenerated(
                answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }

    private void addResultMapElements(XmlElement answer) {
        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getPrimaryKeyColumns()) {
            XmlElement resultElement = new XmlElement("id"); //$NON-NLS-1$

            resultElement
                    .addAttribute(new Attribute(
                            "column", MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(introspectedColumn))); //$NON-NLS-1$
            resultElement.addAttribute(new Attribute(
                    "property", introspectedColumn.getJavaProperty())); //$NON-NLS-1$
            resultElement.addAttribute(new Attribute("jdbcType", //$NON-NLS-1$
                    introspectedColumn.getJdbcTypeName()));

            if (stringHasValue(introspectedColumn.getTypeHandler())) {
                resultElement.addAttribute(new Attribute(
                        "typeHandler", introspectedColumn.getTypeHandler())); //$NON-NLS-1$
            }

            answer.addElement(resultElement);
        }

        List<IntrospectedColumn> columns;
        if (isSimple) {
            columns = introspectedTable.getNonPrimaryKeyColumns();
        } else {
            columns = introspectedTable.getBaseColumns();
        }
        for (IntrospectedColumn introspectedColumn : columns) {
            XmlElement resultElement = new XmlElement("result"); //$NON-NLS-1$

            resultElement
                    .addAttribute(new Attribute(
                            "column", MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(introspectedColumn))); //$NON-NLS-1$
            resultElement.addAttribute(new Attribute(
                    "property", introspectedColumn.getJavaProperty())); //$NON-NLS-1$
            resultElement.addAttribute(new Attribute("jdbcType", //$NON-NLS-1$
                    introspectedColumn.getJdbcTypeName()));

            if (stringHasValue(introspectedColumn.getTypeHandler())) {
                resultElement.addAttribute(new Attribute(
                        "typeHandler", introspectedColumn.getTypeHandler())); //$NON-NLS-1$
            }

            answer.addElement(resultElement);
        }
    }

    private void addResultMapConstructorElements(XmlElement answer) {
        XmlElement constructor = new XmlElement("constructor"); //$NON-NLS-1$

        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getPrimaryKeyColumns()) {
            XmlElement resultElement = new XmlElement("idArg"); //$NON-NLS-1$

            resultElement
                    .addAttribute(new Attribute(
                            "column", MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(introspectedColumn))); //$NON-NLS-1$
            resultElement.addAttribute(new Attribute("jdbcType", //$NON-NLS-1$
                    introspectedColumn.getJdbcTypeName()));
            resultElement.addAttribute(new Attribute("javaType", //$NON-NLS-1$
                    introspectedColumn.getFullyQualifiedJavaType()
                            .getFullyQualifiedName()));

            if (stringHasValue(introspectedColumn.getTypeHandler())) {
                resultElement.addAttribute(new Attribute(
                        "typeHandler", introspectedColumn.getTypeHandler())); //$NON-NLS-1$
            }

            constructor.addElement(resultElement);
        }

        List<IntrospectedColumn> columns;
        if (isSimple) {
            columns = introspectedTable.getNonPrimaryKeyColumns();
        } else {
            columns = introspectedTable.getBaseColumns();
        }
        for (IntrospectedColumn introspectedColumn : columns) {
            XmlElement resultElement = new XmlElement("arg"); //$NON-NLS-1$

            resultElement
                    .addAttribute(new Attribute(
                            "column", MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(introspectedColumn))); //$NON-NLS-1$
            resultElement.addAttribute(new Attribute("jdbcType", //$NON-NLS-1$
                    introspectedColumn.getJdbcTypeName()));
            resultElement.addAttribute(new Attribute("javaType", //$NON-NLS-1$
                    introspectedColumn.getFullyQualifiedJavaType()
                            .getFullyQualifiedName()));

            if (stringHasValue(introspectedColumn.getTypeHandler())) {
                resultElement.addAttribute(new Attribute(
                        "typeHandler", introspectedColumn.getTypeHandler())); //$NON-NLS-1$
            }

            constructor.addElement(resultElement);
        }

        answer.addElement(constructor);
    }
}