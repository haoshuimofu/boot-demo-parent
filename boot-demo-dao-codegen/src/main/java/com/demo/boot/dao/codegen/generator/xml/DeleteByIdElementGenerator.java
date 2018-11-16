package com.demo.boot.dao.codegen.generator.xml;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

/**
 * deleteById方法sql语句生成器
 *
 * @Author wude
 * @Create 2017-06-14 13:58
 */
public class DeleteByIdElementGenerator extends AbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        // 如果Table没有主键列则不生成此方法
        if (introspectedTable.getPrimaryKeyColumns().size() == 0) {
            return;
        }
        XmlElement answer = new XmlElement("delete");
        answer.addAttribute(new Attribute("id", "deleteById"));
        // 参数类型
        if (introspectedTable.getPrimaryKeyColumns().size() > 1) {
            FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(this.introspectedTable.getPrimaryKeyType());
            answer.addAttribute(new Attribute("parameterType", parameterType.getFullyQualifiedName()));
        } else {
            answer.addAttribute(new Attribute("parameterType", introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getFullyQualifiedName()));
        }
        // 标签内第一行生成mbg.generated注解
        context.getCommentGenerator().addComment(answer);

        StringBuilder deleteClause = new StringBuilder();
        deleteClause.append("DELETE FROM ");
        deleteClause.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        deleteClause.append(" WHERE ");

        for (int i = 0; i < introspectedTable.getPrimaryKeyColumns().size(); i++) {
            IntrospectedColumn primaryKeyColumn = introspectedTable.getPrimaryKeyColumns().get(i);
            deleteClause.append(primaryKeyColumn.getActualColumnName());
            deleteClause.append(" = ");
            deleteClause.append(MyBatis3FormattingUtilities.getParameterClause(primaryKeyColumn, ""));
            if (i != introspectedTable.getPrimaryKeyColumns().size() - 1) {
                deleteClause.append(" AND ");
            }
        }
        answer.addElement(new TextElement(deleteClause.toString()));
        parentElement.addElement(answer);
    }
}
