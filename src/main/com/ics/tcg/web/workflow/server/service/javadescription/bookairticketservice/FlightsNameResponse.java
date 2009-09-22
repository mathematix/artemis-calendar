package com.ics.tcg.web.workflow.server.service.javadescription.bookairticketservice;

import java.io.Serializable;
import java.util.ArrayList;

import com.ics.tcg.web.workflow.server.service.javadescription.DataType;

/**
 * The response type of the getting flights message method.
 * @author 
 *
 */
public class FlightsNameResponse extends DataType implements Serializable
{
	private static final long serialVersionUID = -6359983311600669903L;
    
	/** The list to store the flights info. */
	private ArrayList<FlightsInfo> flightsInfoList;

	public FlightsNameResponse()
	{
		flightsInfoList = new ArrayList<FlightsInfo>();
	}
	
	public ArrayList<FlightsInfo> getFlightsInfoList() 
	{
		return flightsInfoList;
	}

	public void setFlightsInfoList(ArrayList<FlightsInfo> flightsInfoList)
	{
		this.flightsInfoList = flightsInfoList;
	}
}
