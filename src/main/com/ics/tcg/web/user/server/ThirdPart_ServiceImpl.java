package com.ics.tcg.web.user.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ics.tcg.web.database.ThirdPartDAO;
import com.ics.tcg.web.database.bean.Issuer;
import com.ics.tcg.web.database.bean.ThirdPart;
import com.ics.tcg.web.database.bean.User_Issuer;
import com.ics.tcg.web.database.bean.User_ThirdPart;
import com.ics.tcg.web.user.client.db.Issuer_Client;
import com.ics.tcg.web.user.client.db.ThirdPart_Client;
import com.ics.tcg.web.user.client.db.User_Issuer_Client;
import com.ics.tcg.web.user.client.db.User_ThirdPart_Client;
import com.ics.tcg.web.user.client.remote.ThirdPart_Service;

@SuppressWarnings("serial")
public class ThirdPart_ServiceImpl extends RemoteServiceServlet implements
		ThirdPart_Service {

	InitialContext ctx;
	Properties props;
	ThirdPartDAO thirdPartDAO;

	public ThirdPart_ServiceImpl() {
		props = new Properties();
		props.setProperty("java.naming.factory.initial",
				"org.jnp.interfaces.NamingContextFactory");
		props.setProperty("java.naming.provider.url", "localhost:1099");
		props.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
		try {
			ctx = new InitialContext(props);
			thirdPartDAO = (ThirdPartDAO) ctx.lookup("ThirdPartDAOBean/remote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteUser_Issuer(Integer userid, Integer issuerid) {
		thirdPartDAO.deleteUser_Issuer(userid, issuerid);
	}

	@Override
	public void deleteUser_TP(Integer userid, Integer tpid) {
		thirdPartDAO.deleteUser_TP(userid, tpid);
	}

	@Override
	public Issuer_Client getIssuerByID(Integer issuerid) {
		Issuer issuer = thirdPartDAO.getIssuerByID(issuerid);
		Issuer_Client issuerClient = new Issuer_Client();
		issuerClient.setIssuerid(issuer.getIssuerid());
		issuerClient.setIssuername(issuer.getIssuername());
		return issuerClient;
	}

	@Override
	public List<Issuer_Client> getIssuers() {
		List<Issuer> issuers = thirdPartDAO.getIssuers();
		ArrayList<Issuer_Client> issuerClients = new ArrayList<Issuer_Client>();
		for (int i = 0; i < issuers.size(); i++) {
			Issuer_Client ic = new Issuer_Client();
			ic.setIssuerid(issuers.get(i).getIssuerid());
			ic.setIssuername(issuers.get(i).getIssuername());
			issuerClients.add(ic);
		}
		return issuerClients;
	}

	@Override
	public List<ThirdPart_Client> getTPs() {
		List<ThirdPart> thirdParts = thirdPartDAO.getTPs();
		ArrayList<ThirdPart_Client> thirdPartClients = new ArrayList<ThirdPart_Client>();
		for (int i = 0; i < thirdParts.size(); i++) {
			ThirdPart_Client tc = new ThirdPart_Client();
			tc.setTpid(thirdParts.get(i).getTpid());
			tc.setTpname(thirdParts.get(i).getTpname());
			thirdPartClients.add(tc);
		}
		return thirdPartClients;
	}

	@Override
	public ThirdPart_Client getThirdPartByID(Integer tpid) {
		ThirdPart thirdPart = thirdPartDAO.getThirdPartByID(tpid);
		ThirdPart_Client thirdPartClient = new ThirdPart_Client();
		thirdPartClient.setTpid(thirdPart.getTpid());
		thirdPartClient.setTpname(thirdPart.getTpname());
		return thirdPartClient;
	}

	@Override
	public List<User_Issuer_Client> getUser_Issuers(Integer userid) {
		List<User_Issuer> userIssuers = thirdPartDAO.getUser_Issuers(userid);
		List<User_Issuer_Client> userIssuerClients = new ArrayList<User_Issuer_Client>();
		for (int i = 0; i < userIssuers.size(); i++) {
			User_Issuer_Client u = new User_Issuer_Client();
			u.setId(userIssuers.get(i).getId());
			u.setIssuerid(userIssuers.get(i).getIssuerid());
			u.setUserid(userIssuers.get(i).getUserid());
			u
					.setIssuename(getIssuerByID(
							userIssuers.get(i).getIssuerid())
							.getIssuername());
			userIssuerClients.add(u);
		}
		return userIssuerClients;
	}

	@Override
	public List<User_ThirdPart_Client> getUser_TPs(Integer userid) {
		List<User_ThirdPart> userThirdParts = thirdPartDAO.getUser_TPs(userid);
		List<User_ThirdPart_Client> userThirdPartClients = new ArrayList<User_ThirdPart_Client>();
		for (int i = 0; i < userThirdParts.size(); i++) {
			User_ThirdPart_Client utc = new User_ThirdPart_Client();
			utc.setId(userThirdParts.get(i).getId());
			utc.setThirdpartid(userThirdParts.get(i).getThirdpartid());
			utc.setTrust(userThirdParts.get(i).getTrust());
			utc.setUserid(userThirdParts.get(i).getUserid());
			utc.setThirdpartname(getThirdPartByID(
					userThirdParts.get(i).getThirdpartid()).getTpname());
			userThirdPartClients.add(utc);
		}
		return userThirdPartClients;
	}

	@Override
	public Integer ifUser_Issuer(Integer userid, Integer issuerid) {
		return thirdPartDAO.ifUser_Issuer(userid, issuerid);
	}

	@Override
	public Integer saveUser_Issuer(User_Issuer_Client userIssuer) {
		User_Issuer user_issuer = new User_Issuer();
		user_issuer.setIssuerid(userIssuer.getIssuerid());
		user_issuer.setUserid(userIssuer.getUserid());
		return thirdPartDAO.saveUser_Issuer(user_issuer);
	}

	@Override
	public Integer saveUser_TP(User_ThirdPart_Client userThirdPart) {
		User_ThirdPart user_thirdpart = new User_ThirdPart();
		user_thirdpart.setThirdpartid(userThirdPart.getThirdpartid());
		user_thirdpart.setTrust(userThirdPart.getTrust());
		user_thirdpart.setUserid(userThirdPart.getUserid());
		return thirdPartDAO.saveUser_TP(user_thirdpart);
	}

	@Override
	public void updateUser_Issuer(User_Issuer_Client userIssuer) {
		User_Issuer user_issuer = new User_Issuer();
		user_issuer.setId(userIssuer.getId());
		user_issuer.setIssuerid(userIssuer.getIssuerid());
		user_issuer.setUserid(userIssuer.getUserid());
		thirdPartDAO.updateUser_Issuer(user_issuer);
	}

	@Override
	public void updateUser_TP(User_ThirdPart_Client userThirdPart) {
		User_ThirdPart user_thirdpart = new User_ThirdPart();
		user_thirdpart.setId(userThirdPart.getId());
		user_thirdpart.setThirdpartid(userThirdPart.getThirdpartid());
		user_thirdpart.setTrust(userThirdPart.getTrust());
		user_thirdpart.setUserid(userThirdPart.getUserid());
		thirdPartDAO.updateUser_TP(user_thirdpart);		
	}

}
