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
 * <p>insertList方法sqlMap生成器</p>
 *
 * @author wude
 * @create 2018-03-14 18:01
 */
public class InsertListElementGenerator extends AbstractXmlElementGenerator {


    @Override
    public void addElements(XmlElement xmlElement) {

        XmlElement answer = new XmlElement("insert");
        answer.addAttribute(new Attribute("id", "insertList"));
        answer.addAttribute(new Attribute("parameterType", List.class.getCanonicalName()));
        // @mbg.generated
        context.getCommentGenerator().addComment(answer);

        StringBuilder insertClause = new StringBuilder();
        insertClause.append("INSERT INTO ")
                .append(introspectedTable.getFullyQualifiedTableNameAtRuntime())
                .append(" (");
        answer.addElement(new TextElement(insertClause.toString()));

        insertClause.setLength(0);
        OutputUtilities.xmlIndent(insertClause, 1);

        List<IntrospectedColumn> columns = ListUtilities.removeGeneratedAlwaysColumns(this.introspectedTable.getAllColumns());
        for (int i = 0; i < columns.size(); i++) {
            IntrospectedColumn column = columns.get(i);
            insertClause.append(column.getActualColumnName());
            if (i != columns.size() - 1) {
                insertClause.append(", ");
            }
            if (insertClause.length() > 80) {
                answer.addElement(new TextElement(insertClause.toString()));
                insertClause.setLength(0);
                OutputUtilities.xmlIndent(insertClause, 1);
            }
        }
        if (insertClause.length() > 0) {
            answer.addElement(new TextElement(insertClause.toString()));
        }
        answer.addElement(new TextElement(") VALUES "));

        // 生成的foreach标签内Attribute
        XmlElement valuesElement = new XmlElement("foreach");
        valuesElement.addAttribute(new Attribute("collection", "list"));
        valuesElement.addAttribute(new Attribute("item", "item"));
        valuesElement.addAttribute(new Attribute("index", "index"));
        valuesElement.addAttribute(new Attribute("separator", ","));
        valuesElement.addElement(new TextElement("("));

        StringBuilder valuesClause = new StringBuilder();
        OutputUtilities.xmlIndent(valuesClause, 1);
        for (int i = 0; i < columns.size(); i++) {
            valuesClause.append(MyBatis3FormattingUtilities.getParameterClause(columns.get(i)).replace("#{", "#{item."));
            if (i != columns.size() - 1) {
                valuesClause.append(", ");
            }
            if (valuesClause.length() > 80) {
                valuesElement.addElement(new TextElement(valuesClause.toString()));
                valuesClause.setLength(0);
                if (i != columns.size() - 1) {
                    OutputUtilities.xmlIndent(valuesClause, 1);
                }
            }
        }
        if (valuesClause.length() > 0) {
            valuesElement.addElement(new TextElement(valuesClause.toString()));
        }
        valuesElement.addElement(new TextElement(")"));
        answer.addElement(valuesElement);

        xmlElement.addElement(answer);
    }
}