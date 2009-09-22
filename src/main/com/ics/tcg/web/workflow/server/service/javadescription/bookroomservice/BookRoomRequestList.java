package com.ics.tcg.web.workflow.server.service.javadescription.bookroomservice;


import java.io.Serializable;
import java.util.ArrayList;

import com.ics.tcg.web.workflow.server.service.javadescription.DataType;

/**
 * The request type of the booking room method.
 * @author 
 *
 */
public class BookRoomRequestList extends DataType implements Serializable 
{
	private static final long serialVersionUID = -5074302362676755828L;
	
	/** The list to store booking request, usually has only one element. */
	private ArrayList<BookRoomRequestInfo> roomRequestInfoList;
	
	public BookRoomRequestList()
	{
		roomRequestInfoList = new ArrayList<BookRoomRequestInfo>();
	}

	public ArrayList<BookRoomRequestInfo> getRoomRequestInfoList() 
	{
		return roomRequestInfoList;
	}

	public void setRoomRequestInfoList(ArrayList<BookRoomRequestInfo> roomRequestInfoList) 
	{
		this.roomRequestInfoList = roomRequestInfoList;
	}
}
