package com.ics.tcg.web.database.impl;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ics.tcg.web.database.UserDAO;
import com.ics.tcg.web.database.bean.User;

@Stateless
@Remote(UserDAO.class)
public class UserDAOBean implements UserDAO {
	@PersistenceContext(unitName = "database")
	protected EntityManager em;

	public void updateName(String newname, int userid) {
		User user = em.find(User.class, userid);
		if (user != null)
			user.setAccount(newname);
	}

	public void deleteUser(int userid) {
		User user = em.find(User.class, userid);
		if (user != null)
			em.remove(user);
	}

	public User getUserByID(int userid) {
		return em.find(User.class, userid);
	}

	@SuppressWarnings("unchecked")
	public List<User> getUserList() {
		Query query = em
				.createQuery("select o from User o order by o.userid asc");
		return query.getResultList();
	}

	public void insertUser(User user) {
		em.persist(user);
	}

	public void mergeUser(User user) {
		em.merge(user);
	}

	@SuppressWarnings("unchecked")
	public int ValidateUser(String account, String password) {
		Query query = em.createQuery("select o from User o where o.account='"
				+ account + "' and o.password='" + password + "'");
		if (query.getResultList().size() == 0)
			return -1;
		else {
			List<User> list = query.getResultList();
			return list.get(0).getUserid();
		}
	}

	@SuppressWarnings("unchecked")
	public int getIDByAccount(String account) {
		Query query = em.createNativeQuery(
				"select * from User where account = '" + account + "'",
				User.class);
		if (query.getResultList().size() == 0)
			return -1;
		List<User> list = query.getResultList();
		return list.get(0).getUserid();
	}

	public User getUserByAccount(String account) {
		Query query = em.createNativeQuery(
				"select * from User where account = '" + account + "'",
				User.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			User user = (User) query.getResultList().get(0);
			return user;
		} else {
			return null;
		}
	}
}
