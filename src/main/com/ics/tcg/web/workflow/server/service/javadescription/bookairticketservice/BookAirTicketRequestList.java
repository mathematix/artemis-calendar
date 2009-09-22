package com.ics.tcg.web.workflow.server.service.javadescription.bookairticketservice;

import java.io.Serializable;
import java.util.ArrayList;

import com.ics.tcg.web.workflow.server.service.javadescription.DataType;

/**
 * The request type of the booking ticket method.
 * @author 
 *
 */
public class BookAirTicketRequestList extends DataType implements Serializable
{
	private static final long serialVersionUID = -3027304184558178852L;
	
	/** The list to store booking request, usually has only one element. */
	private ArrayList<BookAirTicketRequestInfo> airTicketRequestList;

	public BookAirTicketRequestList()
	{
		airTicketRequestList = new ArrayList<BookAirTicketRequestInfo>();
	}
	
	public ArrayList<BookAirTicketRequestInfo> getAirTicketRequestList() 
	{
		return airTicketRequestList;
	}

	public void setAirTicketRequestList(ArrayList<BookAirTicketRequestInfo> airTicketRequestList)
	{
		this.airTicketRequestList = airTicketRequestList;
	}
}
