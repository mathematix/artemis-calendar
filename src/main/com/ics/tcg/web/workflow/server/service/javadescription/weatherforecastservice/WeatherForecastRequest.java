package com.ics.tcg.web.workflow.server.service.javadescription.weatherforecastservice;

import java.io.Serializable;
import java.util.Date;

import com.ics.tcg.web.workflow.server.service.javadescription.DataType;


/**
 * The request type of the weather forecast service.
 * @author 
 *
 */
public class WeatherForecastRequest extends DataType implements Serializable
{
	private static final long serialVersionUID = 8461102696325520486L;
	
	/** The nation of the forecast. */
	private String nation;
	
	/** The city of the forecast. */
	private String city;
	
	/** The start day of the forecast. */
	private Date startDate;
	
	/** The end day of the forecast. */
	private Date endDate;
	
	public WeatherForecastRequest()
	{
		nation = new String();
		city = new String();
		startDate = new Date();
		endDate = new Date();
	}
	
	public String getNation() 
	{
		return nation;
	}
	
	public void setNation(String nation) 
	{
		this.nation = nation;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public void setCity(String city)
	{
		this.city = city;
	}
	
	public Date getStartDate()
	{
		return startDate;
	}
	
	public void setStartDate(Date startDate) 
	{
		this.startDate = startDate;
	}
	
	public Date getEndDate()
	{
		return endDate;
	}
	
	public void setEndDate(Date endDate) 
	{
		this.endDate = endDate;
	}
}
