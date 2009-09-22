package com.ics.tcg.web.workflow.server.service.javadescription.booktrainticketservice;

import java.io.Serializable;
import java.util.ArrayList;

import com.ics.tcg.web.workflow.server.service.javadescription.DataType;

/**
 * The request type of the booking train ticket method.
 * @author 
 *
 */
public class BookTrainTicketRequestList extends DataType implements Serializable
{
	private static final long serialVersionUID = -2952197801956669136L;
    
	/** The list to store booking request, usually has only one element. */
	private ArrayList<BookTrainTicketRequestInfo> trainTicketRequestInfoList;

	public BookTrainTicketRequestList()
	{
		trainTicketRequestInfoList = new ArrayList<BookTrainTicketRequestInfo>();
	}
	
	public ArrayList<BookTrainTicketRequestInfo> getTrainTicketRequestInfoList() 
	{
		return trainTicketRequestInfoList;
	}

	public void setTrainTicketRequestInfoList (ArrayList<BookTrainTicketRequestInfo> trainTicketRequestInfoList)
	{
		this.trainTicketRequestInfoList = trainTicketRequestInfoList;
	}
}
