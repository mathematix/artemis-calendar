package com.ics.tcg.web.workflow.client.service;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The type of the service package.
 * 
 * @author lms
 * 
 */
public class ServiceInfo implements Serializable {
	/**
	 * @author lms
	 */
	private static final long serialVersionUID = 6741990704479131548L;

	/** Service virtual name */
	private String serviceName;
	/** Methods of the service. */
	private ArrayList<MethodInfo> methodInfoArray;

	public ServiceInfo() {
		serviceName = new String();
		methodInfoArray = new ArrayList<MethodInfo>();
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public ArrayList<MethodInfo> getMethodInfoArray() {
		return methodInfoArray;
	}

	public void setMethodInfoArray(ArrayList<MethodInfo> methodInfoArray) {
		this.methodInfoArray = methodInfoArray;
	}
}
