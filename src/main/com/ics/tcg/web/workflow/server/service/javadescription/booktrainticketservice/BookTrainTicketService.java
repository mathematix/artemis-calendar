package com.ics.tcg.web.workflow.server.service.javadescription.booktrainticketservice;

import java.io.Serializable;

/**
 * The definition of the train ticket booking service.
 * @author
 *
 */
public class BookTrainTicketService implements Serializable
{
	private static final long serialVersionUID = 3820305919871972640L;
    
	/** Book the ticket. */
	public BookTrainTicketResponseList bookTrainTicket(BookTrainTicketRequestList bookTrainTicketRequestList)
	{
		return null;
	}
	
	/** Get the train message. */
	public TrainNumResponse getTrainNum(TrainNumRequest trainNumRequest)
	{
		return null;
	}
	
	/** Get the price message. */
	public TicketPriceResponse getTrainTicketPrice(TrainTicketPriceRequest trainTicketPriceRequest)
	{
		return null;
	}
}
