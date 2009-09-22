package com.ics.tcg.web.workflow.client.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import pl.balon.gwt.diagrams.client.connection.Connection;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.ics.tcg.web.workflow.client.DiagramBuilder;
import com.ics.tcg.web.workflow.client.SubDiagram;
import com.ics.tcg.web.workflow.client.command.SetFinishCommand;
import com.ics.tcg.web.workflow.client.command.SetStartCommand;
import com.ics.tcg.web.workflow.client.command.TaskDeleteCommand;
import com.ics.tcg.web.workflow.client.common.customline.CustomConnection;
import com.ics.tcg.web.workflow.client.common.customline.CustomUIObjectConnector;
import com.ics.tcg.web.workflow.client.composite.complement.ContextMenuGwt;
import com.ics.tcg.web.workflow.client.composite.complement.MyPanel;
import com.ics.tcg.web.workflow.client.data.Client_IfCondition;
import com.ics.tcg.web.workflow.client.service.DataLib;
import com.ics.tcg.web.workflow.client.service.MethodInfo;
import com.ics.tcg.web.workflow.client.service.ParamInfo;

public class ChoiceTask extends Workflowtasknode {

	public String contentPath; //
	public ParamInfo paramInfo_for_configure;
	boolean condition_is_int;
	AbsolutePanel instance;
	public int outport_count = 1;
	public Label ori_TextLabel;
	boolean isEditable = true;
	boolean isEditing = false;
	int popup_width, popup_height;

	PopupPanel popupPanel;

	public List<Label> label_array = new ArrayList<Label>(10);
	public List<Client_IfCondition> IfConditionlist = new ArrayList<Client_IfCondition>(
			10);
	// List<Label>

	Map<Label, MyPanel> outport = new HashMap<Label, MyPanel>();
	Map<String, ParamInfo> name_for_paraminfo = new HashMap<String, ParamInfo>();

	@SuppressWarnings("unchecked")
	public void setTextLabel(Label label, String labelText) {
		// label
		if (labelText.equals("DEL")) {
			List<Connection> connections = new ArrayList<Connection>();
			label.removeFromParent();
			MyPanel myPanel = (MyPanel) outport.get(label);

			Collection<Connection> cons = CustomUIObjectConnector.getWrapper(
					myPanel).getConnections();
			for (Iterator j = cons.iterator(); j.hasNext();) {
				Connection c = (Connection) j.next();
				connections.add(c);
			}
			for (Iterator k = connections.iterator(); k.hasNext();) {
				Connection c = (Connection) k.next();
				c.remove();
			}
			CustomUIObjectConnector.unwrap(myPanel);

			connector_list.remove(myPanel);
			myPanel.removeFromParent();

			outport.remove(label);

			int index = label_array.indexOf(label);
			label_array.remove(index);
			IfConditionlist.remove(index);
			outport_count--;
			outport_list.remove(myPanel);

			// æ›´æ–°choiceTaskçš„æ˜¾ç¤º
			refresh();

			// æ›´æ–°è¿žçº¿çš„æ˜¾ç¤º
			for (Iterator iterator = getconnectorList().iterator(); iterator
					.hasNext();) {
				CustomUIObjectConnector c = CustomUIObjectConnector
						.getWrapper((MyPanel) iterator.next());
				if (c != null) {
					c.update();
				}
			}
		} else {
			label.setText(labelText);
		}
	}

	public void cancelLabelChange(Label label, TextBox textbox) {

	}

	public void RestoreVisibility(Label label, TextBox textbox) {
		label.setVisible(true);
		textbox.setVisible(false);
	}

	// update output display ,traverse the outport list and display them again
	public void refresh() {
		// int addheight = 20;
		panel1.setHeight("60");
		instance.setHeight("60");
		setHeight("60");
		instance.add(panel2, 0, 10);
		instance.add(panel4, 85, 45);
		for (int index = 0; index < outport_count; index++) {
			int panel1_original_height = panel1.getOffsetHeight();
			int instance_original_height = instance.getOffsetHeight();
			int looptask_original_height = getOffsetHeight();
			panel1.setHeight(Integer.toString(panel1_original_height + 20));
			instance.setHeight(Integer.toString(instance_original_height + 20));
			setHeight(Integer.toString(looptask_original_height + 20));

			Label key_label = (Label) label_array.get(index);
			MyPanel value_panel = (MyPanel) outport.get(key_label);
			panel1.add(key_label, 24, 10 + (index) * 20);
			instance.add(value_panel, 85, 10 + index * 20);
			instance.add(panel4, 85, 65 + index * 20);
		}
	}

