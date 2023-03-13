package com.card.game.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.card.game.aop.AopResult;
import com.card.game.mapper.SysJobMapper;
import com.card.game.task.Until.JobUtil;
import com.card.game.task.domain.entity.JobAndTriggerEntity;
import com.card.game.task.domain.model.JobModel;
import com.card.game.task.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Service;

/**
 * job service
 *
 * @author cunzhiwang
 * @Date 2023/2/7 23:00
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class JpbServiceImpl implements JobService {


    private final Scheduler scheduler;

    private final SysJobMapper jobMapper;

    @Override
    public void addJob(JobModel jobModel) throws Exception {
        // 启动调度器
        scheduler.start();

        // 构建Job信息
        JobDetail jobDetail = JobBuilder.newJob(JobUtil.getClass(jobModel.getJobClassName()).getClass())
                .withIdentity(jobModel.getJobClassName(), jobModel.getJobGroupName()).build();

        // Cron表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder cron = CronScheduleBuilder.cronSchedule(jobModel.getCronExpression());

        //根据Cron表达式构建一个Trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().
                withIdentity(jobModel.getJobClassName(), jobModel.getJobGroupName())
                .withSchedule(cron).build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("【定时任务】创建失败！", e);
            throw new Exception("【定时任务】创建失败！");
        }
    }

    @Override
    public void deleteJob(JobModel jobModel) throws SchedulerException {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobModel.getJobClassName(), jobModel.getJobGroupName()));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobModel.getJobClassName(), jobModel.getJobGroupName()));
        scheduler.deleteJob(JobKey.jobKey(jobModel.getJobClassName(), jobModel.getJobGroupName()));
    }

    @Override
    public void pauseJob(JobModel jobModel) throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey(jobModel.getJobClassName(), jobModel.getJobGroupName()));
    }

    @Override
    @AopResult
    public void resumeJob(JobModel jobModel) throws SchedulerException {
        scheduler.resumeJob(JobKey.jobKey(jobModel.getJobClassName(), jobModel.getJobGroupName()));
    }

    @Override
    @AopResult
    public void cronJob(JobModel jobModel) throws Exception {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobModel.getJobClassName(), jobModel.getJobGroupName());
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobModel.getCronExpression());

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 根据Cron表达式构建一个Trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            log.error("【定时任务】更新失败！", e);
            throw new Exception("【定时任务】创建失败！");
        }
    }

    @Override
    @AopResult
    public IPage<JobAndTriggerEntity> list(Integer pageNum, Integer pageSize) {
        IPage<JobAndTriggerEntity> page = new Page<>(pageNum, pageSize);
        IPage<JobAndTriggerEntity> jobData = jobMapper.list(page);
        return jobData;
    }
}
