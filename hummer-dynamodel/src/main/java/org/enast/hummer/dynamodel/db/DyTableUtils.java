package org.enast.hummer.dynamodel.db;

/**
 * @author zhujinming6
 * @create 2020-04-13 14:06
 * @update 2020-04-13 14:06
 **/
public class DyTableUtils {

    private final static String pre = "tb_model_";

    public static String tableName(String modelIdentify) {
        return pre + modelIdentify.toLowerCase();
    }
}
