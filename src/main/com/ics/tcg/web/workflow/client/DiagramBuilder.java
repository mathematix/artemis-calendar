package com.ics.tcg.web.workflow.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.balon.gwt.diagrams.client.connection.Connection;
import pl.balon.gwt.diagrams.client.connector.Connector;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.DragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.DragHandlerAdapter;
import com.allen_sauer.gwt.dragdrop.client.PickupDragController;
import com.allen_sauer.gwt.dragdrop.client.VetoDragException;
import com.allen_sauer.gwt.dragdrop.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.DropController;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListenerCollection;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ics.tcg.web.user.client.panels.Panel_Overview;
import com.ics.tcg.web.workflow.client.command.AddTimerCommand;
import com.ics.tcg.web.workflow.client.command.SetFinishCommand;
import com.ics.tcg.web.workflow.client.command.SetStartCommand;
import com.ics.tcg.web.workflow.client.common.customline.CustomConnection;
import com.ics.tcg.web.workflow.client.common.customline.CustomUIObjectConnector;
import com.ics.tcg.web.workflow.client.common.customline.StraightLineConnection;
import com.ics.tcg.web.workflow.client.composite.complement.MyPanel;
import com.ics.tcg.web.workflow.client.data.Client_Choice;
import com.ics.tcg.web.workflow.client.data.Client_ConditionalOutputPort;
import com.ics.tcg.web.workflow.client.data.Client_Configuration;
import com.ics.tcg.web.workflow.client.data.Client_FaultOutputPort;
import com.ics.tcg.web.workflow.client.data.Client_FixedNumFor;
import com.ics.tcg.web.workflow.client.data.Client_IfCondition;
import com.ics.tcg.web.workflow.client.data.Client_InputPort;
import com.ics.tcg.web.workflow.client.data.Client_Loop;
import com.ics.tcg.web.workflow.client.data.Client_Node;
import com.ics.tcg.web.workflow.client.data.Client_NonFixedNumFor;
import com.ics.tcg.web.workflow.client.data.Client_OutputPort;
import com.ics.tcg.web.workflow.client.data.Client_Service;
import com.ics.tcg.web.workflow.client.data.Client_ServiceTask;
import com.ics.tcg.web.workflow.client.data.Client_Timer;
import com.ics.tcg.web.workflow.client.data.Client_WhileCondition;
import com.ics.tcg.web.workflow.client.data.Client_Workflow;
import com.ics.tcg.web.workflow.client.service.ServiceInfo;
import com.ics.tcg.web.workflow.client.task.ChoiceTask;
import com.ics.tcg.web.workflow.client.task.FixedFor;
import com.ics.tcg.web.workflow.client.task.LoopTask;
import com.ics.tcg.web.workflow.client.task.NonFixedFor;
import com.ics.tcg.web.workflow.client.task.ServiceTask;
import com.ics.tcg.web.workflow.client.task.TimerNode;
import com.ics.tcg.web.workflow.client.task.Workflowtasknode;

@SuppressWarnings( { "unchecked", "deprecation" })
public class DiagramBuilder extends AbstractConnections {

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	public ScrollPanel scrollPanel = new ScrollPanel();
	
	/* coverLayer is used of cover the edit region of workflow ,so the panel 
	 * can display the current static state of workflow
	 */
	public AbsolutePanel coverWorkflowEditRegionLayer;
	public AbsolutePanel coverWorkflowControlPanel;

	public final ServerStatus_ServiceAsync sendingService = GWT
			.create(ServerStatus_Service.class);
	public Client_Workflow load_client_Workflow;

	protected static final String SIMPLENODE = "Simple";
	protected static final String LOOPNODE = "Loop";
	protected static final String CHOICENODE = "Choice";
	protected static final String TIMERNODE = "Timer";
	protected static final String FIXEDFORNODE = "FLoop";
	protected static final String NONFIXEDNUMFOR = "TLoop";

	protected static final String LOOPNODETITLE = "loop";
	protected static final String FIXEDFORNODETITLE = "fixednumloop";
	protected static final String NONFIXEDNUMFORTITLE = "timeintervalloop";

	public boolean check = true;

	protected Client_Workflow workFlow;

	ArrayList<String> service_info_list = new ArrayList<String>();
	HashMap<String, Integer> hashMap = new HashMap<String, Integer>(); // 保证创建的service和控制组件编号从小到大;
	public HashMap<String, String> service_name_to_id; // 使得每个service节点的名字和id号对应起来

	protected KeyboardListenerCollection keyboradListeners;
	int complexTask_count = 0;
	HorizontalPanel hp;
	CellPanel toolbox;
	CellPanel helpPanel;
	VerticalPanel leftverticalPanel;
	VerticalPanel northwestPanel;
	// AbsolutePanel panel;
	StraightLineConnection stec;

	public AbsolutePanel getPanel() {
		return panel;
	}

	public void setPanel(AbsolutePanel panel) {
		this.panel = panel;
	}

	public DragController toolboxDragController;
	public DragController timerDragController;
	public DragController dragController1;
	String style;
	public List<AbsolutePositionDropController> dropcontroler_listList = new ArrayList<AbsolutePositionDropController>(); // 将toolboxDragController的dropcontroller保存起来
	public List<AbsolutePositionDropController> timer_dropcontrollerList = new ArrayList<AbsolutePositionDropController>();
	public AbsolutePanel leftAbsolutePanel; // 左侧面板
	public WorkflowEditModule gde;
	public Workflowtasknode lastNode; // 指向工作流中最后一个节点的引用

	public List<ServiceTask> list = new ArrayList<ServiceTask>(); // 没用，待处理

	Widget connector_view_proxy = new Label(); // 连线时候的代理，便于看出哪两个节点准备连线

	public ArrayList<TimerNode> timernodeList = new ArrayList<TimerNode>();

	public List<ServiceTask> getList() {
		return list;
	}

	public void setList(List<ServiceTask> list) {
		this.list = list;
	}

	public Panel_Overview overview;

	public DiagramBuilder(WorkflowEditModule gwtdiagramsexample,
			Panel_Overview panel_Overview) {
		super();
		gde = gwtdiagramsexample;
		overview = panel_Overview;
		this.sinkEvents(Event.ONKEYDOWN);
		createContents();
	}

	public DiagramBuilder() {
		super();
		this.sinkEvents(Event.ONKEYDOWN);
		createContents();
	}

	protected void createContents() {

		service_name_to_id = gde.service_name_to_id; // 指向GwtDiagramsExmaple中的service_name_to_id

		hp = new HorizontalPanel();

		leftAbsolutePanel = new AbsolutePanel();
		DOM.setStyleAttribute(leftAbsolutePanel.getElement(), "borderRight",
				"2px solid #DDDDDD");
		leftAbsolutePanel.setSize("180", "300");

		// Button saveButton = new Button("SAVE");
		// saveButton.addClickListener(new ClickListener() {
		//
		// public void onClick(Widget sender) {
		// save_workflow_in_workflowmode();
		// }
		//
		// });
		// leftAbsolutePanel.add(saveButton, 0, 0);
		// Button loadButton = new Button("LOAD");
		// loadButton.addClickListener(new ClickListener(){
		//
		// public void onClick(Widget sender) {
		// load_workflow_from_server();
		// }
		//			
		// });
		// leftAbsolutePanel.add(loadButton, 0, 20);

		leftverticalPanel = new VerticalPanel();
		Label service_tag = new Label("Services");
		service_tag.addStyleName("service-tag");
		leftverticalPanel.add(service_tag);

		leftAbsolutePanel.add(leftverticalPanel, 0, 100);
		hp.add(leftAbsolutePanel);

		toolbox = new HorizontalPanel();
		toolbox.addStyleName("toolbox");
		panel = new AbsolutePanel();
		// 必须加此句，因为panel的属性是hiden，调用DOMUtil.getClientWidth时，没大小的话，返回0，这样当鼠标up时，
		// 定位到原位置，即没移动
		panel.setSize("400", "300");
		helpPanel = new HorizontalPanel();
		helpPanel.addStyleName("helpPanel");

		scrollPanel = new ScrollPanel();
		scrollPanel.setSize("400", "300");
		// vp.add(helpPanel);
		// vp.add(toolbox);
		scrollPanel.add(panel);
		hp.add(scrollPanel);
		getArea().add(hp);

		dragController1 = createDragController(panel);

		AbsolutePositionDropController dropController = new AbsolutePositionDropController(
				panel);

		toolboxDragController = new ToolboxDragController(dropController);
		timerDragController = new ToolboxDragController(dropController);
		// timerDragController.unregisterDropController(dropController);
		// //试图取消掉对最大面板的注册，这样时钟时间只能在simpletask上绑定

		/* 创建用于保存和载入的按钮 */
		// create_button();
		/* 创建代表loop和choice的类型 */
		create_type();
		/* 创建代表服务的节点 */
		create_service();

		/* 创建可用于拖曳的节点样本 */
		// createToolboxNode(SIMPLENODE);
		// createToolboxNode(LOOPNODE);
		// createToolboxNode(CHOICENODE);
		// createTimerNode(TIMERNODE);
		/* 创建不同的连线方式 */
		// createLineStyle("control");
		// createLineStyle("Data");
		/* 测试使用，假设初始化时已经有一个compoundtask */
		// CompoundTask testCompoundTask = new
		// CompoundTask("Compound Task",this);
		// panel.add(testCompoundTask);
		// dragController1.makeDraggable(testCompoundTask);
		// // AbsolutePositionDropController innerdropController = new
		// // AbsolutePositionDropController(testCompoundTask.getPanel1());
		// // toolboxDragController.registerDropController(innerdropController);
		// // dragController.registerDropController(innerdropController); //
		// MyPanel in1 = testCompoundTask.getPanel2();
		// MyPanel out1 = testCompoundTask.getPanel3(); //
		// // 得到显示的task中的out所代表的label的指针
		// MyPanel fault1 = testCompoundTask.getPanel4();
		// testCompoundTask.sinkEvents(Event.ONCLICK);
		// UIObjectConnector.wrap(in1);
		// UIObjectConnector.wrap(out1);
		// UIObjectConnector.wrap(fault1);
		setStyle("straight"); // 设置默认的连线方式为直线
		//
		// createHelpEntry("drag&drop from toolbar to add");
		// createHelpEntry("CTRL+click to select/connect");
		// createHelpEntry("DEL to delete selected");
	}

	public void create_type() {

		northwestPanel = new VerticalPanel();
		northwestPanel.setSize("180", "100");

		Label tag = new Label("Control");
		tag.addStyleName("service-tag");

		Label label = new Label(LOOPNODE) {
			public void onBrowserEvent(Event event) {
				if (DOM.eventGetType(event) == Event.ONMOUSEOVER) {
					this.addStyleName("label-highlight");
				}
				if (DOM.eventGetType(event) == Event.ONMOUSEOUT) {
					this.removeStyleName("label-highlight");
				}
				if (DOM.eventGetType(event) == Event.ONLOSECAPTURE) {
					this.removeStyleName("label-highlight");
				}
				super.onBrowserEvent(event);
			}
		};
		label.sinkEvents(Event.ONMOUSEOVER | Event.ONMOUSEOUT
				| Event.ONLOSECAPTURE);
		label.addStyleName("service-label");
		toolboxDragController.makeDraggable(label);
		hashMap.put(LOOPNODETITLE, 0);

		Label label2 = new Label(CHOICENODE) {
			public void onBrowserEvent(Event event) {
				if (DOM.eventGetType(event) == Event.ONMOUSEOVER) {
					this.addStyleName("label-highlight");
				}
				if (DOM.eventGetType(event) == Event.ONMOUSEOUT) {
					this.removeStyleName("label-highlight");
				}
				if (DOM.eventGetType(event) == Event.ONLOSECAPTURE) {
					this.removeStyleName("label-highlight");
				}
				super.onBrowserEvent(event);
			}
		};
		label2.sinkEvents(Event.ONMOUSEOVER | Event.ONMOUSEOUT
				| Event.ONLOSECAPTURE);
		label2.addStyleName("service-label");
		toolboxDragController.makeDraggable(label2);
		hashMap.put(CHOICENODE, 0);

		Label label3 = new Label(FIXEDFORNODE) {
			public void onBrowserEvent(Event event) {
				if (DOM.eventGetType(event) == Event.ONMOUSEOVER) {
					this.addStyleName("label-highlight");
				}
				if (DOM.eventGetType(event) == Event.ONMOUSEOUT) {
					this.removeStyleName("label-highlight");
				}
				if (DOM.eventGetType(event) == Event.ONLOSECAPTURE) {
					this.removeStyleName("label-highlight");
				}
				super.onBrowserEvent(event);
			}
		};
		label3.sinkEvents(Event.ONMOUSEOVER | Event.ONMOUSEOUT
				| Event.ONLOSECAPTURE);
		label3.addStyleName("service-label");
		toolboxDragController.makeDraggable(label3);
		hashMap.put(FIXEDFORNODETITLE, 0);

		Label label4 = new Label(NONFIXEDNUMFOR) {
			public void onBrowserEvent(Event event) {
				if (DOM.eventGetType(event) == Event.ONMOUSEOVER) {
					this.addStyleName("label-highlight");
				}
				if (DOM.eventGetType(event) == Event.ONMOUSEOUT) {
					this.removeStyleName("label-highlight");
				}
				if (DOM.eventGetType(event) == Event.ONLOSECAPTURE) {
					this.removeStyleName("label-highlight");
				}
				super.onBrowserEvent(event);
			}
		};
		label4.sinkEvents(Event.ONMOUSEOVER | Event.ONMOUSEOUT
				| Event.ONLOSECAPTURE);
		label4.addStyleName("service-label");
		toolboxDragController.makeDraggable(label4);
		hashMap.put(NONFIXEDNUMFORTITLE, 0);

		northwestPanel.add(tag);
		northwestPanel.add(label);
		// northwestPanel.add(label3);
		northwestPanel.add(label4);
		northwestPanel.add(label2);

		leftAbsolutePanel.add(northwestPanel, 0, 0);

	}

