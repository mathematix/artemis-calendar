package com.ics.tcg.web.user.client.db;

import java.util.Date;

import com.bradrydzewski.gwt.calendar.client.Appointment;

@SuppressWarnings("deprecation")
public class AppointmentMod extends Appointment {

	public Calendar_Client calendarClient;

	public AppointmentMod() {
		super();
		calendarClient = new Calendar_Client();
	}

	/** when change or add the appointment */
	public void setAtoC() {
		calendarClient.setDes(getDescription());
		calendarClient.setEventname(getTitle());
		calendarClient.setStartTime(getStart());
		calendarClient.setEndTime(getEnd());
		long Time = (calendarClient.getStartTime().getTime() / 1000) - 60 * 10;
		Date date = new Date();
		date.setTime(Time * 1000);
		calendarClient.setSearchstartTime(date);// set search start time
		calendarClient.setLocked(false);
		// Integer calendarid;
		// public Integer userid;
		// public Boolean done;
	}

	/** when load from server */
	public void setCtoA() {
		setDescription(calendarClient.getDes());
		setTitle(calendarClient.getEventname());
		setStart(calendarClient.getStartTime());
		setEnd(calendarClient.getEndTime());
		// if is multiday
		if (getEnd().getYear() != getStart().getYear()
				|| getEnd().getMonth() != getStart().getMonth()
				|| getEnd().getDate() != getStart().getDate()) {
			setMultiDay(true);
		}
	}
}
