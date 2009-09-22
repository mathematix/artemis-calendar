package com.ics.tcg.web.database.impl;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ics.tcg.web.database.ServiceDAO;
import com.ics.tcg.web.database.bean.AbstractService;
import com.ics.tcg.web.database.bean.Service;
import com.ics.tcg.web.database.bean.User_Service;

@Stateless
@Remote(ServiceDAO.class)
public class ServiceDAOBean implements ServiceDAO {
	@PersistenceContext(unitName = "database")
	protected EntityManager em;

	@SuppressWarnings("unchecked")
	public List<AbstractService> getServices() {
		Query query = em.createNativeQuery("select * from abstractservice",
				AbstractService.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			return query.getResultList();
		} else {
			return null;
		}
	}

	public Integer SaveUserAbService(User_Service user_Service) {
		em.persist(user_Service);
		Query query = em.createNativeQuery(
				"select * from user_service where userid='"
						+ user_Service.getUserid() + "' and abserviceid='"
						+ user_Service.getAbserviceid() + "'",
				User_Service.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			User_Service us = (User_Service) query.getResultList().get(0);
			return us.getId();
		} else {
			return null;
		}
	}

	public void DeleteUserAbService(int userid, int asid) {
		Query query = em.createNativeQuery(
				"delete from user_service where userid='" + userid
						+ "' and abserviceid='" + asid + "'",
				User_Service.class);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<User_Service> getUserServices(int userid) {
		Query query = em.createNativeQuery(
				"select * from user_service where userid='" + userid + "'",
				User_Service.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			return query.getResultList();
		} else {
			return null;
		}
	}

	public AbstractService getServiceById(int asid) {
		Query query = em.createNativeQuery(
				"select * from abstractservice where asid='" + asid + "'",
				AbstractService.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			return (AbstractService) query.getResultList().get(0);
		} else {
			return null;
		}
	}

	public void UpdateUserAbservice(User_Service user_Service) {
		em.merge(user_Service);
	}

	public Service getRealService(Integer sid) {
		Query query = em.createNativeQuery("select * from service where sid='"
				+ sid + "'", Service.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			return (Service) query.getResultList().get(0);
		} else {
			return null;
		}
	}

	public Integer ifService(Integer userid, Integer abserviceid) {
		Query query = em.createNativeQuery(
				"select * from user_service where userid='" + userid
						+ "' and abserviceid='" + abserviceid + "'",
				User_Service.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			return 1;
		} else {
			return 0;
		}
	}

}