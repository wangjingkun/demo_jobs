package com.lvmama.vst.demo.service;

import java.util.List;
import java.util.Map;

import com.lvmama.vst.demo.exception.BusinessException;
import com.lvmama.vst.demo.po.ScheduleJob;

public interface ScheduleJobService {

	public ScheduleJob findById(int id) throws BusinessException;

	public List<ScheduleJob> findListByParams(Map<String, Object> params) throws BusinessException;

	public int getTotalCount(Map<String, Object> param) throws BusinessException ;

	public int insert(ScheduleJob supp) throws BusinessException;

	public int update(ScheduleJob supp) throws BusinessException;

	public int delateById(int id) throws BusinessException;
}