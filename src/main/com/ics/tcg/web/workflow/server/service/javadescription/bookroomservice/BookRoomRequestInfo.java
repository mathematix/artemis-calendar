package com.ics.tcg.web.workflow.server.service.javadescription.bookroomservice;


import java.io.Serializable;
import java.util.Date;

/**
 * The room booking request info.
 * @author 
 *
 */
public class BookRoomRequestInfo implements Serializable
{
	private static final long serialVersionUID = -2155343697495606656L;
	
	/** The user info, may pay by the account. */
    private UserInfo userInfo;
    
    /** The hotel name. */
	private String hotelName;
	
	/** The room kind, may be 'Single' 'Standard'. */
	private String roomKind;
	
	/** The check in time. */
	private Date roomCheckInTime;
	
	/** The leave time. */
	private Date roomLeaveTime;
	
	/** The number of rooms the user want to book. */
	private Integer roomCount;
	
	public BookRoomRequestInfo()
	{
		userInfo = new UserInfo();
		
		hotelName = new String();
		roomKind = new String();
		roomCheckInTime = new Date();
		roomLeaveTime = new Date();
	}
	
	public String getHotelName() 
	{
		return hotelName;
	}

	public void setHotelName(String hotelName)
	{
		this.hotelName = hotelName;
	}

	public String getRoomKind()
	{
		return roomKind;
	}

	public void setRoomKind(String roomKind) 
	{
		this.roomKind = roomKind;
	}

	public Date getRoomCheckInTime()
	{
		return roomCheckInTime;
	}

	public void setRoomCheckInTime(Date roomCheckInTime) 
	{
		this.roomCheckInTime = roomCheckInTime;
	}

	public Date getRoomLeaveTime() {
		return roomLeaveTime;
	}

	public void setRoomLeaveTime(Date roomLeaveTime)
	{
		this.roomLeaveTime = roomLeaveTime;
	}

	public Integer getRoomCount() 
	{
		return roomCount;
	}

	public void setRoomCount(Integer roomCount)
	{
		this.roomCount = roomCount;
	}

	public UserInfo getUserInfo() 
	{
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) 
	{
		this.userInfo = userInfo;
	}
}
