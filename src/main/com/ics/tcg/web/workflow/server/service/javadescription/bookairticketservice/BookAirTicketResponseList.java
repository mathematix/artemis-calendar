package com.ics.tcg.web.workflow.server.service.javadescription.bookairticketservice;

import java.io.Serializable;
import java.util.ArrayList;

import com.ics.tcg.web.workflow.server.service.javadescription.DataType;


/**
 * The response type of the booking air ticket method.
 * @author 
 *
 */
public class BookAirTicketResponseList extends DataType implements Serializable
{
	private static final long serialVersionUID = 8619390029212999102L;
    
	/** The list to store booking response. */
	private ArrayList<BookAirTicketResponseInfo> airTicketResponseList;
	
	/** Whether all the ordered tickets are successful booked. */
	private Boolean isFullBookSucceed;
    
	public BookAirTicketResponseList()
	{
		airTicketResponseList = new ArrayList<BookAirTicketResponseInfo>();
	}
	
	public ArrayList<BookAirTicketResponseInfo> getAirTicketResponseList() 
	{
		return airTicketResponseList;
	}

	public void setAirTicketResponseList(ArrayList<BookAirTicketResponseInfo> airTicketResponseList) 
	{
		this.airTicketResponseList = airTicketResponseList;
	}

	public Boolean getIsFullBookSucceed()
	{
		return isFullBookSucceed;
	}

	public void setIsFullBookSucceed(Boolean isFullBookSucceed) 
	{
		this.isFullBookSucceed = isFullBookSucceed;
	}
}
