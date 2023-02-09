package com.card.game.task.cardcommontask.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.card.game.task.cardcommontask.Until.JobUtil;
import com.card.game.task.cardcommontask.domin.entity.JobAndTriggerEntity;
import com.card.game.task.cardcommontask.domin.model.JobModel;
import com.card.game.task.cardcommontask.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;

/**
 * job service
 *
 * @author cunzhiwang
 * @Date 2023/2/7 23:00
 */
@RequiredArgsConstructor
@Slf4j
public class JpbServiceImpl implements JobService {

    private final Scheduler scheduler;

    @Override
    public void addJob(JobModel jobModel) throws Exception {
        // 启动调度器
        scheduler.start();

        // 构建Job信息
        JobDetail jobDetail = JobBuilder.newJob(JobUtil.getClass(jobModel.getJobClassName()).getClass()).withIdentity(jobModel.getJobClassName(), jobModel.getJobGroupName()).build();

        // Cron表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder cron = CronScheduleBuilder.cronSchedule(jobModel.getCronExpression());

        //根据Cron表达式构建一个Trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobModel.getJobClassName(), jobModel.getJobGroupName()).withSchedule(cron).build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("【定时任务】创建失败！", e);
            throw new Exception("【定时任务】创建失败！");
        }
    }

    @Override
    public void deleteJob(JobModel jobModel) throws SchedulerException {

    }

    @Override
    public void pauseJob(JobModel jobModel) throws SchedulerException {

    }

    @Override
    public void resumeJob(JobModel jobModel) throws SchedulerException {

    }

    @Override
    public void cronJob(JobModel jobModel) throws Exception {

    }

    @Override
    public IPage<JobAndTriggerEntity> list(Integer currentPage, Integer pageSize) {
        return null;
    }
}
