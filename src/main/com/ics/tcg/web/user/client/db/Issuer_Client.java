package com.ics.tcg.web.user.client.db;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Issuer_Client implements Serializable {

	private Integer issuerid;
	private String issuername;
	
	public Integer getIssuerid() {
		return issuerid;
	}
	public void setIssuerid(Integer issuerid) {
		this.issuerid = issuerid;
	}
	public String getIssuername() {
		return issuername;
	}
	public void setIssuername(String issuername) {
		this.issuername = issuername;
	}

}
