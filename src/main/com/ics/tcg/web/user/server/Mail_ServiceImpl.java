package com.ics.tcg.web.user.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ics.tcg.web.database.MailDAO;
import com.ics.tcg.web.database.bean.Mail;
import com.ics.tcg.web.user.client.db.Mail_Client;
import com.ics.tcg.web.user.client.remote.Mail_Service;

@SuppressWarnings( { "serial" })
public class Mail_ServiceImpl extends RemoteServiceServlet implements
		Mail_Service {

	@Override
	public List<Mail_Client> get_Mail(Integer userid) {
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

		MailDAO mailDAO = null;
		try {
			mailDAO = (MailDAO) ctx.lookup("MailDAOBean/remote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		ArrayList<Mail_Client> mailClients = new ArrayList<Mail_Client>();
		List<Mail> mails = mailDAO.getMailByID(userid);
		if (mails != null) {
			for (int i = 0; i < mails.size(); i++) {
				Mail_Client temp = new Mail_Client();
				temp.setId(mails.get(i).getId());
				temp.setUserid(mails.get(i).getUserid());
				temp.setHead(mails.get(i).getHead());
				// temp.setContent(mails.get(i).getContent());
				temp.setSenttime(mails.get(i).getSenttime());
				temp.setSender(mails.get(i).getSender());
				temp.setUnread(mails.get(i).isUnread());
				mailClients.add(temp);
			}
			return mailClients;
		} else {
			return null;
		}
	}

	@Override
	public Mail_Client getContent(Integer id) {
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
			MailDAO mailDAO = (MailDAO) ctx.lookup("MailDAOBean/remote");
			Mail mail = mailDAO.getOneMail(id);
			Mail_Client mailClient = new Mail_Client();
			mailClient.setHead(mail.getHead());
			mailClient.setId(mail.getId());
			mailClient.setSender(mail.getSender());
			mailClient.setSenttime(mail.getSenttime());
			mailClient.setUserid(mail.getUserid());
			mailClient.setContent(mail.getContent());
			mailClient.setUnread(mail.isUnread());

			return mailClient;
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Integer getUnreadMailCount(Integer userid) {
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
			MailDAO mailDAO = (MailDAO) ctx.lookup("MailDAOBean/remote");
			return (Integer) mailDAO.getUnreadMailCount(userid);
		} catch (NamingException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public void setRead(Integer id) {
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
			MailDAO mailDAO = (MailDAO) ctx.lookup("MailDAOBean/remote");
			mailDAO.setRead(id);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}
