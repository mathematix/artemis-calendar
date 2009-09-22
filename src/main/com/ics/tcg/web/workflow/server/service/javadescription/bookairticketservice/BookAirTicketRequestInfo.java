package com.ics.tcg.web.workflow.server.service.javadescription.bookairticketservice;

import java.io.Serializable;
import java.util.Date;

/**
 * The air ticket booking request info.
 * @author 
 *
 */
public class BookAirTicketRequestInfo implements Serializable
{
	private static final long serialVersionUID = -5120293488970448861L;
	
	/** The user info, may pay by the account. */
    private UserInfo userInfo;
    
    /** The name of the air line that the user want to buy ticket from. */
	private String airLineName;
	
	/** The ticket kind, may be 'First Class' 'Second Class'. */
	private String ticketKind;
	
	/** The flights name, like 'K3432'. */
	private String flightsName;
	
	/** The day of the flight. */
	private Date flightDate;
	
	/** The take-off time of the flight. */
	private Date takeOffTime;
	
	/** The number of tickets the user want to book. */
	private Integer bookCount;
	
	public BookAirTicketRequestInfo()
	{
		userInfo = new UserInfo();
		
		airLineName = new String();
		ticketKind = new String();
		flightsName = new String();
		
		flightDate = new Date();
		takeOffTime = new Date();
		
		bookCount = null;
	}
	
	public String getAirLineName() 
	{
		return airLineName;
	}

	public void setAirLineName(String airLineName) 
	{
		this.airLineName = airLineName;
	}

	public String getTicketKind()
	{
		return ticketKind;
	}

	public void setTicketKind(String ticketKind) 
	{
		this.ticketKind = ticketKind;
	}

	public String getFlightsName() 
	{
		return flightsName;
	}

	public void setFlightsName(String flightsName) 
	{
		this.flightsName = flightsName;
	}

	public Date getFlightDate()
	{
		return flightDate;
	}

	public void setFlightDate(Date flightDate)
	{
		this.flightDate = flightDate;
	}
	
	public Date getTakeOffTime() 
	{
		return takeOffTime;
	}

	public void setTakeOffTime(Date takeOffTime)
	{
		this.takeOffTime = takeOffTime;
	}

	public Integer getBookCount()
	{
		return bookCount;
	}

	public void setBookCount(Integer bookCount) 
	{
		this.bookCount = bookCount;
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
