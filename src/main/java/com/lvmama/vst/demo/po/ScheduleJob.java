package com.lvmama.vst.demo.po;

import java.io.Serializable;
import java.util.Map;


public class ScheduleJob implements Serializable{

	private static final long serialVersionUID = 9028541372416256775L;

	/** 任务id */
    private int jobId; 
    /** 任务名称 */
    private String jobName;
    /** 任务分组 */
    private String jobGroup;
    /** 任务状态 N禁用 Y启用 */
    private String status;
    /** 任务运行时间表达式 */
    private String cronExpression; 
    /** 任务描述 */
    private String remark;
    /** 调度JOB*/
    private String runClass;
    /** JOB DATE MAP*/
    private Map<String, Object> jobDataMap;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRunClass() {
		return runClass;
	}

	public void setRunClass(String runClass) {
		this.runClass = runClass;
	}

	public Map<String, Object> getJobDataMap() {
		return jobDataMap;
	}

	public void setJobDataMap(Map<String, Object> jobDataMap) {
		this.jobDataMap = jobDataMap;
	}
}
