package com.ics.tcg.web.user.client.db;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Calendar_Client implements Serializable {

	private Integer calendarid;
	private Integer userid;
	private String eventname;
	private Date startTime = null;
	private Date endTime = null;
	private Date searchstartTime = null;
	private String des = null;
	private Boolean done;
	private boolean locked;

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public Integer getCalendarid() {
		return calendarid;
	}

	public void setCalendarid(Integer calendarid) {
		this.calendarid = calendarid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getEventname() {
		return eventname;
	}

	public void setEventname(String eventname) {
		this.eventname = eventname;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getSearchstartTime() {
		return searchstartTime;
	}

	public void setSearchstartTime(Date searchstartTime) {
		this.searchstartTime = searchstartTime;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public Boolean getDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}

}
