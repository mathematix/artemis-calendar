package com.ics.tcg.web.database.impl;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ics.tcg.web.database.FriendDAO;
import com.ics.tcg.web.database.bean.Friends;

@Stateless
@Remote(FriendDAO.class)
public class FriendDAOBean implements FriendDAO {
	@PersistenceContext(unitName = "database")
	protected EntityManager em;

	@SuppressWarnings("unchecked")
	// get friends
	public List<Friends> getFriendByID(int userid) {
		Query query = em.createQuery("select o from Friends o where o.userid='"
				+ userid + "'");

		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			return query.getResultList();
		} else {
			return null;
		}
	}

	// delete a friend
	public void deleteFriend(int userid, int friendid) {
		Query query = em.createNativeQuery(
				"select * from friends where userid = '" + userid
						+ "' and friendid='" + friendid + "'", Friends.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0)
			em.remove(query.getResultList().get(0));
	}

	// add a friend
	public Friends saveFriend(Friends friends) {
		em.persist(friends);
		Query query = em.createNativeQuery(
				"select * from friends where userid = '" + friends.getUserid()
						+ "' and friendid='" + friends.getFriendid() + "'",
				Friends.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			return (Friends) query.getResultList().get(0);
		} else {
			return null;
		}
	}

	// check if the relationship is in db
	public int isFriend(int userid, int friendid) {
		Query query = em.createNativeQuery(
				"select * from friends where userid = '" + userid
						+ "' and friendid='" + friendid + "'", Friends.class);
		if (query.getResultList().size() == 0)
			return 0;
		else
			return 1;
	}

}