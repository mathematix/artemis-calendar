package com.ics.tcg.web.user.client.qos;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ContentItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8552301749378366438L;

	private String itemName;
	private String itemDisplayMode;
	private String metric;
	public boolean isRange;
	public boolean isMoreSelect;
	// private List<ContentItem> contentItemList;
	private List<String> valueList;

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
