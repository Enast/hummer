package org.hummer.task.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

/*
* http 接口调用 属性类
 *@auther shixiafeng
 *@create 2018-01-30
 */
@ConfigurationProperties(prefix = "bic")
public class TaskBicProperties {
    /**
     * 是否需要bic验证
     */
    private boolean need;
    /**
     * 校验token
     */
    private String token = "EAAAAAAQAACgsmy+1wGNQPKm83hzbepFMWLgabPs5umzazNB8xa6oh4f+xy4ww7tEP+Qa37W+pQ=";

    public boolean isNeed() {
        return need;
    }

    public void setNeed(boolean need) {
        this.need = need;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
