package com.ics.tcg.web.user.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ics.tcg.web.database.ServiceDAO;
import com.ics.tcg.web.database.bean.AbstractService;
import com.ics.tcg.web.database.bean.User_Service;
import com.ics.tcg.web.user.client.db.AbstractService_Client;
import com.ics.tcg.web.user.client.db.User_Service_Client;
import com.ics.tcg.web.user.client.qos.ServiceQosRequirement;
import com.ics.tcg.web.user.client.qos.UIContent;
import com.ics.tcg.web.user.client.remote.Service_Service;

@SuppressWarnings("serial")
public class Service_ServiceImpl extends RemoteServiceServlet implements
		Service_Service {

	InitialContext ctx;
	Properties props;
	ServiceDAO serviceDAO;

	public Service_ServiceImpl() {
		props = new Properties();
		props.setProperty("java.naming.factory.initial",
				"org.jnp.interfaces.NamingContextFactory");
		props.setProperty("java.naming.provider.url", "localhost:1099");
		props.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
		try {
			ctx = new InitialContext(props);
			serviceDAO = (ServiceDAO) ctx.lookup("ServiceDAOBean/remote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	// get all services
	@Override
	public List<AbstractService_Client> getAllAbservices() {
		List<AbstractService_Client> servicesC = new ArrayList<AbstractService_Client>();
		List<AbstractService> services = new ArrayList<AbstractService>();
		services = serviceDAO.getServices();
		if (services != null) {
			for (int i = 0; i < services.size(); i++) {
				AbstractService_Client abstractServiceC = new AbstractService_Client();
				abstractServiceC.setAsid(services.get(i).getAsid());
				abstractServiceC.setAsname(services.get(i).getAsname());
				abstractServiceC.setDes(services.get(i).getDes());
				servicesC.add(abstractServiceC);
			}
			return servicesC;
		} else {
			return null;
		}
	}

	// get a user's services
	@Override
	public List<User_Service_Client> getUserAbservices(int userid) {
		List<User_Service_Client> user_servicesC = new ArrayList<User_Service_Client>();
		List<User_Service> user_services = new ArrayList<User_Service>();
		user_services = serviceDAO.getUserServices(userid);
		if (user_services != null) {
			for (int i = 0; i < user_services.size(); i++) {
				User_Service_Client user_Service = new User_Service_Client();
				user_Service.setId(user_services.get(i).getId());
				user_Service.setAbserviceid(user_services.get(i)
						.getAbserviceid());
				user_Service.setUserid(user_services.get(i).getUserid());
				user_Service.setAbservicename(user_services.get(i)
						.getAbservicename());
				if (user_services.get(i).getQos() != null) {
					try {
						user_Service.setQos((ServiceQosRequirement) Byte_Object
								.ByteToObject(user_services.get(i).getQos()));
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					user_Service.setQos( null);
				}
				user_servicesC.add(user_Service);
			}
			return user_servicesC;
		} else {
			return null;
		}

	}

	// delete a user's service
	@Override
	public String deleteUserAbservice(int userid, int asid) {
		serviceDAO.DeleteUserAbService(userid, asid);
		return "success";
	}

	// save a user's service
	@Override
	public User_Service_Client saveUserAbservice(int userid, int asid,
			String name) {
		User_Service user_Service = new User_Service();
		user_Service.setAbserviceid(asid);
		user_Service.setUserid(userid);
		user_Service.setAbservicename(name);
		Integer i = serviceDAO.SaveUserAbService(user_Service);
		user_Service.setId(i);

		User_Service_Client userServiceClient = new User_Service_Client();
		userServiceClient.setId(user_Service.getId());
		userServiceClient.setAbserviceid(user_Service.getAbserviceid());
		userServiceClient.setAbservicename(user_Service.getAbservicename());
		userServiceClient.setUserid(user_Service.getUserid());
		userServiceClient.setQos(null);

		return userServiceClient;
	}

	// get qos config from xml
	@Override
	public UIContent getContent(User_Service_Client user_service) {
		UIContent content;
		QosRequirementFrameworkXmlParse parse = null;
		if (user_service.getAbservicename().contains("Service")) {
			parse = new QosRequirementFrameworkXmlParse(
					"xml/QosRequirementFramework_"
							+ user_service.getAbservicename().substring(0,
									user_service.getAbservicename()
											.lastIndexOf("Service")) + ".xml");
		} else {
			parse = new QosRequirementFrameworkXmlParse(
					"xml/QosRequirementFramework_" + user_service.getAbservicename()
							+ ".xml");
		}
		parse.startParse();
		content = parse.getUIContent();
		return content;
	}

	// save the qos value
	@Override
	public String saveQos(User_Service_Client user_ServiceC) {
		User_Service user_Service = new User_Service();

		user_Service.setId(user_ServiceC.getId());
		user_Service.setUserid(user_ServiceC.getUserid());
		user_Service.setAbserviceid(user_ServiceC.getAbserviceid());
		user_Service.setAbservicename(user_ServiceC.getAbservicename());
		try {
			user_Service.setQos(Byte_Object.ObjectToByte(user_ServiceC.getQos()));
			serviceDAO.UpdateUserAbservice(user_Service);
			return "success";
		} catch (IOException e) {
			e.printStackTrace();
			return "fail";
		}
	}

	@Override
	public Integer check(Integer userid, Integer abserviceid) {
		return serviceDAO.ifService(userid, abserviceid);
	}

}
