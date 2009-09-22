package com.ics.tcg.web.workflow.server.service.javadescription.booktrainticketservice;

import java.io.Serializable;
import java.util.Date;

/**
 * The train ticket booking request info.
 * @author 
 *
 */
public class BookTrainTicketRequestInfo implements Serializable
{
	private static final long serialVersionUID = -8781137668480877687L;
	
	/** The user info, may pay by the account. */
    private UserInfo userInfo;
    
    /** The train ticket agent name. */
	private String trainTicketAgentName;
	
	/** The ticket kind, may be 'Standing' 'Sleeper' 'Seat'*/
	private String ticketKind;
	
	/** The train number.*/
	private String trainNum;
	
	/** The number of tickets the user want to book. */
	private Integer bookCount;
	
	/** The start-off time of the train. */
	private Date trainTime;
	
	public BookTrainTicketRequestInfo()
	{
		userInfo = new UserInfo();
		
		trainTicketAgentName = new String();
		ticketKind = new String();
		trainNum = new String();
		trainTime = new Date();
	}
	
	public String getTrainTicketAgentName() 
	{
		return trainTicketAgentName;
	}

	public void setTrainTicketAgentName(String trainTicketAgentName) 
	{
		this.trainTicketAgentName = trainTicketAgentName;
	}

	public String getTicketKind() 
	{
		return ticketKind;
	}

	public void setTicketKind(String ticketKind) 
	{
		this.ticketKind = ticketKind;
	}

	public Date getTrainTime() 
	{
		return trainTime;
	}

	public void setTrainTime(Date trainTime) 
	{
		this.trainTime = trainTime;
	}

	public String getTrainNum() 
	{
		return trainNum;
	}

	public void setTrainNum(String trainNum) 
	{
		this.trainNum = trainNum;
	}

	public Integer getBookCount() 
	{
		return bookCount;
	}

	public void setBookCount(Integer bookCount)
	{
		this.bookCount = bookCount;
	}

	public UserInfo getUserInfo() 
	{
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) 
	{
		this.userInfo = userInfo;
	}
}
