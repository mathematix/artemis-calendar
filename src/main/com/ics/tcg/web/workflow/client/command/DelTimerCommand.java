package com.ics.tcg.web.workflow.client.command;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;
import com.ics.tcg.web.workflow.client.DiagramBuilder;
import com.ics.tcg.web.workflow.client.task.ServiceTask;
import com.ics.tcg.web.workflow.client.task.TimerNode;

public class DelTimerCommand implements Command {

	ServiceTask deletewidget;
	DiagramBuilder diagrambuilderexample;

	public DelTimerCommand(Widget w, DiagramBuilder d) {
		deletewidget = (ServiceTask) w;
		diagrambuilderexample = d;
	}

	public void execute() {
		// TODO Auto-generated method stub
		deleteTimer();
		deletewidget.EnableAddTimer();
		deletewidget.cmg.popupPanel.hide();
	}

	public void deleteTimer() {
		TimerNode timer;
		if (deletewidget.getTimerNode() != null) {
			timer = deletewidget.getTimerNode();
			timer.removeFromParent();
			deletewidget.setTimerNode(null);
			deletewidget.hasTimer = false;
			// diagrambuilderexample.timernodeList.remove(timer);
		}
	}
}
