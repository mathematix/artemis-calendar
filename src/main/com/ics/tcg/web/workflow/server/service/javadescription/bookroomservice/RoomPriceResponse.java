package com.ics.tcg.web.workflow.server.service.javadescription.bookroomservice;

import java.io.Serializable;

import com.ics.tcg.web.workflow.server.service.javadescription.DataType;

/**
 * The response type of the getting room price method.
 * @author 
 *
 */
public class RoomPriceResponse extends DataType implements Serializable 
{
	private static final long serialVersionUID = -2991581380176226301L;
	
	/** The room price. */
    private Float roomPrice;

	public Float getRoomPrice() 
	{
		return roomPrice;
	}

	public void setRoomPrice(Float roomPrice) 
	{
		this.roomPrice = roomPrice;
	}
}
