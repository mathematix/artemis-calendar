package com.ics.tcg.web.database.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "Issuer")
public class Issuer implements Serializable {

	private Integer issuerid;
	private String issuername;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getIssuerid() {
		return issuerid;
	}

	public void setIssuerid(Integer issuerid) {
		this.issuerid = issuerid;
	}

	@Column(nullable = false, length = 32)
	public String getIssuername() {
		return issuername;
	}

	public void setIssuername(String issuername) {
		this.issuername = issuername;
	}

}
