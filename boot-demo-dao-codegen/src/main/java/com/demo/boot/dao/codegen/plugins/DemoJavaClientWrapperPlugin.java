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
 * <p>对生成的Dao接口进行修改包装</p>
 * <p>继承自BaseDao<T, ID>，自动判别T和ID对应的java数据类型</p>
 * <p>为Dao接口自动添加@Repository注解</p>
 *
 * @author wude
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
        File modelKeyJavaFile = new File(modelTargetProject + "/" +
                introspectedTable.getPrimaryKeyType().replaceAll("\\.", "\\/") + ".java");
        System.out.println(modelKeyJavaFile.getAbsolutePath() + " ---> [" + introspectedTable.getBaseRecordType() + "]主键类已存在!");
        return super.modelPrimaryKeyClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        File javaClientFile = new File(javaClientTargetProject + "/" +
                javaClientTargetPackage.replaceAll("\\.", "\\/") + "/" + interfaze.getType().getShortName() + ".java");
        // 如果Dao对应的.java源文件已经存在则跳过，因为Dao里面可能已经有一些自定义接口了
        if (javaClientFile.exists()) {
            System.out.println(javaClientFile.getAbsolutePath() + " ---> [" + introspectedTable.getBaseRecordType() + "]Dao接口类已存在!");
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
        if (introspectedTable.getRules().generatePrimaryKeyClass()) {
            idJavaType = new FullyQualifiedJavaType(introspectedTable.getPrimaryKeyType());
            importType(interfaze, idJavaType);
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