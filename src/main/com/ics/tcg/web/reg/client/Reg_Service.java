package com.ics.tcg.web.reg.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("reg")
public interface Reg_Service extends RemoteService {

	/** save a friend */
	String saveUser(User_Client user);

	/** check if is a friend */
	Integer check(String username);
}
