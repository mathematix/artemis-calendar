package com.ics.tcg.web.user.client.remote;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ics.tcg.web.user.client.db.AbstractService_Client;
import com.ics.tcg.web.user.client.db.User_Service_Client;
import com.ics.tcg.web.user.client.qos.UIContent;

public interface Service_ServiceAsync {

	void getAllAbservices(
			AsyncCallback<List<AbstractService_Client>> asyncCallback);

	void getUserAbservices(int userid,
			AsyncCallback<List<User_Service_Client>> asyncCallback);

	void deleteUserAbservice(int userid, int asid,
			AsyncCallback<String> asyncCallback);

	void saveUserAbservice(int userid, int asid, String name,
			AsyncCallback<User_Service_Client> asyncCallback);

	void getContent(User_Service_Client user_service,
			AsyncCallback<UIContent> asyncCallback);

	void saveQos(User_Service_Client user_ServiceC,
			AsyncCallback<String> asyncCallback);

	void check(Integer userid, Integer abserviceid,
			AsyncCallback<Integer> asyncVallback);

}
