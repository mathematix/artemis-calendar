/*
 * Copyright 2007 Michał Baliński
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.ics.tcg.web.workflow.client;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Base class for examples.
 * 
 * @author Michał Baliński (michal.balinski@gmail.com)
 */
public abstract class AbstractEditRegion extends SimplePanel {

	/**
	 * Examples area.
	 */
	private AbsolutePanel area = new AbsolutePanel();

	/**
	 * Constructor.
	 */
	public AbstractEditRegion() {
		// addStyleName("gwt-diagrams-example");

		VerticalPanel vp = new VerticalPanel();
		add(vp);

		vp.add(area);
		// vp.add(createSourcesPanel(sources()));
	}

	/**
	 * Creates panel with links to source code.
	 * 
	 * @param sources
	 * @return
	 */
	@SuppressWarnings( { "unused", "unchecked" })
	private Widget createSourcesPanel(List sources) {
		FlowPanel fp = new FlowPanel();
		fp.addStyleName("gwt-diagrams-sources-panel");
		for (Iterator i = sources.iterator(); i.hasNext();) {
			Widget source = (Widget) i.next();
			fp.add(source);
		}
		return fp;
	}

	/**
	 * To be implemented in child classes.
	 */
	@SuppressWarnings("unchecked")
	protected abstract List sources();

	/**
	 * Return examples area widget.
	 */
	public AbsolutePanel getArea() {
		return area;
	}

	protected String getTestAttribute() {
		return getStyleName();
	}

}
