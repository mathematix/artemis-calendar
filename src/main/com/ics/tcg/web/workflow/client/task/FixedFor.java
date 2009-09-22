package com.ics.tcg.web.workflow.client.task;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.ics.tcg.web.user.client.panels.Panel_Overview;
import com.ics.tcg.web.workflow.client.DiagramBuilder;
import com.ics.tcg.web.workflow.client.command.EditLoopCommand;
import com.ics.tcg.web.workflow.client.command.SetFinishCommand;
import com.ics.tcg.web.workflow.client.command.SetStartCommand;
import com.ics.tcg.web.workflow.client.command.TaskDeleteCommand;
import com.ics.tcg.web.workflow.client.composite.complement.ContextMenuGwt;
import com.ics.tcg.web.workflow.client.composite.complement.MyPanel;

@SuppressWarnings("deprecation")
public class FixedFor extends LoopTask {

	private AbsolutePanel instance;
	public int circle_Num;
	Panel_Overview overview;

	private void createEditableLabel(String labelText, String in_name,
			String out_name, String fault_name) {

		initServiceInfo(); // 初始化looptask

		instance = new AbsolutePanel();
		instance.setSize("110", "100");

		setText(labelText);
		panel1 = new AbsolutePanel() {
			@Override
			public void onBrowserEvent(Event event) {
				if (DOM.eventGetType(event) == Event.ONDBLCLICK) {
					Configure_FixedFor();
				}

				super.onBrowserEvent(event);
			}
		};
		panel1.sinkEvents(Event.ONDBLCLICK);
		panel1.setSize("80", "100");
		panel1.setStyleName("compound-inner-panel");

		SimplePanel sPanel = new SimplePanel(); // 用来填充内部panel1的上部
		sPanel.setSize("80", "18");
		sPanel.addStyleName("green_color");
		panel1.add(sPanel, 0, 0);

		Label loopname = new Label("floop"); // 填充panel1的中间
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

	/**
	 * Constructor that uses default text values for buttons.
	 * 
	 * @param labelText
	 *            The initial text of the label.
	 * @param onUpdate
	 *            Handler object for performing actions once label is updated.
	 */
	public FixedFor(String labelText, String in, String out, String fault,
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
	public FixedFor(String labelText, DiagramBuilder d,
			Panel_Overview overview) {
		this.overview = overview;
		dbe = d;
		createEditableLabel(labelText, "I", "O", "F");
		setVariableName("circle_Num");
	}

	public FixedFor(String labelText, Panel_Overview overview) {
		this.overview = overview;

		createEditableLabel(labelText, "I", "O", "F");
	}

	public void Configure_FixedFor() {
		final DialogBox dialogBox = new DialogBox();
		dialogBox.addStyleName("g-DialogBox");
		DOM.setStyleAttribute(dialogBox.getElement(), "border", "0px");
		dialogBox.setSize("275px", "167px");
		dialogBox.setText("Configure FixedNumLoop");

		final AbsolutePanel absolutePanel = new AbsolutePanel();
		dialogBox.setWidget(absolutePanel);
		absolutePanel.setSize("275px", "188px");

		final Label circlenumLabel = new Label("CircleNum");
		absolutePanel.add(circlenumLabel, 22, 66);
		circlenumLabel.setWidth("86px");

		final TextBox textBox = new TextBox();
		textBox.setWidth("132px");
		textBox.addFocusListener(new FocusListener() {

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
		absolutePanel.add(textBox, 113, 66);

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
		// absolutePanel.add(changeservieproviderCheckBox, 22, 122);
		changeservieproviderCheckBox.setSize("179px", "20px");
		changeservieproviderCheckBox.setText("ChangeServieProvider");

		final Button okButton = new Button();
		okButton.removeStyleName("gwt-Button");
		DOM.setStyleAttribute(okButton.getElement(), "fontSize", "10pt");
		okButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (textBox.getValue() != null
						&& !textBox.getValue().equals("")
						&& isTypelegal(textBox.getValue(), "Integer")) {
					circle_Num = Integer.parseInt(textBox.getValue());
					setHasFinishedConfigure(true);
					dialogBox.hide();
				}
			}

		});
		absolutePanel.add(okButton, 90, 125);
		okButton.setSize("82px", "21px");
		okButton.setText("OK");

		dialogBox.center();
	}
}
