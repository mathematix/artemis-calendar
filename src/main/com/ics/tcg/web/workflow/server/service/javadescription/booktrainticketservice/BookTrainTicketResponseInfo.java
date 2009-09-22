package com.ics.tcg.web.workflow.server.service.javadescription.booktrainticketservice;

import java.io.Serializable;
import java.util.Date;

/**
 * The train ticket booking response info.
 * @author
 *
 */
public class BookTrainTicketResponseInfo implements Serializable
{
	private static final long serialVersionUID = -7557140377219284225L;
    
	/** The train number. */
	private String trainNum;
	
	/** The ticket kind, may be 'Standing' 'Sleeper' 'Seat'*/
	private String ticketKind;
	
	/** The seat info. */
	private String seatInfo;
	
	/** The start-off time of the train. */
	private Date trainStartOffTime;
	
	/** Whether booking succeed. */
	private Boolean isBookSucceed;
	
	public BookTrainTicketResponseInfo()
	{
		trainNum = new String();
		ticketKind = new String();
		seatInfo = new String();
		trainStartOffTime = new Date();
	}
	
	public String getTrainNum() 
	{
		return trainNum;
	}
	
	public void setTrainNum(String trainNum) 
	{
		this.trainNum = trainNum;
	}
	
	public String getTicketKind()
	{
		return ticketKind;
	}
	
	public void setTicketKind(String ticketKind)
	{
		this.ticketKind = ticketKind;
	}
	
	public Date getTrainStartOffTime() 
	{
		return trainStartOffTime;
	}
	
	public void setTrainStartOffTime(Date trainStartOffTime) 
	{
		this.trainStartOffTime = trainStartOffTime;
	}
	
	public String getSeatInfo() 
	{
		return seatInfo;
	}
	
	public void setSeatInfo(String seatInfo) 
	{
		this.seatInfo = seatInfo;
	}

	public Boolean getIsBookSucceed() 
	{
		return isBookSucceed;
	}

	public void setIsBookSucceed(Boolean isBookSucceed)
	{
		this.isBookSucceed = isBookSucceed;
	}
}
