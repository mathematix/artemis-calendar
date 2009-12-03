package com.ics.tcg.web.user.client.panels;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ics.tcg.web.user.client.db.Issuer_Client;
import com.ics.tcg.web.user.client.db.ThirdPart_Client;
import com.ics.tcg.web.user.client.db.User_Issuer_Client;
import com.ics.tcg.web.user.client.db.User_ThirdPart_Client;
import com.ics.tcg.web.user.client.remote.ThirdPart_Service;
import com.ics.tcg.web.user.client.remote.ThirdPart_ServiceAsync;

public class List_ThirdPart extends VerticalPanel {

	/** widgets */
	Panel_Overview overview;
	PopupPanel contactPopup;
	MLabel selectedLabel;
	VerticalPanel tp_content;
	HTML contactInfo;
	PopupPanel add_popupPanel;
	TabPanel tabPanel;

	/** data */
	User_Issuer_Client uic = new User_Issuer_Client();
	ArrayList<MLabel> labels = new ArrayList<MLabel>();
	ThirdPart_ServiceAsync thirdPartService = GWT
			.create(ThirdPart_Service.class);

	/** start */
	List_ThirdPart(final Panel_Overview overview_panel) {

		overview = overview_panel;

		// Create a popup
		contactPopup = new PopupPanel(true, false);
		VerticalPanel contactPopupContainer = createPopup();
		contactPopup.setWidget(contactPopupContainer);

		// tab1
		final ScrollPanel scrollPanel1 = createTab1();

		// tab2
		final ScrollPanel scrollPanel2 = createTab2();

		// tab
		tabPanel = createTabPanel();
		tabPanel.add(scrollPanel1, "Certificate authority");
		tabPanel.add(scrollPanel2, "Sup...");
		tabPanel.selectTab(0);
		add(tabPanel);

		// toolbar
		HorizontalPanel toolbar = createToolbar();
		;
		this.add(toolbar);

		// change between the tabs
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

	/** create toolbar */
	public HorizontalPanel createToolbar() {
		HorizontalPanel toolbar = new HorizontalPanel();
		toolbar.setWidth("161");
		Hyperlink add = new Hyperlink("add", "add") {
			public void onBrowserEvent(Event event) {
				if (DOM.eventGetType(event) == Event.ONMOUSEOUT) {
					if (event.getClientX() < this.getAbsoluteLeft()
							|| event.getClientX() > this.getAbsoluteLeft() + 100
							|| event.getClientY() < this.getAbsoluteTop()
							|| event.getClientY() > this.getAbsoluteTop() + 200) {
						if (add_popupPanel != null) {
							add_popupPanel.hide();
						}
					}
				}
				super.onBrowserEvent(event);
			}
		};
		add.sinkEvents(Event.ONMOUSEOUT);
		add.setWidth("25");
		DOM.setStyleAttribute(add.getElement(), "fontFamily", "sans-serif");
		DOM.setStyleAttribute(add.getElement(), "fontSize", "10pt");
		toolbar.add(add);
		toolbar.setCellHorizontalAlignment(add, ALIGN_RIGHT);
		DOM.setStyleAttribute(toolbar.getElement(), "backgroundColor",
				"#d0e4f6");
		add.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				add_popupPanel = new PopupPanel(true, false) {
					public void onBrowserEvent(Event event) {
						if (DOM.eventGetType(event) == Event.ONMOUSEOUT) {
							if (event.getClientX() < this.getAbsoluteLeft()
									|| event.getClientX() > this
											.getAbsoluteLeft() + 100
									|| event.getClientY() < this
											.getAbsoluteTop()
									|| event.getClientY() > this
											.getAbsoluteTop() + 200) {
								this.hide();
							}
						}
						super.onBrowserEvent(event);
					}
				};
				add_popupPanel.sinkEvents(Event.ONMOUSEOUT);
				final ListBox tp_listbox = new ListBox(true);
				add_popupPanel.setSize("100", "200");
				tp_listbox.setWidth("100%");
				tp_listbox.setHeight("100%");
				tp_listbox.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						if (tp_listbox.getItemCount() != 0) {
							addTP(tp_listbox);
							add_popupPanel.hide();
						}
					}
				});
				VerticalPanel aPanel = new VerticalPanel();
				aPanel.setSize("100", "200");
				aPanel.add(tp_listbox);
				add_popupPanel.setWidget(aPanel);
				setThirdPartClients(add_popupPanel, tp_listbox);
			}
		});
		return toolbar;
	}

	public void setThirdPartClients(final PopupPanel popupPanel,
			final ListBox container) {
		thirdPartService.getTPs(new AsyncCallback<List<ThirdPart_Client>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("fail to get thirdparts");
			}

			@Override
			public void onSuccess(List<ThirdPart_Client> result) {
				container.clear();
				ArrayList<String> user_tp_names = new ArrayList<String>();
				for (int j = 0; j < labels.size(); j++) {
					user_tp_names.add(labels.get(j).getText());
				}
				for (int i = 0; i < result.size(); i++) {
					if (user_tp_names.contains(result.get(i).getTpname()) == false) {

						// Label label = new Label();
						// label.setSize("100%", "20");
						// label.setText(result.get(i).getTpname());
						// DOM.setStyleAttribute(label.getElement(), "border",
						// "solid 1px");
						container.addItem(result.get(i).getTpname(), Integer
								.toString(result.get(i).getTpid()));
					}
				}
				popupPanel.setPopupPosition(tabPanel.getAbsoluteLeft() + 160,
						tabPanel.getAbsoluteTop() - 30);
				popupPanel.show();
			}
		});
	}

	public void addTP(final ListBox listBox) {
		final User_ThirdPart_Client userThirdPartClient = new User_ThirdPart_Client();
		userThirdPartClient.setThirdpartid(Integer.parseInt(listBox
				.getValue(listBox.getSelectedIndex())));
		userThirdPartClient.setThirdpartname(listBox.getItemText(listBox
				.getSelectedIndex()));
		userThirdPartClient.setUserid(overview.userid);
		// set trust
		userThirdPartClient.setTrust(1.0);
		thirdPartService.saveUser_TP(userThirdPartClient,
				new AsyncCallback<Integer>() {
					@Override
					public void onSuccess(Integer result) {
						userThirdPartClient.setId(result);
						tp_content.clear();
						MLabel label = new MLabel(userThirdPartClient);
						labels.add(label);
						sortTPs();
						for (int j = 0; j < labels.size(); j++) {
							setColor(j);
							tp_content.add(labels.get(j));
						}
						// remove
						listBox.removeItem(listBox.getSelectedIndex());
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("fail to save uer tp");
					}
				});
	}

	/** create tabpanel */
	public TabPanel createTabPanel() {
		final TabPanel tabPanel = new TabPanel();
		tabPanel.setSize("100%", "165");
		tabPanel.removeStyleName("gwt-TabPanel");
		tabPanel.addStyleName("l-TabPanel");
		tabPanel.getTabBar().removeStyleName("gwt-TabBar");
		tabPanel.getTabBar().addStyleName("l-TabBar");
		tabPanel.getDeckPanel().removeStyleName("gwt-TabPanelBottom");
		tabPanel.getDeckPanel().addStyleName("l-TabPanelBottom");
		return tabPanel;
	}

	/** create popup */
	VerticalPanel createPopup() {
		// Create a pop up to show the contact info when a contact is clicked
		// with sinkevent
		final VerticalPanel contactPopupContainer = new VerticalPanel();
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
		contactPopup.sinkEvents(Event.ONMOUSEOUT);
		contactPopup.setSize("200", "100");
		contactPopupContainer.setSpacing(5);

		contactInfo = new HTML();
		final Hyperlink delete = new Hyperlink("delete", "delete");
		delete.setWidth("50");
		delete.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				rmTP(selectedLabel);
				contactPopup.hide();
			}
		});
		contactPopupContainer.add(contactInfo);
		contactPopupContainer.add(delete);

		return contactPopupContainer;
	}

	/** rm the thirdpart */
	public void rmTP(final MLabel label) {
		thirdPartService.deleteUser_TP(overview.userid,
				label.userthirdPartClient.getThirdpartid(),
				new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("fail to delete thirdpart");
					}

					@Override
					public void onSuccess(Void result) {
						labels.remove(label);
						sortTPs();
						tp_content.clear();
						for (int i = 0; i < labels.size(); i++) {
							setColor(i);
							tp_content.add(labels.get(i));
						}
					}
				});
	}

	/** create first tab content */
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

	/** init the issuer */
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
							// listBox.setSelectedIndex(0);
						} else {
							uic = null;
							listBox.setSelectedIndex(-1);

						}
					}
				});
	}

	/** every time to get the list of issuers */
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

	/** create the second panel */
	public ScrollPanel createTab2() {
		final VerticalPanel content = new VerticalPanel();
		tp_content = content;
		content.setSpacing(3);
		content.setWidth("100%");

		final ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setSize("161", "150");
		scrollPanel.add(content);

		initTP();
		return scrollPanel;
	}

	/** init tp */
	public void initTP() {
		thirdPartService.getUser_TPs(overview.userid,
				new AsyncCallback<List<User_ThirdPart_Client>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("fail to get user's tps");
					}

					@Override
					public void onSuccess(List<User_ThirdPart_Client> result) {
						if (result != null && result.size() != 0) {
							for (int i = 0; i < result.size(); i++) {
								MLabel label = new MLabel(result.get(i));
								labels.add(label);
							}
							sortTPs();
							for (int j = 0; j < labels.size(); j++) {
								setColor(j);
								tp_content.add(labels.get(j));
							}
						}
					}
				});
	}

	/** create dialog */
	public PopupPanel createDialog() {
		return null;
	}

	/** Mlabel implement mouse over and out handler */
	class MLabel extends Label {

		User_ThirdPart_Client userthirdPartClient;
		MLabel label = this;

		public MLabel(User_ThirdPart_Client userthirdPartClient) {
			super(userthirdPartClient.getThirdpartname());
			this.userthirdPartClient = userthirdPartClient;
			setWidth("100%");
			DOM.setStyleAttribute(this.getElement(), "cursor", "hand");
			DOM.setStyleAttribute(this.getElement(), "fontSize", "10pt");
			DOM.setStyleAttribute(this.getElement(), "color", "black");
			DOM.setStyleAttribute(this.getElement(), "padding", "2px");
			sinkEvents(Event.ONMOUSEOUT | Event.ONMOUSEOVER);
		}

		@Override
		public void onBrowserEvent(Event event) {
			if (DOM.eventGetType(event) == Event.ONMOUSEOVER) {
				contactPopup.hide();
				DOM.setStyleAttribute(getElement(), "color", "black");
				DOM.setStyleAttribute(getElement(), "filter",
						" Alpha(Opacity=50)");

				selectedLabel = (MLabel) label;
				// Set the info about the contact
				contactInfo.setHTML(setInfo(label.userthirdPartClient.getThirdpartname()));
				int left = label.getAbsoluteLeft() + label.getOffsetWidth() - 1;
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
				DOM.setStyleAttribute(this.getElement(), "color", "black");
			}
			super.onBrowserEvent(event);
		}
	}

	/** sort tp */
	void sortTPs() {
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

	/** set color for labels */
	void setColor(int i) {
		if (i % 2 == 1) {
			DOM.setStyleAttribute(labels.get(i).getElement(), "background",
					"#FFFFCC");
		} else {
			DOM.setStyleAttribute(labels.get(i).getElement(), "background",
					"#EFEFEF");
		}
	}

	/** set information */
	String setInfo(String name) {
		if (name.equals("thirdpart1")) {
			return "State Bureau of Quality and Technical Supervision";
		} else if (name.equals("thirdpart2")) {
			return "Provincial Bureau of Quality and Technical Supervision";
		} else if (name.equals("thirdpart3")) {
			return "Beijing Municipal Administration of Quality and Technology Supervisio";
		} else if (name.equals("thirdpart4")) {
			return "Nanjing Municipal Administration of Quality and Technology Supervisio";
		} else if (name.equals("thirdpart5")) {
			return "Shanghai Municipal Administration of Quality and Technology Supervisio";
		} else {
			return "";
		}
	}
}
