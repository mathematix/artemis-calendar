package com.ics.tcg.web.user.client.qos;

import java.io.Serializable;

public class ServiceConfigureInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8622120095134492798L;

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
