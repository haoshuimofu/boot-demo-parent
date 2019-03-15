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
 * selectOne方法sql语句生成器
 *
 * @Author wude
 * @Create 2017-06-14 11:03
 */
public class SelectOneElementGenerator extends AbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {

        XmlElement answer = new XmlElement("select");
        answer.addAttribute(new Attribute("id", "selectOne"));
        // 参数类型
        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        answer.addAttribute(new Attribute("parameterType", parameterType.getFullyQualifiedName()));
        // 添加ResultMap
        answer.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        // 标签内第一行添加mbg.generated注解
        context.getCommentGenerator().addComment(answer);
        // select语句
        StringBuilder selectClause = new StringBuilder();
        selectClause.append("SELECT ")
                .append(MybatisGeneratorConstants.XML_ELEMENT_BASE_COLUMN_LIST)
                .append(" FROM ");
        selectClause.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(selectClause.toString()));

        XmlElement whereElement = new XmlElement("where");
        // 如果Table主键有且只有一列, 建议查询条件中不要带主键列, 这种情况下可以直接调用selectById方法
        // 当Table存在复合主键甚至不存在主键, 查询条件包括所有列
        List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
        if (introspectedTable.getPrimaryKeyColumns().size() == 1) {
            columns = introspectedTable.getNonPrimaryKeyColumns();
        }
        // 为每个Column添加ifNotNull判断条件
        for (IntrospectedColumn column : columns) {
            selectClause.setLength(0);
            XmlElement ifElement = new XmlElement("if");
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
