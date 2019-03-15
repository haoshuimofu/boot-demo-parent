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
 * selectById方法sql生成器
 *
 * @Author wude
 * @Create 2017-06-14 13:23
 */
public class SelectByIdElementGenerator extends AbstractXmlElementGenerator {

    public SelectByIdElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        // 无主键不用实现selectById方法
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        if (primaryKeyColumns == null || primaryKeyColumns.size() == 0) {
            return;
        }

        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();

        XmlElement answer = new XmlElement("select");

        answer.addAttribute(new Attribute("id", "selectById"));

        if (primaryKeyColumns.size() == 1) {
            // 唯一主键列
            answer.addAttribute(new Attribute("parameterType", primaryKeyColumns.get(0).getFullyQualifiedJavaType().getFullyQualifiedName()));
        } else {
            answer.addAttribute(new Attribute("parameterType", introspectedTable.getPrimaryKeyType()));
        }

        // 添加BaseResultMap
        answer.addAttribute(new Attribute("resultMap", "BaseResultMap"));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();

        sb.append("SELECT ").append(MybatisGeneratorConstants.XML_ELEMENT_BASE_COLUMN_LIST).append(" FROM ");

        sb.append(tableName);

        sb.append(" WHERE ");
        for (int i = 0; i < primaryKeyColumns.size(); i++) {
            if (i != 0) {
                sb.append(" AND ");
            }
            IntrospectedColumn primaryKeyColumn = primaryKeyColumns.get(i);
            sb.append(primaryKeyColumn.getActualColumnName());
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtilities.getParameterClause(primaryKeyColumn, ""));
        }
        answer.addElement(new TextElement(sb.toString()));

        parentElement.addElement(answer);

    }
}
