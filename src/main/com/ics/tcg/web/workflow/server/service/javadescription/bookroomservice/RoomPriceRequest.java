package com.ics.tcg.web.workflow.server.service.javadescription.bookroomservice;

import java.io.Serializable;

import com.ics.tcg.web.workflow.server.service.javadescription.DataType;

/**
 * The request type of the getting room price method.
 * @author 
 *
 */
public class RoomPriceRequest extends DataType implements Serializable
{
	private static final long serialVersionUID = 8558235799705068141L;
    
	/** The hotel name. */
	private String hotelName;
	
	/** The room number. */
	private String roomNum;
	
	public RoomPriceRequest()
	{
		hotelName = new String();
		roomNum = new String();
	}
	
	public String getHotelName() 
	{
		return hotelName;
	}
	
	public void setHotelName(String hotelName) 
	{
		this.hotelName = hotelName;
	}
	
	public String getRoomNum() 
	{
		return roomNum;
	}
	
	public void setRoomNum(String roomNum) 
	{
		this.roomNum = roomNum;
	}
}
