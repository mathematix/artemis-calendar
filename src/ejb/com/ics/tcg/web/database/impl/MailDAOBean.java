package com.ics.tcg.web.database.impl;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ics.tcg.web.database.MailDAO;
import com.ics.tcg.web.database.bean.Mail;

@Stateless
@Remote(MailDAO.class)
@SuppressWarnings("unchecked")
public class MailDAOBean implements MailDAO {
	@PersistenceContext(unitName = "database")
	protected EntityManager em;

	@Override
	public List<Mail> getMailByID(int userid) {
		Query query = em.createNativeQuery("select * from mail where userid ="
				+ userid + " order by senttime desc", Mail.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			return query.getResultList();
		} else {
			return null;
		}
	}

	@Override
	public Mail getOneMail(int id) {
		Query query = em.createNativeQuery("select * from mail where id =" + id
				+ "", Mail.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			return (Mail) query.getResultList().get(0);
		}
		return null;
	}

	@Override
	public Integer getUnreadMailCount(int userid) {
		Query query = em.createNativeQuery("select * from mail where userid=" + userid
				+ " and unread=" + true, Mail.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			return query.getResultList().size();
		}
		return 0;
	}

	@Override
	public void setRead(int id) {
		Query query = em.createNativeQuery("update mail set unread="
				+ false + " where id=" + id, Mail.class);
		query.executeUpdate();
	}
}