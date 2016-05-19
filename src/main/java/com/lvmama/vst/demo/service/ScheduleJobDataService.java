package com.lvmama.vst.demo.service;

import java.util.List;
import java.util.Map;

import com.lvmama.vst.demo.exception.BusinessException;
import com.lvmama.vst.demo.po.ScheduleJobData;

public interface ScheduleJobDataService {

	public ScheduleJobData findById(int id) throws BusinessException;

	public List<ScheduleJobData> findListByParams(Map<String, Object> params) throws BusinessException;

	public int insert(ScheduleJobData suppScheduleJobData) throws BusinessException;

	public int update(ScheduleJobData suppScheduleJobData) throws BusinessException;

	public int updateByJobIdDataName(ScheduleJobData suppScheduleJobData) throws BusinessException;

	public void saveOrUpdateDockingByJobIdDataName(int jobId ,String dataName,String dataValue) throws BusinessException;

	public int deleteByJobId(ScheduleJobData suppScheduleJobData) throws BusinessException;

	public int deleteByJobDataId(ScheduleJobData suppScheduleJobData) throws BusinessException;;
}