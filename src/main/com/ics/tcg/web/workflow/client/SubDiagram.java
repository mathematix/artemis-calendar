package com.ics.tcg.web.workflow.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;
import com.ics.tcg.web.user.client.panels.Panel_Overview;
import com.ics.tcg.web.workflow.client.command.AddTimerCommand;
import com.ics.tcg.web.workflow.client.command.SetFinishCommand;
import com.ics.tcg.web.workflow.client.command.SetStartCommand;
import com.ics.tcg.web.workflow.client.common.customline.CustomConnection;
import com.ics.tcg.web.workflow.client.common.customline.CustomUIObjectConnector;
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
import com.ics.tcg.web.workflow.client.service.ParamInfo;
import com.ics.tcg.web.workflow.client.service.ServiceInfo;
import com.ics.tcg.web.workflow.client.task.ChoiceTask;
import com.ics.tcg.web.workflow.client.task.FixedFor;
import com.ics.tcg.web.workflow.client.task.LoopTask;
import com.ics.tcg.web.workflow.client.task.NonFixedFor;
import com.ics.tcg.web.workflow.client.task.ServiceTask;
import com.ics.tcg.web.workflow.client.task.TimerNode;
import com.ics.tcg.web.workflow.client.task.Workflowtasknode;

@SuppressWarnings( { "unchecked", "unused" })
public class SubDiagram extends DiagramBuilder {

	public LoopTask loopTask;
	public Client_Workflow sub_workflow;
	private boolean save_state;

	// public Workflowtasknode lastNode;

	List<ServiceTask> list = new ArrayList<ServiceTask>();

	@Override
	public List<ServiceTask> getList() {
		return list;
	}

	@Override
	public void setList(List<ServiceTask> list) {
		this.list = list;
	}

