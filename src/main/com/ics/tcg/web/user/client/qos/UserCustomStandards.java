package com.ics.tcg.web.user.client.qos;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The special need in the standard requirement
 * 
 * @author Administrator
 * 
 */
public class UserCustomStandards implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2794565144024747115L;

	/** The list to store the special standard requirement */
	private ArrayList<RequirementItem> items;

	public UserCustomStandards() {
		items = new ArrayList<RequirementItem>();
	}

	public ArrayList<RequirementItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<RequirementItem> items) {
		this.items = items;
	}
}
