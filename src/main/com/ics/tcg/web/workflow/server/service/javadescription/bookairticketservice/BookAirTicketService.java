package com.ics.tcg.web.workflow.server.service.javadescription.bookairticketservice;

import java.io.Serializable;

/**
 * The definition of the air ticket booking service.
 * @author
 *
 */
public class BookAirTicketService implements Serializable
{
	private static final long serialVersionUID = -1455753278079165473L;
    
	/** Book the ticket. */
	public BookAirTicketResponseList bookAirTicket(BookAirTicketRequestList bookAirTicketRequestList)
	{
		return null;
	}
	
	/** Get the flights message. */
	public FlightsNameResponse getFlightsName(FlightsNameRequest flightsNameRequest)
	{
		return null;
	}
	
	/** Get the price message. */
	public TicketPriceResponse getAirTicketPrice (AirTicketPriceRequest ariTicketPriceRequesst)
	{
		return null;
	}
}
