package com.ics.tcg.web.workflow.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ServerStatus_ServiceAsync {

	@SuppressWarnings("unchecked")
	// void GetServiceName(AsyncCallback callback);
	void getServiceInfo(String ServiceName, AsyncCallback callback);
}
