package com.ics.tcg.web.workflow.client.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasWordWrap;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.MouseWheelListener;
import com.google.gwt.user.client.ui.MouseWheelListenerCollection;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesChangeEvents;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.SourcesMouseWheelEvents;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.datepicker.client.DateBox;
import com.ics.tcg.web.workflow.client.DiagramBuilder;
import com.ics.tcg.web.workflow.client.SubDiagram;
import com.ics.tcg.web.workflow.client.command.AddTimerCommand;
import com.ics.tcg.web.workflow.client.command.DelTimerCommand;
import com.ics.tcg.web.workflow.client.command.SetFinishCommand;
import com.ics.tcg.web.workflow.client.command.SetStartCommand;
import com.ics.tcg.web.workflow.client.command.TaskDeleteCommand;
import com.ics.tcg.web.workflow.client.common.customline.CustomConnection;
import com.ics.tcg.web.workflow.client.common.customline.CustomUIObjectConnector;
import com.ics.tcg.web.workflow.client.composite.complement.ContextMenuGwt;
import com.ics.tcg.web.workflow.client.composite.complement.MyPanel;
import com.ics.tcg.web.workflow.client.service.InputInfo;
import com.ics.tcg.web.workflow.client.service.MethodInfo;
import com.ics.tcg.web.workflow.client.service.ParamInfo;
import com.ics.tcg.web.workflow.client.service.ServiceConfigure;
import com.ics.tcg.web.workflow.client.service.ServiceInfo;

@SuppressWarnings( { "deprecation", "unused" })
public class ServiceTask extends Workflowtasknode {

	public String start_time;

	private ServiceInfo serviceInfo;
	private ServiceConfigure serviceConfigure;
	
	
	public boolean deleteState, hasTimer;
	public MenuItem delTimer, deleteNode, addTimer;
	// public ContextMenuGwt cmg;
	public boolean isopen;
	private HashMap<AbsolutePanel, Integer> user_for_flextable = new HashMap<AbsolutePanel, Integer>(); // 用来记录flextable中absolutepanel的位置，即在第几行
	private HashMap<Image, Integer> use_for_flextable_col0 = new HashMap<Image, Integer>();
	private HashMap<String, Integer> service_id = new HashMap<String, Integer>();

	AbsolutePanel instance;
	int outport_count = 1;

	public int current_index = 0;

	private ScrollPanel downlist_ScrollPanel;

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

	public ArrayList<MyPanel> getconnectorList() {
		return (ArrayList<MyPanel>) connector_list;
	}

	private void createEditableLabel(String labelText, String in_name,
			String out_name, String fault_name) {

		// init_service(); //初始化服务，将服务包含的webservice保存起来

		instance = new AbsolutePanel();
		instance.setSize("130", "50");

		setText(labelText); // 保存simple task的名孄1�7

		panel1 = new AbsolutePanel() {
			public void onBrowserEvent(Event event) {
				if (DOM.eventGetType(event) == Event.ONDBLCLICK) {
					// dbe.select_task(this);
					Configure_Service();
				}

				super.onBrowserEvent(event);
			}
		};
		panel1.sinkEvents(Event.ONDBLCLICK);
		panel1.setSize("100", "50");
		panel1.addStyleName("Task-node");

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
		// panel3.setStyleName("inner-panel");
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
		cmg = new ContextMenuGwt(panel1, menuBar);
		TaskDeleteCommand command = new TaskDeleteCommand(this, dbe);
		deleteNode = new MenuItem("Delete", command);
		AddTimerCommand addTimerCommand = new AddTimerCommand(this, dbe);
		addTimer = new MenuItem("Add Timer", addTimerCommand);
		// addTimer.addStyleName("menu-item");
		// DelTimerCommand delTimerCommand = new DelTimerCommand(this,dbe);
		delTimer = new MenuItem("Del Timer", (Command) null);
		// delTimer.addStyleName("menu-item-disable");
		menuBar.addItem(deleteNode);
		MenuItemSeparator menuItemSeparator = new MenuItemSeparator();
		menuItemSeparator.addStyleName("menuItem-seperator");
		menuItemSeparator.setHeight("2px");
		menuBar.addSeparator(menuItemSeparator);
		menuBar.addItem(addTimer);
		menuBar.addItem(delTimer);
		// 设置添加按钮和删除按钮的可用怄1�7
		if (hasTimer) {
			addTimer.addStyleName("menu-item-disable");
			delTimer.addStyleName("menu-item");
		} else {
			addTimer.addStyleName("menu-item");
			delTimer.addStyleName("menu-item-disable");
		}

		MenuItemSeparator menuItemSeparator2 = new MenuItemSeparator();
		menuItemSeparator2.addStyleName("menuItem-seperator");
		menuBar.addSeparator(menuItemSeparator2);
		menuItemSeparator2.setHeight("2px");

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

		// Add panels/widgets to the widget panel
		instance.add(panel2, 0, 8);
		instance.add(cmg, 15, 0);
		instance.add(panel3, 115, 8);
		instance.add(panel4, 115, 28);

		// Set initial visibilities. This needs to be after
		// adding the widgets to the panel bec ilmause the FlowPanel
		// will mess them up when added.
		panel1.setVisible(true);
		panel2.setVisible(true);
		panel3.setVisible(true);
		panel4.setVisible(true);

		// 把每个连接点放入连接点链表中
		connector_list.add(panel2);
		connector_list.add(panel3);
		connector_list.add(panel4);

		outport_list.add(panel3);

		initWidget(instance);
		setSize("130", "50");
	}

	public void Configure_Service() {

		Get_preTask_all(); // 获取扄1�7有向前相邻一个服务的集合

		final DialogBox dialogBox = new DialogBox();
		dialogBox.addStyleName("g-DialogBox");
		DOM.setStyleAttribute(dialogBox.getElement(), "border", "0px");
		dialogBox.setSize("650px", "460px");
		dialogBox.setText("Configure Service");

		final AbsolutePanel absolutePanel = new AbsolutePanel();
		absolutePanel.setSize("650px", "442px");
		absolutePanel.addStyleName("gwt-absolutepanel");

		dialogBox.setWidget(absolutePanel);

		final TabPanel tabPanel = new TabPanel();
		tabPanel.removeStyleName("gwt-TabPanel");
		tabPanel.addStyleName("l-TabPanel");
		tabPanel.getTabBar().removeStyleName("gwt-TabBar");
		tabPanel.getTabBar().addStyleName("l-TabBar");
		tabPanel.getDeckPanel().removeStyleName("gwt-TabPanelBottom");
		tabPanel.getDeckPanel().addStyleName("l-TabPanelBottom");

		tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {

			public void onSelection(SelectionEvent<Integer> event) {
				// TODO Auto-generated method stub
				current_index = event.getSelectedItem();
			}

		});
		tabPanel.setAnimationEnabled(true);
		absolutePanel.add(tabPanel, 0, 20);
		tabPanel.setSize("645px", "365px");

