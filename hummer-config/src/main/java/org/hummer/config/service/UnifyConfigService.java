package org.hummer.config.service;

/**
 * @author zhujinming6
 * @create 2019-10-18 14:38
 * @update 2019-10-18 14:38
 **/
public interface UnifyConfigService {

    /**
     * 项目启动时,调用
     */
    void resetAllProperties();

    void updateProperty(String propertyName);
}
