package com.demo.boot.dao.codegen;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.NullProgressCallback;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Mybatis Generator java调用入口
 *
 * @Author wude
 * @Create 2017-05-11 10:24
 */
public class DemoMybatisGenerator {

    private static final String USER_MODULE_CONFIG = "classpath:user/generatorConfig.xml";
    private static final String ORDER_MODULE_CONFIG = "classpath:order/generatorConfig.xml";


    public static void main(String[] args) throws FileNotFoundException {

        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        File configFile = ResourceUtils.getFile(USER_MODULE_CONFIG);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = null;
        try {
            config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);

            ProgressCallback progressCallback = new NullProgressCallback();
            // progressCallback = new VerboseProgressCallback(); // 详细处理回调
            myBatisGenerator.generate(progressCallback);
        } catch (IOException | XMLParserException | InvalidConfigurationException | InterruptedException | SQLException e) {
            e.printStackTrace();
        }
    }

}
