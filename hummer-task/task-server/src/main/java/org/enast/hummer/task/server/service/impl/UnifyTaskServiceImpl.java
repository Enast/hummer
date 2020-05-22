package org.enast.hummer.task.server.service.impl;

import com.alibaba.fastjson.JSON;
import org.enast.hummer.cluster.core.aop.ClusterMaster;
import org.enast.hummer.task.core.vo.*;
import org.enast.hummer.task.server.delayqueue.UnifyTaskQueueElement;
import org.enast.hummer.task.server.delayqueue.UnifyTaskQueueManager;
import org.enast.hummer.task.server.service.UnifyTaskDispatchService;
import org.enast.hummer.task.server.service.UnifyTaskService;
import org.enast.hummer.task.core.common.UnifyTaskStatusType;
import org.enast.hummer.task.server.model.UnifyTask;
import org.enast.hummer.task.server.model.UnifyTaskLog;
import org.enast.hummer.task.server.thread.UnifyTaskDispatchThread;
import org.enast.hummer.task.server.thread.UnifyTaskRetryThread;
import org.enast.hummer.task.server.biz.UnifyTaskBiz;
import org.enast.hummer.task.server.biz.UnifyTaskLogBiz;
import org.enast.hummer.task.server.web.vo.Pagination;
import org.enast.hummer.task.server.web.vo.TaskQueryVO;
import org.enast.hummer.task.server.web.vo.TaskVO;
import org.quartz.CronExpression;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import sf.common.wrapper.Page;
import sf.tools.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhujinming6
 * @create 2019-10-11 14:09
 * @update 2019-10-11 14:09
 **/
@Service
public class UnifyTaskServiceImpl implements UnifyTaskService {

    private static final Logger logger = LoggerFactory.getLogger(UnifyTaskServiceImpl.class);
    private static String LASTDISPATCHTIME = "HUMMER:TASK:LASTDISPATCHTIME";
    private static ScheduledExecutorService scheduledThreadPool;
    private static UnifyTaskDispatchThread taskDispatchThread;
    @Resource
    UnifyTaskBiz taskBiz;
    @Resource
    UnifyTaskLogBiz unifyTaskLogBiz;
    @Resource
    UnifyTaskDispatchService taskDispatchService;
    @Resource
    RedisTemplate redisTemplate;
    @Value("${hummer.unifyTask.config.period:90}")
    private Integer period;

    /**
     * 处理任务上报的状态
     *
     * @param taskStatus
     * @return
     */
    @Override
    public TaskAjaxResult<Boolean> dealTaskStatus(UnifyTaskStatus taskStatus) {
        TaskAjaxResult taskAjaxResult = new TaskAjaxResult();
        taskAjaxResult.setCode("0");
        logger.info("{}", JSON.toJSONString(taskStatus));
        // 1.任务执行成功,设置任务状态为结束状态
        if (taskStatus.getSuccess()) {
            taskBiz.updateTaskStatusAndLastExcuteTime(taskStatus.getServer(), taskStatus.getTaskNo(), UnifyTaskStatusType.success, taskStatus.getTime());
            UnifyTask task = taskBiz.findOne(taskStatus.getServer(), taskStatus.getTaskNo());
            // 1.1记录成功日志
            UnifyTaskLog log = new UnifyTaskLog();
            log.setCreated(taskStatus.getTime());
            log.setId(StringUtils.uuid32());
            log.setServer(taskStatus.getServer());
            log.setName(taskStatus.getTaskNo());
            log.setTaskId(task == null ? "" : task.getId());
            log.setStatus(UnifyTaskStatusType.success);
            log.setTaskLog("success");
            log.setDuration(taskStatus.getDuration());
            unifyTaskLogBiz.add(log);
        } else {
            // 2.任务执行失败
            UnifyTask task = taskBiz.findOne(taskStatus.getTaskNo(), taskStatus.getServer());
            if (task != null) {
                Integer retryTimes = task.getRetryTimes() == null ? 0 : task.getRetryTimes();
                Integer retryTimesLimit = task.getRetryTimesLimit();
                //重试达上限
                if (retryTimesLimit == null || retryTimesLimit == 0 || retryTimes >= retryTimesLimit) {
                    // 2.2 不进行重试,设置任务结束
                    logger.info("do not retry,task end,retryTimes:{},retryTimesLimit:{},TaskNo:{}", retryTimes, retryTimesLimit, taskStatus.getTaskNo());
                    taskBiz.updateTaskStatusAndLastExcuteTime(taskStatus.getServer(), taskStatus.getTaskNo(), UnifyTaskStatusType.fail, taskStatus.getTime());
                } else {
                    new Thread(new UnifyTaskRetryThread(retryTimes, retryTimesLimit, taskDispatchService, task, taskBiz, unifyTaskLogBiz)).start();
                }
                // 2.2记录失败日志
                UnifyTaskLog log = new UnifyTaskLog();
                log.setCreated(taskStatus.getTime());
                log.setId(StringUtils.uuid32());
                log.setServer(task.getServer());
                log.setName(task.getTaskNo());
                log.setTaskId(task.getId());
                log.setStatus(UnifyTaskStatusType.fail);
                log.setTaskLog("fail");
                log.setDuration(taskStatus.getDuration());
                unifyTaskLogBiz.add(log);
            }
        }
        taskAjaxResult.setData(true);
        return taskAjaxResult;
    }

