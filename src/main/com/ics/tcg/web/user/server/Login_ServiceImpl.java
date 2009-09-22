package com.ics.tcg.web.user.server;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ics.tcg.web.database.UserDAO;
import com.ics.tcg.web.user.client.remote.Login_Service;

@SuppressWarnings("serial")
public class Login_ServiceImpl extends RemoteServiceServlet implements
		Login_Service {

	
	@Override
	public Integer Valid_Login(String name, String password) {

		Integer validate = -1;

		// ///////////////////////////////////////////////////////////////////////////////
		//
		// try {
		// InitialContext ctx = new InitialContext();
		// UserDAO userdao = (UserDAO) ctx.lookup("UserDAOBean/remote");
		// validate = userdao.ValidateUser(req.getParameter("account"),
		// req.getParameter("password"));
		// } catch (NamingException e) {
		// e.printStackTrace();
		// }
		//
		// //////////////////////////////////////////////////////////////////////////////
		InitialContext ctx = null;
		Properties props = new Properties();
		{
			props.setProperty("java.naming.factory.initial",
					"org.jnp.interfaces.NamingContextFactory");
			props.setProperty("java.naming.provider.url", "localhost:1099");
			props.setProperty("java.naming.factory.url.pkgs",
					"org.jboss.naming");
		}
		try {
			ctx = new InitialContext(props);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		UserDAO userdao = null;
		try {
			userdao = (UserDAO) ctx.lookup("UserDAOBean/remote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		validate = userdao.ValidateUser(name, password);
		return validate;
	}

}
