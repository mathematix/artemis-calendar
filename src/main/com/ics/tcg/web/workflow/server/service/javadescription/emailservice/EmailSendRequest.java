package com.ics.tcg.web.workflow.server.service.javadescription.emailservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.ics.tcg.web.workflow.server.service.javadescription.DataType;

/**
 * The parameter type of the email sending method.
 * @author
 *
 */
public class EmailSendRequest extends DataType implements Serializable {
	
	private static final long serialVersionUID = -1282693952746470711L;
	
	/** The email sender's email. */
	private String emailSender;
	
	/** The email content. */
	private String emailContent;
	
	/** The email sending time. */
	private Date emailSendTime;

	/** The email receivers' email. */
	private ArrayList<String> receiversEmail;

	public EmailSendRequest() {
		emailSender = new String();
		emailContent = new String();
		emailSendTime = new Date();
		receiversEmail = new ArrayList<String>();
	}

	public String getEmailSender() {
		return emailSender;
	}

	public void setEmailSender(String emailSender) {
		this.emailSender = emailSender;
	}

	public Date getEmailSendTime() {
		return emailSendTime;
	}

	public void setEmailSendTime(Date emailSendTime) {
		this.emailSendTime = emailSendTime;
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	public ArrayList<String> getReceiversEmail() {
		return receiversEmail;
	}

	public void setReceiversEmail(ArrayList<String> receiversEmail) {
		this.receiversEmail = receiversEmail;
	}
}
