package com.ics.tcg.web.workflow.client.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import pl.balon.gwt.diagrams.client.connection.Connection;

import com.allen_sauer.gwt.dragdrop.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.DropController;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;
import com.ics.tcg.web.workflow.client.DiagramBuilder;
import com.ics.tcg.web.workflow.client.common.customline.CustomConnection;
import com.ics.tcg.web.workflow.client.common.customline.CustomUIObjectConnector;
import com.ics.tcg.web.workflow.client.composite.complement.MyPanel;
import com.ics.tcg.web.workflow.client.service.InputInfo;
import com.ics.tcg.web.workflow.client.service.ParamInfo;
import com.ics.tcg.web.workflow.client.task.ChoiceTask;
import com.ics.tcg.web.workflow.client.task.ServiceTask;
import com.ics.tcg.web.workflow.client.task.Workflowtasknode;

@SuppressWarnings( { "unchecked", "unused" })
public class TaskDeleteCommand implements Command {

	Workflowtasknode deletewidget;
	DiagramBuilder diagrambuilderexample;
	List<Workflowtasknode> backTaskList = new ArrayList<Workflowtasknode>();

	public TaskDeleteCommand(Widget w, DiagramBuilder d) {
		deletewidget = (Workflowtasknode) w;
		diagrambuilderexample = d;
	}

	public void execute() {
		// TODO Auto-generated method stub
		delete();
	}

	public void delete() {
		diagrambuilderexample.selected.clear(); // 如果是选中的准备连线的节点，则把准备连线节点列表清空
		deletewidget.cmg.popupPanel.hide(); // 右击弹出框隐藏

		// 删除节点的前，检查其后继节点，如果后继节点服务输入为本节点的输出，将他们设置为null
		// Get_backTask_all();
		// for (Iterator iterator = backTaskList.iterator();
		// iterator.hasNext();) {
		// SimpleTask backSimpleTask = (SimpleTask)iterator.next();
		// for (Iterator iterator2 = backSimpleTask.webservice.iterator();
		// iterator2.hasNext();) {
		// WebServiceFuction function = (WebServiceFuction)iterator2.next();
		// String inputString = function.input_value;
		// if(inputString!=null){
		// String[] temp = inputString.split("\\."); //"."必须用转义的方式表达
		// if(deletewidget.getText().equals(temp[0])){
		// function.input_value=null;
		// }
		// }
		// }
		// }
		deletewidget.Get_backTask_all();
		for (Iterator<Workflowtasknode> iterator = deletewidget.backTaskList
				.iterator(); iterator.hasNext();) {
			Workflowtasknode workflowtasknode = iterator.next();
			// 如果已经完成了服务配置，才能进行进一步判断
			if (workflowtasknode.hasFinishedConfigure) {
				if (workflowtasknode.getText().equals("Choice")) {
					ChoiceTask choice = (ChoiceTask) workflowtasknode;
					if (choice.check_ParamInfoWeatherComefrom(deletewidget,
							choice.contentPath)) {
						choice.setHasFinishedConfigure(false);
						System.out.println(workflowtasknode.getTitle()
								+ "的配置信息有一部分来自于" + deletewidget.getTitle());
					}
				} else {
					ServiceTask serviceTask = (ServiceTask)workflowtasknode;
					InputInfo inputInfo = serviceTask.getServiceConfigure()
							.getMethodInfo().getInputInfo();
					for (Iterator<ParamInfo> iterator2 = inputInfo
							.getParamInfosArray().iterator(); iterator2
							.hasNext();) {
						if (workflowtasknode.check_ParamInfoWeatherComefrom(
								deletewidget, iterator2.next())) {
							workflowtasknode.setHasFinishedConfigure(false);
							System.out.println(workflowtasknode.getTitle()
									+ "的配置信息有一部分来自于" + deletewidget.getTitle());
						}
					}
				}
			}
		}

		/* 分别对simpleTask的每个出入口做边的删除 */
		List connection_list = new ArrayList();
		List connection_list2 = new ArrayList();
		List connection_list3 = new ArrayList();

		for (Iterator iterator = deletewidget.getconnectorList().iterator(); iterator
				.hasNext();) {
			CustomUIObjectConnector connector = CustomUIObjectConnector
					.getWrapper((MyPanel) iterator.next());
			Collection cons = connector.getConnections();
			for (Iterator j = cons.iterator(); j.hasNext();) {
				Connection c = (Connection) j.next();
				connection_list.add(c);
			}
			for (Iterator k = connection_list.iterator(); k.hasNext();) {
				Connection c = (Connection) k.next();
				c.remove();
			}
			CustomUIObjectConnector.unwrap(deletewidget.getPanel2());
		}

		/* 删除容器控件的同时，要将其从toolboxDragController中取消注册 */
		for (Iterator k = (diagrambuilderexample.dropcontroler_listList)
				.iterator(); k.hasNext();) {
			/* 比对匹配后，未加删除，删除可能会出现问题，因此操作多了List可能会很大 */
			DropController c = (AbsolutePositionDropController) k.next();
			if (c.getDropTarget() == deletewidget.getPanel1())
				diagrambuilderexample.toolboxDragController
						.unregisterDropController(c);
		}
		for (Iterator k = diagrambuilderexample.timer_dropcontrollerList
				.iterator(); k.hasNext();) {
			/* 比对匹配后，未加删除，删除可能会出现问题，因此操作多了List可能会很大 */
			DropController c = (AbsolutePositionDropController) k.next();
			if (c.getDropTarget() == deletewidget.getPanel1())
				diagrambuilderexample.timerDragController
						.unregisterDropController(c);
		}

		if (deletewidget.getTimerNode() != null) {
			deletewidget.getTimerNode().removeFromParent();
		}

		deletewidget.removeFromParent();
		// diagrambuilderexample.list.remove(deletewidget); //没用，待处理

	}

	public void Get_backTask_all() {
		backTaskList.clear();
		List list = deletewidget.outport_list;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
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
				ServiceTask front_end_simpletask = (ServiceTask) back_end_panelPanel
						.getParent().getParent();
				backTaskList.add(front_end_simpletask);
			}
			System.out.println("set successful");
		}
	}

}