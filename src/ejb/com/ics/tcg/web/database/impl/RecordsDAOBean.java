package com.ics.tcg.web.database.impl;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ics.tcg.web.database.RecordsDAO;
import com.ics.tcg.web.database.bean.Records;

@SuppressWarnings("unchecked")
@Stateless
@Remote(RecordsDAO.class)
public class RecordsDAOBean implements RecordsDAO {
	@PersistenceContext(unitName = "database")
	protected EntityManager em;

	public List<Records> getRecords(Integer calendarid) {
		Query query = em.createNativeQuery(
				"select * from records where calendarid=" + calendarid + "",
				Records.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			return query.getResultList();
		} else {
			return null;
		}

	}

	public void updateRecord(Records records) {
		em.merge(records);
	}
}
