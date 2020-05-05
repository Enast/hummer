package org.enast.hummer.task.core.common;

/**
 * @author zhujinming6
 * @create 2019-10-14 14:33
 * @update 2019-10-14 14:33
 **/
public enum UnifyTaskStatusType {

    /**
     * 执行中
     */
    executing,
    /**
     * 等待中
     */
    watting,
    /**
     * 结束
     */
    success,
    /**
     * 执行失败
     */
    fail;
}
