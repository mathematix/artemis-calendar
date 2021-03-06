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
@Table(name = "User_ThirdPart")
public class User_ThirdPart implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(nullable = false)
	protected Integer userid;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(nullable = false)
	protected Integer thirdpartid;

	public Integer getThirdpartid() {
		return thirdpartid;
	}

	public void setThirdpartid(Integer thirdpartid) {
		this.thirdpartid = thirdpartid;
	}

	@Column(nullable = false)
	private Double trust;

	public Double getTrust() {
		return trust;
	}

	public void setTrust(Double trust) {
		this.trust = trust;
	}
}