    /**
     * 主节点
     */
    @Override
    @ClusterMaster
    public void dispatchTask() {
        logger.info("dispatchTask start");
        List<UnifyTask> taskList = taskBiz.findList();
        if (CollectionUtils.isEmpty(taskList)) {
            logger.info("no tasks find");
            return;
        }
        Long timeMills = System.currentTimeMillis();
        // 上次调度结束时间
        Long lastDispatchTime = (Long) redisTemplate.opsForValue().get(LASTDISPATCHTIME);
        long nextDispatchTime;
        long dValue = 0;
        if (lastDispatchTime == null) {
            // 缓存校时
            lastDispatchTime = timeMills;
        } else {
            // 当前方法每三分钟执行一次
            dValue = timeMills - lastDispatchTime;
            if (Math.abs(dValue) > period * 1000) {
                // 如果服务器时间改了
                lastDispatchTime = lastDispatchTime + dValue;
                // 重置所有任务下次执行时间
                resetAllTaskNextExecuteTime(taskList, lastDispatchTime);
            }
        }
        logger.info("lastDispatchTime {},timeMills:{},dValue:{}", lastDispatchTime, timeMills, dValue);
        nextDispatchTime = lastDispatchTime + period * 1000;
        logger.info("nextDispatchTime {}", nextDispatchTime);
        List<UnifyTask> taskListUpdate = new ArrayList<>();
        List<UnifyTaskQueueElement> taskList2Queue = new ArrayList<>();
        Map<String, UnifyTask> taskMap = taskList.stream().collect(Collectors.toMap(UnifyTask::getId, Function.identity(), (oldDate, newData) -> newData));
        for (UnifyTask task : taskList) {
            // 1.任务过滤
            boolean pass = validateCronExpression(task, lastDispatchTime);
            if (!pass) {
                logger.info("filter task:{}", task.getTaskNo());
                continue;
            }
            Date[] nextExecuteTime = {null};
            boolean[] update = {false};
            // 1.1 刷新任务执行时间,解析任务的cron表达式
            if (task.getNextExecuteTime() == null || task.getInterval() == null || (task.getResetNextExecuteTime() != null && task.getResetNextExecuteTime())) {
                boolean success = resetTask(lastDispatchTime, task, update, nextExecuteTime);
                if (!success) {
                    logger.info("resetTask fail");
                    continue;
                }
            } else {
                nextExecuteTime[0] = task.getNextExecuteTime();
            }
            if (nextExecuteTime[0] == null) {
                logger.info("nextExecuteTime is null :{}", task.getTaskNo());
                continue;
            }
            // 2.下次执行时间在 范围内,放入延时队列
            // 3.更新下下次执行执行时间和时间执行间隔,更新新增加到队列的任务的任务状态
            if (nextExecuteTime[0].getTime() > lastDispatchTime && nextExecuteTime[0].getTime() <= nextDispatchTime) {
                // 时间刚好到,但是有前置任务
                // 处理前置任务
                logger.info("it is time to exe:{}", task.getTaskNo());
                update[0] = dealPreTask(task, taskMap, taskList2Queue, nextExecuteTime[0]);
            }
            // 任务延迟了
            // 可能任务处于调度状态中,主从切换导致任务丢失
            // 可能任务由于前置任务导致了延迟
            else if (nextExecuteTime[0].getTime() < lastDispatchTime) {
                // 4.判断当前任务是否处于等待被调度状态中,并检测延时队列是否存在该任务(不存在,可能是主从节点有切换),不存在则再次放入延时队列中
                if (task.getStatus() != null && task.getStatus() == UnifyTaskStatusType.watting) {
                    if (!UnifyTaskQueueManager.taskSet.contains(task.getServer() + task.getTaskNo())) {
                        taskList2Queue.add(new UnifyTaskQueueElement(task.getServer(), task.getTaskNo(), nextExecuteTime[0].getTime()));
                    }
                } else {
                    // 4.1由于前置任务延迟,导致了延迟
                    logger.info(" maybe task delay due to pre task:{},dealPreTask", task.getTaskNo());
                    update[0] = dealPreTask(task, taskMap, taskList2Queue, nextExecuteTime[0]);
                }
            }
            if (update[0]) {
                task.setModified(new Date());
                taskListUpdate.add(task);
            }
        }
        if (!CollectionUtils.isEmpty(taskListUpdate)) {
            logger.info("update task :{}", taskListUpdate.size());
            taskBiz.updateList(taskListUpdate);
        }
        // 5.放入队列中
        if (!CollectionUtils.isEmpty(taskList2Queue)) {
            for (UnifyTaskQueueElement element : taskList2Queue) {
                if (UnifyTaskQueueManager.taskSet.contains(element.getServer() + element.getTaskNo())) {
                    logger.info("had in queue {}", element.getTaskNo());
                    continue;
                }
                UnifyTaskQueueManager.put(element, TimeUnit.MILLISECONDS);
                logger.info("put into queue :{}", element.getTaskNo());
            }
        }
        redisTemplate.opsForValue().set(LASTDISPATCHTIME, nextDispatchTime);
        logger.info("dispatchTask end");
    }

