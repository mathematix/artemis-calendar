package com.ics.tcg.web.workflow.server.service.javadescription.bookroomservice;

import java.io.Serializable;

/**
 * The room booking response info.
 * @author
 *
 */
public class BookRoomResponseInfo implements Serializable
{	
	private static final long serialVersionUID = -2467026332071453459L;
	
	/** The room number. */
	private String roomNum;
	
	/** Whether booking succeed. */
	private Boolean isBookSucceed;
	
	public BookRoomResponseInfo()
	{
		roomNum = new String();
	}
	
	public String getRoomNum() 
	{
		return roomNum;
	}
	
	public void setRoomNum(String roomNum)
	{
		this.roomNum = roomNum;
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
