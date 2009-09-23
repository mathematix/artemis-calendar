package com.ics.tcg.web.user.client.panels;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ics.tcg.web.user.client.db.AbstractService_Client;
import com.ics.tcg.web.user.client.db.User_Service_Client;
import com.ics.tcg.web.user.client.qos.QosDialog;

public class List_Service extends VerticalPanel {

	/** data */
	String namepre = "";
	Image imagepre = new Image();
	Integer select_service_id = -1;
	String select_service_name = "";
	ArrayList<MLabel> labels = new ArrayList<MLabel>();
	/** widgets */
	protected MLabel selectedLabel;
	protected PopupPanel contactPopup;
	protected HTML contactInfo;
	protected VerticalPanel content;
	Panel_Overview overview_panel;

	/** list service */
	List_Service(final Panel_Overview overview_panel) {

		this.overview_panel = overview_panel;

		// Create a popup
		VerticalPanel contactPopupContainer = createPopup();
		contactPopup.setWidget(contactPopupContainer);

		// create content
		content = new VerticalPanel();
		content.setSpacing(3);
		content.setWidth("100%");
		final ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setSize("161", "165");
		scrollPanel.add(content);

		// create toolbar
		HorizontalPanel toolbar = createToolbar();

		add(scrollPanel);
		add(toolbar);

		// get all abservices
		overview_panel.service_Service.getUserAbservices(overview_panel.userid,
				new AsyncCallback<List<User_Service_Client>>() {
					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(List<User_Service_Client> result) {
						if (result != null) {
							for (int i = 0; i < result.size(); i++) {
								// add to the list
								overview_panel.user_Service_Clients.add(result
										.get(i));
								final MLabel label = new MLabel(result.get(i));
								label.addStyleName(result.get(i).abservicename
										+ "-Label");
								labels.add(label);
							}
							sortServices();
							content.clear();
							for (int i = 0; i < labels.size(); i++) {
								content.add(labels.get(i));
							}
						}
					}
				});
	}

	/** label */
	class MLabel extends Label {

		User_Service_Client userServiceClient;
		MLabel label = this;

		public MLabel(User_Service_Client userServiceClient) {
			super(userServiceClient.abservicename);
			this.userServiceClient = userServiceClient;
			sinkEvents(Event.ONMOUSEOUT | Event.ONMOUSEOVER);
			setWidth("100%");
		}

		@Override
		public void onBrowserEvent(Event event) {
			if (DOM.eventGetType(event) == Event.ONMOUSEOVER) {
				DOM.setStyleAttribute(label.getElement(), "color", "black");
				DOM.setStyleAttribute(label.getElement(), "filter",
						" Alpha(Opacity=50)");
				selectedLabel = label;
				// Set the info about the contact
				contactInfo.setHTML(userServiceClient.abservicename + "<br><i>"
						+ "fuction:" + "</i><br>");
				int left = label.getAbsoluteLeft() + 154;
				int top = label.getAbsoluteTop();
				contactPopup.setPopupPosition(left, top);
				contactPopup.show();
			} else if (DOM.eventGetType(event) == Event.ONMOUSEOUT) {
				if (event.getClientX() < contactPopup.getAbsoluteLeft()
						|| event.getClientX() > contactPopup.getAbsoluteLeft() + 200
						|| event.getClientY() < contactPopup.getAbsoluteTop()
						|| event.getClientY() > contactPopup.getAbsoluteTop() + 100) {
					contactPopup.hide();
				}
				DOM.setStyleAttribute(this.getElement(), "filter",
						" Alpha(Opacity=100)");
				DOM.setStyleAttribute(this.getElement(), "color", "white");
			}
			super.onBrowserEvent(event);
		}
	}