    /**
     * 系统时间变更之后,重置所有任务执行时间
     *
     * @param taskList
     * @param lastDispatchTime 校正之后的上次执行时间
     */
    private void resetAllTaskNextExecuteTime(List<UnifyTask> taskList, Long lastDispatchTime) {
        List<UnifyTask> taskListUpdate = new ArrayList<>();
        Date[] nextExecuteTime = {null};
        for (UnifyTask task : taskList) {
            boolean[] update = {false};
            resetTask(lastDispatchTime, task, update, nextExecuteTime);
            if (update[0]) {
                taskListUpdate.add(task);
            }
        }
        if (!CollectionUtils.isEmpty(taskListUpdate)) {
            logger.info("resetAllTaskNextExcuteTime -- update task :{}", taskListUpdate.size());
            taskBiz.updateList(taskListUpdate);
        }
    }

    private boolean resetTask(Long lastDispatchTime, UnifyTask task, boolean[] update, Date[] nextExecuteTime) {
        CronTriggerImpl cronTrigger = new CronTriggerImpl();
        try {
            cronTrigger.setCronExpression(task.getTaskCron());
            Date date = null;
            Date nextTime = null;
            date = cronTrigger.getFireTimeAfter(new Date(lastDispatchTime));
            if (date != null) {
                nextExecuteTime[0] = date;
                nextTime = cronTrigger.getFireTimeAfter(nextExecuteTime[0]);
                if (nextTime != null) {
                    task.setInterval(nextTime.getTime() - date.getTime());
                } else {
                    return false;
                }
                task.setNextExecuteTime(date);
            } else {
                return false;
            }
            task.setResetNextExecuteTime(false);
            update[0] = true;
            logger.info("taskNO:{},lastDispatchTime:{},nextExecuteTime:{},nextTime:{},Interval:{}", task.getTaskNo(), lastDispatchTime, date, nextTime, task.getInterval());
        } catch (Exception e) {
            logger.error("", e);
        }
        return true;
    }


    private boolean resetTask(Long lastDispatchTime, UnifyTask task) {
        CronTriggerImpl cronTrigger = new CronTriggerImpl();
        try {
            cronTrigger.setCronExpression(task.getTaskCron());
            Date date = null;
            Date nextTime = null;
            date = cronTrigger.getFireTimeAfter(new Date(lastDispatchTime));
            if (date != null) {
                nextTime = cronTrigger.getFireTimeAfter(date);
                if (nextTime != null) {
                    task.setInterval(nextTime.getTime() - date.getTime());
                } else {
                    return false;
                }
                task.setNextExecuteTime(date);
            } else {
                return false;
            }
            task.setResetNextExecuteTime(false);
            logger.info("taskNO:{},lastDispatchTime:{},nextExecuteTime:{},nextTime:{},Interval:{}", task.getTaskNo(), lastDispatchTime, date, nextTime, task.getInterval());
        } catch (Exception e) {
            logger.error("", e);
        }
        return true;
    }


