package com.ics.tcg.web.workflow.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ics.tcg.web.workflow.client.ServerStatus_Service;
import com.ics.tcg.web.workflow.client.service.ServiceInfo;

@SuppressWarnings("serial")
public class ServerStatus_ServiceImpl extends RemoteServiceServlet implements
		ServerStatus_Service {

	// public ArrayList<String> GetServiceName() {
	// ServiceConfigureXmlParse xmlParse = new ServiceConfigureXmlParse();
	// ArrayList<ServiceConfigureInfo> serviceInfolist = new
	// ArrayList<ServiceConfigureInfo>();
	//		
	// try
	// {
	// xmlParse.parse();
	// }
	// catch (ParserConfigurationException e)
	// {
	// e.printStackTrace();
	// }
	// catch (SAXException e)
	// {
	// e.printStackTrace();
	// }
	// catch (IOException e)
	// {
	// e.printStackTrace();
	// }
	// serviceInfolist = xmlParse.getServiceConfigureInfosList();
	//		
	// ArrayList<String> serviceNameList = new ArrayList<String>();
	// for(Iterator iterator = serviceInfolist.iterator();iterator.hasNext();){
	// ServiceConfigureInfo serviceConfigureInfo =
	// (ServiceConfigureInfo)iterator.next();
	// serviceNameList.add(serviceConfigureInfo.getServiceName());
	// }
	//		
	// return serviceNameList;
	// }

	public ServiceInfo getServiceInfo(String ServiceName) {

		CreateServiceInfo service = new CreateServiceInfo(ServiceName);
		ServiceInfo serviceInfo = new ServiceInfo();

		try {
			service.parseClass();
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		serviceInfo = service.getServiceInfo();

		return serviceInfo;
	}

}
