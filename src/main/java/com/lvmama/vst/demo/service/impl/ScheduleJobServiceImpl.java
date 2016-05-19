

package com.lvmama.vst.demo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.vst.demo.common.constant.ErrorCodeMsg;
import com.lvmama.vst.demo.exception.BusinessException;
import com.lvmama.vst.demo.po.ScheduleJob;
import com.lvmama.vst.demo.service.ScheduleJobService;
import com.lvmama.vst.demo.service.dao.ScheduleJobDao;


@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {

	private static final Log LOG = LogFactory.getLog(ScheduleJobServiceImpl.class);
	@Autowired
	ScheduleJobDao suppDao;

	public List<ScheduleJob> findListByParams(Map<String,Object> params) throws BusinessException {
		List<ScheduleJob> list = null;
		try{
			list = suppDao.findList(params);
		}catch (Exception e) {
			LOG.error("method findListByParams error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_SYS);
		}
		return list;
	}

	public ScheduleJob findById(int id) throws BusinessException {
		ScheduleJob supp = null;
		try{
			supp = suppDao.selectByPrimaryKey(id);
		}catch (Exception e) {
			LOG.error("method findById error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_SYS);
		}
		return supp;
	}

	public int insert(ScheduleJob supp) throws BusinessException {	
		int result = 0;
		try{
			result = suppDao.insert(supp);
		}catch (Exception e) {
			LOG.error("method insert error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_SYS);
		}
		return result;
	}

	public int update(ScheduleJob supp) throws BusinessException {
		int result = 0;
		try{
			result = suppDao.updateByPrimaryKeySelective(supp);
		}catch (Exception e) {
			LOG.error("method update error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_SYS);
		}
		return result;
	}

	public int delateById(int id) throws BusinessException {
		
		int result = 0;
		try{
			result = suppDao.deleteByPrimaryKey(id);
		}catch (Exception e) {
			LOG.error("method delateById error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_SYS);
		}
		return result;
	}

	public int getTotalCount(Map<String, Object> param)throws BusinessException {
		int result = 0 ;
		try {
			result = suppDao.queryTotalCount(param);
		} catch (Exception e) {
			LOG.error("method getTotalCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_SYS);
		}
		return result;
	}
 }