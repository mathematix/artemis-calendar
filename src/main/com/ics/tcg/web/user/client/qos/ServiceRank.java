package com.ics.tcg.web.user.client.qos;

import java.io.Serializable;

/***
 * The service rank item.
 * 
 * @author Administrator
 * 
 */
public class ServiceRank implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3632648760272958225L;

	/** Service rank item. */
	private RequirementItem item;

	public ServiceRank() {
		item = new RequirementItem();
	}

	public RequirementItem getItem() {
		return item;
	}

	public void setItem(RequirementItem item) {
		this.item = item;
	}
}
