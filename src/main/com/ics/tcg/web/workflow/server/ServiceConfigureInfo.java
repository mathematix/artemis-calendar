package com.ics.tcg.web.workflow.server;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ServiceConfigureInfo implements Serializable {
	private String serviceName;
	private String serviceClassPath;

	public ServiceConfigureInfo() {
		serviceName = new String();
		serviceClassPath = new String();
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceClassPath() {
		return serviceClassPath;
	}

	public void setServiceClassPath(String serviceClassPath) {
		this.serviceClassPath = serviceClassPath;
	}
}
