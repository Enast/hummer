package org.enast.hummer.task.server.thread;

import org.enast.hummer.common.SpringBeanFactory;
import org.enast.hummer.task.server.delayqueue.UnifyTaskQueueElement;
import org.enast.hummer.task.server.delayqueue.UnifyTaskQueueManager;
import org.enast.hummer.task.server.service.impl.UnifyTaskServiceImpl;
import org.enast.hummer.task.core.common.UnifyTaskStatusType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author zhujinming6
 * @create 2018-07-03 17:47
 * @update 2018-07-03 17:47
 **/
public class UnifyTaskDispatchThread extends Thread {

    Logger log = LoggerFactory.getLogger(UnifyTaskDispatchThread.class);

    AtomicBoolean isRunning = new AtomicBoolean(true);

    CountDownLatch shutdownLatch = new CountDownLatch(1);

    public UnifyTaskDispatchThread(String name) {
        super(name);
    }

    public UnifyTaskDispatchThread() {
    }

    @Override
    public void run() {
        UnifyTaskServiceImpl unifyTaskService = SpringBeanFactory.getBean(UnifyTaskServiceImpl.class);
        if (unifyTaskService == null) {
            log.error( "UnifyTaskServiceImpl is null ");
            return;
        }
        log.info("start UnifyTaskDispatchThread");
        while (isRunning.get()) {
            try {
                // 批量处理延时队列的元素
                UnifyTaskQueueElement msg = UnifyTaskQueueManager.take();
                if (msg == null) {
                    continue;
                }
                // 1.修改任务状态
                unifyTaskService.updateTaskStatusAndTryTimes(msg.getServer(), msg.getTaskNo(), UnifyTaskStatusType.executing, 0);
                log.info("out from delay queue and success exe:{},{}", msg.getServer(), msg.getTaskNo());
                // 调用客户端接口
                unifyTaskService.dispatchTask(msg);
            } catch (Exception e) {
                log.error("error", "", e);
            }
        }
        // 确保上面代码执行完成
        shutdownLatch.countDown();
    }

    @PreDestroy
    public void shutdown() {
        try {
            isRunning.set(false);
            this.interrupt();
            // 确保上面代码执行完成
            shutdownLatch.await();
        } catch (InterruptedException e) {
            throw new Error("Interrupted when shutting down consumer worker thread.");
        }
    }
}
