package com.ics.tcg.web.workflow.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ics.tcg.web.workflow.client.service.ServiceInfo;

@RemoteServiceRelativePath("serverstatus")
public interface ServerStatus_Service extends RemoteService {

	// ArrayList<String> GetServiceName();
	ServiceInfo getServiceInfo(String ServiceName);
}
