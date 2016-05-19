package com.lvmama.vst.demo.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lvmama.vst.demo.common.vo.Page;
import com.lvmama.vst.demo.common.web.BaseActionSupport;
import com.lvmama.vst.demo.exception.BusinessException;
import com.lvmama.vst.demo.po.ScheduleJob;
import com.lvmama.vst.demo.po.ScheduleJobData;
import com.lvmama.vst.demo.service.ScheduleJobTriggerService;
import com.lvmama.vst.demo.service.ScheduleJobDataService;
import com.lvmama.vst.demo.service.ScheduleJobService;
import com.lvmama.vst.demo.service.dao.ScheduleJobDao;
import com.lvmama.vst.demo.vo.ResultMessage;

@Controller
@RequestMapping("/schedule")
public class ScheduleJobAction extends BaseActionSupport {

	@Autowired
	ScheduleJobService scheduleJobService;
	@Autowired
	ScheduleJobDataService scheduleJobDataService;
	@Autowired
	ScheduleJobDao scheduleJobDao;
	@Autowired
	ScheduleJobTriggerService scheduleJobTriggerService;

	private static final Log LOG = LogFactory.getLog(ScheduleJobAction.class);

	/**
	 * JOB添加初始化
	 * 
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/addJobInit")
	public String addJobInit(Model model) throws BusinessException {
		return "/schedule/addJob";
	}

	@RequestMapping(value = "/updateInitJob")
	public String updateJobInit(Model model, ScheduleJob scheduleJobParam) throws BusinessException {
		ScheduleJob scheduleJob = scheduleJobService.findById(scheduleJobParam.getJobId());
		model.addAttribute("scheduleJob", scheduleJob);
		return "/schedule/updateInitJob";
	}

	/**
	 * JOB添加
	 * 
	 * @param model
	 * @param scheduleJob
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/addScheduleJob")
	@ResponseBody
	public Object addScheduleJob(Model model, ScheduleJob scheduleJob, HttpServletRequest req) {

		try {
			scheduleJobService.insert(scheduleJob);
		} catch (BusinessException e) {
			LOG.error("add addScheduleJob error{}", e);
		}

		return ResultMessage.ADD_SUCCESS_RESULT;
	}

	/**
	 * JOB删除
	 * 
	 * @param model
	 * @param scheduleJob
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/deleteScheduleJob")
	@ResponseBody
	public Object deleteScheduleJob(Model model, ScheduleJob scheduleJob, HttpServletRequest req) {
		try {
			scheduleJobService.delateById(scheduleJob.getJobId());// 删除主表记录
			ScheduleJobData jobDateParam = new ScheduleJobData();
			jobDateParam.setJobId(scheduleJob.getJobId());
			scheduleJobDataService.deleteByJobId(jobDateParam);
		} catch (BusinessException e) {
			LOG.error("delete deleteScheduleJob error{}", e);
		}

		return ResultMessage.DELETE_SUCCESS_RESULT;
	}

	/**
	 * JOB更新主表
	 * 
	 * @param model
	 * @param scheduleJob
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/updateScheduleJob")
	@ResponseBody
	public Object updateScheduleJob(Model model, ScheduleJob scheduleJob, HttpServletRequest req) {
		try {
			scheduleJobService.update(scheduleJob);
		} catch (BusinessException e) {
			LOG.error("update updateScheduleJob error{}", e);
		}

		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}

	/**
	 * JOB主表查询
	 * 
	 * @param model
	 * @param scheduleJob
	 * @param page
	 * @param req
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/findScheduleJobList")
	public String findScheduleJobList(Model model, ScheduleJob scheduleJob, Integer page, HttpServletRequest req) throws BusinessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("start method<findScheduleJobList>");
		}

		Map<String, Object> param = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(scheduleJob.getStatus())) {
			param.put("status", scheduleJob.getStatus());
		}

		String jobName = req.getParameter("jobName");
		if (!StringUtils.isEmpty(jobName)) {
			param.put("jobName", jobName);
			model.addAttribute("jobName", jobName);
		}

		int count = scheduleJobService.getTotalCount(param);

		//分装分页信息
		int pagenum = page == null ? 1 : page;
		Page<ScheduleJob> pageParam = Page.page(count, 10, pagenum);
		pageParam.buildUrl(req);

		param.put("_start", pageParam.getStartRowsMySql());
		param.put("_pageSize", pageParam.getPageSize());
		List<ScheduleJob> scheduleJobList = scheduleJobService.findListByParams(param);
		pageParam.setItems(scheduleJobList);

		model.addAttribute("pageParam", pageParam);
		model.addAttribute("scheduleJob", scheduleJob);

		return "/schedule/findScheduleJob";
	}

	/**
	 * 子表维护
	 */
	@RequestMapping(value = "/findScheduleJobDataList")
	public String findScheduleJobDataList(Model model, ScheduleJob scheduleJob, HttpServletRequest req)
			throws BusinessException {
		if (scheduleJob == null) {
			return "/schedule/findScheduleJobDataList";
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("jobId", scheduleJob.getJobId());
		List<ScheduleJobData> list = scheduleJobDataService.findListByParams(paramMap);
		model.addAttribute("scheduleJobDataList", list);
		model.addAttribute("scheduleJob", scheduleJob);

		return "/schedule/findScheduleJobDataList";
	}

	/**
	 * JOB_DATA添加
	 * 
	 * @param model
	 * @param scheduleJobData
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/addScheduleJobData")
	@ResponseBody
	public Object addScheduleJobData(Model model, ScheduleJobData scheduleJobData, HttpServletRequest req) {

		try {
			scheduleJobDataService.insert(scheduleJobData);
		} catch (BusinessException e) {
			LOG.error("addScheduleJobData error{}", e);
		}

		return ResultMessage.ADD_SUCCESS_RESULT;
	}

	/**
	 * JOB_DATA添加初始化
	 * 
	 * @param model
	 * @param scheduleJob
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/addJobDataInit")
	public String addJobDataInit(Model model, ScheduleJob scheduleJob) throws BusinessException {
		model.addAttribute("scheduleJob", scheduleJob);
		return "/schedule/addJobData";
	}

	/**
	 * JOB_DATA删除
	 * 
	 * @param model
	 * @param jobDateParam
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/deleteScheduleJobData")
	@ResponseBody
	public Object deleteScheduleJobData(Model model,ScheduleJobData jobDateParam, HttpServletRequest req) {

		try {
			scheduleJobDataService.deleteByJobDataId(jobDateParam);
		} catch (Exception e) {
			LOG.error("delete deleteScheduleJobData error{}", e);
		}

		return ResultMessage.DELETE_SUCCESS_RESULT;
	}

	/**
	 * JOB_DATA更新主表
	 * 
	 * @param model
	 * @param jobData
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/updateScheduleJobData")
	@ResponseBody
	public Object updateScheduleJobData(Model model, ScheduleJobData jobData, HttpServletRequest req) {

		try {
			int row = scheduleJobDataService.update(jobData);
			LOG.info(row);
		} catch (BusinessException e) {
			LOG.error("update updateScheduleJobData error{}", e);
		}

		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}

	/**
	 * 更新DATA初始化
	 */
	@RequestMapping(value = "/updateInitJobData")
	public String updateInitJobData(Model model, ScheduleJobData jobData) throws BusinessException {
		ScheduleJobData scheduleJobData = scheduleJobDataService.findById(jobData.getJobDataId());
		model.addAttribute("scheduleJobData", scheduleJobData);
		return "/schedule/updateInitJobData";
	}

	/**
	 * JOB启动
	 */
	@RequestMapping(value = "/startJob")
	@ResponseBody
	public Object startJob(Model model, ScheduleJob jobParam) throws BusinessException {

		ScheduleJob scheduleJob = scheduleJobService.findById(jobParam.getJobId());
		if ("N".equalsIgnoreCase(scheduleJob.getStatus())) {
			scheduleJob.setStatus("Y");
			scheduleJobService.update(scheduleJob);
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("jobId", scheduleJob.getJobId());
		List<ScheduleJobData> listJobData = scheduleJobDataService.findListByParams(paramMap);
		if (listJobData.size() < 3) {
			return ResultMessage.JOB_PARAM_ERROR;
		}
		Map<String, Object> jobDataMap = new HashMap<String, Object>();
		for (ScheduleJobData jobData : listJobData) {
			jobDataMap.put(jobData.getDataName(), jobData.getDataValue());
		}
		scheduleJob.setJobDataMap(jobDataMap);
		int result = scheduleJobTriggerService.addJob(scheduleJob);

		if (result == 0) {
			return ResultMessage.JOB_START_FAIL;
		}
		return ResultMessage.JOB_START_SUCCESS;
	}

	@RequestMapping(value = "/stopJob")
	@ResponseBody
	public Object stopJob(Model model, ScheduleJob job) throws BusinessException {

		ScheduleJob scheduleJob = scheduleJobService.findById(job.getJobId());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("jobId", scheduleJob.getJobId());
		List<ScheduleJobData> listJobData = scheduleJobDataService.findListByParams(paramMap);
		if (listJobData.size() < 3) {
			return ResultMessage.JOB_PARAM_ERROR;
		}
		Map<String, Object> jobDataMap = new HashMap<String, Object>();
		for (ScheduleJobData jobData : listJobData) {
			jobDataMap.put(jobData.getDataName(), jobData.getDataValue());
		}
		scheduleJob.setJobDataMap(jobDataMap);
		int result = scheduleJobTriggerService.delate(scheduleJob);

		if (result == 0) {
			return ResultMessage.JOB_STOP_FAIL;
		}
		scheduleJob.setStatus("N");
		int row = scheduleJobService.update(scheduleJob);
		if (row == 0) {
			return ResultMessage.JOB_STOP_FAIL;
		}
		return ResultMessage.JOB_STOP_SUCCESS;

	}

}
