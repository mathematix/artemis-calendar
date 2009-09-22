package com.ics.tcg.web.user.client.db;

import java.io.Serializable;

public class Records_Client implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5816108495645151819L;

	private Integer recordid;
	private Integer calendarid;
	private Integer userid;
	/** real service id */
	private Integer sid;
	/** component id that contain the service */
	private Integer cid;
	private boolean success;
	private Double restime;
	private Integer rating;
	/** not in the table,but add to */
	private String servicename;
	private String abservicename;
	private String companyname;

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public String getAbservicename() {
		return abservicename;
	}

	public void setAbservicename(String abservicename) {
		this.abservicename = abservicename;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getRecordid() {
		return recordid;
	}

	public void setRecordid(Integer recordid) {
		this.recordid = recordid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Integer getCalendarid() {
		return calendarid;
	}

	public void setCalendarid(Integer calendarid) {
		this.calendarid = calendarid;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Double getRestime() {
		return restime;
	}

	public void setRestime(Double restime) {
		this.restime = restime;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

}
