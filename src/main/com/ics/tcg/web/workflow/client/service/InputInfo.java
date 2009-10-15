package com.ics.tcg.web.workflow.client.service;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The type of the service's input.
 * 
 * @author lms
 * 
 */
public class InputInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4539453084211274555L;

	/** The parameters list */
	private ArrayList<ParamInfo> paramInfosArray;

	public InputInfo() {
		paramInfosArray = new ArrayList<ParamInfo>();
	}

	public ArrayList<ParamInfo> getParamInfosArray() {
		return paramInfosArray;
	}

	public void setParamInfosArray(ArrayList<ParamInfo> paramInfosArray) {
		this.paramInfosArray = paramInfosArray;
	}
}
