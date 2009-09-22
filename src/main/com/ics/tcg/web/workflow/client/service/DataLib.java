package com.ics.tcg.web.workflow.client.service;

public class DataLib {
	static String[] datalibStrings = { "weatherState", "windDirection",
			"windPower", "ticketKind", "ticketKind" };

	public static boolean whetherInDataLib(String s) {
		for (int i = 0; i < datalibStrings.length; i++) {
			if (datalibStrings[i].equals(s))
				return true;
		}
		return false;
	}
}
