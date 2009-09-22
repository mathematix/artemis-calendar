package com.ics.tcg.web.user.client.qos;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ContentBlock implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3724547665382537178L;

	private String contentBlockName;
	private List<ContentItem> contentItemsList;

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
