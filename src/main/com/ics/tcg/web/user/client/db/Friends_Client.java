package com.ics.tcg.web.user.client.db;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Friends_Client implements IsSerializable {

	protected Integer id;
	public Integer userid;
	public Integer friendid;
	private String friendname;
	/** trust value */
	protected double value;

	public String getFriendname() {
		return friendname;
	}

	public void setFriendname(String friendname) {
		this.friendname = friendname;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getFriendid() {
		return friendid;
	}

	public void setFriendid(Integer friendid) {
		this.friendid = friendid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}
