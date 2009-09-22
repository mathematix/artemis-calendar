package com.ics.tcg.web.user.client.db;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Service_Client implements Serializable {

	/** Service ID. */
	private Integer sid;

	/** Service provider name. */
	private String businessName;

	/** The real service name. */
	private String serviceName;

	/** Abstract service name. */
	private String abservicename;

	/** The service url. */
	private String accessPoint;

	/** The service wsdl url. */
	private String overviewURL;

	/** The service qos url. */
	private String qosInfo;

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getAbservicename() {
		return abservicename;
	}

	public void setAbservicename(String abservicename) {
		this.abservicename = abservicename;
	}

	public String getAccessPoint() {
		return accessPoint;
	}

	public void setAccessPoint(String accessPoint) {
		this.accessPoint = accessPoint;
	}

	public String getOverviewURL() {
		return overviewURL;
	}

	public void setOverviewURL(String overviewURL) {
		this.overviewURL = overviewURL;
	}

	public String getQosInfo() {
		return qosInfo;
	}

	public void setQosInfo(String qosInfo) {
		this.qosInfo = qosInfo;
	}

}
