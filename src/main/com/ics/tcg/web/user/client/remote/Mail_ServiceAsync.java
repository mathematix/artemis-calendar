package com.ics.tcg.web.user.client.remote;

import java.util.List;

import com.ics.tcg.web.user.client.db.Mail_Client;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface Mail_ServiceAsync {

	/**get all mails of the user */
	void get_Mail(Integer userid, AsyncCallback<List<Mail_Client>> callback);

	/**get content of the specified mail*/
	void getContent(Integer id, AsyncCallback<Mail_Client> callback);

	void getUnreadMailCount(Integer userid, AsyncCallback<Integer> callback);

	void setRead(Integer id, AsyncCallback<Void> callback);
}
