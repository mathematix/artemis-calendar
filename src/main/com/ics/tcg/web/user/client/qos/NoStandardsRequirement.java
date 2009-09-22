package com.ics.tcg.web.user.client.qos;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The non standard requirement, such as discount
 * 
 * @author Administrator
 */
public class NoStandardsRequirement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -172165680999099791L;

	/** The list to store the non standard requirement */
	private ArrayList<RequirementItem> items;

	public NoStandardsRequirement() {
		items = new ArrayList<RequirementItem>();
	}

	public ArrayList<RequirementItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<RequirementItem> items) {
		this.items = items;
	}
}
