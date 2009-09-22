package com.ics.tcg.web.database;

import java.util.List;

import com.ics.tcg.web.database.bean.CalendarEvent;
import com.ics.tcg.web.database.bean.Calendar_Workflow;

public interface CalendarEventDAO {
	/**
	 * get all events of the user
	 * 
	 * @param userid
	 */
	public List<CalendarEvent> getCalendar(Integer userid);

	/**
	 * get specific event
	 * 
	 * @param userid
	 * @param calendarid
	 */
	public CalendarEvent get(Integer userid, Integer calendarid);

	/**
	 * insert an event into the database
	 * 
	 * @param calendarEvent
	 */
	public int insertCalendar(CalendarEvent calendarEvent);

	/**
	 * update an event
	 * 
	 * @param calendarEvent
	 */
	public void mergeCalendar(CalendarEvent calendarEvent);

	/**
	 * delete an event
	 * 
	 * @param calendarid
	 */
	public void deleteCalendar(int calendarid);

	/**
	 * get workflow of the event
	 * 
	 * @param calendarid
	 */
	public Calendar_Workflow getWorkflow(Integer calendarid);

	/**
	 * delete workflow of the event
	 * 
	 * @param calendarid
	 */
	public void deleteWorkflow(int calendarid);

	/**
	 * insert workflow of the event
	 * 
	 * @param cw
	 */
	public void insertWorkflow(Calendar_Workflow cw);

	/**
	 * update workflow of the event
	 * 
	 * @param cw
	 */
	public void mergeWorkflow(Calendar_Workflow cw);

	/**
	 * get events that have been done
	 * 
	 * @param userid
	 */
	public List<CalendarEvent> getDoneCalendar(Integer userid);

	/**
	 * get events unfinished
	 * 
	 * @param userid
	 */
	public List<CalendarEvent> getUndoneCalendar(Integer userid);
}
