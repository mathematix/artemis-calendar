package com.ics.tcg.web.user.client.panels;

import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ics.tcg.web.user.client.db.User_Service_Client;

public class List_ThirdPart extends VerticalPanel {

	String namepre;
	Image imagepre = new Image();
	/** for add to list */
	Integer select_service_id = -1;
	String select_service_name = "";

	// User_Service_Client selected_service = new User_Service_Client();
	MLabel selectedLabel;

	List_ThirdPart(final Panel_Overview overview_panel) {

		// Create a popup to show the contact info when a contact is clicked
		VerticalPanel contactPopupContainer = new VerticalPanel();
		contactPopupContainer.setSpacing(5);
		final HTML contactInfo = new HTML();
		contactPopupContainer.add(contactInfo);
		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(15);
		final Hyperlink edit = new Hyperlink("edit", "edit");
		final Hyperlink delete = new Hyperlink("delete", "delete");
		horizontalPanel.add(edit);
		horizontalPanel.add(delete);
		contactPopupContainer.add(horizontalPanel);
		final PopupPanel contactPopup = new PopupPanel(true, false);
		contactPopup.setWidget(contactPopupContainer);

		// container.setHeight("200");
		// tab1
		final VerticalPanel content1 = new VerticalPanel();
		content1.setSpacing(3);
		content1.setWidth("100%");

		final ScrollPanel scrollPanel1 = new ScrollPanel();
		scrollPanel1.setSize("161", "150");
		scrollPanel1.add(content1);

		final MLabel label1 = new MLabel("IBM");
		label1.addMouseOverHandler(new ThirdLabelHandler(label1));

		final MLabel label2 = new MLabel("MS");
		label2.addMouseOverHandler(new ThirdLabelHandler(label2));

		final MLabel label3 = new MLabel("AT&T");
		label3.addMouseOverHandler(new ThirdLabelHandler(label3));

		content1.add(label1);
		content1.add(label2);
		content1.add(label3);

		// tab2
		final VerticalPanel content2 = new VerticalPanel();
		content2.setSpacing(3);
		content2.setWidth("100%");

		final ScrollPanel scrollPanel2 = new ScrollPanel();
		scrollPanel2.setSize("161", "150");
		scrollPanel2.add(content2);

		final TabPanel tabPanel = new TabPanel();
		tabPanel.add(scrollPanel1, "Certificate authority");
		tabPanel.add(scrollPanel2, "Sup...");
		tabPanel.setSize("100%", "165");

		tabPanel.removeStyleName("gwt-TabPanel");
		tabPanel.addStyleName("l-TabPanel");
		tabPanel.getTabBar().removeStyleName("gwt-TabBar");
		tabPanel.getTabBar().addStyleName("l-TabBar");
		tabPanel.getDeckPanel().removeStyleName("gwt-TabPanelBottom");
		tabPanel.getDeckPanel().addStyleName("l-TabPanelBottom");
		
		this.add(tabPanel);

		tabPanel.selectTab(0);
		// toolbar
		HorizontalPanel toolbar = new HorizontalPanel();
		toolbar.setWidth("161");
		Hyperlink add = new Hyperlink("add", "add");
		add.setWidth("25");
		DOM.setStyleAttribute(add.getElement(), "fontFamily", "sans-serif");
		DOM.setStyleAttribute(add.getElement(), "fontSize", "10pt");
		toolbar.add(add);
		DOM.setStyleAttribute(toolbar.getElement(), "backgroundColor",
				"#d0e4f6");
		this.add(toolbar);

		tabPanel.addBeforeSelectionHandler(new BeforeSelectionHandler<Integer>() {
			@Override
			public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
				if (event.getItem().equals(0)) {
					tabPanel.getTabBar().setTabText(0, "Certificate authority");
					tabPanel.getTabBar().setTabText(1, "Sup...");
				}
				else {
					tabPanel.getTabBar().setTabText(0, "Cert..");
					tabPanel.getTabBar().setTabText(1, "Supervisory department");
				}
			}
		});
		
	}

	// label handler
	class ThirdLabelHandler implements MouseOverHandler {
		MLabel label;
		String account;
		HTML contactInfo;
		PopupPanel contactPopup;
		User_Service_Client user_Service_client;

		ThirdLabelHandler(MLabel label) {
			this.label = label;
			// this.contactInfo = contactInfo;
			// this.contactPopup = contactPopup;
			// this.user_Service_client = label.userServiceClient;

		}

		@Override
		public void onMouseOver(MouseOverEvent event) {
			// contactPopup.hide();
			DOM.setStyleAttribute(label.getElement(), "color", "black");
			DOM.setStyleAttribute(label.getElement(), "filter",
					" Alpha(Opacity=50)");
			// selectedLabel = label;
			// // Set the info about the contact
			// contactInfo.setHTML(user_Service_client.abservicename + "<br><i>"
			// + "fuction:" + "</i><br>");
			// // Show the popup of contact info
			// int left = label.getAbsoluteLeft() + 165;
			// int top = label.getAbsoluteTop();
			// contactPopup.setPopupPosition(left, top);
			// contactPopup.show();
		}

	}

	// label
	class MLabel extends Label {

		// User_Service_Client userServiceClient;

		public MLabel(String name) {
			super(name);
			// this.userServiceClient = userServiceClient;
			sinkEvents(Event.ONMOUSEOUT);
			setWidth("100%");
			DOM.setStyleAttribute(this.getElement(), "cursor", "hand");
			DOM.setStyleAttribute(this.getElement(), "fontSize", "10pt");
			DOM.setStyleAttribute(this.getElement(), "color", "white");
			DOM.setStyleAttribute(this.getElement(), "padding", "2px");
			// add color
			DOM.setStyleAttribute(this.getElement(), "background", "#"
					+ Integer.toHexString(Random.nextInt(0xf))
					+ Integer.toHexString(Random.nextInt(0xf))
					+ Integer.toHexString(Random.nextInt(0xf))
					+ Integer.toHexString(Random.nextInt(0xf))
					+ Integer.toHexString(Random.nextInt(0xf))
					+ Integer.toHexString(Random.nextInt(0xf)));
		}

		@Override
		public void onBrowserEvent(Event event) {

			if (DOM.eventGetType(event) == Event.ONMOUSEOUT) {
				DOM.setStyleAttribute(this.getElement(), "filter",
						" Alpha(Opacity=100)");
				DOM.setStyleAttribute(this.getElement(), "color", "white");
			}
			super.onBrowserEvent(event);
		}
	}

}
