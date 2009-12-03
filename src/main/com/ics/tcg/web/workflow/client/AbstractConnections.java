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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pl.balon.gwt.diagrams.client.connection.Connection;
import pl.balon.gwt.diagrams.client.connector.Connector;
import pl.balon.gwt.diagrams.client.connector.Direction;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.PickupDragController;
import com.allen_sauer.gwt.dragdrop.client.drop.BoundaryDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.VetoDropException;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.ics.tcg.web.workflow.client.common.customline.CustomConnection;
import com.ics.tcg.web.workflow.client.common.customline.CustomUIObjectConnector;
import com.ics.tcg.web.workflow.client.composite.complement.MyPanel;
import com.ics.tcg.web.workflow.client.task.TimerNode;
import com.ics.tcg.web.workflow.client.task.Workflowtasknode;

/**
 * Base examples tab for gwt-diagrams connections examples.
 * 
 * @author Michał Baliński (michal.balinski@gmail.com)
 */
@SuppressWarnings( { "unchecked", "deprecation" })
public abstract class AbstractConnections extends AbstractEditRegion {

	/**
	 * DnD controller
	 */
	protected final DragController dragController;

	public AbsolutePanel panel = null;

	/**
	 * List of connections presented in this example
	 */

	private List connections = new ArrayList();

	/**
	 * Indicates if this example is already initialized.
	 */
	private boolean initialized = false;

	/**
	 * Constructor, sets up example.
	 */
	public AbstractConnections() {
		this.dragController = createDragController(getArea());

		// 把这两行注释掉，发现拖动节点到编辑区之后，左边栏的显示变成空白
		DOM.setStyleAttribute(getArea().getElement(), "width", "400px");
		DOM.setStyleAttribute(getArea().getElement(), "height", "300px");

	}

	/**
	 * Creates example contents (sets up connectors and connections)
	 */
	protected void createContents() {

	}

	/**
	 * Adds connection to examples panel.
	 * 
	 * @param c
	 *            connection to be added
	 */
	protected void add(CustomConnection c, AbsolutePanel panel) {
		// panel.add(c);
		// DOM.setStyleAttribute(c.getElement(), "cursor", "hand");
		c.appendTo(panel);
		connections.add(c);
	}

	/**
	 * Removes connection from examples panel.
	 * 
	 * @param c
	 */
	protected void remove(Connection c) {
		connections.remove(c);
	}

	/**
	 * Recalculates and redraws all connections in this example
	 */
	public void update() {
		initializeIfNecessary();
		for (Iterator i = connections.iterator(); i.hasNext();) {
			Connection c = (Connection) i.next();
			c.update();
		}
	}

	/**
	 * Initializes example.
	 */
	protected void initializeIfNecessary() {
		if (!initialized) {
			initialized = true;
			doAttachChildren();
		}
	}

	/**
	 * Prevents from attaching children when example is not initialized (eg. not
	 * visible/selected tab) - it's necessary to avoid vml curve problems on IE.
	 * 
	 * @see com.google.gwt.user.client.ui.Panel#doAttachChildren()
	 */
	@Override
	protected void doAttachChildren() {
		if (initialized) {
			super.doAttachChildren();
		}
	}

	/**
	 * @see com.google.gwt.user.client.ui.Widget#onDetach()
	 */
	@Override
	protected void onDetach() {
		if (initialized) {
			super.onDetach();
		}
	}

	/**
	 * Connects two connectors. To be implemented in child classes.
	 * 
	 * @param a
	 * @param b
	 */
	protected abstract void connect(Connector a, Connector b, Widget panel);

	/**
	 * Creates connector.
	 * 
	 * @param text
	 *            - label on connector
	 * @param left
	 *            - horizontal position
	 * @param top
	 *            - vertical position
	 * @param direction
	 *            - allowed direction of connection from this connector (null
	 *            means all directions_
	 * @return
	 */
	protected CustomUIObjectConnector createConnector(String text, int left,
			int top, Direction direction) {
		Label l = new Label(text);
		l.addStyleName("example-connector");
		getArea().add(l, left, top);
		dragController.makeDraggable(l);
		if (direction != null) {
			return CustomUIObjectConnector.wrap(l,
					new Direction[] { direction });
		}
		return CustomUIObjectConnector.wrap(l);
	}

