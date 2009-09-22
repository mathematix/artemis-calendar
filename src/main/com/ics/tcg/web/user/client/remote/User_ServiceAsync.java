package com.ics.tcg.web.user.client.remote;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ics.tcg.web.user.client.db.User_Client;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface User_ServiceAsync {

	void getUser(int userid, AsyncCallback<User_Client> callback);

	void saveUser(User_Client user, AsyncCallback<String> callback);

	void updateUser(User_Client user, AsyncCallback<String> callback);

}
