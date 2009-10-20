package com.ics.tcg.web.user.client.panels;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ics.tcg.web.user.client.db.Issuer_Client;
import com.ics.tcg.web.user.client.db.User_Issuer_Client;
import com.ics.tcg.web.user.client.db.User_Service_Client;
import com.ics.tcg.web.user.client.remote.ThirdPart_Service;
import com.ics.tcg.web.user.client.remote.ThirdPart_ServiceAsync;

public class List_ThirdPart extends VerticalPanel {

	String namepre;
	Image imagepre = new Image();
	/** for add to list */
	Integer select_service_id = -1;
	String select_service_name = "";
	User_Issuer_Client uic = new User_Issuer_Client();

	Panel_Overview overview;

	ThirdPart_ServiceAsync thirdPartService = GWT
			.create(ThirdPart_Service.class);

	// User_Service_Client selected_service = new User_Service_Client();
	MLabel selectedLabel;

	List_ThirdPart(final Panel_Overview overview_panel) {

		overview = overview_panel;

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
		final ScrollPanel scrollPanel1 = createTab1();

		// tab2
		final ScrollPanel scrollPanel2 = createTab2();

		// tab
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
		add(tabPanel);
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

		tabPanel
				.addBeforeSelectionHandler(new BeforeSelectionHandler<Integer>() {
					@Override
					public void onBeforeSelection(
							BeforeSelectionEvent<Integer> event) {
						if (event.getItem().equals(0)) {
							tabPanel.getTabBar().setTabText(0,
									"Certificate authority");
							tabPanel.getTabBar().setTabText(1, "Sup...");
						} else {
							tabPanel.getTabBar().setTabText(0, "Cert..");
							tabPanel.getTabBar().setTabText(1,
									"Supervisory department");
						}
					}
				});

	}

	public ScrollPanel createTab1() {
		final VerticalPanel content = new VerticalPanel();
		content.setSpacing(3);
		content.setWidth("100%");

		final ScrollPanel scrollPanel1 = new ScrollPanel();
		scrollPanel1.setSize("161", "150");
		scrollPanel1.add(content);

		final ListBox listBox = new ListBox();
		listBox.setWidth("150");
		listBox.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				getIssuerList(listBox);
			}
		});
		listBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				if (uic == null) {
					uic = new User_Issuer_Client();
					uic.setIssuename(listBox.getItemText(listBox
							.getSelectedIndex()));
					uic.setIssuerid(Integer.parseInt(listBox.getValue(listBox
							.getSelectedIndex())));
					uic.setUserid(overview.userid);
					thirdPartService.saveUser_Issuer(uic,
							new AsyncCallback<Integer>() {
								@Override
								public void onFailure(Throwable caught) {
									Window.alert("update issuer failed");
								}

								@Override
								public void onSuccess(Integer result) {
									uic.setId(result);
								}
							});
				} else {
					uic.setIssuename(listBox.getItemText(listBox
							.getSelectedIndex()));
					uic.setIssuerid(Integer.parseInt(listBox.getValue(listBox
							.getSelectedIndex())));
					thirdPartService.updateUser_Issuer(uic,
							new AsyncCallback<Void>() {
								@Override
								public void onFailure(Throwable caught) {
									Window.alert("update issuer failed");
								}

								@Override
								public void onSuccess(Void result) {

								}
							});
				}
			}
		});

		content.add(listBox);
		initIssuer(listBox);
		return scrollPanel1;
	}

	public void initIssuer(final ListBox listBox) {
		thirdPartService.getUser_Issuers(overview.userid,
				new AsyncCallback<List<User_Issuer_Client>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("fail to get user issuer");
					}

					@Override
					public void onSuccess(List<User_Issuer_Client> result) {
						if (result != null && result.size() != 0) {
							uic = result.get(0);
							listBox.addItem(result.get(0).getIssuename(),
									Integer.toString(result.get(0)
											.getIssuerid()));
						//	listBox.setSelectedIndex(0);
						} else {
							uic = null;
							listBox.setSelectedIndex(-1);

						}
					}
				});
	}

	public void getIssuerList(final ListBox listBox) {
		thirdPartService.getIssuers(new AsyncCallback<List<Issuer_Client>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("fail to get issuers");
			}

			@Override
			public void onSuccess(List<Issuer_Client> result) {
				if (result != null) {
					listBox.clear();
					for (int i = 0; i < result.size(); i++) {
						listBox.addItem(result.get(i).getIssuername(), Integer
								.toString(result.get(i).getIssuerid()));
					}
				}
			}
		});
	}

	public ScrollPanel createTab2() {
		final VerticalPanel content2 = new VerticalPanel();
		content2.setSpacing(3);
		content2.setWidth("100%");

		final ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setSize("161", "150");
		scrollPanel.add(content2);

		return scrollPanel;
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
