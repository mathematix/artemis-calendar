package com.ics.tcg.web.workflow.client.data;

public class Client_ServiceTask extends Client_Node {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3242980046269327869L;
	protected Client_Timer timer;
	protected Client_Service service;
	public boolean beDone;

	// protected ServiceInfo serviceInfo;

	protected int current_method_index;
	protected Integer hasCycleNum;

	protected Client_OutputPort outputPort;

	// public ServiceInfo getServiceInfo() {
	// return serviceInfo;
	// }
	//
	// public void setServiceInfo(ServiceInfo serviceInfo) {
	// this.serviceInfo = serviceInfo;
	// }

	public Client_Timer getTimer() {
		return timer;
	}

	public void setTimer(Client_Timer value) {
		this.timer = value;
	}

	public Client_Service getService() {
		return service;
	}

	public void setService(Client_Service value) {
		this.service = value;
	}

	// public boolean isHasTimer() {
	// return hasTimer;
	// }
	//
	// public void setHasTimer(boolean hasTimer) {
	// this.hasTimer = hasTimer;
	// }

	// public String getStartTime() {
	// return startTime;
	// }
	//
	// public void setStartTime(String startTime) {
	// this.startTime = startTime;
	// }

	public int getCurrent_method_index() {
		return current_method_index;
	}

	public void setCurrent_method_index(int current_method_index) {
		this.current_method_index = current_method_index;
	}

	public int getHasCycleNum() {
		if (hasCycleNum == null) {
			return 1;
		} else {
			return hasCycleNum;
		}
	}

	public void setHasCycleNum(Integer value) {
		this.hasCycleNum = value;
	}

	public boolean isBeDone() {
		return beDone;
	}

	public void setBeDone(boolean beDone) {
		this.beDone = beDone;
	}

	public Client_OutputPort getOutputPort() {
		return outputPort;
	}

	public void setOutputPort(Client_OutputPort value) {
		this.outputPort = value;
	}
}
