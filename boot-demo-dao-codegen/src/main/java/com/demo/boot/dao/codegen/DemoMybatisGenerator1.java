package com.demo.boot.dao.codegen;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.VerboseProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @Author ddmc
 * @Create 2019-04-12 10:27
 */
public class DemoMybatisGenerator1 {

    private static final String USER_MODULE_CONFIG = "user/generatorConfig.xml";
    private static final String BUSINESS_MODULE_CONFIG = "business/generatorConfig.xml";
    private static final String KANBAN_MODULE_CONFIG = "kanban/generatorConfig.xml";
    private static final String SCM_MODULE_CONFIG = "scm/generatorConfig.xml";

    public static void main(String[] args) throws FileNotFoundException {

        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(KANBAN_MODULE_CONFIG);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = null;
        try {
            config = cp.parseConfiguration(inputStream);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);

            ProgressCallback progressCallback = new VerboseProgressCallback();
            myBatisGenerator.generate(progressCallback);
        } catch (IOException | XMLParserException | InvalidConfigurationException | InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}