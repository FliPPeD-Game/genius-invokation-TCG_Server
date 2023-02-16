package com.card.game.task;

import com.baomidou.mybatisplus.extension.service.IService;
import com.card.game.pojo.entity.SysAttributeSyncEntity;
import com.card.game.service.SysAttributeSyncService;
import com.card.game.task.job.base.BaseJob;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;

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
                DataSource dataSource = appCtx.getBean(DataSource.class);
                try {
                    Connection connection = dataSource.getConnection();
                    StringBuilder sql1 =new StringBuilder("select ");
                    sql1.append(attribute.getRelationshipField());
                    sql1.append("from");
                    sql1.append("where");
                    sql1.append(attribute.getT2Attribute());
                    sql1.append("is null");
                    PreparedStatement preparedStatement = connection.prepareStatement(sql1.toString());
                    List<String> resultField=new ArrayList<>();
                    ResultSet result = preparedStatement.executeQuery();
                    if(!result.first()){
                        resultField=null;
                        log.info("对应字段数据都已填充，没有需要填充的字段");
                        return;
                    }
                    while (result.next()){

                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                    log.error("获取数据库链接失败");
                }
            });

        } catch (SchedulerException e1) {
            // TODO 尚未处理异常
            e1.printStackTrace();
        }
        System.out.println("-------->sysAttributeSyncEntities");

    }
}
