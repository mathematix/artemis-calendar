package com.ics.tcg.web.workflow.server.service.javadescription.bookbusticketservice;

import java.io.Serializable;

/**
 * The definition of the bus ticket booking service.
 * @author
 *
 */
public class BookBusTicketService implements Serializable
{
	private static final long serialVersionUID = 6998991637817082603L;
    
	/** Book the ticket. */
	public BookBusTicketResponseList bookBusTicket(BookBusTicketRequestList bookBusTicketRequestList)
	{
		return null;
	}
	
	/** Get the bus message. */
	public BusNumResponse getBusNum(BusNumRequest busNumRequest)
	{
		return null;
	}
	
	/** Get the price message. */
	public TicketPriceResponse getBusTicketPrice(BusTicketPriceRequest busTicketPriceRequest)
	{
		return null;
	}
}
