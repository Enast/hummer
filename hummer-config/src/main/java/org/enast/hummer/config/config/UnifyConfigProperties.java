package org.enast.hummer.config.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 统一配置属性
 *
 * @author zhujinming6
 * @create 2019-10-18 17:26
 * @update 2019-10-18 17:26
 **/
@ConfigurationProperties(prefix = "hummer.unify.config")
public class UnifyConfigProperties {

    private static final Logger log = LoggerFactory.getLogger(UnifyConfigProperties.class);

    /**
     * 组件id
     */
    private String componentId;
    /**
     * 段id
     */
    private String segmentId;

    /**
     * 实例编号
     */
    private Integer index = 1;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public String getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }
}
