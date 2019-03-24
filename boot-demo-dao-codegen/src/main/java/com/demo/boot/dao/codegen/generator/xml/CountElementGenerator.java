package com.demo.boot.dao.codegen.generator.xml;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import java.util.List;

/**
 * <p>count方法sqlMap生成器</p>
 *
 * @Author wude
 * @Create 2017-06-14 13:23
 */
public class CountElementGenerator extends AbstractXmlElementGenerator {

    private boolean isSample;

    public CountElementGenerator(boolean isSimple) {
        super();
        this.isSample = isSimple;
    }

    @Override
    public void addElements(XmlElement parentElement) {
        FullyQualifiedJavaType parameterType;
        // isSample=true, parameterType是Model类全名(包括表仅仅存在复合主键列)
        if (this.isSample) {
            parameterType = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        } else {
            parameterType = this.introspectedTable.getRules().calculateAllFieldsClass();
        }

        XmlElement answer = new XmlElement("select");
        answer.addAttribute(new Attribute("id", "count"));
        answer.addAttribute(new Attribute("parameterType", parameterType.getFullyQualifiedName()));
        answer.addAttribute(new Attribute("resultType", Long.class.getCanonicalName()));

        context.getCommentGenerator().addComment(answer);

        StringBuilder selectClause = new StringBuilder();
        selectClause.append("SELECT count(1) FROM ")
                .append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(selectClause.toString()));

        List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
        XmlElement whereElement = new XmlElement("where");
        for (int i = 0; i < columns.size(); i++) {
            IntrospectedColumn column = columns.get(i);
            XmlElement ifElement = new XmlElement("if");
            ifElement.addAttribute(new Attribute("test", column.getJavaProperty() + " != null"));
            ifElement.addElement(new TextElement("AND " + MyBatis3FormattingUtilities.getEscapedColumnName(column) + "=" + MyBatis3FormattingUtilities.getParameterClause(column, "")));
            whereElement.addElement(ifElement);
        }
        answer.addElement(whereElement);
        parentElement.addElement(answer);
    }
}
