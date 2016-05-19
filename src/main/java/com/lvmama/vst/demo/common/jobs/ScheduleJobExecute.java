package com.lvmama.vst.demo.common.jobs;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import com.lvmama.vst.demo.common.spring.SpringBeanProxy;
import com.lvmama.vst.demo.po.ScheduleJob;

public class ScheduleJobExecute implements Job{

	private final static Log LOG = LogFactory.getLog(ScheduleJobExecute.class);
	private static final String jobPrefixBeanName = "job_";

	@Resource(name = "schedulerFactoryBean")
	Scheduler scheduler;

	public ScheduleJobExecute() {}

	public ScheduleJobExecute(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void addAllJob(List<ScheduleJob> jobList) {

		if (CollectionUtils.isEmpty(jobList)) {
			return;
		}

		for (ScheduleJob job : jobList) {
			this.addJob(job);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("ScheduleJobExecute addAllJob");
			try {
				Thread.sleep(1000 * 120);
			} catch (InterruptedException e) {
				LOG.error("error, caused by: " + e);
			}
		}
	}

	public boolean addJob(ScheduleJob job) {

		if (job == null) {
			return true;
		}

		try {
			this.initJob(job);
			if ("Y".equalsIgnoreCase(job.getStatus())) {
				String jobName = job.getJobName();
				String jobGroup = job.getJobGroup();
				Class<? extends Job> jobClass = this.getClass();
				String cronExpression = job.getCronExpression();
				// 获取trigger
				CronTrigger trigger = (CronTrigger) scheduler.getTrigger(jobName, jobGroup);
				if (null == trigger) {// 不存在，创建一个
					JobDetail jobDetail = new JobDetail(jobName, jobGroup, jobClass);
					jobDetail.getJobDataMap().put("scheduleJob", job);
					if (!CollectionUtils.isEmpty(job.getJobDataMap())) {
						jobDetail.getJobDataMap().putAll(job.getJobDataMap());
					}

					trigger = new CronTrigger(jobName, jobGroup);
					trigger.setCronExpression(cronExpression);
					scheduler.scheduleJob(jobDetail, trigger);
				} else {// Trigger已存在，那么更新相应的定时设置
					// 按新的cronExpression表达式重新构建trigger
					trigger.setCronExpression(cronExpression);
					trigger.getJobDataMap().put("scheduleJob", job);
					if (job.getJobDataMap() != null) {
						trigger.getJobDataMap().putAll(job.getJobDataMap());
					}
					JobDetail jobDetail = scheduler.getJobDetail(jobName, jobGroup);
					jobDetail.setJobClass(jobClass);
					// 按新的trigger重新设置job执行
					scheduler.rescheduleJob(jobName, jobGroup, trigger);
				}
			} else {
				this.deleteJob(job);
			}
		} catch (SchedulerException e) {
			LOG.error("ScheduleJobExecute Add Job occures SchedulerException, caused by:" + e);
			return false;
		} catch (ParseException e) {
			LOG.error("ScheduleJobExecute Add Job occures ParseException, caused by:" + e);
			return false;
		} catch (Exception e) {
			LOG.error("ScheduleJobExecute Add Job occures Exception, caused by:" + e);
			return false;
		}
		return true;
	}

	public boolean deleteJob(ScheduleJob job) {

		if (scheduler == null || job == null) {
			return false;
		}

		// 删除trigger
		try {
			scheduler.deleteJob(job.getJobName(), job.getJobGroup());
			return true;
		} catch (SchedulerException e) {
			LOG.error("ScheduleJobExecute Delete Job occurs SchedulerException, caused by:" + e);
			return false;
		}
	}

	/**
	 * 获取JOB的线程池
	 * 
	 * @param scheduleJob
	 * @return
	 */
	public AbstractJob getJobBean(ScheduleJob scheduleJob) {

		if (scheduleJob == null) {
			return null;
		}

		String jobBeanName = this.getJobBeanName(scheduleJob);
		boolean jobContainsBean = SpringBeanProxy.containsBean(jobBeanName);
		if (jobContainsBean) {
			return (AbstractJob) SpringBeanProxy.getBean(jobBeanName);
		} else {
			return null;
		}
	}

	private String getJobBeanName(ScheduleJob scheduleJob) {
		return jobPrefixBeanName + scheduleJob.getJobName();
	}

	private void initJob(ScheduleJob scheduleJob) throws Exception {
		Map<String, Object> jobDataMap = scheduleJob.getJobDataMap();
		String threadBeanName = "scheduleJobThreadPoolTaskExecutor" + scheduleJob.getJobId();
		boolean containsBean = SpringBeanProxy.containsBean(threadBeanName);

		// 线程池START
		ThreadPoolTaskExecutor threadPoolTaskExecutor = null;
		Integer corePoolSize = null;
		Integer maxPoolSize = null;
		Integer keepAliveSeconds = null;
		if (jobDataMap.get("corePoolSize") != null) {
			corePoolSize = Integer.parseInt(String.valueOf(jobDataMap.get("corePoolSize")));
		}
		if (jobDataMap.get("maxPoolSize") != null) {
			maxPoolSize = Integer.parseInt(String.valueOf(jobDataMap.get("maxPoolSize")));
		}
		if (jobDataMap.get("keepAliveSeconds") != null) {
			keepAliveSeconds = Integer.parseInt(String.valueOf(jobDataMap.get("keepAliveSeconds")));
		}
		if (corePoolSize == null) {
			corePoolSize = 1;
		}
		if (maxPoolSize == null) {
			maxPoolSize = 2;
		}
		if (keepAliveSeconds == null) {
			keepAliveSeconds = 0;
		}

		jobDataMap.put("corePoolSize", corePoolSize);
		jobDataMap.put("maxPoolSize", maxPoolSize);
		jobDataMap.put("keepAliveSeconds", keepAliveSeconds);
		if (containsBean) {
			threadPoolTaskExecutor = (ThreadPoolTaskExecutor) SpringBeanProxy.getBean(threadBeanName);
		} else {
			threadPoolTaskExecutor = (ThreadPoolTaskExecutor) SpringBeanProxy.addBeanService(threadBeanName,ThreadPoolTaskExecutor.class);
			threadPoolTaskExecutor.initialize();
		}
		// 线程池所使用的缓冲队列
		// threadPoolTaskExecutor.setQueueCapacity(200);
		// 线程池维护线程的最少数量
		threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
		// 线程池维护线程的最大数量
		threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
		// 线程池维护线程所允许的空闲时间
		threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
		// 线程池END

		try {
			String jobServiceName = "job_" + scheduleJob.getJobName();
			boolean jobContainsBean = SpringBeanProxy.containsBean(jobServiceName);
			AbstractJob runnable = null;
			if (jobContainsBean) {
				runnable = (AbstractJob) SpringBeanProxy.getBean(jobServiceName);
			} else {
				//TODO 确保该类是否存在
				runnable = (AbstractJob) SpringBeanProxy.addBeanService(jobServiceName, Class.forName(scheduleJob.getRunClass()));
			}
			if (runnable != null) {
				runnable.valueInit(jobDataMap);
				runnable.setThreadPoolTaskExecutor(threadPoolTaskExecutor);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		ScheduleJob scheduleJob = (ScheduleJob) jobDataMap.get("scheduleJob");
		if (scheduleJob != null && "Y".equalsIgnoreCase(scheduleJob.getStatus())) {
			AbstractJob runnable = this.getJobBean(scheduleJob);
			if (runnable != null) {
				runnable.run();
			}
		}
	}
}
