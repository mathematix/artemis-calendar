package com.ics.tcg.web.workflow.server.service.javadescription.bookairticketservice;

import java.io.Serializable;

/**
 * The air ticket booking response info.
 * @author
 *
 */
public class BookAirTicketResponseInfo implements Serializable
{
	private static final long serialVersionUID = -420540628365774461L;
	
	/** The flights name. */
	private String flightsName;
	
	/** The ticket kind. */
	private String ticketKind;
	
	/** The seat info. */
	private String seatInfo;
	
	/** Whether booking succeed. */
	private Boolean isBookSucceed;
	
	public BookAirTicketResponseInfo()
	{
		flightsName = new String();
		ticketKind = new String();
		seatInfo = new String();
	}
	
	public String getFlightsName() 
	{
		return flightsName;
	}
	
	public void setFlightsName(String flightsName)
	{
		this.flightsName = flightsName;
	}
	
	public String getTicketKind() 
	{
		return ticketKind;
	}
	
	public void setTicketKind(String ticketKind)
	{
		this.ticketKind = ticketKind;
	}
	
	public String getSeatInfo() 
	{
		return seatInfo;
	}
	
	public void setSeatInfo(String seatInfo)
	{
		this.seatInfo = seatInfo;
	}

	public Boolean getIsBookSucceed()
	{
		return isBookSucceed;
	}

	public void setIsBookSucceed(Boolean isBookSucced) 
	{
		this.isBookSucceed = isBookSucced;
	}
}
