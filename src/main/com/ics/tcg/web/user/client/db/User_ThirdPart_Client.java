package com.ics.tcg.web.user.client.db;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User_ThirdPart_Client implements Serializable {
	
	private Integer userid;
	private Integer thirdpartid;
	private Double trust;
	private Integer id;
	private String thirdpartname;

	public String getThirdpartname() {
		return thirdpartname;
	}

	public void setThirdpartname(String thirdpartname) {
		this.thirdpartname = thirdpartname;
	}

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
	public Integer getThirdpartid() {
		return thirdpartid;
	}
	public void setThirdpartid(Integer thirdpartid) {
		this.thirdpartid = thirdpartid;
	}
	public Double getTrust() {
		return trust;
	}
	public void setTrust(Double trust) {
		this.trust = trust;
	}
}