	private void createEditableLabel(String labelText, String in_name,
			String out_name, String fault_name) {

		instance = new AbsolutePanel();
		instance.setSize("100", "80");

		setText(labelText);
		panel1 = new AbsolutePanel() {
			public void onBrowserEvent(Event event) {
				if (DOM.eventGetType(event) == Event.ONDBLCLICK) {
					configure_choice_task();
				}

				super.onBrowserEvent(event);
			}
		};
		panel1.sinkEvents(Event.ONDBLCLICK);
		panel1.setSize("70", "80");
		panel1.setStyleName("choice-inner-panel");

		ori_TextLabel = new Label("false") {
			public void onBrowserEvent(Event event) {
				if (DOM.eventGetType(event) == Event.ONMOUSEDOWN) {
					if (DOM.eventGetButton(event) == Event.BUTTON_LEFT) {
						final int x = DOM.eventGetClientX(event);
						final int y = DOM.eventGetClientY(event);
						// System.out.println(getAbsoluteLeft());
						if (contentPath != null && !contentPath.equals("")) {
							generate_popuppanel(this, x, y);
						}
						DOM.eventCancelBubble(event, true);// é˜»æ­¢äº‹ä»¶ä¼ åˆ°çˆ¶å¯¹è±¡ï¼Œå�¦åˆ™ä¼šå‡ºçŽ°ç‚¹å‡»å��ä¸ºfalseçš„labelæ—¶ï¼ŒèŠ‚ç‚¹ç¦»å¼€èƒŒæ™¯é�¢æ�¿ï¼Œç§»åŠ¨èµ·æ�¥
					}
				}
				super.onBrowserEvent(event);
			}
		};
		ori_TextLabel.sinkEvents(Event.ONMOUSEDOWN);
		// ori_TextLabel.addClickHandler(new ClickHandler(){
		//
		// public void onClick(ClickEvent event) {
		// // TODO Auto-generated method stub
		//				
		// }
		//			
		// });
		ori_TextLabel.setSize("44", "10");
		ori_TextLabel.addStyleName("choice-label");
		label_array.add(0, ori_TextLabel);
		panel1.add(ori_TextLabel, 24, 10);

		Client_IfCondition client_IfCondition = new Client_IfCondition(
				ori_TextLabel.getText());
		IfConditionlist.add(0, client_IfCondition);

		Image image = new Image("img/new.png");
		image.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				addoutput();
			}

		});
		image.addStyleName("select_handle");
		panel1.add(image, 55, 0);

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
		instance.add(panel3, 85, 10);
		instance.add(panel4, 85, 65);

		// add outputlabel and outputimage in outportMap
		connector_list.add(panel2);
		connector_list.add(panel3);
		connector_list.add(panel4);
		outport.put(ori_TextLabel, panel3);
		outport_list.add(panel3);

		// Set the widget that this Composite represents
		initWidget(instance);
		setSize("100", "80");

	}

	public void configure_choice_task() {

		final DialogBox dialogBox = new DialogBox();
		dialogBox.addStyleName("g-DialogBox");
		DOM.setStyleAttribute(dialogBox.getElement(), "border", "0px");
		dialogBox.setText("Configure ChoiceTask");
		dialogBox.setSize("413", "372");

		final AbsolutePanel absolutePanel = new AbsolutePanel();
		dialogBox.setWidget(absolutePanel);
		absolutePanel.setSize("393px", "372px");

		final TextBox textBox = new TextBox();
		textBox.setReadOnly(true);
		absolutePanel.add(textBox, 47, 13);
		textBox.setWidth("300px");
		if (contentPath != null && !contentPath.equals("")) {
			String[] divided = contentPath.split("\\.");
			String value_for_display = "";
			String serviceID = divided[0];
			String serviceName = dbe.SearchID(serviceID);

			if (serviceName != null) {
				value_for_display = contentPath.replace(serviceID, serviceName);
			}
			textBox.setValue(value_for_display);
		}

		final Tree staticTree = new Tree();
		staticTree.setAnimationEnabled(true);
		staticTree.ensureDebugId("cwTree-staticTree");
		staticTree.addMouseDownHandler(new MouseDownHandler() {

			public void onMouseDown(MouseDownEvent event) {
				// TODO Auto-generated method stub
				Tree tree = (Tree) event.getSource();
				TreeItem treeItem = tree.getSelectedItem(); // æ­¤å¤„èŽ·å�–é€‰ä¸­èŠ‚ç‚¹æœ‰äº›é—®é¢˜ï¼Œæ˜¯å�¦ç‚¹é€‰çš„ä¸ºåŠ å�·è€Œé�žå†…å®¹æ‰€è‡´
				if (treeItem != null && treeItem.getChildCount() == 0) {
					String treeItemName = treeItem.getText();

					// ä»Žå�¶å­�èŠ‚ç‚¹å�‘æ ¹èŠ‚ç‚¹å›žæº¯ï¼Œæ‰¾åˆ°ç»�å¯¹è·¯å¾„
					while (treeItem.getParentItem() != null) {
						treeItem = treeItem.getParentItem();
						treeItemName = treeItem.getText() + "." + treeItemName;
					}

					contentPath = treeItemName;
					textBox.setText(contentPath);
					String[] complex_name = contentPath.split(" ");
					String[] name_levels = complex_name[0].split("\\.");

					// current_levelè¡¨ç¤ºå½“å‰�çš„å­�å·¥ä½œæµ�çš„æ·±åº¦
					int current_level = dbe.gde.tabs.getWidgetCount();
					String serviceID, serviceName;
					if (isIsstart()) {
						serviceName = name_levels[0];
						for (int j = 1; j < current_level - 1; j++) {
							serviceName += "." + name_levels[j];
						}
						serviceID = dbe.service_name_to_id.get(serviceName);
					} else {
						serviceName = name_levels[0];
						for (int j = 1; j < current_level; j++) {
							serviceName += "." + name_levels[j];
						}
						serviceID = dbe.service_name_to_id.get(serviceName);
					}
					String new_contentPath = contentPath.replace(serviceName,
							serviceID);
					contentPath = new_contentPath;
					String lastName = name_levels[name_levels.length - 1];
					paramInfo_for_configure = name_for_paraminfo.get(lastName);
					// System.out.println("Like breakpoint");
					System.out.println(paramInfo_for_configure.getParamEnums());

				}

			}

		});

		Get_preTask_all();

		// for(Iterator<SimpleTask> iterator =
		// preTaskList.iterator();iterator.hasNext();){
		// SimpleTask simpleTask = (SimpleTask)iterator.next();
		// // if(!simpleTask.getText().equals("Choice")){
		// // TreeItem serviceName = staticTree.addItem(simpleTask.getTitle());
		// // for(Iterator<MethodInfo> iterator2 =
		// simpleTask.getServiceInfo().getMethodInfoArray().iterator();iterator2.hasNext();){
		// // MethodInfo methodInfo = (MethodInfo)iterator2.next();
		// // TreeItem output =
		// serviceName.addItem(methodInfo.getMethodName()+".OutputInfo");
		// // addParamInfoInTree(output,
		// methodInfo.getOutputInfo().getParamInfoArray());
		// // }
		// // }
		// if(!simpleTask.getText().equals("Choice")){
		// //check whether preNode has been configured ,then determine how to
		// display the preNode information
		// if(simpleTask.getServiceConfigure().getMethodInfo()!=null){
		// TreeItem serviceName = staticTree.addItem(simpleTask.getTitle());
		// MethodInfo methodInfo =
		// simpleTask.getServiceConfigure().getMethodInfo();
		// if(methodInfo!=null){
		// TreeItem output =
		// serviceName.addItem(methodInfo.getMethodName()+".OutputInfo");
		// addParamInfoInTree(output,
		// methodInfo.getOutputInfo().getParamInfoArray());
		// }
		// }
		// }
		//			
		// }

		for (Iterator<Workflowtasknode> iterator = preTaskList.iterator(); iterator
				.hasNext();) {
			Workflowtasknode workflowtasknode = (Workflowtasknode) iterator.next();
			// åˆ¤æ–­å‰�ç«¯ç›¸é‚»èŠ‚ç‚¹æ˜¯å�¦ä¸ºchoiceï¼Œå¦‚æžœæ˜¯çš„è¯�è¡¨ç¤ºå…¶æŽ§åˆ¶æµ�çš„åŠŸèƒ½ï¼Œä¸�éœ€è¦�é…�ç½®æ•°æ�®å±žæ€§
			if (!workflowtasknode.getText().equals("Choice")) {
				if (workflowtasknode.getText().equals("Loop")) {
					LoopTask loopTask = (LoopTask) workflowtasknode;
					if (loopTask.getServiceConfigure().getMethodInfo() != null) {
						TreeItem serviceName = staticTree.addItem(loopTask
								.getTitle());
						MethodInfo methodInfo = loopTask.getServiceConfigure()
								.getMethodInfo();
						if (methodInfo != null) {
							TreeItem output = serviceName.addItem(methodInfo
									.getMethodName()
									+ ".OutputInfo");
							addParamInfoInTree(output, methodInfo
									.getOutputInfo().getParamInfoArray());
						}
					}
				} else if (workflowtasknode.getText().equals("TLoop")) {
					NonFixedFor nonFixedFor = (NonFixedFor) workflowtasknode;
					if (nonFixedFor.getServiceConfigure().getMethodInfo() != null) {
						TreeItem serviceName = staticTree.addItem(nonFixedFor
								.getTitle());
						MethodInfo methodInfo = nonFixedFor
								.getServiceConfigure().getMethodInfo();
						if (methodInfo != null) {
							TreeItem output = serviceName.addItem(methodInfo
									.getMethodName()
									+ ".OutputInfo");
							addParamInfoInTree(output, methodInfo
									.getOutputInfo().getParamInfoArray());
						}
					}
				} else {
					// check whether preNode has been configured ,then determine
					// how to display the preNode information
					ServiceTask serviceTask = (ServiceTask)workflowtasknode;
					if (serviceTask.getServiceConfigure().getMethodInfo() != null) {
						TreeItem serviceName = staticTree.addItem(serviceTask
								.getTitle());
						MethodInfo methodInfo = serviceTask
								.getServiceConfigure().getMethodInfo();
						if (methodInfo != null) {
							TreeItem output = serviceName.addItem(methodInfo
									.getMethodName()
									+ ".OutputInfo");
							addParamInfoInTree(output, methodInfo
									.getOutputInfo().getParamInfoArray());
						}
					}
				}
			}

		}

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
				// æœ‰é—®é¢˜
				if (textBox.getValue() != null
						&& !textBox.getValue().equals("")) {
					setHasFinishedConfigure(true);
					dialogBox.hide();
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
				dialogBox.hide();
			}
		});
		absolutePanel.add(cancelButton, 233, 338);
		cancelButton.setSize("116px", "21px");
		cancelButton.setText("Cancel");

		dialogBox.center();
	}

	public void addParamInfoInTree(TreeItem treeItem, ParamInfo paramInfo) {
		if (paramInfo.isPrimitive()) {
			treeItem.addItem(paramInfo.getParamName() + "	" + "("
					+ paramInfo.getParamTypeName() + ")");
			name_for_paraminfo.put(paramInfo.getParamName(), paramInfo); // å°†paraminfoçš„å��å­—ä¸Žparaminfoçš„å¼•ç”¨ç»‘å®šèµ·æ�¥
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

	public void addoutput() {

		// åˆ¤æ–­æ˜¯å�¦ä¸ºè¾“å‡ºæ�¡ä»¶ä¸ºfalseçš„è¾“å‡ºç«¯å�£ï¼Œæœ‰çš„è¯�åˆ™ä¸�èƒ½å†�æ–°å»ºå‡ºå�£
		boolean has_false_label = false;
		for (Iterator<Label> iterator = label_array.iterator(); iterator
				.hasNext();) {
			Label label = (Label) iterator.next();
			if (label.getText().equals("false")) {
				has_false_label = true;
			}
		}
		if (!has_false_label) {
			outport_count++;
			// int addheight = 20;
			int panel1_original_height = panel1.getOffsetHeight();
			int instance_original_height = instance.getOffsetHeight();
			int looptask_original_height = getOffsetHeight();
			panel1.setHeight(Integer.toString(panel1_original_height + 20));
			instance.setHeight(Integer.toString(instance_original_height + 20));
			setHeight(Integer.toString(looptask_original_height + 20));

			Image newout = new Image("img/out.png");
			MyPanel panel5 = new MyPanel(dbe);
			panel5.setSize("15", "15");
			panel5.setStyleName("inner-panel");
			panel5.setTitle("out");
			panel5.add(newout);
			CustomUIObjectConnector.wrap(panel5);

			final Label newlabel = new Label("false") {

				public void onBrowserEvent(Event event) {
					if (DOM.eventGetType(event) == Event.ONMOUSEDOWN) {
						if (DOM.eventGetButton(event) == Event.BUTTON_LEFT) {
							final int x = DOM.eventGetClientX(event);
							final int y = DOM.eventGetClientY(event);
							if (contentPath != null && !contentPath.equals("")) {
								generate_popuppanel(this, x, y);
							}
							DOM.eventCancelBubble(event, true);// é˜»æ­¢äº‹ä»¶ä¼ åˆ°çˆ¶å¯¹è±¡ï¼Œå�¦åˆ™ä¼šå‡ºçŽ°ç‚¹å‡»å��ä¸ºfalseçš„labelæ—¶ï¼ŒèŠ‚ç‚¹ç¦»å¼€èƒŒæ™¯é�¢æ�¿ï¼Œç§»åŠ¨èµ·æ�¥
						}
					}
					super.onBrowserEvent(event);
				}
			};
			newlabel.sinkEvents(Event.ONMOUSEDOWN);
			newlabel.setTitle("newlabel");
			newlabel.setSize("44", "10");
			newlabel.addStyleName("choice-label");
			panel1.add(newlabel, 24, 10 + (outport_count - 1) * 20);

			// æ–°å»ºè¾“å‡ºç«¯å�£çš„æ—¶å€™ï¼Œå»ºç«‹ä¸€ä¸ªå¯¹åº”çš„ifconditionç±»ï¼Œä¿�å­˜ç›¸åº”çš„æ�¡ä»¶ä¿¡æ�¯
			Client_IfCondition client_IfCondition = new Client_IfCondition(
					newlabel.getText());
			IfConditionlist.add(outport_count - 1, client_IfCondition);

			newlabel.setVisible(true);
			outport.put(newlabel, panel5);
			label_array.add(outport_count - 1, newlabel);
			connector_list.add(panel5);
			outport_list.add(panel5); // å°†æ–°å¢žåŠ çš„å‡ºå�£åŠ å…¥åˆ°å‡ºå�£åˆ—è¡¨ä¸­

			instance.add(panel4, 85, 65 + (outport_count - 1) * 20);
			instance.add(panel5, 85, 10 + (outport_count - 1) * 20);

			// å¦‚æžœå‡ºå�£è¢«åˆ é™¤ä¸º0ï¼Œå�ˆé‡�æ–°æ·»åŠ æ–°å‡ºå�£æ—¶ï¼Œåˆ¤æ–­æ˜¯å�¦ä¸ºç¬¬ä¸€ä¸ªæ·»åŠ çš„ï¼Œç¬¬ä¸€ä¸ªæ·»åŠ çš„è®¾ç½®ä¸ºoutputport
			if (outport_count == 1) {
				setPanel3(panel5);
			}

			// update connections
			for (Iterator<MyPanel> iterator = getconnectorList().iterator(); iterator
					.hasNext();) {
				CustomUIObjectConnector c = CustomUIObjectConnector
						.getWrapper((MyPanel) iterator.next());
				if (c != null) {
					c.update();
				}
			}

		}
	}

	public void configure_ifconfition(Label label) {

	}

	public String generate_popuppanel(final Label label, int x, int y) {
		final PopupPanel popupPanel = new PopupPanel(true) {
			public void onBrowserEvent(Event event) {
				if (DOM.eventGetType(event) == Event.ONCLICK) {
					// this.hide();
				}
				super.onBrowserEvent(event);
			}
		};
		popupPanel.addCloseHandler(new CloseHandler<PopupPanel>() {

			public void onClose(CloseEvent<PopupPanel> event) {
				// TODO Auto-generated method stub
				AbsolutePanel absolutePanel = (AbsolutePanel) popupPanel
						.getWidget();
				String condition = label.getText();
				String new_condition;
				// ç›®å‰�å�‡è®¾è¿˜æ²¡å�šç±»åž‹é™�åˆ¶ï¼Œè€Œè¾“å…¥ç±»åž‹å�ªæœ‰intï¼Œstringä¸¤ç§�
				if (paramInfo_for_configure.getParamTypeName()
						.equals("Integer")) {
					new_condition = get_condition_value(absolutePanel,
							condition);
				} else {
					new_condition = get_another_condition_value(absolutePanel,
							condition);
				}

				setTextLabel(label, new_condition); // ç»™ä»£è¡¨è¾“å‡ºæ�¡ä»¶çš„labelèµ‹å€¼
				if (!new_condition.equals("DEL")) {
					int index = label_array.indexOf(label);
					Client_IfCondition client_IfCondition = IfConditionlist
							.get(index);
					client_IfCondition.setCondition_value(new_condition);
				}
			}

		});
		if (paramInfo_for_configure.getParamTypeName().equals("Integer")) {
			popupPanel.setWidget(generate_panel_for_popup());
		} else {
			popupPanel.setWidget(generate_another_panel_for_popup());
		}

		AbsolutePanel back_panel = (AbsolutePanel) getParent();
		determine_popup_position(popupPanel, back_panel, x, y); // æ ¹æ�®é¼ æ ‡ç‚¹å‡»çš„ä½�ç½®ï¼Œå†³å®šå¼¹å‡ºè�œå�•çš„æ˜¾ç¤ºä½�ç½®
		popupPanel.show();

		return label.getText();
	}

	public void determine_popup_position(PopupPanel popupPanel,
			Widget backgroud_region, int x, int y) {

		int back_panel_center_x = backgroud_region.getAbsoluteLeft()
				+ backgroud_region.getOffsetWidth() / 2;
		int back_panel_center_y = backgroud_region.getAbsoluteTop()
				+ backgroud_region.getOffsetHeight() / 2;
		if (x < back_panel_center_x && y < back_panel_center_y) {
			popupPanel.setPopupPosition(x, y);
		} else if (x < back_panel_center_x && y > back_panel_center_y) {
			popupPanel.setPopupPosition(x, y - popup_height);
		} else if (x > back_panel_center_x && y < back_panel_center_y) {
			popupPanel.setPopupPosition(x - popup_width, y);
		} else {
			popupPanel.setPopupPosition(x - popup_width, y - popup_height);
		}
	}

	public AbsolutePanel generate_panel_for_popup() {

		final AbsolutePanel absolutePanel = new AbsolutePanel();
		absolutePanel.setSize("261px", "65px");
		absolutePanel.addStyleName("absolutepanel-condition");

		// ä¿�ç•™ä½�absolutepanelçš„ä¿¡æ�¯ï¼Œä»¥åœ¨å°†è¦�æ˜¾ç¤ºpopuppanelçš„æ—¶å€™å®šä½�
		popup_width = 261;
		popup_height = 65;

		final Label xLabel = new Label("x");
		absolutePanel.add(xLabel, 30, 23);
		xLabel.setWidth("24px");

		final ListBox listBox = new ListBox();
		absolutePanel.add(listBox, 59, 23);
		listBox.setSize("45px", "18px");
		listBox.addItem(" ");
		listBox.addItem("<");
		listBox.addItem("=");
		listBox.addItem(">");
		listBox.addItem("<=");
		listBox.addItem(">=");
		listBox.setSelectedIndex(1);

		final TextBox textBox = new TextBox();
		absolutePanel.add(textBox, 135, 23);
		textBox.setWidth("100px");

		final Label label = new Label("x");
		absolutePanel.add(label, 30, 57);
		label.setSize("24px", "18px");

		final ListBox listBox_1 = new ListBox();
		absolutePanel.add(listBox_1, 59, 57);
		listBox_1.addItem("<");
		listBox_1.addItem("<=");
		listBox_1.setSize("45px", "18px");

		final TextBox textBox_1 = new TextBox();
		absolutePanel.add(textBox_1, 135, 57);
		textBox_1.setSize("100px", "21px");

		label.setVisible(false);
		listBox_1.setVisible(false);
		textBox_1.setVisible(false);

		final Image down_image = new Image("img/downlist.PNG");
		final Image up_image = new Image("img/up.PNG");

		down_image.setSize("18", "18");
		absolutePanel.add(down_image, 5, 23);
		down_image.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				absolutePanel.setSize("261px", "100px");

				while (listBox.getItemCount() > 0) {
					listBox.removeItem(0);
				}
				listBox.addItem(">");
				listBox.addItem(">=");

				label.setVisible(true);
				listBox_1.setVisible(true);
				textBox_1.setVisible(true);

				down_image.setVisible(false);
				up_image.setVisible(true);
			}

		});

		up_image.setSize("18", "18");
		absolutePanel.add(up_image, 5, 57);
		up_image.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				absolutePanel.setSize("261px", "65px");

				while (listBox.getItemCount() > 0) {
					listBox.removeItem(0);
				}
				listBox.addItem(" ");
				listBox.addItem("<");
				listBox.addItem("=");
				listBox.addItem(">");
				listBox.addItem("<=");
				listBox.addItem(">=");
				listBox.setSelectedIndex(1);

				label.setVisible(false);
				listBox_1.setVisible(false);
				textBox_1.setVisible(false);

				up_image.setVisible(false);
				down_image.setVisible(true);
			}

		});
		up_image.setVisible(false);

		return absolutePanel;
	}

	public AbsolutePanel generate_another_panel_for_popup() {

		final AbsolutePanel absolutePanel = new AbsolutePanel();
		absolutePanel.setSize("180px", "43px");
		absolutePanel.addStyleName("absolutepanel-condition");

		// ä¿�ç•™ä½�absolutepanelçš„ä¿¡æ�¯ï¼Œä»¥åœ¨å°†è¦�æ˜¾ç¤ºpopuppanelçš„æ—¶å€™å®šä½�
		popup_width = 180;
		popup_height = 43;

		final Label xLabel = new Label("x=");
		absolutePanel.add(xLabel, 25, 13);
		xLabel.setSize("24px", "18px");

		final ListBox listBox = new ListBox();
		absolutePanel.add(listBox, 55, 13);
		listBox.setSize("102px", "18px");
		listBox.addItem(" ");
		System.out.println("paramInfo_for_configure"
				+ paramInfo_for_configure.getParamName());
		if (paramInfo_for_configure.getParamTypeName().equals("String")) {
			// listBox.addItem("Sunny");
			// listBox.addItem("Cloudy");
			// listBox.addItem("Rainy");
			if (DataLib
					.whetherInDataLib(paramInfo_for_configure.getParamName())) {
				String[] result = paramInfo_for_configure.getParamEnums()
						.split(",");
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
		listBox.setSelectedIndex(1);

		return absolutePanel;
	}

	public String get_condition_value(AbsolutePanel absolutePanel,
			String condition) {
		String expression = condition;

		Image image = (Image) absolutePanel.getWidget(6);
		if (image.isVisible()) {
			Label label = (Label) absolutePanel.getWidget(0);
			ListBox listBox = (ListBox) absolutePanel.getWidget(1);
			int index = listBox.getSelectedIndex();
			String listBoxString = listBox.getValue(index);
			if (listBoxString.equals(" ")) {
				expression = "DEL";
				return expression;
			}
			TextBox textBox = (TextBox) absolutePanel.getWidget(2);
			String textString = textBox.getText();
			if (isInt(textString)) {
				expression = label.getText() + listBoxString + textString;
			}
		} else {

			Label label = (Label) absolutePanel.getWidget(0);
			ListBox listBox = (ListBox) absolutePanel.getWidget(1);
			int index = listBox.getSelectedIndex();
			String listBoxString = listBox.getValue(index);
			TextBox textBox = (TextBox) absolutePanel.getWidget(2);
			String textString = textBox.getText();
			if (listBoxString.equals(">")) {
				listBoxString = "<";
			}
			if (listBoxString.equals(">=")) {
				listBoxString = "<=";
			}

			ListBox listBox1 = (ListBox) absolutePanel.getWidget(4);
			int index1 = listBox.getSelectedIndex();
			String listBoxString1 = listBox1.getValue(index1);
			TextBox textBox1 = (TextBox) absolutePanel.getWidget(5);
			String textString1 = textBox1.getText();

			if (isInt(textString1) && isInt(textString)) {
				expression = textString + listBoxString + label.getText()
						+ listBoxString1 + textBox1.getText();
			}

		}
		return expression;
	}

	public String get_another_condition_value(AbsolutePanel absolutePanel,
			String condition) {
		String expression = condition;

		Label label = (Label) absolutePanel.getWidget(0);
		ListBox listBox = (ListBox) absolutePanel.getWidget(1);
		int index = listBox.getSelectedIndex();
		String listBoxString = listBox.getValue(index);
		if (listBoxString.equals(" ")) {
			expression = "DEL";
			return expression;
		}
		expression = label.getText() + listBoxString;
		return expression;

	}

	public boolean check_ParamInfoWeatherComefrom(Workflowtasknode workflowtasknode,
			String contentPath) {
		String id = workflowtasknode.getID();
		String valueString = contentPath;
		// å°†ä¿�å­˜çš„è·¯å¾„ä¿¡æ�¯ä¸­çš„æœ�åŠ¡å��æ›¿æ�¢ä¸ºidå�·
		String[] divided = valueString.split("\\.");
		String serviceID = divided[0];
		if (serviceID.equals(id)) {
			return true;
		}
		return false;
	}

	public boolean isInt(String str) {
		try {
			int i = Integer.parseInt(str);
			System.out.println("ä½ è¾“å…¥çš„æ•´æ•°" + i);
			return true;
		} catch (NumberFormatException e) {
			System.out.println("ä½ è¾“å…¥çš„ä¸�æ˜¯æ•´æ•°,å�¯èƒ½æ˜¯æµ®ç‚¹æ•°");
			return false;
		}
	}

	public ChoiceTask() {

	}

	/**
	 * Constructor that uses default text values for buttons.
	 * 
	 * @param labelText
	 *            The initial text of the label.
	 * @param onUpdate
	 *            Handler object for performing actions once label is updated.
	 */
	public ChoiceTask(String labelText, String in, String out, String fault,
			DiagramBuilder d) {
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
	public ChoiceTask(String labelText, DiagramBuilder d) {
		dbe = d;
		createEditableLabel(labelText, "I", "O", "F");
	}

	public ChoiceTask(String labelText) {

		createEditableLabel(labelText, "I", "O", "F");
	}

	// get preTaskNodeList,if current node is the begin node of one loop,then
	// preTaskNodelist contains the preTaskNodes of loop
	public void Get_preTask_all() {
		// 获取所有前向节点集合之前，必须先清空preTaskList,preTaskSet
		preTaskList.clear();
		preTaskSet.clear();
		Get_preTask_all(this);

	}

	// 递归调用此函数，以获取所有的前向节点
	@SuppressWarnings("unchecked")
	public void Get_preTask_all(Workflowtasknode workflowtasknode) {
		// 如果不是开始节点，则直接找到其前驱节点列表
		if (!isstart) {
			CustomUIObjectConnector cons = CustomUIObjectConnector
					.getWrapper(workflowtasknode.getPanel2());
			Collection collection = cons.getConnections();
			CustomUIObjectConnector front_end_Connector;
			for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
				CustomConnection c = (CustomConnection) iterator.next();
				front_end_Connector = (CustomUIObjectConnector) c
						.getConnected().get(0);
				MyPanel front_end_panelPanel = (MyPanel) front_end_Connector.wrapped;
				ServiceTask front_end_simpletask = (ServiceTask) front_end_panelPanel
						.getParent().getParent();
				if (!preTaskSet.contains(front_end_simpletask)) {
					preTaskList.add(front_end_simpletask);
					preTaskSet.add(front_end_simpletask);
					Get_preTask_all(front_end_simpletask);
				}

			}
			System.out.println("get preTasklist successful");
		}
		// 如果是开始节点，若此节点属于子工作流，则为包含此子工作流的looptask的前驱节点
		else if (belongToSubWorkflow) {
			SubDiagram subDiagram = (SubDiagram) dbe;
			subDiagram.loopTask.Get_preTask_all();
			preTaskList = subDiagram.loopTask.preTaskList;
		}
	}
}
