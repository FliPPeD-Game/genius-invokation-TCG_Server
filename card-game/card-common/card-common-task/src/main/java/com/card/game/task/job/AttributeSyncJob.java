package com.card.game.task.job;

import com.card.game.task.job.base.BaseJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * 属性同步异步任务
 */
@Component
@Slf4j
public class AttributeSyncJob implements BaseJob {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.error("------------------------>test");
    }
}
