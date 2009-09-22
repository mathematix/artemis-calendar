package com.ics.tcg.web.user.client.panels;

import java.util.Date;
import java.util.List;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.AppointmentInterface;
import com.bradrydzewski.gwt.calendar.client.CalendarSettings;
import com.bradrydzewski.gwt.calendar.client.DayView;
import com.bradrydzewski.gwt.calendar.client.CalendarSettings.Click;
import com.bradrydzewski.gwt.calendar.client.event.DeleteEvent;
import com.bradrydzewski.gwt.calendar.client.event.DeleteHandler;
import com.bradrydzewski.gwt.calendar.client.event.TimeBlockClickEvent;
import com.bradrydzewski.gwt.calendar.client.event.TimeBlockClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedTabBar;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.ics.tcg.web.user.client.db.AppointmentMod;
import com.ics.tcg.web.user.client.db.Calendar_Client;

@SuppressWarnings("deprecation")
public class Panel_Calendar extends AbsolutePanel {

	private Panel_Calendar calendarpanel = this;
	private Panel_Overview overviewpanel;
	public DayView dayView = null;
	private DatePicker datePicker = new DatePicker();
	public FlexTable layoutTable = new FlexTable();
	public AbsolutePanel leftPanel = new AbsolutePanel();
	private AbsolutePanel topPanel = new AbsolutePanel();
	private DecoratorPanel dayViewDecorator = new DecoratorPanel();
	private DecoratorPanel datePickerDecorator = new DecoratorPanel();
	private DecoratedTabBar daysTabBar = new DecoratedTabBar();
	private PopupPanel calendarPopup;
	private PopupPanel calendarPopup_bottom;
	private HTML html_showTime = new HTML();

	private CalendarSettings settings = new CalendarSettings();

	/** data */
	private boolean create_show = false;
	private boolean ifCreate = false;
	private AppointmentMod createApp;
	public AppointmentMod workflowApp;
	private AppointmentMod selectApp;
	private String text_What;
	private String text_Detail;
	private Date datepicked = new Date();// today next pre
	private DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd");

