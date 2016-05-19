package com.lvmama.vst.demo.service.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.vst.demo.common.mybatis.MyBatisDao;
import com.lvmama.vst.demo.po.ScheduleJob;

@Repository
public class ScheduleJobDao extends MyBatisDao {

	public ScheduleJobDao() {
		super("SCHEDULE_JOB");
	}

	public List<ScheduleJob> findList(Map<String, Object> params) {
		return super.queryForList("selectListByParams", params);
	}

	public Integer insert(ScheduleJob record) {
		return super.insert("insertSelective", record);
	}

	public ScheduleJob selectByPrimaryKey(int id) {
		return super.get("selectByPrimaryKey", id); 
	}

	public Integer updateByPrimaryKey(ScheduleJob record) {
		return super.update("updateByPrimaryKey", record); 
	}

	public Integer updateByPrimaryKeySelective(ScheduleJob record) {
		return super.update("updateByPrimaryKeySelective", record); 
	}

	public Integer queryTotalCount(Map<String, Object> param){
		return super.get("queryTotalCount", param); 
	}

	public Integer deleteByPrimaryKey(int id) {
		return super.delete("deleteByPrimaryKey",id); 
	}

/*	public Long getSeq(){
		Long id=(Long)super.get("getSequence",""); 
		return id;
	}*/
}