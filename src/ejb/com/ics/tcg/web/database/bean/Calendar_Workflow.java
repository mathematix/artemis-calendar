package com.ics.tcg.web.database.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "calendar_workflow")
public class Calendar_Workflow implements Serializable {

	@Id
	protected Integer calendarid;

	public Integer getCalendarid() {
		return calendarid;
	}

	public void setCalendarid(Integer calendarid) {
		this.calendarid = calendarid;
	}

	@Lob
	protected byte[] workflow;

	public byte[] getWorkflow() {
		return workflow;
	}

	public void setWorkflow(byte[] workflow) {
		this.workflow = workflow;
	}

}
