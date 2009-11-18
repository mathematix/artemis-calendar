package com.ics.tcg.web.database.impl;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ics.tcg.web.database.ThirdPartDAO;
import com.ics.tcg.web.database.bean.Issuer;
import com.ics.tcg.web.database.bean.ThirdPart;
import com.ics.tcg.web.database.bean.User_Issuer;
import com.ics.tcg.web.database.bean.User_ThirdPart;

@SuppressWarnings("unchecked")
@Stateless
@Remote(ThirdPartDAO.class)
public class ThirdPartDAOBean implements ThirdPartDAO {
	@PersistenceContext(unitName = "database")
	protected EntityManager em;

	@Override
	public void deleteUser_Issuer(Integer userid, Integer issuerid) {
		Query query = em.createNativeQuery(
				"select * from user_issuer where userid = '" + userid
						+ "' and issuerid='" + issuerid + "'", User_Issuer.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0)
			em.remove(query.getResultList().get(0));
	}

	@Override
	public void deleteUser_TP(Integer userid, Integer tpid) {
		Query query = em.createNativeQuery(
				"select * from user_thirdpart where userid = '" + userid
						+ "' and thirdpartid='" + tpid + "'", User_ThirdPart.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0)
			em.remove(query.getResultList().get(0));
	}

	@Override
	public Issuer getIssuerByID(Integer issuerid) {
		return em.find(Issuer.class, issuerid);
	}

	@Override
	public List<Issuer> getIssuers() {
		Query query = em
				.createNativeQuery("select * from issuer", Issuer.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			return query.getResultList();
		} else {
			return null;
		}
	}

	@Override
	public List<ThirdPart> getTPs() {
		Query query = em.createNativeQuery("select * from thirdpart",
				ThirdPart.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			return query.getResultList();
		} else {
			return null;
		}
	}

	@Override
	public ThirdPart getThirdPartByID(Integer tpid) {
		return em.find(ThirdPart.class, tpid);
	}

	@Override
	public List<User_Issuer> getUser_Issuers(Integer userid) {
		Query query = em.createNativeQuery("select * from user_issuer where userid ="+userid,
				User_Issuer.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			return query.getResultList();
		} else {
			return null;
		}
	}

	@Override
	public List<User_ThirdPart> getUser_TPs(Integer userid) {
		Query query = em.createNativeQuery("select * from user_thirdpart where userid ="+userid,
				User_ThirdPart.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			return query.getResultList();
		} else {
			return null;
		}
	}

	@Override
	public Integer ifUser_Issuer(Integer userid, Integer issuerid) {
		Query query = em.createNativeQuery(
				"select * from user_issuer where userid='" + userid
						+ "' and issuerid='" + issuerid + "'",
				User_Issuer.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public Integer saveUser_Issuer(User_Issuer userIssuer) {
		em.persist(userIssuer);
		Query query = em.createNativeQuery(
				"select * from user_issuer where userid='"
						+ userIssuer.getUserid() + "' and issuerid='"
						+ userIssuer.getIssuerid() + "'",
				User_Issuer.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			User_Issuer ui = (User_Issuer) query.getResultList().get(0);
			return ui.getId();
		} else {
			return null;
		}
	}

	@Override
	public Integer saveUser_TP(User_ThirdPart userThirdPart) {
		em.persist(userThirdPart);
		Query query = em.createNativeQuery(
				"select * from user_thirdpart where userid='"
						+ userThirdPart.getUserid() + "' and thirdpartid='"
						+ userThirdPart.getThirdpartid() + "'",
				User_ThirdPart.class);
		if (query != null && query.getResultList() != null
				&& query.getResultList().size() != 0) {
			User_ThirdPart ui = (User_ThirdPart) query.getResultList().get(0);
			return ui.getId();
		} else {
			return null;
		}
	}

	@Override
	public void updateUser_Issuer(User_Issuer userIssuer) {
		em.merge(userIssuer);
	}

	@Override
	public void updateUser_TP(User_ThirdPart userThirdPart) {
		em.merge(userThirdPart);
	}

}