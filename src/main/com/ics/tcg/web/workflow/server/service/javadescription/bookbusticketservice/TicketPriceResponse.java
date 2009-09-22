package com.ics.tcg.web.workflow.server.service.javadescription.bookbusticketservice;

import java.io.Serializable;

import com.ics.tcg.web.workflow.server.service.javadescription.DataType;


public class TicketPriceResponse extends DataType implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -865410139796254940L;
	private Float ticketPrice;

	public Float getTicketPrice() 
	{
		return ticketPrice;
	}

	public void setTicketPrice(Float ticketPrice) 
	{
		this.ticketPrice = ticketPrice;
	}
}
