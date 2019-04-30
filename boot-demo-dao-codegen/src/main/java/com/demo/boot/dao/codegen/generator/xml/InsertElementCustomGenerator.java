package com.demo.boot.dao.codegen.generator.xml;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.InsertElementGenerator;
import org.mybatis.generator.config.GeneratedKey;

import java.util.List;

/**
 * <p>insert方法sqlMap生成器</p>
 *
 * @author wude
 * @create 2018-05-11 10:26
 */
public class InsertElementCustomGenerator extends InsertElementGenerator {

    private boolean isSample;

    public InsertElementCustomGenerator(boolean isSimple) {
        super(isSimple);
        this.isSample = isSimple;
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("insert");
        answer.addAttribute(new Attribute("id", this.introspectedTable.getInsertStatementId()));
        FullyQualifiedJavaType parameterType;
        // isSample=true, parameterType是Model类全名(包括表仅仅存在复合主键列)
        if (this.isSample) {
            parameterType = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        } else {
            parameterType = this.introspectedTable.getRules().calculateAllFieldsClass();
        }
        answer.addAttribute(new Attribute("parameterType", parameterType.getFullyQualifiedName()));
        // @mbg.generated
        this.context.getCommentGenerator().addComment(answer);
        // Mysql：主键只有一自增长列默认追加useGeneratedKeys设置，以便insert之后返回id
        if (introspectedTable.getPrimaryKeyColumns().size() == 1 && introspectedTable.getPrimaryKeyColumns().get(0).isGeneratedAlways()) {
            answer.addAttribute(new Attribute("useGeneratedKeys", "true"));
            answer.addAttribute(new Attribute("keyProperty", introspectedTable.getPrimaryKeyColumns().get(0).getJavaProperty()));
            answer.addAttribute(new Attribute("keyColumn", introspectedTable.getPrimaryKeyColumns().get(0).getActualColumnName()));
        } else {
            GeneratedKey gk = this.introspectedTable.getGeneratedKey();
            if (gk != null) {
                IntrospectedColumn introspectedColumn = this.introspectedTable.getColumn(gk.getColumn());
                if (introspectedColumn != null) {
                    if (gk.isJdbcStandard()) {
                        answer.addAttribute(new Attribute("useGeneratedKeys", "true"));
                        answer.addAttribute(new Attribute("keyProperty", introspectedColumn.getJavaProperty()));
                        answer.addAttribute(new Attribute("keyColumn", introspectedColumn.getActualColumnName()));
                    } else {
                        answer.addElement(this.getSelectKey(introspectedColumn, gk));
                    }
                }
            }
        }

        boolean hasColumnDefulatValue = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(this.introspectedTable.getAllColumns())
                .stream().anyMatch(column -> column.getDefaultValue() != null);
        if (!hasColumnDefulatValue) {
            addElementsWithAllColumns(parentElement, answer);
        } else {
            addElementsWithDefaultColumns(parentElement, answer);
        }
    }

