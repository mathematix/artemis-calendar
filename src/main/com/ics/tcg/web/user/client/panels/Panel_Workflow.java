package com.ics.tcg.web.user.client.panels;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.ics.tcg.web.workflow.client.WorkflowEditModule;
import com.ics.tcg.web.workflow.client.data.Client_Workflow;

@SuppressWarnings("deprecation")
public class Panel_Workflow extends AbsolutePanel {

	/** panels */
	public WorkflowEditModule base;
	private Panel_Workflow panel_Workflow = this;
	private Panel_Overview overview_panel;
	private Panel_Calendar calendar_panel;
	public final AbsolutePanel workflowPanel_inner;
	TextBox textBox1;
	AbsolutePanel textBox2;
	TextArea textBox3;
	PopupPanel Popup;

	/** data */
	String text_What;
	String time_Start;
	String time_End;
	String text_Des;
	DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd/yyyy HH:mm");
	DateTimeFormat dateFormat1 = DateTimeFormat.getFormat("MM/dd/yyyy");
	DateTimeFormat dateFormat2 = DateTimeFormat.getFormat("HH:mm");

	/** workflow */
	public Panel_Workflow(Panel_Overview panel_Overview,
			Panel_Calendar panelGoogleCalendar) {

		overview_panel = panel_Overview;
		calendar_panel = panelGoogleCalendar;
		base = new WorkflowEditModule(overview_panel);
		base.onModuleLoad();

		// workflow style
		this.setSize("100%", "100%");
		DOM.setStyleAttribute(this.getElement(), "borderLeft",
				"10px solid #C3D9FF");
		DOM.setStyleAttribute(this.getElement(), "borderBottom",
				"10px solid #C3D9FF");

		// Create a pop up to show the contact info when a contact is clicked
		// final VerticalPanel contactPopupContainer = new VerticalPanel();
		Popup = new PopupPanel(true, false);
		Popup.removeStyleName("gwt-PopupPanel");
		Popup.setSize("80", "180");
		// Popup.setWidget();

		// head
		Label head = new Label("Event&Workflow");
		{
			head.setSize("100%", "30");
			DOM.setStyleAttribute(head.getElement(), "paddingTop", "6px");
			DOM.setStyleAttribute(head.getElement(), "paddingBottom", "3px");
			DOM.setStyleAttribute(head.getElement(), "fontFamily",
					"Arial, sans-serif");
			DOM.setStyleAttribute(head.getElement(), "fontSize", "13pt");
			DOM.setStyleAttribute(head.getElement(), "fontWeight", "bold");
			DOM.setStyleAttribute(head.getElement(), "backgroundColor",
					"#C3D9FF");
		}
		this.add(head, 0, 0);

		// confirm
		final AbsolutePanel confirm = new AbsolutePanel();
		{
			confirm.setWidth("100%");
			confirm.setHeight("30");
			DOM.setStyleAttribute(confirm.getElement(), "backgroundColor",
					"#E1EDF9");
			Hyperlink hyperlink = new Hyperlink("« Back to Calendar", "back");
			DOM.setStyleAttribute(hyperlink.getElement(), "fontSize", "10pt");
			hyperlink.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					overview_panel.calendar_show();
				}
			});
			Button ok = new Button("Save");
			Button cancel = new Button("Cancel");
			Button delete = new Button("Delete");
			ok.removeStyleName("gwt-Button");
			cancel.removeStyleName("gwt-Button");
			delete.removeStyleName("gwt-Button");
			DOM.setStyleAttribute(ok.getElement(), "fontSize", "8pt");
			DOM.setStyleAttribute(cancel.getElement(), "fontSize", "8pt");
			DOM.setStyleAttribute(delete.getElement(), "fontSize", "8pt");

			confirm.add(hyperlink, 10, 10);
			confirm.add(ok, 130, 5);
			confirm.add(cancel, 175, 5);
			confirm.add(delete, 228, 5);
		}
		this.add(confirm, 0, 30);

		// // hr
		// final HTMLPanel html = new HTMLPanel("<hr noshade size=0>");
		// html.setWidth("100%");
		// this.add(html, 10, 50);

		// calendar settings
		AbsolutePanel calendarsettings = new AbsolutePanel();
		calendarsettings.setWidth("100%");
		calendarsettings.setHeight("140");
		AbsolutePanel detailpanel = setDetail();
		calendarsettings.add(detailpanel, 10, 10);
		this.add(calendarsettings, 0, 60);

		// add workflow
		final AbsolutePanel workflowPanel_outer = new AbsolutePanel();
		workflowPanel_outer.setWidth("100%");
		workflowPanel_outer.setHeight("100%");

		workflowPanel_inner = new AbsolutePanel();
		workflowPanel_inner.setSize("99%", "99%");
		DOM.setStyleAttribute(workflowPanel_inner.getElement(), "border",
				"10px solid #DDDDDD");
		DOM.setStyleAttribute(workflowPanel_inner.getElement(), "borderTop",
				"5px solid #DDDDDD");

		Label label_w = new Label("Edit Workflow");
		label_w.setWidth("100%");
		label_w.setHeight("22");
		DOM.setStyleAttribute(label_w.getElement(), "backgroundColor",
				"#DDDDDD");
		DOM.setStyleAttribute(label_w.getElement(), "fontSize", "11pt");
		DOM.setStyleAttribute(label_w.getElement(), "fontWeight", "bold");
		workflowPanel_inner.add(label_w, 0, 0);
		workflowPanel_inner.add(base.tabs, 0, 22);
		workflowPanel_outer.add(workflowPanel_inner, 10, 15);
		this.add(workflowPanel_outer, 0, 200);

		// save button click handler
		((Button) (confirm.getWidget(1))).addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				time_Start = ((DateBox) textBox2.getWidget(0)).getTextBox()
						.getText()
						+ " " + ((TextBox) textBox2.getWidget(1)).getText();
				time_End = ((DateBox) textBox2.getWidget(4)).getTextBox()
						.getText()
						+ " " + ((TextBox) textBox2.getWidget(3)).getText();

				// set client_workflow value
				Client_Workflow client_Workflow = base.diagramBuilderExample
						.save_workflow_in_workflowmode();
				if (base.diagramBuilderExample.check == false) {

				} else {
					// set appointment value
					calendar_panel.workflowApp.setDescription(text_Des);
					calendar_panel.workflowApp.setEnd(dateFormat
							.parse(time_End));
					calendar_panel.workflowApp.setStart(dateFormat
							.parse(time_Start));
					calendar_panel.workflowApp.setTitle(text_What);
					if (calendar_panel.workflowApp.getStart().getYear() != calendar_panel.workflowApp
							.getEnd().getYear()
							|| calendar_panel.workflowApp.getStart().getMonth() != calendar_panel.workflowApp
									.getEnd().getMonth()
							|| calendar_panel.workflowApp.getStart().getDate() != calendar_panel.workflowApp
									.getEnd().getDate()) {
						calendar_panel.workflowApp.setMultiDay(true);
					}
					// set calendar_client
					calendar_panel.workflowApp.setAtoC();
					calendar_panel.workflowApp.calendarClient
							.setUserid(overview_panel.userid);
					calendar_panel.workflowApp.calendarClient.setDone(false);

					// if this is a new event (cos calendarid=null)
					if (calendar_panel.workflowApp.calendarClient
							.getCalendarid() == null
							|| calendar_panel.workflowApp.calendarClient
									.getCalendarid() == 0) {

						overview_panel.calendar_Service.saveCalendar(
								calendar_panel.workflowApp.calendarClient,
								client_Workflow, new AsyncCallback<Integer>() {
									@Override
									public void onFailure(Throwable caught) {
										Window.alert("fail to save");
									}

									@Override
									public void onSuccess(Integer result) {
										if (result != -1 && result != -2
												&& result != null) {
											calendar_panel.workflowApp.calendarClient
													.setCalendarid(result);
											calendar_panel.dayView
													.addAppointment(calendar_panel.workflowApp);
											overview_panel.calendar_show();
										}
										if (result == -1)
											Window.alert("NamingError");
										if (result == -2)
											Window.alert("IOError");
									}
								});
					} else
					// else update the event
					{
						overview_panel.calendar_Service.updateCalendar(
								calendar_panel.workflowApp.calendarClient,
								client_Workflow, new AsyncCallback<Integer>() {
									@Override
									public void onFailure(Throwable caught) {
										Window.alert("fail to update");
									}

									@Override
									public void onSuccess(Integer result) {
										if (result != -1 && result != -2
												&& result != null) {
											calendar_panel.dayView
													.updateAppointment(calendar_panel.workflowApp);
											overview_panel.calendar_show();
										}
										if (result == -1)
											Window.alert("NamingError");
										if (result == -2)
											Window.alert("IOError");
									}
								});
					}
				}

			}
		});

		// cancel button click handler
		((Button) (confirm.getWidget(2))).addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// turn the appointment to original
				overview_panel.calendar_show();
			}
		});

		// delete button click handler
		((Button) (confirm.getWidget(3))).addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// turn the appointment to original
				if (calendar_panel.workflowApp.calendarClient.getCalendarid() == null
						|| calendar_panel.workflowApp.calendarClient
								.getCalendarid() == 0) {
					overview_panel.calendar_show();
				} else {
					overview_panel.calendar_Service.deleteCalendar(
							calendar_panel.workflowApp.calendarClient
									.getCalendarid(),
							new AsyncCallback<String>() {
								@Override
								public void onFailure(Throwable caught) {
									Window.alert("fail to remove the calendar");
								}

								@Override
								public void onSuccess(String result) {
									calendar_panel.dayView
											.removeAppointment(calendar_panel.workflowApp);
									overview_panel.calendar_show();
								}
							});
				}
			}
		});

		// resize the window
		this.setHeight(Window.getClientHeight() - 90 + "px");
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				int h = event.getHeight();
				panel_Workflow.setHeight(h - 90 + "px");
			}
		});
	}

	/** set what when detail */
	AbsolutePanel setDetail() {
		final AbsolutePanel absolutePanel = new AbsolutePanel();
		absolutePanel.setSize("70%", "100%");
		DOM.setStyleAttribute(absolutePanel.getElement(), "backgroundColor",
				"#D2E6D2");
		HTML html1 = new HTML();
		html1.setHeight("22");
		DOM.setStyleAttribute(html1.getElement(), "fontSize", "10pt");
		html1.setHTML("<b>What</b>");
		absolutePanel.add(html1, 10, 10);
		HTML html2 = new HTML();
		html2.setHeight("22");
		DOM.setStyleAttribute(html2.getElement(), "fontSize", "10pt");
		html2.setHTML("<b>When</b>");
		absolutePanel.add(html2, 10, 40);
		HTML html3 = new HTML();
		html3.setHeight("22");
		DOM.setStyleAttribute(html3.getElement(), "fontSize", "10pt");
		html3.setHTML("<b>Description</b>");
		absolutePanel.add(html3, 10, 70);
		// what
		textBox1 = new TextBox();
		textBox1.setHeight("22");
		textBox1.setWidth("70%");
		DOM.setStyleAttribute(textBox1.getElement(), "fontSize", "10pt");
		absolutePanel.add(textBox1, 90, 10);
		// when
		textBox2 = new AbsolutePanel();
		final DateBox dateBox_1 = new DateBox();
		final TextBox textBoxT1 = new TextBox();
		HTML html = new HTML("to");
		final TextBox textBoxT2 = new TextBox();
		final DateBox dateBox_2 = new DateBox();
		//

		final ListBox listBox1 = new ListBox(true);
		listBox1.setSize("80", "180");
		DOM.setStyleAttribute(listBox1.getElement(), "fontSize", "9pt");
		for (int i = 0; i < 24; i++) {
			for (int j = 0; j < 4; j++) {
				if (i < 10) {
					if (j == 0) {
						listBox1.insertItem("0" + i + ":00", 4 * i + j);
					} else {
						listBox1.insertItem("0" + i + ":" + j * 15, 4 * i + j);
					}
				} else {
					if (j == 0) {
						listBox1.insertItem(i + ":00", 4 * i + j);
					} else {
						listBox1.insertItem(i + ":" + j * 15, 4 * i + j);
					}
				}
			}
		}
		final ListBox listBox2 = new ListBox(true);
		listBox2.setSize("80", "180");
		DOM.setStyleAttribute(listBox2.getElement(), "fontSize", "9pt");
		for (int i = 0; i < 24; i++) {
			for (int j = 0; j < 4; j++) {
				if (i < 10) {
					if (j == 0) {
						listBox2.insertItem("0" + i + ":00", 4 * i + j);
					} else {
						listBox2.insertItem("0" + i + ":" + j * 15, 4 * i + j);
					}
				} else {
					if (j == 0) {
						listBox2.insertItem(i + ":00", 4 * i + j);
					} else {
						listBox2.insertItem(i + ":" + j * 15, 4 * i + j);
					}
				}
			}
		}
		//
		{
			dateBox_1.setWidth("77");
			DOM.setStyleAttribute(dateBox_1.getElement(), "fontSize", "10pt");
			dateBox_1.setFormat(new DateBox.DefaultFormat(DateTimeFormat
					.getFormat("MM/dd/yyyy")));
			textBox2.add(dateBox_1, 0, 0);//
			DOM.setStyleAttribute(textBoxT1.getElement(), "fontSize", "10pt");
			textBoxT1.setWidth("65");
			textBox2.add(textBoxT1, 80, 0);//
			DOM.setStyleAttribute(html.getElement(), "fontSize", "10pt");
			textBox2.add(html, 150, 0);//
			DOM.setStyleAttribute(textBoxT2.getElement(), "fontSize", "10pt");
			textBoxT2.setWidth("65");
			textBox2.add(textBoxT2, 165, 0);//
			dateBox_2.setWidth("77");
			DOM.setStyleAttribute(dateBox_2.getElement(), "fontSize", "10pt");
			dateBox_2.setFormat(new DateBox.DefaultFormat(DateTimeFormat
					.getFormat("MM/dd/yyyy")));
			textBox2.add(dateBox_2, 233, 0);//

			textBoxT1.addFocusHandler(new FocusHandler() {
				@Override
				public void onFocus(FocusEvent event) {
					Popup.clear();
					Popup.setWidget(listBox1);
					Popup.setPopupPosition(textBoxT1.getAbsoluteLeft(),
							textBoxT1.getAbsoluteTop()
									+ textBoxT1.getOffsetHeight());
					Popup.show();
				}
			});
			listBox1.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					textBoxT1.setText(listBox1.getItemText(listBox1
							.getSelectedIndex()));
					Popup.hide();
				}
			});
			textBoxT2.addFocusHandler(new FocusHandler() {

				@Override
				public void onFocus(FocusEvent event) {
					Popup.clear();
					Popup.setWidget(listBox2);
					Popup.setPopupPosition(textBoxT2.getAbsoluteLeft(),
							textBoxT2.getAbsoluteTop()
									+ textBoxT1.getOffsetHeight());
					Popup.show();
				}
			});

			listBox2.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					textBoxT2.setText(listBox2.getItemText(listBox2
							.getSelectedIndex()));
					Popup.hide();
				}
			});

		}
		textBox2.setHeight("22");
		textBox2.setWidth("70%");
		DOM.setStyleAttribute(textBox2.getElement(), "fontSize", "10pt");
		absolutePanel.add(textBox2, 90, 40);
		// des
		textBox3 = new TextArea();
		textBox3.setHeight("44");
		textBox3.setWidth("70%");
		DOM.setStyleAttribute(textBox3.getElement(), "fontSize", "10pt");
		absolutePanel.add(textBox3, 90, 70);

		textBox1.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				text_What = textBox1.getText();
			}
		});

		textBox3.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				text_Des = textBox3.getText();
			}
		});

		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				textBox1.setWidth("70%");
				textBox2.setWidth("70%");
				textBox3.setWidth("70%");
			}
		});
		return absolutePanel;

	}

	/** on show */
	public void onShow() {

		base.diagramBuilderExample.scrollPanel.scrollToLeft();
		base.diagramBuilderExample.scrollPanel.scrollToTop();

		textBox1.setText(calendar_panel.workflowApp.getTitle());
		textBox3.setText(calendar_panel.workflowApp.getDescription());
		((DateBox) textBox2.getWidget(0)).getTextBox().setText(
				dateFormat1.format(calendar_panel.workflowApp.getStart()));
		((TextBox) textBox2.getWidget(1)).setText(dateFormat2
				.format(calendar_panel.workflowApp.getStart()));
		((TextBox) textBox2.getWidget(3)).setText(dateFormat2
				.format(calendar_panel.workflowApp.getEnd()));
		((DateBox) textBox2.getWidget(4)).getTextBox().setText(
				dateFormat1.format(calendar_panel.workflowApp.getEnd()));

		text_What = textBox1.getText();
		text_Des = textBox3.getText();
		time_Start = dateFormat.format(calendar_panel.workflowApp.getStart());
		time_End = dateFormat.format(calendar_panel.workflowApp.getEnd());

		base.clearpanel();

		if (calendar_panel.workflowApp.calendarClient.getCalendarid() != null) {
			overview_panel.calendar_Service.getWorkflow(
					calendar_panel.workflowApp.calendarClient.getCalendarid(),
					new AsyncCallback<Client_Workflow>() {
						@Override
						public void onFailure(Throwable arg0) {
							Window.alert("fail to load");
						}

						@Override
						public void onSuccess(Client_Workflow client_Workflow) {
							base.diagramBuilderExample
									.load_workflow_from_server(client_Workflow);
						}
					});
		} else {
			base.diagramBuilderExample.load_workflow_from_server(null);
		}

		// update service
		base.diagramBuilderExample.update_service();

		// set layout
		workflowPanel_inner.setHeight(Integer.toString(panel_Workflow
				.getOffsetHeight() - 235));

		base.tabs.setSize("100%", Integer.toString(workflowPanel_inner
				.getOffsetHeight() - 65));
		base.diagramBuilderExample.getArea().setSize(
				Integer.toString(workflowPanel_inner.getOffsetWidth() - 30),
				Integer.toString(workflowPanel_inner.getOffsetHeight() - 70));
		base.diagramBuilderExample.getPanel().setSize("1200", "800");
		base.diagramBuilderExample.scrollPanel.setSize(Integer
				.toString(workflowPanel_inner.getOffsetWidth() - 210), Integer
				.toString(workflowPanel_inner.getOffsetHeight() - 70));

		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				if (panel_Workflow.isVisible() == true) {
					workflowPanel_inner.setHeight(Integer
							.toString(panel_Workflow.getOffsetHeight() - 235));
					base.tabs.setHeight(Integer.toString(workflowPanel_inner
							.getOffsetHeight() - 35));
					base.diagramBuilderExample.getArea().setSize(
							Integer.toString(workflowPanel_inner
									.getOffsetWidth() - 30),
							Integer.toString(workflowPanel_inner
									.getOffsetHeight() - 70));
					base.diagramBuilderExample.scrollPanel.setSize(
							Integer.toString(workflowPanel_inner
									.getOffsetWidth() - 210), Integer
									.toString(workflowPanel_inner
											.getOffsetHeight() - 70));
				}
			}
		});
	}

}
