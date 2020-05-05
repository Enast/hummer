package org.enast.hummer.perfectmat.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * @author zhujinming6
 * @create 2018-05-11 15:21
 * @update 2018-05-11 15:21
 **/
public abstract class AbstractQueue<T> {

    protected Logger log = LoggerFactory.getLogger(AbstractQueue.class);

    /**
     * 数据拉取超时时间(毫秒)
     */
    protected ThreadLocal<Long> timeout = new ThreadLocal<>();

    /**
     * 队列大小 
     */
    public int QUEUE_LENGTH = 10000;

    /**
     * 基于内存的阻塞队列
     *
     * @param t
     */
    public BlockingQueue<T> queue;

    /**
     * 拉取元素
     */
    public <T> List<T> poll() {
        return null;
    }

    /**
     * 拉取元素
     */
    public <T> List<T> poll(int size) {
        return null;
    }

    public List<T> poll(int count, long delayTime) {
        long currentTime = System.currentTimeMillis();
        // 进入一次批量操作的周期或者队列数量大于100时,做批量操作
        if (timeout.get() == null || timeout.get() <= currentTime || queue.size() > count) {
            try {
                List<T> dataList = list(count);
                // 超时时间周期为2秒
                timeout.set(currentTime + delayTime);
                return dataList;
            } catch (Exception e) {
                log.error("", e);
            }
        } else {
            setTimeOut(currentTime, delayTime);
        }
        return null;
    }

    private void setTimeOut(long currentTime, long delayTime) {
        try {
            long t = timeout.get() - currentTime;
            if (t > delayTime) {
                // 服务器时间改小了
                timeout.set(currentTime + delayTime);
                log.warn("t > delayTime,t:{},delayTime:{}", t, delayTime);
                return;
            } else if (t < 0) {
                // 服务器时间改大了
                timeout.set(currentTime);
                log.warn("t < 0 add delayTime,t:{},delayTime:{}", t, delayTime);
                return;
            }
            Thread.sleep(t);
        } catch (InterruptedException e) {
            log.error("", e);
        }
    }

    public <T> Map<String, List<T>> pollMap(int size, long delayTime) {
        return null;
    }

    /**
     * 添加数据
     */
    public void put(T t) {
    }

    protected List<T> list(int count) {
        int size = queue.size();
        if (size > count) {
            size = count;
        }
        List<T> dataList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            dataList.add(queue.poll());
        }
        return dataList;
    }

    public int currentSize() {
        return queue.size();
    }

    public int capacity() {
        return QUEUE_LENGTH;
    }
}