    private boolean validateCronExpression(UnifyTask task, long lastDispatchTime) {
        // 跳过执行中的任务,但是大于一个周期还未执行完的任务不算在内
        if (task.getStatus() != null && task.getStatus() == UnifyTaskStatusType.executing) {
            logger.info("task executing:{}", task.getTaskNo());
            if (task.getNextExecuteTime().getTime() < lastDispatchTime) {
                logger.info("task has not report status util now:{}, it will be restart ", task.getTaskNo());
                return true;
            }
            return false;
        }
        if (StringUtils.isBlank(task.getTaskCron())) {
            logger.info("taskCron not set :{}", task.getTaskNo());
            return false;
        }
        if (!CronExpression.isValidExpression(task.getTaskCron())) {
            logger.info("taskCron is not valid :{}", task.getTaskNo());
            return false;
        }
        return true;
    }

    private boolean dealPreTask(UnifyTask task, Map<String, UnifyTask> taskMap, List<UnifyTaskQueueElement> taskList2Queue, Date nextExecuteTime) {
        String[] preTasks = StringUtils.isBlank(task.getPreTaskId()) ? null : task.getPreTaskId().split(",");
        boolean putQueue = false;
        if (preTasks != null && preTasks.length > 0) {
            logger.info("have pre task :{}", task.getTaskNo());
            int count = 0;
            for (String taskId : preTasks) {
                UnifyTask task1 = taskMap.get(taskId);
                // 1.前置任务还没执行完,或者失败,就不执行后置任务
                if (task1.getStatus() == UnifyTaskStatusType.success && (task1.getNextExecuteTime().getTime() - task1.getLastExecuteTime().getTime()) < task1.getInterval()) {
                    count++;
                } else {
                    logger.info("pre task has not finish :current :{},pre :{},status:{}", task.getTaskNo(), task1.getTaskNo(), task1.getStatus());
                    break;
                }
            }
            if (count == preTasks.length) {
                logger.info("all pre task had finished:{}", task.getTaskNo());
                putQueue = true;
            }
        } else {
            logger.info("no pre task :{}", task.getTaskNo());
            putQueue = true;
        }
        if (putQueue) {
            // 2.放入队列
            taskList2Queue.add(new UnifyTaskQueueElement(task.getServer(), task.getTaskNo(), nextExecuteTime.getTime()));
            task.setStatus(UnifyTaskStatusType.watting);
            task.setNextExecuteTime(new Date(task.getNextExecuteTime().getTime() + task.getInterval()));
        }
        return putQueue;
    }

    @Override
    public void dispatchTask(UnifyTaskQueueElement element) {
        TaskAjaxResult<Boolean> taskAjaxResult = taskDispatchService.dispatchTask(element.getServer(), element.getTaskNo());
        if (!(taskAjaxResult != null && taskAjaxResult.getData())) {
            updateTaskStatusAndTryTimes(element.getServer(), element.getTaskNo(), UnifyTaskStatusType.watting, 0);
            logger.info("dispatchTask fail and set status to waiting:{},{}", element.getServer(), element.getTaskNo());
            // 2.调用失败,重新提交到,延时队列,延迟10秒,防止死循环调用
            element.setExecuteTime(System.currentTimeMillis() + 10 * 1000);
            if (!UnifyTaskQueueManager.taskSet.contains(element.getServer() + element.getTaskNo())) {
                UnifyTaskQueueManager.put(element, TimeUnit.MILLISECONDS);
                logger.info("out from delay queue, and exe fail:{},{},put into the queue", element.getServer(), element.getTaskNo());
            } else {
                logger.info("out from delay queue, and exe fail:{},{},had in the queue", element.getServer(), element.getTaskNo());
            }
        }
    }

    @Override
    public void updateTaskStatusAndTryTimes(String server, String taskNo, UnifyTaskStatusType executing, int retryTimes) {
        taskBiz.updateTaskStatusAndTryTimes(server, taskNo, executing, retryTimes);
    }

