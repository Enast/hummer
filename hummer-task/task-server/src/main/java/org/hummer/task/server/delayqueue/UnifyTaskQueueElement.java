package org.hummer.task.server.delayqueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 任务延时队列
 * @author zhujinming6
 * @create 2019-10-14 14:51
 * @update 2019-10-14 14:51
 **/
public class UnifyTaskQueueElement implements Delayed, Serializable {

    Logger log = LoggerFactory.getLogger(UnifyTaskQueueElement.class);

    private String server;
    private String taskNo;
    private long executeTime;


    public UnifyTaskQueueElement(String server, String taskNo, long executeTime) {
        this.server = server;
        this.taskNo = taskNo;
        this.executeTime = executeTime;
    }

    public UnifyTaskQueueElement(String server, String taskNo) {
        this.server = server;
        this.taskNo = taskNo;
    }

    /**
     * 计算延迟时间
     */
    @Override
    public long getDelay(TimeUnit unit) {
        Long timeOut = this.executeTime - System.currentTimeMillis();
        Long result = unit.convert(timeOut, TimeUnit.MILLISECONDS);
        return result;
    }

    /**
     * 队列优先级排序用
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        UnifyTaskQueueElement other = (UnifyTaskQueueElement) o;
        long diff = executeTime - other.executeTime;
        if (diff > 0) {
            return 1;
        } else if (diff < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public int hashCode() {
        return (server + taskNo).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() == this.getClass()) {
            UnifyTaskQueueElement msg = (UnifyTaskQueueElement) obj;
            if (msg.getServer() == null || msg.getTaskNo() == null) {
                return false;
            } else if (msg.getServer().equals(this.server) && msg.getTaskNo().equals(this.taskNo)) {
                return true;
            }
        } else {
            return false;
        }
        return false;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(long executeTime) {
        this.executeTime = executeTime;
    }
}
