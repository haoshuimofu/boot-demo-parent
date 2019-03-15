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
 * deleteById方法sql语句生成器
 *
 * @Author wude
 * @Create 2017-06-14 13:58
 */
public class DeleteByIdElementGenerator extends AbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        // 无主键不用实现deleteById方法
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        if (primaryKeyColumns == null || primaryKeyColumns.size() == 0) {
            return;
        }
        XmlElement answer = new XmlElement("delete");
        answer.addAttribute(new Attribute("id", "deleteById"));
        // 主键参数类型：单列和多列
        if (primaryKeyColumns.size() > 1) {
            FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(this.introspectedTable.getPrimaryKeyType());
            answer.addAttribute(new Attribute("parameterType", parameterType.getFullyQualifiedName()));
        } else {
            answer.addAttribute(new Attribute("parameterType", primaryKeyColumns.get(0).getFullyQualifiedJavaType().getFullyQualifiedName()));
        }
        // 标签内添加mbg.generated
        context.getCommentGenerator().addComment(answer);

        StringBuilder deleteClause = new StringBuilder();
        deleteClause.append("DELETE FROM ");
        deleteClause.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        deleteClause.append(" WHERE ");

        for (int i = 0; i < primaryKeyColumns.size(); i++) {
            IntrospectedColumn primaryKeyColumn = primaryKeyColumns.get(i);
            deleteClause.append(primaryKeyColumn.getActualColumnName());
            deleteClause.append(" = ");
            deleteClause.append(MyBatis3FormattingUtilities.getParameterClause(primaryKeyColumn, ""));
            if (i != primaryKeyColumns.size() - 1) {
                deleteClause.append(" AND ");
            }
        }
        answer.addElement(new TextElement(deleteClause.toString()));
        parentElement.addElement(answer);
    }
}
