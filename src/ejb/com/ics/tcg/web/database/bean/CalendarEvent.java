package com.ics.tcg.web.database.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "Calendar")
public class CalendarEvent implements Serializable {

	private Integer calendarid;
	private Integer userid;
	private String eventname;
	private String des;
	private Date starttime;
	private Date endtime;
	private Date searchstarttime;
	private Boolean done;
	private boolean locked;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getCalendarid() {
		return calendarid;
	}

	public void setCalendarid(Integer calendarid) {
		this.calendarid = calendarid;
	}

	@Column(nullable = true)
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(nullable = true, length = 32)
	public String getEventname() {
		return eventname;
	}

	public void setEventname(String eventname) {
		this.eventname = eventname;
	}

	@Lob
	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	@Temporal(value = TemporalType.TIMESTAMP)
	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	@Temporal(value = TemporalType.TIMESTAMP)
	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	@Temporal(value = TemporalType.TIMESTAMP)
	public Date getSearchstarttime() {
		return searchstarttime;
	}

	public void setSearchstarttime(Date searchstarttime) {
		this.searchstarttime = searchstarttime;
	}
	
	@Column(nullable = false)
	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Column(nullable = true)
	public Boolean getDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}


	
}
