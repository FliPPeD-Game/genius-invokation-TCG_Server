package com.card.game.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.card.game.task.common.ApiResponse;
import com.card.game.task.domain.entity.JobAndTriggerEntity;
import com.card.game.task.domain.model.JobModel;
import com.card.game.task.service.JobService;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 异步任务控制
 *
 * @author cunzhiwang
 * @Date 2023/2/13 20:06
 */
@RestController
@RequestMapping("/job")
@Slf4j
@RequiredArgsConstructor
public class JobController {

private final JobService jobService;

    /**
     * 保存定时任务
     */
    @PostMapping("/addJob")
    public ResponseEntity<ApiResponse> addJob( @Valid@RequestBody JobModel jobModel) {
        try {
            jobService.addJob(jobModel);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.msg(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(ApiResponse.msg("操作成功"), HttpStatus.CREATED);
    }

    /**
     * 删除定时任务
     */
    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteJob(JobModel jobModel) throws SchedulerException {
        if (StrUtil.hasBlank(jobModel.getJobGroupName(), jobModel.getJobClassName())) {
            return new ResponseEntity<>(ApiResponse.msg("参数不能为空"), HttpStatus.BAD_REQUEST);
        }

        jobService.deleteJob(jobModel);
        return new ResponseEntity<>(ApiResponse.msg("删除成功"), HttpStatus.OK);
    }

    /**
     * 暂停定时任务
     */
    @PutMapping(params = "pause")
    public ResponseEntity<ApiResponse> pauseJob(JobModel jobModel) throws SchedulerException {
        if (StrUtil.hasBlank(jobModel.getJobGroupName(), jobModel.getJobClassName())) {
            return new ResponseEntity<>(ApiResponse.msg("参数不能为空"), HttpStatus.BAD_REQUEST);
        }

        jobService.pauseJob(jobModel);
        return new ResponseEntity<>(ApiResponse.msg("暂停成功"), HttpStatus.OK);
    }

    /**
     * 恢复定时任务
     */
    @PutMapping(params = "resume")
    public ResponseEntity<ApiResponse> resumeJob(JobModel jobModel) throws SchedulerException {
        if (StrUtil.hasBlank(jobModel.getJobGroupName(), jobModel.getJobClassName())) {
            return new ResponseEntity<>(ApiResponse.msg("参数不能为空"), HttpStatus.BAD_REQUEST);
        }

        jobService.resumeJob(jobModel);
        return new ResponseEntity<>(ApiResponse.msg("恢复成功"), HttpStatus.OK);
    }

    /**
     * 修改定时任务，定时时间
     */
    @PutMapping(params = "cron")
    public ResponseEntity<ApiResponse> cronJob(@Valid JobModel jobModel) {
        try {
            jobService.cronJob(jobModel);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.msg(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(ApiResponse.msg("修改成功"), HttpStatus.OK);
    }

    @GetMapping("list")
    public ResponseEntity<ApiResponse> jobList(Integer currentPage, Integer pageSize) {
        if (ObjectUtil.isNull(currentPage)) {
            currentPage = 1;
        }
        if (ObjectUtil.isNull(pageSize)) {
            pageSize = 10;
        }
        IPage<JobAndTriggerEntity> jobData = jobService.list(currentPage, pageSize);
        return ResponseEntity.ok(ApiResponse.ok(Dict.create().set("total",jobData)));
    }

}
