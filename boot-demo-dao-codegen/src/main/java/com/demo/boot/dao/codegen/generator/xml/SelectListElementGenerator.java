package com.demo.boot.dao.codegen.generator.xml;

import com.demo.boot.dao.codegen.contants.MybatisGeneratorConstants;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import java.util.List;

/**
 * <p>selectList方法sqlMap生成器</p>
 * <p>如果数据库表为单列主键，查询where条件忽略主键，可以根据selectById直接查询，但是如果没有非主键列就不生成sqlMap了</p>
 *
 * @Author wude
 * @Create 2017-06-14 13:06
 */
public class SelectListElementGenerator extends AbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {

        XmlElement answer = new XmlElement("select");
        answer.addAttribute(new Attribute("id", "selectList"));
        // parameterType就是Model类
        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        answer.addAttribute(new Attribute("parameterType", parameterType.getFullyQualifiedName()));
        // resultMap=BaseResultMap
        answer.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        // @mbg.generated
        context.getCommentGenerator().addComment(answer);

        StringBuilder selectClause = new StringBuilder();
        selectClause.append("SELECT ")
                .append(MybatisGeneratorConstants.XML_ELEMENT_BASE_COLUMN_LIST)
                .append(" FROM ")
                .append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(selectClause.toString()));

        // 如果数据库表为单列主键，where条件就不包括主键了，否则where条件包括所有列
        List<IntrospectedColumn> columns = introspectedTable.getPrimaryKeyColumns().size() == 1 ? introspectedTable.getNonPrimaryKeyColumns() : introspectedTable.getAllColumns();
        XmlElement whereElement = new XmlElement("where");
        // 为每个Column添加ifNotNull判断条件
        for (IntrospectedColumn column : columns) {
            XmlElement ifElement = new XmlElement("if");
            selectClause.setLength(0);
            selectClause.append(column.getJavaProperty());
            selectClause.append(" != null");
            ifElement.addAttribute(new Attribute("test", selectClause.toString()));

            selectClause.setLength(0);
            selectClause.append("AND ");
            selectClause.append(MyBatis3FormattingUtilities.getEscapedColumnName(column));
            selectClause.append(" = ");
            selectClause.append(MyBatis3FormattingUtilities.getParameterClause(column, ""));
            ifElement.addElement(new TextElement(selectClause.toString()));
            whereElement.addElement(ifElement);
        }
        answer.addElement(whereElement);
        parentElement.addElement(answer);
    }
}
