package org.hummer.cluster.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 机器码生成类
 *
 * @author zhujinming6
 * @create 2019-10-09 12:28
 * @update 2019-10-09 12:28
 **/
public class MachineCodeUtils {

    private final static Logger logger = LoggerFactory.getLogger(MachineCodeUtils.class);

    public static String getCode() {
        try {
            List<String> macList = MacToolUtils.getMacList();
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : macList) {
                stringBuilder.append(s.replaceAll("-", ""));
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            logger.error("", e);
        }
        return StringUtils.EMPTY;
    }

    //    public static void main(String[] args) throws Exception {
    //        System.out.println("本机地址：" + getCode());
    //    }
}
