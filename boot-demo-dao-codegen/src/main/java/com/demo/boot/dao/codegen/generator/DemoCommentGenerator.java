package com.demo.boot.dao.codegen.generator;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * MBG 自定义注释生成器
 *
 * @Author wude
 * @Create 2017-05-11 13:33
 */
public class DemoCommentGenerator implements CommentGenerator {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String mbgGenerated = "@mbg.generated";

    private Properties properties;
    private Properties sysProperties;
    private boolean suppressDate;
    private boolean suppressAllComments;
    private String currentDateStr;
    private String currentUsername;

    public DemoCommentGenerator() {
        super();
        properties = new Properties();
        properties.put("override", true);
        sysProperties = System.getProperties();
        suppressDate = false;
        suppressAllComments = false;
        currentDateStr = format.format(new Date());
        // 当前系统用户名
        Map<String, String> map = System.getenv();
        currentUsername = map.get("USERNAME");// 获取用户名
        if (currentUsername == null || "".equals(currentUsername.trim())) {
            currentUsername = sysProperties.getProperty("user.name");
        }
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);

        suppressDate = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));

        suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (!suppressAllComments) {
            field.addJavaDocLine("/**");
            if (introspectedColumn.getRemarks() == null || "".equals(introspectedColumn.getRemarks().trim())) {
                field.addJavaDocLine(" * " + introspectedColumn.getActualColumnName());
            } else {
                field.addJavaDocLine(" * " + introspectedColumn.getRemarks() + ": " + introspectedColumn.getActualColumnName());
            }
            field.addJavaDocLine(" * ");
            field.addJavaDocLine(" * " + mbgGenerated + " " + currentDateStr);
            field.addJavaDocLine("  */");
        }
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (!suppressAllComments) {
            field.addJavaDocLine("/**");
            field.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable());
            field.addJavaDocLine(" * ");
            field.addJavaDocLine(" * " + mbgGenerated + " " + currentDateStr);
            field.addJavaDocLine(" */");
        }
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (!suppressAllComments) {
            topLevelClass.addJavaDocLine("/**");
            topLevelClass.addJavaDocLine(" * @Description Automatically generated by MBG, please do not manually modify it.");
            topLevelClass.addJavaDocLine(" * @Author " + currentUsername);
            topLevelClass.addJavaDocLine(" * @Datetime " + mbgGenerated + " " + currentDateStr);
            topLevelClass.addJavaDocLine(" * @Table " + introspectedTable.getFullyQualifiedTable());
            topLevelClass.addJavaDocLine("*/");
        }
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        if (!this.suppressAllComments) {
            StringBuilder sb = new StringBuilder();
            innerClass.addJavaDocLine("/**");
            innerClass.addJavaDocLine(" * This class was generated by MyBatis Generator.");
            sb.append(" * This class corresponds to the database table ");
            sb.append(introspectedTable.getFullyQualifiedTable());
            innerClass.addJavaDocLine(sb.toString());
//            this.addJavadocTag(innerClass, markAsDoNotDelete);
            innerClass.addJavaDocLine(" */");
        }
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean b) {
        if (!this.suppressAllComments) {
            StringBuilder sb = new StringBuilder();
            innerClass.addJavaDocLine("/**");
            innerClass.addJavaDocLine(" * This class was generated by MyBatis Generator.");
            sb.append(" * This class corresponds to the database table ");
            sb.append(introspectedTable.getFullyQualifiedTable());
            innerClass.addJavaDocLine(sb.toString());
            innerClass.addJavaDocLine(" */");
        }
    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (!suppressAllComments) {
//            method.addJavaDocLine("/**");
//            method.addJavaDocLine(" * @return " + introspectedColumn.getRemarks());
//            method.addJavaDocLine(" * ");
//            method.addJavaDocLine(" * " + mbgGenerated + " " + currentDateStr);
//            method.addJavaDocLine(" */");
        }
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (!suppressAllComments) {
//            method.addJavaDocLine("/**");
//            method.addJavaDocLine(" * set " + introspectedColumn.getRemarks());
//            method.addJavaDocLine(" * ");
//            method.addJavaDocLine(" * @Param " + method.getParameters().get(0).getName() + " " + introspectedColumn.getRemarks());
//            method.addJavaDocLine(" * " + mbgGenerated + " " + currentDateStr);
//            method.addJavaDocLine(" */");
        }
    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * " + mbgGenerated + " " + currentDateStr);
        method.addJavaDocLine(" */");
    }

    /**
     * 给所有生成的java源文件追加文件注释（import上部）
     *
     * @param compilationUnit
     */
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {

    }

    /**
     * 给xml文件中每个sql追加一个mbg标识，否则会重复生成同一个sql
     *
     * @param xmlElement
     */
    @Override
    public void addComment(XmlElement xmlElement) {
        Element element = new TextElement("<!--" + mbgGenerated + " " + currentDateStr + "-->");
        xmlElement.addElement(0, element);
    }

    @Override
    public void addRootComment(XmlElement xmlElement) {

    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }


    public void addMapperInterfaceComment(Interface interfaze, IntrospectedTable introspectedTable) {

        interfaze.addJavaDocLine("/**");
        interfaze.addJavaDocLine(" * @Description Automatically generated by MBG.");
        interfaze.addJavaDocLine(" * @Author " + currentUsername);
        interfaze.addJavaDocLine(" * @Datetime " + currentDateStr);
        interfaze.addJavaDocLine(" * " + mbgGenerated + " " + currentDateStr);
        interfaze.addJavaDocLine("*/");
    }

}
