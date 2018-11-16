package com.demo.boot.dao.codegen.generator;

import com.demo.boot.dao.codegen.generator.xml.*;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.BaseColumnListElementGenerator;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * 自定义XMLMapperGenerator，以实现自己的Mapper接口和对应的xml
 *
 * @Author wude
 * @Create 2017-06-14 10:49
 */
public class DemoXmlMapperGenerator extends XMLMapperGenerator {

    @Override
    protected XmlElement getSqlMapElement() {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        progressCallback.startTask(getString(
                "Progress.12", table.toString())); //$NON-NLS-1$
        XmlElement answer = new XmlElement("mapper"); //$NON-NLS-1$
        String namespace = introspectedTable.getMyBatis3SqlMapNamespace();
        answer.addAttribute(new Attribute("namespace", //$NON-NLS-1$
                namespace));

        context.getCommentGenerator().addRootComment(answer);

        addResultMapWithoutBLOBsElementToXml(answer);
        /*addResultMapWithBLOBsElement(answer);
        addExampleWhereClauseElement(answer);
        addMyBatis3UpdateByExampleWhereClauseElement(answer);
        addBaseColumnListElement(answer);
        addBlobColumnListElement(answer);
        addSelectByExampleWithBLOBsElement(answer);
        addSelectByExampleWithoutBLOBsElement(answer);
        addSelectByPrimaryKeyElement(answer);
        addDeleteByPrimaryKeyElement(answer);
        addDeleteByExampleElement(answer);*/
        // addBaseColumnList sql
        addBaseColumnListElementToXml(answer);
        // addInsertElement(answer);
        addInsertElementToXml(answer);
        // 添加insertList方法
        addInsertList(answer);
        /*addInsertSelectiveElement(answer);
        addCountByExampleElement(answer);
        addUpdateByExampleSelectiveElement(answer);
        addUpdateByExampleWithBLOBsElement(answer);
        addUpdateByExampleWithoutBLOBsElement(answer);
        addUpdateByPrimaryKeySelectiveElement(answer);
        addUpdateByPrimaryKeyWithBLOBsElement(answer);
        addUpdateByPrimaryKeyWithoutBLOBsElement(answer);*/

        // 添加selectById方法
        addSelectByIdElement(answer);
        // 添加selectOne方法
        addSelectOneElement(answer);
        // 添加selectList方法
        addSelectListElement(answer);
        // 添加selectAll方法
        addSelectAllElement(answer);
        // 添加deleteById方法
        addDeleteById(answer);
        // 添加update方法
        addUpdate(answer);

        return answer;
    }

    private void addResultMapWithoutBLOBsElementToXml(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new ResultMapWithoutBLOBsElementCustomGenerator(false);
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    private void addBaseColumnListElementToXml(XmlElement answer) {
        if (introspectedTable.getAllColumns().size() > 0) {
            AbstractXmlElementGenerator elementGenerator = new BaseColumnListElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, answer);
        }
    }

    private void addSelectByIdElement(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new SelectByIdElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    private void addSelectOneElement(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new SelectOneElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    private void addSelectListElement(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new SelectListElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    private void addSelectAllElement(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new SelectAllElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    private void addDeleteById(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new DeleteByIdElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    private void addUpdate(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new UpdateElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    private void addInsertElementToXml(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new InsertElementCustomGenerator(true);
        initializeAndExecuteGenerator(elementGenerator, answer);
    }

    private void addInsertList(XmlElement answer) {
        AbstractXmlElementGenerator elementGenerator = new InsertListElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, answer);
    }
}
