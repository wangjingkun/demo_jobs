package com.lvmama.vst.demo.po;

import java.io.Serializable;

public class ScheduleJobData implements Serializable {

	private static final long serialVersionUID = 686405341668299067L;

	/** 任务数据id */
	private int jobDataId;
	/** 任务id */
	private int jobId;
	/** 任务数据名称 */
	private String dataName;
	/** 任务数据值 */
	private String dataValue;

	public int getJobDataId() {
		return jobDataId;
	}

	public void setJobDataId(int jobDataId) {
		this.jobDataId = jobDataId;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
}
