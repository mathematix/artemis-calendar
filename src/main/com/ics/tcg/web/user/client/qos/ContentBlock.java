package com.ics.tcg.web.user.client.qos;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

//This class is used to create the content of qos.
public class ContentBlock implements Serializable {
	/**
	 * This class is created by lms.
	 */
	private static final long serialVersionUID = -3724547665382537178L;

	private String contentBlockName;//The name of content of qos
	private List<ContentItem> contentItemsList;//The items of content 

	public ContentBlock() {
		contentBlockName = new String();
		contentItemsList = new LinkedList<ContentItem>();
	}

	public String getContentBlockName() {
		return contentBlockName;
	}

	public void setContentBlockName(String contentBlockName) {
		this.contentBlockName = contentBlockName;
	}

	public List<ContentItem> getContentItemsList() {
		return contentItemsList;
	}

	public void setContentItemsList(List<ContentItem> contentItemsList) {
		this.contentItemsList = contentItemsList;
	}
}
