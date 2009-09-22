package com.ics.tcg.web.user.client.remote;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("login")
public interface Login_Service extends RemoteService {

	/** valid the user */
	Integer Valid_Login(String name, String password);
}
