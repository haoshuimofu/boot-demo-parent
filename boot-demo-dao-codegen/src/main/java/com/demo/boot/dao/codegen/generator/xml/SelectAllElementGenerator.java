package com.demo.boot.dao.codegen.generator.xml;

import com.demo.boot.dao.codegen.contants.MybatisGeneratorConstants;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

/**
 * selectAll方法sql语句生成器
 *
 * @Author wude
 * @Create 2017-06-14 13:11
 */
public class SelectAllElementGenerator extends AbstractXmlElementGenerator {

    public SelectAllElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");
        answer.addAttribute(new Attribute("id", "selectAll"));
        // 添加ResultMap
        answer.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        // 标签内第一行生成mbg.generated注解
        context.getCommentGenerator().addComment(answer);

        StringBuilder selectClause = new StringBuilder();
        selectClause.append("SELECT ").append(MybatisGeneratorConstants.XML_ELEMENT_BASE_COLUMN_LIST).append(" FROM ");
        selectClause.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(selectClause.toString()));
        parentElement.addElement(answer);
    }
}
