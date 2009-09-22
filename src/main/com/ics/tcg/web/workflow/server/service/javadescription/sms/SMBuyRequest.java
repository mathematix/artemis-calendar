package com.ics.tcg.web.workflow.server.service.javadescription.sms;

import java.io.Serializable;

import com.ics.tcg.web.workflow.server.service.javadescription.DataType;

/**
 * The parameter type of the message buying method.
 * @author
 *
 */
public class SMBuyRequest extends DataType implements Serializable
{
	private static final long serialVersionUID = 563799158884637129L;
	
	/** Buyer id. */
	private String buyerId;
	
	/** Buyer password. */
	private String buyerPassword;
	
	/** Buyer telephone number. */
	private Integer buyerTelNum;
	
	/** Buying count. */
	private Integer smCount;
	
	public SMBuyRequest()
	{
		buyerId = new String();
		buyerPassword = new String();
	}
	
	public Integer getBuyerTelNum()
	{
		return buyerTelNum;
	}
	
	public void setBuyerTelNum(Integer buyerTelNum)
	{
		this.buyerTelNum = buyerTelNum;
	}
	
	public String getBuyerId() 
	{
		return buyerId;
	}
	
	public void setBuyerId(String buyerId) 
	{
		this.buyerId = buyerId;
	}
	
	public String getBuyerPassword()
	{
		return buyerPassword;
	}
	
	public void setBuyerPassword(String buyerPassword)
	{
		this.buyerPassword = buyerPassword;
	}
	
	public Integer getSmCount() 
	{
		return smCount;
	}
	
	public void setSmCount(Integer smCount)
	{
		this.smCount = smCount;
	}
}
