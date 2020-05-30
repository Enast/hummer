package org.enast.hummer.task.server.delayqueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * 延时任务管理类
 *
 * @author zhujinming6
 * @create 2019-10-14 15:01
 * @update 2019-10-14 15:01
 **/
public class UnifyTaskQueueManager {

    private static final Logger log = LoggerFactory.getLogger(UnifyTaskQueueManager.class);

    /**
     * 延时队列
     */
    private static DelayQueue<UnifyTaskQueueElement> delayQueue = new DelayQueue<>();
    public static Set<String> taskSet = new HashSet<>();

    /**
     * 添加任务
     *
     * @param server
     * @param delayTime 延时时间
     * @param unit      时间单位
     */
    public static void put(String server, String taskNo,String name,String id, long delayTime, TimeUnit unit) {
        // 获取延时时间
        long timeout = TimeUnit.MILLISECONDS.convert(delayTime, unit);
        // 将任务封装成实现Delayed接口的消息体
        UnifyTaskQueueElement delayOrder = new UnifyTaskQueueElement(server, taskNo, timeout,name,id);
        // 将消息体放到延时队列中
        try {
            delayQueue.add(delayOrder);
        } catch (Exception e) {
            log.error("queue.put error", "", e);
        }
    }

    public static void put(UnifyTaskQueueElement delayOrder, TimeUnit unit) {
        // 获取延时时间
        long timeout = TimeUnit.MILLISECONDS.convert(delayOrder.getExecuteTime(), unit);
        delayOrder.setExecuteTime(timeout);
        // 将消息体放到延时队列中
        try {
            delayQueue.add(delayOrder);
            taskSet.add(delayOrder.getServer() + delayOrder.getTaskNo());
        } catch (Exception e) {
            log.error("queue.put error", "", e);
        }
    }

    /**
     * 删除任务
     *
     * @param server 服务
     * @return
     */
    public static boolean removeTask(String server, String taskNo) {
        UnifyTaskQueueElement msg = new UnifyTaskQueueElement(server, taskNo);
        return delayQueue.remove(msg);
    }

    /**
     * 获取延时任务
     *
     * @return 资源的离线任务
     */
    public static UnifyTaskQueueElement take() {
        UnifyTaskQueueElement msg = null;
        try {
            msg = delayQueue.take();
            taskSet.remove(msg.getServer() + msg.getTaskNo());
        } catch (InterruptedException e) {
            log.error("delayQueue.take() error", "", e);
        }
        return msg;
    }

    /**
     * 批量 获取延时任务
     *
     * @return 资源的离线任务列表
     */
    public static List<UnifyTaskQueueElement> takeBatch(int size) {
        List<UnifyTaskQueueElement> msgList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            UnifyTaskQueueElement msg = delayQueue.poll();
            if (msg == null) {
                continue;
            }
            msgList.add(msg);
        }
        return msgList;
    }
}
