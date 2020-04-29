package org.hummer.task.vo;

import java.lang.reflect.Method;

/**
 * @author zhujinming6
 * @create 2019-10-11 16:48
 * @update 2019-10-11 16:48
 **/
public class UnifyTaskBean {

    private Object bean;
    private Method method;

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