	/** new */
	public void create_service() {
		final ArrayList<String> serviceNameList = new ArrayList<String>();
		for (int i = 0; i < overview.user_Service_Clients.size(); i++) {
			serviceNameList
					.add(overview.user_Service_Clients.get(i).abservicename);
		}
		service_info_list = serviceNameList;
		// 根据服务的个数，动态设置服务显示列表的大小
		int service_count = service_info_list.size();
		leftverticalPanel.setSize("180", Integer.toString(30 * service_count));

		for (Iterator iterator = service_info_list.iterator(); iterator
				.hasNext();) {
			String serviceName = (String) iterator.next();
			Label label = new Label(serviceName) {
				public void onBrowserEvent(Event event) {
					if (DOM.eventGetType(event) == Event.ONMOUSEOVER) {
						this.addStyleName("label-highlight");
					}
					if (DOM.eventGetType(event) == Event.ONMOUSEOUT) {
						this.removeStyleName("label-highlight");
					}
					if (DOM.eventGetType(event) == Event.ONLOSECAPTURE) {
						this.removeStyleName("label-highlight");
					}
					super.onBrowserEvent(event);
				}
			};
			label.sinkEvents(Event.ONMOUSEOVER | Event.ONMOUSEOUT
					| Event.ONLOSECAPTURE);
			label.addStyleName(serviceName+"-Label");
			toolboxDragController.makeDraggable(label);

			leftverticalPanel.add(label);

			hashMap.put(serviceName, 0);
		}
	}

	public void update_service() {
		leftverticalPanel.clear();
		Label service_tag = new Label("Services");
		service_tag.addStyleName("service-tag");
		leftverticalPanel.add(service_tag);
		create_service();
	}

	public void createHelpEntry(String help) {
		Label l = new Label(help);
		l.addStyleName("helpEntry");
		helpPanel.add(l);
	}

	public Widget createToolboxNode(String label) {
		Widget node = new Label(label);
		node.setSize("50", "50");
		node.setStyleName("toolbox-node");
		toolbox.add(node);
		toolboxDragController.makeDraggable(node);
		return node;
	}

	public Widget createLineStyle(String lineName) {
		Widget node = new Label(lineName);
		node.setSize("50", "50");
		node.setStyleName("toolbox-node");
		toolbox.add(node);
		return node;
	}

	public Widget createTimerNode(String label) {
		Widget node = new Label(label);
		node.setSize("50", "50");
		node.addStyleName("toolbox-node");
		toolbox.add(node);
		timerDragController.makeDraggable(node);
		return node;
	}

	public List<MyPanel> selected = new ArrayList<MyPanel>();

	public void select(Widget ww) { // 修改过，原来为protected
		MyPanel w = (MyPanel) ww;
		if (selected.isEmpty()) {
			// 制造鬼影效果
			Label display_label = new Label();
			display_label.setSize("15", "15");
			display_label.addStyleName("port_shadow");
			w.add(display_label, 0, 0);
			connector_view_proxy = display_label;

			selected.add(w);
		} else if (selected.contains(w)) {
			connector_view_proxy.removeFromParent();
			selected.remove(w);
		} else if (selected.size() == 1) {
			MyPanel w2 = (MyPanel) selected.get(0);

			Widget w_simpletask, w_absolutepanel, w2_simpletask, w2_absolutepanel;
			w_simpletask = w.getParent().getParent(); // 此处需改，
			// looptask中用的是absolutepanel
			w2_simpletask = w2.getParent().getParent();
			w_absolutepanel = w_simpletask.getParent();
			w2_absolutepanel = w2_simpletask.getParent();

			/* 保证在同一个面板上的控件才能相连，并且出口只能连入口，每条出口最多只能连一天线 */
			if (w_simpletask == w2_simpletask
					|| w_absolutepanel != w2_absolutepanel) {

			} else if ((w.getTitle().equals("in") && !w2.getTitle()
					.equals("in"))
					|| (!w.getTitle().equals("in") && w2.getTitle()
							.equals("in"))) {
				connector_view_proxy.removeFromParent();

				CustomUIObjectConnector ui1 = CustomUIObjectConnector
						.getWrapper(w2);
				CustomUIObjectConnector ui2 = CustomUIObjectConnector
						.getWrapper(w);
				// if (ui1.getConnections().size() == 0
				// && ui2.getConnections().size() == 0) {
				// 保证每个连接端点只能连一条线
				// 如果w是in的话，则其为终点，如果不是in则为起点
				if (w.getTitle().equals("in"))
					connect(ui1, ui2, w_absolutepanel); // 在connect中添加线条背景面板,
				// 保证线条画在其中
				else {
					connect(ui2, ui1, w_absolutepanel);
				}
				selected.clear();
				// }
			}
		}
	}

	private List selected_taskList = new ArrayList();

	public void select_task(Widget w) {
		if (selected_taskList.isEmpty()) {
			// w.addStyleName("selected-connector");;
			w.addStyleName("selected-connector");

			selected_taskList.add(w);
		} else if (selected_taskList.contains(w)) {
			w.removeStyleName("selected-connector");
			selected_taskList.remove(w);
		}
	}

	protected void createConnector(Widget widget, AbsolutePanel panel) {
		Workflowtasknode proxy = (Workflowtasknode) widget;

		final Workflowtasknode l;
		@SuppressWarnings("unused")
		TimerNode timer;

		if (proxy.getText().equals(SIMPLENODE)) {
			l = new ServiceTask(proxy.getText(), this); // 将diagrambuilder的引用传给simpletask
			System.out.println(proxy.getText() + "task name");
		} else if (proxy.getText().equals(LOOPNODE)) {
			int num = hashMap.get(LOOPNODETITLE);
			l = new LoopTask(proxy.getText(), this, overview); // 将diagrambuilder的引用传给simpletask
			set_node_title(l, LOOPNODETITLE + (num + 1));
			hashMap.put(LOOPNODETITLE, num + 1);
		} else if (proxy.getText().equals(CHOICENODE)) {
			int num = hashMap.get(CHOICENODE);
			l = new ChoiceTask(proxy.getText(), this);
			set_node_title(l, CHOICENODE + (num + 1));
			hashMap.put(CHOICENODE, num + 1);
		} else if (proxy.getText().equals(FIXEDFORNODE)) {
			int num = hashMap.get(FIXEDFORNODETITLE);
			l = new FixedFor(proxy.getText(), this, overview);
			set_node_title(l, FIXEDFORNODETITLE + (num + 1));
			hashMap.put(FIXEDFORNODETITLE, num + 1);
		} else if (proxy.getText().equals(NONFIXEDNUMFOR)) {
			int num = hashMap.get(NONFIXEDNUMFORTITLE);
			l = new NonFixedFor(proxy.getText(), this, overview);
			set_node_title(l, NONFIXEDNUMFORTITLE + (num + 1));
			hashMap.put(NONFIXEDNUMFORTITLE, num + 1);
		} else {
			// 按服务编号从小到大，依次产生webservice节点
			String service_name = proxy.getText();
			int num = hashMap.get(service_name);
			l = new ServiceTask(service_name, this);
			
			//allocate different color to different service
			AbsolutePanel innerPanel = l.getPanel1();
			if(proxy.getText().equals("BookAirTicketService")){
//				DOM.setStyleAttribute(innerPanel.getElement(), "backgroundColor", "#3161CE");
//				DOM.setStyleAttribute(innerPanel.getElement(), "border", "1px solid black");
				DOM.setStyleAttribute(innerPanel.getElement(), "border", "2px solid #3161CE");
//				innerPanel.addStyleName("ServiceNode-BookAirTicketService");
			}
			else if(proxy.getText().equals("BookBusTicketService")){
//				DOM.setStyleAttribute(innerPanel.getElement(), "backgroundColor", "#109618");
//				DOM.setStyleAttribute(innerPanel.getElement(), "border", "1px solid black");
				DOM.setStyleAttribute(innerPanel.getElement(), "border", "2px solid #109618");
//				innerPanel.addStyleName("ServiceNode-BookBusTicketService");
			}
			else if(proxy.getText().equals("BookTrainTicketService")){
//				DOM.setStyleAttribute(innerPanel.getElement(), "backgroundColor", "#63AE00");
//				DOM.setStyleAttribute(innerPanel.getElement(), "border", "1px solid black");
				DOM.setStyleAttribute(innerPanel.getElement(), "border", "2px solid #63AE00");
//				innerPanel.addStyleName("ServiceNode-BookTrainTicketService");
			}
			else if(proxy.getText().equals("BookRoomService")){
//				DOM.setStyleAttribute(innerPanel.getElement(), "backgroundColor", "#9C419C");
//				DOM.setStyleAttribute(innerPanel.getElement(), "border", "1px solid black");
				DOM.setStyleAttribute(innerPanel.getElement(), "border", "2px solid #9C419C");
//				innerPanel.addStyleName("ServiceNode-BookRoomService");
			}
			else if(proxy.getText().equals("EmailService")){
//				DOM.setStyleAttribute(innerPanel.getElement(), "backgroundColor", "#D6AE00");
//				DOM.setStyleAttribute(innerPanel.getElement(), "border", "1px solid black");
				DOM.setStyleAttribute(innerPanel.getElement(), "border", "2px solid #D6AE00");
//				innerPanel.addStyleName("ServiceNode-EmailService");
			}
			else if(proxy.getText().equals("SMS")){
//				DOM.setStyleAttribute(innerPanel.getElement(), "backgroundColor", "#8C8E52");
//				DOM.setStyleAttribute(innerPanel.getElement(), "border", "1px solid black");
				DOM.setStyleAttribute(innerPanel.getElement(), "border", "2px solid #8C8E52");
//				innerPanel.addStyleName("ServiceNode-SMS");
			}
			else{
//				DOM.setStyleAttribute(innerPanel.getElement(), "backgroundColor", "#31619C");
//				DOM.setStyleAttribute(innerPanel.getElement(), "border", "1px solid black");
				DOM.setStyleAttribute(innerPanel.getElement(), "border", "2px solid #31619C");
//				innerPanel.addStyleName("ServiceNode-WeatherForecastServcie");
			}
			
			set_node_title(l, service_name + (num + 1));
			hashMap.put(service_name, num + 1);

			sendingService.getServiceInfo(service_name, new AsyncCallback() {

				public void onFailure(Throwable caught) {
					// Show the RPC error message to the user
					Window.alert("fail");
				}

				public void onSuccess(Object result) {

					ServiceInfo serviceInfo = (ServiceInfo) result;
					((ServiceTask)l).setServiceInfo(serviceInfo);
					System.out.println("The result of rpc is "
							+ serviceInfo.getServiceName());
				}
			});
		}

		service_name_to_id.put(l.getTitle(), Integer.toString(l.hashCode())); // 每新建一个节点，就把名字和id号关联起来
		l.setID(Integer.toString(l.hashCode()));

		MyPanel in = l.getPanel2();
		MyPanel out = l.getPanel3(); // 得到显示的task中的out所代表的label的指针
		MyPanel fault = l.getPanel4();

		l.sinkEvents(Event.ONCLICK);
		l.setPixelSize(proxy.getOffsetWidth(), proxy.getOffsetHeight());

		// 将坐标信息保存到节点内部
		l.setPixel_x(proxy.getAbsoluteLeft() - panel.getAbsoluteLeft());
		l.setPixel_y(proxy.getAbsoluteTop() - panel.getAbsoluteTop());

		panel.add(l, proxy.getAbsoluteLeft() - panel.getAbsoluteLeft(), proxy
				.getAbsoluteTop()
				- panel.getAbsoluteTop());
		dragController1.makeDraggable(l);

		CustomUIObjectConnector.wrap(in);
		CustomUIObjectConnector.wrap(out);
		CustomUIObjectConnector.wrap(fault);

	}

	// 为重构服务，这样子工作流的节点的名字比较好设置
	protected void set_node_title(Workflowtasknode w, String text) {
		w.setTitle(text);
	}

	protected void connect(Connector a, Connector b, Widget panel) {

		stec = new StraightLineConnection(a, b);
		add(stec, (AbsolutePanel) panel);
	}

	public void setStyle(String style) {
		this.style = style;
	}

	protected void add(CustomConnection c, AbsolutePanel panel) {
		super.add(c, panel);
	}

