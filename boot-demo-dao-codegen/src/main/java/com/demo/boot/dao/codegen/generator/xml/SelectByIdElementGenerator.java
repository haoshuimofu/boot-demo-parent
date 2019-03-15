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
        // 因为selectById方法实际查询依据就是主键, 所以没有主键的表就无需生成相应的sql了
        List<IntrospectedColumn> introspectedColumns = introspectedTable.getPrimaryKeyColumns();
        if (introspectedColumns == null || introspectedColumns.size() < 1) {
            return;
        }

        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();

        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", "selectById")); //$NON-NLS-1$

        if (introspectedColumns.size() == 1) {
            // 唯一主键列
            IntrospectedColumn primaryKeyColumn = introspectedTable.getPrimaryKeyColumns().get(0);
            answer.addAttribute(new Attribute("parameterType", primaryKeyColumn.getFullyQualifiedJavaType().getFullyQualifiedName()));
        } else {
            answer.addAttribute(new Attribute("parameterType", introspectedTable.getPrimaryKeyType()));
        }

        // 添加BaseResultMap
        answer.addAttribute(new Attribute("resultMap", "BaseResultMap"));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();

        sb.append("SELECT ").append(MybatisGeneratorConstants.XML_ELEMENT_BASE_COLUMN_LIST).append(" FROM ");

        sb.append(tableName);
        // 追加where id=xxx条件
        sb.append(" WHERE ");
        for (int i = 0; i < introspectedColumns.size(); i++) {
            if (i != 0) {
                sb.append(" AND ");
            }
            sb.append(introspectedColumns.get(i).getActualColumnName());
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumns.get(i), ""));
        }
        answer.addElement(new TextElement(sb.toString()));

        parentElement.addElement(answer);

    }
}
