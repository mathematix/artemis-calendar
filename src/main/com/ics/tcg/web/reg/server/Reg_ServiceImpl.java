package com.ics.tcg.web.reg.server;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ics.tcg.web.database.UserDAO;
import com.ics.tcg.web.database.bean.User;
import com.ics.tcg.web.reg.client.Reg_Service;
import com.ics.tcg.web.reg.client.User_Client;

@SuppressWarnings("serial")
public class Reg_ServiceImpl extends RemoteServiceServlet implements
		Reg_Service {
	InitialContext ctx = null;
	UserDAO userdao = null;

	public Reg_ServiceImpl() {

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

	@Override
	public Integer check(String username) {
		int id = userdao.getIDByAccount(username);
		return id;
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
		user.setPassword(userC.password);

		userdao.insertUser(user);
		return "success";

	}
}
