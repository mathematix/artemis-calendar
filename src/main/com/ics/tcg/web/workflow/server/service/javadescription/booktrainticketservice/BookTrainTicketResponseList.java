package com.ics.tcg.web.workflow.server.service.javadescription.booktrainticketservice;

import java.io.Serializable;
import java.util.ArrayList;

import com.ics.tcg.web.workflow.server.service.javadescription.DataType;

/**
 * The response type of the booking train ticket method.
 * @author 
 *
 */
public class BookTrainTicketResponseList extends DataType implements Serializable
{
	private static final long serialVersionUID = 3681106726291958009L;
    
	/** The list to store booking response. */
	private ArrayList<BookTrainTicketResponseInfo> bookedTrainTicketInfoList;
	
	/** Whether all the ordered tickets are successful booked. */
	private Boolean isFullBookSucceed;
	
	public BookTrainTicketResponseList()
	{
		bookedTrainTicketInfoList = new ArrayList<BookTrainTicketResponseInfo>();
	}
	
	public ArrayList<BookTrainTicketResponseInfo> getBookedTrainTicketInfoList() 
	{
		return bookedTrainTicketInfoList;
	}
	
	public void setBookedTrainTicketInfoList(ArrayList<BookTrainTicketResponseInfo> bookedTrainTicketInfoList) 
	{
		this.bookedTrainTicketInfoList = bookedTrainTicketInfoList;
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
