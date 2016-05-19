package com.lvmama.vst.demo.quartz.test.example;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

public class SimpleTriggerRunner {

	public void run() {
		try {
//			①创建一个JobDetail实例，指定SimpleJob

			JobDetail jobDetail = new JobDetail("job1_1","jGroup1", HelloJob.class);

//			②通过SimpleTrigger定义调度规则：马上启动，每2秒运行一次，共运行100次

			SimpleTrigger simpleTrigger = new SimpleTrigger("trigger1_1","tgroup1");

			simpleTrigger.setStartTime(new Date());

			simpleTrigger.setRepeatInterval(2000);

			simpleTrigger.setRepeatCount(100);

//			③通过SchedulerFactory获取一个调度器实例

			SchedulerFactory schedulerFactory = new StdSchedulerFactory();

			Scheduler scheduler = schedulerFactory.getScheduler();

//			④ 注册并进行调度
			scheduler.scheduleJob(jobDetail, simpleTrigger);

//			⑤调度启动
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new SimpleTriggerRunner().run();
	}
}
