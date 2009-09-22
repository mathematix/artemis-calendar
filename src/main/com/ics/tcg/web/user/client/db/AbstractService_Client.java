package com.ics.tcg.web.user.client.db;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AbstractService_Client implements Serializable {

	/** abstract service id */
	public Integer asid;

	/** abstract service name */
	public String asname;

	/** abstract service description */
	public String des;

	public Integer getAsid() {
		return asid;
	}

	public void setAsid(Integer asid) {
		this.asid = asid;
	}

	public String getAsname() {
		return asname;
	}

	public void setAsname(String asname) {
		this.asname = asname;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}
}
