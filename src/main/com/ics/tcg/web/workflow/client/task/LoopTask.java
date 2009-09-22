package com.ics.tcg.web.workflow.client.task;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.ics.tcg.web.user.client.panels.Panel_Overview;
import com.ics.tcg.web.workflow.client.DiagramBuilder;
import com.ics.tcg.web.workflow.client.command.EditLoopCommand;
import com.ics.tcg.web.workflow.client.command.SetFinishCommand;
import com.ics.tcg.web.workflow.client.command.SetStartCommand;
import com.ics.tcg.web.workflow.client.command.TaskDeleteCommand;
import com.ics.tcg.web.workflow.client.composite.complement.ContextMenuGwt;
import com.ics.tcg.web.workflow.client.composite.complement.MyPanel;
import com.ics.tcg.web.workflow.client.data.Client_Loop;
import com.ics.tcg.web.workflow.client.data.Client_Node;
import com.ics.tcg.web.workflow.client.data.Client_NonFixedNumFor;
import com.ics.tcg.web.workflow.client.data.Client_ServiceTask;
import com.ics.tcg.web.workflow.client.data.Client_Workflow;
import com.ics.tcg.web.workflow.client.service.DataLib;
import com.ics.tcg.web.workflow.client.service.MethodInfo;
import com.ics.tcg.web.workflow.client.service.ParamInfo;
import com.ics.tcg.web.workflow.client.service.ServiceConfigure;
import com.ics.tcg.web.workflow.client.service.ServiceInfo;

@SuppressWarnings("deprecation")
public class LoopTask extends ServiceTask {

	private AbsolutePanel instance;
	// int outport_count=1;
	// List outport_info;
	public Client_Workflow sub_workflow;
	public String variableName;
	public ServiceInfo serviceInfo;
	public ServiceConfigure serviceConfigure = new ServiceConfigure();

	public boolean changeServiceProvider;
	public int selectServiceNumber;
	public int conditionNumber = 1;
	public String contentPath;
	public Map<String, ParamInfo> name_for_paraminfo = new HashMap<String, ParamInfo>();
	public ParamInfo paramInfo_for_configure; // 指向设置好路径后的输入参数的引用,为choice配置界面的显示方式和显示内容服务
	public boolean textBox_error;
	public String whilecondition;

	Panel_Overview overview;

	public int getSelectServiceNumber() {
		return selectServiceNumber;
	}

	public void setSelectServiceNumber(int selectServiceNumber) {
		this.selectServiceNumber = selectServiceNumber;
	}

