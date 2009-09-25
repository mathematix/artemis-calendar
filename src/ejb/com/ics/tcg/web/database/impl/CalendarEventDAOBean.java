package com.ics.tcg.web.database.impl;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ics.tcg.web.database.CalendarEventDAO;
import com.ics.tcg.web.database.bean.CalendarEvent;
import com.ics.tcg.web.database.bean.Calendar_Workflow;

@SuppressWarnings("unchecked")
@Stateless
@Remote(CalendarEventDAO.class)
public class CalendarEventDAOBean implements CalendarEventDAO {
	@PersistenceContext(unitName = "database")
	protected EntityManager em;

	// get the specified activity
	public CalendarEvent get(Integer userid, Integer calendarid) {
		Query query = em
				.createQuery("select o from calendar o where o.calendarid='"
						+ calendarid + "'");
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			return (CalendarEvent) query.getResultList();
		} else {
			return null;
		}
	}

	// merge an activity
	public void mergeCalendar(CalendarEvent calendarActivity) {
		em.merge(calendarActivity);
	}

	// get all calendar events
	public List<CalendarEvent> getCalendar(Integer userid) {
		Query query = em.createNativeQuery(
				"select * from calendar where userid='" + userid + "'",
				CalendarEvent.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			return query.getResultList();
		} else {
			return null;
		}
	}

	// insert an event into database
	public int insertCalendar(CalendarEvent calendarActivity) {
		em.persist(calendarActivity);
		return calendarActivity.getCalendarid();
	}

	public void deleteCalendar(int calendarid) {
		CalendarEvent cal = em.find(CalendarEvent.class, calendarid);
		if (cal != null)
			em.remove(cal);
	}

	//
	public void deleteWorkflow(int calendarid) {
		Calendar_Workflow cw = em.find(Calendar_Workflow.class, calendarid);
		if (cw != null) {
			em.remove(cw);
		}
	}

	public Calendar_Workflow getWorkflow(Integer calendarid) {
		Query query = em.createNativeQuery(
				"select * from calendar_workflow where calendarid="
						+ calendarid + "", Calendar_Workflow.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			Calendar_Workflow workflow = (Calendar_Workflow) query
					.getResultList().get(0);
			return workflow;
		} else {
			return null;
		}
	}

	public void insertWorkflow(Calendar_Workflow cw) {
		em.persist(cw);
	}

	public void mergeWorkflow(Calendar_Workflow cw) {
		em.merge(cw);

	}

	/**
	 * get calendarevent that has been done
	 * */
	public List<CalendarEvent> getDoneCalendar(Integer userid) {
		Query query = em.createNativeQuery(
				"select * from calendar where userid=" + userid + " and done="
						+ true + "", CalendarEvent.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			return query.getResultList();
		} else {
			return null;
		}
	}

	/**
	 * get calendarevent that has not been done yet
	 * */
	public List<CalendarEvent> getUndoneCalendar(Integer userid) {
		Query query = em.createNativeQuery(
				"select * from calendar where userid=" + userid + " and done="
						+ false + "", CalendarEvent.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			return query.getResultList();
		} else {
			return null;
		}
	}

	/**
	 * get a calendar by id
	 * 
	 * @param id
	 */
	@Override
	public CalendarEvent getCalendarByID(Integer id) {
		CalendarEvent calendarEvent = em.find(CalendarEvent.class, id);
		if (calendarEvent != null) {
			return calendarEvent;
		} else {
			return null;
		}
	}
}
