package com.ics.tcg.web.workflow.client.task;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;

@SuppressWarnings( { "unused" })
public class TimerNode extends Composite {

	private ServiceTask simpleTask;
	private String start_time;

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	private void createEditableLabel(String labelText) {

		Image image = new Image("img/clock.png") {
			@Override
			public void onBrowserEvent(Event event) {
				if (DOM.eventGetType(event) == Event.ONDBLCLICK) {
					System.out.println("invoke timer setting");
					Configure_start_time();
					System.out.println(start_time);
				}
				super.onBrowserEvent(event);
			}
		};
		image.sinkEvents(Event.ONDBLCLICK);
		image.addStyleName("select_handle");
		// apAbsolutePanel.add(image);
		initWidget(image);
		setSize("32", "47");
	}

	public TimerNode(String labelText) {
		createEditableLabel(labelText);
	}

	public TimerNode(String labelText, ServiceTask s) {
		createEditableLabel(labelText);
		simpleTask = s;
	}

	public void Configure_start_time() {

		final DialogBox dialogBox = new DialogBox();
		dialogBox.addStyleName("g-DialogBox");
		DOM.setStyleAttribute(dialogBox.getElement(), "border", "0px");
		dialogBox.setText("Set Time");
		dialogBox.setSize("294", "207");

		final AbsolutePanel absolutePanel = new AbsolutePanel();
		dialogBox.setWidget(absolutePanel);
		absolutePanel.setSize("294px", "187px");

		// 自定义datebox的输出格式，在这里采用了简洁的输出格式"M/d/yy H:mm"
		final DateBox dateBox = new DateBox();
		DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss"); // DateTimeFormat只能通过函数getFormat来生成满足特定pattern的对象，因为其构造函数为protected
		DateBox.DefaultFormat defaultFormat = new DateBox.DefaultFormat(
				dateTimeFormat);
		dateBox.setFormat(defaultFormat);
		absolutePanel.add(dateBox, 109, 35);
		dateBox.setWidth("150px");

		final Label dateLabel = new Label("Date:");
		absolutePanel.add(dateLabel, 36, 35);
		dateLabel.setSize("38px", "18px");

		final Label dateLabel_1 = new Label("Time:");
		absolutePanel.add(dateLabel_1, 36, 80);
		dateLabel_1.setSize("38px", "18px");

		final ListBox listBox = new ListBox();
		absolutePanel.add(listBox, 109, 77);
		listBox.setSize("150px", "21px");
		String[] minutes = { "00", "15", "30", "45" };
		String[] hours = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20", "21", "22", "23" };
		String[] times = new String[96];
		// System.out.println(minutes.length+"  "+hours.length+"  "+times.length);
		for (int i = 0; i < 24; i++) {
			for (int j = 0; j < 4; j++) {
				times[4 * i + j] = hours[i] + ":" + minutes[j];
			}
		}
		for (int i = 0; i < 96; i++) {
			listBox.addItem(times[i]);
		}

		final Button okButton = new Button();
		DOM.setStyleAttribute(okButton.getElement(), "fontSize", "10pt");
		okButton.removeStyleName("gwt-Button");
		okButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				TextBox inner_textBox = dateBox.getTextBox();
				if (inner_textBox.getText() == null
						|| inner_textBox.getText().equals("")) {
					dateBox.addStyleName("dateBoxFormatError"); // 如果用户没编辑日期，则datebox框变红，等待用户更正
				} else {
					String dateString = inner_textBox.getText();
					int index = listBox.getSelectedIndex();
					String timeString = listBox.getValue(index);
					if (timeString != null) {
						String[] date_and_time = dateString.split(" ");
						date_and_time[1] = timeString;
						dateString = date_and_time[0] + " " + date_and_time[1];
						start_time = dateString;
					}
					dialogBox.hide();
				}
				System.out.println(start_time);
			}
		});

		absolutePanel.add(okButton, 93, 129);
		okButton.setSize("105px", "21px");
		okButton.setText("OK");

		dialogBox.center();
	}
}