	public ServiceInfo getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(ServiceInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	public ServiceConfigure getServiceConfigure() {
		if (serviceConfigure == null)
			serviceConfigure = new ServiceConfigure();
		return serviceConfigure;
	}

	public void setServiceConfigure(ServiceConfigure serviceConfigure) {
		this.serviceConfigure = serviceConfigure;
	}

	private void createEditableLabel(String labelText, String in_name,
			String out_name, String fault_name) {

		initServiceInfo();

		instance = new AbsolutePanel();
		instance.setSize("110", "100");

		setText(labelText);
		panel1 = new AbsolutePanel() {
			public void onBrowserEvent(Event event) {
				if (DOM.eventGetType(event) == Event.ONDBLCLICK) {
					Configure_GeneralLoop();
				}

				super.onBrowserEvent(event);
			}
		};
		panel1.sinkEvents(Event.ONDBLCLICK);
		panel1.setSize("80", "100");
		panel1.setStyleName("compound-inner-panel");

		SimplePanel sPanel = new SimplePanel();
		sPanel.setSize("80", "18");
		sPanel.addStyleName("blue_color");
		panel1.add(sPanel, 0, 0);

		Label loopname = new Label("while(flase)");
		loopname.setSize("80", "18");
		loopname.addStyleName("loop-label");
		panel1.add(loopname, 0, 18);

		SimplePanel sPanel2 = new SimplePanel();
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
		EditLoopCommand command2 = new EditLoopCommand(this, dbe, overview);
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

	public LoopTask() {
	}

	/**
	 * Constructor that uses default text values for buttons.
	 * 
	 * @param labelText
	 *            The initial text of the label.
	 * @param onUpdate
	 *            Handler object for performing actions once label is updated.
	 */
	public LoopTask(String labelText, String in, String out, String fault,
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
	public LoopTask(String labelText, DiagramBuilder d,
			Panel_Overview overview) {
		this.overview = overview;
		dbe = d;
		createEditableLabel(labelText, "I", "O", "F");
	}

	public LoopTask(String labelText, Panel_Overview overview) {
		this.overview = overview;

		createEditableLabel(labelText, "I", "O", "F");
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	// 将loop看成是一个对task提供包装的服务，初始化的时候，新建这个服务的名字，和方法的名字，输出内容直到子工作流配置完成再设置
	public void initServiceInfo() {

		serviceInfo = new ServiceInfo();
		serviceInfo.setServiceName("Loop");
		MethodInfo methodInfo = new MethodInfo();
		methodInfo.setMethodName("getLoopResult");
		serviceInfo.getMethodInfoArray().add(methodInfo);
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setIsUserFilled(false);
		paramInfo.setPrimitive(false);
		paramInfo.setFieldInfoArray(null);

	}

	public void setServiceConfigure() {
		String serviceName = getTitle();
		serviceConfigure.setServiceName(serviceName);
		MethodInfo methodInfo = null;
		if (sub_workflow != null) {
			if (sub_workflow.getHasLastNode().getName().equals("Loop")) {
				Client_Loop client_Loop = (Client_Loop) sub_workflow
						.getHasLastNode();
				methodInfo = client_Loop.getServiceConfigure().getMethodInfo();
			} else if (sub_workflow.getHasLastNode().getName().equals("TLoop")) {
				Client_NonFixedNumFor client_NonFixedNumFor = (Client_NonFixedNumFor) sub_workflow
						.getHasLastNode();
				methodInfo = client_NonFixedNumFor.getServiceConfigure()
						.getMethodInfo();
			} else if (sub_workflow.getHasLastNode().getName().equals("Choice")) {

			} else {
				Client_ServiceTask client_ServiceTask = (Client_ServiceTask) sub_workflow
						.getHasLastNode();
				methodInfo = client_ServiceTask.getService()
						.getHasConfiguration().getServiceConfigure()
						.getMethodInfo();
			}
		}
		serviceConfigure.setMethodInfo(methodInfo);
	}

	public boolean isChangeServiceProvider() {
		return changeServiceProvider;
	}

	public void setChangeServiceProvider(boolean changeServiceProvider) {
		this.changeServiceProvider = changeServiceProvider;
	}

	public void Configure_GeneralLoop() {
		final DialogBox dialogBox = new DialogBox();
		dialogBox.addStyleName("g-DialogBox");
		DOM.setStyleAttribute(dialogBox.getElement(), "border", "0px");
		dialogBox.setSize("518px", "282px");
		dialogBox.setText("Configure Loop");

		final AbsolutePanel absolutePanel = new AbsolutePanel();
		dialogBox.setWidget(absolutePanel);
		absolutePanel.setSize("518px", "336px");

		final Label trialNumberLabel = new Label("Trial Number:");
		trialNumberLabel.addStyleName("gray-text");
		absolutePanel.add(trialNumberLabel, 235, 21);

		final TextBox trialNum_textBox = new TextBox();
		trialNum_textBox.addFocusListener(new FocusListener() {

			public void onFocus(Widget sender) {
				// TODO Auto-generated method stub

			}

			public void onLostFocus(Widget sender) {
				// TODO Auto-generated method stub
				String s = ((TextBox) sender).getValue();
				if (isInt(s)) {
					sender.removeStyleName("x-form-invalid");
					selectServiceNumber = Integer.parseInt(s);
				} else {
					sender.addStyleName("x-form-invalid");
				}
			}

		});
		trialNum_textBox.setEnabled(false);
		absolutePanel.add(trialNum_textBox, 335, 25);

		final CheckBox changeserviceproviderCheckBox = new CheckBox();
		changeserviceproviderCheckBox.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				CheckBox checkBox = (CheckBox) event.getSource();
				if (checkBox.isChecked()) {
					checkBox.setChecked(false);
					setChangeServiceProvider(false);
					trialNumberLabel.addStyleName("gray-text");
					trialNum_textBox.setEnabled(false);

					trialNum_textBox.removeStyleName("x-form-invalid");
				} else {
					checkBox.setChecked(true);
					setChangeServiceProvider(true);
					trialNumberLabel.removeStyleName("gray-text");
					trialNum_textBox.setEnabled(true);
				}
			}
		});
		absolutePanel.add(changeserviceproviderCheckBox, 40, 19);
		changeserviceproviderCheckBox.setText("ChangeServiceProvider");

		final Label whileconditionLabel = new Label("WhileCondition:");
		absolutePanel.add(whileconditionLabel, 42, 53);

		final TextBox whileCondition_textBox = new TextBox();
		absolutePanel.add(whileCondition_textBox, 42, 80);
		whileCondition_textBox.setWidth("445px");

		final ScrollPanel scrollPanel = new ScrollPanel();
		absolutePanel.add(scrollPanel, 42, 108);
		scrollPanel.setSize("445", "151");
		scrollPanel.setStyleName("gwt-BlackBorder");

		final AbsolutePanel outer_absolutePanel = new AbsolutePanel();
		scrollPanel.setWidget(outer_absolutePanel);
		outer_absolutePanel.setStyleName("gwt-GrayBorder");
		outer_absolutePanel.setSize("422px", "151px");

		// final Label label = new Label("A");
		// outer_absolutePanel.add(label, 5, 5);
		// label.setSize("19px", "9px");

		final Image addImage = new Image("img/add.PNG");
		addImage.addStyleName("add_image");
		addImage.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				conditionNumber++;
				AbsolutePanel newInnerAbsolutePanel = generateAbsolutePanelForConditionSet(whileCondition_textBox);
				outer_absolutePanel.setHeight(Integer
						.toString(150 * conditionNumber));
				outer_absolutePanel.add(newInnerAbsolutePanel, 21,
						150 * (conditionNumber - 1));
			}

		});
		outer_absolutePanel.add(addImage, 0, 5);

		final Image delImage = new Image("img/del.PNG");
		delImage.addStyleName("add_image");
		delImage.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (conditionNumber > 1) {
					AbsolutePanel lastInnerAbsolutePanel = (AbsolutePanel) outer_absolutePanel
							.getWidget(1 + conditionNumber);
					lastInnerAbsolutePanel.removeFromParent();
					conditionNumber--;
					outer_absolutePanel.setHeight(Integer
							.toString(150 * conditionNumber));

					refreshWhileCondition(whileCondition_textBox,
							outer_absolutePanel);
				}
			}

		});
		outer_absolutePanel.add(delImage, 0, 25);

		final AbsolutePanel inner_absolutePanel = generateAbsolutePanelForConditionSet(whileCondition_textBox);
		outer_absolutePanel.add(inner_absolutePanel, 21, 1);

		final Button okButton = new Button();
		okButton.removeStyleName("gwt-Button");
		DOM.setStyleAttribute(okButton.getElement(), "fontSize", "10pt");
		okButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (textBox_error == true) {
					promptMessage("There is an error textBox,please check it and modify!");
				} else if (!checkConditionConfigure(outer_absolutePanel)) {
					promptMessage("There is an empty textBox,please check it and modify!");
				} else {
					if (whileCondition_textBox.getValue().equals("")) {
						promptMessage("You must set one condition at least");
					} else {
						whilecondition = whileCondition_textBox.getValue(); // save
																			// the
																			// whilecondition
						setHasFinishedConfigure(true); // Has finished configure
					}
					dialogBox.hide();
				}
			}

		});
		absolutePanel.add(okButton, 83, 281);
		okButton.setSize("82px", "20px");
		okButton.setText("OK");

		final Button cancelButton = new Button();
		cancelButton.removeStyleName("gwt-Button");
		DOM.setStyleAttribute(cancelButton.getElement(), "fontSize", "10pt");
		cancelButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				dialogBox.hide();
			}

		});
		absolutePanel.add(cancelButton, 336, 281);
		cancelButton.setSize("82px", "20px");
		cancelButton.setText("CANCEL");

		dialogBox.center();
	}

	public AbsolutePanel generateAbsolutePanelForConditionSet(
			final TextBox conditionTextBox) {

		final AbsolutePanel inner_absolutePanel = new AbsolutePanel();
		inner_absolutePanel.setSize("402px", "150px");
		inner_absolutePanel.setStyleName("gwt-GrayBorder");

		final Label leftoperatorLabel = new Label("LeftOperator:");
		inner_absolutePanel.add(leftoperatorLabel, 9, 12);

		final TextBox leftOperator_textBox = new TextBox();
		leftOperator_textBox.setReadOnly(true);
		leftOperator_textBox.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				// you have not set the subworkflow , please set it first
				if (sub_workflow == null) {
					promptMessage("The subworkflow is null,you can configure loop now");
				} else {
					setContentPath(inner_absolutePanel);
				}
			}

		});
		inner_absolutePanel.add(leftOperator_textBox, 121, 14);
		leftOperator_textBox.setWidth("268px");

		final Label compareLabel = new Label("Compare:");
		compareLabel.addStyleName("gray-text");
		inner_absolutePanel.add(compareLabel, 9, 64);

		// final TextBox compareLabel_textBox = new TextBox();
		// inner_absolutePanel.add(compareLabel_textBox, 117, 61);

		final ListBox compareLabel_listBox = new ListBox();
		compareLabel_listBox.addChangeListener(new ChangeListener() {

			public void onChange(Widget sender) {
				// TODO Auto-generated method stub
				AbsolutePanel innerAbsolutePanel = (AbsolutePanel) sender
						.getParent();
				AbsolutePanel outterAbsolutePanel = (AbsolutePanel) innerAbsolutePanel
						.getParent();
				refreshWhileCondition(conditionTextBox, outterAbsolutePanel);
			}

		});
		compareLabel_listBox.setEnabled(false);
		compareLabel_listBox.setSize("152px", "21px");
		inner_absolutePanel.add(compareLabel_listBox, 117, 61);

		final Label rightoperatorLabel = new Label("RightOperator:");
		rightoperatorLabel.addStyleName("gray-text");
		inner_absolutePanel.add(rightoperatorLabel, 9, 110);

		final DeckPanel deckPanel = new DeckPanel();
		deckPanel.setSize("152px", "21px");
		inner_absolutePanel.add(deckPanel, 117, 110);

		final TextBox rightOperator_textBox = new TextBox();
		rightOperator_textBox.addFocusListener(new FocusListener() {

			public void onFocus(Widget sender) {
				// TODO Auto-generated method stub

			}

			public void onLostFocus(Widget sender) {
				// TODO Auto-generated method stub
				String s = ((TextBox) sender).getValue();
				if (isInt(s)) {
					textBox_error = false;
					sender.removeStyleName("x-form-invalid");

					DeckPanel parentDeckPanel = (DeckPanel) sender.getParent();
					AbsolutePanel innerAbsolutePanel = (AbsolutePanel) parentDeckPanel
							.getParent();
					AbsolutePanel outterAbsolutePanel = (AbsolutePanel) innerAbsolutePanel
							.getParent();
					// conditionTextBox.setValue(leftOperator_textBox.getValue()+compareLabel_listBox.getValue(compareLabel_listBox.getSelectedIndex())+s);

					for (int i = 1; i <= conditionNumber; i++) {
						AbsolutePanel newinnerAbsolutePanel = (AbsolutePanel) outterAbsolutePanel
								.getWidget(i + 1);

						TextBox leftOperator = (TextBox) newinnerAbsolutePanel
								.getWidget(1);
						String leftOperatorValue = leftOperator.getValue();
						if (leftOperatorValue != null
								&& !leftOperatorValue.equals("")) {
							ListBox compareBox = (ListBox) newinnerAbsolutePanel
									.getWidget(3);
							String compareBoxValue = compareBox
									.getValue(compareBox.getSelectedIndex());

							DeckPanel deckPanel = (DeckPanel) newinnerAbsolutePanel
									.getWidget(5);
							int index = deckPanel.getVisibleWidget();
							String rightOperatorValue;
							if (index == 0) {
								TextBox textBox = (TextBox) deckPanel
										.getWidget(0);
								rightOperatorValue = textBox.getValue();
							} else {
								ListBox listBox = (ListBox) deckPanel
										.getWidget(1);
								rightOperatorValue = listBox.getValue(listBox
										.getSelectedIndex());
							}

							String conditionString = leftOperatorValue
									+ compareBoxValue + rightOperatorValue;
							if (i == 1) {
								conditionTextBox.setValue(conditionString);
							} else {
								conditionTextBox.setValue(conditionTextBox
										.getValue()
										+ "&&" + conditionString);
							}
						}

					}
				} else {
					textBox_error = true;
					sender.addStyleName("x-form-invalid");
				}
			}

		});
		rightOperator_textBox.setEnabled(false);
		rightOperator_textBox.setSize("152px", "21px");
		// inner_absolutePanel.add(rightOperator_textBox, 117, 110);

		final ListBox rightOperator_listBox = new ListBox();
		rightOperator_listBox.addChangeListener(new ChangeListener() {

			public void onChange(Widget sender) {
				// TODO Auto-generated method stub
				DeckPanel parentDeckPanel = (DeckPanel) sender.getParent();
				AbsolutePanel innerAbsolutePanel = (AbsolutePanel) parentDeckPanel
						.getParent();
				AbsolutePanel outterAbsolutePanel = (AbsolutePanel) innerAbsolutePanel
						.getParent();
				refreshWhileCondition(conditionTextBox, outterAbsolutePanel);
			}

		});
		rightOperator_listBox.setEnabled(false);
		rightOperator_listBox.setSize("152px", "21px");
		// inner_absolutePanel.add(rightOperator_listBox, 117, 110);
		deckPanel.add(rightOperator_textBox);
		deckPanel.add(rightOperator_listBox);
		deckPanel.showWidget(0);

		return inner_absolutePanel;
	}

	public boolean isInt(String str) {
		try {
			int i = Integer.parseInt(str);
			System.out.println("你输入的整数" + i);
			return true;
		} catch (NumberFormatException e) {
			System.out.println("你输入的不是整数,可能是浮点数");
			return false;
		}
	}

	public void promptMessage(String messageString) {
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setSize("261px", "127px");
		dialogBox.setText("MessageBox!");

		final AbsolutePanel absolutePanel = new AbsolutePanel();
		dialogBox.setWidget(absolutePanel);
		absolutePanel.setSize("261px", "107px");

		final Button okButton = new Button();
		okButton.removeStyleName("gwt-Button");
		DOM.setStyleAttribute(okButton.getElement(), "fontSize", "10pt");
		okButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				dialogBox.hide();
			}

		});
		absolutePanel.add(okButton, 89, 77);
		okButton.setSize("76px", "19px");
		okButton.setText("OK");

		final Label label = new Label(messageString);
		absolutePanel.add(label, 14, 17);
		label.setSize("234px", "41px");

		dialogBox.center();
	}

	public void setContentPath(final AbsolutePanel innerAbsolutePanel) {

		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Configure ChoiceTask");
		dialogBox.setSize("413", "372");

		final AbsolutePanel absolutePanel = new AbsolutePanel();
		dialogBox.setWidget(absolutePanel);
		absolutePanel.setSize("393px", "372px");

		final TextBox textBox = new TextBox();
		textBox.setReadOnly(true);
		absolutePanel.add(textBox, 47, 13);
		textBox.setWidth("300px");
		// if(contentPath!=null&&!contentPath.equals("")){
		// String[] divided = contentPath.split("\\.");
		// String value_for_display = "";
		// String serviceID = divided[0];
		// String serviceName = dbe.SearchID(serviceID);
		//			
		// if(serviceName!=null){
		// value_for_display = contentPath.replace(serviceID,serviceName);
		// }
		// textBox.setValue(value_for_display);
		// }

		final Tree staticTree = new Tree();
		staticTree.setAnimationEnabled(true);
		staticTree.ensureDebugId("cwTree-staticTree");
		staticTree.addMouseDownHandler(new MouseDownHandler() {

			public void onMouseDown(MouseDownEvent event) {
				// TODO Auto-generated method stub
				Tree tree = (Tree) event.getSource();
				TreeItem treeItem = tree.getSelectedItem(); // 此处获取选中节点有些问题，是否点选的为加号而非内容所致
				if (treeItem != null && treeItem.getChildCount() == 0) {
					String treeItemName = treeItem.getText();

					// 从叶子节点向根节点回溯，找到绝对路径
					while (treeItem.getParentItem() != null) {
						treeItem = treeItem.getParentItem();
						treeItemName = treeItem.getText() + "." + treeItemName;
					}

					contentPath = treeItemName;
					textBox.setText(contentPath);
					String[] complex_name = contentPath.split(" ");
					String[] name_levels = complex_name[0].split("\\.");
					// current_level表示当前的子工作流的深度
					// int current_level = dbe.gde.tabs.getWidgetCount();
					String serviceID, serviceName;
					serviceName = sub_workflow.getHasFirstNode().getName();
					serviceID = sub_workflow.getHasLastNode().getId();
					String new_contentPath = contentPath.replace(serviceName,
							serviceID);
					contentPath = new_contentPath;
					String lastName = name_levels[name_levels.length - 1];
					paramInfo_for_configure = name_for_paraminfo.get(lastName);
					System.out.println("Like breakpoint");

				}

			}

		});

		Client_Node client_Node = sub_workflow.getHasLastNode();
		if (client_Node.getName().equals("Choice")) {

		} else if (client_Node.getName().equals("Loop")) {
			Client_Loop client_Loop = (Client_Loop) client_Node;
			if (client_Loop.getServiceConfigure().getMethodInfo() != null) {
				TreeItem serviceName = staticTree
						.addItem(client_Loop.getName());
				MethodInfo methodInfo = client_Loop.getServiceConfigure()
						.getMethodInfo();
				if (methodInfo != null) {
					TreeItem output = serviceName.addItem(methodInfo
							.getMethodName()
							+ ".OutputInfo");
					addParamInfoInTree(output, methodInfo.getOutputInfo()
							.getParamInfoArray());
				}
			}
		} else if (client_Node.getName().equals("TLoop")) {
			Client_NonFixedNumFor client_NonFixedNumFor = (Client_NonFixedNumFor) client_Node;
			if (client_NonFixedNumFor.getServiceConfigure().getMethodInfo() != null) {
				TreeItem serviceName = staticTree.addItem(client_NonFixedNumFor
						.getName());
				MethodInfo methodInfo = client_NonFixedNumFor
						.getServiceConfigure().getMethodInfo();
				if (methodInfo != null) {
					TreeItem output = serviceName.addItem(methodInfo
							.getMethodName()
							+ ".OutputInfo");
					addParamInfoInTree(output, methodInfo.getOutputInfo()
							.getParamInfoArray());
				}
			}
		} else {
			Client_ServiceTask client_ServiceTask = (Client_ServiceTask) client_Node;
			TreeItem serviceName = staticTree.addItem(client_ServiceTask
					.getName());
			MethodInfo methodInfo = client_ServiceTask.getService()
					.getHasConfiguration().getServiceConfigure()
					.getMethodInfo();
			TreeItem output = serviceName.addItem(methodInfo.getMethodName()
					+ ".OutputInfo");
			addParamInfoInTree(output, methodInfo.getOutputInfo()
					.getParamInfoArray());
		}

		// for(Iterator<SimpleTask> iterator =
		// preTaskList.iterator();iterator.hasNext();){
		// SimpleTask simpleTask = (SimpleTask)iterator.next();
		// if(!simpleTask.getText().equals("Choice")){
		// TreeItem serviceName = staticTree.addItem(simpleTask.getTitle());
		// for(Iterator<MethodInfo> iterator2 =
		// simpleTask.getServiceInfo().getMethodInfoArray().iterator();iterator2.hasNext();){
		// MethodInfo methodInfo = (MethodInfo)iterator2.next();
		// TreeItem output =
		// serviceName.addItem(methodInfo.getMethodName()+".OutputInfo");
		// addParamInfoInTree(output,
		// methodInfo.getOutputInfo().getParamInfoArray());
		// }
		// }
		//			
		// }

		total_staticTreeWrapper = new ScrollPanel(staticTree);
		absolutePanel.add(total_staticTreeWrapper, 47, 33);
		total_staticTreeWrapper.ensureDebugId("cwTree-staticTree-Wrapper");
		total_staticTreeWrapper.addStyleName("no-transparent-panel");
		total_staticTreeWrapper.setSize("300px", "300px");

		final Button okButton = new Button();
		okButton.removeStyleName("gwt-Button");
		DOM.setStyleAttribute(okButton.getElement(), "fontSize", "10pt");
		okButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				// 有问题
				if (textBox.getValue() != null
						&& !textBox.getValue().equals("")) {
					// setHasFinishedConfigure(true);
					TextBox leftOperator = (TextBox) innerAbsolutePanel
							.getWidget(1);
					leftOperator.setValue(textBox.getValue());

					Label compareLabel = (Label) innerAbsolutePanel
							.getWidget(2);
					compareLabel.removeStyleName("gray-text");

					ListBox compareBox = (ListBox) innerAbsolutePanel
							.getWidget(3);
					compareBox.setEnabled(true);
					if (paramInfo_for_configure != null) {
						if (paramInfo_for_configure.getParamTypeName().equals(
								"Integer")) {
							compareBox.clear();
							compareBox.addItem("<");
							compareBox.addItem("=");
							compareBox.addItem(">");
							compareBox.addItem("<=");
							compareBox.addItem(">=");
							compareBox.addItem("!=");
							compareBox.setSelectedIndex(0);
						} else {
							compareBox.clear();
							compareBox.addItem("=");
							compareBox.addItem("!=");
							compareBox.setSelectedIndex(0);
						}
					}

					Label rightOperator = (Label) innerAbsolutePanel
							.getWidget(4);
					rightOperator.removeStyleName("gray-text");

					DeckPanel rightBox = (DeckPanel) innerAbsolutePanel
							.getWidget(5);
					if (paramInfo_for_configure.getParamTypeName().equals(
							"Integer")) {
						rightBox.showWidget(0);
						TextBox textBox = (TextBox) rightBox.getWidget(0);
						textBox.setEnabled(true);

					} else {
						rightBox.showWidget(1);
						ListBox listBox = (ListBox) rightBox.getWidget(1);
						listBox.setEnabled(true);
						listBox.clear();
						if (paramInfo_for_configure.getParamTypeName().equals(
								"String")) {
							if (DataLib
									.whetherInDataLib(paramInfo_for_configure
											.getParamName())) {
								String[] result = paramInfo_for_configure
										.getParamEnums().split(",");
								for (int i = 0; i < result.length; i++) {
									listBox.addItem(result[i]);
								}
							} else {
								listBox.addItem("Sunny");
								listBox.addItem("Cloudy");
								listBox.addItem("Rainy");
							}
						} else {
							listBox.addItem("true");
						}

					}

					dialogBox.hide();

					// AbsolutePanel outterAbsolutePanel =
					// (AbsolutePanel)innerAbsolutePanel.getParent();
					// refreshWhileCondition(conditionTextBox,
					// outterAbsolutePanel);
				}
			}

		});
		absolutePanel.add(okButton, 64, 338);
		okButton.setSize("116px", "21px");
		okButton.setText("OK");

		final Button cancelButton = new Button();
		cancelButton.removeStyleName("gwt-Button");
		DOM.setStyleAttribute(cancelButton.getElement(), "fontSize", "10pt");
		cancelButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				dialogBox.hide();
			}

		});
		absolutePanel.add(cancelButton, 233, 338);
		cancelButton.setSize("116px", "21px");
		cancelButton.setText("CANCEL");

		dialogBox.center();
	}

	// refresh the display of all while condition
	public void refreshWhileCondition(final TextBox conditionTextBox,
			final AbsolutePanel absolutePanel) {

		AbsolutePanel outterAbsolutePanel = absolutePanel;

		for (int i = 1; i <= conditionNumber; i++) {
			AbsolutePanel newinnerAbsolutePanel = (AbsolutePanel) outterAbsolutePanel
					.getWidget(i + 1);

			TextBox leftOperator = (TextBox) newinnerAbsolutePanel.getWidget(1);
			String leftOperatorValue = leftOperator.getValue();
			if (leftOperatorValue != null && !leftOperatorValue.equals("")) {
				ListBox compareBox = (ListBox) newinnerAbsolutePanel
						.getWidget(3);
				String compareBoxValue = compareBox.getValue(compareBox
						.getSelectedIndex());

				DeckPanel deckPanel = (DeckPanel) newinnerAbsolutePanel
						.getWidget(5);
				int index = deckPanel.getVisibleWidget();
				String rightOperatorValue;
				if (index == 0) {
					TextBox textBox = (TextBox) deckPanel.getWidget(0);
					rightOperatorValue = textBox.getValue();
				} else {
					ListBox listBox = (ListBox) deckPanel.getWidget(1);
					rightOperatorValue = listBox.getValue(listBox
							.getSelectedIndex());
				}

				String conditionString = leftOperatorValue + compareBoxValue
						+ rightOperatorValue;
				if (i == 1) {
					conditionTextBox.setValue(conditionString);
				} else {
					conditionTextBox.setValue(conditionTextBox.getValue()
							+ "&&" + conditionString);
				}
			}

		}
	}

	public boolean checkConditionConfigure(AbsolutePanel outterAbsolutePanel) {
		boolean valid = true;
		for (int i = 1; i <= conditionNumber; i++) {
			AbsolutePanel innerAbsolutePanel = (AbsolutePanel) outterAbsolutePanel
					.getWidget(i + 1);
			DeckPanel deckPanel = (DeckPanel) innerAbsolutePanel.getWidget(5);
			int index = deckPanel.getVisibleWidget();
			if (index == 0) {
				TextBox textBox = (TextBox) deckPanel.getWidget(0);
				if (textBox.getValue().equals("")) {
					valid = false;
				}
			}
		}
		return valid;
	}

	public void addParamInfoInTree(TreeItem treeItem, ParamInfo paramInfo) {
		if (paramInfo.isPrimitive()) {
			treeItem.addItem(paramInfo.getParamName() + "	" + "("
					+ paramInfo.getParamTypeName() + ")");
			name_for_paraminfo.put(paramInfo.getParamName(), paramInfo); // 将paraminfo的名字与paraminfo的引用绑定起来
		} else {
			TreeItem currentTreeItem = treeItem.addItem(paramInfo
					.getParamTypeName());
			for (Iterator<ParamInfo> iterator = paramInfo.getFieldInfoArray()
					.iterator(); iterator.hasNext();) {
				ParamInfo sub_paramInfo = (ParamInfo) iterator.next();
				addParamInfoInTree(currentTreeItem, sub_paramInfo);
			}
		}
	}
}