	public SubDiagram(LoopTask l, final WorkflowEditModule gwtDiagramsExample,
			final Panel_Overview overview) {
		super(gwtDiagramsExample, overview);

		/** set panel */
		getArea().setSize(
				Integer.toString(overview.workflowpanel.workflowPanel_inner
						.getOffsetWidth() - 30),
				Integer.toString(overview.workflowpanel.workflowPanel_inner
						.getOffsetHeight() - 70));
		getPanel().setSize("1200", "800");
		scrollPanel.setSize(Integer
				.toString(overview.workflowpanel.workflowPanel_inner
						.getOffsetWidth() - 210), Integer
				.toString(overview.workflowpanel.workflowPanel_inner
						.getOffsetHeight() - 70));

		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {

				getArea()
						.setSize(
								Integer
										.toString(overview.workflowpanel.workflowPanel_inner
												.getOffsetWidth() - 30),
								Integer
										.toString(overview.workflowpanel.workflowPanel_inner
												.getOffsetHeight() - 70));
				scrollPanel.setSize(Integer
						.toString(overview.workflowpanel.workflowPanel_inner
								.getOffsetWidth() - 210), Integer
						.toString(overview.workflowpanel.workflowPanel_inner
								.getOffsetHeight() - 70));

			}
		});

		/** resume to normal */
		loopTask = l;
		// create_save_button();

		// display_workflow(loopTask.sub_workflow);
	}

	// 重载set_title函数，使得之工作流中的节点，能够显示出属于哪个容器
	@Override
	protected void set_node_title(Workflowtasknode w, String text) {
		w.setTitle(loopTask.getTitle() + "." + text);
	}

	// public void create_save_button(){
	// Button save_button = new Button("Save");
	// save_button.addClickListener(new ClickListener(){
	// public void onClick(Widget sender){
	// save_workflow_in_workflowmode();
	// loopTask.sub_workflow = sub_workflow;
	// System.out.println("sub_workflow_be_invoke");
	// }
	// });
	// leftAbsolutePanel.add(save_button,0,0);
	// }

	public void create_button() {

	}

	@Override
	protected void createConnector(Widget widget, AbsolutePanel panel) {
		Workflowtasknode proxy = (Workflowtasknode) widget;

		final Workflowtasknode l;
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
		l.setBelongToSubWorkflow(true);

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

	@Override
	public AbsolutePanel getPanel() {
		return panel;
	}

	@Override
	public Client_Workflow save_workflow_in_workflowmode() {

		check = true;

		sub_workflow = new Client_Workflow();
		sub_workflow.getHasNodes();
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
					loop.setComment(null); // 暂时没有
					loop.setWorkflow(loopTask.sub_workflow);
					// transfer the service configure from looptask to
					// client_loop
					loop.setServiceConfigure(loopTask.getServiceConfigure());
					loop.setIsstart(loopTask.isIsstart());
					loop.setIsfinished(loopTask.isIsfinished());

					// 在工作流类中，设置起始节点
					if (loop.isIsstart()) {
						sub_workflow.setHasFirstNode(loop);
						starter_count++;
						if (starter_count > 1) {
							System.out.println("弹出一个对话框，起始节点多于一个，强行结束保存过程");
							check = Window
									.confirm("Just one end-node,force to end and return to null, sure?");
							return null;
						}
					}
					if (loop.isIsfinished()) {
						sub_workflow.setHasLastNode(loop);
						ender_count++;
						if (ender_count > 1) {
							System.out.println("弹出一个对话框，终止节点多于一个，强行结束保存过程");
							check = Window
									.confirm("Just one endnode, force to end and return to null, sure?");
							return null;
						}
					}

					if (!loopTask.hasFinishedConfigure)
						allNodesHasBeenSave = false;

					sub_workflow.getHasNodes().add(loop);
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
						Client_IfCondition client_IfCondition = choiceTask.IfConditionlist
								.get(ifcondition_index++);
						conditionalOutputPort
								.setClient_IfCondition(client_IfCondition);
						choice.conditionalOutputPort.add(conditionalOutputPort);
					}

					choice.setComment(null); // 暂时没有
					choice.setContentPath(choiceTask.contentPath);
					choice.setParamInfo(choiceTask.paramInfo_for_configure);
					choice.setIsstart(choiceTask.isIsstart());
					choice.setIsfinished(choiceTask.isIsfinished());

					// 在工作流类中，设置起始节点
					if (choice.isIsstart()) {
						sub_workflow.setHasFirstNode(choice);
						starter_count++;
						if (starter_count > 1) {
							System.out.println("弹出一个对话框，起始节点多于一个，强行结束保存过程");
							check = Window
									.confirm("Just one begin-node, force to end and return to null, sure?");
							return null;
						}
					}
					if (choice.isIsfinished()) {
						sub_workflow.setHasLastNode(choice);
						ender_count++;
						if (ender_count > 1) {
							System.out.println("弹出一个对话框，终止节点多于一个，强行结束保存过程");
							check = Window
									.confirm("Just one end-node, force to end and return to null, sure?");
							return null;
						}
					}

					if (!choiceTask.hasFinishedConfigure)
						allNodesHasBeenSave = false;

					sub_workflow.getHasNodes().add(choice);
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
					fixedNumFor.setWorkflow(fixedForTask.sub_workflow);
					fixedNumFor.setServiceConfigure(fixedForTask
							.getServiceConfigure());
					fixedNumFor.setChangeServiceProvider(fixedForTask
							.isChangeServiceProvider());
					fixedNumFor.setIsstart(fixedForTask.isIsstart());
					fixedNumFor.setIsfinished(fixedForTask.isIsfinished());

					// 在工作流类中，设置起始节点
					if (fixedNumFor.isIsstart()) {
						sub_workflow.setHasFirstNode(fixedNumFor);
						starter_count++;
						if (starter_count > 1) {
							System.out.println("弹出一个对话框，起始节点多于一个，强行结束保存过程");
							check = Window
									.confirm("Just one begin-node, force to end and return to null, sure?");
							return null;
						}
					}
					if (fixedNumFor.isIsfinished()) {
						sub_workflow.setHasLastNode(fixedNumFor);
						ender_count++;
						if (ender_count > 1) {
							System.out.println("弹出一个对话框，终止节点多于一个，强行结束保存过程");
							check = Window
									.confirm("Just one begin-node, force to end and return to null, sure?");
							return null;
						}
					}

					if (!fixedForTask.hasFinishedConfigure)
						allNodesHasBeenSave = false;

					sub_workflow.getHasNodes().add(fixedNumFor);
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
					nonFixedNumFor.setWorkflow(nonFixedForTask.sub_workflow);
					nonFixedNumFor.setServiceConfigure(nonFixedForTask
							.getServiceConfigure());
					nonFixedNumFor.setChangeServiceProvider(nonFixedForTask
							.isChangeServiceProvider());
					nonFixedNumFor.setIsstart(nonFixedForTask.isIsstart());
					nonFixedNumFor
							.setIsfinished(nonFixedForTask.isIsfinished());

					// 在工作流类中，设置起始节点
					if (nonFixedNumFor.isIsstart()) {
						sub_workflow.setHasFirstNode(nonFixedNumFor);
						starter_count++;
						if (starter_count > 1) {
							System.out.println("弹出一个对话框，起始节点多于一个，强行结束保存过程");
							check = Window
									.confirm("Just one begin-node, force to end and return to null, sure?");
							return null;
						}
					}
					if (nonFixedNumFor.isIsfinished()) {
						sub_workflow.setHasLastNode(nonFixedNumFor);
						ender_count++;
						if (ender_count > 1) {
							System.out.println("弹出一个对话框，终止节点多于一个，强行结束保存过程");
							check = Window
									.confirm("Just one begin-node, force to end and return to null, sure?");
							return null;
						}
					}

					if (!nonFixedForTask.hasFinishedConfigure)
						allNodesHasBeenSave = false;

					sub_workflow.getHasNodes().add(nonFixedNumFor);
					Client_Node workFlowNode = nonFixedNumFor;
					workflowMap.put(workflowtasknode, workFlowNode);
				} else {
					ServiceTask simpleTask = (ServiceTask) workflowtasknode;
					Client_ServiceTask service = new Client_ServiceTask();

					// 设置基本属性
					service.setId(simpleTask.getID());
					service.setX(simpleTask.getPixel_x());
					service.setY(simpleTask.getPixel_y());
					service.setName(simpleTask.getText());

					// 设置服务的基本信息，包括服务配置信息，服务描述，服务的wsdl路径
					Client_Service client_Service = new Client_Service();
					// client_Service.setServiceName(simpleTask.getText());
					Client_Configuration client_Configuration = new Client_Configuration();
					client_Configuration.setServiceConfigure(simpleTask
							.getServiceConfigure());
					client_Service.setHasConfiguration(client_Configuration);
					client_Service.setHasDesciption(null); // 暂时不设置
					client_Service.setHasQosRequirement(null); // 暂时不设置
					client_Service.setServiceInfo(simpleTask.getServiceInfo());
					service.setService(client_Service); // 将服务配置，和服务内容都保存到servicetask当中
					// service.setServiceInfo(simpleTask.getServiceInfo());

					if (simpleTask.hasTimer) {
						Client_Timer client_Timer = new Client_Timer();
						client_Timer.setId(Integer.toString(client_Timer
								.hashCode()));
						client_Timer.setTimeString(simpleTask.getTimerNode()
								.getStart_time());
						service.setTimer(client_Timer);
					}

					Client_InputPort inputPort = new Client_InputPort();
					inputPort.setId(Integer.toString(simpleTask.getPanel2()
							.hashCode()));
					inputPort.setBelongTo(service);
					Client_OutputPort outputPort = new Client_OutputPort();
					outputPort.setId(Integer.toString(simpleTask.getPanel3()
							.hashCode()));
					outputPort.setBelongTo(service);
					Client_FaultOutputPort faultOutputPort = new Client_FaultOutputPort();
					faultOutputPort.setId(Integer.toString(simpleTask
							.getPanel4().hashCode()));
					faultOutputPort.setBelongTo(service);
					service.setInputPort(inputPort);
					service.setOutputPort(outputPort);
					service.setFaultOutputPort(faultOutputPort);

					service.setComment(null); // 暂时没有
					service.setCurrent_method_index(simpleTask.current_index);
					service.setIsstart(simpleTask.isIsstart());
					service.setIsfinished(simpleTask.isIsfinished());

					// 在工作流类中，设置起始节点
					if (service.isIsstart()) {
						sub_workflow.setHasFirstNode(service);
						starter_count++;
						if (starter_count > 1) {
							System.out.println("弹出一个对话框，起始节点多于一个，强行结束保存过程");
							check = Window
									.confirm("Just one begin-node, force to end and return to null, sure?");
							return null;
						}
					}
					if (service.isIsfinished()) {
						sub_workflow.setHasLastNode(service);
						ender_count++;
						if (ender_count > 1) {
							System.out.println("弹出一个对话框，终止节点多于一个，强行结束保存过程");
							check = Window
									.confirm("Just one begin-node, force to end and return to null, sure?");
							return null;
						}
					}

					if (!simpleTask.hasFinishedConfigure)
						allNodesHasBeenSave = false;

					sub_workflow.getHasNodes().add(service);
					Client_Node workFlowNode = service;
					workflowMap.put(workflowtasknode, workFlowNode);
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
						Client_Node loop2 = workflowMap.get(workflowtasknode2);
						choice.getInputPort().getBeforeNodes().add(loop2);
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
					ServiceTask simpleTask = (ServiceTask) workflowtasknode;
					Client_ServiceTask service = (Client_ServiceTask) workflowMap
							.get(simpleTask);

					ArrayList<Workflowtasknode> backTaskList = new ArrayList<Workflowtasknode>();
					MyPanel outport = simpleTask.getPanel3();
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
					MyPanel inport = simpleTask.getPanel2();
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
						Client_Node loop2 = workflowMap.get(workflowtasknode2);
						service.getInputPort().getBeforeNodes().add(loop2);
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
					.confirm("At learst one node is not configured, force to end and return to null, sure?");
			return null;
		}

		// 检查最后一个节点的输入设置是否包含looptask中的变量，如果没有，则说明配置不合法
		if (lastNode != null) {
			// find the last node of client_workflow
			Client_Node last_client_node = workflowMap.get(lastNode);
			boolean valid = check_ParaminfoWeatherHasVariableInput(
					last_client_node, loopTask);
			// if the looptask is not a general loop and timeintervalloop, we
			// must check whether the last node of sub workflow contain
			// loopVariable
			// if(!loopTask.getText().equals("Loop")&&!loopTask.getText().equals("TLoop")){
			// SimpleTask simpleTask = (SimpleTask)lastNode;
			// for(Iterator<ParamInfo> iterator =
			// simpleTask.getServiceConfigure().getMethodInfo().getInputInfo().getParamInfosArray().iterator();iterator.hasNext();){
			// ParamInfo paramInfo = iterator.next();
			// valid =
			// valid||simpleTask.check_ParaminfoWeatherHasVariableInput(paramInfo,lastNode,loopTask);
			// }
			// }
			// //if the last node is a loop type ,we must check the last node of
			// the looptask
			// else{
			//				
			// }
			if (valid == false) {
				System.out.println("弹出一个对话框，终止节点未包含循环变量，强行结束保存过程");
				check = Window
						.confirm("end-node contains no loop value, force to end and return to null, sure?");
				return null;
			}
		} else {
			System.out.println("弹出一个对话框，终止节点未设置，强行结束保存过程");
			check = Window
					.confirm("no end-node, force to end and return to null, sure?");
			return null;
		}

		Client_Node firstNode = sub_workflow.getHasFirstNode();
		Client_InputPort client_InputPort = firstNode.getInputPort();
		if (client_InputPort.getBeforeNodes() != null
				&& client_InputPort.getBeforeNodes().size() > 0) {
			System.out.println("弹出一个对话框，起始节点前还有节点，强行结束保存过程");
			check = Window
					.confirm("Node before begin-node, force to end and return to null, sure?");
			return null;
		}

		Client_Node lastNode = sub_workflow.getHasLastNode();
		if (lastNode.getName().equals(FIXEDFORNODE)) {
			Client_FixedNumFor client_FixedNumFor = (Client_FixedNumFor) lastNode;
			Client_OutputPort client_OutputPort = client_FixedNumFor
					.getOutputPort();
			if (client_OutputPort.getHasNextNodes() != null
					&& client_OutputPort.getHasNextNodes().size() > 0) {
				System.out.println("弹出一个对话框，终止节点节点后还有节点，强行结束保存过程");
				check = Window
						.confirm("Node after end-node, force to end and return to null, sure?");
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
						.confirm("Node after end-node, force to end and return to null, sure?");
				return null;
			}
		} else if (lastNode.getName().equals(LOOPNODE)) {
			Client_Loop client_Loop = (Client_Loop) lastNode;
			Client_OutputPort client_OutputPort = client_Loop.getOutputPort();
			if (client_OutputPort.getHasNextNodes() != null
					&& client_OutputPort.getHasNextNodes().size() > 0) {
				System.out.println("弹出一个对话框，终止节点节点后还有节点，强行结束保存过程");
				check = Window
						.confirm("Node after end-node, force to end and return to null, sure?");
				return null;
			}
		} else if (lastNode.getName().equals(CHOICENODE)) {
			Client_Choice client_Choice = (Client_Choice) lastNode;
			for (Iterator<Client_ConditionalOutputPort> iterator = client_Choice
					.getConditionalOutputPort().iterator(); iterator.hasNext();) {
				Client_ConditionalOutputPort client_ConditionalOutputPort = iterator
						.next();
				if (client_ConditionalOutputPort.getHasNextNodes() != null
						&& client_ConditionalOutputPort.getHasNextNodes()
								.size() > 0) {
					System.out.println("弹出一个对话框，终止节点节点后还有节点，强行结束保存过程");
					check = Window
							.confirm("Node after end-node, force to end and return to null, sure?");
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
						.confirm("Node after end-node, force to end and return to null, sure?");
				return null;
			}
		}

		loopTask.sub_workflow = sub_workflow;

		// the whole workflow is legal, then keep the serviceConfigure of the
		// last node in the loopTask
		loopTask.setServiceConfigure();
		System.out.println(node_count);

		return sub_workflow;

	}

	@Override
	public void display_workflow(Client_Workflow client_Workflow) {

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

					CustomUIObjectConnector.wrap(in);
					CustomUIObjectConnector.wrap(out);
					CustomUIObjectConnector.wrap(fault);

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

					service_name_to_id.put(choice.getTitle(), client_Choice
							.getId()); // 每新建一个节点，就把名字和id号关联起来

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

					fixedFor.sub_workflow = client_FixedNumFor.getWorkflow();
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
							.getWorkflow();
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

	public boolean check_ParaminfoWeatherHasVariableInput(
			Client_Node client_Node, LoopTask loopTask) {
		if (client_Node.getName().equals("Loop")
				|| client_Node.getName().equals("TLoop")) {
			Client_Loop client_Loop = (Client_Loop) client_Node;
			if (client_Loop.getWorkflow() != null) {
				return check_ParaminfoWeatherHasVariableInput(client_Loop
						.getWorkflow().getHasLastNode(), loopTask);
			} else {
				return false;
			}
		} else {
			Client_ServiceTask client_ServiceTask = (Client_ServiceTask) client_Node;
			boolean valid = false;
			for (Iterator<ParamInfo> iterator = client_ServiceTask.getService()
					.getHasConfiguration().getServiceConfigure()
					.getMethodInfo().getInputInfo().getParamInfosArray()
					.iterator(); iterator.hasNext();) {
				ParamInfo paramInfo = iterator.next();
				valid = valid
						|| check_ParaminfoWeatherHasVariableInput(paramInfo,
								loopTask);
			}
			return valid;
		}

	}

	private boolean check_ParaminfoWeatherHasVariableInput(ParamInfo paramInfo,
			LoopTask loopTask) {
		if (loopTask.getText().equals("Loop")) {
			return true;
		} else {
			if (paramInfo.isPrimitive()) {
				if (paramInfo.getParamValue() != null) {
					String valueString = paramInfo.getParamValue();
					String[] divided = valueString.split("\\.");
					String workflowNodeID = divided[0];
					// int current_level = dbe.gde.tabs.getWidgetCount();
					// because there is only one type of looptask ,so we can
					// check the variable in a simple way
					if (divided[divided.length - 1]
							.equals("currentDate (Variable)")
							&& workflowNodeID.equals(loopTask.getID())) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else {
				for (Iterator<ParamInfo> iterator = paramInfo
						.getFieldInfoArray().iterator(); iterator.hasNext();) {
					if (check_ParaminfoWeatherHasVariableInput(
							iterator.next(), loopTask) == true) {
						return true;
					}
				}
				return false;
			}
		}

	}
}
