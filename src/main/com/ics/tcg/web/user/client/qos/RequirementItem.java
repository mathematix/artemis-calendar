package com.ics.tcg.web.user.client.qos;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The requirement item, defined to generalize the requirement info.
 * 
 * @author lms
 * 
 */
public class RequirementItem implements Serializable {

	private static final long serialVersionUID = -3144589785575600145L;

	/** The requirement item name */
	private String itemName;

	/** The unit of the item */
	private String metric;

	/** The value of the item */
	private String itemValue;

	/** Can be multiple-selected */
	private boolean isMoreSelect;

	/** The values to choose from */
	private ArrayList<String> selectedValues;

	/** Value is a range */
	private boolean isRange;

	/** The upper bound of the range */
	private String upperBoundValue;

	/** The lower bound of the range */
	private String lowerBoundValue;

	public RequirementItem() {
		itemName = null;
		metric = null;
		itemValue = null;
		isMoreSelect = false;
		isRange = false;
		upperBoundValue = null;
		lowerBoundValue = null;

		selectedValues = new ArrayList<String>();
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public boolean isMoreSelect() {
		return isMoreSelect;
	}

	public void setMoreSelect(boolean isMoreSelect) {
		this.isMoreSelect = isMoreSelect;
	}

	public ArrayList<String> getSelectedValues() {
		return selectedValues;
	}

	public void setSelectedValues(ArrayList<String> selectedValues) {
		this.selectedValues = selectedValues;
	}

	public boolean isRange() {
		return isRange;
	}

	public void setRange(boolean isRange) {
		this.isRange = isRange;
	}

	public String getUpperBoundValue() {
		return upperBoundValue;
	}

	public void setUpperBoundValue(String upperBoundValue) {
		this.upperBoundValue = upperBoundValue;
	}

	public String getLowerBoundValue() {
		return lowerBoundValue;
	}

	public void setLowerBoundValue(String lowerBoundValue) {
		this.lowerBoundValue = lowerBoundValue;
	}
}
