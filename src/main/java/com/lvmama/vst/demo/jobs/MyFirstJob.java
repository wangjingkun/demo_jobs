package com.lvmama.vst.demo.jobs;

import java.util.Date;

import com.lvmama.vst.demo.common.jobs.AbstractJob;

public class MyFirstJob extends AbstractJob{

	public void run() {
		System.out.println("MyFirstJob:" + new Date().toString());
	}

}
