package com.akis.logview.model;

public class logVO {

	public String logData;
	
	
	public void setLog(String log) {
		this.logData += log;
	}
	public String getLog() {
		return this.logData;
	}
}
