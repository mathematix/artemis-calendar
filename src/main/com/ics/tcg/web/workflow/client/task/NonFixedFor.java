package com.ics.tcg.web.workflow.client.task;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.ics.tcg.web.user.client.panels.Panel_Overview;
import com.ics.tcg.web.workflow.client.DiagramBuilder;
import com.ics.tcg.web.workflow.client.command.EditLoopCommand;
import com.ics.tcg.web.workflow.client.command.SetFinishCommand;
import com.ics.tcg.web.workflow.client.command.SetStartCommand;
import com.ics.tcg.web.workflow.client.command.TaskDeleteCommand;
import com.ics.tcg.web.workflow.client.composite.complement.ContextMenuGwt;
import com.ics.tcg.web.workflow.client.composite.complement.MyPanel;
import com.ics.tcg.web.workflow.client.service.ParamInfo;

@SuppressWarnings( { "deprecation" })
public class NonFixedFor extends LoopTask {

	AbsolutePanel instance;
	String startDate, endDate, currentDate;
	int step; // 表明跨越的天数
	ArrayList<ParamInfo> paraminfolist = new ArrayList<ParamInfo>();
	Panel_Overview overview;

	private void createEditableLabel(String labelText, String in_name,
			String out_name, String fault_name) {

		initServiceInfo(); // 初始化looptask

		instance = new AbsolutePanel();
		instance.setSize("110", "100");

		setText(labelText);
		panel1 = new AbsolutePanel() {
			public void onBrowserEvent(Event event) {
				if (DOM.eventGetType(event) == Event.ONDBLCLICK) {
					Configure_NonFixedFor();
				}

				super.onBrowserEvent(event);
			}
		};
		panel1.sinkEvents(Event.ONDBLCLICK);
		panel1.setSize("80", "100");
		panel1.setStyleName("compound-inner-panel");

		SimplePanel sPanel = new SimplePanel(); // 用来填充内部panel1的上部
		sPanel.setSize("80", "18");
		sPanel.addStyleName("yellow_color");
		panel1.add(sPanel, 0, 0);

		Label loopname = new Label("tloop"); // 填充panel1的中间
		loopname.setSize("80", "18");
		loopname.addStyleName("loop-label");
		panel1.add(loopname, 0, 18);

		SimplePanel sPanel2 = new SimplePanel(); // 填充panel1的下部
		sPanel2.setSize("62", "63");
		sPanel2.addStyleName("loop-inner");
		panel1.add(sPanel2, 0, 36);

		// Create the TextBox element used for non word wrapped Labels
		// and add a KeyboardListener for Return and Esc key presses
		in = new Image("img/in.png");
		panel2 = new MyPanel(dbe);
		panel2.setSize("15", "15");
		panel2.setTitle("in");
		panel2.setStyleName("inner-panel");
		panel2.add(in);

		out = new Image("img/out.png");
		panel3 = new MyPanel(dbe);
		panel3.setSize("15", "15");
		panel3.setStyleName("inner-panel");
		panel3.setTitle("out");
		panel3.add(out);

		fault = new Image("img/fault.png");
		panel4 = new MyPanel(dbe);
		panel4.setSize("15", "15");
		panel4.setStyleName("inner-panel");
		panel4.setTitle("fault");
		panel4.add(fault);

		// add right click menu to this widget
		MenuBar menuBar = new MenuBar(true);
		TaskDeleteCommand command = new TaskDeleteCommand(this, dbe);
		MenuItem menuItem = new MenuItem("Delete", command);
		menuBar.addItem(menuItem);
		EditLoopCommand command2 = new EditLoopCommand(this, dbe, overview); // 暂时取消for循环控件的子工作流编辑
		MenuItem menuItem2 = new MenuItem("Edit", command2);
		menuBar.addItem(menuItem2);

		MenuItemSeparator menuItemSeparator2 = new MenuItemSeparator();
		menuItemSeparator2.addStyleName("menuItem-seperator");
		menuBar.addSeparator(menuItemSeparator2);

		setstart = new MenuItem("isStart", (Command) null);
		SetStartCommand setStartCommand = new SetStartCommand(this, dbe,
				setstart);
		setstart.setCommand(setStartCommand);

		setfinish = new MenuItem("isfinish", (Command) null);
		SetFinishCommand setFinishCommand = new SetFinishCommand(this, dbe,
				setfinish);
		setfinish.setCommand(setFinishCommand);

		menuBar.addItem(setstart);
		menuBar.addItem(setfinish);

		menuBar.addStyleName("popup-menu");
		cmg = new ContextMenuGwt(panel1, menuBar);

		// Add panels/widgets to the widget panel
		instance.add(panel2, 0, 10);
		instance.add(cmg, 15, 0);
		instance.add(panel3, 95, 10);
		instance.add(panel4, 95, 85);

		// Set initial visibilities. This needs to be after
		// adding the widgets to the panel bec ilmause the FlowPanel
		// will mess them up when added.
		panel1.setVisible(true);
		panel2.setVisible(true);
		panel3.setVisible(true);
		panel4.setVisible(true);

		// Set the widget that this Composite represents
		connector_list.add(panel2);
		connector_list.add(panel3);
		connector_list.add(panel4);
		outport_list.add(panel3);

		initWidget(instance);
		setSize("110", "100");
	}

