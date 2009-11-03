package com.ics.tcg.web.workflow.server.service.javadescription.bookbusticketservice;

import java.io.Serializable;

import com.ics.tcg.web.workflow.server.service.javadescription.DataType;


public class BusTicketPriceRequest extends DataType implements Serializable
{
	private static final long serialVersionUID = 944423375036868009L;
    
	private String busTicketAgentName;
	private String ticketKind;
	private String busNum;
	
	public BusTicketPriceRequest()
	{
		busTicketAgentName = new String();
		ticketKind = new String();
		busNum = new String();
	}
	
	public String getBusTicketAgentName() 
	{
		return busTicketAgentName;
	}
	
	public void setBusTicketAgentName(String busTicketAgentName)
	{
		this.busTicketAgentName = busTicketAgentName;
	}
	
	public String getTicketKind() 
	{
		return ticketKind;
	}
	
	public void setTicketKind(String ticketKind) 
	{
		this.ticketKind = ticketKind;
	}
	
	public String getBusNum()
	{
		return busNum;
	}
	
	public void setBusNum(String busNum)
	{
		this.busNum = busNum;
	}
}
