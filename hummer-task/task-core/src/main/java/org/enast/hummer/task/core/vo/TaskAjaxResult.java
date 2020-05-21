package org.enast.hummer.task.core.vo;

import java.io.Serializable;

public class TaskAjaxResult<T> implements Serializable {

    private static final long serialVersionUID = 33L;

    private String code;

    private String msg;
    private T data;

    public TaskAjaxResult() {
    }

    public TaskAjaxResult(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AjaxResult{" + "resultCode=" + code + ", message='" + msg + '\'' + ", data=" + data + '}';
    }

    public static <T> TaskAjaxResult<T> buildSuccess() {
        TaskAjaxResult<T> result = new TaskAjaxResult<>();
        result.setCode("0");
        return result;
    }

}
