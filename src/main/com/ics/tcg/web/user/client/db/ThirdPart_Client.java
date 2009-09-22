package com.ics.tcg.web.user.client.db;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ThirdPart_Client implements Serializable {

	public Integer tpid;
	public String tpname;

	public Integer getTpid() {
		return tpid;
	}

	public void setTpid(Integer tpid) {
		this.tpid = tpid;
	}

	public String getTpname() {
		return tpname;
	}

	public void setTpname(String tpname) {
		this.tpname = tpname;
	}

}
