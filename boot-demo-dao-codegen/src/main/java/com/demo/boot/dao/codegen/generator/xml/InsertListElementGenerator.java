package com.demo.boot.dao.codegen.generator.xml;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import java.util.List;

/**
 * insertList方法sql语句生成器
 *
 * @author wude
 * @create 2018-03-14 18:01
 */
public class InsertListElementGenerator extends AbstractXmlElementGenerator {


    @Override
    public void addElements(XmlElement xmlElement) {

        XmlElement answer = new XmlElement("insert"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", "insertList")); //$NON-NLS-1$

        answer.addAttribute(new Attribute("parameterType", List.class.getName()));

        context.getCommentGenerator().addComment(answer);

        StringBuilder insertClause = new StringBuilder();

        // 拼接insertList语句
        insertClause.append("INSERT INTO ");
        insertClause.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        insertClause.append(" (");
        answer.addElement(new TextElement(insertClause.toString()));
        insertClause.setLength(0);
        OutputUtilities.xmlIndent(insertClause, 1);

        List<IntrospectedColumn> columns = ListUtilities.removeGeneratedAlwaysColumns(this.introspectedTable.getAllColumns());
        int columnSize = columns.size();
        for (int i = 0; i < columnSize; i++) {
            insertClause.append(columns.get(i).getActualColumnName());
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
        insertClause.append(") VALUES ");
        answer.addElement(new TextElement(insertClause.toString()));

        // 生成的foreach标签内Attribute乱序
        XmlElement valuesElement = new XmlElement("foreach");
        valuesElement.addAttribute(new Attribute("collection", "list"));
        valuesElement.addAttribute(new Attribute("item", "item"));
        valuesElement.addAttribute(new Attribute("index", "index"));
        valuesElement.addAttribute(new Attribute("open", "("));
        valuesElement.addAttribute(new Attribute("separator", ","));
        valuesElement.addAttribute(new Attribute("close", ")"));

        StringBuilder valuesClause = new StringBuilder();
        for (int i = 0; i < columnSize; i++) {
//            valuesClause.append("#{item.");
//            System.out.println(MyBatis3FormattingUtilities.getParameterClause(columns.get(i)));
//            valuesClause.append(MyBatis3FormattingUtilities.getEscapedColumnName(columns.get(i)));
//            valuesClause.append(",jdbcType=");
//            valuesClause.append(columns.get(i).getJdbcTypeName());
//            valuesClause.append("}");
            valuesClause.append(MyBatis3FormattingUtilities.getParameterClause(columns.get(i)).replace("#{", "#{item."));
            if (i != columnSize - 1) {
                valuesClause.append(", ");
            }

            if (valuesClause.length() > 80) {
                valuesElement.addElement(new TextElement(valuesClause.toString()));
                valuesClause.setLength(0);
            }
        }
        if (valuesClause.length() > 0) {
            valuesElement.addElement(new TextElement(valuesClause.toString()));
        }
        answer.addElement(valuesElement);

        xmlElement.addElement(answer);

    }
}