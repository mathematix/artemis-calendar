package com.ics.tcg.web.user.client.qos;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

//This class is used to describe the display of the qos'items in the user UI.
public class ContentItem implements Serializable {
	/**
	 * This class is created by lms.
	 */
	private static final long serialVersionUID = 8552301749378366438L;

	private String itemName;//The name of the item of qos.
	private String itemDisplayMode;//describe the form of the item displayed.
	private String metric;//the  metric of the item
	public boolean isRange;//estimate the item which the user is asked to fill in wheather is range.
	public boolean isMoreSelect;//wheather the item has many values.
	// private List<ContentItem> contentItemList;
	private List<String> valueList;//the values of the item

	public ContentItem() {
		itemDisplayMode = new String();
		itemName = new String();
		valueList = new LinkedList<String>();
		isRange = false;
		isMoreSelect = false;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDisplayMode() {
		return itemDisplayMode;
	}

	public void setItemDisplayMode(String itemDisplayMode) {
		this.itemDisplayMode = itemDisplayMode;
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}

	public boolean isRange() {
		return isRange;
	}

	public void setRange(boolean isRange) {
		this.isRange = isRange;
	}

	public boolean isMoreSelect() {
		return isMoreSelect;
	}

	public void setMoreSelect(boolean isMoreSelect) {
		this.isMoreSelect = isMoreSelect;
	}

	public List<String> getValueList() {
		return valueList;
	}

	public void setValueList(List<String> valueList) {
		this.valueList = valueList;
	}
}
