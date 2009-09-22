package com.ics.tcg.web.workflow.client.data;

import java.util.ArrayList;
import java.util.List;

public class Client_InputPort extends Client_Port {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2313905408525972511L;
	public Client_Node belongTo;
	public List<Client_Node> beforeNodes;

	public Client_Node getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(Client_Node belongTo) {
		this.belongTo = belongTo;
	}

	public List<Client_Node> getBeforeNodes() {
		if (beforeNodes == null) {
			beforeNodes = new ArrayList<Client_Node>();
		}
		return this.beforeNodes;
	}
}
