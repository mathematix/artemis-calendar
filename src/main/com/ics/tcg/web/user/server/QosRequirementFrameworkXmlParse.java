package com.ics.tcg.web.user.server;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.ics.tcg.web.user.client.qos.ContentBlock;
import com.ics.tcg.web.user.client.qos.ContentItem;
import com.ics.tcg.web.user.client.qos.UIContent;

@SuppressWarnings("serial")
public class QosRequirementFrameworkXmlParse implements Serializable {
	private static final String DISPLAYMODE1 = "select";
	private static final String DISPLAYMODE2 = "fill";

	private UIContent uiContent;
	private String xmlFileName;
	private String serviceName;

	public QosRequirementFrameworkXmlParse(String xmlFileName) {
		this.xmlFileName = xmlFileName;
		serviceName = new String();
		uiContent = new UIContent();
	}

	public QosRequirementFrameworkXmlParse(String xmlFileName,
			String serviceName) {
		this.xmlFileName = xmlFileName;
		this.serviceName = serviceName;
		uiContent = new UIContent();
		uiContent.setServiceName(serviceName);
	}

	public QosRequirementFrameworkXmlParse(String xmlFileName,
			UIContent interfaceContent) {
		this.xmlFileName = xmlFileName;
		serviceName = new String();
		this.uiContent = interfaceContent;
	}

	public QosRequirementFrameworkXmlParse(String xmlFileName,
			String serviceName, UIContent interfaceContent) {
		this.xmlFileName = xmlFileName;
		this.serviceName = serviceName;
		this.uiContent = interfaceContent;
		interfaceContent.setServiceName(serviceName);
	}

	public void startParse() {
		if (!xmlFileName.endsWith(".xml")) {
			System.out.println("aaaaaaaaaaa");
			System.exit(0);
		}

		try {
			File xmlFile = new File(xmlFileName);
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(xmlFile);

			Element rootElement = doc.getDocumentElement();
			createUIContent(rootElement);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createUIContent(Element rootElement) {
		if (serviceName.length() == 0) {
			String rootName = rootElement.getTagName();
			serviceName = rootName.split("_")[1].trim();
			uiContent.setServiceName(serviceName);
		}

		List<ContentBlock> contentBlocksList = new LinkedList<ContentBlock>();
		NodeList nodeList = rootElement.getChildNodes();

		for (int index = 0; index < nodeList.getLength(); index++) {
			if (nodeList.item(index) instanceof Element) {
				ContentBlock contentBlock = new ContentBlock();
				createContentBlock((Element) (nodeList.item(index)),
						contentBlock);
				contentBlocksList.add(contentBlock);
			}
		}

		uiContent.setContentBlocksList(contentBlocksList);
	}

	public void createContentBlock(Element contentBlockElement,
			ContentBlock contentBlock) {
		contentBlock.setContentBlockName(contentBlockElement.getTagName());
		List<ContentItem> contentItemList = new LinkedList<ContentItem>();

		if (contentBlock.getContentBlockName().equals("ServiceRank")) {
			ContentItem contentItem = new ContentItem();
			createContentItem(contentBlockElement, contentItem);
			contentItemList.add(contentItem);
		}

		else {
			NodeList nodeList = contentBlockElement.getChildNodes();
			for (int index = 0; index < nodeList.getLength(); index++) {
				if (nodeList.item(index) instanceof Element) {
					ContentItem contentItem = new ContentItem();
					createContentItem((Element) (nodeList.item(index)),
							contentItem);
					contentItemList.add(contentItem);
				}
			}
		}

		contentBlock.setContentItemsList(contentItemList);
	}

	public void createContentItem(Element contentItemElement,
			ContentItem contentItem) {
		if (!contentItemElement.getTagName().equals("Security")) {
			contentItem.setItemName(contentItemElement.getTagName());
		}

		NamedNodeMap attributeMap = contentItemElement.getAttributes();
		for (int index = 0; index < attributeMap.getLength(); index++) {
			if (attributeMap.item(index).getNodeName().equals("metric")) {
				contentItem.setMetric(attributeMap.item(index).getNodeValue());
			}

			if (attributeMap.item(index).getNodeName().equals("isMoreSelect")) {
				if (attributeMap.item(index).getNodeValue().equals("true")) {
					contentItem.setMoreSelect(true);
				}
			}

			if (attributeMap.item(index).getNodeName().equals("isRange")) {
				contentItem.setRange(true);
			}
		}

		NodeList nodeList = contentItemElement.getChildNodes();
		if (nodeList.getLength() <= 1) {
			if (nodeList.item(0) instanceof Text) {
				Text textNode = (Text) contentItemElement.getFirstChild();
				if ((textNode.getData().trim()).length() > 0) {
					contentItem.getValueList().add(textNode.getData().trim());
				}
			}

			contentItem.setItemDisplayMode(DISPLAYMODE2);
		}

		else if (nodeList.getLength() > 1) {

			Vector<String> tagNameVector = new Vector<String>();

			for (int index = 0; index < nodeList.getLength(); index++) {
				if (nodeList.item(index) instanceof Element) {
					Element childElement = (Element) nodeList.item(index);
					Text textNode = (Text) childElement.getFirstChild();
					contentItem.getValueList().add(textNode.getData().trim());

					if (((Element) childElement.getParentNode()).getTagName()
							.equals("Security")) {
						contentItem.setItemName(childElement.getTagName());
					}

					tagNameVector.add(childElement.getTagName());
				}
			}

			String name = tagNameVector.get(0);
			int index = 0;
			for (; index < tagNameVector.size(); index++) {
				if (tagNameVector.get(index) == name) {
					continue;
				}

				else
					break;
			}

			if (index >= tagNameVector.size() && index > 1) {
				contentItem.setItemDisplayMode(DISPLAYMODE1);
			}

			else
				contentItem.setItemDisplayMode(DISPLAYMODE2);
		}
	}

	public UIContent getUIContent() {
		return uiContent;
	}

	public void setUIContent(UIContent uiContent) {
		this.uiContent = uiContent;
	}

	public String getXmlName() {
		return xmlFileName;
	}

	public void setXmlName(String xmlName) {
		this.xmlFileName = xmlName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
}
