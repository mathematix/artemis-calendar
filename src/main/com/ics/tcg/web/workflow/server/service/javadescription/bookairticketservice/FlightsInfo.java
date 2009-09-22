package com.ics.tcg.web.workflow.server.service.javadescription.bookairticketservice;

import java.io.Serializable;
import java.util.Date;

/**
 * The flights info.
 * @author 
 *
 */
public class FlightsInfo implements Serializable
{
	private static final long serialVersionUID = 6390810741917682450L;
    
	/** The flights name. */
	private String flightName;
	
	/** The take-off time. */
	private Date takeOffTime;
	
	/** The landing time. */
	private Date landingTime;
	
	public FlightsInfo()
	{
		flightName = new String();
		takeOffTime = new Date();
		landingTime = new Date();
	}
	
	public String getFlightName() 
	{
		return flightName;
	}
	
	public void setFlightName(String flightName) 
	{
		this.flightName = flightName;
	}
	public Date getTakeOffTime()
	{
		return takeOffTime;
	}
	
	public void setTakeOffTime(Date takeOffTime)
	{
		this.takeOffTime = takeOffTime;
	}
	
	public Date getLandingTime() 
	{
		return landingTime;
	}
	
	public void setLandingTime(Date landingTime) 
	{
		this.landingTime = landingTime;
	}
}
