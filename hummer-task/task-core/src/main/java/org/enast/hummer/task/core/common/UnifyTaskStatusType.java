package org.enast.hummer.task.core.common;

/**
 * @author zhujinming6
 * @create 2019-10-14 14:33
 * @update 2019-10-14 14:33
 **/
public enum UnifyTaskStatusType {

    /**
     *
     */
    all,
    /**
     * 执行中
     */
    executing,
    /**
     * 等待中
     */
    waiting,
    /**
     * 结束
     */
    success,
    /**
     * 客户端执行失败
     */
    fail,
    /**
     * 服务端直接调用失败
     */
    serverFastFail,
    /**
     * 服务端重试调用失败
     */
    severRetryFail,
}
