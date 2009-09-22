package com.ics.tcg.web.workflow.server.service.enumvalue;

import java.io.Serializable;

@SuppressWarnings("serial")
public class WeatherStateEnum implements EnumValue, Serializable {
	private String[] values;

	public WeatherStateEnum() {
		values = new String[4];
		values[0] = "Rainy";
		values[1] = "Sunny";
		values[2] = "Snowy";
		values[3] = "Cloudy";
	}

	public String[] getValues() {
		return values;
	}
}
