package com.lvmama.vst.demo.quartz.test;

import java.util.Date;

public class SampleJob implements Runnable {

	public void run() {
		System.out.println("当前时间：" + new Date().toString());
		try {
			Thread.sleep(5*1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