	/** create popup */
	VerticalPanel createPopup() {
		VerticalPanel contactPopupContainer = new VerticalPanel();
		contactPopupContainer.setSpacing(5);
		contactInfo = new HTML();
		contactPopupContainer.add(contactInfo);
		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(15);
		final Hyperlink edit = new Hyperlink("edit", "edit");
		final Hyperlink delete = new Hyperlink("delete", "delete");
		delete.setWidth("50");
		edit.setWidth("50");
		edit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				QosDialog qosdialog = new QosDialog(
						selectedLabel.userServiceClient);
				qosdialog.setText("Qos Config");
				int height = Window.getClientHeight() / 2 - 200;
				int width = Window.getClientWidth() / 2 - 300;
				qosdialog.setPopupPosition(width, height);
				contactPopup.hide();
				qosdialog.show();

			}
		});
		delete.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				overview_panel.service_Service.deleteUserAbservice(
						overview_panel.userid,
						selectedLabel.userServiceClient.abserviceid,
						new AsyncCallback<String>() {
							@Override
							public void onFailure(Throwable caught) {
								Window.alert("failure");
							}

							@Override
							public void onSuccess(String result) {
								overview_panel.user_Service_Clients
										.remove(selectedLabel.userServiceClient);
								labels.remove(selectedLabel);
								content.remove(selectedLabel);
								contactPopup.hide();
							}
						});
			}
		});
		horizontalPanel.add(edit);
		horizontalPanel.add(delete);
		contactPopupContainer.add(horizontalPanel);
		contactPopup = new PopupPanel(true, false) {
			public void onBrowserEvent(Event event) {
				if (DOM.eventGetType(event) == Event.ONMOUSEOUT) {
					if (event.getClientX() < contactPopup.getAbsoluteLeft()
							|| event.getClientX() > contactPopup
									.getAbsoluteLeft() + 200
							|| event.getClientY() < contactPopup
									.getAbsoluteTop()
							|| event.getClientY() > contactPopup
									.getAbsoluteTop() + 100) {
						contactPopup.hide();
					}
				}
				super.onBrowserEvent(event);
			}
		};
		contactPopup.setSize("200", "100");
		contactPopup.sinkEvents(Event.ONMOUSEOUT);

		return contactPopupContainer;
	}

	/** create toolbar */
	HorizontalPanel createToolbar() {
		HorizontalPanel toolbar = new HorizontalPanel();
		toolbar.setWidth("161");
		Hyperlink add = new Hyperlink("add", "add");
		add.setWidth("25");
		DOM.setStyleAttribute(add.getElement(), "fontFamily", "sans-serif");
		DOM.setStyleAttribute(add.getElement(), "fontSize", "10pt");
		toolbar.add(add);
		DOM.setStyleAttribute(toolbar.getElement(), "backgroundColor",
				"#d0e4f6");
		add.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final DialogBox dialogBox = createBoxDialogBox();
				dialogBox.show();
			}
		});
		return toolbar;
	}

	/** show all abservices */
	void showAllAbservices(final FlexTable flexTable) {
		overview_panel.service_Service
				.getAllAbservices(new AsyncCallback<List<AbstractService_Client>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("fail");
					}

					@Override
					public void onSuccess(List<AbstractService_Client> result) {
						for (int i = 0; i < result.size(); i++) {
							final AbstractService_Client abstractService_Client = result
									.get(i);
							final Image image = new Image();
							image.setUrl("xml/" + abstractService_Client.asname
									+ ".jpg");
							image.setSize("80", "40");
							flexTable.setCellSpacing(20);
							flexTable.setWidget(i / 3, i % 3, image);
							image.addClickHandler(new ClickHandler() {
								@Override
								public void onClick(ClickEvent event) {
									imagepre.setUrl(namepre);
									image.setUrl("img/select_service.png");
									namepre = "xml/"
											+ abstractService_Client.asname
											+ ".jpg";
									imagepre = image;
									select_service_id = abstractService_Client.asid;
									select_service_name = abstractService_Client.asname;
								}
							});
							image.setTitle(abstractService_Client.asname);
						}
					}
				});
	}

	/** create dialogbox */
	DialogBox createBoxDialogBox() {

		final DialogBox dialogBox = new DialogBox();
		dialogBox.addStyleName("g-DialogBox");
		AbsolutePanel absolutePanel = new AbsolutePanel();
		absolutePanel.setPixelSize(400, 300);
		dialogBox.setWidget(absolutePanel);
		dialogBox.setText("Add Service");
		DOM.setStyleAttribute(dialogBox.getElement(), "border", "0px");

		int height = Window.getClientHeight() / 2 - 200;
		int width = Window.getClientWidth() / 2 - 250;
		dialogBox.setPopupPosition(width, height);

		final ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setSize("350", "240");
		final FlexTable flexTable = new FlexTable();
		scrollPanel.add(flexTable);
		scrollPanel.scrollToRight();
		HorizontalPanel confirm = new HorizontalPanel();
		confirm.setSpacing(10);
		final Button ok = new Button("Save");
		DOM.setStyleAttribute(ok.getElement(), "fontSiz3", "10pt");
		ok.removeStyleName("gwt-Button");
		final Button cancel = new Button("Cancel");
		DOM.setStyleAttribute(cancel.getElement(), "fontSiz3", "10pt");
		cancel.removeStyleName("gwt-Button");

		confirm.add(ok);
		confirm.add(cancel);
		absolutePanel.add(scrollPanel, 25, 25);
		absolutePanel.add(confirm, 25, 240);

		showAllAbservices(flexTable);

		ok.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				overview_panel.service_Service.check(overview_panel.userid,
						select_service_id, new AsyncCallback<Integer>() {
							@Override
							public void onFailure(Throwable caught) {
							}

							@Override
							public void onSuccess(Integer result) {
								if (result == 1) {
									Window.alert("It's already in the list!");
								} else {
									saveAbservice(dialogBox);
								}
							}
						});
			}
		});

		cancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
		});
		return dialogBox;
	}

	/**save a user abservice*/
	void saveAbservice(final DialogBox dialogBox) {
		overview_panel.service_Service.saveUserAbservice(overview_panel.userid,
				select_service_id, select_service_name,
				new AsyncCallback<User_Service_Client>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("fail to add service");
					}
					@Override
					public void onSuccess(User_Service_Client result) {
						overview_panel.user_Service_Clients.add(result);
						MLabel label = new MLabel(result);
						label.addStyleName(result.abservicename + "-Label");
						labels.add(label);
						dialogBox.hide();
						sortServices();
						content.clear();
						for (int i = 0; i < labels.size(); i++) {
							content.add(labels.get(i));
						}
					}
				});
	}

	/** sort services */
	void sortServices() {
		if (labels != null && labels.size() != 0) {
			for (int i = 0; i < labels.size(); i++) {
				for (int j = i + 1; j < labels.size(); j++) {
					if (labels.get(j).getText().compareToIgnoreCase(
							labels.get(i).getText()) < 0) {
						MLabel temp1 = labels.get(i);
						MLabel temp2 = labels.get(j);
						labels.remove(i);
						labels.add(i, temp2);
						labels.remove(j);
						labels.add(j, temp1);
					}
				}
			}
		}
	}
}
