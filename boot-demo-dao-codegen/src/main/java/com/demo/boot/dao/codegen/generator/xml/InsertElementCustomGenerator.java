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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * insert方法sql语句生成器
 *
 * @author wude
 * @version 1.0.0
 * @create 2018-05-11 10:26
 */
public class InsertElementCustomGenerator extends InsertElementGenerator {

    private boolean isSample;

    public InsertElementCustomGenerator(boolean isSimple) {
        super(isSimple);
        this.isSample = isSimple;
    }

    @Override
    public void addElements(XmlElement xmlElement) {
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
        this.context.getCommentGenerator().addComment(answer);
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

        StringBuilder insertClause = new StringBuilder();
        insertClause.append("INSERT INTO ");
        insertClause.append(this.introspectedTable.getFullyQualifiedTableNameAtRuntime());
        insertClause.append(" (");
        answer.addElement(new TextElement(insertClause.toString()));
        insertClause.setLength(0);
        OutputUtilities.xmlIndent(insertClause, 1);

        List<IntrospectedColumn> columns = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(this.introspectedTable.getAllColumns());
        int columnSize = columns.size();
        for (int i = 0; i < columnSize; i++) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn) columns.get(i);
            insertClause.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));

            if (i != columnSize - 1) {
                insertClause.append(", ");
            }

            if (insertClause.length() > 80) {
                answer.addElement(new TextElement(insertClause.toString()));
                insertClause.setLength(0);
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

        List<String> valuesClauses = new ArrayList();

        for (int i = 0; i < columnSize; ++i) {
            if (i == 0) {
                OutputUtilities.xmlIndent(valuesClause, 1);
            }
            IntrospectedColumn introspectedColumn = (IntrospectedColumn) columns.get(i);
            valuesClause.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            if (i !=  columnSize - 1) {
                valuesClause.append(", ");
            }
            if (valuesClause.length() > 80) {
                valuesClauses.add(valuesClause.toString());
                valuesClause.setLength(0);
                if (i != columnSize - 1)
                OutputUtilities.xmlIndent(valuesClause, 1);
            }
        }

        for (String clause : valuesClauses) {
            answer.addElement(new TextElement(clause));
        }

        Iterator var12 = valuesClauses.iterator();
        valuesClause.setLength(0);
        valuesClause.append(")");
        answer.addElement(new TextElement(valuesClause.toString()));

        if (this.context.getPlugins().sqlMapInsertElementGenerated(answer, this.introspectedTable)) {
            xmlElement.addElement(answer);
        }
    }
}