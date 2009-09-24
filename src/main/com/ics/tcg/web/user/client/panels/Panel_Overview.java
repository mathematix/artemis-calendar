package com.ics.tcg.web.user.client.panels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ics.tcg.web.user.client.User_Main;
import com.ics.tcg.web.user.client.db.AppointmentMod;
import com.ics.tcg.web.user.client.db.User_Client;
import com.ics.tcg.web.user.client.db.User_Service_Client;
import com.ics.tcg.web.user.client.remote.Calendar_Service;
import com.ics.tcg.web.user.client.remote.Calendar_ServiceAsync;
import com.ics.tcg.web.user.client.remote.Friend_Service;
import com.ics.tcg.web.user.client.remote.Friend_ServiceAsync;
import com.ics.tcg.web.user.client.remote.Mail_Service;
import com.ics.tcg.web.user.client.remote.Mail_ServiceAsync;
import com.ics.tcg.web.user.client.remote.Service_Service;
import com.ics.tcg.web.user.client.remote.Service_ServiceAsync;
import com.ics.tcg.web.user.client.remote.User_Service;
import com.ics.tcg.web.user.client.remote.User_ServiceAsync;
import com.ics.tcg.web.user.client.widgets.YellowFadeHandler;

@SuppressWarnings("deprecation")
public class Panel_Overview extends DockPanel {
	/** panels */
	public User_Main userMain;
	public VerticalPanel northPanel;// north part
	public AbsolutePanel westPanel;// left part
	public VerticalPanel centerPanel;// center part
	public Panel_Workflow workflowpanel;
	public Panel_Setting settingpanel;
	public Panel_Rating ratingPanel;
	public Panel_Calendar calendarPanel;
	public Panel_Mail mailPanel;
	public Label infolabel;
	protected Hyperlink hypermail;

	/** data */
	Panel_Overview overviewPanel = this;
	public int userid = -1;
	public User_Client userClient;
	public List<User_Service_Client> user_Service_Clients = new ArrayList<User_Service_Client>();
	public YellowFadeHandler fadeHandler = new YellowFadeHandler();

	/** remote */
	public Calendar_ServiceAsync calendar_Service = GWT
			.create(Calendar_Service.class);
	public Friend_ServiceAsync friend_Service = GWT
			.create(Friend_Service.class);
	public Service_ServiceAsync service_Service = GWT
			.create(Service_Service.class);
	public User_ServiceAsync user_Service = GWT.create(User_Service.class);
	public Mail_ServiceAsync mail_Service = GWT.create(Mail_Service.class);

	/**
	 * main
	 * */
	public Panel_Overview(final User_Main user_main) {

		super();

		userMain = user_main;
		userid = user_main.userid;
		userClient = user_main.userClient;

		calendarPanel = new Panel_Calendar(this);

		/** northpanel takes 80px height */
		northPanel = createNorthPanel();// north part
		westPanel = createWestPanel();// left part
		centerPanel = createCenterPanel();// center part

		overviewPanel.add(northPanel, DockPanel.NORTH);
		overviewPanel.add(westPanel, DockPanel.WEST);
		overviewPanel.add(centerPanel, DockPanel.CENTER);

		overviewPanel.setCellHeight(northPanel, "80");
		overviewPanel.setCellWidth(westPanel, "172");
		overviewPanel.setCellWidth(centerPanel, "100%");

		// Create a new timer
		Timer t = new Timer() {
			@Override
			public void run() {
				mail_Service.getUnreadMailCount(userid,
						new AsyncCallback<Integer>() {
							@Override
							public void onFailure(Throwable caught) {
								Window.alert("fail to get mail account");
							}

							@Override
							public void onSuccess(Integer result) {
								if (result != 0) {
									hypermail
											.setHTML("<DIV style='font-size:10pt;font-weight:bold;font-family:sans-serif'>Mailbox ("
													+ result + " )</DIV>");
								} else {
									hypermail
											.setHTML("<DIV style='font-size:10pt;font-family:sans-serif'>Mailbox</DIV>");
								}
							}
						});
			}
		};
		t.scheduleRepeating(30000);
	}

