package com.ics.tcg.web.database.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "Service")
public class Service implements Serializable {

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

	@Id
	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	@Column(nullable = true, length = 32)
	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	@Column(nullable = true, length = 32)
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Column(nullable = true)
	public String getAccessPoint() {
		return accessPoint;
	}

	public void setAccessPoint(String accessPoint) {
		this.accessPoint = accessPoint;
	}

	@Column(nullable = true)
	public String getOverviewURL() {
		return overviewURL;
	}

	public void setOverviewURL(String overviewURL) {
		this.overviewURL = overviewURL;
	}

	@Column(nullable = true)
	public String getQosInfo() {
		return qosInfo;
	}

	public void setQosInfo(String qosInfo) {
		this.qosInfo = qosInfo;
	}

	@Column(nullable = true)
	public String getAbservicename() {
		return abservicename;
	}

	public void setAbservicename(String abservicename) {
		this.abservicename = abservicename;
	}

}