	// 在hashmap中寻找id号对应的servie
	public String SearchID(String id) {
		Set<String> keySet = service_name_to_id.keySet();
		for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
			String serviceName = iterator.next();
			if (service_name_to_id.get(serviceName).equals(id)) {
				return serviceName;
			}
		}
		return null;
	}

	private class ToolboxDragController extends PickupDragController {

		Map nodes = new HashMap();

		public ToolboxDragController(final DropController dropController) {
			super((AbsolutePanel) dropController.getDropTarget(), false); // 源程序为false
			setDragProxyEnabled(true);
			registerDropController(dropController); // 重复注册了droptarget,
			// 在abstractconnectionexample中已经注册了一个

			addDragHandler(new DragHandlerAdapter() {

				public void onPreviewDragEnd(DragEndEvent event)
						throws VetoDragException {
					Widget node = (Widget) event.getSource();

					AbsolutePanel panel = (AbsolutePanel) event.getDropTarget();
					Widget proxy = (Widget) nodes.get(node);

					createConnector(proxy, panel);

					throw new VetoDragException();
				}

			});

		}

		protected Widget maybeNewDraggableProxy(Widget draggable) {
			Label node = (Label) draggable;
			String node_name = node.getText();
			Widget proxy;
			System.out.println(node_name);
			if (node_name.equals(SIMPLENODE)) {
				proxy = new ServiceTask(node.getText());
			} else if (node_name.equals(LOOPNODE)) {
				proxy = new LoopTask(node.getText(), overview);
			} else if (node_name.equals(CHOICENODE)) {
				proxy = new ChoiceTask(node.getText());
			} else if (node_name.equals(FIXEDFORNODE)) {
				proxy = new FixedFor(node.getText(), overview);
			} else if (node_name.equals(NONFIXEDNUMFOR)) {
				proxy = new NonFixedFor(node.getText(), overview);
			}

			// else if (node_name.equals(TIMERNODE)) {
			// proxy = new TimerNode(node.getText());
			// }
			else {
				proxy = new ServiceTask(node.getText());
			}

			proxy.addStyleName(CSS_PROXY);
			proxy.addStyleName("toolbox-node-proxy");
			// proxy.setPixelSize(node.getOffsetWidth(),
			// node.getOffsetHeight());

			nodes.put(node, proxy);

			return proxy;
		}

	}

	public AbsolutePanel getMyArea() {
		return panel;
	}

	public void onBrowserEvent(Event event) {
		if (DOM.eventGetType(event) == Event.ONKEYDOWN) {
			int keyCode = DOM.eventGetKeyCode(event);
			if (keyCode == 46) {
				remove_task();
			}
			// System.out.print(keyCode);
		}

		super.onBrowserEvent(event);
	}

	public void remove_task() {
		for (Iterator i = selected_taskList.listIterator(); i.hasNext();) {
			AbsolutePanel w = (AbsolutePanel) i.next();
			ServiceTask parentSimpleTask = (ServiceTask) w.getParent()
					.getParent();
			List connection_list = new ArrayList();
			List connection_list2 = new ArrayList();
			List connection_list3 = new ArrayList();

			/* 分别对simpleTask的每个出入口做边的删除 */

			Collection cons = CustomUIObjectConnector.getWrapper( // in
					parentSimpleTask.getPanel2()).getConnections();
			for (Iterator j = cons.iterator(); j.hasNext();) {
				Connection c = (Connection) j.next();
				connection_list.add(c);
			}
			for (Iterator k = connection_list.iterator(); k.hasNext();) {
				Connection c = (Connection) k.next();
				c.remove();
			}
			CustomUIObjectConnector.unwrap(parentSimpleTask.getPanel2());

			Collection cons2 = CustomUIObjectConnector.getWrapper( // out
					parentSimpleTask.getPanel3()).getConnections();
			for (Iterator j = cons2.iterator(); j.hasNext();) {
				Connection c = (Connection) j.next();
				connection_list2.add(c);
			}
			for (Iterator k = connection_list2.iterator(); k.hasNext();) {
				Connection c = (Connection) k.next();
				c.remove();
			}
			CustomUIObjectConnector.unwrap(parentSimpleTask.getPanel3());

			Collection cons3 = CustomUIObjectConnector.getWrapper( // fault
					parentSimpleTask.getPanel4()).getConnections();
			for (Iterator j = cons3.iterator(); j.hasNext();) {
				Connection c = (Connection) j.next();
				connection_list3.add(c);
			}
			for (Iterator k = connection_list3.iterator(); k.hasNext();) {
				Connection c = (Connection) k.next();
				c.remove();
			}
			CustomUIObjectConnector.unwrap(parentSimpleTask.getPanel4());

			/* 删除容器控件的同时，要将其从toolboxDragController中取消注册 */
			for (Iterator k = dropcontroler_listList.iterator(); k.hasNext();) {
				/* 比对匹配后，未加删除，删除可能会出现问题，因此操作多了List可能会很大 */
				DropController c = (AbsolutePositionDropController) k.next();
				if (c.getDropTarget() == w)
					toolboxDragController.unregisterDropController(c);
			}
			for (Iterator k = timer_dropcontrollerList.iterator(); k.hasNext();) {
				/* 比对匹配后，未加删除，删除可能会出现问题，因此操作多了List可能会很大 */
				DropController c = (AbsolutePositionDropController) k.next();
				if (c.getDropTarget() == w)
					timerDragController.unregisterDropController(c);
			}

			i.remove();
			if (parentSimpleTask.getTimerNode() != null) {
				parentSimpleTask.getTimerNode().removeFromParent();
			}
			// if (parentSimpleTask.hasTimer) {
			// parentSimpleTask.getTimerNode().removeFromParent();
			// }
			parentSimpleTask.removeFromParent();
			list.remove(parentSimpleTask);

			return;
		}
	}

	public Client_Workflow save_workflow_in_workflowmode() {

		check = true;

		workFlow = new Client_Workflow();
		workFlow.getHasNodes();
		int starter_count = 0, ender_count = 0;
		boolean allNodesHasBeenSave = true;

		int node_count = panel.getWidgetCount();
		HashMap<Workflowtasknode, Client_Node> workflowMap = new HashMap<Workflowtasknode, Client_Node>();
		for (int index = 0; index < node_count; index++) {
			Widget widget = panel.getWidget(index);
			if (widget instanceof Workflowtasknode) {
				Workflowtasknode workflowtasknode = (Workflowtasknode) panel
						.getWidget(index);
				if (workflowtasknode.getText().equals(LOOPNODE)) {
					LoopTask loopTask = (LoopTask) workflowtasknode;
					Client_Loop loop = new Client_Loop();

					// 设置基本属性
					loop.setId(loopTask.getID());
					loop.setX(loopTask.getPixel_x());
					loop.setY(loopTask.getPixel_y());
					loop.setName(loopTask.getText());

					Client_InputPort inputPort = new Client_InputPort();
					inputPort.setId(Integer.toString(loopTask.getPanel2()
							.hashCode()));
					inputPort.setBelongTo(loop);
					Client_OutputPort outputPort = new Client_OutputPort();
					outputPort.setId(Integer.toString(loopTask.getPanel3()
							.hashCode()));
					outputPort.setBelongTo(loop);
					// outputport的nextnodes在第二轮设置
					Client_FaultOutputPort faultOutputPort = new Client_FaultOutputPort();
					faultOutputPort.setId(Integer.toString(loopTask.getPanel4()
							.hashCode()));
					faultOutputPort.setBelongTo(loop);
					loop.setInputPort(inputPort);
					loop.setOutputPort(outputPort);
					loop.setFaultOutputPort(faultOutputPort);

					Client_WhileCondition client_WhileCondition = new Client_WhileCondition();
					client_WhileCondition
							.setWhileCondition(loopTask.whilecondition);
					loop.setWhileCondition(client_WhileCondition);
					// System.out.println(loop.whileCondition);
					loop.setComment(null); // 暂时没有
					loop.setWorkflow(loopTask.sub_workflow);
					// transfer the service configure from looptask to
					// client_loop
					loop.setServiceConfigure(loopTask.getServiceConfigure());
					loop.setIsstart(loopTask.isIsstart());
					loop.setIsfinished(loopTask.isIsfinished());
					loop
							.setChangeServiceProvider(loopTask.changeServiceProvider);
					if (loopTask.changeServiceProvider) {
						loop
								.setSelectServiceNumber(loopTask.selectServiceNumber);
					}

					// 在工作流类中，设置起始节点
					if (loop.isIsstart()) {
						workFlow.setHasFirstNode(loop);
						starter_count++;
						if (starter_count > 1) {
							System.out.println("弹出一个对话框，强行结束保存过程");
							check = Window
									.confirm("Force to end and return to null, sure?");
							return null;
						}
					}

					if (loop.isIsfinished()) {
						workFlow.setHasLastNode(loop);
						ender_count++;
						workFlow.getLastNodes().add(loop);
						// if(ender_count>1){
						// System.out.println("弹出一个对话框，强行结束保存过程");
						// return null;
						// }
					}

					if (!loopTask.hasFinishedConfigure)
						allNodesHasBeenSave = false;

					workFlow.getHasNodes().add(loop);
					Client_Node workFlowNode = loop;
					workflowMap.put(workflowtasknode, workFlowNode); // 为接下来寻找nextnode服务
				} else if (workflowtasknode.getText().equals(CHOICENODE)) {
					ChoiceTask choiceTask = (ChoiceTask) workflowtasknode;
					Client_Choice choice = new Client_Choice();

					// 设置基本属性
					choice.setId(choiceTask.getID());
					choice.setX(choiceTask.getPixel_x());
					choice.setY(choiceTask.getPixel_y());
					choice.setName(choiceTask.getText());

					Client_InputPort inputPort = new Client_InputPort();
					inputPort.setId(Integer.toString(choiceTask.getPanel2()
							.hashCode()));
					inputPort.setBelongTo(choice);
					Client_FaultOutputPort faultOutputPort = new Client_FaultOutputPort();
					faultOutputPort.setId(Integer.toString(choiceTask
							.getPanel4().hashCode()));
					faultOutputPort.setBelongTo(choice);
					choice.setInputPort(inputPort);
					choice.setFaultOutputPort(faultOutputPort);
					choice.conditionalOutputPort = new ArrayList<Client_ConditionalOutputPort>();
					int ifcondition_index = 0;
					for (Iterator iterator = choiceTask.outport_list.iterator(); iterator
							.hasNext();) {
						MyPanel myPanel = (MyPanel) iterator.next();
						Client_ConditionalOutputPort conditionalOutputPort = new Client_ConditionalOutputPort();
						conditionalOutputPort.setId(Integer.toString(myPanel
								.hashCode()));
						conditionalOutputPort.setBelongTo(choice);
						Client_IfCondition client_IfCondition = (Client_IfCondition) choiceTask.IfConditionlist
								.get(ifcondition_index++);
						conditionalOutputPort
								.setClient_IfCondition(client_IfCondition);
						choice.conditionalOutputPort.add(conditionalOutputPort);
					}

					// for test ,set a default port
					choice.setActual_outport(0);
					System.out.println(choice.getActual_outport());

					choice.setComment(null); // 暂时没有
					choice.setContentPath(choiceTask.contentPath);
					choice.setParamInfo(choiceTask.paramInfo_for_configure);
					choice.setIsstart(choiceTask.isIsstart());
					choice.setIsfinished(choiceTask.isIsfinished());

					if (choiceTask.outport_count == 0) {
						System.out.println("弹出一个对话框，choiceTask不能没有出口，强行结束保存过程");
						check = Window
								.confirm("ChoiceTask should have an output, force to end and return to null, sure?");
						return null;
					}
					// 在工作流类中，设置起始节点
					if (choice.isIsstart()) {
						workFlow.setHasFirstNode(choice);
						starter_count++;
						if (starter_count > 1) {
							System.out.println("弹出一个对话框，强行结束保存过程");
							check = Window
									.confirm("Force to end and return to null, sure?");
							return null;
						}
					}

					if (choice.isIsfinished()) {
						workFlow.setHasLastNode(choice);
						ender_count++;
						workFlow.getLastNodes().add(choice);
						// if(ender_count>1){
						// System.out.println("弹出一个对话框，强行结束保存过程");
						// return null;
						// }
					}

					if (!choiceTask.hasFinishedConfigure)
						allNodesHasBeenSave = false;

					workFlow.getHasNodes().add(choice);
					Client_Node workFlowNode = choice;
					workflowMap.put(workflowtasknode, workFlowNode);
				} else if (workflowtasknode.getText().equals(FIXEDFORNODE)) {
					FixedFor fixedForTask = (FixedFor) workflowtasknode;
					Client_FixedNumFor fixedNumFor = new Client_FixedNumFor();

					// 设置基本属性
					fixedNumFor.setId(fixedForTask.getID());
					fixedNumFor.setX(fixedForTask.getPixel_x());
					fixedNumFor.setY(fixedForTask.getPixel_y());
					fixedNumFor.setName(fixedForTask.getText());

					Client_InputPort inputPort = new Client_InputPort();
					inputPort.setId(Integer.toString(fixedForTask.getPanel2()
							.hashCode()));
					inputPort.setBelongTo(fixedNumFor);
					Client_OutputPort outputPort = new Client_OutputPort();
					outputPort.setId(Integer.toString(fixedForTask.getPanel3()
							.hashCode()));
					outputPort.setBelongTo(fixedNumFor);
					Client_FaultOutputPort faultOutputPort = new Client_FaultOutputPort();
					faultOutputPort.setId(Integer.toString(fixedForTask
							.getPanel4().hashCode()));
					faultOutputPort.setBelongTo(fixedNumFor);
					fixedNumFor.setInputPort(inputPort);
					fixedNumFor.setOutputPort(outputPort);
					fixedNumFor.setFaultOutputPort(faultOutputPort);

					fixedNumFor.setComment(null); // 暂时没有
					fixedNumFor.setChangeServiceProvider(fixedForTask
							.isChangeServiceProvider());
					fixedNumFor.setWorkflow(fixedForTask.sub_workflow);
					fixedNumFor.setServiceConfigure(fixedForTask
							.getServiceConfigure());
					fixedNumFor.setIsstart(fixedForTask.isIsstart());
					fixedNumFor.setIsfinished(fixedForTask.isIsfinished());

					// 在工作流类中，设置起始节点
					if (fixedNumFor.isIsstart()) {
						workFlow.setHasFirstNode(fixedNumFor);
						starter_count++;
						if (starter_count > 1) {
							System.out.println("弹出一个对话框，强行结束保存过程");
							check = Window
									.confirm("Force to end and return to null, sure?");
							return null;
						}
					}

					if (fixedNumFor.isIsfinished()) {
						workFlow.setHasLastNode(fixedNumFor);
						ender_count++;
						workFlow.getLastNodes().add(fixedNumFor);
						// if(ender_count>1){
						// System.out.println("弹出一个对话框，强行结束保存过程");
						// return null;
						// }
					}

					if (!fixedForTask.hasFinishedConfigure)
						allNodesHasBeenSave = false;

					workFlow.getHasNodes().add(fixedNumFor);
					Client_Node workFlowNode = fixedNumFor;
					workflowMap.put(workflowtasknode, workFlowNode);
				} else if (workflowtasknode.getText().equals(NONFIXEDNUMFOR)) {
					NonFixedFor nonFixedForTask = (NonFixedFor) workflowtasknode;
					Client_NonFixedNumFor nonFixedNumFor = new Client_NonFixedNumFor();

					// 设置基本属性
					nonFixedNumFor.setId(nonFixedForTask.getID());
					nonFixedNumFor.setX(nonFixedForTask.getPixel_x());
					nonFixedNumFor.setY(nonFixedForTask.getPixel_y());
					nonFixedNumFor.setName(nonFixedForTask.getText());

					Client_InputPort inputPort = new Client_InputPort();
					inputPort.setId(Integer.toString(nonFixedForTask
							.getPanel2().hashCode()));
					inputPort.setBelongTo(nonFixedNumFor);
					Client_OutputPort outputPort = new Client_OutputPort();
					outputPort.setId(Integer.toString(nonFixedForTask
							.getPanel3().hashCode()));
					outputPort.setBelongTo(nonFixedNumFor);
					Client_FaultOutputPort faultOutputPort = new Client_FaultOutputPort();
					faultOutputPort.setId(Integer.toString(nonFixedForTask
							.getPanel4().hashCode()));
					faultOutputPort.setBelongTo(nonFixedNumFor);
					nonFixedNumFor.setInputPort(inputPort);
					nonFixedNumFor.setOutputPort(outputPort);
					nonFixedNumFor.setFaultOutputPort(faultOutputPort);

					nonFixedNumFor.setComment(null); // 暂时没有
					nonFixedNumFor.setChangeServiceProvider(nonFixedForTask
							.isChangeServiceProvider());
					nonFixedNumFor.setWorkflow(nonFixedForTask.sub_workflow);
					nonFixedNumFor.setServiceConfigure(nonFixedForTask
							.getServiceConfigure());
					nonFixedNumFor.setIsstart(nonFixedForTask.isIsstart());
					nonFixedNumFor
							.setIsfinished(nonFixedForTask.isIsfinished());

					// 在工作流类中，设置起始节点
					if (nonFixedNumFor.isIsstart()) {
						workFlow.setHasFirstNode(nonFixedNumFor);
						starter_count++;
						if (starter_count > 1) {
							System.out.println("弹出一个对话框，强行结束保存过程");
							check = Window
									.confirm("Force to end and return to null, sure?");
							return null;
						}
					}

					if (nonFixedNumFor.isIsfinished()) {
						workFlow.setHasLastNode(nonFixedNumFor);
						ender_count++;
						workFlow.getLastNodes().add(nonFixedNumFor);
						// if(ender_count>1){
						// System.out.println("弹出一个对话框，强行结束保存过程");
						// return null;
						// }
					}

					if (!nonFixedForTask.hasFinishedConfigure)
						allNodesHasBeenSave = false;

					workFlow.getHasNodes().add(nonFixedNumFor);
					Client_Node workFlowNode = nonFixedNumFor;
					workflowMap.put(workflowtasknode, workFlowNode);
				} else {
					ServiceTask ServiceTask = (ServiceTask) workflowtasknode;
					Client_ServiceTask service = new Client_ServiceTask();

					// 设置基本属性
					service.setId(ServiceTask.getID());
					service.setX(ServiceTask.getPixel_x());
					service.setY(ServiceTask.getPixel_y());
					service.setName(ServiceTask.getText());

					// 设置服务的基本信息，包括服务配置信息，服务描述，服务的wsdl路径
					Client_Service client_Service = new Client_Service();
					// client_Service.setServiceName(ServiceTask.getText());
					Client_Configuration client_Configuration = new Client_Configuration();
					client_Configuration.setServiceConfigure(ServiceTask
							.getServiceConfigure());
					client_Service.setHasConfiguration(client_Configuration);
					client_Service.setHasDesciption(null); // 暂时不设置
					client_Service.setHasQosRequirement(null); // 暂时不设置
					client_Service.setServiceInfo(ServiceTask.getServiceInfo());
					service.setService(client_Service); // 将服务配置，和服务内容都保存到servicetask当中
					// service.setServiceInfo(ServiceTask.getServiceInfo());

					if (ServiceTask.hasTimer) {
						Client_Timer client_Timer = new Client_Timer();
						client_Timer.setId(Integer.toString(client_Timer
								.hashCode()));
						client_Timer.setTimeString(ServiceTask.getTimerNode()
								.getStart_time());
						service.setTimer(client_Timer);
					}

					Client_InputPort inputPort = new Client_InputPort();
					inputPort.setId(Integer.toString(ServiceTask.getPanel2()
							.hashCode()));
					inputPort.setBelongTo(service);
					Client_OutputPort outputPort = new Client_OutputPort();
					outputPort.setId(Integer.toString(ServiceTask.getPanel3()
							.hashCode()));
					outputPort.setBelongTo(service);
					Client_FaultOutputPort faultOutputPort = new Client_FaultOutputPort();
					faultOutputPort.setId(Integer.toString(ServiceTask
							.getPanel4().hashCode()));
					faultOutputPort.setBelongTo(service);
					service.setInputPort(inputPort);
					service.setOutputPort(outputPort);
					service.setFaultOutputPort(faultOutputPort);

					service.setComment(null); // 暂时没有
					service.setCurrent_method_index(ServiceTask.current_index);
					service.setIsstart(ServiceTask.isIsstart());
					service.setIsfinished(ServiceTask.isIsfinished());

					// 在工作流类中，设置起始节点
					if (service.isIsstart()) {
						workFlow.setHasFirstNode(service);
						starter_count++;
						if (starter_count > 1) {
							System.out.println("弹出一个对话框，起始节点多于一个，强行结束保存过程");
							check = Window
									.confirm("Begin with multi nodes, force to end and return to null, sure?");
							return null;
						}
					}
					if (service.isIsfinished()) {
						workFlow.setHasLastNode(service);
						ender_count++;
						workFlow.getLastNodes().add(service);
						// if(ender_count>1){
						// System.out.println("弹出一个对话框，终止节点多于一个，强行结束保存过程");
						// return null;
						// }
					}

					if (!ServiceTask.hasFinishedConfigure)
						allNodesHasBeenSave = false;

					workFlow.getHasNodes().add(service);
					Client_Node workFlowNode = service;
					workflowMap.put(workflowtasknode, workFlowNode);
					
					//for test, assume the state of node is running
//					service.setNodestate(2);
				}
			}
		}

		/* 添加边，并且设置nextnode列表 */
		for (int index = 0; index < node_count; index++) {
			Widget widget = panel.getWidget(index);
			if (widget instanceof Workflowtasknode) { // 确保widget是workflow的节点

				Workflowtasknode workflowtasknode = (Workflowtasknode) panel
						.getWidget(index);

				if (workflowtasknode.getText().equals(LOOPNODE)) { // 节点是loopnode时
					LoopTask loopTask = (LoopTask) workflowtasknode;
					Client_Loop loop = (Client_Loop) workflowMap.get(loopTask);

					// 给loop的outputport中的nextnodes添加节点
					ArrayList<ServiceTask> backTaskList = new ArrayList<ServiceTask>();
					MyPanel outport = loopTask.getPanel3();
					CustomUIObjectConnector cons = CustomUIObjectConnector
							.getWrapper(outport);
					Collection collection = cons.getConnections();
					CustomUIObjectConnector back_end_Connector;
					for (Iterator iterator2 = collection.iterator(); iterator2
							.hasNext();) {
						// 找出outputport的下一个节点
						CustomConnection c = (CustomConnection) iterator2
								.next();
						back_end_Connector = (CustomUIObjectConnector) c
								.getConnected().get(1);
						MyPanel back_end_panelPanel = (MyPanel) back_end_Connector.wrapped;
						ServiceTask back_end_simpletask = (ServiceTask) back_end_panelPanel
								.getParent().getParent();

						Workflowtasknode workflowtasknode2 = back_end_simpletask;
						Client_Node loop2 = workflowMap.get(workflowtasknode2);
						loop.getOutputPort().getHasNextNodes().add(loop2);
						backTaskList.add(back_end_simpletask);
					}

					ArrayList<ServiceTask> preTaskList = new ArrayList<ServiceTask>();
					MyPanel inport = loopTask.getPanel2();
					CustomUIObjectConnector cons2 = CustomUIObjectConnector
							.getWrapper(inport);
					Collection collection2 = cons2.getConnections();

					loop.setPreNodeNum(collection2.size());

					CustomUIObjectConnector pre_end_Connector;
					for (Iterator iterator2 = collection2.iterator(); iterator2
							.hasNext();) {
						// 找出outputport的下一个节点
						CustomConnection c = (CustomConnection) iterator2
								.next();
						pre_end_Connector = (CustomUIObjectConnector) c
								.getConnected().get(0);
						MyPanel pre_end_panelPanel = (MyPanel) pre_end_Connector.wrapped;
						ServiceTask pre_end_simpletask = (ServiceTask) pre_end_panelPanel
								.getParent().getParent();

						Workflowtasknode workflowtasknode2 = pre_end_simpletask;
						Client_Node loop2 = workflowMap.get(workflowtasknode2);
						loop.getInputPort().getBeforeNodes().add(loop2);
						preTaskList.add(pre_end_simpletask);
					}

				} else if (workflowtasknode.getText().equals(CHOICENODE)) { // 节点是选择几点
					ChoiceTask choiceTask = (ChoiceTask) workflowtasknode;
					Client_Choice choice = (Client_Choice) workflowMap
							.get(choiceTask);
					int outputport_count = 0;
					for (Iterator iterator2 = choiceTask.outport_list
							.iterator(); iterator2.hasNext();) {

						ArrayList<ServiceTask> backTaskList = new ArrayList<ServiceTask>();
						MyPanel outport = (MyPanel) iterator2.next();
						@SuppressWarnings("unused")
						String outportid = Integer.toString(outport.hashCode());
						// Client_ConditionalOutputPort conditionalOutputPort =
						// null;
						// for (Iterator iterator3 =
						// choice.conditionalOutputPort
						// .iterator(); iterator3.hasNext();) {
						// Client_ConditionalOutputPort conditionalOutputPort2 =
						// (Client_ConditionalOutputPort) iterator3
						// .next();
						// if (outportid
						// .equals(conditionalOutputPort2.getId())) {
						// conditionalOutputPort = conditionalOutputPort2;
						// }
						// }
						Client_ConditionalOutputPort conditionalOutputPort = choice
								.getConditionalOutputPort().get(
										outputport_count++);

						CustomUIObjectConnector cons = CustomUIObjectConnector
								.getWrapper(outport);
						Collection collection = cons.getConnections();
						CustomUIObjectConnector back_end_Connector;
						for (Iterator iterator4 = collection.iterator(); iterator4
								.hasNext();) {
							CustomConnection c = (CustomConnection) iterator4
									.next();
							back_end_Connector = (CustomUIObjectConnector) c
									.getConnected().get(1);
							MyPanel back_end_panelPanel = (MyPanel) back_end_Connector.wrapped;
							ServiceTask back_end_simpletask = (ServiceTask) back_end_panelPanel
									.getParent().getParent();

							Workflowtasknode workflowtasknode2 = back_end_simpletask;
							Client_Node choice2 = workflowMap
									.get(workflowtasknode2);
							conditionalOutputPort.getHasNextNodes()
									.add(choice2);

							backTaskList.add(back_end_simpletask);
						}
					}

					ArrayList<ServiceTask> preTaskList = new ArrayList<ServiceTask>();
					MyPanel inport = choiceTask.getPanel2();
					CustomUIObjectConnector cons2 = CustomUIObjectConnector
							.getWrapper(inport);
					Collection collection2 = cons2.getConnections();

					choice.setPreNodeNum(collection2.size());

					CustomUIObjectConnector pre_end_Connector;
					for (Iterator iterator2 = collection2.iterator(); iterator2
							.hasNext();) {
						// 找出outputport的下一个节点
						CustomConnection c = (CustomConnection) iterator2
								.next();
						pre_end_Connector = (CustomUIObjectConnector) c
								.getConnected().get(0);
						MyPanel pre_end_panelPanel = (MyPanel) pre_end_Connector.wrapped;
						ServiceTask pre_end_simpletask = (ServiceTask) pre_end_panelPanel
								.getParent().getParent();

						Workflowtasknode workflowtasknode2 = pre_end_simpletask;
						Client_Node choice2 = workflowMap
								.get(workflowtasknode2);
						choice.getInputPort().getBeforeNodes().add(choice2);
						preTaskList.add(pre_end_simpletask);
					}
				} else if (workflowtasknode.getText().equals(FIXEDFORNODE)) {
					FixedFor fixedForTask = (FixedFor) workflowtasknode;
					Client_FixedNumFor fixedNumFor = (Client_FixedNumFor) workflowMap
							.get(fixedForTask);

					ArrayList<ServiceTask> backTaskList = new ArrayList<ServiceTask>();
					MyPanel outport = fixedForTask.getPanel3();
					CustomUIObjectConnector cons = CustomUIObjectConnector
							.getWrapper(outport);
					Collection collection = cons.getConnections();
					CustomUIObjectConnector back_end_Connector;
					for (Iterator iterator2 = collection.iterator(); iterator2
							.hasNext();) {
						CustomConnection c = (CustomConnection) iterator2
								.next();
						back_end_Connector = (CustomUIObjectConnector) c
								.getConnected().get(1);
						MyPanel back_end_panelPanel = (MyPanel) back_end_Connector.wrapped;
						ServiceTask back_end_simpletask = (ServiceTask) back_end_panelPanel
								.getParent().getParent();

						Workflowtasknode workflowtasknode2 = back_end_simpletask;
						Client_Node fixedNumFor2 = workflowMap
								.get(workflowtasknode2);
						fixedNumFor.getOutputPort().getHasNextNodes().add(
								fixedNumFor2);

						backTaskList.add(back_end_simpletask);
					}

					ArrayList<ServiceTask> preTaskList = new ArrayList<ServiceTask>();
					MyPanel inport = fixedForTask.getPanel2();
					CustomUIObjectConnector cons2 = CustomUIObjectConnector
							.getWrapper(inport);
					Collection collection2 = cons2.getConnections();

					fixedNumFor.setPreNodeNum(collection2.size());

					CustomUIObjectConnector pre_end_Connector;
					for (Iterator iterator2 = collection2.iterator(); iterator2
							.hasNext();) {
						// 找出outputport的下一个节点
						CustomConnection c = (CustomConnection) iterator2
								.next();
						pre_end_Connector = (CustomUIObjectConnector) c
								.getConnected().get(0);
						MyPanel pre_end_panelPanel = (MyPanel) pre_end_Connector.wrapped;
						ServiceTask pre_end_simpletask = (ServiceTask) pre_end_panelPanel
								.getParent().getParent();

						Workflowtasknode workflowtasknode2 = pre_end_simpletask;
						Client_Node loop2 = workflowMap.get(workflowtasknode2);
						fixedNumFor.getInputPort().getBeforeNodes().add(loop2);
						preTaskList.add(pre_end_simpletask);
					}
				} else if (workflowtasknode.getText().equals(NONFIXEDNUMFOR)) {
					NonFixedFor nonFixedForTask = (NonFixedFor) workflowtasknode;
					Client_NonFixedNumFor nonFixedNumFor = (Client_NonFixedNumFor) workflowMap
							.get(nonFixedForTask);

					ArrayList<ServiceTask> backTaskList = new ArrayList<ServiceTask>();
					MyPanel outport = nonFixedForTask.getPanel3();
					CustomUIObjectConnector cons = CustomUIObjectConnector
							.getWrapper(outport);
					Collection collection = cons.getConnections();
					CustomUIObjectConnector back_end_Connector;
					for (Iterator iterator2 = collection.iterator(); iterator2
							.hasNext();) {
						CustomConnection c = (CustomConnection) iterator2
								.next();
						back_end_Connector = (CustomUIObjectConnector) c
								.getConnected().get(1);
						MyPanel back_end_panelPanel = (MyPanel) back_end_Connector.wrapped;
						ServiceTask back_end_simpletask = (ServiceTask) back_end_panelPanel
								.getParent().getParent();

						Workflowtasknode workflowtasknode2 = back_end_simpletask;
						Client_Node nonFixedNumFor2 = workflowMap
								.get(workflowtasknode2);
						nonFixedNumFor.getOutputPort().getHasNextNodes().add(
								nonFixedNumFor2);

						backTaskList.add(back_end_simpletask);
					}

					ArrayList<ServiceTask> preTaskList = new ArrayList<ServiceTask>();
					MyPanel inport = nonFixedForTask.getPanel2();
					CustomUIObjectConnector cons2 = CustomUIObjectConnector
							.getWrapper(inport);
					Collection collection2 = cons2.getConnections();

					nonFixedNumFor.setPreNodeNum(collection2.size());

					CustomUIObjectConnector pre_end_Connector;
					for (Iterator iterator2 = collection2.iterator(); iterator2
							.hasNext();) {
						// 找出outputport的下一个节点
						CustomConnection c = (CustomConnection) iterator2
								.next();
						pre_end_Connector = (CustomUIObjectConnector) c
								.getConnected().get(0);
						MyPanel pre_end_panelPanel = (MyPanel) pre_end_Connector.wrapped;
						ServiceTask pre_end_simpletask = (ServiceTask) pre_end_panelPanel
								.getParent().getParent();

						Workflowtasknode workflowtasknode2 = pre_end_simpletask;
						Client_Node loop2 = workflowMap.get(workflowtasknode2);
						nonFixedNumFor.getInputPort().getBeforeNodes().add(
								loop2);
						preTaskList.add(pre_end_simpletask);
					}
				} else { // 节点是服务节点
					ServiceTask ServiceTask = (ServiceTask) workflowtasknode;
					Client_ServiceTask service = (Client_ServiceTask) workflowMap
							.get(ServiceTask);

					ArrayList<Workflowtasknode> backTaskList = new ArrayList<Workflowtasknode>();
					MyPanel outport = ServiceTask.getPanel3();
					CustomUIObjectConnector cons = CustomUIObjectConnector
							.getWrapper(outport);
					Collection collection = cons.getConnections();
					CustomUIObjectConnector back_end_Connector;
					for (Iterator iterator2 = collection.iterator(); iterator2
							.hasNext();) {
						CustomConnection c = (CustomConnection) iterator2
								.next();
						back_end_Connector = (CustomUIObjectConnector) c
								.getConnected().get(1);
						MyPanel back_end_panelPanel = (MyPanel) back_end_Connector.wrapped;
						Workflowtasknode back_end_simpletask = (Workflowtasknode) back_end_panelPanel
								.getParent().getParent();

						Workflowtasknode workflowtasknode2 = back_end_simpletask;
						Client_Node service2 = workflowMap
								.get(workflowtasknode2);
						service.getOutputPort().getHasNextNodes().add(service2);

						backTaskList.add(back_end_simpletask);
					}

					ArrayList<Workflowtasknode> preTaskList = new ArrayList<Workflowtasknode>();
					MyPanel inport = ServiceTask.getPanel2();
					CustomUIObjectConnector cons2 = CustomUIObjectConnector
							.getWrapper(inport);
					Collection collection2 = cons2.getConnections();

					service.setPreNodeNum(collection2.size());

					CustomUIObjectConnector pre_end_Connector;
					for (Iterator iterator2 = collection2.iterator(); iterator2
							.hasNext();) {
						// 找出outputport的下一个节点
						CustomConnection c = (CustomConnection) iterator2
								.next();
						pre_end_Connector = (CustomUIObjectConnector) c
								.getConnected().get(0);
						MyPanel pre_end_panelPanel = (MyPanel) pre_end_Connector.wrapped;
						Workflowtasknode pre_end_simpletask = (Workflowtasknode) pre_end_panelPanel
								.getParent().getParent();

						Workflowtasknode workflowtasknode2 = pre_end_simpletask;
						Client_Node service2 = workflowMap
								.get(workflowtasknode2);
						service.getInputPort().getBeforeNodes().add(service2);
						preTaskList.add(pre_end_simpletask);
					}
				}
			}
		}

		if (starter_count == 0 || ender_count == 0) {
			System.out.println("弹出一个对话框，起始节点或终止节点未设置，强行结束保存过程");
			check = Window
					.confirm("No begin-node or end-node, force to end and return to null, sure?");
			return null;
		}
		if (allNodesHasBeenSave == false) {
			System.out.println("还有节点未完成服务配置，强行结束保存过程");
			check = Window
					.confirm("Some nodes are not configured, force to end and return to null, sure?");
			return null;
		}

		Client_Node firstNode = workFlow.getHasFirstNode();
		Client_InputPort client_InputPort = firstNode.getInputPort();
		if (client_InputPort.getBeforeNodes() != null
				&& client_InputPort.getBeforeNodes().size() > 0) {
			System.out.println("弹出一个对话框，起始节点前还有节点，强行结束保存过程");
			check = Window
					.confirm("There's node before begin-node, force to end and return to null, sure?");
			return null;
		}

		// Client_Node lastNode = workFlow.getHasLastNode();
		// System.out.println("lastNode "+lastNode.getName());
		// if(lastNode.getName().equals(FIXEDFORNODE)){
		// Client_FixedNumFor client_FixedNumFor = (Client_FixedNumFor)lastNode;
		// Client_OutputPort client_OutputPort =
		// client_FixedNumFor.getOutputPort();
		// if(client_OutputPort.getHasNextNodes()!=null&&client_OutputPort.getHasNextNodes().size()>0){
		// System.out.println("弹出一个对话框，终止节点节点后还有节点，强行结束保存过程");
		// return null;
		// }
		// }
		// else if(lastNode.getName().equals(NONFIXEDNUMFOR)){
		// Client_NonFixedNumFor client_NonFixedNumFor =
		// (Client_NonFixedNumFor)lastNode;
		// Client_OutputPort client_OutputPort =
		// client_NonFixedNumFor.getOutputPort();
		// if(client_OutputPort.getHasNextNodes()!=null&&client_OutputPort.getHasNextNodes().size()>0){
		// System.out.println("弹出一个对话框，终止节点节点后还有节点，强行结束保存过程");
		// return null;
		// }
		// }
		// else if(lastNode.getName().equals(LOOPNODE)){
		// Client_Loop client_Loop = (Client_Loop)lastNode;
		// Client_OutputPort client_OutputPort = client_Loop.getOutputPort();
		// if(client_OutputPort.getHasNextNodes()!=null&&client_OutputPort.getHasNextNodes().size()>0){
		// System.out.println("弹出一个对话框，终止节点节点后还有节点，强行结束保存过程");
		// return null;
		// }
		// }
		// else if(lastNode.getName().equals(CHOICENODE)){
		// Client_Choice client_Choice = (Client_Choice)lastNode;
		// for(Iterator< Client_ConditionalOutputPort> iterator =
		// client_Choice.getConditionalOutputPort().iterator();iterator.hasNext();){
		// Client_ConditionalOutputPort client_ConditionalOutputPort =
		// iterator.next();
		// if(client_ConditionalOutputPort.getHasNextNodes()!=null&&client_ConditionalOutputPort.getHasNextNodes().size()>0){
		// System.out.println("弹出一个对话框，终止节点节点后还有节点，强行结束保存过程");
		// return null;
		// }
		// }
		// }
		// else{
		// Client_ServiceTask client_ServiceTask = (Client_ServiceTask)lastNode;
		// Client_OutputPort client_OutputPort =
		// client_ServiceTask.getOutputPort();
		// if(client_OutputPort.getHasNextNodes()!=null&&client_OutputPort.getHasNextNodes().size()>0){
		// System.out.println("弹出一个对话框，终止节点节点后还有节点，强行结束保存过程");
		// return null;
		// }
		// }
		for (Iterator<Client_Node> iterator = workFlow.getLastNodes()
				.iterator(); iterator.hasNext();) {
			Client_Node lastNode = iterator.next();
			System.out.println("lastNode " + lastNode.getName());
			if (lastNode.getName().equals(FIXEDFORNODE)) {
				Client_FixedNumFor client_FixedNumFor = (Client_FixedNumFor) lastNode;
				Client_OutputPort client_OutputPort = client_FixedNumFor
						.getOutputPort();
				if (client_OutputPort.getHasNextNodes() != null
						&& client_OutputPort.getHasNextNodes().size() > 0) {
					System.out.println("弹出一个对话框，终止节点节点后还有节点，强行结束保存过程");
					check = Window
							.confirm("There's node after end-node, force to end and return to null, sure?");
					return null;
				}
			} else if (lastNode.getName().equals(NONFIXEDNUMFOR)) {
				Client_NonFixedNumFor client_NonFixedNumFor = (Client_NonFixedNumFor) lastNode;
				Client_OutputPort client_OutputPort = client_NonFixedNumFor
						.getOutputPort();
				if (client_OutputPort.getHasNextNodes() != null
						&& client_OutputPort.getHasNextNodes().size() > 0) {
					System.out.println("弹出一个对话框，终止节点节点后还有节点，强行结束保存过程");
					check = Window
							.confirm("There's node after end-node, force to end and return to null, sure?");
					return null;
				}
			} else if (lastNode.getName().equals(LOOPNODE)) {
				Client_Loop client_Loop = (Client_Loop) lastNode;
				Client_OutputPort client_OutputPort = client_Loop
						.getOutputPort();
				if (client_OutputPort.getHasNextNodes() != null
						&& client_OutputPort.getHasNextNodes().size() > 0) {
					System.out.println("弹出一个对话框，终止节点节点后还有节点，强行结束保存过程");
					check = Window
							.confirm("There's node after end-node, force to end and return to null, sure?");
					return null;
				}
			} else if (lastNode.getName().equals(CHOICENODE)) {
				Client_Choice client_Choice = (Client_Choice) lastNode;
				for (Iterator<Client_ConditionalOutputPort> iterator2 = client_Choice
						.getConditionalOutputPort().iterator(); iterator
						.hasNext();) {
					Client_ConditionalOutputPort client_ConditionalOutputPort = iterator2
							.next();
					if (client_ConditionalOutputPort.getHasNextNodes() != null
							&& client_ConditionalOutputPort.getHasNextNodes()
									.size() > 0) {
						System.out.println("弹出一个对话框，终止节点节点后还有节点，强行结束保存过程");
						check = Window
								.confirm("There's node after end-node, force to end and return to null, sure?");
						return null;
					}
				}
			} else {
				Client_ServiceTask client_ServiceTask = (Client_ServiceTask) lastNode;
				Client_OutputPort client_OutputPort = client_ServiceTask
						.getOutputPort();
				if (client_OutputPort.getHasNextNodes() != null
						&& client_OutputPort.getHasNextNodes().size() > 0) {
					System.out.println("弹出一个对话框，终止节点节点后还有节点，强行结束保存过程");
					check = Window
							.confirm("There's node after end-node, force to end and return to null, sure?");
					return null;
				}
			}
		}
		System.out.println(node_count);

		return workFlow;

	}

	public void load_workflow_from_server(Client_Workflow client_Workflow) {
		load_client_Workflow = null;
		load_client_Workflow = client_Workflow;
		
		if(coverWorkflowControlPanel!=null){
			coverWorkflowControlPanel.removeFromParent();
		}
		if(coverWorkflowEditRegionLayer!=null){
			coverWorkflowEditRegionLayer.removeFromParent();
		}
		
		
		display_workflow(load_client_Workflow);
	}

	public void display_workflow(Client_Workflow client_Workflow) {
		if(client_Workflow!=null){
			if(!client_Workflow.isLock()){
				display_workflow_while_unlock(client_Workflow);
				System.out.println("workflow unlocked");
			}
			else{
				display_workflow_while_locked(client_Workflow);
				System.out.println("workflow locked");
			}
		}

	}
	
	public void display_workflow_while_unlock(Client_Workflow client_Workflow){
		
		panel.clear(); // 清空主面板上显示的控件
		// 清空显示在rootpanel上的timernode
		for (Iterator<TimerNode> iterator = timernodeList.iterator(); iterator
				.hasNext();) {
			TimerNode timerNode = iterator.next();
			timerNode.removeFromParent();
		}
		timernodeList.clear();
		hashMap.clear(); // 清空记录当前各类节点个数的列表

		// 设置各种控制节点的初始个数为0
		hashMap.put(LOOPNODETITLE, 0);
		hashMap.put(CHOICENODE, 0);
		hashMap.put(FIXEDFORNODETITLE, 0);
		hashMap.put(NONFIXEDNUMFORTITLE, 0);
		// 设置各种服务节点的初始个数为0
		for (Iterator iterator = service_info_list.iterator(); iterator
				.hasNext();) {
			String serviceName = (String) iterator.next();
			hashMap.put(serviceName, 0);
		}

		if (client_Workflow != null) {
			Client_Workflow workflow = client_Workflow;
			HashMap<Client_Node, Workflowtasknode> workflowMap = new HashMap<Client_Node, Workflowtasknode>();
			int node_count = client_Workflow.getHasNodes().size();
			for (int index = 0; index < node_count; index++) {
				Client_Node client_Node = workflow.getHasNodes().get(index);
				if (client_Node.getName().equals(LOOPNODE)) {
					Client_Loop client_Loop = (Client_Loop) client_Node;
					LoopTask loop = new LoopTask(client_Node.getName(), this,
							overview);

					loop.setID(client_Loop.getId());

					int num = hashMap.get(LOOPNODETITLE);
					set_node_title(loop, LOOPNODETITLE + (num + 1));
					hashMap.put(LOOPNODETITLE, num + 1);

					loop.sub_workflow = client_Loop.getWorkflow(); // 将client_loop中的子工作流数据保存到looptask中
					loop.setServiceConfigure(client_Loop.getServiceConfigure());
					if (client_Loop.getWhileCondition() != null) {
						String whileCondition = client_Loop.getWhileCondition()
								.getWhileCondition();
						loop.whilecondition = whileCondition;
					}

					MyPanel in = loop.getPanel2();
					MyPanel out = loop.getPanel3();
					MyPanel fault = loop.getPanel4();
					loop.sinkEvents(Event.ONCLICK);

					loop.setPixel_x(client_Loop.getX());
					loop.setPixel_y(client_Loop.getY());

					panel.add(loop, client_Loop.getX(), client_Loop.getY());
					dragController1.makeDraggable(loop);

					CustomUIObjectConnector.wrap(in);
					CustomUIObjectConnector.wrap(out);
					CustomUIObjectConnector.wrap(fault);

					service_name_to_id
							.put(loop.getTitle(), client_Loop.getId()); // 每新建一个节点，就把名字和id号关联起来

					// 设置是否为终止点
					if (client_Loop.isIsfinished()) {
						SetFinishCommand setFinishCommand = (SetFinishCommand) loop.setfinish
								.getCommand();
						setFinishCommand.setFinisher();
					}
					// 设置是否为起始点
					if (client_Loop.isIsstart()) {
						SetStartCommand setStartCommand = (SetStartCommand) loop.setstart
								.getCommand();
						setStartCommand.setStarter();
					}

					loop.setHasFinishedConfigure(true);
					loop
							.setChangeServiceProvider(client_Loop.changeServiceProvider);
					if (client_Loop.changeServiceProvider) {
						loop
								.setSelectServiceNumber(client_Loop.selectServiceNumber);
					}

					// 设置基本属性
					Workflowtasknode workFlowNode = loop;
					workflowMap.put(client_Node, workFlowNode); // 为接下来寻找nextnode服务
				} else if (client_Node.getName().equals(CHOICENODE)) {
					Client_Choice client_Choice = (Client_Choice) client_Node;
					ChoiceTask choice = new ChoiceTask(client_Node.getName(),
							this);

					choice.setID(client_Choice.getId());

					int num = hashMap.get(CHOICENODE);
					set_node_title(choice, CHOICENODE + (num + 1));
					hashMap.put(CHOICENODE, num + 1);

					// 设置基本属性
					MyPanel in = choice.getPanel2();
					MyPanel out = choice.getPanel3();
					MyPanel fault = choice.getPanel4();
					CustomUIObjectConnector.wrap(in);
					CustomUIObjectConnector.wrap(out);
					CustomUIObjectConnector.wrap(fault);

					choice.sinkEvents(Event.ONCLICK);

					choice.setPixel_x(client_Choice.getX());
					choice.setPixel_y(client_Choice.getY());

					panel.add(choice, client_Choice.getX(), client_Choice
							.getY());
					dragController1.makeDraggable(choice);

					// choice节点的初始化设置
					choice.contentPath = client_Choice.contentPath;
					choice.paramInfo_for_configure = client_Choice
							.getParamInfo();
					choice.setTextLabel(choice.ori_TextLabel, "DEL"); // 删除初始化的条件outputport，然后依次添加新的条件outputport
					for (Iterator<Client_ConditionalOutputPort> iterator = client_Choice.conditionalOutputPort
							.iterator(); iterator.hasNext();) {
						Client_ConditionalOutputPort client_ConditionalOutputPort = iterator
								.next();
						choice.addoutput();
						choice.label_array
								.get(choice.outport_count - 1)
								.setText(
										client_ConditionalOutputPort
												.getClient_IfCondition().condition_value);
						choice.IfConditionlist.get(choice.outport_count - 1).condition_value = client_ConditionalOutputPort
								.getClient_IfCondition().condition_value;
					}
					choice.setPanel3(choice.outport_list.get(0)); // 指定默认的out为第一个出口

					service_name_to_id.put(choice.getTitle(), client_Choice
							.getId()); // 每新建一个节点，就把名字和id号关联起来

					// 设置是否为终止点
					if (client_Choice.isIsfinished()) {
						SetFinishCommand setFinishCommand = (SetFinishCommand) choice.setfinish
								.getCommand();
						setFinishCommand.setFinisher();
					}
					// 设置是否为起始点
					if (client_Choice.isIsstart()) {
						SetStartCommand setStartCommand = (SetStartCommand) choice.setstart
								.getCommand();
						setStartCommand.setStarter();
					}

					choice.setHasFinishedConfigure(true);

					Workflowtasknode workFlowNode = choice;
					workflowMap.put(client_Node, workFlowNode);
				} else if (client_Node.getName().equals(FIXEDFORNODE)) {
					Client_FixedNumFor client_FixedNumFor = (Client_FixedNumFor) client_Node;
					FixedFor fixedFor = new FixedFor(client_Node.getName(),
							this, overview);

					fixedFor.setID(client_FixedNumFor.getId());

					int num = hashMap.get(FIXEDFORNODETITLE);
					set_node_title(fixedFor, FIXEDFORNODETITLE + (num + 1));
					hashMap.put(FIXEDFORNODETITLE, num + 1);

					fixedFor.sub_workflow = client_FixedNumFor.getWorkflow(); // 将client_loop中的子工作流数据保存到looptask中
					fixedFor.setServiceConfigure(client_FixedNumFor
							.getServiceConfigure());

					MyPanel in = fixedFor.getPanel2();
					MyPanel out = fixedFor.getPanel3();
					MyPanel fault = fixedFor.getPanel4();
					fixedFor.sinkEvents(Event.ONCLICK);

					fixedFor.setPixel_x(client_FixedNumFor.getX());
					fixedFor.setPixel_y(client_FixedNumFor.getY());

					panel.add(fixedFor, client_FixedNumFor.getX(),
							client_FixedNumFor.getY());
					dragController1.makeDraggable(fixedFor);

					service_name_to_id.put(fixedFor.getTitle(),
							client_FixedNumFor.getId()); // 每新建一个节点，就把名字和id号关联起来

					// 设置是否为终止点
					if (client_FixedNumFor.isIsfinished()) {
						SetFinishCommand setFinishCommand = (SetFinishCommand) fixedFor.setfinish
								.getCommand();
						setFinishCommand.setFinisher();
					}
					// 设置是否为起始点
					if (client_FixedNumFor.isIsstart()) {
						SetStartCommand setStartCommand = (SetStartCommand) fixedFor.setstart
								.getCommand();
						setStartCommand.setStarter();
					}

					fixedFor.setHasFinishedConfigure(true);

					CustomUIObjectConnector.wrap(in);
					CustomUIObjectConnector.wrap(out);
					CustomUIObjectConnector.wrap(fault);

					// 设置基本属性
					Workflowtasknode workFlowNode = fixedFor;
					workflowMap.put(client_Node, workFlowNode); // 为接下来寻找nextnode服务
				} else if (client_Node.getName().equals(NONFIXEDNUMFOR)) {
					Client_NonFixedNumFor client_NonFixedNumFor = (Client_NonFixedNumFor) client_Node;
					NonFixedFor nonFixedFor = new NonFixedFor(client_Node
							.getName(), this, overview);

					nonFixedFor.setID(client_NonFixedNumFor.getId());

					int num = hashMap.get(NONFIXEDNUMFORTITLE);
					set_node_title(nonFixedFor, NONFIXEDNUMFORTITLE + (num + 1));
					hashMap.put(NONFIXEDNUMFORTITLE, num + 1);

					nonFixedFor.sub_workflow = client_NonFixedNumFor
							.getWorkflow(); // 将client_loop中的子工作流数据保存到looptask中
					nonFixedFor.setServiceConfigure(client_NonFixedNumFor
							.getServiceConfigure());

					MyPanel in = nonFixedFor.getPanel2();
					MyPanel out = nonFixedFor.getPanel3();
					MyPanel fault = nonFixedFor.getPanel4();
					nonFixedFor.sinkEvents(Event.ONCLICK);

					nonFixedFor.setPixel_x(client_NonFixedNumFor.getX());
					nonFixedFor.setPixel_y(client_NonFixedNumFor.getY());

					panel.add(nonFixedFor, client_NonFixedNumFor.getX(),
							client_NonFixedNumFor.getY());
					dragController1.makeDraggable(nonFixedFor);

					service_name_to_id.put(nonFixedFor.getTitle(),
							client_NonFixedNumFor.getId()); // 每新建一个节点，就把名字和id号关联起来

					// 设置是否为终止点
					if (client_NonFixedNumFor.isIsfinished()) {
						SetFinishCommand setFinishCommand = (SetFinishCommand) nonFixedFor.setfinish
								.getCommand();
						setFinishCommand.setFinisher();
					}
					// 设置是否为起始点
					if (client_NonFixedNumFor.isIsstart()) {
						SetStartCommand setStartCommand = (SetStartCommand) nonFixedFor.setstart
								.getCommand();
						setStartCommand.setStarter();
					}

					nonFixedFor.setHasFinishedConfigure(true);

					CustomUIObjectConnector.wrap(in);
					CustomUIObjectConnector.wrap(out);
					CustomUIObjectConnector.wrap(fault);

					// 设置基本属性
					Workflowtasknode workFlowNode = nonFixedFor;
					workflowMap.put(client_Node, workFlowNode); // 为接下来寻找nextnode服务
				} else {
					Client_ServiceTask client_ServiceTask = (Client_ServiceTask) client_Node;
					ServiceTask service = new ServiceTask(client_Node.getName(),
							this);

					AbsolutePanel innerPanel = service.getPanel1();
					if(service.getText().equals("BookAirTicketService")){
//						DOM.setStyleAttribute(innerPanel.getElement(), "backgroundColor", "#3161CE");
//						DOM.setStyleAttribute(innerPanel.getElement(), "border", "1px solid black");
						DOM.setStyleAttribute(innerPanel.getElement(), "border", "2px solid #3161CE");
//						innerPanel.addStyleName("ServiceNode-BookAirTicketService");
					}
					else if(service.getText().equals("BookBusTicketService")){
//						DOM.setStyleAttribute(innerPanel.getElement(), "backgroundColor", "#109618");
//						DOM.setStyleAttribute(innerPanel.getElement(), "border", "1px solid black");
						DOM.setStyleAttribute(innerPanel.getElement(), "border", "2px solid #109618");
//						innerPanel.addStyleName("ServiceNode-BookBusTicketService");
					}
					else if(service.getText().equals("BookTrainTicketService")){
//						DOM.setStyleAttribute(innerPanel.getElement(), "backgroundColor", "#63AE00");
//						DOM.setStyleAttribute(innerPanel.getElement(), "border", "1px solid black");
						DOM.setStyleAttribute(innerPanel.getElement(), "border", "2px solid #63AE00");
//						innerPanel.addStyleName("ServiceNode-BookTrainTicketService");
					}
					else if(service.getText().equals("BookRoomService")){
//						DOM.setStyleAttribute(innerPanel.getElement(), "backgroundColor", "#9C419C");
//						DOM.setStyleAttribute(innerPanel.getElement(), "border", "1px solid black");
						DOM.setStyleAttribute(innerPanel.getElement(), "border", "2px solid #9C419C");
//						innerPanel.addStyleName("ServiceNode-BookRoomService");
					}
					else if(service.getText().equals("EmailService")){
//						DOM.setStyleAttribute(innerPanel.getElement(), "backgroundColor", "#D6AE00");
//						DOM.setStyleAttribute(innerPanel.getElement(), "border", "1px solid black");
						DOM.setStyleAttribute(innerPanel.getElement(), "border", "2px solid #D6AE00");
//						innerPanel.addStyleName("ServiceNode-EmailService");
					}
					else if(service.getText().equals("SMS")){
//						DOM.setStyleAttribute(innerPanel.getElement(), "backgroundColor", "#8C8E52");
//						DOM.setStyleAttribute(innerPanel.getElement(), "border", "1px solid black");
						DOM.setStyleAttribute(innerPanel.getElement(), "border", "2px solid #8C8E52");
//						innerPanel.addStyleName("ServiceNode-SMS");
					}
					else{
//						DOM.setStyleAttribute(innerPanel.getElement(), "backgroundColor", "#31619C");
//						DOM.setStyleAttribute(innerPanel.getElement(), "border", "1px solid black");
						DOM.setStyleAttribute(innerPanel.getElement(), "border", "2px solid #31619C");
//						innerPanel.addStyleName("ServiceNode-WeatherForecastServcie");
					}
					
					service.setID(client_ServiceTask.getId());

					int num = hashMap.get(client_Node.getName());
					set_node_title(service, client_Node.getName() + (num + 1));
					hashMap.put(client_Node.getName(), num + 1);

					// ServiceInfo serviceInfo = new ServiceInfo();
					// serviceInfo.getMethodInfoArray().add(client_ServiceTask.getService().getHasConfiguration().getServiceConfigure().getMethodInfo());
					// service.setServiceInfo(serviceInfo);
					service.setServiceInfo(client_ServiceTask.getService()
							.getServiceInfo());
					service.setServiceConfigure(client_ServiceTask.getService()
							.getHasConfiguration().getServiceConfigure());

					// 设置基本属性
					MyPanel in = service.getPanel2();
					MyPanel out = service.getPanel3();
					MyPanel fault = service.getPanel4();
					service.sinkEvents(Event.ONCLICK);

					service.setPixel_x(client_ServiceTask.getX());
					service.setPixel_y(client_ServiceTask.getY());

					// 将服务节点显示出来
					panel.add(service, client_ServiceTask.getX(),
							client_ServiceTask.getY());
					dragController1.makeDraggable(service);

					// 判断是否有时钟
					if (client_ServiceTask.getTimer() != null) {
						AddTimerCommand addTimerCommand = new AddTimerCommand(
								service, this);
						addTimerCommand.addTimer();
						// 设置时间节点的start_time
						service.getTimerNode().setStart_time(
								client_ServiceTask.getTimer().getTimeString());
					}
					// if(client_ServiceTask.isHasTimer()){
					// AddTimerCommand addTimerCommand = new
					// AddTimerCommand(service,this);
					// addTimerCommand.addTimer();
					// //设置时间节点的start_time
					// service.getTimerNode().setStart_time(client_ServiceTask.getTimer().getTimeString());
					// }
					service.current_index = client_ServiceTask
							.getCurrent_method_index();

					service_name_to_id.put(service.getTitle(),
							client_ServiceTask.getId()); // 每新建一个节点，就把名字和id号关联起来

					// 设置是否为终止点
					if (client_ServiceTask.isIsfinished()) {
						SetFinishCommand setFinishCommand = (SetFinishCommand) service.setfinish
								.getCommand();
						setFinishCommand.setFinisher();
					}
					// 设置是否为起始点
					if (client_ServiceTask.isIsstart()) {
						SetStartCommand setStartCommand = (SetStartCommand) service.setstart
								.getCommand();
						setStartCommand.setStarter();
					}

					service.setHasFinishedConfigure(true);

					CustomUIObjectConnector.wrap(in);
					CustomUIObjectConnector.wrap(out);
					CustomUIObjectConnector.wrap(fault);

					Workflowtasknode workFlowNode = service;
					workflowMap.put(client_Node, workFlowNode);
				}
			}

			/* 添加边，并且设置nextnode列表 */
			for (int index = 0; index < node_count; index++) {
				Client_Node client_Node = workflow.getHasNodes().get(index);

				if (client_Node.getName().equals(LOOPNODE)) { // 节点是loopnode时
					Client_Loop client_Loop = (Client_Loop) client_Node;
					LoopTask loop = (LoopTask) workflowMap.get(client_Loop);

					Client_OutputPort client_OutputPort = client_Loop
							.getOutputPort();
					for (Iterator iterator2 = client_OutputPort
							.getHasNextNodes().iterator(); iterator2.hasNext();) {
						// 找出outputport的下一个节点
						Client_Node nextnode = (Client_Node) iterator2.next();
						Workflowtasknode loop2 = workflowMap.get(nextnode);

						CustomUIObjectConnector ui1 = CustomUIObjectConnector
								.getWrapper(loop.getPanel3());
						CustomUIObjectConnector ui2 = CustomUIObjectConnector
								.getWrapper(loop2.getPanel2());
						connect(ui1, ui2, panel); // 在connect中添加线条背景面板,panel指明线是画在编辑区的面板上
					}

				} else if (client_Node.getName().equals(CHOICENODE)) { // 节点是选择几点
					Client_Choice client_Choice = (Client_Choice) client_Node;
					ChoiceTask choice = (ChoiceTask) workflowMap
							.get(client_Choice);
					int outputport_count = 0;
					for (Iterator iterator2 = client_Choice
							.getConditionalOutputPort().iterator(); iterator2
							.hasNext();) {
						Client_ConditionalOutputPort client_ConditionalOutputPort = (Client_ConditionalOutputPort) iterator2
								.next();

						MyPanel conditionalOutputPort = choice.outport_list
								.get(outputport_count++);
						for (Iterator iterator4 = client_ConditionalOutputPort
								.getHasNextNodes().iterator(); iterator4
								.hasNext();) {
							Client_Node nextnode = (Client_Node) iterator4
									.next();
							Workflowtasknode choice2 = workflowMap
									.get(nextnode);
							CustomUIObjectConnector ui1 = CustomUIObjectConnector
									.getWrapper(conditionalOutputPort);
							CustomUIObjectConnector ui2 = CustomUIObjectConnector
									.getWrapper(choice2.getPanel2());
							connect(ui1, ui2, panel); // 在connect中添加线条背景面板,panel指明线是画在编辑区的面板上
						}
					}
				} else if (client_Node.getName().equals(FIXEDFORNODE)) {
					Client_FixedNumFor client_FixedNumFor = (Client_FixedNumFor) client_Node;
					FixedFor fixedFor = (FixedFor) workflowMap
							.get(client_FixedNumFor);

					Client_OutputPort client_OutputPort = client_FixedNumFor
							.getOutputPort();
					for (Iterator iterator2 = client_OutputPort
							.getHasNextNodes().iterator(); iterator2.hasNext();) {
						// 找出outputport的下一个节点
						Client_Node nextnode = (Client_Node) iterator2.next();
						Workflowtasknode fixedFor2 = workflowMap.get(nextnode);

						CustomUIObjectConnector ui1 = CustomUIObjectConnector
								.getWrapper(fixedFor.getPanel3());
						CustomUIObjectConnector ui2 = CustomUIObjectConnector
								.getWrapper(fixedFor2.getPanel2());
						connect(ui1, ui2, panel); // 在connect中添加线条背景面板,panel指明线是画在编辑区的面板上
					}

				} else if (client_Node.getName().equals(NONFIXEDNUMFOR)) {
					Client_NonFixedNumFor client_NonFixedNumFor = (Client_NonFixedNumFor) client_Node;
					NonFixedFor nonFixedFor = (NonFixedFor) workflowMap
							.get(client_NonFixedNumFor);

					Client_OutputPort client_OutputPort = client_NonFixedNumFor
							.getOutputPort();
					for (Iterator iterator2 = client_OutputPort
							.getHasNextNodes().iterator(); iterator2.hasNext();) {
						// 找出outputport的下一个节点
						Client_Node nextnode = (Client_Node) iterator2.next();
						Workflowtasknode nonFixedFor2 = workflowMap
								.get(nextnode);

						CustomUIObjectConnector ui1 = CustomUIObjectConnector
								.getWrapper(nonFixedFor.getPanel3());
						CustomUIObjectConnector ui2 = CustomUIObjectConnector
								.getWrapper(nonFixedFor2.getPanel2());
						connect(ui1, ui2, panel); // 在connect中添加线条背景面板,panel指明线是画在编辑区的面板上
					}
				} else { // 节点是服务节点
					Client_ServiceTask client_ServiceTask = (Client_ServiceTask) client_Node;
					ServiceTask service = (ServiceTask) workflowMap
							.get(client_ServiceTask);

					Client_OutputPort client_OutputPort = client_ServiceTask
							.getOutputPort();
					for (Iterator iterator2 = client_OutputPort
							.getHasNextNodes().iterator(); iterator2.hasNext();) {
						// 找出outputport的下一个节点
						Client_Node nextnode = (Client_Node) iterator2.next();
						Workflowtasknode service2 = workflowMap.get(nextnode);
						CustomUIObjectConnector ui1 = CustomUIObjectConnector
								.getWrapper(service.getPanel3());
						CustomUIObjectConnector ui2 = CustomUIObjectConnector
								.getWrapper(service2.getPanel2());
						connect(ui1, ui2, panel); // 在connect中添加线条背景面板,panel指明线是画在编辑区的面板上
					}
				}
			}

			System.out.println(node_count);
		}
	}
	
	public void display_workflow_while_locked(Client_Workflow client_Workflow){
	
		/* cancel the draggable function of leftpanel*/
		coverWorkflowControlPanel = new AbsolutePanel();
		coverWorkflowControlPanel.setSize("180", "300");
		DOM.setStyleAttribute(coverWorkflowControlPanel.getElement(), "backgroundColor", "white");
		DOM.setStyleAttribute(coverWorkflowControlPanel.getElement(), "filter", "Alpha(opacity=20)");
		leftAbsolutePanel.add(coverWorkflowControlPanel, 0, 0);

		
		panel.clear(); // 清空主面板上显示的控件
		// 清空显示在rootpanel上的timernode
		for (Iterator<TimerNode> iterator = timernodeList.iterator(); iterator
				.hasNext();) {
			TimerNode timerNode = iterator.next();
			timerNode.removeFromParent();
		}
		timernodeList.clear();
		hashMap.clear(); // 清空记录当前各类节点个数的列表

		// 设置各种控制节点的初始个数为0
		hashMap.put(LOOPNODETITLE, 0);
		hashMap.put(CHOICENODE, 0);
		hashMap.put(FIXEDFORNODETITLE, 0);
		hashMap.put(NONFIXEDNUMFORTITLE, 0);
		// 设置各种服务节点的初始个数为0
		for (Iterator iterator = service_info_list.iterator(); iterator
				.hasNext();) {
			String serviceName = (String) iterator.next();
			hashMap.put(serviceName, 0);
		}

		if (client_Workflow != null) {
			Client_Workflow workflow = client_Workflow;
			HashMap<Client_Node, Workflowtasknode> workflowMap = new HashMap<Client_Node, Workflowtasknode>();
			int node_count = client_Workflow.getHasNodes().size();
			for (int index = 0; index < node_count; index++) {
				Client_Node client_Node = workflow.getHasNodes().get(index);
				if (client_Node.getName().equals(LOOPNODE)) {
					Client_Loop client_Loop = (Client_Loop) client_Node;
					LoopTask loop = new LoopTask(client_Node.getName(), this,
							overview);

					loop.setID(client_Loop.getId());

					int num = hashMap.get(LOOPNODETITLE);
					set_node_title(loop, LOOPNODETITLE + (num + 1));
					hashMap.put(LOOPNODETITLE, num + 1);

					loop.sub_workflow = client_Loop.getWorkflow(); // 将client_loop中的子工作流数据保存到looptask中
					loop.setServiceConfigure(client_Loop.getServiceConfigure());
					if (client_Loop.getWhileCondition() != null) {
						String whileCondition = client_Loop.getWhileCondition()
								.getWhileCondition();
						loop.whilecondition = whileCondition;
					}

					MyPanel in = loop.getPanel2();
					MyPanel out = loop.getPanel3();
					MyPanel fault = loop.getPanel4();
					loop.sinkEvents(Event.ONCLICK);
					
					AbsolutePanel innerPanel = loop.getPanel1();
					Image nodeStateImage;
					int nodeState = client_Loop.getNodestate();
					if(nodeState==1){
						nodeStateImage = new Image("img/runnig.PNG");
						innerPanel.add(nodeStateImage,55,75);
					}
					else if(nodeState==2){
						nodeStateImage = new Image("img/success.PNG");
						innerPanel.add(nodeStateImage,55,75);
					}
					else if(nodeState==3){
						nodeStateImage = new Image("img/failed.PNG");
						innerPanel.add(nodeStateImage,55,75);
					}
					
					

					loop.setPixel_x(client_Loop.getX());
					loop.setPixel_y(client_Loop.getY());

					panel.add(loop, client_Loop.getX(), client_Loop.getY());

					CustomUIObjectConnector.wrap(in);
					CustomUIObjectConnector.wrap(out);
					CustomUIObjectConnector.wrap(fault);

					service_name_to_id
							.put(loop.getTitle(), client_Loop.getId()); // 每新建一个节点，就把名字和id号关联起来

					// 设置是否为终止点
					if (client_Loop.isIsfinished()) {
						SetFinishCommand setFinishCommand = (SetFinishCommand) loop.setfinish
								.getCommand();
						setFinishCommand.setFinisher();
					}
					// 设置是否为起始点
					if (client_Loop.isIsstart()) {
						SetStartCommand setStartCommand = (SetStartCommand) loop.setstart
								.getCommand();
						setStartCommand.setStarter();
					}

					loop.setHasFinishedConfigure(true);
					loop
							.setChangeServiceProvider(client_Loop.changeServiceProvider);
					if (client_Loop.changeServiceProvider) {
						loop
								.setSelectServiceNumber(client_Loop.selectServiceNumber);
					}

					// 设置基本属性
					Workflowtasknode workFlowNode = loop;
					workflowMap.put(client_Node, workFlowNode); // 为接下来寻找nextnode服务
				} else if (client_Node.getName().equals(CHOICENODE)) {
					Client_Choice client_Choice = (Client_Choice) client_Node;
					ChoiceTask choice = new ChoiceTask(client_Node.getName(),
							this);

					choice.setID(client_Choice.getId());

					int num = hashMap.get(CHOICENODE);
					set_node_title(choice, CHOICENODE + (num + 1));
					hashMap.put(CHOICENODE, num + 1);

					// 设置基本属性
					MyPanel in = choice.getPanel2();
					MyPanel out = choice.getPanel3();
					MyPanel fault = choice.getPanel4();
					CustomUIObjectConnector.wrap(in);
					CustomUIObjectConnector.wrap(out);
					CustomUIObjectConnector.wrap(fault);

					choice.sinkEvents(Event.ONCLICK);
					
					AbsolutePanel innerPanel = choice.getPanel1();
					Image nodeStateImage;
					int nodeState = client_Choice.getNodestate();
					if(nodeState==1){
						nodeStateImage = new Image("img/runnig.PNG");
						innerPanel.add(nodeStateImage,45,55);
					}
					else if(nodeState==2){
						nodeStateImage = new Image("img/success.PNG");
						innerPanel.add(nodeStateImage,45,55);
					}
					else if(nodeState==3){
						nodeStateImage = new Image("img/failed.PNG");
						innerPanel.add(nodeStateImage,45,55);
					}

					choice.setPixel_x(client_Choice.getX());
					choice.setPixel_y(client_Choice.getY());

					panel.add(choice, client_Choice.getX(), client_Choice
							.getY());

					// choice节点的初始化设置
					choice.contentPath = client_Choice.contentPath;
					choice.paramInfo_for_configure = client_Choice
							.getParamInfo();
					choice.setTextLabel(choice.ori_TextLabel, "DEL"); // 删除初始化的条件outputport，然后依次添加新的条件outputport
					for (Iterator<Client_ConditionalOutputPort> iterator = client_Choice.conditionalOutputPort
							.iterator(); iterator.hasNext();) {
						Client_ConditionalOutputPort client_ConditionalOutputPort = iterator
								.next();
						choice.addoutput();
						choice.label_array
								.get(choice.outport_count - 1)
								.setText(
										client_ConditionalOutputPort
												.getClient_IfCondition().condition_value);
						choice.IfConditionlist.get(choice.outport_count - 1).condition_value = client_ConditionalOutputPort
								.getClient_IfCondition().condition_value;
					}
					choice.setPanel3(choice.outport_list.get(0)); // 指定默认的out为第一个出口

					service_name_to_id.put(choice.getTitle(), client_Choice
							.getId()); // 每新建一个节点，就把名字和id号关联起来

					// 设置是否为终止点
					if (client_Choice.isIsfinished()) {
						SetFinishCommand setFinishCommand = (SetFinishCommand) choice.setfinish
								.getCommand();
						setFinishCommand.setFinisher();
					}
					// 设置是否为起始点
					if (client_Choice.isIsstart()) {
						SetStartCommand setStartCommand = (SetStartCommand) choice.setstart
								.getCommand();
						setStartCommand.setStarter();
					}

					choice.setHasFinishedConfigure(true);

					Workflowtasknode workFlowNode = choice;
					workflowMap.put(client_Node, workFlowNode);
				} else if (client_Node.getName().equals(FIXEDFORNODE)) {
					Client_FixedNumFor client_FixedNumFor = (Client_FixedNumFor) client_Node;
					FixedFor fixedFor = new FixedFor(client_Node.getName(),
							this, overview);

					fixedFor.setID(client_FixedNumFor.getId());

					int num = hashMap.get(FIXEDFORNODETITLE);
					set_node_title(fixedFor, FIXEDFORNODETITLE + (num + 1));
					hashMap.put(FIXEDFORNODETITLE, num + 1);

					fixedFor.sub_workflow = client_FixedNumFor.getWorkflow(); // 将client_loop中的子工作流数据保存到looptask中
					fixedFor.setServiceConfigure(client_FixedNumFor
							.getServiceConfigure());

					MyPanel in = fixedFor.getPanel2();
					MyPanel out = fixedFor.getPanel3();
					MyPanel fault = fixedFor.getPanel4();
					fixedFor.sinkEvents(Event.ONCLICK);

					fixedFor.setPixel_x(client_FixedNumFor.getX());
					fixedFor.setPixel_y(client_FixedNumFor.getY());

					panel.add(fixedFor, client_FixedNumFor.getX(),
							client_FixedNumFor.getY());

					service_name_to_id.put(fixedFor.getTitle(),
							client_FixedNumFor.getId()); // 每新建一个节点，就把名字和id号关联起来

					// 设置是否为终止点
					if (client_FixedNumFor.isIsfinished()) {
						SetFinishCommand setFinishCommand = (SetFinishCommand) fixedFor.setfinish
								.getCommand();
						setFinishCommand.setFinisher();
					}
					// 设置是否为起始点
					if (client_FixedNumFor.isIsstart()) {
						SetStartCommand setStartCommand = (SetStartCommand) fixedFor.setstart
								.getCommand();
						setStartCommand.setStarter();
					}

					fixedFor.setHasFinishedConfigure(true);

					CustomUIObjectConnector.wrap(in);
					CustomUIObjectConnector.wrap(out);
					CustomUIObjectConnector.wrap(fault);

					// 设置基本属性
					Workflowtasknode workFlowNode = fixedFor;
					workflowMap.put(client_Node, workFlowNode); // 为接下来寻找nextnode服务
				} else if (client_Node.getName().equals(NONFIXEDNUMFOR)) {
					Client_NonFixedNumFor client_NonFixedNumFor = (Client_NonFixedNumFor) client_Node;
					NonFixedFor nonFixedFor = new NonFixedFor(client_Node
							.getName(), this, overview);

					nonFixedFor.setID(client_NonFixedNumFor.getId());

					int num = hashMap.get(NONFIXEDNUMFORTITLE);
					set_node_title(nonFixedFor, NONFIXEDNUMFORTITLE + (num + 1));
					hashMap.put(NONFIXEDNUMFORTITLE, num + 1);

					nonFixedFor.sub_workflow = client_NonFixedNumFor
							.getWorkflow(); // 将client_loop中的子工作流数据保存到looptask中
					nonFixedFor.setServiceConfigure(client_NonFixedNumFor
							.getServiceConfigure());

					MyPanel in = nonFixedFor.getPanel2();
					MyPanel out = nonFixedFor.getPanel3();
					MyPanel fault = nonFixedFor.getPanel4();
					nonFixedFor.sinkEvents(Event.ONCLICK);

					nonFixedFor.setPixel_x(client_NonFixedNumFor.getX());
					nonFixedFor.setPixel_y(client_NonFixedNumFor.getY());

					panel.add(nonFixedFor, client_NonFixedNumFor.getX(),
							client_NonFixedNumFor.getY());

					service_name_to_id.put(nonFixedFor.getTitle(),
							client_NonFixedNumFor.getId()); // 每新建一个节点，就把名字和id号关联起来

					// 设置是否为终止点
					if (client_NonFixedNumFor.isIsfinished()) {
						SetFinishCommand setFinishCommand = (SetFinishCommand) nonFixedFor.setfinish
								.getCommand();
						setFinishCommand.setFinisher();
					}
					// 设置是否为起始点
					if (client_NonFixedNumFor.isIsstart()) {
						SetStartCommand setStartCommand = (SetStartCommand) nonFixedFor.setstart
								.getCommand();
						setStartCommand.setStarter();
					}

					nonFixedFor.setHasFinishedConfigure(true);

					CustomUIObjectConnector.wrap(in);
					CustomUIObjectConnector.wrap(out);
					CustomUIObjectConnector.wrap(fault);

					// 设置基本属性
					Workflowtasknode workFlowNode = nonFixedFor;
					workflowMap.put(client_Node, workFlowNode); // 为接下来寻找nextnode服务
				} else {
					Client_ServiceTask client_ServiceTask = (Client_ServiceTask) client_Node;
					ServiceTask service = new ServiceTask(client_Node.getName(),
							this);

					AbsolutePanel innerPanel = service.getPanel1();
					if(service.getText().equals("BookAirTicketService")){
//						DOM.setStyleAttribute(innerPanel.getElement(), "backgroundColor", "#3161CE");
//						DOM.setStyleAttribute(innerPanel.getElement(), "border", "1px solid black");
						DOM.setStyleAttribute(innerPanel.getElement(), "border", "2px solid #3161CE");
//						innerPanel.addStyleName("ServiceNode-BookAirTicketService");
					}
					else if(service.getText().equals("BookBusTicketService")){
//						DOM.setStyleAttribute(innerPanel.getElement(), "backgroundColor", "#109618");
//						DOM.setStyleAttribute(innerPanel.getElement(), "border", "1px solid black");
						DOM.setStyleAttribute(innerPanel.getElement(), "border", "2px solid #109618");
//						innerPanel.addStyleName("ServiceNode-BookBusTicketService");
					}
					else if(service.getText().equals("BookTrainTicketService")){
//						DOM.setStyleAttribute(innerPanel.getElement(), "backgroundColor", "#63AE00");
//						DOM.setStyleAttribute(innerPanel.getElement(), "border", "1px solid black");
						DOM.setStyleAttribute(innerPanel.getElement(), "border", "2px solid #63AE00");
//						innerPanel.addStyleName("ServiceNode-BookTrainTicketService");
					}
					else if(service.getText().equals("BookRoomService")){
//						DOM.setStyleAttribute(innerPanel.getElement(), "backgroundColor", "#9C419C");
//						DOM.setStyleAttribute(innerPanel.getElement(), "border", "1px solid black");
						DOM.setStyleAttribute(innerPanel.getElement(), "border", "2px solid #9C419C");
//						innerPanel.addStyleName("ServiceNode-BookRoomService");
					}
					else if(service.getText().equals("EmailService")){
//						DOM.setStyleAttribute(innerPanel.getElement(), "backgroundColor", "#D6AE00");
//						DOM.setStyleAttribute(innerPanel.getElement(), "border", "1px solid black");
						DOM.setStyleAttribute(innerPanel.getElement(), "border", "2px solid #D6AE00");
//						innerPanel.addStyleName("ServiceNode-EmailService");
					}
					else if(service.getText().equals("SMS")){
//						DOM.setStyleAttribute(innerPanel.getElement(), "backgroundColor", "#8C8E52");
//						DOM.setStyleAttribute(innerPanel.getElement(), "border", "1px solid black");
						DOM.setStyleAttribute(innerPanel.getElement(), "border", "2px solid #8C8E52");
//						innerPanel.addStyleName("ServiceNode-SMS");
					}
					else{
//						DOM.setStyleAttribute(innerPanel.getElement(), "backgroundColor", "#31619C");
//						DOM.setStyleAttribute(innerPanel.getElement(), "border", "1px solid black");
						DOM.setStyleAttribute(innerPanel.getElement(), "border", "2px solid #31619C");
//						innerPanel.addStyleName("ServiceNode-WeatherForecastServcie");
					}
					
					service.setID(client_ServiceTask.getId());

					int num = hashMap.get(client_Node.getName());
					set_node_title(service, client_Node.getName() + (num + 1));
					hashMap.put(client_Node.getName(), num + 1);

					// ServiceInfo serviceInfo = new ServiceInfo();
					// serviceInfo.getMethodInfoArray().add(client_ServiceTask.getService().getHasConfiguration().getServiceConfigure().getMethodInfo());
					// service.setServiceInfo(serviceInfo);
					service.setServiceInfo(client_ServiceTask.getService()
							.getServiceInfo());
					service.setServiceConfigure(client_ServiceTask.getService()
							.getHasConfiguration().getServiceConfigure());

					// 设置基本属性
					MyPanel in = service.getPanel2();
					MyPanel out = service.getPanel3();
					MyPanel fault = service.getPanel4();
					service.sinkEvents(Event.ONCLICK);
					
					Image nodeStateImage;
					int nodeState = client_ServiceTask.getNodestate();
					if(nodeState==1){
						nodeStateImage = new Image("img/runnig.PNG");
						innerPanel.add(nodeStateImage,75,25);
					}
					else if(nodeState==2){
						nodeStateImage = new Image("img/success.PNG");
						innerPanel.add(nodeStateImage,75,25);
					}
					else if(nodeState==3){
						nodeStateImage = new Image("img/failed.PNG");
						innerPanel.add(nodeStateImage,75,25);
					}

					service.setPixel_x(client_ServiceTask.getX());
					service.setPixel_y(client_ServiceTask.getY());

					// 将服务节点显示出来
					panel.add(service, client_ServiceTask.getX(),
							client_ServiceTask.getY());

					// 判断是否有时钟
					if (client_ServiceTask.getTimer() != null) {
						AddTimerCommand addTimerCommand = new AddTimerCommand(
								service, this);
						addTimerCommand.addTimer();
						// 设置时间节点的start_time
						service.getTimerNode().setStart_time(
								client_ServiceTask.getTimer().getTimeString());
					}
					// if(client_ServiceTask.isHasTimer()){
					// AddTimerCommand addTimerCommand = new
					// AddTimerCommand(service,this);
					// addTimerCommand.addTimer();
					// //设置时间节点的start_time
					// service.getTimerNode().setStart_time(client_ServiceTask.getTimer().getTimeString());
					// }
					service.current_index = client_ServiceTask
							.getCurrent_method_index();

					service_name_to_id.put(service.getTitle(),
							client_ServiceTask.getId()); // 每新建一个节点，就把名字和id号关联起来

					// 设置是否为终止点
					if (client_ServiceTask.isIsfinished()) {
						SetFinishCommand setFinishCommand = (SetFinishCommand) service.setfinish
								.getCommand();
						setFinishCommand.setFinisher();
					}
					// 设置是否为起始点
					if (client_ServiceTask.isIsstart()) {
						SetStartCommand setStartCommand = (SetStartCommand) service.setstart
								.getCommand();
						setStartCommand.setStarter();
					}

					service.setHasFinishedConfigure(true);

					CustomUIObjectConnector.wrap(in);
					CustomUIObjectConnector.wrap(out);
					CustomUIObjectConnector.wrap(fault);

					Workflowtasknode workFlowNode = service;
					workflowMap.put(client_Node, workFlowNode);
				}
			}

			/* 添加边，并且设置nextnode列表 */
			for (int index = 0; index < node_count; index++) {
				Client_Node client_Node = workflow.getHasNodes().get(index);

				if (client_Node.getName().equals(LOOPNODE)) { // 节点是loopnode时
					Client_Loop client_Loop = (Client_Loop) client_Node;
					LoopTask loop = (LoopTask) workflowMap.get(client_Loop);

					Client_OutputPort client_OutputPort = client_Loop
							.getOutputPort();
					for (Iterator iterator2 = client_OutputPort
							.getHasNextNodes().iterator(); iterator2.hasNext();) {
						// 找出outputport的下一个节点
						Client_Node nextnode = (Client_Node) iterator2.next();
						Workflowtasknode loop2 = workflowMap.get(nextnode);

						CustomUIObjectConnector ui1 = CustomUIObjectConnector
								.getWrapper(loop.getPanel3());
						CustomUIObjectConnector ui2 = CustomUIObjectConnector
								.getWrapper(loop2.getPanel2());
						connect(ui1, ui2, panel); // 在connect中添加线条背景面板,panel指明线是画在编辑区的面板上
					}

				} else if (client_Node.getName().equals(CHOICENODE)) { // 节点是选择几点
					Client_Choice client_Choice = (Client_Choice) client_Node;
					ChoiceTask choice = (ChoiceTask) workflowMap
							.get(client_Choice);
					int outputport_count = 0;
					for (Iterator iterator2 = client_Choice
							.getConditionalOutputPort().iterator(); iterator2
							.hasNext();) {
						Client_ConditionalOutputPort client_ConditionalOutputPort = (Client_ConditionalOutputPort) iterator2
								.next();

						MyPanel conditionalOutputPort = choice.outport_list
								.get(outputport_count++);
						for (Iterator iterator4 = client_ConditionalOutputPort
								.getHasNextNodes().iterator(); iterator4
								.hasNext();) {
							Client_Node nextnode = (Client_Node) iterator4
									.next();
							Workflowtasknode choice2 = workflowMap
									.get(nextnode);
							CustomUIObjectConnector ui1 = CustomUIObjectConnector
									.getWrapper(conditionalOutputPort);
							CustomUIObjectConnector ui2 = CustomUIObjectConnector
									.getWrapper(choice2.getPanel2());
							connect(ui1, ui2, panel); // 在connect中添加线条背景面板,panel指明线是画在编辑区的面板上
						}
					}
				} else if (client_Node.getName().equals(FIXEDFORNODE)) {
					Client_FixedNumFor client_FixedNumFor = (Client_FixedNumFor) client_Node;
					FixedFor fixedFor = (FixedFor) workflowMap
							.get(client_FixedNumFor);

					Client_OutputPort client_OutputPort = client_FixedNumFor
							.getOutputPort();
					for (Iterator iterator2 = client_OutputPort
							.getHasNextNodes().iterator(); iterator2.hasNext();) {
						// 找出outputport的下一个节点
						Client_Node nextnode = (Client_Node) iterator2.next();
						Workflowtasknode fixedFor2 = workflowMap.get(nextnode);

						CustomUIObjectConnector ui1 = CustomUIObjectConnector
								.getWrapper(fixedFor.getPanel3());
						CustomUIObjectConnector ui2 = CustomUIObjectConnector
								.getWrapper(fixedFor2.getPanel2());
						connect(ui1, ui2, panel); // 在connect中添加线条背景面板,panel指明线是画在编辑区的面板上
					}

				} else if (client_Node.getName().equals(NONFIXEDNUMFOR)) {
					Client_NonFixedNumFor client_NonFixedNumFor = (Client_NonFixedNumFor) client_Node;
					NonFixedFor nonFixedFor = (NonFixedFor) workflowMap
							.get(client_NonFixedNumFor);

					Client_OutputPort client_OutputPort = client_NonFixedNumFor
							.getOutputPort();
					for (Iterator iterator2 = client_OutputPort
							.getHasNextNodes().iterator(); iterator2.hasNext();) {
						// 找出outputport的下一个节点
						Client_Node nextnode = (Client_Node) iterator2.next();
						Workflowtasknode nonFixedFor2 = workflowMap
								.get(nextnode);

						CustomUIObjectConnector ui1 = CustomUIObjectConnector
								.getWrapper(nonFixedFor.getPanel3());
						CustomUIObjectConnector ui2 = CustomUIObjectConnector
								.getWrapper(nonFixedFor2.getPanel2());
						connect(ui1, ui2, panel); // 在connect中添加线条背景面板,panel指明线是画在编辑区的面板上
					}
				} else { // 节点是服务节点
					Client_ServiceTask client_ServiceTask = (Client_ServiceTask) client_Node;
					ServiceTask service = (ServiceTask) workflowMap
							.get(client_ServiceTask);

					Client_OutputPort client_OutputPort = client_ServiceTask
							.getOutputPort();
					for (Iterator iterator2 = client_OutputPort
							.getHasNextNodes().iterator(); iterator2.hasNext();) {
						// 找出outputport的下一个节点
						Client_Node nextnode = (Client_Node) iterator2.next();
						Workflowtasknode service2 = workflowMap.get(nextnode);
						CustomUIObjectConnector ui1 = CustomUIObjectConnector
								.getWrapper(service.getPanel3());
						CustomUIObjectConnector ui2 = CustomUIObjectConnector
								.getWrapper(service2.getPanel2());
						connect(ui1, ui2, panel); // 在connect中添加线条背景面板,panel指明线是画在编辑区的面板上
					}
				}
			}

			System.out.println(node_count);
		}
		
		/* cancel the draggable function of edit region*/
		coverWorkflowEditRegionLayer = new AbsolutePanel();
		coverWorkflowEditRegionLayer.setSize("400", "300");
		DOM.setStyleAttribute(coverWorkflowEditRegionLayer.getElement(), "backgroundColor", "white");
		DOM.setStyleAttribute(coverWorkflowEditRegionLayer.getElement(), "filter", "Alpha(opacity=20)");
		panel.add(coverWorkflowEditRegionLayer, 0, 0);
	}
}
