package com.demo.boot.dao.codegen.utils;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;

import java.util.ArrayList;
import java.util.List;

/**
 * 拓展{@link org.mybatis.generator.codegen.mybatis3.ListUtilities}
 *
 * @Author ddmc
 * @Create 2019-04-12 13:59
 */
public class DemoListUtilities extends ListUtilities {

    public static List<IntrospectedColumn> removeIdentityAndAutoIncrementColumns(List<IntrospectedColumn> columns) {
        List<IntrospectedColumn> filteredList = new ArrayList<IntrospectedColumn>();
        for (IntrospectedColumn ic : columns) {
            if (!ic.isIdentity() || !ic.isAutoIncrement()) {
                filteredList.add(ic);
            }
        }
        return filteredList;
    }


}