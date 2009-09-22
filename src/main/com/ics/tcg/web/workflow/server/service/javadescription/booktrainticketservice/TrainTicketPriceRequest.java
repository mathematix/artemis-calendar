package com.ics.tcg.web.workflow.server.service.javadescription.booktrainticketservice;

import java.io.Serializable;

import com.ics.tcg.web.workflow.server.service.javadescription.DataType;

/**
 * The request type of the getting ticket price method.
 * @author 
 *
 */
public class TrainTicketPriceRequest extends DataType implements Serializable
{
	private static final long serialVersionUID = 1206682604264763527L;
    
	/** The train ticket agent name. */
	private String trainTicketAgentName;
	
	/** The ticket kind, may be 'Standing' 'Sleeper' 'Seat'*/
	private String ticketKind;
	
	/** The train number.*/
	private String trainNum;
	
	public TrainTicketPriceRequest()
	{
		trainTicketAgentName = new String();
		ticketKind = new String();
		trainNum = new String();
	}
	
	public String getTrianTicketAgentName() 
	{
		return trainTicketAgentName;
	}
	
	public void setTrianTicketAgentName(String trianTicketAgentName) 
	{
		this.trainTicketAgentName = trianTicketAgentName;
	}
	
	public String getTicketKind() 
	{
		return ticketKind;
	}
	
	public void setTicketKind(String ticketKind)
	{
		this.ticketKind = ticketKind;
	}
	
	public String getTrainNum()
	{
		return trainNum;
	}
	
	public void setTrainNum(String trainNum)
	{
		this.trainNum = trainNum;
	}
}
