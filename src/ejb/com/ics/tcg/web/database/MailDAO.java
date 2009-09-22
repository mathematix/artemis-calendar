package com.ics.tcg.web.database;

import java.util.List;

import com.ics.tcg.web.database.bean.Mail;

public interface MailDAO {
	/**
	 * get all Mails of the user
	 * 
	 * @param userid
	 * @return List<Mail>
	 */
	public List<Mail> getMailByID(int userid);
	
	/**
	 * get specified mail
	 * 
	 * @param id
	 * @return String
	 */
	public Mail getOneMail(int id);

	/**
	 * get mail count
	 * 
	 * @param userid
	 * @return String
	 */
	public Integer getUnreadMailCount(int userid);
	
	/**
	 * set unread to read
	 * 
	 * @param id
	 * @return String
	 */
	public void setRead(int id);
	
}
