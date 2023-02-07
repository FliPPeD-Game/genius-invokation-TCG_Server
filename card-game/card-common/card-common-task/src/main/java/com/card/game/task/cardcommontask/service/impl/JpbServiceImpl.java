package com.card.game.task.cardcommontask.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.card.game.task.cardcommontask.domin.entity.JobAndTriggerEntity;
import com.card.game.task.cardcommontask.domin.model.JobModel;
import com.card.game.task.cardcommontask.service.JobService;
import org.quartz.SchedulerException;

/**
 * job service
 *
 * @author cunzhiwang
 * @Date 2023/2/7 23:00
 */
public class JpbServiceImpl implements JobService {

    @Override
    public void addJob(JobModel jobModel) throws Exception {

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