	public void init() {
		mail_Service.getUnreadMailCount(userid, new AsyncCallback<Integer>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("fail to get mail account");
			}

			@Override
			public void onSuccess(Integer result) {
				if (result != 0) {
					hypermail
							.setHTML("<DIV style='font-size:10pt;font-weight:bold;font-family:sans-serif'>Mailbox ("
									+ result + " )</DIV>");
				} else {
					hypermail
							.setHTML("<DIV style='font-size:10pt;font-family:sans-serif'>Mailbox</DIV>");
				}
			}
		});
	}

	/** show the panels in center */
	public void settings_show() {
		if (workflowpanel != null)
			workflowpanel.setVisible(false);
		calendarPanel.setVisible(false);
		if (settingpanel != null) {
			infolabel.setText("show settings");
			fadeHandler.add(infolabel);
			settingpanel.setVisible(true);
			settingpanel.init();
		}
		if (ratingPanel != null)
			ratingPanel.setVisible(false);
		if (mailPanel != null)
			mailPanel.setVisible(false);
	}

	public void workflow_show() {
		calendarPanel.setVisible(false);
		if (settingpanel != null)
			settingpanel.setVisible(false);
		if (ratingPanel != null)
			ratingPanel.setVisible(false);
		if (mailPanel != null)
			mailPanel.setVisible(false);
		if (workflowpanel != null) {
			infolabel.setText("show workflow");
			fadeHandler.add(infolabel);
			workflowpanel.setVisible(true);
		}
	}

	public void calendar_show() {
		if (settingpanel != null)
			settingpanel.setVisible(false);
		if (workflowpanel != null)
			workflowpanel.setVisible(false);
		if (ratingPanel != null)
			ratingPanel.setVisible(false);
		if (mailPanel != null)
			mailPanel.setVisible(false);
		if (calendarPanel != null) {
			calendarPanel.setVisible(true);
			init();
		}
	}

	public void rating_show() {
		if (settingpanel != null)
			settingpanel.setVisible(false);
		if (workflowpanel != null)
			workflowpanel.setVisible(false);
		if (mailPanel != null)
			mailPanel.setVisible(false);
		calendarPanel.setVisible(false);
		if (ratingPanel != null) {
			infolabel.setText("show Detail");
			fadeHandler.add(infolabel);
			ratingPanel.setVisible(true);
		}
	}

	public void mail_show() {
		if (settingpanel != null)
			settingpanel.setVisible(false);
		if (workflowpanel != null)
			workflowpanel.setVisible(false);
		if (mailPanel != null) {
			mailPanel.setVisible(true);
			mailPanel.init();
		}
		calendarPanel.setVisible(false);
		if (ratingPanel != null) {
			ratingPanel.setVisible(false);
		}
	}

	/** create northern panel */
	public VerticalPanel createNorthPanel() {

		VerticalPanel headPanel = new VerticalPanel();
		headPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		headPanel.setWidth("100%");

		Label h_User = new Label(userClient.getAccount());
		DOM.setStyleAttribute(h_User.getElement(), "fontFamily", "sans-serif");
		DOM.setStyleAttribute(h_User.getElement(), "fontSize", "10pt");

		Hyperlink h_Help = new Hyperlink("Help", "help");
		DOM.setStyleAttribute(h_Help.getElement(), "fontFamily", "sans-serif");
		DOM.setStyleAttribute(h_Help.getElement(), "fontSize", "10pt");

		Hyperlink h_Account = new Hyperlink("My account", "account");
		DOM.setStyleAttribute(h_Account.getElement(), "fontFamily",
				"sans-serif");
		DOM.setStyleAttribute(h_Account.getElement(), "fontSize", "10pt");

		Hyperlink h_Mail = new Hyperlink("Mailbox", "mail");
		hypermail = h_Mail;
		DOM.setStyleAttribute(h_Mail.getElement(), "fontFamily", "sans-serif");
		DOM.setStyleAttribute(h_Mail.getElement(), "fontSize", "10pt");

		Hyperlink h_Signout = new Hyperlink("Sign out", userMain.LOGIN_STATE);
		DOM.setStyleAttribute(h_Signout.getElement(), "fontFamily",
				"sans-serif");
		DOM.setStyleAttribute(h_Signout.getElement(), "fontSize", "10pt");

		h_Account.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				settings_show();
			}

		});

		h_Mail.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				mail_show();
			}
		});

		HTMLPanel h1 = new HTMLPanel(
				"<font face='Arial, sans-serif' size='-1'>&nbsp;|&nbsp;</font>");
		HTMLPanel h2 = new HTMLPanel(
				"<font face='Arial, sans-serif' size='-1'>&nbsp;|&nbsp;</font>");
		HTMLPanel h3 = new HTMLPanel(
				"<font face='Arial, sans-serif' size='-1'>&nbsp;|&nbsp;</font>");
		HTMLPanel h4 = new HTMLPanel(
				"<font face='Arial, sans-serif' size='-1'>&nbsp;|&nbsp;</font>");
		HTMLPanel h5 = new HTMLPanel("&nbsp;");

		HorizontalPanel hPanel = new HorizontalPanel();

		hPanel.add(h_User);
		hPanel.add(h1);
		hPanel.add(h_Help);
		hPanel.add(h2);
		hPanel.add(h_Account);
		hPanel.add(h3);
		hPanel.add(h_Mail);
		hPanel.add(h4);
		hPanel.add(h_Signout);
		hPanel.add(h5);
		headPanel.add(hPanel);
		hPanel.setHeight("20");
		headPanel.setCellWidth(hPanel, "100%");

		// image and search bar
		AbsolutePanel imagecontainer = new AbsolutePanel();
		Image image = new Image();
		image.setUrl("img/logo.png");
		image.setHeight("35");
		image.setWidth("272");
		imagecontainer.setWidth("100%");
		imagecontainer.setHeight("60");
		imagecontainer.add(image, 0, 5);

		TextBox searchbox = new TextBox();
		searchbox.setWidth("150");
		Button button = new Button("Search calendar");
		button.removeStyleName("gwt-Button");
		button.setWidth("120");
		imagecontainer.add(searchbox, 300, 20);
		imagecontainer.add(button, 460, 19);

		infolabel = new Label();
		DOM.setStyleAttribute(infolabel.getElement(), "fontSize", "10pt");
		DOM.setStyleAttribute(infolabel.getElement(), "color", "#CCCCCC");
		DOM.setStyleAttribute(infolabel.getElement(), "padding", "3px");
		DOM.setStyleAttribute(infolabel.getElement(), "fontWeight", "bold");
		imagecontainer.add(infolabel, 620, 15);

		headPanel.add(imagecontainer);

		return headPanel;
	}

	/** create western panel */
	public AbsolutePanel createWestPanel() {

		final AbsolutePanel west_Panel = new AbsolutePanel();
		west_Panel.setWidth("172");
		west_Panel.setHeight("490");

		Integer westheight = 5;

		// add link
		{
			VerticalPanel verticalPanel = new VerticalPanel();
			verticalPanel.setSpacing(3);
			Hyperlink createHyperlink = new Hyperlink("Create Event", "create");
			Hyperlink reviewHyperlink = new Hyperlink("Show Events", "show");
			DOM.setStyleAttribute(createHyperlink.getElement(), "fontFamily",
					"sans-serif");
			DOM.setStyleAttribute(createHyperlink.getElement(), "fontSize",
					"10pt");
			DOM.setStyleAttribute(createHyperlink.getElement(), "fontWeight",
					"bold");
			DOM.setStyleAttribute(reviewHyperlink.getElement(), "fontFamily",
					"sans-serif");
			DOM.setStyleAttribute(reviewHyperlink.getElement(), "fontSize",
					"10pt");

			verticalPanel.add(createHyperlink);
			verticalPanel.add(reviewHyperlink);

			west_Panel.add(verticalPanel, 0, westheight);

			westheight = westheight + 20 + 10;

			createHyperlink.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					AppointmentMod appt = new AppointmentMod();
					// set data
					appt.setTitle("New Event");
					appt.setDescription("New Event");
					Date date = new Date();
					appt
							.setStart(new Date(date.getYear(), date.getMonth(),
									date.getDate(), date.getHours(), date
											.getMinutes()));
					long Time = (date.getTime() / 1000) + 60 * 60;
					Date date2 = new Date();
					date2.setTime(Time * 1000);
					appt.setEnd(new Date(date2.getYear(), date2.getMonth(),
							date2.getDate(), date2.getHours(), date2
									.getMinutes()));
					if (date.getYear() != date2.getYear()
							|| date.getMonth() != date2.getMonth()
							|| date.getDate() != date2.getDate()) {
						appt.setMultiDay(true);
					}
					// set appt style
					appt.addStyleName("gwt-appointment-blue");
					appt.setAtoC();
					appt.calendarClient.setUserid(userid);
					appt.calendarClient.setDone(false);
					calendarPanel.workflowApp = appt;
					workflow_show();
					workflowpanel.onShow();
				}
			});

			reviewHyperlink.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent arg0) {
					rating_show();
					ratingPanel.showTable();
				}
			});
		}

		// add date picker, done in panel_googlecalendar
		{
			west_Panel.add(calendarPanel.leftPanel, 0, westheight);
			westheight = westheight + 170 + 10;
		}

		// friends list
		final DisclosurePanel advancedDisclosure_friends = new DisclosurePanel(
				"Friends");
		{
			advancedDisclosure_friends.getHeader();
			advancedDisclosure_friends.removeStyleName("gwt-DisclosurePanel");
			advancedDisclosure_friends.addStyleName("g-DisclosurePanel");
			List_Friend friendList = new List_Friend(this);
			advancedDisclosure_friends.add(friendList);
		}

		// third part list
		final DisclosurePanel advancedDisclosure_3part = new DisclosurePanel(
				"ThirdPart");
		{
			advancedDisclosure_3part.removeStyleName("gwt-DisclosurePanel");
			advancedDisclosure_3part.addStyleName("g-DisclosurePanel");
			List_ThirdPart list_ThirdPart = new List_ThirdPart(this);
			advancedDisclosure_3part.add(list_ThirdPart);
		}

		// service list
		final DisclosurePanel advancedDisclosure_service = new DisclosurePanel(
				"Service List");
		{
			advancedDisclosure_service.removeStyleName("gwt-DisclosurePanel");
			advancedDisclosure_service.addStyleName("g-DisclosurePanel");
			List_Service list_Service = new List_Service(this);
			advancedDisclosure_service.add(list_Service);
		}

		// just one disclosure is allow to open at one time
		{
			advancedDisclosure_friends
					.addOpenHandler(new OpenHandler<DisclosurePanel>() {
						@Override
						public void onOpen(OpenEvent<DisclosurePanel> event) {
							if (event.getTarget().isOpen()) {
								advancedDisclosure_3part.setOpen(false);
								advancedDisclosure_service.setOpen(false);
							}
						}
					});

			advancedDisclosure_3part
					.addOpenHandler(new OpenHandler<DisclosurePanel>() {
						@Override
						public void onOpen(OpenEvent<DisclosurePanel> event) {
							if (event.getTarget().isOpen()) {
								advancedDisclosure_friends.setOpen(false);
								advancedDisclosure_service.setOpen(false);
							}
						}
					});

			advancedDisclosure_service
					.addOpenHandler(new OpenHandler<DisclosurePanel>() {
						@Override
						public void onOpen(OpenEvent<DisclosurePanel> event) {
							if (event.getTarget().isOpen()) {
								advancedDisclosure_3part.setOpen(false);
								advancedDisclosure_friends.setOpen(false);
							}
						}
					});
		}

		// add the disclosures in a panel
		VerticalPanel listContainer = new VerticalPanel();
		listContainer.setWidth("172");

		AbsolutePanel v1 = new AbsolutePanel();
		v1.setHeight("10");
		AbsolutePanel v2 = new AbsolutePanel();
		v2.setHeight("10");

		listContainer.add(advancedDisclosure_friends);
		listContainer.add(v1);
		listContainer.add(advancedDisclosure_3part);
		listContainer.add(v2);
		listContainer.add(advancedDisclosure_service);

		west_Panel.add(listContainer, 0, westheight);
		return west_Panel;

	}

	/** create western panel */
	public VerticalPanel createCenterPanel() {

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setWidth("100%");
		settingpanel = new Panel_Setting(overviewPanel);
		ratingPanel = new Panel_Rating(overviewPanel);
		mailPanel = new Panel_Mail(overviewPanel);
		workflowpanel = new Panel_Workflow(overviewPanel, calendarPanel);

		verticalPanel.add(workflowpanel);
		verticalPanel.add(settingpanel);
		verticalPanel.add(ratingPanel);
		verticalPanel.add(calendarPanel);
		verticalPanel.add(mailPanel);
		calendarPanel.setWidth("100%");
		calendar_show();

		return verticalPanel;
	}
}
