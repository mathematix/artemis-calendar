package com.ics.tcg.web.user.client.remote;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ics.tcg.web.user.client.db.AbstractService_Client;
import com.ics.tcg.web.user.client.db.User_Service_Client;
import com.ics.tcg.web.user.client.qos.UIContent;

@RemoteServiceRelativePath("service")
public interface Service_Service extends RemoteService {

	/** get all abstract services */
	List<AbstractService_Client> getAllAbservices();

	/** get user's abstract services include qos */
	List<User_Service_Client> getUserAbservices(int userid);

	/** delete an abstract service of the user */
	String deleteUserAbservice(int userid, int asid);

	/** save a service without qos */
	User_Service_Client saveUserAbservice(int userid, int asid, String name);

	/** get the qos framework */
	UIContent getContent(User_Service_Client user_service);

	/** save the qos settings */
	String saveQos(User_Service_Client user_Service_Client);

	/** check if is in the service list */
	Integer check(Integer userid, Integer abserviceid);

}