	/**
	 * DnD stuff, not related to gwt-diagrams idea. It's only 'bonus' in this
	 * example app.
	 * 
	 * @return DragController
	 */
	protected DragController createDragController(Widget droptarget) { // 原文件中为无参类型
		PickupDragController dragController = new PickupDragController(
				(AbsolutePanel) droptarget, true) { // 原文件为true
			@Override
			public BoundaryDropController newBoundaryDropController(
					AbsolutePanel boundaryPanel, boolean allowDropping) {
				return new BoundaryDropController(boundaryPanel, allowDropping) {
					// public void onMove(Widget reference, Widget draggable,
					// DragController dragController) {
					// super.onMove(reference, draggable, dragController);
					// UIObjectConnector c = UIObjectConnector
					// .getWrapper(draggable);
					// if (c != null) {
					// c.update();
					// }
					// UIObjectConnector temp;
					// temp = UIObjectConnector
					// .getWrapper(((SimpleTask) draggable)
					// .getPanel2());
					// if (temp != null) {
					// temp.update();
					// }
					// temp = UIObjectConnector
					// .getWrapper(((SimpleTask) draggable)
					// .getPanel3());
					// if (temp != null) {
					// temp.update();
					// }
					// temp = UIObjectConnector
					// .getWrapper(((SimpleTask) draggable)
					// .getPanel4());
					// if (temp != null) {
					// temp.update();
					// }
					// //
					// // /*添加容器中每个子控件的响应事件，移动的时候，每个子控件相应的更新 */
					// // Grid panel =
					// // (Grid)((SimpleTask)draggable).GetBackPanel();
					// // for(int i=0;i<panel.getColumnCount();i++)
					// // for(int j=0;j<panel.getRowCount();j++){
					// // UIObjectConnector temp =
					// // UIObjectConnector.getWrapper(panel.getWidget(i,j));
					// // if(temp!=null){
					// // temp.update();
					// // }
					// // }
					//
					// }

					// 在节点拖动结束前，对移动节点的每条连线更新
					@Override
					public void onPreviewDrop(Widget reference,
							Widget draggable, DragController dragController)
							throws VetoDropException {
						super.onPreviewDrop(reference, draggable,dragController);
						Workflowtasknode workflowtasknode = (Workflowtasknode) draggable;

						// 更新节点中保存的坐标信息
						workflowtasknode.setPixel_x(workflowtasknode.getAbsoluteLeft()- panel.getAbsoluteLeft());
						workflowtasknode.setPixel_y(workflowtasknode.getAbsoluteTop()- panel.getAbsoluteTop());
						
						if(workflowtasknode.getAbsoluteLeft()+workflowtasknode.getOffsetWidth()>panel.getAbsoluteLeft()+panel.getOffsetWidth())
						{
							panel.setWidth(Integer.toString(workflowtasknode.getAbsoluteLeft()+
									workflowtasknode.getOffsetWidth()-panel.getAbsoluteLeft()));
						}

						if (workflowtasknode.getTimerNode() != null) {
							TimerNode timerNode = workflowtasknode.getTimerNode();
							AbsolutePanel backPanel = (AbsolutePanel) timerNode
									.getParent();
							timerNode.removeFromParent();
							backPanel.add(timerNode, workflowtasknode.getAbsoluteLeft()
									- backPanel.getAbsoluteLeft() - 32,workflowtasknode.getAbsoluteTop()- backPanel.getAbsoluteTop() - 24);
						}
						for (Iterator iterator = workflowtasknode.getconnectorList()
								.iterator(); iterator.hasNext();) {
							CustomUIObjectConnector c = CustomUIObjectConnector
									.getWrapper((MyPanel) iterator.next());
							if (c != null) {
								c.update();
							}
						}
					}
				};
			}

			@Override
			public void makeDraggable(Widget widget) {
				super.makeDraggable(widget);
				DOM.setStyleAttribute(widget.getElement(), "position",
						"absolute");
				DOM.setStyleAttribute(widget.getElement(), "zIndex", "100");
			}
		};
		return dragController;
	}

	/**
	 * Nothing really important. Presents links to source code on examples
	 * panel.
	 * 
	 * @see com.ics.tcg.web.workflow.client.AbstractEditRegion#sources()
	 */
	@Override
	protected List sources() {
		ArrayList sources = new ArrayList();

		int dot = GWT.getTypeName(this).lastIndexOf('.');
		String className = GWT.getTypeName(this).substring(dot + 1);

		sources.add(new Hyperlink(className + ".java", ""));
		sources.add(new Hyperlink("AbstractExample.java", ""));
		sources.add(new Hyperlink("AbstractConnectionsExample.java", ""));

		for (Iterator i = sources.iterator(); i.hasNext();) {
			final Hyperlink h = (Hyperlink) i.next();
			h.addStyleName("gwt-diagrams-source-link");
			h.addClickListener(new ClickListener() {
				public void onClick(Widget sender) {
					Window.open("../source/" + h.getText(), "", "");
				}
			});
		}

		return sources;
	}

}
