package org.enast.hummer.demo.task.service.impl;

import org.enast.hummer.common.TimeUtils;
import org.enast.hummer.demo.task.service.TestService;
import org.enast.hummer.task.client.service.TaskExecuteService;
import org.enast.hummer.task.core.aop.EnableUnifyTask;
import org.enast.hummer.task.core.aop.UnifyTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.dnd.DropTarget;
import java.util.Date;

/**
 * 测试类
 *
 * @author zhujinming6
 * @create 2019-10-11 12:30
 * @update 2019-10-11 12:30
 **/
@EnableUnifyTask
@Service
public class TestServiceImpl implements TestService {

    private Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

    @Resource
    TaskExecuteService taskExecuteService;

    /**
     * 执行任务
     *
     * @return
     */
    @Override
    @UnifyTask(taskNo = "one-hours-task", cron = "0 0 */1 * * ?",name = "每小时任务")
    public void hoursTask() {
        // 处理逻辑
        log.info("start one-hours-task，now is:{} ", TimeUtils.date2Str_yyyy_MM_dd_HH_mm_ss(new Date()));
        return;
    }

    /**
     * 执行任务
     *
     * @return
     */
    @UnifyTask(taskNo = "five-minutes-task", cron = "0 */5 * * * ?",name = "周期5分钟任务")
    public void min5Task() {
        // 处理逻辑
        log.info("start five-minutes-task，now is:{} ", TimeUtils.date2Str_yyyy_MM_dd_HH_mm_ss(new Date()));
        return;
    }

    /**
     * 执行任务
     *
     * @return
     */
    @UnifyTask(taskNo = "pre-three-minutes-task", cron = "0 */5 * * * ?",name = "周期3分钟的前置任务")
    public void pre3MinTask() {
        // 处理逻辑
        log.info("start pre-three-minutes-task，now is:{} ", TimeUtils.date2Str_yyyy_MM_dd_HH_mm_ss(new Date()));
        return;
    }

    /**
     * 执行任务
     *
     * @return
     */
    @UnifyTask(taskNo = "next-three-minutes-task", cron = "0 */5 * * * ?",name = "周期3分钟后置任务")
    public void flow3MinTask() {
        // 处理逻辑
        log.info("start next-three-minutes-task，now is:{} ", TimeUtils.date2Str_yyyy_MM_dd_HH_mm_ss(new Date()));
        return;
    }

    /**
     * 执行任务
     *
     * @return
     */
    @UnifyTask(taskNo = "ten-minutes-task", cron = "0 */10 * * * ?",name = "周期10分钟任务")
    public void min10Task() {
        // 处理逻辑
        log.info("start ten-minutes-task，now is:{} ", TimeUtils.date2Str_yyyy_MM_dd_HH_mm_ss(new Date()));
        return;
    }


    /**
     * 执行任务
     *
     * @return
     */
    @UnifyTask(taskNo = "half-hours-task", cron = "0 */30 * * * ?",name = "周期半小时任务")
    public void min30Task() {
        // 处理逻辑
        log.info("start half-hours-task，now is:{} ", TimeUtils.date2Str_yyyy_MM_dd_HH_mm_ss(new Date()));
        return;
    }

    /**
     * 执行任务
     *
     * @return
     */
    @UnifyTask(taskNo = "one-day-task", cron = "0 0 1 * * ?",name = "每天凌晨1点的任务")
    public void dayTask() {
        // 处理逻辑
        log.info("start one-day-task，now is:{} ", TimeUtils.date2Str_yyyy_MM_dd_HH_mm_ss(new Date()));
        return;
    }
}
