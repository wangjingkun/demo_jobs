package com.lvmama.vst.demo.common.util;

import java.io.Serializable;

public class ResultHandle implements Serializable{

	private static final long serialVersionUID = 4713111068609482725L;

	private boolean success=true;
	private String msg;

	public ResultHandle(){}

	public ResultHandle(String msg){
		setMsg(msg);
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.success=false;
		this.msg = msg;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	public boolean isFail(){
		return !isSuccess();
	}
}
