package com.ics.tcg.web.user.client.remote;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ics.tcg.web.user.client.db.Friends_Client;
import com.ics.tcg.web.user.client.db.User_Client;

public interface Friend_ServiceAsync {

	void getAllFriends(int userid, AsyncCallback<List<Friends_Client>> callback);

	void deleteFriend(int userid, int friendid,
			AsyncCallback<String> asyncCallback);

	void saveFriend(int userid, String friendname,
			AsyncCallback<Friends_Client> asyncCallback);

	void getFriendInfo(String account, AsyncCallback<User_Client> asyncCallback);

	void check(Integer userid, Integer id, AsyncCallback<Integer> asyncCallback);

}
