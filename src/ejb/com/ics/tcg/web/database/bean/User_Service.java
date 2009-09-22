package com.ics.tcg.web.database.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "User_Service")
public class User_Service implements Serializable {

	private Integer id;
	private Integer userid;
	private Integer abserviceid;
	private String abservicename;
	private byte[] qos;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(nullable = false)
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(nullable = false)
	public Integer getAbserviceid() {
		return abserviceid;
	}

	public void setAbserviceid(Integer abserviceid) {
		this.abserviceid = abserviceid;
	}

	@Column(nullable = false)
	public String getAbservicename() {
		return abservicename;
	}

	public void setAbservicename(String abservicename) {
		this.abservicename = abservicename;
	}

	@Lob
	public byte[] getQos() {
		return qos;
	}

	public void setQos(byte[] qos) {
		this.qos = qos;
	}

}
