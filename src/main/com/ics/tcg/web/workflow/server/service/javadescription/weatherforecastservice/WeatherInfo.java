package com.ics.tcg.web.workflow.server.service.javadescription.weatherforecastservice;

import java.io.Serializable;
import java.util.Date;

/**
 * The weather info of a special day.
 * @author
 *
 */
public class WeatherInfo implements Serializable
{
	private static final long serialVersionUID = -7775424171316670044L;
    
	/** The day that is inquired. An inquire may include several days. */
	private Date date;
	
	/** The lowest temperature of the day. */
	private Integer lowestTemperature;
	
	/** The highest temperature of the day. */
	private Integer highestTemperature;
	
	/** The power of the wind. */
	private String windPower;
	
	/** The direction of the wind. */
	private String windDirection;
	
	/** The state of the weather, may be 'Sunny' 'Rainy' 'Cloudy'. */
	private String weatherState;

	public WeatherInfo()
	{
		date = new Date();
		windPower = new String();
		windDirection = new String();
		weatherState = new String();
	}
	
	public Date getDate() 
	{
		return date;
	}

	public void setDate(Date date) 
	{
		this.date = date;
	}
	
	public Integer getLowestTemperature()
	{
		return lowestTemperature;
	}
	
	public void setLowestTemperature(Integer lowestTemperature) 
	{
		this.lowestTemperature = lowestTemperature;
	}
	
	public Integer getMaximumTemperature() 
	{
		return highestTemperature;
	}
	
	public void setMaximumTemperature(Integer highestTemperature) 
	{
		this.highestTemperature = highestTemperature;
	}
	
	public String getWindPower()
	{
		return windPower;
	}
	
	public void setWindPower(String windPower) 
	{
		this.windPower = windPower;
	}
	
	public String getWindDirection()
	{
		return windDirection;
	}
	
	public void setWindDirection(String windDirection)
	{
		this.windDirection = windDirection;
	}

	public String getWeatherState() 
	{
		return weatherState;
	}

	public void setWeatherState(String weatherState) 
	{
		this.weatherState = weatherState;
	}
}
