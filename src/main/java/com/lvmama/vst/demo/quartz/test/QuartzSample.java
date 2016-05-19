package com.lvmama.vst.demo.quartz.test;

import java.util.Date;

import org.quartz.DateIntervalTrigger;
import org.quartz.DateIntervalTrigger.IntervalUnit;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzSample {

	public void run() throws Exception{
		// 1
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		//2
		JobDetail job = new JobDetail("sampleJob", "sampleGroup", SampleJob.class);
		//3
		final DateIntervalTrigger trigger = new DateIntervalTrigger();
		trigger.setName("sampleTrigger");
		trigger.setStartTime(new Date());
		trigger.setRepeatIntervalUnit(IntervalUnit.MINUTE);
		trigger.setRepeatInterval(1);
		//4
		sched.scheduleJob(job,trigger);
		//5
		sched.start();
		//6
		Thread.sleep(4*1000);
//		sched.deleteJob("sampleJob", "sampleGroup");
		//8
//		sched.shutdown(true);
	}
	
	public static void main(String[] args) {
		QuartzSample samp = new QuartzSample();
		try {
			samp.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
