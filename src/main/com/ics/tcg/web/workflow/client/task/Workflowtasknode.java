package com.ics.tcg.web.workflow.client.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWordWrap;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.MouseWheelListener;
import com.google.gwt.user.client.ui.MouseWheelListenerCollection;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SourcesChangeEvents;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.SourcesMouseWheelEvents;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.ics.tcg.web.workflow.client.DiagramBuilder;
import com.ics.tcg.web.workflow.client.SubDiagram;
import com.ics.tcg.web.workflow.client.common.customline.CustomConnection;
import com.ics.tcg.web.workflow.client.common.customline.CustomUIObjectConnector;
import com.ics.tcg.web.workflow.client.composite.complement.ContextMenuGwt;
import com.ics.tcg.web.workflow.client.composite.complement.MyPanel;
import com.ics.tcg.web.workflow.client.service.ParamInfo;

public class Workflowtasknode extends Composite implements HasHorizontalAlignment, 
SourcesClickEvents, SourcesChangeEvents,SourcesMouseEvents, SourcesMouseWheelEvents{

	protected String ID;
	protected String taskType;
	private String text;
	protected AbsolutePanel panel1;
	protected MyPanel panel2, panel3, panel4;
	protected int pixel_x, pixel_y;
	protected boolean isstart, isfinished;
	
	public ContextMenuGwt cmg;
	public MenuItem setstart, setfinish;
	public boolean belongToSubWorkflow;
	public boolean hasFinishedConfigure;
	
	private ClickListenerCollection clickListeners;
	private HorizontalAlignmentConstant horzAlign;
	private MouseListenerCollection mouseListeners;
	private MouseWheelListenerCollection mouseWheelListeners;
	private ChangeListenerCollection changeListeners;
	
	List<MyPanel> connector_list = new ArrayList<MyPanel>();
	public List<Workflowtasknode> preTaskList = new ArrayList<Workflowtasknode>();
	public Set<Workflowtasknode> preTaskSet = new HashSet<Workflowtasknode>();
	public List<Workflowtasknode> backTaskList = new ArrayList<Workflowtasknode>();
	public Set<Workflowtasknode> backTaskSet = new HashSet<Workflowtasknode>();	
	public ArrayList<MyPanel> outport_list = new ArrayList<MyPanel>();
	
	protected DiagramBuilder dbe;

	protected Image in, out, fault;
	
	protected ScrollPanel total_staticTreeWrapper;
	
	private TimerNode timerNode;

	
	public ArrayList<MyPanel> getconnectorList() {
		return (ArrayList<MyPanel>) connector_list;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}

	public boolean isBelongToSubWorkflow() {
		return belongToSubWorkflow;
	}

	public void setBelongToSubWorkflow(boolean belongToSubWorkflow) {
		this.belongToSubWorkflow = belongToSubWorkflow;
	}

	public int getPixel_x() {
		return pixel_x;
	}

	public void setPixel_x(int pixel_x) {
		this.pixel_x = pixel_x;
	}

	public int getPixel_y() {
		return pixel_y;
	}

	public void setPixel_y(int pixel_y) {
		this.pixel_y = pixel_y;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getText() {
		// TODO Auto-generated method stub
		return text;
	}

	public void setText(String text) {
		// TODO Auto-generated method stub
		this.text = text;
	}

	public AbsolutePanel getPanel1() {
		return panel1;
	}

	public void setPanel1(MyPanel panel1) {
		this.panel1 = panel1;
	}

	public MyPanel getPanel2() {
		return panel2;
	}

	public void setPanel2(MyPanel panel2) {
		this.panel2 = panel2;
	}

	public MyPanel getPanel3() {
		return panel3;
	}

	public void setPanel3(MyPanel panel3) {
		this.panel3 = panel3;
	}

	public MyPanel getPanel4() {
		return panel4;
	}

	public void setPanel4(MyPanel panel4) {
		this.panel4 = panel4;
	}

	public boolean isIsstart() {
		return isstart;
	}

	public void setIsstart(boolean isstart) {
		this.isstart = isstart;
	}

	public boolean isIsfinished() {
		return isfinished;
	}

	public void setIsfinished(boolean isfinished) {
		this.isfinished = isfinished;
	}

	public boolean isHasFinishedConfigure() {
		return hasFinishedConfigure;
	}

	public void setHasFinishedConfigure(boolean hasFinishedConfigure) {
		this.hasFinishedConfigure = hasFinishedConfigure;
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
				Workflowtasknode front_end_simpletask = (Workflowtasknode) front_end_panelPanel
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
	
	public void Get_backTask_all() {
		backTaskList.clear();
		backTaskSet.clear();
		Get_backTask_all(this);
	}

	@SuppressWarnings("unchecked")
	public void Get_backTask_all(Workflowtasknode workflowtasknode) {
		ArrayList<MyPanel> list = workflowtasknode.outport_list;
		for (Iterator<MyPanel> iterator = list.iterator(); iterator.hasNext();) {
			MyPanel outport = (MyPanel) iterator.next();
			CustomUIObjectConnector cons = CustomUIObjectConnector
					.getWrapper(outport);
			Collection collection = cons.getConnections();
			CustomUIObjectConnector back_end_Connector;
			for (Iterator iterator2 = collection.iterator(); iterator2
					.hasNext();) {
				CustomConnection c = (CustomConnection) iterator2.next();
				back_end_Connector = (CustomUIObjectConnector) c.getConnected()
						.get(1);
				MyPanel back_end_panelPanel = (MyPanel) back_end_Connector.wrapped;
				Workflowtasknode back_end_simpletask = (Workflowtasknode) back_end_panelPanel
						.getParent().getParent();
				if (!backTaskSet.contains(back_end_simpletask)) {
					backTaskList.add(back_end_simpletask);
					backTaskSet.add(back_end_simpletask);
					Get_backTask_all(back_end_simpletask);
				}

			}
			System.out.println("set successful");
		}
	}
	
	public boolean check_ParamInfoWeatherComefrom(Workflowtasknode workflowtasknode,
			ParamInfo paramInfo) {
		String id = workflowtasknode.getID();
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
	
	public TimerNode getTimerNode() {
		return timerNode;
	}

	public void setTimerNode(TimerNode timerNode) {
		this.timerNode = timerNode;
	}
	
	public void addClickListener(ClickListener listener) {
		if (clickListeners == null) {
			clickListeners = new ClickListenerCollection();
			sinkEvents(Event.ONCLICK);
		}
		clickListeners.add(listener);
	}

	public void addMouseListener(MouseListener listener) {
		if (mouseListeners == null) {
			mouseListeners = new MouseListenerCollection();
			sinkEvents(Event.MOUSEEVENTS);
		}
		mouseListeners.add(listener);
	}

	public void addMouseWheelListener(MouseWheelListener listener) {
		if (mouseWheelListeners == null) {
			mouseWheelListeners = new MouseWheelListenerCollection();
			sinkEvents(Event.ONMOUSEWHEEL);
		}
		mouseWheelListeners.add(listener);
	}

	public void removeClickListener(ClickListener listener) {
		if (clickListeners != null) {
			clickListeners.remove(listener);
		}
	}

	public void removeMouseListener(MouseListener listener) {
		if (mouseListeners != null) {
			mouseListeners.remove(listener);
		}
	}

	public void removeMouseWheelListener(MouseWheelListener listener) {
		if (mouseWheelListeners != null) {
			mouseWheelListeners.remove(listener);
		}
	}

	public HorizontalAlignmentConstant getHorizontalAlignment() {
		// TODO Auto-generated method stub
		return horzAlign;
	}

	public void setHorizontalAlignment(HorizontalAlignmentConstant align) {
		// TODO Auto-generated method stub
		horzAlign = align;
		getElement().getStyle().setProperty("textAlign",
				align.getTextAlignString());
	}

	public void addChangeListener(ChangeListener listener) {
		// TODO Auto-generated method stub
		if (changeListeners == null) {
			changeListeners = new ChangeListenerCollection();
		}
		changeListeners.add(listener);
	}

	public void removeChangeListener(ChangeListener listener) {
		// TODO Auto-generated method stub
		if (changeListeners != null) {
			changeListeners.remove(listener);
		}
	}
	
	public void onBrowserEvent(Event event) {
		switch (event.getTypeInt()) {
		case Event.ONCLICK:
			if (clickListeners != null) {
				clickListeners.fireClick(this);
			}
			break;

		case Event.ONMOUSEDOWN:
		case Event.ONMOUSEUP:
		case Event.ONMOUSEMOVE:
		case Event.ONMOUSEOVER:
		case Event.ONMOUSEOUT:
			if (mouseListeners != null) {
				// DOM.eventCancelBubble(event, true); // 阻止事件传到父对豄1�7
				mouseListeners.fireMouseEvent(this, event);
			}
			break;

		case Event.ONMOUSEWHEEL:
			if (mouseWheelListeners != null) {
				mouseWheelListeners.fireMouseWheelEvent(this, event);
			}
			break;
		}
	}
}
