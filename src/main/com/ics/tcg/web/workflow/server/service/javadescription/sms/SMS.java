package com.ics.tcg.web.workflow.server.service.javadescription.sms;

import java.io.Serializable;

/**
 * The definition of the SMS service.
 * @author 
 *
 */
public class SMS implements Serializable
{
	private static final long serialVersionUID = -666983915659228938L;
    
	/** Buy the message. */
	public Boolean smBuy(SMBuyRequest smBuyRequest)
	{
		return null;
	}
	
	/** Send the message. */
	public Boolean smSend(SMSendRequest smSendRequest)
	{
		return null;
	}
	
	//public Boolean smBuy(Integer buyerTelNum, String buyerId, String buyerPassword, Integer smCount);
}
