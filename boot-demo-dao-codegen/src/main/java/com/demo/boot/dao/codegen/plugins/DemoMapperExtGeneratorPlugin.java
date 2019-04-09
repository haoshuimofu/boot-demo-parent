package com.demo.boot.dao.codegen.plugins;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ModelMapperExt.xml：ModelDao中自定义接口的sqlMap实现
 *
 * @author wude
 * @create 2018-04-19 17:10
 */
public class DemoMapperExtGeneratorPlugin extends PluginAdapter {

    private ShellCallback shellCallback = null;

    public DemoMapperExtGeneratorPlugin() {
        shellCallback = new DefaultShellCallback(false);
    }


    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {

        List<GeneratedXmlFile> generatedXmlFiles = new ArrayList<>();
        for (GeneratedXmlFile generatedXmlFile : introspectedTable.getGeneratedXmlFiles()) {
            String targetProject = generatedXmlFile.getTargetProject();
            String targetPackage = generatedXmlFile.getTargetPackage();
            String mapperExtFileName = introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "MapperExt.xml";
            File mapperExtFile = new File(targetProject + "/" + targetPackage + "/" + mapperExtFileName);
            // 如果XxMapperExt.xml文件已存在, 直接跳过
            if (mapperExtFile.exists()) {
                return null;
            }

            XmlElement mapper = new XmlElement("mapper");
            mapper.addAttribute(new Attribute("namespace", introspectedTable.getMyBatis3SqlMapNamespace()));
            Document document = new Document("-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
            document.setRootElement(mapper);

            // 追加空白文本，防止mapper标签以/结尾
            TextElement blankElement = new TextElement("");
            document.getRootElement().addElement(blankElement);

            GeneratedXmlFile generatedExtXmlFile = new GeneratedXmlFile(document, mapperExtFileName, targetPackage, targetProject, true, context.getXmlFormatter());
            generatedXmlFiles.add(generatedExtXmlFile);
        }
        return generatedXmlFiles;
    }

}
