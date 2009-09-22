package com.ics.tcg.web.user.client.remote;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ics.tcg.web.user.client.db.User_Client;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("user")
public interface User_Service extends RemoteService {

	/** get user info */
	User_Client getUser(int userid);

	/** save user */
	String saveUser(User_Client user);

	/** update user */
	String updateUser(User_Client user);
}
