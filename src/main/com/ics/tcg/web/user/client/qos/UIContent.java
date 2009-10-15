package com.ics.tcg.web.user.client.qos;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class UIContent implements Serializable {
	/**
	 * This class is used to describe the content of UI which is used to display qos information. 
	 * @author lms
	 */
	private static final long serialVersionUID = -7302013946490698890L;

	private String serviceName;
	private List<ContentBlock> contentBlocksList;

	public UIContent() {
		serviceName = new String();
		contentBlocksList = new LinkedList<ContentBlock>();
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public List<ContentBlock> getContentBlocksList() {
		return contentBlocksList;
	}

	public void setContentBlocksList(List<ContentBlock> contentBlocksList) {
		this.contentBlocksList = contentBlocksList;
	}
}
