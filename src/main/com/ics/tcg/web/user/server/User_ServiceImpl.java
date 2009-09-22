package com.ics.tcg.web.user.server;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ics.tcg.web.database.UserDAO;
import com.ics.tcg.web.database.bean.User;
import com.ics.tcg.web.user.client.db.User_Client;
import com.ics.tcg.web.user.client.remote.User_Service;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class User_ServiceImpl extends RemoteServiceServlet implements
		User_Service {
	
	InitialContext ctx = null;
	UserDAO userdao = null;

	public User_ServiceImpl() {

		Properties props = new Properties();
		props.setProperty("java.naming.factory.initial",
				"org.jnp.interfaces.NamingContextFactory");
		props.setProperty("java.naming.provider.url", "localhost:1099");
		props.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
		try {
			ctx = new InitialContext(props);
			userdao = (UserDAO) ctx.lookup("UserDAOBean/remote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public User_Client getUser(int userid) {

		User user;
		user = userdao.getUserByID(userid);
		User_Client userItem = new User_Client();
		userItem.setAccount(user.getAccount());
		userItem.setAge(user.getAge());
		userItem.setBirthday(user.getBirthday());
		userItem.setEmail(user.getEmail());
		userItem.setPassword(user.getPassword());
		userItem.setSex(user.getSex());
		userItem.setTel(user.getTel());
		userItem.setUserid(user.getUserid());
		userItem.setBymail(user.isBymail());
		userItem.setBymobile(user.isBymobile());
		return userItem;

	}

	@Override
	public String saveUser(User_Client userC) {
		User user = new User();
		user.setUserid(userC.getUserid());
		user.setAccount(userC.getAccount());
		user.setAge(userC.getAge());
		user.setBirthday(userC.getBirthday());
		user.setBymail(userC.isBymail());
		user.setBymobile(userC.isBymobile());
		user.setEmail(userC.getEmail());
		user.setSex(userC.getSex());
		user.setTel(userC.getTel());
		user.setPassword(userC.getPassword());

		userdao.mergeUser(user);
		return "success";

	}

	@Override
	public String updateUser(User_Client userC) {
		User user = new User();
		user.setUserid(userC.getUserid());
		user.setAccount(userC.getAccount());
		user.setAge(userC.getAge());
		user.setBirthday(userC.getBirthday());
		user.setBymail(userC.isBymail());
		user.setBymobile(userC.isBymobile());
		user.setEmail(userC.getEmail());
		user.setSex(userC.getSex());
		user.setTel(userC.getTel());
		user.setPassword(userC.getPassword());

		userdao.mergeUser(user);
		return "success";
	}
}
