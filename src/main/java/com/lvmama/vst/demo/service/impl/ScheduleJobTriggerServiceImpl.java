package com.lvmama.vst.demo.service.impl;

import javax.annotation.Resource;

import org.quartz.Scheduler;
import org.springframework.stereotype.Service;

import com.lvmama.vst.demo.common.jobs.ScheduleJobExecute;
import com.lvmama.vst.demo.common.spring.SpringBeanProxy;
import com.lvmama.vst.demo.exception.BusinessException;
import com.lvmama.vst.demo.po.ScheduleJob;
import com.lvmama.vst.demo.service.ScheduleJobTriggerService;

@Service
public class ScheduleJobTriggerServiceImpl implements ScheduleJobTriggerService{
	
	@Resource(name="schedulerFactoryBean")
	Scheduler scheduler ;

	public int addJob(ScheduleJob job) throws BusinessException {
		int result = 0 ;
		if (null == scheduler) {
			scheduler =  (Scheduler) SpringBeanProxy.getBean("schedulerFactoryBean");
		}
		ScheduleJobExecute jobExecute = new ScheduleJobExecute(scheduler);
		boolean flag = jobExecute.addJob(job);
		if (flag) {
			result++;
		}
		return result;
	}

	public int delate(ScheduleJob job) throws BusinessException {
		int result = 0 ;
		if (null == scheduler) {
			scheduler = (Scheduler) SpringBeanProxy.getBean("schedulerFactoryBean");
		}
		ScheduleJobExecute jobExecute = new ScheduleJobExecute(scheduler);
		boolean flag = jobExecute.deleteJob(job);
		if (flag) {
			result++;
		}
		return result;
	}

}
