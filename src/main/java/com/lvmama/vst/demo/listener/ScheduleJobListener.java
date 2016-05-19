package com.lvmama.vst.demo.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.springframework.web.context.ContextLoaderListener;

import com.lvmama.vst.demo.common.jobs.ScheduleJobExecute;
import com.lvmama.vst.demo.common.spring.SpringBeanProxy;
import com.lvmama.vst.demo.exception.BusinessException;
import com.lvmama.vst.demo.po.ScheduleJob;
import com.lvmama.vst.demo.po.ScheduleJobData;
import com.lvmama.vst.demo.service.ScheduleJobDataService;
import com.lvmama.vst.demo.service.ScheduleJobService;

public class ScheduleJobListener extends ContextLoaderListener{

	private final static Log LOG=LogFactory.getLog(ScheduleJobListener.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		new Thread(new Execute()).start();
	}

	/**
	 * 内部线程类，该类用于将数据库中的job相关数据初始化到ScheduleJobExecute
	 */
	class Execute implements Runnable {

		public void run() {
			ScheduleJobService scheduleJobService = SpringBeanProxy.getBean(ScheduleJobService.class);
			ScheduleJobDataService scheduleJobDataService = SpringBeanProxy.getBean(ScheduleJobDataService.class);
			Scheduler scheduler = (Scheduler) SpringBeanProxy.getBean("schedulerFactoryBean");
			Map<String, Object> params = new HashMap<String, Object>();

			try {
				// 获取有效的job配置信息
				params.put("status", "Y");
				List<ScheduleJob> scheduleJobs = scheduleJobService.findListByParams(params);

				for (ScheduleJob ScheduleJob : scheduleJobs) {
					// 获取job对应的详情配置信息
					params.clear();
					params.put("jobId", ScheduleJob.getJobId());
					List<ScheduleJobData> ScheduleJobDatas = scheduleJobDataService.findListByParams(params);
					Map<String, Object> jobDataMap = new HashMap<String, Object>();
					for (ScheduleJobData ScheduleJobData : ScheduleJobDatas) {
						jobDataMap.put(ScheduleJobData.getDataName(), ScheduleJobData.getDataValue());
					}
					ScheduleJob.setJobDataMap(jobDataMap);
				}

				ScheduleJobExecute jobExecute = new ScheduleJobExecute(scheduler);
				jobExecute.addAllJob(scheduleJobs);
			} catch (BusinessException e) {
				LOG.error("ScheduleJobListener occurs BusinessException, caused by:" + e);
			} catch (Exception e) {
				LOG.error("ScheduleJobListener occurs run Exception, caused by:" + e);
			}
		}

	}
}
