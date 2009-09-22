package com.ics.tcg.web.workflow.client.data;

import java.io.Serializable;

import com.ics.tcg.web.workflow.client.service.ServiceConfigure;

public class Client_Configuration implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 58931077441297121L;
	ServiceConfigure serviceConfigure;

	public ServiceConfigure getServiceConfigure() {
		return serviceConfigure;
	}

	public void setServiceConfigure(ServiceConfigure serviceConfigure) {
		this.serviceConfigure = serviceConfigure;
	}
}
