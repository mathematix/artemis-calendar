package com.ics.tcg.web.workflow.client.command;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.ics.tcg.web.workflow.client.DiagramBuilder;
import com.ics.tcg.web.workflow.client.composite.complement.MyPanel;
import com.ics.tcg.web.workflow.client.task.Workflowtasknode;

public class SetStartCommand implements Command {

	Workflowtasknode workflowtasknode;
	DiagramBuilder diagrambuilderexample;
	MenuItem menuItem;
	Widget beginner_view_proxy = new Label(); // 用于区分，是否为起始节点的颜色label

	public SetStartCommand(Widget w, DiagramBuilder d, MenuItem menuItem) {
		workflowtasknode = (Workflowtasknode) w;
		diagrambuilderexample = d;
		this.menuItem = menuItem;
	}

	public void execute() {
		// TODO Auto-generated method stub
		setStarter();
		workflowtasknode.cmg.popupPanel.hide();
	}

	public void setStarter() {

		if (menuItem.getText().equals("notStart")) {
			workflowtasknode.setIsstart(false);
			menuItem.setText("isStart");
			beginner_view_proxy.removeFromParent();
		} else {
			workflowtasknode.setIsstart(true);
			menuItem.setText("notStart");
			MyPanel in = workflowtasknode.getPanel2();
			Label display_label = new Label();
			display_label.setSize("15", "15");
			display_label.addStyleName("start_port");
			in.add(display_label, 0, 0);
			beginner_view_proxy = display_label;
		}

	}

}
