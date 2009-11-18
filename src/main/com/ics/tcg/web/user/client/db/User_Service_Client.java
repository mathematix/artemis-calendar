package com.ics.tcg.web.user.client.db;

import java.io.Serializable;

import com.ics.tcg.web.user.client.qos.ServiceQosRequirement;

@SuppressWarnings("serial")
public class User_Service_Client implements Serializable {
	private Integer id;
	private Integer userid;
	private Integer abserviceid;
	private String abservicename;
	private ServiceQosRequirement qos;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getAbserviceid() {
		return abserviceid;
	}

	public void setAbserviceid(Integer abserviceid) {
		this.abserviceid = abserviceid;
	}

	public String getAbservicename() {
		return abservicename;
	}

	public void setAbservicename(String abservicename) {
		this.abservicename = abservicename;
	}

	public ServiceQosRequirement getQos() {
		return qos;
	}

	public void setQos(ServiceQosRequirement qos) {
		this.qos = qos;
	}

}