    public void addElementsWithAllColumns(XmlElement xmlElement, XmlElement answer) {
        StringBuilder insertClause = new StringBuilder();
        insertClause.append("INSERT INTO ")
                .append(this.introspectedTable.getFullyQualifiedTableNameAtRuntime())
                .append(" (");
        answer.addElement(new TextElement(insertClause.toString()));

        insertClause.setLength(0);
        OutputUtilities.xmlIndent(insertClause, 1);

        List<IntrospectedColumn> columns = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(this.introspectedTable.getAllColumns());
        for (int i = 0; i < columns.size(); i++) {
            IntrospectedColumn column = columns.get(i);
            insertClause.append(column.getActualColumnName());
            if (i != columns.size() - 1) {
                insertClause.append(", ");
            }
            if (insertClause.length() > 80) {
                answer.addElement(new TextElement(insertClause.toString()));
                insertClause.setLength(0);
                if (i != columns.size() - 1) {
                    OutputUtilities.xmlIndent(insertClause, 1);
                }
            }
        }
        if (insertClause.length() > 0) {
            answer.addElement(new TextElement(insertClause.toString()));
            insertClause.setLength(0);
        }

        StringBuilder valuesClause = new StringBuilder();
        valuesClause.append(") VALUES (");
        OutputUtilities.xmlIndent(valuesClause, 1);
        answer.addElement(new TextElement(valuesClause.toString()));
        valuesClause.setLength(0);

        for (int i = 0; i < columns.size(); ++i) {
            if (i == 0) {
                OutputUtilities.xmlIndent(valuesClause, 1);
            }
            valuesClause.append(MyBatis3FormattingUtilities.getParameterClause(columns.get(i)));
            if (i != columns.size() - 1) {
                valuesClause.append(", ");
            }
            if (valuesClause.length() > 80) {
                answer.addElement(new TextElement(valuesClause.toString()));
                valuesClause.setLength(0);
                if (i != columns.size() - 1) {
                    OutputUtilities.xmlIndent(valuesClause, 1);
                }
            }
        }
        if (valuesClause.length() > 0) {
            answer.addElement(new TextElement(valuesClause.toString()));
        }

        valuesClause.setLength(0);
        valuesClause.append(")");
        answer.addElement(new TextElement(valuesClause.toString()));

        if (this.context.getPlugins().sqlMapInsertElementGenerated(answer, this.introspectedTable)) {
            xmlElement.addElement(answer);
        }
    }

    public void addElementsWithDefaultColumns(XmlElement xmlElement, XmlElement answer) {

        StringBuilder insertClause = new StringBuilder();
        insertClause.append("INSERT INTO ")
                .append(this.introspectedTable.getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(insertClause.toString()));

        insertClause.setLength(0);
        OutputUtilities.xmlIndent(insertClause, 1);

        // <trim prefix="(" suffix=")" suffixOverrides=",">
        XmlElement trimElement = new XmlElement("trim");
        trimElement.addAttribute(new Attribute("prefix", "("));
        trimElement.addAttribute(new Attribute("suffix", ")"));
        trimElement.addAttribute(new Attribute("suffixOverrides", ","));

        List<IntrospectedColumn> columns = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(this.introspectedTable.getAllColumns());
        for (int i = 0; i < columns.size(); i++) {
            IntrospectedColumn column = columns.get(i);
            if (column.getDefaultValue() == null) {
                trimElement.addElement(new TextElement(column.getActualColumnName() + ","));
            } else {
                XmlElement ifElement = new XmlElement("if");
                ifElement.addAttribute(new Attribute("test", column.getJavaProperty() + " != null"));
                ifElement.addElement(new TextElement(column.getActualColumnName() + ","));
                trimElement.addElement(ifElement);
            }
        }
        answer.addElement(trimElement);
        answer.addElement(new TextElement("VALUES"));

        XmlElement valueTrimElement = new XmlElement("trim");
        valueTrimElement.addAttribute(new Attribute("prefix", "("));
        valueTrimElement.addAttribute(new Attribute("suffix", ")"));
        valueTrimElement.addAttribute(new Attribute("suffixOverrides", ","));


        for (int i = 0; i < columns.size(); ++i) {
            IntrospectedColumn column = columns.get(i);
            if (column.getDefaultValue() == null) {
                valueTrimElement.addElement(new TextElement(MyBatis3FormattingUtilities.getParameterClause(column) + ","));
            } else {
                XmlElement ifElement = new XmlElement("if");
                ifElement.addAttribute(new Attribute("test", column.getJavaProperty() + " != null"));
                ifElement.addElement(new TextElement(MyBatis3FormattingUtilities.getParameterClause(column) + ","));
                valueTrimElement.addElement(ifElement);
            }
        }
        answer.addElement(valueTrimElement);

        if (this.context.getPlugins().sqlMapInsertElementGenerated(answer, this.introspectedTable)) {
            xmlElement.addElement(answer);
        }
    }
}