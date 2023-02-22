package com.card.game.task;

import cn.hutool.log.LogFactory;
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
import java.util.logging.Logger;

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
        log.info("-------->AttributeSyncJob begin");

        // TODO 后续优化，避免sql注入
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
                    StringBuilder sql1 = new StringBuilder("select ");
                    StringBuilder commonSql = new StringBuilder("");
                    // 共用条件sql
                    commonSql.append("where");
                    commonSql.append(" ");
                    // 1、表一需要同步数据
                    sql1.append(attribute.getRelationshipField1());
                    sql1.append(" ");
                    sql1.append("from");
                    sql1.append(" ");
                    sql1.append(attribute.getTable1());
                    sql1.append("  ");
                    sql1.append(commonSql);
                    sql1.append(attribute.getT2Attribute());
                    sql1.append(" ");
                    sql1.append("is null");
                    log.info("SQL1 is {}",sql1);
                    PreparedStatement preparedStatement = connection.
                            prepareStatement(sql1.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
                    List<String> resultField = new ArrayList<>();
                    ResultSet result = preparedStatement.executeQuery();
                    while (result.next()) {
                        resultField.add(result.getString(1));
                    }
                    log.info("sql result is: {}",resultField);
                    resultField.forEach(field -> {
                        // 表二目标数据
                        StringBuilder sql2 = new StringBuilder("select ");
                        sql2.append(attribute.getT2Attribute());
                        sql2.append(" ");
                        sql2.append("from");
                        sql2.append(" ");
                        sql2.append(attribute.getTable2());
                        sql2.append("  ");
                        sql2.append("where");
                        sql2.append(" ");
                        sql2.append(attribute.getRelationshipField2());
                        sql2.append("=");
                        sql2.append("'");
                        sql2.append(field);
                        sql2.append("'");
                        log.info("sql2 is: {}",sql2);
                        try {
                            ResultSet resultSet = preparedStatement.executeQuery(sql2.toString());
                            List<String> fieldList = new ArrayList<>();
                            while (resultSet.next()) {
                                fieldList.add(resultSet.getString(1));
                            }
                            log.info("sql2 result is:{}",fieldList);
                            fieldList.forEach(e -> {
                                // 执行更新
                                StringBuilder sql3 = new StringBuilder("UPDATE ");
                                sql3.append(attribute.getTable1());
                                sql3.append(" ");
                                sql3.append("SET");
                                sql3.append(" ");
                                sql3.append(attribute.getT1Attribute());
                                sql3.append(" = ");
                                sql3.append("'");
                                sql3.append(e);
                                sql3.append("'");
                                sql3.append(" ");
                                sql3.append(commonSql);
                                sql3.append(attribute.getRelationshipField1());
                                sql3.append("=");
                                sql3.append("'");
                                sql3.append(field);
                                sql3.append("'");
                                log.info("sql3 is:{}",sql3);
                                try {
                                    boolean execute = preparedStatement.execute(sql3.toString());
                                    log.info("是否更新成功：{}",execute);
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }
                            });
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
                    if(resultField==null|| resultField.size()<=0){
                        log.info("数据同步完，暂停定时任务");
                            JobKey key = context.getJobDetail().getKey();
                            scheduler.pauseJob(key);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    log.error("获取数据库链接失败");
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
            });

        } catch (SchedulerException e1) {
            // TODO 尚未处理异常
            e1.printStackTrace();
        }
        log.info("-------->AttributeSyncJob end");

    }
}
