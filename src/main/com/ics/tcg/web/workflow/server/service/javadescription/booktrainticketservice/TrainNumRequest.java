package com.ics.tcg.web.workflow.server.service.javadescription.booktrainticketservice;

import java.io.Serializable;
import java.util.Date;

import com.ics.tcg.web.workflow.server.service.javadescription.DataType;

/**
 * The request type of the getting train message method.
 * @author 
 *
 */
public class TrainNumRequest extends DataType implements Serializable
{
	private static final long serialVersionUID = -1527237836869929785L;
    
	/** The train ticket agent name. */
	private String trainTicketAgentName;
	
	/** The start address. */
	private String startAddress;
	
	/** The destination. */
	private String destination;
	
	/** The date of the train. */
	private Date trainDate;
	
	public TrainNumRequest()
	{
		trainTicketAgentName = new String();
		startAddress = new String();
		destination = new String();
		trainDate = new Date();
	}
	
	public String getTrainTicketAgentName() 
	{
		return trainTicketAgentName;
	}
	
	public void setTrainTicketAgentName(String trainTicketAgentName)
	{
		this.trainTicketAgentName = trainTicketAgentName;
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
	
	public Date getTrainDate() 
	{
		return trainDate;
	}
	
	public void setTrainDate(Date trainDate)
	{
		this.trainDate = trainDate;
	}
}
