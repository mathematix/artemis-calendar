package com.ics.tcg.web.workflow.client.data;

import java.io.Serializable;

public class Client_ServiceDescription implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6672640658647934004L;
	protected String hasWsdlFile;

	public String getHasWsdlFile() {
		return hasWsdlFile;
	}

	public void setHasWsdlFile(String hasWsdlFile) {
		this.hasWsdlFile = hasWsdlFile;
	}
}
