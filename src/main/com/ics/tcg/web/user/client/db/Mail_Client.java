package com.ics.tcg.web.user.client.db;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Mail_Client implements Serializable {

	private Integer id;
	private Integer userid;
	private String head;
	private String content;
	private Date senttime;
	private String sender;
	private boolean unread;

	public boolean isUnread() {
		return unread;
	}

	public void setUnread(boolean unread) {
		this.unread = unread;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSenttime() {
		return senttime;
	}

	public void setSenttime(Date senttime) {
		this.senttime = senttime;
	}

}
