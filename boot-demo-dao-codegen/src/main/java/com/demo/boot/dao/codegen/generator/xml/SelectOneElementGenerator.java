package com.demo.boot.dao.codegen.generator.xml;

import com.demo.boot.dao.codegen.contants.MybatisGeneratorConstants;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import java.util.List;

/**
 * <p>selectOne方法sqlMap生成器</p>
 * <p>如果数据库表为单列主键，查询where条件忽略主键，可以根据selectById直接查询，但是如果没有非主键列就不生成sqlMap了</p>
 * <p>如果数据库表没有主键列或主键列数大于1在sql语句生成好后追加limit 1</p>
 *
 * @Author wude
 * @Create 2017-06-14 11:03
 */
public class SelectOneElementGenerator extends AbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {

        if (introspectedTable.getPrimaryKeyColumns().size() == 1 && introspectedTable.getNonPrimaryKeyColumns().size() == 0) {
            return;
        }

        XmlElement answer = new XmlElement("select");
        answer.addAttribute(new Attribute("id", "selectOne"));
        // parameterType就是Model类
        // FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        // answer.addAttribute(new Attribute("parameterType", parameterType.getFullyQualifiedName()));
        answer.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        // 追加resultMap=BaseResultMap
        answer.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        // @mbg.generated
        context.getCommentGenerator().addComment(answer);

        StringBuilder selectClause = new StringBuilder();
        selectClause.append("SELECT ")
                .append(MybatisGeneratorConstants.XML_ELEMENT_BASE_COLUMN_LIST)
                .append(" FROM ")
                .append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(selectClause.toString()));

        XmlElement whereElement = new XmlElement("where");
        // 如果数据库表为单列主键，where条件就不包括主键了，否则where条件包括所有列
        List<IntrospectedColumn> columns = introspectedTable.getPrimaryKeyColumns().size() == 1 ? introspectedTable.getNonPrimaryKeyColumns() : introspectedTable.getAllColumns();
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
        whereElement.addElement(new TextElement("LIMIT 1"));
        answer.addElement(whereElement);
        parentElement.addElement(answer);
    }

}
