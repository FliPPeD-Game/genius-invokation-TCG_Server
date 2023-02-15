package com.card.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.card.game.task.domain.entity.JobAndTriggerEntity;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 任务mapper
 *
 * @author cunzhiwang
 * @Date 2023/2/15 11:23
 */

public interface JobMapper extends BaseMapper<JobAndTriggerEntity> {

    /**
     * 查询定时作业和触发器列表
     *
     * @return 定时作业和触发器列表
     */
    IPage<JobAndTriggerEntity> list(IPage<JobAndTriggerEntity> page);
}
