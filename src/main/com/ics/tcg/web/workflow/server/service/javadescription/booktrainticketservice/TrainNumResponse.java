package com.ics.tcg.web.workflow.server.service.javadescription.booktrainticketservice;

import java.io.Serializable;
import java.util.ArrayList;

import com.ics.tcg.web.workflow.server.service.javadescription.DataType;

/**
 * The response type of the getting train message method.
 * @author 
 *
 */
public class TrainNumResponse extends DataType implements Serializable
{
	private static final long serialVersionUID = -6886108539228069169L;
    
	/** The list to store the train info. */
	private ArrayList<TrainNumInfo> trainNumInfoList;

	public TrainNumResponse()
	{
		trainNumInfoList = new ArrayList<TrainNumInfo>();
	}
	
	public ArrayList<TrainNumInfo> getTrainNumInfoList()
	{
		return trainNumInfoList;
	}

	public void setTrainNumInfoList(ArrayList<TrainNumInfo> trainNumInfoList) 
	{
		this.trainNumInfoList = trainNumInfoList;
	}
}