    @Override
    public TaskAjaxResult<Boolean> tasksRegister(List<UnifyTaskRegister> taskRegisters) {
        Integer res = null;
        if (!CollectionUtils.isEmpty(taskRegisters)) {
            List<UnifyTask> taskList = new ArrayList<>();
            taskRegisters.stream().forEach(taskRegister -> {
                UnifyTask task = checkTask(taskRegister);
                if (task == null) {
                    return;
                }
                taskList.add(task);
            });
            if (!CollectionUtils.isEmpty(taskList)) {
                taskBiz.addList(taskList);
                res = 1;
            }
        }
        TaskAjaxResult taskAjaxResult = new TaskAjaxResult(res == null ? false : (res == 1 ? true : false));
        taskAjaxResult.setCode("0");
        return taskAjaxResult;
    }

    /**
     * 主动发现添加引用task 的代码中的任务
     * 如果数据库中存在,则不作更新
     *
     * @param taskRegister
     * @return
     */
    @Override
    public TaskAjaxResult<Boolean> taskRegister(UnifyTaskRegister taskRegister) {
        UnifyTask task = checkTask(taskRegister);
        Integer res = null;
        if (task != null) {
            res = taskBiz.add(task);
        }
        return new TaskAjaxResult<>(res == null ? false : (res == 1 ? true : false));
    }

    @Override
    public UnifyTask checkTask(UnifyTaskRegister taskRegister) {
        if (taskRegister == null || StringUtils.isBlank(taskRegister.getTaskNo()) || StringUtils.isBlank(taskRegister.getServer())) {
            return null;
        }
        UnifyTask task = taskBiz.findOne(taskRegister.getTaskNo(), taskRegister.getServer());
        if (task == null) {
            task = new UnifyTask();
            task.setCreated(new Date());
            task.setStatus(UnifyTaskStatusType.success);
            task.setForceExecute(false);
            task.setDataValid(true);
            task.setId(StringUtils.uuid32());
            task.setName(taskRegister.getTaskName());
            task.setServer(taskRegister.getServer());
            task.setTaskCron(taskRegister.getCron());
            task.setTaskNo(taskRegister.getTaskNo());
            task.setResetNextExecuteTime(false);
        } else {
            task = null;
        }
        return task;
    }


    /**
     * 启动定时任务定时调度线程
     */
    @Override
    public void scheduleStart() {
        logger.info("check task");
        if (scheduledThreadPool == null) {
            scheduledThreadPool = Executors.newScheduledThreadPool(1);
            //每次执行结束，已固定时延开启下次执行
            scheduledThreadPool.scheduleWithFixedDelay(() -> {
                try {
                    dispatchTask();
                } catch (Exception e) {
                    logger.error("", e);
                }
            }, 10, period, TimeUnit.SECONDS);
            logger.info("start unify deal logic listener thread");
        }
        if (taskDispatchThread == null) {
            taskDispatchThread = new UnifyTaskDispatchThread();
            taskDispatchThread.start();
            logger.info("start unify listener thread");
        }
    }

    /**
     * 关闭定时任务定时调度线程
     */
    @Override
    public void scheduleDestroy() {
        logger.info("close scheduleDestroy");
        if (scheduledThreadPool != null) {
            scheduledThreadPool.shutdown();
            logger.info("close scheduledThreadPool");
        }
        if (taskDispatchThread != null) {
            taskDispatchThread.shutdown();
            logger.info("close taskDispatchThread");
        }
    }

    /**
     * 更新任务执行时间
     *
     * @param tasks
     * @return
     */
    @Override
    public TaskAjaxResult<Boolean> tasksUpdate(List<UnifyTaskUpdate> tasks) {
        Integer res = null;
        if (!CollectionUtils.isEmpty(tasks)) {
            List<UnifyTask> taskList = new ArrayList<>();
            boolean[] update = {false};
            Date[] nextExecuteTime = {null};
            tasks.forEach(unifyTaskUpdate -> {
                UnifyTask task = taskBiz.findOne(unifyTaskUpdate.getTaskNo(), unifyTaskUpdate.getServer());
                if (task == null) {
                    return;
                }
                task.setName(unifyTaskUpdate.getTaskName());
                task.setTaskCron(unifyTaskUpdate.getCron());
                resetTask((task.getLastExecuteTime() == null ? System.currentTimeMillis() : task.getLastExecuteTime().getTime()), task, update, nextExecuteTime);
                taskList.add(task);
            });
            if (!CollectionUtils.isEmpty(taskList)) {
                taskBiz.updateList(taskList);
                res = 1;
            }
        }
        TaskAjaxResult taskAjaxResult = new TaskAjaxResult(res == null ? false : (res == 1 ? true : false));
        taskAjaxResult.setCode("0");
        return taskAjaxResult;
    }

