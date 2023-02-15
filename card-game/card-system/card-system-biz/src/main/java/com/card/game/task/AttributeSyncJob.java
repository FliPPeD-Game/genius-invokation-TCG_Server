package com.card.game.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.card.game.mapper.SysAttributeSyncConfigMapper;
import com.card.game.pojo.entity.SysAttributeSyncEntity;
import com.card.game.pojo.entity.SysImageInfoEntity;
import com.card.game.service.SysAttributeSyncService;
import com.card.game.task.job.base.BaseJob;

import java.util.List;

import com.card.game.task.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 属性同步异步任务
 */
@Slf4j
public class AttributeSyncJob implements BaseJob {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Scheduler scheduler = (Scheduler) context.getScheduler();
//获取JobExecutionContext中的service对象
        try {
            //获取JobExecutionContext中的service对象
            SchedulerContext schCtx = context.getScheduler().getContext();
            //获取Spring中的上下文
            ApplicationContext appCtx = (ApplicationContext) schCtx.get("applicationContext");
            SysAttributeSyncService configService = appCtx.getBean(SysAttributeSyncService.class);
            List<SysAttributeSyncEntity> attributeSyncEntities = configService.list();
            attributeSyncEntities.forEach(attribute -> {
                Class<?> mapper1 = null;
                Class<?> mapper2=null;
                try {
                    mapper1 = Class.forName(attribute.getMapper1());
                    mapper2 = Class.forName(attribute.getT2Attribute());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    log.error(String.format("对应mapper1【%s】路径或mapper2【%s】配置错误", attribute.getMapper1(),
                            attribute.getMapper2()));
                }
                IService service = (IService)appCtx.getBean("");
            });

        } catch (SchedulerException e1) {
            // TODO 尚未处理异常
            e1.printStackTrace();
        }
        System.out.println("-------->sysAttributeSyncEntities");

    }
}
