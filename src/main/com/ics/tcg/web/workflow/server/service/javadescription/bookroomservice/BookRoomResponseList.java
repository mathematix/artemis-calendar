package com.ics.tcg.web.workflow.server.service.javadescription.bookroomservice;

import java.io.Serializable;
import java.util.ArrayList;

import com.ics.tcg.web.workflow.server.service.javadescription.DataType;

/**
 * The response type of the booking room method.
 * @author 
 *
 */
public class BookRoomResponseList extends DataType implements Serializable
{
	private static final long serialVersionUID = -4993734954838086637L;
	
	/** The list to store booking response. */
	private ArrayList<BookRoomResponseInfo> bookRoomResponseInfoList;
	
	/** Whether all the ordered tickets are successful booked. */
	private Boolean isFullBookSucceed;
	
	public BookRoomResponseList()
	{
		bookRoomResponseInfoList = new ArrayList<BookRoomResponseInfo>();
	}
	
	public ArrayList<BookRoomResponseInfo> getBookRoomResponseInfoList()
	{
		return bookRoomResponseInfoList;
	}

	public void setBookRoomResponseInfoList(ArrayList<BookRoomResponseInfo> bookRoomResponseInfoList) 
	{
		this.bookRoomResponseInfoList = bookRoomResponseInfoList;
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
