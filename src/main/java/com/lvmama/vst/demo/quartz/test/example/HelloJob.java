package com.lvmama.vst.demo.quartz.test.example;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class HelloJob implements Job {

	private static final Log LOG = LogFactory.getLog(HelloJob.class);

	public void execute(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {
		 // Say Hello to the World and display the date/time
		LOG.info("Hello World! - " + new Date());
	}

}
