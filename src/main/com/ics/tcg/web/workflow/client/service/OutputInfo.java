package com.ics.tcg.web.workflow.client.service;

import java.io.Serializable;

/**
 * The type of output of the service.
 * 
 * @author lms
 * 
 */
public class OutputInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1783060895842446827L;

	/** The parameter info */
	private ParamInfo paramInfo;

	public OutputInfo() {
		paramInfo = new ParamInfo();
	}

	public ParamInfo getParamInfoArray() {
		return paramInfo;
	}

	public void setParamInfo(ParamInfo paramInfo) {
		this.paramInfo = paramInfo;
	}
}
