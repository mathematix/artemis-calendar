package com.ics.tcg.web.user.client.remote;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface Login_ServiceAsync {

	/** valid the user */
	void Valid_Login(String name, String password,
			AsyncCallback<Integer> asyncCallback);
}
