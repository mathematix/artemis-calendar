package com.ics.tcg.web.reg.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("reg")
public interface Reg_Service extends RemoteService {

	/** save a user */
	String saveUser(User_Client user);

	/** check if the name is available */
	Integer check(String username);
}