    @Override
    public TaskAjaxResult<BasicTask> taskByNo(String taskNo, String server) {
        TaskAjaxResult<BasicTask> taskAjaxResult = new TaskAjaxResult<>();
        taskAjaxResult.setCode("0");
        UnifyTask task = taskBiz.findOne(taskNo, server);
        if (task == null) {
            return taskAjaxResult;
        }
        BasicTask basicTask = new BasicTask();
        basicTask.setCron(task.getTaskCron());
        basicTask.setServer(task.getServer());
        basicTask.setTaskName(task.getName());
        basicTask.setTaskNo(task.getTaskNo());
        taskAjaxResult.setData(basicTask);
        return taskAjaxResult;
    }

    /**
     * 前端页面，分页查询
     *
     * @param taskQueryVO 查询参数
     * @return
     */
    @Override
    public Pagination<TaskVO> pageList(TaskQueryVO taskQueryVO) {
        Pagination<TaskVO> pagination = new Pagination<>();
        Page<UnifyTask> page = taskBiz.pageList((taskQueryVO.getPageNo() - 1) * taskQueryVO.getPageSize(), taskQueryVO.getPageSize());
        pagination.setPageNo(taskQueryVO.getPageNo());
        pagination.setPageSize(taskQueryVO.getPageSize());
        pagination.setRows(transVO(page.getList()));
        pagination.setTotal(page.getTotalCount());
        pagination.setTotalPage(page.getTotalPage());
        return pagination;
    }


    /**
     * 前端页面，修改整个任务
     *
     * @param taskVO
     * @return
     */
    @Override
    public String update(TaskVO taskVO) {
        UnifyTask task = taskBiz.findOneById(taskVO.getId());
        if (task == null) {
            logger.info("task not exists:{}", taskVO.getId());
            // TODO 统一异常处理
            throw new RuntimeException("task not exists");
        }
        task.setName(taskVO.getName());
        if (StringUtils.isNotBlank(taskVO.getTaskCron())) {
            boolean success = resetTask((task.getLastExecuteTime() == null ? System.currentTimeMillis() : task.getLastExecuteTime().getTime()), task);
            if (!success) {
                logger.info("resetTask fail :{}", taskVO.getId());
                throw new RuntimeException("resetTask cron express fail");
            }
            task.setTaskCron(taskVO.getTaskCron());
        }
        // 重试次数上限
        if (taskVO.getRetryTimesLimit() != null) {
            task.setRetryTimesLimit(taskVO.getRetryTimesLimit());
        }
        taskBiz.update(task);
        return "success";
    }

    /**
     * 执行任务
     *
     * @param id
     * @return
     */
    @Override
    public String running(String id) {
        UnifyTask task = taskBiz.findOneById(id);
        if (task == null) {
            logger.info("task not exists:{}", id);
            // TODO 统一异常处理
            throw new RuntimeException("task not exists");
        }
        UnifyTaskQueueElement element = new UnifyTaskQueueElement(task.getServer(), task.getTaskNo(), System.currentTimeMillis());
        task.setStatus(UnifyTaskStatusType.watting);
        if (task.getInterval() != null) {
            task.setNextExecuteTime(new Date(System.currentTimeMillis() + task.getInterval()));
        }
        if (UnifyTaskQueueManager.taskSet.contains(element.getServer() + element.getTaskNo())) {
            logger.info(" had in queue {},{}", element.getServer(), element.getTaskNo());
            return "success";
        }
        // 1.修改任务状态
        updateTaskStatusAndTryTimes(element.getServer(), element.getTaskNo(), UnifyTaskStatusType.executing, 0);
        // 调用客户端接口
        dispatchTask(element);
        logger.info(" running task :{},{}", element.getServer(), element.getTaskNo());
        return "success";
    }

    private List<TaskVO> transVO(List<UnifyTask> list) {
        List<TaskVO> taskVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(task -> {
                taskVOS.add(new TaskVO(task));
            });
        }
        return taskVOS;
    }


    //    public static void main(String[] a) {
    //        System.out.println(CronExpression.isValidExpression("0 15 10 * * *"));
    //    }
}
