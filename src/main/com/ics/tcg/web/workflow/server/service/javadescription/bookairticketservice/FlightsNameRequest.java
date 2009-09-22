package com.ics.tcg.web.workflow.server.service.javadescription.bookairticketservice;

import java.io.Serializable;
import java.util.Date;

import com.ics.tcg.web.workflow.server.service.javadescription.DataType;

/**
 * The request type of the getting flights message method.
 * @author 
 *
 */
public class FlightsNameRequest extends DataType implements Serializable
{
	private static final long serialVersionUID = 6084863078642803348L;
    
	/** The air line name. */
	private String airLineName;
	
	/** The start address, mostly a city name. */
	private String startAddress;
	
	/** The destination. */
	private String destination;
	
	/** The date of the flights. */
	private Date flightDate;
	
	public FlightsNameRequest()
	{
		airLineName = new String();
		startAddress = new String();
		destination = new String();
		flightDate = new Date();
	}
	
	public String getAirLineName() 
	{
		return airLineName;
	}
	
	public void setAirLineName(String airLineName)
	{
		this.airLineName = airLineName;
	}
	
	public String getStartAddress() 
	{
		return startAddress;
	}
	
	public void setStartAddress(String startAddress)
	{
		this.startAddress = startAddress;
	}
	
	public String getDestination() 
	{
		return destination;
	}
	
	public void setDestination(String destination) 
	{
		this.destination = destination;
	}
	
	public Date getFlightDate()
	{
		return flightDate;
	}
	
	public void setFlightDate(Date flightDate) 
	{
		this.flightDate = flightDate;
	}
}
