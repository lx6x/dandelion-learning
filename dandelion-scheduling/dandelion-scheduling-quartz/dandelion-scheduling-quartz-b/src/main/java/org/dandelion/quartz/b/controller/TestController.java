package org.dandelion.quartz.b.controller;

import org.dandelion.quartz.b.entity.QuartzJob;
import org.dandelion.quartz.b.service.QuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2021/9/14 13:05
 */
@RestController
public class TestController {

    @Autowired
    private QuartzService quartzService;


    @GetMapping("test")
    public String test() {
        System.out.println("---- 请求来了");
        return "ok -- ";
    }

    @PostMapping("saveJob")
    public void saveJob(@RequestBody QuartzJob quartzJob) {
        System.out.println("--- 新增");
        quartzService.saveJob(quartzJob);
    }

    @GetMapping("triggerJob")
    public void triggerJob(@RequestParam String jobName, @RequestParam String jobGroup) {
        quartzService.triggerJob(jobName, jobGroup);
    }

    @GetMapping("pauseJob")
    public void pauseJob(@RequestParam String jobName, @RequestParam String jobGroup) {
        quartzService.pauseJob(jobName, jobGroup);
    }

    @GetMapping("resumeJob")
    public void resumeJob(@RequestParam String jobName, @RequestParam String jobGroup) {
        quartzService.resumeJob(jobName, jobGroup);
    }

    @GetMapping("removeJob")
    public void removeJob(@RequestParam String jobName, @RequestParam String jobGroup) {
        quartzService.removeJob(jobName, jobGroup);
    }


}
