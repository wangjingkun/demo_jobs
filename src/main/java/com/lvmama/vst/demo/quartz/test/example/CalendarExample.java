package com.lvmama.vst.demo.quartz.test.example;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.AnnualCalendar;

public class CalendarExample {
	public static void main(String[] args) {
		try {
			SchedulerFactory sf = new StdSchedulerFactory();

			Scheduler scheduler = sf.getScheduler();

//			①法定节日是以每年为周期的，所以使用AnnualCalendar

			AnnualCalendar holidays = new AnnualCalendar();

//			②五一劳动节

			GregorianCalendar laborDay = new GregorianCalendar();

			laborDay.add(Calendar.MONTH,5);

			laborDay.add(Calendar.DATE,1);

//			 ②-1：排除的日期，如果设置为false则为包含
			holidays.setDayExcluded(laborDay, true);

//			③国庆节

			GregorianCalendar nationalDay = new GregorianCalendar();

			nationalDay.add(Calendar.MONTH,10);

			nationalDay.add(Calendar.DATE,1);

//			③-1：排除该日期
			holidays.setDayExcluded(nationalDay, true);

//			④向Scheduler注册日历
			scheduler.addCalendar("holidays", holidays, false, false);

//			⑤4月1号 上午10点
			Date runDate = TriggerUtils.getDateOf(0,0, 10, 1, 4);

			JobDetail job = new JobDetail("job1", "group1", HelloJob.class);

			SimpleTrigger trigger = new SimpleTrigger("trigger1", "group1", runDate, null, SimpleTrigger.REPEAT_INDEFINITELY, 60L * 60L * 1000L);

//			⑥让Trigger应用指定的日历规则
			trigger.setCalendarName("holidays");

			scheduler.scheduleJob(job, trigger);

			scheduler.start();

			//实际应用中主线程不能停止，否则Scheduler得不到执行，此处从略

		} catch(SchedulerException e) {
			e.printStackTrace();
		}
	}
}
