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
@Table(name = "AbstractService")
public class AbstractService implements Serializable {

	/** abstract service id */
	public Integer asid;
	/** abstract service name */
	public String asname;
	/** abstract service description */
	public String des;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getAsid() {
		return asid;
	}

	public void setAsid(Integer asid) {
		this.asid = asid;
	}

	@Column(nullable = false, length = 32)
	public String getAsname() {
		return asname;
	}

	public void setAsname(String asname) {
		this.asname = asname;
	}

	@Lob
	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}
}
