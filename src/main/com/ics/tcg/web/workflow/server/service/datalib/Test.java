package com.ics.tcg.web.workflow.server.service.datalib;

import com.ics.tcg.web.workflow.client.service.ServiceInfo;
import com.ics.tcg.web.workflow.server.CreateServiceInfo;

@SuppressWarnings( { "unused" })
public class Test {
	public static void main(String[] args) {
		CreateServiceInfo service = new CreateServiceInfo(
				"BookTrainTicketService");
		ServiceInfo serviceInfo = new ServiceInfo();

		try {
			service.parseClass();
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		serviceInfo = service.getServiceInfo();
	}
}
