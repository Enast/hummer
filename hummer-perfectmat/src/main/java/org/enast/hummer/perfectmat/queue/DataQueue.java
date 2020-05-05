package org.enast.hummer.perfectmat.queue;

import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 本地队列
 *
 * @author zhujinming6
 * @create 2018-05-11 15:17
 * @update 2018-05-11 15:17
 **/
public class DataQueue<T> extends AbstractQueue<T> {

    public DataQueue(int size) {
        this.QUEUE_LENGTH = size;
        queue = new LinkedBlockingQueue<T>(size);
        log = LoggerFactory.getLogger(DataQueue.class);
    }

    /**
     * 保存策略:数据过大,超时1s,抛出异常
     *
     * @param t
     */
    @Override
    public void put(T t) {
        // 误差一个
        try {
            queue.offer(t, 1, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("queue.put error", "", e);
        }
    }

    public boolean filterData() {
        return (QUEUE_LENGTH - queue.size() < 100);
    }
}