		for (Iterator<MethodInfo> iterator = serviceInfo.getMethodInfoArray()
				.iterator(); iterator.hasNext();) {
			final MethodInfo methodInfo = (MethodInfo) iterator.next();
			tabPanel.add(GeneratePanelForServiceMethod(methodInfo), methodInfo
					.getMethodName());
		}

		if (serviceConfigure != null) {
			tabPanel.selectTab(current_index);
		}
		// if(hasFinishedConfigure){
		// tabPanel.selectTab(current_index);
		// }
		else {
			tabPanel.selectTab(0);
		}

		final Button okButton = new Button();
		okButton.removeStyleName("gwt-Button");
		DOM.setStyleAttribute(okButton.getElement(), "fontSize", "10pt");
		okButton.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				// TODO Auto-generated method stub
				boolean complete_set = true;
				ArrayList<ParamInfo> paraminfoArrayList = serviceInfo
						.getMethodInfoArray().get(current_index).getInputInfo()
						.getParamInfosArray();
				for (Iterator<ParamInfo> iterator = paraminfoArrayList
						.iterator(); iterator.hasNext();) {
					complete_set = check_Paraminfo((ParamInfo) iterator.next())
							&& complete_set;

				}
				if (complete_set == false) {
					final DialogBox dialogBox = new DialogBox();
					dialogBox.addStyleName("g-DialogBox");
					DOM.setStyleAttribute(dialogBox.getElement(), "border",
							"0px");

					dialogBox.setSize("308px", "176px");
					dialogBox.setText("Message");

					final AbsolutePanel absolutePanel = new AbsolutePanel();
					dialogBox.setWidget(absolutePanel);
					absolutePanel.setSize("308px", "156px");

					final Button button = new Button();
					button.removeStyleName("gwt-Button");
					button.addClickHandler(new ClickHandler() {

						public void onClick(ClickEvent event) {
							dialogBox.hide();
						}

					});
					absolutePanel.add(button, 108, 108);
					button.setWidth("81px");
					button.setText("OK");

					final Label label = new Label(
							"You have forget some input items,please set them now");
					absolutePanel.add(label, 17, 17);
					label.setSize("272px", "66px");

					dialogBox.center();
				} else {
					MethodInfo methodInfo = serviceInfo.getMethodInfoArray()
							.get(current_index);
					getServiceConfigure().setMethodInfo(methodInfo);
					setHasFinishedConfigure(true); //
					// hasFinishedConfigure = true; //表明已经完成了服务配置
					dialogBox.hide();
				}
			}

		});
		absolutePanel.add(okButton, 182, 408);
		okButton.setSize("93px", "21px");
		okButton.setText("OK");

		final Button cancelButton = new Button();
		cancelButton.removeStyleName("gwt-Button");
		DOM.setStyleAttribute(cancelButton.getElement(), "fontSize", "10pt");
		cancelButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				// TODO Auto-generated method stub
				dialogBox.hide();
			}
		});
		absolutePanel.add(cancelButton, 387, 408);
		cancelButton.setSize("93px", "21px");
		cancelButton.setText("CANCEL");

		dialogBox.center();

	}

	public AbsolutePanel GeneratePanelForServiceMethod(MethodInfo m) {

		MethodInfo methodInfo = m;
		InputInfo inputInfo = methodInfo.getInputInfo();

		final AbsolutePanel scrollPanelWrapper = new AbsolutePanel();
		scrollPanelWrapper.setSize("645px", "365px");

		final ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setSize("645px", "355px");
		scrollPanelWrapper.add(scrollPanel);

		// 布置InputInfo的verticalpanel
		final VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setSpacing(5);
		scrollPanel.add(verticalPanel);

		for (Iterator<ParamInfo> iterator = inputInfo.getParamInfosArray()
				.iterator(); iterator.hasNext();) {
			ParamInfo paramInfo = (ParamInfo) iterator.next();
			if (!paramInfo.isPrimitive()) {
				final DisclosurePanel Input_Group_Name = parseParamInfo(paramInfo);
				// Input_Group_Name.removeStyleName("gwt-DisclosurePanel");
				// Input_Group_Name.addStyleName("g-DisclosurePanel");
				verticalPanel.add(Input_Group_Name);
			}
		}

		return scrollPanelWrapper;
	}

	public DisclosurePanel parseParamInfo(ParamInfo complex_paramInfo) {
		String paramName;
		if (complex_paramInfo.getParamName().equals("")) {
			paramName = complex_paramInfo.getParamTypeName();
		} else {
			paramName = complex_paramInfo.getParamName();
		}
		final DisclosurePanel disclosurePanel = new DisclosurePanel(paramName);
		disclosurePanel.ensureDebugId("gwt-DisclosurePanel");
		// disclosurePanel.setWidth("405px");
		disclosurePanel.setOpen(true);
		disclosurePanel.setAnimationEnabled(false); // 此处有较大疑问，设置为ture时，隐藏再展开后，内部内容无法显示完全？
		// disclosurePanel.removeStyleName("gwt-DisclosurePanel");
		// disclosurePanel.addStyleName("g-DisclosurePanel");

		final HorizontalPanel innerHorizontalPanel = new HorizontalPanel();
		final SimplePanel layoutPanel = new SimplePanel();
		layoutPanel.setWidth("15");
		innerHorizontalPanel.add(layoutPanel);

		final VerticalPanel innerVerticalPanel = new VerticalPanel();
		innerHorizontalPanel.add(innerVerticalPanel);
		disclosurePanel.setContent(innerHorizontalPanel);

		// 创建felxtable，用于显示输入参数列表
		final FlexTable flexTable = new FlexTable();
		innerVerticalPanel.add(flexTable);
		flexTable.setSize("435px", "30px");
		FlexCellFormatter cellFormatter = flexTable.getFlexCellFormatter();
		flexTable.addStyleName("gwt-FlexTable");
		flexTable.getCellFormatter().setWidth(0, 0, "135");
		flexTable.getCellFormatter().setWidth(0, 1, "320");

		int simple_input_downlist_index = 0, simple_input_addbutton_index = 0;
		for (Iterator<ParamInfo> iterator = complex_paramInfo
				.getFieldInfoArray().iterator(); iterator.hasNext();) {

			final ParamInfo paramInfo = (ParamInfo) iterator.next();

			if (paramInfo.isPrimitive()) {

				// final HorizontalPanel horizontalPanel = new
				// HorizontalPanel();
				// horizontalPanel.setWidth("95");

				final AbsolutePanel serviceNameAbsolutePanel = new AbsolutePanel();
				serviceNameAbsolutePanel.setSize("135", "28");

				final Image add_image = new Image("img/add.PNG");
				add_image.addStyleName("add_image");
				add_image.addClickHandler(new ClickHandler() {

					public void onClick(ClickEvent event) {
						// TODO Auto-generated method stub
						Image image = (Image) event.getSource();
						int row = use_for_flextable_col0.get(image);
						AbsolutePanel image_parent = (AbsolutePanel) image
								.getParent();
						FlexTable flexTable = (FlexTable) image_parent
								.getParent();
						AbsolutePanel absolutePanel = (AbsolutePanel) flexTable
								.getWidget(row, 1);
						int height = absolutePanel.getOffsetHeight();

						AbsolutePanel new_downlistAbsolutePanel = Generate_empty_downpanel(paramInfo);
						absolutePanel.setSize("325", Integer
								.toString(height + 28));
						// 添加新的下拉列表时，保证内容为空
						TextBox textBox = (TextBox) new_downlistAbsolutePanel
								.getWidget(0);
						textBox.setValue("");
						user_for_flextable.put(new_downlistAbsolutePanel, row);
						absolutePanel.add(new_downlistAbsolutePanel, 0, height);

						serviceNameAbsolutePanel.setSize("135", Integer
								.toString(absolutePanel.getWidgetCount() * 28));
					}

				});
				use_for_flextable_col0.put(add_image,
						simple_input_addbutton_index);
				serviceNameAbsolutePanel.add(add_image, 0, 0);

				final Image del_image = new Image("img/del.PNG");
				del_image.addStyleName("add_image");
				del_image.addClickHandler(new ClickHandler() {

					public void onClick(ClickEvent event) {
						// TODO Auto-generated method stub
						Image image = (Image) event.getSource();
						int row = use_for_flextable_col0.get(image);
						AbsolutePanel image_parent = (AbsolutePanel) image
								.getParent();
						FlexTable flexTable = (FlexTable) image_parent
								.getParent();
						AbsolutePanel absolutePanel = (AbsolutePanel) flexTable
								.getWidget(row, 1);
						AbsolutePanel innerAbsolutePanel = (AbsolutePanel) absolutePanel
								.getWidget(absolutePanel.getWidgetCount() - 1);
						if (absolutePanel.getWidgetCount() > 1) {
							// 更新paraminfo的值
							TextBox textBox = (TextBox) innerAbsolutePanel
									.getWidget(0);
							String value = textBox.getValue();
							if (value != null) {
								if (paramInfo.getParamValue() != null
										&& paramInfo.getParamValue().contains(
												value)) {

									String new_value = paramInfo
											.getParamValue().replace(
													"+" + value, "");
									paramInfo.setParamValue(new_value);
								}
							}
							// 删除最后一个下拉列表框
							int index = absolutePanel.getWidgetCount() - 1;
							absolutePanel.remove(index);
							absolutePanel
									.setHeight(Integer.toString(absolutePanel
											.getWidgetCount() * 28));
							// flexTable.getCellFormatter().setHeight(row, 0,
							// "28");
							flexTable.getCellFormatter().setHeight(
									row,
									1,
									Integer.toString(absolutePanel
											.getOffsetHeight()));

							serviceNameAbsolutePanel.setSize("135",
									Integer.toString(absolutePanel
											.getWidgetCount() * 28));
						}
					}

				});
				use_for_flextable_col0.put(del_image,
						simple_input_addbutton_index);
				serviceNameAbsolutePanel.add(del_image, 20, 0);

				Label label = new Label(paramInfo.getParamName());
				serviceNameAbsolutePanel.add(label, 40, 0);
				flexTable.setWidget(simple_input_downlist_index, 0,
						serviceNameAbsolutePanel);

				final AbsolutePanel outterAbsolutePanel = new AbsolutePanel();
				final ArrayList<AbsolutePanel> downlist_panelAbsolutePanel = region_for_fill_paraminfo(paramInfo);
				outterAbsolutePanel.setSize("325", Integer
						.toString(downlist_panelAbsolutePanel.size() * 28));
				int count = 0;
				for (Iterator<AbsolutePanel> iterator2 = downlist_panelAbsolutePanel
						.iterator(); iterator2.hasNext();) {
					AbsolutePanel absolutePanel3 = iterator2.next();
					user_for_flextable.put(absolutePanel3,
							simple_input_downlist_index);
					outterAbsolutePanel.add(absolutePanel3, 0, 28 * count++);
				}

				flexTable.setWidget(simple_input_downlist_index, 1,
						outterAbsolutePanel);
				// 起初设置为right，导致内部的tree也是靠右排列的，可见这个设置是向内传播的
				cellFormatter.setHorizontalAlignment(
						simple_input_downlist_index, 1,
						HasHorizontalAlignment.ALIGN_LEFT);

				simple_input_downlist_index++;
				simple_input_addbutton_index++;
			} else {
				final DisclosurePanel recurcive = parseParamInfo(paramInfo);
				// recurcive.removeStyleName("gwt-DisclosurePanel");
				// recurcive.addStyleName("g-DisclosurePanel");
				innerVerticalPanel.add(recurcive);
			}

		}
		return disclosurePanel;
	}

	public ArrayList<AbsolutePanel> region_for_fill_paraminfo(
			final ParamInfo paramInfo) {
		ArrayList<AbsolutePanel> downpanel_arraylist = new ArrayList<AbsolutePanel>();
		// 判断textbox的值是否为用户手动设置的
		if (paramInfo.getParamValue() != null) {
			String[] directory = paramInfo.getParamValue().split("\\+");
			for (int i = 0; i < directory.length; i++) {
				AbsolutePanel absolutePanel = Generate_empty_downpanel(paramInfo);
				TextBox textBox = (TextBox) absolutePanel.getWidget(0);
				String valueString = directory[i];
				if (paramInfo.getIsUserFilled()) { // 如果配置信息是用户输入的，则直接显示
					textBox.setValue(valueString);
				} else {
					if (!valueString.equals("")) {
						String[] divided = valueString.split("\\.");
						String value_for_display = "";
						// current_level表示当前的子工作流的深度
						int current_level = dbe.gde.tabs.getWidgetCount();
						// 如果字符串被分成两个字符串，说明配置参数是一个loop的内部变量
						if (divided.length == current_level) {
							// value_for_display = valueString;

							String variableName = divided[divided.length - 1];
							SubDiagram subDiagram = (SubDiagram) dbe;
							LoopTask loopTask = subDiagram.loopTask;
							String LoopTaskName = loopTask.getTitle();
							value_for_display = LoopTaskName + "."
									+ variableName;
						} else {
							String serviceID = divided[0];
							String serviceName = dbe.SearchID(serviceID);

							if (serviceName != null) {
								value_for_display = valueString.replace(
										serviceID, serviceName);
							}
							// 如果没找到，应该是中间删除过节点，此时应该重新配置输入，在工作流的最后检测时不能忘记
						}

						textBox.setValue(value_for_display);
					} else {
						textBox.setValue(valueString);
					}
				}

				downpanel_arraylist.add(absolutePanel);
			}
		} else {
			AbsolutePanel absolutePanel = Generate_empty_downpanel(paramInfo);
			downpanel_arraylist.add(absolutePanel);
		}

		return downpanel_arraylist;
	}

	// 此时的paraminfo已经是基本类型的
	public AbsolutePanel Generate_empty_downpanel(final ParamInfo paramInfo) {
		final AbsolutePanel downlist_panelAbsolutePanel = new AbsolutePanel();
		downlist_panelAbsolutePanel.setSize("325", "28");

		final TextBox textBox = new TextBox();
		if (paramInfo.getParamTypeName().equals("Date")) {
			textBox.setReadOnly(true);
		}
		textBox.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (paramInfo.getParamTypeName().equals("Date")) {
					setDateInTextBox(textBox, paramInfo);
				}
			}

		});

		textBox.addFocusListener(new FocusListener() {

			public void onFocus(Widget sender) {
				// TODO Auto-generated method stub

			}

			// 每次失去焦点，都要更新paraminfo保存的值
			public void onLostFocus(Widget sender) {
				// TODO Auto-generated method stub
				// 获得事件响应的实际控件
				TextBox innerTextBox = (TextBox) sender;
				String value = innerTextBox.getValue();
				if (value != null) {
					/*
					 * 先做个isuserfilled检查，如果是的话，可能由于用户误点进入编辑状态，
					 * 不改变paramvalue值，如果不是用户输入，则进行类型检测，合法情况下刷新保存的值
					 */
					if (!UserFilled(value)) {

					} else {
						// 如果类型合法，并且内容合法，则将内容保存起来
						if (isTypelegal(value, paramInfo.getParamTypeName())
								&& isContentlegal(value, paramInfo
										.getParamName())) {
							sender.removeStyleName("x-form-invalid");

							AbsolutePanel current_innerAbsolutePanel = (AbsolutePanel) innerTextBox
									.getParent();
							AbsolutePanel outterAbsolutePanel = (AbsolutePanel) current_innerAbsolutePanel
									.getParent();

							for (int i = 0; i < outterAbsolutePanel
									.getWidgetCount(); i++) {
								AbsolutePanel innerAbsolutePanel = (AbsolutePanel) outterAbsolutePanel
										.getWidget(i);
								TextBox currentTextBox = (TextBox) innerAbsolutePanel
										.getWidget(0);
								// 设置保存的值的来源，用路径的方式表示,保存的时候把服务名换成id号
								String valueString = currentTextBox.getValue();
								// String value_for_save =
								// treeItemName.replace((CharSequence)valueString,(CharSequence)Integer.toString(service_id.get(valueString)));
								if (i == 0) {
									paramInfo.setParamValue(valueString);
								} else {
									paramInfo.setParamValue(paramInfo
											.getParamValue()
											+ "+" + valueString);
								}
							}
						} else {
							if (value.equals("")) {
								sender.removeStyleName("x-form-invalid");
							} else {
								sender.addStyleName("x-form-invalid");
							}
						}
						paramInfo.setIsUserFilled(true); // 用来指明信息为用户输入，主要用来下次初始化服务配置窗口的显示
					}
				} else {
					sender.removeStyleName("x-form-invalid");
				}
			}

		});
		textBox.setSize("275", "25");
		downlist_panelAbsolutePanel.add(textBox, 15, 2);
		textBox.setTextAlignment(TextBoxBase.ALIGN_LEFT);

		final Image downlist = new Image("img/downlist.PNG");
		downlist.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				// 如果下拉列表未展开，将其展开
				if (isopen == false) {

					isopen = true;

					final Tree staticTree = new Tree();
					staticTree.setAnimationEnabled(true);
					staticTree.ensureDebugId("cwTree-staticTree");
					staticTree.addMouseDownHandler(new MouseDownHandler() {

						public void onMouseDown(MouseDownEvent event) {
							// TODO Auto-generated method stub
							Tree tree = (Tree) event.getSource();
							TreeItem treeItem = tree.getSelectedItem(); // 此处获取选中节点有些问题，是否点选的为加号而非内容所致
							if (treeItem != null
									&& treeItem.getChildCount() == 0) {
								String treeItemName = treeItem.getText();

								// 从叶子节点向根节点回溯，找到绝对路径
								while (treeItem.getParentItem() != null) {
									treeItem = treeItem.getParentItem();
									treeItemName = treeItem.getText() + "."
											+ treeItemName;
								}

								ScrollPanel currentScrollPanel = (ScrollPanel) tree
										.getParent();
								AbsolutePanel current_innerAbsolutePanel = (AbsolutePanel) currentScrollPanel
										.getParent();
								TextBox textBox = (TextBox) current_innerAbsolutePanel
										.getWidget(0);
								textBox.setValue(treeItemName);
								// currentAbsolutePanel.remove(currentScrollPanel);
								// //点选完毕后，移除下拉列表

								isopen = false;
								AbsolutePanel outterAbsolutePanel = (AbsolutePanel) current_innerAbsolutePanel
										.getParent();
								current_innerAbsolutePanel
										.remove(total_staticTreeWrapper); // 已知新加入的scrollpanel的index是2，所以直接删除，否则容易出现越界错误
								current_innerAbsolutePanel.setSize("325", "28");
								int location = outterAbsolutePanel
										.getWidgetIndex(current_innerAbsolutePanel);
								for (; location + 1 < outterAbsolutePanel
										.getWidgetCount(); location++) {
									AbsolutePanel absolutePanel = (AbsolutePanel) outterAbsolutePanel
											.getWidget(location + 1);
									int relative_height = absolutePanel
											.getAbsoluteTop()
											- outterAbsolutePanel
													.getAbsoluteTop();
									outterAbsolutePanel.setWidgetPosition(
											absolutePanel, 0,
											relative_height - 300);
								}
								int widget_count = outterAbsolutePanel
										.getWidgetCount();
								outterAbsolutePanel.setSize("325", Integer
										.toString(widget_count * 28));
								FlexTable current_flextable = (FlexTable) outterAbsolutePanel
										.getParent();

								int row = user_for_flextable
										.get(current_innerAbsolutePanel);
								current_flextable.getCellFormatter().setHeight(
										row,
										1,
										Integer.toString(outterAbsolutePanel
												.getOffsetHeight()));

								paramInfo.setIsUserFilled(false);
								// 如果路径信息还是空的，则直接保存，否则把这次路径信息拼接到上次信息的末尾，并且保证路径信息来源是下拉列表从上往下的得到的
								for (int i = 0; i < outterAbsolutePanel
										.getWidgetCount(); i++) {
									AbsolutePanel innerAbsolutePanel = (AbsolutePanel) outterAbsolutePanel
											.getWidget(i);
									TextBox currentTextBox = (TextBox) innerAbsolutePanel
											.getWidget(0);
									// 设置保存的值的来源，用路径的方式表示,保存的时候把服务名换成id号
									String valueString = currentTextBox
											.getValue();
									// 将保存的路径信息中的服务名替换为id号
									String[] divided = valueString.split("\\.");
									String value_for_save;

									// if the last String of divided equals
									// "currentDate",it imply the configure is a
									// loop variable
									if (divided[divided.length - 1]
											.equals("currentDate (Variable)")) {
										String total_name = "";
										for (int j = divided.length - 2; j >= 0; j--) {
											String loopTaskName = divided[j];
											if (j == divided.length - 2) {
												total_name = loopTaskName;
											} else {
												total_name = loopTaskName + "."
														+ total_name;
											}
										}
										String loopID = dbe.service_name_to_id
												.get(total_name);

										value_for_save = loopID + "."
												+ "currentDate (Variable)";
									} else {
										int current_level = dbe.gde.tabs
												.getWidgetCount();
										if (isIsstart()) {
											String serviceName = divided[0];
											for (int j = 1; j < current_level - 1; j++) {
												serviceName += "." + divided[j];
											}
											String serviceID = dbe.service_name_to_id
													.get(serviceName);
											value_for_save = valueString
													.replace(serviceName,
															serviceID);
										} else {
											String serviceName = divided[0];
											for (int j = 1; j < current_level; j++) {
												serviceName += "." + divided[j];
											}
											String serviceID = dbe.service_name_to_id
													.get(serviceName);
											value_for_save = valueString
													.replace(serviceName,
															serviceID);
										}
									}
									// //current_level表示当前的子工作流的深度
									// int current_level =
									// dbe.gde.tabs.getWidgetCount();
									// //如果字符串被分成两个字符串，说明配置参数是一个loop的内部变量
									// if(divided.length==current_level){
									// String variableName =
									// divided[divided.length-1];
									// SubDiagram subDiagram = (SubDiagram)dbe;
									// LoopTask loopTask = subDiagram.loopTask;
									// String LoopID = loopTask.getID();
									// value_for_save = LoopID+"."+variableName;
									//											
									// }
									// //否则就是前一个节点的输出路径
									// else{
									// if(isIsstart()){
									// String serviceName = divided[0];
									// for(int j=1;j<current_level-1;j++){
									// serviceName += "."+divided[j];
									// }
									// String serviceID =
									// dbe.service_name_to_id.get(serviceName);
									// value_for_save =
									// valueString.replace(serviceName,serviceID);
									// }
									// else{
									// String serviceName=divided[0];
									// for(int j=1;j<current_level;j++){
									// serviceName += "."+divided[j];
									// }
									// String serviceID =
									// dbe.service_name_to_id.get(serviceName);
									// value_for_save =
									// valueString.replace(serviceName,serviceID);
									// }
									//											
									// }

									// String value_for_save =
									// treeItemName.replace((CharSequence)valueString,(CharSequence)Integer.toString(service_id.get(valueString)));
									// if there is only one input configure ,
									// set the value directly,else concatenate
									// all the input configure
									if (i == 0) {
										paramInfo.setParamValue(value_for_save);
									} else {
										paramInfo.setParamValue(paramInfo
												.getParamValue()
												+ "+" + value_for_save);
									}
								}
							}

						}

					});

					// Get_preTask_all(); //在Configure_Service方法中已经调用过一次
					// 如果是子工作流中的节点，则在配置服务的可选项中，加入此子工作流所属的loop的date属性
					if (belongToSubWorkflow) {
						SubDiagram subDiagram = (SubDiagram) dbe;
						if (subDiagram.loopTask.getText().equals("Loop")) {

						} else {
							staticTree.addItem(subDiagram.loopTask.getTitle()
									+ "."
									+ subDiagram.loopTask.getVariableName()
									+ " (Variable)");
						}
						if (subDiagram.loopTask.belongToSubWorkflow) {
							SubDiagram subDiagram2 = (SubDiagram) subDiagram.loopTask.dbe;
							if (!subDiagram2.loopTask.getText().equals("Loop")) {
								staticTree.addItem(subDiagram2.loopTask
										.getTitle()
										+ "."
										+ subDiagram2.loopTask
												.getVariableName()
										+ " (Variable)");
							}
						}
					}

					for (Iterator<Workflowtasknode> iterator = preTaskList.iterator(); iterator
							.hasNext();) {
						Workflowtasknode workflowtasknode = (Workflowtasknode) iterator.next();
						// 判断前端相邻节点是否为choice，如果是的话表示其控制流的功能，不需要配置数据属性
						if (!workflowtasknode.getText().equals("Choice")) {
							if (workflowtasknode.getText().equals("Loop")) {
								LoopTask loopTask = (LoopTask) workflowtasknode;
								if (loopTask.getServiceConfigure()
										.getMethodInfo() != null) {
									TreeItem serviceName = staticTree
											.addItem(loopTask.getTitle());
									service_id.put(loopTask.getTitle(),
											loopTask.hashCode());
									// for(Iterator<MethodInfo> iterator2 =
									// simpleTask.getServiceInfo().getMethodInfoArray().iterator();iterator2.hasNext();){
									// MethodInfo methodInfo =
									// (MethodInfo)iterator2.next();
									// TreeItem output =
									// serviceName.addItem(methodInfo.getMethodName()+".OutputInfo");
									// addParamInfoInTree(output,paramInfo,
									// methodInfo.getOutputInfo().getParamInfoArray());
									// }
									MethodInfo methodInfo = loopTask
											.getServiceConfigure()
											.getMethodInfo();
									if (methodInfo != null) {
										TreeItem output = serviceName
												.addItem(methodInfo
														.getMethodName()
														+ ".OutputInfo");
										addParamInfoInTree(output, paramInfo,
												methodInfo.getOutputInfo()
														.getParamInfoArray());
									}
								}
							} else if (workflowtasknode.getText().equals("TLoop")) {
								NonFixedFor nonFixedFor = (NonFixedFor) workflowtasknode;
								if (nonFixedFor.getServiceConfigure()
										.getMethodInfo() != null) {
									TreeItem serviceName = staticTree
											.addItem(nonFixedFor.getTitle());
									service_id.put(nonFixedFor.getTitle(),
											nonFixedFor.hashCode());
									// for(Iterator<MethodInfo> iterator2 =
									// simpleTask.getServiceInfo().getMethodInfoArray().iterator();iterator2.hasNext();){
									// MethodInfo methodInfo =
									// (MethodInfo)iterator2.next();
									// TreeItem output =
									// serviceName.addItem(methodInfo.getMethodName()+".OutputInfo");
									// addParamInfoInTree(output,paramInfo,
									// methodInfo.getOutputInfo().getParamInfoArray());
									// }
									MethodInfo methodInfo = nonFixedFor
											.getServiceConfigure()
											.getMethodInfo();
									if (methodInfo != null) {
										TreeItem output = serviceName
												.addItem(methodInfo
														.getMethodName()
														+ ".OutputInfo");
										addParamInfoInTree(output, paramInfo,
												methodInfo.getOutputInfo()
														.getParamInfoArray());
									}
								}
							} else {
								// check whether preNode has been configured
								// ,then determine how to display the preNode
								// information
								ServiceTask serviceTask = (ServiceTask)workflowtasknode;
								if (serviceTask.getServiceConfigure()
										.getMethodInfo() != null) {
									TreeItem serviceName = staticTree
											.addItem(workflowtasknode.getTitle());
									service_id.put(workflowtasknode.getTitle(),
											workflowtasknode.hashCode());
									// for(Iterator<MethodInfo> iterator2 =
									// simpleTask.getServiceInfo().getMethodInfoArray().iterator();iterator2.hasNext();){
									// MethodInfo methodInfo =
									// (MethodInfo)iterator2.next();
									// TreeItem output =
									// serviceName.addItem(methodInfo.getMethodName()+".OutputInfo");
									// addParamInfoInTree(output,paramInfo,
									// methodInfo.getOutputInfo().getParamInfoArray());
									// }
									MethodInfo methodInfo = serviceTask
											.getServiceConfigure()
											.getMethodInfo();
									if (methodInfo != null) {
										TreeItem output = serviceName
												.addItem(methodInfo
														.getMethodName()
														+ ".OutputInfo");
										addParamInfoInTree(output, paramInfo,
												methodInfo.getOutputInfo()
														.getParamInfoArray());
									}
								}
							}
						}

					}

					total_staticTreeWrapper = new ScrollPanel(staticTree);
					total_staticTreeWrapper
							.ensureDebugId("cwTree-staticTree-Wrapper");
					total_staticTreeWrapper
							.addStyleName("no-transparent-panel");
					total_staticTreeWrapper.setSize("300px", "300px");

					/************************************** 将几层absolutepanel整体扩大 *************************/
					Image current_image = (Image) event.getSource();
					AbsolutePanel current_innerAbsolutePanel = (AbsolutePanel) current_image
							.getParent();
					AbsolutePanel outterAbsolutePanel = (AbsolutePanel) current_innerAbsolutePanel
							.getParent();
					int image_relativeheight = current_innerAbsolutePanel
							.getAbsoluteTop()
							- outterAbsolutePanel.getAbsoluteTop();
					current_innerAbsolutePanel.setSize("325", "328");
					outterAbsolutePanel.setSize("325", Integer
							.toString(300 + 28 * outterAbsolutePanel
									.getWidgetCount()));
					FlexTable current_flextable = (FlexTable) outterAbsolutePanel
							.getParent(); // 此块的设置使得absolutepanel丧失了松耦合特性

					int row = user_for_flextable
							.get(current_innerAbsolutePanel);
					current_flextable.getCellFormatter().setHeight(row, 1,
							Integer.toString(328 + image_relativeheight));

					current_innerAbsolutePanel.add(total_staticTreeWrapper, 15,
							28);

					int location = outterAbsolutePanel
							.getWidgetIndex(current_innerAbsolutePanel);
					for (; location + 1 < outterAbsolutePanel.getWidgetCount(); location++) {
						AbsolutePanel absolutePanel = (AbsolutePanel) outterAbsolutePanel
								.getWidget(location + 1);
						int relative_height = absolutePanel.getAbsoluteTop()
								- outterAbsolutePanel.getAbsoluteTop();
						outterAbsolutePanel.setWidgetPosition(absolutePanel, 0,
								relative_height + 300);
					}

				}
				// 若下拉列表展开了，则将其收回
				else {

					isopen = false;
					Image current_image = (Image) event.getSource();
					AbsolutePanel current_innerAbsolutePanel = (AbsolutePanel) current_image
							.getParent();
					AbsolutePanel outterAbsolutePanel = (AbsolutePanel) current_innerAbsolutePanel
							.getParent();
					current_innerAbsolutePanel.remove(total_staticTreeWrapper); // 已知新加入的scrollpanel的index是2，所以直接删除，否则容易出现越界错误
					current_innerAbsolutePanel.setSize("325", "28");
					int location = outterAbsolutePanel
							.getWidgetIndex(current_innerAbsolutePanel);
					for (; location + 1 < outterAbsolutePanel.getWidgetCount(); location++) {
						AbsolutePanel absolutePanel = (AbsolutePanel) outterAbsolutePanel
								.getWidget(location + 1);
						int relative_height = absolutePanel.getAbsoluteTop()
								- outterAbsolutePanel.getAbsoluteTop();
						outterAbsolutePanel.setWidgetPosition(absolutePanel, 0,
								relative_height - 300);
					}
					int widget_count = outterAbsolutePanel.getWidgetCount();
					outterAbsolutePanel.setSize("325", Integer
							.toString(widget_count * 28));
					FlexTable current_flextable = (FlexTable) outterAbsolutePanel
							.getParent();

					int row = user_for_flextable
							.get(current_innerAbsolutePanel);
					current_flextable.getCellFormatter().setHeight(
							row,
							1,
							Integer.toString(outterAbsolutePanel
									.getOffsetHeight()));
				}

			}

		});
		downlist_panelAbsolutePanel.add(downlist, 290, 2);
		downlist.setSize("25", "23");

		return downlist_panelAbsolutePanel;
	}

	public void addParamInfoInTree(TreeItem treeItem,
			ParamInfo paramInfo_for_textbox, ParamInfo paramInfo) {
		if (paramInfo.isPrimitive()) {
			TreeItem deepesTreeItem = new TreeItem(paramInfo.getParamName()
					+ "	" + "(" + paramInfo.getParamTypeName() + ")");
			treeItem.addItem(deepesTreeItem);
			// 对下拉列表中的树的内容做类型判断，如果不符合，则隐藏树的内容
			if (paramInfo_for_textbox.getParamTypeName().equals("String")) {
				deepesTreeItem.setVisible(true);
			} else if (paramInfo_for_textbox.getParamTypeName().equals(
					"Integer")) {
				if (!paramInfo.getParamTypeName().equals("Integer")) {
					deepesTreeItem.setVisible(false);
				}
			} else if (paramInfo_for_textbox.getParamTypeName().equals("Date")) {
				if (!paramInfo.getParamTypeName().equals("Date")) {
					deepesTreeItem.setVisible(false);
				}
			} else if (paramInfo_for_textbox.getParamTypeName().equals("Float")) {
				if (!paramInfo.getParamTypeName().equals("Float")) {
					deepesTreeItem.setVisible(false);
				}
			}
		} else {
			TreeItem currentTreeItem = treeItem.addItem(paramInfo
					.getParamTypeName());
			for (Iterator<ParamInfo> iterator = paramInfo.getFieldInfoArray()
					.iterator(); iterator.hasNext();) {
				ParamInfo sub_paramInfo = (ParamInfo) iterator.next();
				addParamInfoInTree(currentTreeItem, paramInfo_for_textbox,
						sub_paramInfo);
			}
		}
	}

	// 判断每个输入项是否都配置过了，如果配置了，则返回true，否则返回false
	public boolean check_Paraminfo(ParamInfo paramInfo) {
		if (paramInfo.isPrimitive()) {
			if (paramInfo.getParamValue() != null) {
				return true;
			} else {
				return false;
			}
		} else {
			for (Iterator<ParamInfo> iterator = paramInfo.getFieldInfoArray()
					.iterator(); iterator.hasNext();) {
				if (check_Paraminfo((ParamInfo) iterator.next()) == false) {
					return false;
				}
			}
			return true;
		}
	}

	public boolean check_ParamInfoWeatherComefrom(ServiceTask simpleTask,
			ParamInfo paramInfo) {
		String id = simpleTask.getID();
		return containIDString(id, paramInfo);
	}

	private boolean containIDString(String ID, ParamInfo paramInfo) {
		if (paramInfo.isPrimitive()) {
			if (!paramInfo.getIsUserFilled()) {
				String valueString = paramInfo.getParamValue();
				// 将保存的路径信息中的服务名替换为id号
				String[] divided = valueString.split("\\.");
				String serviceID = divided[0];
				if (serviceID.equals(ID)) {
					paramInfo.setParamValue("");
					return true;
				}
			}
			return false;
		} else {
			for (Iterator<ParamInfo> iterator = paramInfo.getFieldInfoArray()
					.iterator(); iterator.hasNext();) {
				if (containIDString(ID, iterator.next()) == true) {
					return true;
				}
			}
			return false;
		}
	}

	public void setDateInTextBox(final TextBox textBox,
			final ParamInfo paramInfo) {
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
				// TODO Auto-generated method stub
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
						paramInfo.setParamValue(dateString);
						paramInfo.setIsUserFilled(true);
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

	public void EnableAddTimer() {
		if (hasTimer == false) {
			delTimer.setCommand((Command) null);
			delTimer.addStyleName("menu-item-disable");

			AddTimerCommand addTimerCommand = new AddTimerCommand(this, dbe);
			addTimer.setCommand(addTimerCommand);
			addTimer.removeStyleName("menu-item-disable");
		}
	}

	public void DisableAddTimer() {
		if (hasTimer == true) {

			addTimer.setCommand((Command) null);
			addTimer.addStyleName("menu-item-disable");

			DelTimerCommand delTimerCommand = new DelTimerCommand(this, dbe);
			delTimer.setCommand(delTimerCommand);
			delTimer.removeStyleName("menu-item-disable");
		}
	}

	public void setDisabled(MenuItem menuItem) {
		if (menuItem.getText().equals("Del Timer")) {
			menuItem.setCommand((Command) null);
		} else if (menuItem.getText().equals("Add Timer")) {
			menuItem.setCommand((Command) null);
		}
	}

//	// get preTaskNodeList,if current node is the begin node of one loop,then
//	// preTaskNodelist contains the preTaskNodes of loop
//	public void Get_preTask_all() {
//		// 获取所有前向节点集合之前，必须先清空preTaskList,preTaskSet
//		preTaskList.clear();
//		preTaskSet.clear();
//		Get_preTask_all(this);
//
//	}
//
//	// 递归调用此函数，以获取所有的前向节点
//	@SuppressWarnings("unchecked")
//	public void Get_preTask_all(ServiceTask simpleTask) {
//		// 如果不是开始节点，则直接找到其前驱节点列表
//		if (!isstart) {
//			CustomUIObjectConnector cons = CustomUIObjectConnector
//					.getWrapper(simpleTask.getPanel2());
//			Collection collection = cons.getConnections();
//			CustomUIObjectConnector front_end_Connector;
//			for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
//				CustomConnection c = (CustomConnection) iterator.next();
//				front_end_Connector = (CustomUIObjectConnector) c
//						.getConnected().get(0);
//				MyPanel front_end_panelPanel = (MyPanel) front_end_Connector.wrapped;
//				ServiceTask front_end_simpletask = (ServiceTask) front_end_panelPanel
//						.getParent().getParent();
//				if (!preTaskSet.contains(front_end_simpletask)) {
//					preTaskList.add(front_end_simpletask);
//					preTaskSet.add(front_end_simpletask);
//					Get_preTask_all(front_end_simpletask);
//				}
//
//			}
//			System.out.println("get preTasklist successful");
//		}
//		// 如果是开始节点，若此节点属于子工作流，则为包含此子工作流的looptask的前驱节点
//		else if (belongToSubWorkflow) {
//			SubDiagram subDiagram = (SubDiagram) dbe;
//			subDiagram.loopTask.Get_preTask_all();
//			preTaskList = subDiagram.loopTask.preTaskList;
//		}
//	}
//
//	public void Get_backTask_all() {
//		backTaskList.clear();
//		backTaskSet.clear();
//		Get_backTask_all(this);
//	}
//
//	@SuppressWarnings("unchecked")
//	public void Get_backTask_all(ServiceTask simpleTask) {
//		ArrayList<MyPanel> list = simpleTask.outport_list;
//		for (Iterator<MyPanel> iterator = list.iterator(); iterator.hasNext();) {
//			MyPanel outport = (MyPanel) iterator.next();
//			CustomUIObjectConnector cons = CustomUIObjectConnector
//					.getWrapper(outport);
//			Collection collection = cons.getConnections();
//			CustomUIObjectConnector back_end_Connector;
//			for (Iterator iterator2 = collection.iterator(); iterator2
//					.hasNext();) {
//				CustomConnection c = (CustomConnection) iterator2.next();
//				back_end_Connector = (CustomUIObjectConnector) c.getConnected()
//						.get(1);
//				MyPanel back_end_panelPanel = (MyPanel) back_end_Connector.wrapped;
//				ServiceTask back_end_simpletask = (ServiceTask) back_end_panelPanel
//						.getParent().getParent();
//				if (!backTaskSet.contains(back_end_simpletask)) {
//					backTaskList.add(back_end_simpletask);
//					backTaskSet.add(back_end_simpletask);
//					Get_backTask_all(back_end_simpletask);
//				}
//
//			}
//			System.out.println("set successful");
//		}
//	}

	public Widget GetBackPanel() {
		return super.getWidget();
	}

	public boolean isContentlegal(String input, String paramName) {
		if (paramName.equals("userPhoneNum")) {
			if (!isPhoneNumber(input)) {
				return false;
			}
			return true;
		} else
			return true;
	}

	public boolean isTypelegal(String input, String type) {
		boolean isvalid = false;
		if (type.equals("Integer")) {
			isvalid = isInt(input);
		} else if (type.equals("Double")) {
			isvalid = isDouble(input);
		} else if (type.equals("String")) {
			isvalid = true;
		} else if (type.equals("Date")) {
			isvalid = isDate(input);
		}
		return isvalid;
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

	public boolean isDouble(String str) {
		try {
			double d = Double.parseDouble(str); // 是浮点数
			return true;
		} catch (NumberFormatException e) {
			// 不是浮点敄1�7
			return false;
		}
	}

	public boolean isDate(String str) {
		if (str != null && str.length() > 0) {
			String[] date_time = str.split(" ");
			if (date_time.length == 2) {
				String[] time = date_time[1].split(":");
				if (dateFormatHelper(date_time[0])) {
					if (time.length == 3 && Integer.parseInt(time[0]) >= 0
							&& Integer.parseInt(time[0]) <= 24
							&& Integer.parseInt(time[1]) >= 0
							&& Integer.parseInt(time[1]) <= 60
							&& Integer.parseInt(time[2]) >= 0
							&& Integer.parseInt(time[2]) <= 60) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/*****
	 * 判断该字符串是否为正确格式的时间 YYYY-MM-DD 注意加入对于月份和天数的判断还有闰年 比如2月28日
	 * 
	 * @param dateString
	 * @return
	 */

	// date判断辅助类
	private static boolean dateFormatHelper(String dateString) {
		String[] tempSplits = new String[3];
		try {
			tempSplits = dateString.split("-");
			if ((Integer.parseInt(tempSplits[0]) >= 0 && Integer
					.parseInt(tempSplits[0]) <= 3000)
					&& (Integer.parseInt(tempSplits[1]) >= 1 && Integer
							.parseInt(tempSplits[1]) <= 12)
					&& (Integer.parseInt(tempSplits[2]) >= 1 && Integer
							.parseInt(tempSplits[2]) <= 31)) {
				// 获得当前月份 如果是 4 6 9 11月份则应该是30天
				int tmpMonth = Integer.parseInt(tempSplits[1]);
				int tmpDate = Integer.parseInt(tempSplits[2]);
				int tmpYear = Integer.parseInt(tempSplits[0]);

				if (tmpMonth == 4 || tmpMonth == 6 || tmpMonth == 9
						|| tmpMonth == 11) {
					if (tmpDate > 30) {
						return false;
					}
				} else if (tmpMonth == 2) {
					// 对于2月份天数的判断
					if (tmpYear % 4 == 0) {
						if (tmpDate > 29) {
							return false;
						}
					} else {
						if (tmpDate > 28) {
							return false;
						}
					}
				}
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}

	public boolean isPhoneNumber(String string) {
		if (string.length() == 11) {
			for (int i = 0; i < 11; i++) {
				if (string.charAt(i) <= '9' && string.charAt(i) >= '0') {

				} else {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean UserFilled(String string) {
		String not_user_filled_character1 = "OutputInfo", not_user_filled_character2 = "(Variable)";
		boolean state1 = string
				.contains((CharSequence) not_user_filled_character1);
		boolean state2 = string
				.contains((CharSequence) not_user_filled_character2);
		return !(state1 || state2);
	}

	public void TextBoxisLegal() {

	}

	public ServiceTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ServiceTask(String labelText, String in, String out, String fault,
			DiagramBuilder d) {
		dbe = d;
		createEditableLabel(labelText, in, out, fault);
	}

	public ServiceTask(String labelText, DiagramBuilder d) {
		dbe = d;
		createEditableLabel(labelText, "I", "O", "F");
	}

	public ServiceTask(String labelText) {
		createEditableLabel(labelText, "I", "O", "F");
	}
}
