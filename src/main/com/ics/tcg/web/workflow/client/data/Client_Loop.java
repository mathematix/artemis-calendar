package com.ics.tcg.web.workflow.client.data;

import com.ics.tcg.web.workflow.client.service.ServiceConfigure;

public class Client_Loop extends Client_Node {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6915680013311325032L;
	protected Client_OutputPort outputPort;
	protected Client_WhileCondition whileCondition;
	protected Client_Workflow workflow;
	public boolean changeServiceProvider;
	public int selectServiceNumber;

	public ServiceConfigure serviceConfigure;

	public ServiceConfigure getServiceConfigure() {
		return serviceConfigure;
	}

	public void setServiceConfigure(ServiceConfigure serviceConfigure) {
		this.serviceConfigure = serviceConfigure;
	}

	public Client_OutputPort getOutputPort() {
		return outputPort;
	}

	public void setOutputPort(Client_OutputPort value) {
		this.outputPort = value;
	}

	public Client_WhileCondition getWhileCondition() {
		return whileCondition;
	}

	public void setWhileCondition(Client_WhileCondition value) {
		this.whileCondition = value;
	}

	public Client_Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Client_Workflow value) {
		this.workflow = value;
	}

	public int getSelectServiceNumber() {
		return selectServiceNumber;
	}

	public void setSelectServiceNumber(int selectServiceNumber) {
		this.selectServiceNumber = selectServiceNumber;
	}

	public boolean isChangeServiceProvider() {
		return changeServiceProvider;
	}

	public void setChangeServiceProvider(boolean changeServiceProvider) {
		this.changeServiceProvider = changeServiceProvider;
	}
}
