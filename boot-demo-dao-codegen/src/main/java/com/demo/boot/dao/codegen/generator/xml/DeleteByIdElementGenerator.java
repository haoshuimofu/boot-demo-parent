package com.demo.boot.dao.codegen.generator.xml;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import java.util.List;

/**
 * <p>deleteById方法sqlMap生成器</p>
 * <p>where条件就只有主键列，所以数据库表假如没有主键列则不实现deleteById的sqlMap了</p>
 *
 * @Author wude
 * @Create 2017-06-14 13:58
 */
public class DeleteByIdElementGenerator extends AbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        if (primaryKeyColumns == null || primaryKeyColumns.size() == 0) {
            return;
        }

        XmlElement answer = new XmlElement("delete");
        answer.addAttribute(new Attribute("id", "deleteById"));
        // parameterType就是主键类型
        if (primaryKeyColumns.size() > 1) {
            FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(this.introspectedTable.getPrimaryKeyType());
            answer.addAttribute(new Attribute("parameterType", parameterType.getFullyQualifiedName()));
        } else {
            answer.addAttribute(new Attribute("parameterType", primaryKeyColumns.get(0).getFullyQualifiedJavaType().getFullyQualifiedName()));
        }
        // @mbg.generated
        context.getCommentGenerator().addComment(answer);

        StringBuilder deleteClause = new StringBuilder();
        deleteClause.append("DELETE FROM ")
                .append(introspectedTable.getFullyQualifiedTableNameAtRuntime())
                .append(" WHERE ");

        for (int i = 0; i < primaryKeyColumns.size(); i++) {
            if (i != 0) {
                deleteClause.setLength(0);
                OutputUtilities.xmlIndent(deleteClause, 1);
                deleteClause.append(" AND ");
            }
            IntrospectedColumn primaryKeyColumn = primaryKeyColumns.get(i);
            deleteClause.append(primaryKeyColumn.getActualColumnName())
                    .append(" = ")
                    .append(MyBatis3FormattingUtilities.getParameterClause(primaryKeyColumn, ""));
            if (i != primaryKeyColumns.size() - 1) {
                deleteClause.append(", ");
            }
            answer.addElement(new TextElement(deleteClause.toString()));
        }
        parentElement.addElement(answer);
    }
}
