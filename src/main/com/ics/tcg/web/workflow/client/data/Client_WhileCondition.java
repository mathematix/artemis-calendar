//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.04.16 at 11:50:14 ���� CST 
//

package com.ics.tcg.web.workflow.client.data;

import java.io.Serializable;

public class Client_WhileCondition implements Serializable {

	private static final long serialVersionUID = -5515497810464890398L;
	protected String id;
	protected String whileCondition;

	public String getId() {
		return id;
	}

	public void setId(String value) {
		this.id = value;
	}

	public String getWhileCondition() {
		return whileCondition;
	}

	public void setWhileCondition(String whileCondition) {
		this.whileCondition = whileCondition;
	}
}