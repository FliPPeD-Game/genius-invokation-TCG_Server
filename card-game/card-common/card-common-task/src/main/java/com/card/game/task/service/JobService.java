package com.card.game.task.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.card.game.task.domain.entity.JobAndTriggerEntity;
import com.card.game.task.domain.model.JobModel;
import org.quartz.SchedulerException;

/**
 * jobService
 *
 * @author cunzhiwang
 * @Date 2023/2/7 22:48
 */
public interface JobService {
    /**
     * 添加并启动定时任务
     *
     * @param jobModel 表单参数 {@link JobModel}
     * @throws Exception 异常
     */
    void addJob(JobModel jobModel) throws Exception;

    /**
     * 删除定时任务
     *
     * @param jobModel 表单参数 {@link JobModel}
     * @throws SchedulerException 异常
     */
    void deleteJob(JobModel jobModel) throws SchedulerException;

    /**
     * 暂停定时任务
     *
     * @param jobModel 表单参数 {@link JobModel}
     * @throws SchedulerException 异常
     */
    void pauseJob(JobModel jobModel) throws SchedulerException;

    /**
     * 恢复定时任务
     *
     * @param jobModel 表单参数 {@link JobModel}
     * @throws SchedulerException 异常
     */
    void resumeJob(JobModel jobModel) throws SchedulerException;

    /**
     * 重新配置定时任务
     *
     * @param jobModel 表单参数 {@link JobModel}
     * @throws Exception 异常
     */
    void cronJob(JobModel jobModel) throws Exception;

    /**
     * 查询定时任务列表
     *
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @return 定时任务列表
     */
    IPage<JobAndTriggerEntity> list(Integer currentPage, Integer pageSize);
}
