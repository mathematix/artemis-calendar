package com.ics.tcg.web.user.client.db;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User_Issuer_Client implements Serializable {

	private Integer id;
	private Integer issuerid;
	private Integer userid;
	private String issuename;

	public String getIssuename() {
		return issuename;
	}

	public void setIssuename(String issuename) {
		this.issuename = issuename;
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

	public Integer getIssuerid() {
		return issuerid;
	}

	public void setIssuerid(Integer issuerid) {
		this.issuerid = issuerid;
	}
}
