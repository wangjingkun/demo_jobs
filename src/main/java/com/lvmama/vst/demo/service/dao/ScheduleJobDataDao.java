package com.lvmama.vst.demo.service.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.vst.demo.common.mybatis.MyBatisDao;
import com.lvmama.vst.demo.po.ScheduleJobData;

@Repository
public class ScheduleJobDataDao extends MyBatisDao {

	public ScheduleJobDataDao() {
		super("SCHEDULE_JOB_DATA");
	}

	public List<ScheduleJobData> findList(Map<String, Object> params) {
		return super.queryForList("selectListByParams", params);
	}

	public Integer insert(ScheduleJobData record) {
		return super.insert("insertSelective", record);
	}

	public ScheduleJobData selectByPrimaryKey(int jobDataId) {
		return super.get("selectByPrimaryKey", jobDataId); 
	}

	public Integer updateByPrimaryKey(ScheduleJobData record) {
		return super.update("updateByPrimaryKey", record); 
	}

	public Integer updateByJobIdDataName(ScheduleJobData record) {
		return super.update("updateByJobIdDataName", record); 
	}

	public Integer updateByPrimaryKeySelective(ScheduleJobData record) {
		return super.update("updateByPrimaryKeySelective", record); 
	}

	public Integer queryTotalCount(Map<String, Object> param){
		return super.get("queryTotalCount", param); 
	}

	public Integer deleteByJobId(ScheduleJobData record){
		return super.delete("deleteByJobId", record); 
	}

	public Integer deleteByJobDataId(ScheduleJobData record){
		return super.delete("deleteByPrimaryKey", record); 
	}
}