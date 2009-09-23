package com.ics.tcg.web.reg.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface Reg_ServiceAsync {

	/** check if the name is available */
	void check(String username, AsyncCallback<Integer> asyncCallback);

	/** save a user */
	void saveUser(User_Client user, AsyncCallback<String> callback);

}
