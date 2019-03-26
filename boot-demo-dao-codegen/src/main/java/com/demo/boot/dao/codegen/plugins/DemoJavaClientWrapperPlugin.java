package com.demo.boot.dao.codegen.plugins;

import com.demo.boot.base.dao.BaseDao;
import com.demo.boot.dao.codegen.generator.DemoCommentGenerator;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * 对生成的XxDao.java进行包装, 主要Dao主键 && BaseDao及其泛型 设置
 *
 * @author wude
 * @version 1.0.0
 * @create 2018-04-19 17:10
 */
public class DemoJavaClientWrapperPlugin extends PluginAdapter {

    /**
     * Dao接口源文件所在Project && Package
     */
    private String javaClientTargetPackage;
    private String javaClientTargetProject;

    private String modelTargetPackage;
    private String modelTargetProject;

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        if (javaClientTargetProject == null) {
            this.javaClientTargetProject = properties.getProperty("javaClientTargetProject");
        }
        if (javaClientTargetPackage == null) {
            this.javaClientTargetPackage = properties.getProperty("javaClientTargetPackage");
        }
        if (modelTargetPackage == null) {
            this.modelTargetPackage = properties.getProperty("modelTargetPackage");
        }
        if (modelTargetProject == null) {
            this.modelTargetProject = properties.getProperty("modelTargetProject");
        }
    }

    @Override
    public boolean validate(List<String> list) {
        Assert.notNull(javaClientTargetPackage, "javaClientTargetPackage is null!");
        Assert.notNull(javaClientTargetProject, "javaClientTargetProject is null!");
        return true;
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        File modelFile = new File(modelTargetProject + "/" +
                modelTargetPackage.replaceAll("\\.", "\\/") + "/" + introspectedTable.getPrimaryKeyType() + ".java");
        System.out.println(modelFile.getAbsolutePath() + " --------> " + modelFile.exists());
        if (modelFile.exists()) {
            return false;
        }
        return super.modelPrimaryKeyClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // 没有非主键列不生成Model类，只有Model类不合适，还是直接调用super类此方法直接返回true吧
        // if (introspectedTable.getNonPrimaryKeyColumns().size() < 1) return false;
        // super.modelBaseRecordClassGenerated()方法直接返回true，所以假如表只有主键列而没有非主键列也会生成对应的Model类
        // 只不过这个Model类是空的，继承自ModelKey类
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        File javaClientFile = new File(javaClientTargetProject + "/" +
                javaClientTargetPackage.replaceAll("\\.", "\\/") + "/" + interfaze.getType().getShortName() + ".java");
        // Dao源文件已存在, 跳过修改
        if (javaClientFile.exists()) {
            return false;
        }

        // 清空Dao源文件Method和Annotation
        interfaze.getAnnotations().clear();
        interfaze.getMethods().clear();

        // 生成注释
        CommentGenerator commentGenerator = introspectedTable.getContext().getCommentGenerator();
        if (commentGenerator != null) {
            ((DemoCommentGenerator) commentGenerator).addMapperInterfaceComment(interfaze, introspectedTable);
        }

        // Import导入@Repository类, 并为Dao添加@Repository注解
        FullyQualifiedJavaType repositoryJavaType = new FullyQualifiedJavaType(Repository.class.getName());
        interfaze.addImportedType(repositoryJavaType);
        interfaze.getAnnotations().add("@" + Repository.class.getSimpleName());

        // 继承BaseDao, 并设置泛型参数
        FullyQualifiedJavaType parentInterface = new FullyQualifiedJavaType(BaseDao.class.getName());
        interfaze.addImportedType(parentInterface);
        // BaseDao泛型参数
        FullyQualifiedJavaType modelJavaType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        importType(interfaze, modelJavaType);
        parentInterface.addTypeArgument(modelJavaType);
        FullyQualifiedJavaType idJavaType = null;
        if (introspectedTable.getPrimaryKeyColumns().size() > 1) {
            idJavaType = new FullyQualifiedJavaType(introspectedTable.getPrimaryKeyType());
            importType(interfaze, idJavaType);
        } else {
            idJavaType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType();
        }
        interfaze.addImportedType(parentInterface);
        parentInterface.addTypeArgument(idJavaType);
        interfaze.addSuperInterface(new FullyQualifiedJavaType(parentInterface.getShortName()));
        return true;
    }

    public void importType(Interface interfaze, FullyQualifiedJavaType importJavaType) {
        Set<FullyQualifiedJavaType> importJavaTypes = interfaze.getImportedTypes();
        boolean exist = false;
        if (importJavaTypes != null && importJavaTypes.size() > 0) {
            for (FullyQualifiedJavaType javaType : importJavaTypes) {
                if (javaType.getFullyQualifiedName().equals(importJavaType.getFullyQualifiedName())) {
                    exist = true;
                    break;
                }
            }
        }
        if (!exist) {
            interfaze.addImportedType(importJavaType);
        }
    }
}