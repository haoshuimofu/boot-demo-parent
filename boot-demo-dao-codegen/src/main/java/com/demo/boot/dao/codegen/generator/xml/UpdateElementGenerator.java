package com.demo.boot.dao.codegen.generator.xml;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import java.util.List;

/**
 * <p>update方法sqlMap生成器</p>
 * <p>入参就是Model类，where条件就是主键列，如果数据库表<em>没有主键列</em>就不生成sqlMap了</p>
 * <p>Model类对应数据库表主键有多列，where后面条件会有多个，用and拼接</p>
 * <p>因为update sql语句主要set的是非主键列，所以数据库表<em>没有非主键列</em>也不生成sqlMap了</p>
 *
 * @Author wude
 * @Create 2017-06-15 13:26
 */
public class UpdateElementGenerator extends AbstractXmlElementGenerator {

    public UpdateElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement xmlElement) {
        if (introspectedTable.getPrimaryKeyColumns().size() == 0 || introspectedTable.getNonPrimaryKeyColumns().size() == 0) {
            return;
        }
        XmlElement answer = new XmlElement("update");
        answer.addAttribute(new Attribute("id", "update"));
        // parameterType就是数据表对应的Model类
        answer.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        // @mbg.generated 注释添加
        this.context.getCommentGenerator().addComment(answer);

        StringBuilder updateClause = new StringBuilder();
        updateClause.append("UPDATE ");
        updateClause.append(this.introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(updateClause.toString()));

        List<IntrospectedColumn> columns = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(this.introspectedTable.getAllColumns());
        XmlElement setElement = new XmlElement("set");
        answer.addElement(setElement);
        for (IntrospectedColumn column : columns) {
            // 为if标签添加属性：test="xx != null"
            XmlElement ifElement = new XmlElement("if");
            updateClause.setLength(0);
            updateClause.append(column.getJavaProperty());
            updateClause.append(" != null");
            ifElement.addAttribute(new Attribute("test", updateClause.toString()));
            // 在if标签内添加 xx = #{xx,jdbcType=xx}
            updateClause.setLength(0);
            updateClause.append(MyBatis3FormattingUtilities.getAliasedEscapedColumnName(column));
            updateClause.append(" = ");
            updateClause.append(MyBatis3FormattingUtilities.getParameterClause(column, ""));
            updateClause.append(',');
            ifElement.addElement(new TextElement(updateClause.toString()));
            setElement.addElement(ifElement);
        }

        updateClause.setLength(0);
        updateClause.append(" WHERE ");
        for (int i = 0; i < introspectedTable.getPrimaryKeyColumns().size(); i++) {
            IntrospectedColumn column = introspectedTable.getPrimaryKeyColumns().get(i);
            updateClause.append(column.getActualColumnName())
                    .append(" = ")
                    .append(MyBatis3FormattingUtilities.getParameterClause(column, ""));
            if (i != introspectedTable.getPrimaryKeyColumns().size() - 1) {
                updateClause.append(" AND ");
            }
        }
        answer.addElement(new TextElement(updateClause.toString()));
        xmlElement.addElement(answer);
    }
}
