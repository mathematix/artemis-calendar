package com.ics.tcg.web.user.client.remote;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ics.tcg.web.user.client.db.Friends_Client;
import com.ics.tcg.web.user.client.db.User_Client;

@RemoteServiceRelativePath("friend")
public interface Friend_Service extends RemoteService {

	/** get a list of all friends of user */
	List<Friends_Client> getAllFriends(int userid);

	/** delete a friend */
	String deleteFriend(int userid, int friendid);

	/** save a friend */
	Friends_Client saveFriend(int userid, String friendname);

	/** get a friend's information */
	User_Client getFriendInfo(String account);

	/** check if is a friend */
	Integer check(Integer userid, Integer id);
}
