package org.enast.hummer.perfectmat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.enast.hummer.common.SpringBeanFactory;
import org.enast.hummer.perfectmat.entity.AccessData;
import org.enast.hummer.perfectmat.entity.ResourceQuotas;
import org.enast.hummer.perfectmat.queue.DataQueueCollection;
import org.enast.hummer.perfectmat.service.impl.ParseDataServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author zhujinming6
 * @create 2018-05-11 16:05
 * @update 2018-05-11 16:05
 **/
public class AccessThread extends Thread {

    Logger log = LoggerFactory.getLogger(AccessThread.class);

    AtomicBoolean isRunning = new AtomicBoolean(true);

    CountDownLatch shutdownLatch = new CountDownLatch(1);

    Executor executor;

    Semaphore semaphore;

    private int consumerThreadCount = 8;
    private int batchSize = 2000;

    public AccessThread(String name, Executor executor, int consumerThreadCount, int batchSize) {
        super(name);
        this.executor = executor;
        this.consumerThreadCount = consumerThreadCount;
        this.batchSize = batchSize;
        semaphore = new Semaphore(consumerThreadCount);
    }

    @Override
    public void run() {
        ObjectMapper objectMapper = SpringBeanFactory.getBean(ObjectMapper.class);
        if (objectMapper == null) {
            log.error("objectMapper is null. ");
            return;
        }
        ParseDataServiceImpl pafParseDataService = SpringBeanFactory.getBean(ParseDataServiceImpl.class);
        if (pafParseDataService == null) {
            log.error("pafParseDataService is null. ");
            return;
        }
        while (isRunning.get()) {
            try {
                List<String> datas = (List) DataQueueCollection.pafQueue.poll(batchSize, 2000);
                if (CollectionUtils.isEmpty(datas)) {
                    continue;
                }
                semaphore.acquire();
                executor.execute(() -> {
                    try {
                        long time = System.currentTimeMillis();
                        log.warn("start parse data size", "dataSize", datas.size());
                        List<AccessData> accessDataList = new ArrayList<>();
                        for (String data : datas) {
                            AccessData accessData = null;
                            try {
                                accessData = objectMapper.readValue(data, AccessData.class);
                            } catch (Exception e) {
                                log.error("", e);
                                continue;
                            }
                            if (accessData == null || StringUtils.isBlank(accessData.getCode())) {
                                log.debug("jsonObject is null or not containsKey code.");
                                continue;
                            }
                            accessDataList.add(accessData);
                        }
                        for (AccessData accessData : accessDataList) {
                            parseMessage(accessData, pafParseDataService,null);
                        }
                        log.warn("finished parse data,dataSize:{},time", accessDataList.size(), System.currentTimeMillis() - time);
                    } catch (Exception e) {
                        log.error("", e);
                    } finally {
                        semaphore.release();
                    }
                });
            } catch (Exception e) {
                log.error("", e);
            }
        }
        // 确保上面代码执行完成
        shutdownLatch.countDown();
    }

    private void parseMessage(AccessData accessData, ParseDataServiceImpl pafParseDataService, ResourceQuotas quota) {
        try {
            pafParseDataService.parseData(null,accessData, quota,null);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    @PreDestroy
    public void shutdown() {
        try {
            isRunning.set(false);
            if (!this.isInterrupted()) {
                this.interrupt();
            }
            // 确保上面代码执行完成
            shutdownLatch.await();
        } catch (InterruptedException e) {
            throw new Error("Interrupted when shutting down consumer worker thread.");
        }
    }


    //    public static void main(String[] a) {
    //        ObjectMapper objectMapper = new ObjectMapper();
    //        try {
    //            JsonNode jsonNode = objectMapper.readTree("{\n" + "\"code\": \"0\",\n" + "\"indexCode\": \"c56e6ada0e9c4a27a5e03f2926bb8f14\",\n" + "\"deviceTypeCode\": \"camera\",\n" + "\"typeErrorCode\": null,\n" + "\"dataType\": 2,\n" + "\"businessType\": \"record\",\n" + "\"collectTime\": \"2019-04-10T16:24:47.489+08:00\",\n" + "\"reportType\": 1,\n" + "\"msg\": \"\",\n" + "\"data\": {\"1\":\"\"}\n" + ",\"array\":[{\"1\":\"\"},{\"1\":\"\"}]}");
    //            System.out.println(jsonNode.get("code").asText());
    //            JsonNode jsonNode1 = jsonNode.get("data");
    //            JsonNode jsonNode2 = jsonNode.get("array");
    //            if (jsonNode1 instanceof List) {
    //
    //            } else if (jsonNode2 instanceof Map) {
    //
    //            }
    //            //            System.out.println(jsonNode.path("code1").asText());
    //        } catch (IOException e)
    //
    //        {
    //            e.printStackTrace();
    //        }
    //    }
}
