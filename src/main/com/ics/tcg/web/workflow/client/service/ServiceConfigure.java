package com.ics.tcg.web.workflow.client.service;

import java.io.Serializable;

/**
 * The type of the service configuration by the user.
 * 
 * @author lms
 * 
 */
public class ServiceConfigure implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5331583323700702015L;

	/** The service virtual name. */
	private String serviceName;
	/** The method to be configured. */
	private MethodInfo methodInfo;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public MethodInfo getMethodInfo() {
		return methodInfo;
	}

	public void setMethodInfo(MethodInfo methodInfo) {
		this.methodInfo = methodInfo;
	}
}
