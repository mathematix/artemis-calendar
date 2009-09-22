package com.ics.tcg.web.workflow.client.data;

public class Client_NonFixedNumFor extends Client_Loop {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8491371225847296064L;
	protected String startTime;
	protected String endTime;
	protected String step;
	protected String currentTime;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

}
