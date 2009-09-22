package com.ics.tcg.web.workflow.server.service.javadescription.booktrainticketservice;

import java.io.Serializable;
import java.util.Date;

/**
 * The train info.
 * @author 
 *
 */
public class TrainNumInfo implements Serializable
{
	private static final long serialVersionUID = 7596704218985381511L;
    
	/** The train number. */
	private String trainNum;
	
	/** The start-off time. */
	private Date startOffTime;
	
	/** The arrival time. */
	private Date arrivalTime;
	
	public TrainNumInfo()
	{
		trainNum = new String();
		startOffTime = new Date();
		arrivalTime = new Date();
	}
	
	public String getTrainNum() 
	{
		return trainNum;
	}
	
	public void setTrainNum(String trainNum)
	{
		this.trainNum = trainNum;
	}
	
	public Date getStartOffTime() 
	{
		return startOffTime;
	}
	
	public void setStartOffTime(Date startOffTime)
	{
		this.startOffTime = startOffTime;
	}
	
	public Date getArrivalTime() 
	{
		return arrivalTime;
	}
	
	public void setArrivalTime(Date arrivalTime) 
	{
		this.arrivalTime = arrivalTime;
	}
}
