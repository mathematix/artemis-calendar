package com.ics.tcg.web.workflow.server.service.javadescription.bookroomservice;

import java.io.Serializable;

import com.ics.tcg.web.workflow.server.service.javadescription.DataType;

/**
 * The response type of the getting room state method.
 * @author 
 *
 */
public class RoomStateResponse extends DataType implements Serializable
{
	private static final long serialVersionUID = -3383328516696673333L;
	
	/** The room state. */
	private String roomState;

	public RoomStateResponse()
	{
		roomState = new String();
	}
	
	public String getRoomState() 
	{
		return roomState;
	}

	public void setRoomState(String roomState)
	{
		this.roomState = roomState;
	}
}
