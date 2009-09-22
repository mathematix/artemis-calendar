package com.ics.tcg.web.workflow.server.service.javadescription.bookairticketservice;

import java.io.Serializable;

import com.ics.tcg.web.workflow.server.service.javadescription.DataType;

/**
 * The response type of the getting ticket price method.
 * @author 
 *
 */
public class TicketPriceResponse extends DataType implements Serializable
{
	private static final long serialVersionUID = -7968477986116365185L;
	
	/** The price of the special ticket. */
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
