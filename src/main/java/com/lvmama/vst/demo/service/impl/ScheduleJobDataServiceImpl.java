

package com.lvmama.vst.demo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.vst.demo.common.constant.ErrorCodeMsg;
import com.lvmama.vst.demo.exception.BusinessException;
import com.lvmama.vst.demo.po.ScheduleJobData;
import com.lvmama.vst.demo.service.ScheduleJobDataService;
import com.lvmama.vst.demo.service.dao.ScheduleJobDataDao;


@Service
public  class ScheduleJobDataServiceImpl implements ScheduleJobDataService {

	private static final Log LOG = LogFactory.getLog(ScheduleJobDataServiceImpl.class);

	@Autowired
	ScheduleJobDataDao suppDao;

	public ScheduleJobData findById(int id) throws BusinessException {
		ScheduleJobData supp = null;
		try{
			supp = suppDao.selectByPrimaryKey(id);
		}catch (Exception e) {
			LOG.error("method findById error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_SYS);
		}
		return supp;
	}

	public List<ScheduleJobData> findListByParams(Map<String, Object> params) throws BusinessException {
		List<ScheduleJobData> list = null;
		try{
			list = suppDao.findList(params);
		}catch (Exception e) {
			LOG.error("method findListByParams error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_SYS);
		}
		return list;
	}

	public int insert(ScheduleJobData suppScheduleJobData) throws BusinessException {
		int result = 0;
		try{
			result = suppDao.insert(suppScheduleJobData);
		}catch (Exception e) {
			LOG.error("method insert error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_SYS);
		}
		return result;
	}

	public int update(ScheduleJobData suppScheduleJobData) throws BusinessException {
		int result = 0;
		try{
			result = suppDao.updateByPrimaryKeySelective(suppScheduleJobData);
		}catch (Exception e) {
			LOG.error("method update error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_SYS);
		}
		return result;
	}

	public int updateByJobIdDataName(ScheduleJobData suppScheduleJobData) throws BusinessException {
		int result = 0;
		try{
			result = suppDao.updateByJobIdDataName(suppScheduleJobData);
		}catch (Exception e) {
			LOG.error("method update error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_SYS);
		}
		return result;
	}

	public void saveOrUpdateDockingByJobIdDataName(int jobId, String dataName, String dataValue) throws BusinessException {
		ScheduleJobData supp = new ScheduleJobData();
		supp.setJobId(jobId);
		supp.setDataName(dataName);
		supp.setDataValue(dataValue);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("jobId", jobId);
		params.put("dataName", dataName);
		List<ScheduleJobData> supps = this.findListByParams(params);
		if(supps.size()>0){
			this.updateByJobIdDataName(supp);
		}else{
			this.insert(supp);
		}
	}

	public int deleteByJobId(ScheduleJobData suppScheduleJobData)
			throws BusinessException {
		int row =suppDao.deleteByJobId(suppScheduleJobData);
		return row;
	}

	public int deleteByJobDataId(ScheduleJobData suppScheduleJobData)
			throws BusinessException {
		int row =suppDao.deleteByJobDataId(suppScheduleJobData);
		return row;
	}
 }