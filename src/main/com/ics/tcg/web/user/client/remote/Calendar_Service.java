package com.ics.tcg.web.user.client.remote;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ics.tcg.web.user.client.db.Calendar_Client;
import com.ics.tcg.web.user.client.db.Records_Client;
import com.ics.tcg.web.workflow.client.data.Client_Workflow;

@RemoteServiceRelativePath("calendar")
public interface Calendar_Service extends RemoteService {

	/** get the user's calendars */
	List<Calendar_Client> getAllCalendars(int userid);

	/** get all events that have done */
	public List<Calendar_Client> getDoneCalendar(Integer userid);

	/** get all events that is undone */
	public List<Calendar_Client> getUndoneCalendar(Integer userid);

	/** get workflow of a calendar */
	Client_Workflow getWorkflow(int calendarid);

	/** save a Calendar */
	Integer saveCalendar(Calendar_Client calendarClient,
			Client_Workflow client_Workflow);

	/** update a calendar */
	Integer updateCalendar(Calendar_Client calendarClient,
			Client_Workflow client_Workflow);

	/** delete a calendar */
	String deleteCalendar(Integer calendarid);

	/** get all records of an event */
	public List<Records_Client> getRecords(Integer calendarid);

	/** Update a record means to add rating */
	public String updateRecords(Records_Client recordsClient);
	
}
