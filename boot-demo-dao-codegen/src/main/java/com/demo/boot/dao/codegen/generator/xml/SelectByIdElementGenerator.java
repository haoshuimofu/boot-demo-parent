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
 * <p>selectById方法sqlMap生成器</p>
 * <p>因为where条件就是主键列，所以数据库表假如没有主键列就不实现sqlMap了<p/>
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
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        if (primaryKeyColumns == null || primaryKeyColumns.size() == 0) {
            return;
        }

        XmlElement answer = new XmlElement("select");
        answer.addAttribute(new Attribute("id", "selectById"));
        // parameterType设置需要看数据库表主键列列数
        if (primaryKeyColumns.size() == 1) {
            answer.addAttribute(new Attribute("parameterType", primaryKeyColumns.get(0).getFullyQualifiedJavaType().getFullyQualifiedName()));
        } else {
            // 主键列有多列，那么parameterType是ModelKey类
            answer.addAttribute(new Attribute("parameterType", introspectedTable.getPrimaryKeyType()));
        }

        // resultMap=BaseResultMap
        answer.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        // @mbg.generated
        context.getCommentGenerator().addComment(answer);

        StringBuilder selectCaluse = new StringBuilder();
        selectCaluse.append("SELECT ")
                .append(MybatisGeneratorConstants.XML_ELEMENT_BASE_COLUMN_LIST)
                .append(" FROM ")
                .append(introspectedTable.getFullyQualifiedTableNameAtRuntime());

        selectCaluse.append(" WHERE ");
        for (int i = 0; i < primaryKeyColumns.size(); i++) {
            if (i != 0) {
                selectCaluse.append(" AND ");
            }
            IntrospectedColumn column = primaryKeyColumns.get(i);
            selectCaluse.append(column.getActualColumnName());
            selectCaluse.append(" = ");
            selectCaluse.append(MyBatis3FormattingUtilities.getParameterClause(column, ""));
            answer.addElement(new TextElement(selectCaluse.toString()));
        }
//        answer.addElement(new TextElement(selectCaluse.toString()));
        parentElement.addElement(answer);
    }
}
