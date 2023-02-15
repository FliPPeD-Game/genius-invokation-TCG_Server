package com.card.game.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.card.game.mapper.SysAttributeSyncConfigMapper;
import com.card.game.pojo.entity.SysAttributeSyncEntity;
import com.card.game.task.job.base.BaseJob;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 属性同步异步任务
 */
@Slf4j
public class AttributeSyncJob implements BaseJob {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("-------->sysAttributeSyncEntities");

    }
}
