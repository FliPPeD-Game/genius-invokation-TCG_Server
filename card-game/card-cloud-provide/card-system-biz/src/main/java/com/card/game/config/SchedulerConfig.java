package com.card.game.config;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
@Configuration
@RequiredArgsConstructor
public class SchedulerConfig {
    private final DataSource dataSource;
    private final ApplicationContext applicationContext;
    @Bean(name="SchedulerFactory")
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException{
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setAutoStartup(true);
        factory.setStartupDelay(10);//延时5秒启动
        factory.setDataSource(dataSource);
        factory.setApplicationContextSchedulerContextKey("applicationContext");
        factory.setApplicationContext(applicationContext);
        return factory;
    }

    /*
     * quartz初始化监听器
     */
    @Bean
    public QuartzInitializerListener executorListener() {
        return new QuartzInitializerListener();
    }



}
