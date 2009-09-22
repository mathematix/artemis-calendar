package com.ics.tcg.web.user.client.remote;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ics.tcg.web.user.client.db.Mail_Client;

@RemoteServiceRelativePath("mail")
public interface Mail_Service extends RemoteService {

	/**get all mails of the user */
	List<Mail_Client> get_Mail(Integer userid);
	
	/**get content of the specified mail*/
	Mail_Client getContent(Integer id);
	
	/**get mail count*/
	Integer getUnreadMailCount(Integer userid);
	
	/**set unread to read*/
	void setRead(Integer id);
}
