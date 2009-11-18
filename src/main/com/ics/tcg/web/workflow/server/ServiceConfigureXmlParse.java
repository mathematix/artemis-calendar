package com.ics.tcg.web.workflow.server;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@SuppressWarnings("serial")
public class ServiceConfigureXmlParse implements Serializable {
	/**
	 * @author lms
	 */
	private ArrayList<ServiceConfigureInfo> serviceConfigureInfosList;
	private String xmlFilePath;

	public ServiceConfigureXmlParse() {
		serviceConfigureInfosList = new ArrayList<ServiceConfigureInfo>();
		xmlFilePath = "xml/ServiceConfigure.xml";
	}

	/**
	 * parse the xml to create serviceConfigure information
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void parse() throws ParserConfigurationException, SAXException,
			IOException {
		File xmlFile = new File(xmlFilePath);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(xmlFile);

		Element rootElement = doc.getDocumentElement();
		createServiceInfo(rootElement);
	}

	/**
	 * @author lms
	 * @param rootElement
	 */
	public void createServiceInfo(Element rootElement) {
		ServiceConfigureInfo serviceInfo;
		NodeList nodeList = rootElement.getChildNodes();

		for (int index = 0; index < nodeList.getLength(); index++) {
			Node node = nodeList.item(index);

			if (node instanceof Element) {
				serviceInfo = new ServiceConfigureInfo();
				NodeList childNodeList = node.getChildNodes();

				for (int itemIndex = 0; itemIndex < childNodeList.getLength(); itemIndex++) {
					Node childNode = childNodeList.item(itemIndex);

					if (childNode instanceof Element) {
						if (((Element) childNode).getTagName().equals("Name")) {
							serviceInfo.setServiceName(childNode
									.getTextContent());
						}

						else if (((Element) childNode).getTagName().equals(
								"ClassPath")) {
							serviceInfo.setServiceClassPath(childNode
									.getTextContent());
						}
					}
				}

				serviceConfigureInfosList.add(serviceInfo);
			}
		}
	}

	/**
	 * 
	 * @param serviceName
	 * @return
	 */
	public String findServiceClassPath(String serviceName) {
		String serviceClassPath = null;

		if (serviceConfigureInfosList.isEmpty()) {
			return serviceClassPath;
		}

		for (int index = 0; index < serviceConfigureInfosList.size(); index++) {
			if (serviceConfigureInfosList.get(index).getServiceName().equals(
					serviceName)) {
				serviceClassPath = serviceConfigureInfosList.get(index)
						.getServiceClassPath();
				break;
			}
		}

		return serviceClassPath;
	}

	public ArrayList<ServiceConfigureInfo> getServiceConfigureInfosList() {
		return serviceConfigureInfosList;
	}

	public void setServiceConfigureInfosList(
			ArrayList<ServiceConfigureInfo> serviceConfigureInfosList) {
		this.serviceConfigureInfosList = serviceConfigureInfosList;
	}
}
