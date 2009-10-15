package com.ics.tcg.web.user.client.remote;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ics.tcg.web.user.client.db.Calendar_Client;
import com.ics.tcg.web.user.client.db.Records_Client;
import com.ics.tcg.web.workflow.client.data.Client_Workflow;

public interface Calendar_ServiceAsync {

	void getAllCalendars(int userid,
			AsyncCallback<List<Calendar_Client>> asyncCallback);

	void saveCalendar(Calendar_Client calendarClient,
			Client_Workflow client_Workflow,
			AsyncCallback<Integer> asyncCallback);

	void updateCalendar(Calendar_Client calendarClient,
			Client_Workflow client_Workflow,
			AsyncCallback<Integer> asyncCallback);

	void deleteCalendar(Integer calendarid, AsyncCallback<String> asyncCallback);

	void getWorkflow(int calendarid,
			AsyncCallback<Client_Workflow> asyncCallback);

	/**
	 * get all events
	 */
	void getDoneCalendar(Integer userid,
			AsyncCallback<List<Calendar_Client>> asyncCallback);

	void getUndoneCalendar(Integer userid,
			AsyncCallback<List<Calendar_Client>> asyncCallback);

	/**
	 * get all records of an event
	 */
	void getRecords(Integer calendarid,
			AsyncCallback<List<Records_Client>> asyncCallback);

	/**
	 * Update a record means to add rating
	 */
	void updateRecords(Records_Client recordsClient,
			AsyncCallback<String> asyncCallback);

	void getCalendar(Integer id, AsyncCallback<Calendar_Client> callback);
}
