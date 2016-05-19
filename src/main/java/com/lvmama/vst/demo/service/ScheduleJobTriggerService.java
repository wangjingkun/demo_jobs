package com.lvmama.vst.demo.service;

import com.lvmama.vst.demo.exception.BusinessException;
import com.lvmama.vst.demo.po.ScheduleJob;

public interface ScheduleJobTriggerService {

	public int addJob(ScheduleJob job) throws BusinessException;

	public int delate(ScheduleJob job) throws BusinessException;
}