	public Panel_Calendar(Panel_Overview overview) {

		overviewpanel = overview;
		calendarPopup = createPopupPanel();

		// change hour offset to false to facilitate google style
		settings.setTimeBlockClickNumber(Click.Single);
		settings.setEnableDragDrop(true);
		settings.setIntervalsPerHour(4);
		// create day view
		dayView = new DayView(settings);
		// set style as google-cal
		dayView.setWidth("100%");
		// set today as default date
		datePicker.setValue(new Date());

		datePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				datepicked = (Date) event.getValue().clone();
				dayView.setDate(event.getValue());

				sethtml_ShowTime();
			}
		});

		// delete
		dayView.addDeleteHandler(new DeleteHandler<AppointmentInterface>() {
			@Override
			public void onDelete(DeleteEvent<AppointmentInterface> event) {
				boolean commit = Window
						.confirm("Are you sure you want to delete appointment \""
								+ event.getTarget().getTitle() + "\"");
				if (commit == false) {
					event.setCancelled(true);
					System.out.println("cancelled appointment deletion");
				}
			}
		});

		dayView.addValueChangeHandler(new ValueChangeHandler<Appointment>() {

			@Override
			public void onValueChange(ValueChangeEvent<Appointment> event) {
				create_show = false;

				selectApp = (AppointmentMod) event.getValue();
				int x = event.getValue().getAbsoluteLeft();
				int y = event.getValue().getAbsoluteTop();

				((HTML) (((AbsolutePanel) (((AbsolutePanel) calendarPopup
						.getWidget()).getWidget(1))).getWidget(0)))
						.setHTML("<div>" + selectApp.getTitle() + "</div>");
				((HTML) (((AbsolutePanel) (((AbsolutePanel) calendarPopup
						.getWidget()).getWidget(1))).getWidget(1)))
						.setHTML("<div style='color:red'>(&nbsp"
								+ selectApp.getDescription() + "&nbsp)</div>");
				((HTML) (((AbsolutePanel) (((AbsolutePanel) calendarPopup
						.getWidget()).getWidget(1))).getWidget(2)))
						.setHTML("<div style='color:green'>"
								+ selectApp.getStart().toString()
								+ "&nbsp&nbsp-<br>&nbsp&nbsp&nbsp&nbsp&nbsp"
								+ "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
								+ selectApp.getEnd().toString() + "</div>");
				showDialog(x, y, selectApp.getOffsetWidth());
			}
		});

		calendarPopup.addCloseHandler(new CloseHandler<PopupPanel>() {

			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				if (selectApp != null) {
					selectApp.setSelected(false);
					dayView.deselectAppointment();
					selectApp = null;
				}
				calendarPopup_bottom.hide();
				if (createApp != null) {
					if (ifCreate == false) {
						dayView.removeAppointment(createApp);
						createApp = null;
					} else {
						dayView.updateAppointment(createApp);
						ifCreate = false;
						createApp = null;
					}
				} else {
					ifCreate = false;
				}
			}

		});

		/** add a new appointment */
		dayView.addTimeBlockClickHandler(new TimeBlockClickHandler<Date>() {
			@Override
			public void onTimeBlockClick(TimeBlockClickEvent<Date> event) {
				create_show = true;
				AppointmentMod appt = new AppointmentMod();
				createApp = appt;
				// set data
				appt.setTitle("New Event");
				appt.setStart(new Date(event.getTarget().getYear(), event
						.getTarget().getMonth(), event.getTarget().getDate(),
						event.getTarget().getHours(), event.getTarget()
								.getMinutes()));
				long Time = (event.getTarget().getTime() / 1000) + 60 * 60;
				Date date = new Date();
				date.setTime(Time * 1000);
				appt.setEnd(new Date(date.getYear(), date.getMonth(), date
						.getDate(), date.getHours(), date.getMinutes()));
				if (event.getTarget().getYear() != date.getYear()
						|| event.getTarget().getMonth() != date.getMonth()
						|| event.getTarget().getDate() != date.getDate()) {
					appt.setMultiDay(true);
				}
				// set appt style
				appt.addStyleName("gwt-appointment-purple");

				// add the appointment to dayview

				dayView.addAppointment(createApp);

				// show the popup dialog
				((HTML) (((AbsolutePanel) (((AbsolutePanel) calendarPopup
						.getWidget()).getWidget(0))).getWidget(3)))
						.setHTML("<div style='color:green'>"
								+ event.getTarget().toString()
								+ "&nbsp&nbsp-<br>&nbsp&nbsp&nbsp&nbsp&nbsp"
								+ "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
								+ date.toString() + "</div>");
				((TextBox) (((AbsolutePanel) (((AbsolutePanel) calendarPopup
						.getWidget()).getWidget(0))).getWidget(4))).setText("");
				text_What = "";
				text_Detail = "";
				((TextBox) (((AbsolutePanel) (((AbsolutePanel) calendarPopup
						.getWidget()).getWidget(0))).getWidget(5))).setText("");

				showDialog(appt.getAbsoluteLeft(), appt.getAbsoluteTop(), appt
						.getOffsetWidth());

			}

		});

		daysTabBar.addTab("1 Day");
		daysTabBar.addTab("3 Day");
		daysTabBar.addTab("Work Week");
		daysTabBar.addTab("Week");
		daysTabBar.selectTab(2);
		dayView.setDays(5);
		daysTabBar.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				int dayIndex = event.getSelectedItem();
				if (dayIndex == 0) {
					dayView.setDays(1);
				} else if (dayIndex == 1) {
					dayView.setDays(3);
				} else if (dayIndex == 2)
					dayView.setDays(5);
				else if (dayIndex == 3)
					dayView.setDays(7);
				sethtml_ShowTime();
			}
		});

		AbsolutePanel todaypanel = createToday();

		topPanel.add(daysTabBar);
		topPanel.setStyleName("daysTabBar");
		topPanel.add(todaypanel, 0, 0);

		leftPanel.setStyleName("leftPanel");

		leftPanel.add(datePickerDecorator);

		AbsolutePanel datepickervertical = new AbsolutePanel();
		datepickervertical.setSize("167", "140");
		DOM.setStyleAttribute(datepickervertical.getElement(), "background",
				"#C3D9FF");
		DOM.setStyleAttribute(datepickervertical.getElement(), "paddingLeft",
				"5px");
		DOM.setStyleAttribute(datepickervertical.getElement(), "PaddingBottom",
				"5px");
		DOM.setStyleAttribute(datePicker.getElement(), "border", "0px solid");
		datepickervertical.add(datePicker, 5, -5);
		datePickerDecorator.removeStyleName("gwt-DecoratorPanel");
		datePickerDecorator.addStyleName("g-DecoratorPanel");
		datePickerDecorator.add(datepickervertical);

		dayViewDecorator.removeStyleName("gwt-DecoratorPanel");
		dayViewDecorator.addStyleName("g-DecoratorPanel");
		dayViewDecorator.add(dayView);

		layoutTable.setWidth("100%");
		layoutTable.setCellPadding(0);
		layoutTable.setCellSpacing(0);
		layoutTable.setText(0, 0, "");
		layoutTable.setWidget(0, 0, topPanel);
		layoutTable.setWidget(1, 0, dayViewDecorator);
		layoutTable.getCellFormatter().setVerticalAlignment(1, 0,
				HasVerticalAlignment.ALIGN_TOP);
		layoutTable.getCellFormatter().setVerticalAlignment(1, 1,
				HasVerticalAlignment.ALIGN_TOP);
		layoutTable.getCellFormatter().setWidth(1, 0, "50px");

		add(layoutTable);

		// generate appointments
		generateAppointments();

		// window events to handle resizing
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				int h = event.getHeight();
				if (calendarpanel.isVisible() == true) {
					dayView.setHeight(h - 130 + "px");
				}

			}
		});
		DeferredCommand.addCommand(new Command() {
			@Override
			public void execute() {
				dayView.setHeight(Window.getClientHeight() - 130 + "px");
				dayView.scrollToHour(6);
			}
		});

		// DOM.setStyleAttribute(getElement(), "padding", "10px");
	}

	/** Generate appoinmentents from server */
	public void generateAppointments() {

		dayView.doLayout();

		overviewpanel.calendar_Service.getAllCalendars(overviewpanel.userid,
				new AsyncCallback<List<Calendar_Client>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("fail to get calendars");
					}

					@Override
					public void onSuccess(List<Calendar_Client> result) {
						if (result == null) {
							// if (result==null||result.size() == 0) {
						} else {
							for (int i = 0; i < result.size(); i++) {
								AppointmentMod temp = new AppointmentMod();
								temp
										.setStart(new Date(result.get(i)
												.getStartTime().getYear(),
												result.get(i).getStartTime()
														.getMonth(), result
														.get(i).getStartTime()
														.getDate(), result.get(
														i).getStartTime()
														.getHours(), result
														.get(i).getStartTime()
														.getMinutes()));
								temp.setEnd(new Date(result.get(i).getEndTime()
										.getYear(), result.get(i).getEndTime()
										.getMonth(), result.get(i).getEndTime()
										.getDate(), result.get(i).getEndTime()
										.getHours(), result.get(i).getEndTime()
										.getMinutes()));
								temp.addStyleName("gwt-appointment-blue");
								temp.setTitle(result.get(i).getEventname());
								temp.setDescription(result.get(i).getDes());
								temp.calendarClient = result.get(i);
								if (result.get(i).getStartTime().getYear() != result
										.get(i).getEndTime().getYear()
										|| result.get(i).getStartTime()
												.getMonth() != result.get(i)
												.getEndTime().getMonth()
										|| result.get(i).getStartTime()
												.getDate() != result.get(i)
												.getEndTime().getDate()) {
									temp.setMultiDay(true);
								}
								dayView.addAppointment(temp);
							}
						}
					}
				});

		// dayView.resumeLayout();
	}

	/** create the calendar popup */
	PopupPanel createPopupPanel() {
		// popup and container
		final AbsolutePanel popupContainer = new AbsolutePanel();
		AbsolutePanel createview = new AbsolutePanel();
		AbsolutePanel modiview = new AbsolutePanel();
		createview.setSize("400", "165");
		modiview.setSize("400", "165");
		popupContainer.setSize("400", "165");
		popupContainer.add(createview, 0, 0);
		popupContainer.add(modiview, 0, 0);
		modiview.setVisible(false);

		final PopupPanel contactPopup = new PopupPanel(true, false);
		contactPopup.removeStyleName("gwt-PopupPanel");
		DOM.setStyleAttribute(contactPopup.getElement(), "background",
				"transparent url(img/googledialog.png) no-repeat");
		contactPopup.setWidget(popupContainer);
		contactPopup.setSize("400", "165");

		// set content of createview
		{
			// create labels and textbox
			{
				Label lblWhen = new Label("When:");// when
				createview.add(lblWhen, 20, 15);
				DOM.setStyleAttribute(lblWhen.getElement(), "fontSize", "10pt");
				Label lblWhat = new Label("What:");// what
				createview.add(lblWhat, 20, 55);
				DOM.setStyleAttribute(lblWhat.getElement(), "fontSize", "10pt");
				Label lblDescription = new Label("Detail:");// detail
				createview.add(lblDescription, 20, 85);
				DOM.setStyleAttribute(lblDescription.getElement(), "fontSize",
						"10pt");
				HTML html = new HTML();// when
				createview.add(html, 100, 15);
				html.setSize("283px", "36px");
				DOM.setStyleAttribute(html.getElement(), "fontSize", "9pt");
				TextBox textBox_what = new TextBox();// what
				textBox_what.removeStyleName("gwt-TextBox");
				createview.add(textBox_what, 100, 55);
				textBox_what.setSize("282px", "24px");
				DOM.setStyleAttribute(textBox_what.getElement(), "fontSize",
						"9pt");
				DOM.setStyleAttribute(textBox_what.getElement(), "padding",
						"1px");
				textBox_what
						.addValueChangeHandler(new ValueChangeHandler<String>() {
							@Override
							public void onValueChange(
									ValueChangeEvent<String> event) {
								text_What = event.getValue();
							}
						});
				textBox_what.setFocus(true);
				TextBox textBox_de = new TextBox();// detail
				textBox_de.removeStyleName("gwt-TextBox");
				createview.add(textBox_de, 100, 85);
				textBox_de.setSize("282px", "24px");
				DOM.setStyleAttribute(textBox_de.getElement(), "fontSize",
						"9pt");
				DOM
						.setStyleAttribute(textBox_de.getElement(), "padding",
								"1px");
				textBox_de
						.addValueChangeHandler(new ValueChangeHandler<String>() {
							@Override
							public void onValueChange(
									ValueChangeEvent<String> event) {
								text_Detail = event.getValue();
							}
						});
			}

			// close button
			Label closeLabel = new Label();
			closeLabel.removeStyleName("gwt-Label");
			closeLabel.setStyleName("dialog-close");
			createview.add(closeLabel);
			closeLabel.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ifCreate = false;
					contactPopup.hide();
					calendarPopup_bottom.hide();
				}
			});
			// add an event button
			Button addButton = new Button("Create Event");
			addButton.setSize("110", "25");
			addButton.removeStyleName("gwt-Button");
			DOM.setStyleAttribute(addButton.getElement(), "fontSize", "9pt");
			DOM.setStyleAttribute(addButton.getElement(), "padding", "2px");
			DOM.setStyleAttribute(addButton.getElement(), "verticalAlign",
					"middle");
			createview.add(addButton, 31, 125);
			addButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					// set data of appointment
					createApp.setTitle(text_What);
					createApp.setDescription(text_Detail);
					createApp.removeStyleName("gwt-appointment-purple");
					createApp.addStyleName("gwt-appointment-blue");

					// set data of calendar_Client
					createApp.setAtoC();// set 5 values
					createApp.calendarClient.setUserid(overviewpanel.userid);
					createApp.calendarClient.setDone(false);
					// no calendarid now, save to get it
					overviewpanel.calendar_Service.saveCalendar(
							createApp.calendarClient, null,
							new AsyncCallback<Integer>() {
								@Override
								public void onSuccess(Integer result) {
									ifCreate = true;
									createApp.calendarClient
											.setCalendarid(result);
									contactPopup.hide();
								}

								@Override
								public void onFailure(Throwable caught) {
									ifCreate = false;
									contactPopup.hide();
								}
							});
				}
			});
			// click to detail
			Hyperlink hyperlink = new Hyperlink("edit event details »",
					"detail");
			DOM.setStyleAttribute(hyperlink.getElement(), "fontSize", "9pt");
			createview.add(hyperlink, 160, 135);
			hyperlink.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					// set data of appointment
					createApp.setTitle(text_What);
					createApp.setDescription(text_Detail);
					createApp.removeStyleName("gwt-appointment-purple");
					createApp.addStyleName("gwt-appointment-blue");
					// set data of calendar_Client
					createApp.setAtoC();// set 5 values
					createApp.calendarClient.setUserid(overviewpanel.userid);
					createApp.calendarClient.setDone(false);
					// no calendarid now
					ifCreate = false;
					workflowApp = createApp;
					overviewpanel.workflow_show();
					overviewpanel.workflowpanel.onShow();
					calendarPopup.hide();
					calendarPopup_bottom.hide();
				}
			});
		}

		// create modifying view
		{
			// create htmls
			{
				HTML html1 = new HTML();// title
				HTML html2 = new HTML();// detail
				HTML html3 = new HTML();// time
				Hyperlink hyperlink4 = new Hyperlink("Delete", "del");// delete
				AbsolutePanel delete = new AbsolutePanel();
				{
					delete.setSize("100", "25");
					Label label = new Label("[          ]");
					DOM.setStyleAttribute(label.getElement(), "fontSize",
							"10pt");

					delete.add(label, 0, 0);
					delete.add(hyperlink4, 5, 0);
				}
				HTML html5 = new HTML();// -------

				modiview.add(html1, 20, 25);
				modiview.add(html2, 20, 45);
				modiview.add(html3, 20, 65);
				modiview.add(delete, 20, 105);
				modiview.add(html5, 20, 120);

				DOM.setStyleAttribute(html1.getElement(), "fontSize", "10pt");
				DOM.setStyleAttribute(html2.getElement(), "fontSize", "10pt");
				html3.setSize("283px", "36px");
				DOM.setStyleAttribute(html3.getElement(), "fontSize", "9pt");
				DOM.setStyleAttribute(hyperlink4.getElement(), "fontSize",
						"10pt");
				html5.setWidth("360");
				html5.setHTML("<hr color=#A32929 size=0 noshade>");

				// delete
				hyperlink4.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						overviewpanel.calendar_Service.deleteCalendar(
								selectApp.calendarClient.getCalendarid(),
								new AsyncCallback<String>() {
									@Override
									public void onFailure(Throwable caught) {
										calendarPopup.hide();
										calendarPopup_bottom.hide();
									}

									@Override
									public void onSuccess(String result) {
										dayView.removeAppointment(selectApp);
										selectApp = null;
										calendarPopup.hide();
										calendarPopup_bottom.hide();
									}
								});
					}
				});

			}
			// close button
			Label closeLabel = new Label();
			closeLabel.removeStyleName("gwt-Label");
			closeLabel.setStyleName("dialog-close");
			modiview.add(closeLabel);
			closeLabel.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					contactPopup.hide();
					calendarPopup_bottom.hide();
				}
			});

			// click to detail
			Hyperlink hyperlink = new Hyperlink("edit event details »",
					"detail");
			DOM.setStyleAttribute(hyperlink.getElement(), "fontSize", "9pt");
			modiview.add(hyperlink, 20, 135);
			hyperlink.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					workflowApp = selectApp;
					calendarPopup.hide();
					calendarPopup_bottom.hide();
					overviewpanel.workflow_show();
					overviewpanel.workflowpanel.onShow();
				}
			});
		}

		// init the bottom
		calendarPopup_bottom = new PopupPanel();
		Image image = new Image();
		image.setSize("100", "70");
		image.setUrl("img/googledialog_bottom.png");
		DOM.setStyleAttribute(image.getElement(), "background", "transparent");
		calendarPopup_bottom.setSize("100", "70");
		calendarPopup_bottom.add(image);
		calendarPopup_bottom.removeStyleName("gwt-PopupPanel");

		return contactPopup;
	}

	/** show dialogbox */
	void showDialog(int left, int top, double length) {
		double x1 = left + length * 0.7 - 105 - 100;
		double x2 = left + length * 0.7 - 105;
		double y;
		double x_b = left + length * 0.7;
		double y_b = top - 70;

		if (create_show == true) {
			((AbsolutePanel) (((AbsolutePanel) calendarPopup.getWidget())
					.getWidget(1))).setVisible(false);
			((AbsolutePanel) (((AbsolutePanel) calendarPopup.getWidget())
					.getWidget(0))).setVisible(true);
		} else {
			((AbsolutePanel) (((AbsolutePanel) calendarPopup.getWidget())
					.getWidget(0))).setVisible(false);
			((AbsolutePanel) (((AbsolutePanel) calendarPopup.getWidget())
					.getWidget(1))).setVisible(true);
		}

		if (top < 185) {
			y = top + 20;
			if (y < 0)
				y = 120;
			if (x1 >= Window.getClientWidth() - 420) {
				x1 = Window.getClientWidth() - 420;
			}
			calendarPopup.setPopupPosition((int) x1, (int) y);
			calendarPopup.show();
		}
		if (top >= 185 && top < 245) {
			y = top - 10 - 165;
			if (x1 >= Window.getClientWidth() - 420) {
				x1 = Window.getClientWidth() - 420;
			}
			calendarPopup.setPopupPosition((int) x1, (int) y);
			calendarPopup.show();
		}
		if (top > 245) {
			y = top - 165 - 69;
			if (x2 >= Window.getClientWidth() - 420) {
				x2 = Window.getClientWidth() - 420;
				y = top - 10 - 165;
				calendarPopup.setPopupPosition((int) x2, (int) y);
				calendarPopup.show();
			} else {
				calendarPopup.setPopupPosition((int) x2, (int) y);
				calendarPopup.show();
				calendarPopup_bottom.setPopupPosition((int) x_b, (int) y_b);
				calendarPopup_bottom.show();
			}
		}
	}

	AbsolutePanel createToday() {

		AbsolutePanel absolutePanel = new AbsolutePanel();
		absolutePanel.setSize("300", "25");
		Image image1 = new Image();
		Image image2 = new Image();
		image1.setUrl("img/dayleft.png");
		DOM.setStyleAttribute(image1.getElement(), "cursor", "hand");
		image2.setUrl("img/dayright.png");
		DOM.setStyleAttribute(image2.getElement(), "cursor", "hand");
		Button todayButton = new Button("Today");
		todayButton.removeStyleName("gwt-Button");
		DOM.setStyleAttribute(todayButton.getElement(), "fontSize", "8pt");

		image1.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (daysTabBar.getSelectedTab() == 0) {
					datepicked.setDate(datepicked.getDate() - 1);
				}
				if (daysTabBar.getSelectedTab() == 1) {
					datepicked.setDate(datepicked.getDate() - 3);
				}
				if (daysTabBar.getSelectedTab() == 2) {
					datepicked.setDate(datepicked.getDate() - 5);
				}
				if (daysTabBar.getSelectedTab() == 3) {
					datepicked.setDate(datepicked.getDate() - 7);
				}
				datePicker.setCurrentMonth(datepicked);
				datePicker.setValue(datepicked, true);
			}
		});

		image2.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (daysTabBar.getSelectedTab() == 0) {
					datepicked.setDate(datepicked.getDate() + 1);
				}
				if (daysTabBar.getSelectedTab() == 1) {
					datepicked.setDate(datepicked.getDate() + 3);
				}
				if (daysTabBar.getSelectedTab() == 2) {
					datepicked.setDate(datepicked.getDate() + 5);
				}
				if (daysTabBar.getSelectedTab() == 3) {
					datepicked.setDate(datepicked.getDate() + 7);
				}
				datePicker.setCurrentMonth(datepicked);
				datePicker.setValue(datepicked, true);

			}
		});

		todayButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				datepicked = new Date();
				datePicker.setCurrentMonth(datepicked);
				datePicker.setValue(datepicked, true);
			}
		});

		html_showTime.setWidth("150");

		Date date = new Date();
		date.setTime(datepicked.getTime() + 1000 * 60 * 60 * 24 * 4);
		html_showTime.setHTML("<div style='font-size:10pt;font-Weight:bold'>"
				+ dateFormat.format(datepicked) + " - "
				+ dateFormat.format(date) + "</div>");

		absolutePanel.add(image1, 0, 4);
		absolutePanel.add(image2, 33, 4);
		absolutePanel.add(todayButton, 65, 2);
		absolutePanel.add(html_showTime, 120, 4);

		return absolutePanel;
	}

	// set html_showTime's value
	void sethtml_ShowTime() {
		if (daysTabBar.getSelectedTab() == 0) {
			html_showTime
					.setHTML("<div style='font-size:10pt;font-Weight:bold'>"
							+ dateFormat.format(datepicked) + "</div>");
		}
		if (daysTabBar.getSelectedTab() == 1) {
			Date date = new Date();
			date.setTime(datepicked.getTime() + 1000 * 60 * 60 * 24 * 2);
			html_showTime
					.setHTML("<div style='font-size:10pt;font-Weight:bold'>"
							+ dateFormat.format(datepicked) + " - "
							+ dateFormat.format(date) + "</div>");
		}
		if (daysTabBar.getSelectedTab() == 2) {
			Date date = new Date();
			date.setTime(datepicked.getTime() + 1000 * 60 * 60 * 24 * 4);
			html_showTime
					.setHTML("<div style='font-size:10pt;font-Weight:bold'>"
							+ dateFormat.format(datepicked) + " - "
							+ dateFormat.format(date) + "</div>");
		}
		if (daysTabBar.getSelectedTab() == 3) {
			Date date = new Date();
			date.setTime(datepicked.getTime() + 1000 * 60 * 60 * 24 * 6);
			html_showTime
					.setHTML("<div style='font-size:10pt;font-Weight:bold'>"
							+ dateFormat.format(datepicked) + " - "
							+ dateFormat.format(date) + "</div>");
		}
	}
}