	public void Configure_NonFixedFor() {
		final DialogBox dialogBox = new DialogBox();
		dialogBox.addStyleName("g-DialogBox");
		DOM.setStyleAttribute(dialogBox.getElement(), "border", "0px");
		dialogBox.setSize("324px", "282px");
		dialogBox.setText("Configure TimeIntervalLoop");

		final AbsolutePanel absolutePanel = new AbsolutePanel();
		dialogBox.setWidget(absolutePanel);
		absolutePanel.setSize("324px", "248px");

		final Label starttimeLabel = new Label("StartTime");
		absolutePanel.add(starttimeLabel, 40, 53);

		final Label endTimeLabel = new Label("EndTime");
		absolutePanel.add(endTimeLabel, 40, 100);
		endTimeLabel.setSize("68px", "18px");

		final Label intervalLabel = new Label("Interval");
		absolutePanel.add(intervalLabel, 40, 144);

		final TextBox textBox = new TextBox();
		absolutePanel.add(textBox, 126, 53);
		textBox.setReadOnly(true);
		textBox.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				setDateInTextBox(textBox);
			}

		});

		final TextBox textBox2 = new TextBox();
		absolutePanel.add(textBox2, 126, 100);
		textBox2.setReadOnly(true);
		textBox2.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				setDateInTextBox(textBox2);
			}

		});

		final TextBox textBox3 = new TextBox();
		textBox3.addFocusListener(new FocusListener() {

			public void onFocus(Widget sender) {
				// TODO Auto-generated method stub

			}

			public void onLostFocus(Widget sender) {
				// TODO Auto-generated method stub
				// 获得事件响应的实际控件
				TextBox innerTextBox = (TextBox) sender;
				String value = innerTextBox.getValue();
				if (value != null) {
					if (isTypelegal(value, "Integer")) {
						sender.removeStyleName("x-form-invalid");
					} else {
						if (value.equals("")) {
							sender.removeStyleName("x-form-invalid");
						} else {
							sender.addStyleName("x-form-invalid");
						}
					}

				} else {
					sender.removeStyleName("x-form-invalid");
				}
			}

		});
		absolutePanel.add(textBox3, 126, 144);

		final CheckBox changeservieproviderCheckBox = new CheckBox();
		changeservieproviderCheckBox.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				CheckBox checkBox = (CheckBox) event.getSource();
				if (checkBox.isChecked()) {
					checkBox.setChecked(false);
					setChangeServiceProvider(false);
				} else {
					checkBox.setChecked(true);
					setChangeServiceProvider(true);
				}
			}
		});
		// absolutePanel.add(changeservieproviderCheckBox, 40, 191);
		changeservieproviderCheckBox.setText("ChangeServieProvider");

		final Button okButton = new Button();
		okButton.removeStyleName("gwt-Button");
		DOM.setStyleAttribute(okButton.getElement(), "fontSize", "10pt");

		absolutePanel.add(okButton, 126, 199);
		okButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (textBox.getValue() != null
						&& !textBox.getValue().equals("")
						&& isTypelegal(textBox3.getValue(), "Integer")) {
					startDate = textBox.getValue();
					step = Integer.parseInt(textBox3.getValue());
					setHasFinishedConfigure(true);
					dialogBox.hide();
				}
			}

		});
		okButton.setSize("82px", "21px");
		okButton.setText("OK");

		dialogBox.center();
	}

	public void setDateInTextBox(final TextBox textBox) {
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
		DateTimeFormat dateTimeFormat = DateTimeFormat
				.getFormat("yyyy-MM-dd HH:mm:ss"); // DateTimeFormat只能通过函数getFormat来生成满足特定pattern的对象，因为其构造函数为protected
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
		String[] hours = { "00", "01", "02", "03", "04", "05", "06", "07",
				"08", "09", "10", "11", "12", "13", "14", "15", "16", "17",
				"18", "19", "20", "21", "22", "23" };
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
		okButton.removeStyleName("gwt-Button");
		DOM.setStyleAttribute(okButton.getElement(), "fontSize", "10pt");
		okButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				TextBox inner_textBox = dateBox.getTextBox();
				if (inner_textBox.getText() == null
						|| inner_textBox.getText().equals("")) {
					dateBox.addStyleName("dateBoxFormatError"); // 如果用户没编辑日期，则datebox框变红，等待用户更正
				} else {
					String dateString = inner_textBox.getText();
					int index = listBox.getSelectedIndex();
					String timeString = listBox.getValue(index);
					timeString = timeString + ":00";
					if (timeString != null) {
						String[] date_and_time = dateString.split(" ");
						date_and_time[1] = timeString;
						dateString = date_and_time[0] + " " + date_and_time[1];
						textBox.setValue(dateString);
					}
					dialogBox.hide();
				}
			}
		});

		absolutePanel.add(okButton, 93, 129);
		okButton.setSize("105px", "21px");
		okButton.setText("OK");

		dialogBox.center();
	}

	public NonFixedFor(String labelText, String in, String out, String fault,
			DiagramBuilder d, Panel_Overview overview) {
		this.overview = overview;
		dbe = d;
		createEditableLabel(labelText, in, out, fault);
	}

	/**
	 * Constructor that uses default text values for buttons.
	 * 
	 * @param labelText
	 *            The initial text of the label.
	 * @param onUpdate
	 *            Handler object for performing actions once label is updated.
	 */
	public NonFixedFor(String labelText, DiagramBuilder d,
			Panel_Overview overview) {
		this.overview = overview;
		dbe = d;
		createEditableLabel(labelText, "I", "O", "F");
		setVariableName("currentDate");
	}

	public NonFixedFor(String labelText, Panel_Overview overview) {
		this.overview = overview;

		createEditableLabel(labelText, "I", "O", "F");
	}
}
