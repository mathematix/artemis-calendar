package com.ics.tcg.web.user.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ics.tcg.web.database.CalendarEventDAO;
import com.ics.tcg.web.database.RecordsDAO;
import com.ics.tcg.web.database.ServiceDAO;
import com.ics.tcg.web.database.bean.CalendarEvent;
import com.ics.tcg.web.database.bean.Calendar_Workflow;
import com.ics.tcg.web.database.bean.Records;
import com.ics.tcg.web.database.bean.Service;
import com.ics.tcg.web.user.client.db.Calendar_Client;
import com.ics.tcg.web.user.client.db.Records_Client;
import com.ics.tcg.web.user.client.remote.Calendar_Service;
import com.ics.tcg.web.workflow.client.data.Client_Workflow;

@SuppressWarnings( { "serial" })
public class Calendar_ServiceImpl extends RemoteServiceServlet implements
		Calendar_Service {

	InitialContext ctx;
	Properties props;
	CalendarEventDAO calendarEventDAO;
	RecordsDAO recordsDAO;
	ServiceDAO serviceDAO;

	public Calendar_ServiceImpl() {

		props = new Properties();
		{
			props.setProperty("java.naming.factory.initial",
					"org.jnp.interfaces.NamingContextFactory");
			props.setProperty("java.naming.provider.url", "localhost:1099");
			props.setProperty("java.naming.factory.url.pkgs",
					"org.jboss.naming");
		}
		try {
			ctx = new InitialContext(props);
			calendarEventDAO = (CalendarEventDAO) ctx
					.lookup("CalendarEventDAOBean/remote");
			recordsDAO = (RecordsDAO) ctx.lookup("RecordsDAOBean/remote");
			serviceDAO = (ServiceDAO) ctx.lookup("ServiceDAOBean/remote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String deleteCalendar(Integer calendarid) {
		calendarEventDAO.deleteCalendar(calendarid);
		calendarEventDAO.deleteWorkflow(calendarid);
		return "success";
	}

	@Override
	public List<Calendar_Client> getAllCalendars(int userid) {
		List<CalendarEvent> calendarEvents = calendarEventDAO
				.getCalendar(userid);
		ArrayList<Calendar_Client> calendarClients = new ArrayList<Calendar_Client>();
		if (calendarEvents != null && calendarEvents.size() != 0) {
			for (int i = 0; i < calendarEvents.size(); i++) {
				Calendar_Client temp = new Calendar_Client();
				temp.setCalendarid(calendarEvents.get(i).getCalendarid());
				temp.setDes(calendarEvents.get(i).getDes());
				temp.setDone(calendarEvents.get(i).getDone());
				temp.setEndTime(calendarEvents.get(i).getEndtime());
				temp.setEventname(calendarEvents.get(i).getEventname());
				temp.setSearchstartTime(calendarEvents.get(i)
						.getSearchstarttime());
				temp.setStartTime(calendarEvents.get(i).getStarttime());
				temp.setUserid(calendarEvents.get(i).getUserid());
				temp.setLocked(calendarEvents.get(i).isLocked());
				calendarClients.add(temp);
			}
			return calendarClients;
		} else {
			return null;
		}
	}

	@Override
	public List<Calendar_Client> getUndoneCalendar(Integer userid) {
		List<CalendarEvent> calendarEvents = calendarEventDAO
				.getUndoneCalendar(userid);
		ArrayList<Calendar_Client> calendarClients = new ArrayList<Calendar_Client>();
		if (calendarEvents != null) {
			for (int i = 0; i < calendarEvents.size(); i++) {
				Calendar_Client temp = new Calendar_Client();
				temp.setCalendarid(calendarEvents.get(i).getCalendarid());
				temp.setDes(calendarEvents.get(i).getDes());
				temp.setDone(calendarEvents.get(i).getDone());
				temp.setEndTime(calendarEvents.get(i).getEndtime());
				temp.setEventname(calendarEvents.get(i).getEventname());
				temp.setSearchstartTime(calendarEvents.get(i)
						.getSearchstarttime());
				temp.setStartTime(calendarEvents.get(i).getStarttime());
				temp.setUserid(calendarEvents.get(i).getUserid());
				temp.setLocked(calendarEvents.get(i).isLocked());
				calendarClients.add(temp);
			}
			return calendarClients;
		} else {
			return null;
		}
	}

	@Override
	public List<Calendar_Client> getDoneCalendar(Integer userid) {
		List<CalendarEvent> calendarEvents = calendarEventDAO
				.getDoneCalendar(userid);
		ArrayList<Calendar_Client> calendarClients = new ArrayList<Calendar_Client>();
		if (calendarEvents != null) {
			for (int i = 0; i < calendarEvents.size(); i++) {
				Calendar_Client temp = new Calendar_Client();
				temp.setCalendarid(calendarEvents.get(i).getCalendarid());
				temp.setDes(calendarEvents.get(i).getDes());
				temp.setDone(calendarEvents.get(i).getDone());
				temp.setEndTime(calendarEvents.get(i).getEndtime());
				temp.setEventname(calendarEvents.get(i).getEventname());
				temp.setSearchstartTime(calendarEvents.get(i)
						.getSearchstarttime());
				temp.setStartTime(calendarEvents.get(i).getStarttime());
				temp.setUserid(calendarEvents.get(i).getUserid());
				temp.setLocked(calendarEvents.get(i).isLocked());
				calendarClients.add(temp);
			}
			return calendarClients;
		} else {
			return null;
		}
	}

	@Override
	public Client_Workflow getWorkflow(int calendarid) {

		Calendar_Workflow calendarWorkflow = calendarEventDAO
				.getWorkflow(calendarid);
		if (calendarWorkflow != null && calendarWorkflow.getWorkflow() != null) {
			try {
				return (Client_Workflow) Byte_Object
						.ByteToObject(calendarWorkflow.getWorkflow());
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public Integer saveCalendar(Calendar_Client calendarClient,
			Client_Workflow clientWorkflow) {
		CalendarEvent calendarEvent = new CalendarEvent();

		calendarEvent.setDes(calendarClient.getDes());
		calendarEvent.setDone(calendarClient.getDone());
		calendarEvent.setEndtime(calendarClient.getEndTime());
		calendarEvent.setEventname(calendarClient.getEventname());
		calendarEvent.setSearchstarttime(calendarClient.getSearchstartTime());
		calendarEvent.setStarttime(calendarClient.getStartTime());
		calendarEvent.setUserid(calendarClient.getUserid());
		calendarEvent.setLocked(calendarClient.isLocked());

		Integer calendarid = calendarEventDAO.insertCalendar(calendarEvent);

		Calendar_Workflow calendarWorkflow = new Calendar_Workflow();
		calendarWorkflow.setCalendarid(calendarid);
		try {
			if (clientWorkflow != null) {
				calendarWorkflow.setWorkflow(Byte_Object
						.ObjectToByte(clientWorkflow));
			} else {
				calendarWorkflow.setWorkflow(null);
			}
			calendarEventDAO.insertWorkflow(calendarWorkflow);
			return calendarid;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Integer updateCalendar(Calendar_Client calendarClient,
			Client_Workflow clientWorkflow) {
		CalendarEvent calendarEvent = new CalendarEvent();

		calendarEvent.setCalendarid(calendarClient.getCalendarid());
		calendarEvent.setDes(calendarClient.getDes());
		calendarEvent.setDone(calendarClient.getDone());
		calendarEvent.setEndtime(calendarClient.getEndTime());
		calendarEvent.setEventname(calendarClient.getEventname());
		calendarEvent.setSearchstarttime(calendarClient.getSearchstartTime());
		calendarEvent.setStarttime(calendarClient.getStartTime());
		calendarEvent.setUserid(calendarClient.getUserid());
		calendarEvent.setLocked(calendarClient.isLocked());

		calendarEventDAO.mergeCalendar(calendarEvent);

		Calendar_Workflow calendarWorkflow = new Calendar_Workflow();
		calendarWorkflow.setCalendarid(calendarEvent.getCalendarid());

		try {
			if (clientWorkflow != null) {
				calendarWorkflow.setWorkflow(Byte_Object
						.ObjectToByte(clientWorkflow));
			} else {
				calendarWorkflow.setWorkflow(null);
			}
			calendarEventDAO.mergeWorkflow(calendarWorkflow);
			return calendarWorkflow.getCalendarid();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Records_Client> getRecords(Integer calendarid) {
		List<Records> list = recordsDAO.getRecords(calendarid);
		if (list != null && list.size() != 0) {
			List<Records_Client> rc = new ArrayList<Records_Client>();
			for (int i = 0; i < list.size(); i++) {
				Records_Client temp = new Records_Client();
				temp.setCalendarid(list.get(i).getCalendarid());
				temp.setCid(list.get(i).getCid());
				temp.setRating(list.get(i).getRating());
				temp.setRecordid(list.get(i).getRecordid());
				temp.setRestime(list.get(i).getRestime());
				temp.setSid(list.get(i).getSid());
				temp.setSuccess(list.get(i).isSuccess());
				temp.setUserid(list.get(i).getUserid());

				Service service = serviceDAO.getRealService(list.get(i)
						.getSid());
				temp.setAbservicename(service.getAbservicename());
				temp.setCompanyname(service.getBusinessName());
				temp.setServicename(service.getServiceName());
				rc.add(temp);
			}
			return rc;
		} else {
			return null;
		}
	}

	@Override
	public String updateRecords(Records_Client recordsClient) {
		Records records = new Records();
		records.setCalendarid(recordsClient.getCalendarid());
		records.setCid(recordsClient.getCid());
		records.setRating(recordsClient.getRating());
		records.setRecordid(recordsClient.getRecordid());
		records.setRestime(recordsClient.getRestime());
		records.setSid(recordsClient.getSid());
		records.setSuccess(recordsClient.isSuccess());
		records.setUserid(recordsClient.getUserid());
		recordsDAO.updateRecord(records);
		return "success";
	}

	@Override
	public Calendar_Client getCalendar(Integer id) {
		CalendarEvent calendarEvent = calendarEventDAO.getCalendarByID(id);
		if (calendarEvent!=null) {
			Calendar_Client temp = new Calendar_Client();
			temp.setCalendarid(calendarEvent.getCalendarid());
			temp.setDes(calendarEvent.getDes());
			temp.setDone(calendarEvent.getDone());
			temp.setEndTime(calendarEvent.getEndtime());
			temp.setEventname(calendarEvent.getEventname());
			temp.setSearchstartTime(calendarEvent.getSearchstarttime());
			temp.setStartTime(calendarEvent.getStarttime());
			temp.setUserid(calendarEvent.getUserid());
			temp.setLocked(calendarEvent.isLocked());
			
			return temp;
		}
		else {
			return null;
		}
	}
}
