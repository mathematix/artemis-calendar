package com.ics.tcg.web.workflow.server.service.enumvalue;

import java.io.Serializable;

/**
 * This class is used to describe the ticketKind
 * @author lms
 *
 */
@SuppressWarnings("serial")
public class TicketKindsEnum implements EnumValue, Serializable {
	private String[] values;

	public TicketKindsEnum() {
		values = new String[3];
		values[0] = "Sit";
		values[1] = "Stand";
		values[2] = "Lie";
	}

	public String[] getValues() {
		return values;
	}
}
