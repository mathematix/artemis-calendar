package com.ics.tcg.web.user.client.qos;

import java.io.Serializable;

public class ServiceConfigureInfo implements Serializable {
	/**
	 * This class is used to describe the serviceConfigure information.
	 * @author lms
	 */
	private static final long serialVersionUID = 8622120095134492798L;
    
	private String serviceName;
	/**
	 *This parameter is used to save the path of the implement class of webService 
	*/
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
