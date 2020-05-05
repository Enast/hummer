package org.enast.hummer.config.vo;

/**
 * 配置信息实体
 *
 * @author zhujinming6
 * @create 2019-10-18 14:47
 * @update 2019-10-18 14:47
 **/
public class UnifyConfigInfo {

    /**
     * 配置所在bean
     */
    private String beanName;

    /**
     * 配置类型
     */
    private PropertyType type;

    /**
     * 属性名称
     */
    private String filedName;

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }
}
