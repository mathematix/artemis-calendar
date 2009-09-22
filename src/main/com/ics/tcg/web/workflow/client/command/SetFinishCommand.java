package com.ics.tcg.web.workflow.client.command;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.ics.tcg.web.workflow.client.DiagramBuilder;
import com.ics.tcg.web.workflow.client.composite.complement.MyPanel;
import com.ics.tcg.web.workflow.client.task.Workflowtasknode;

public class SetFinishCommand implements Command {

	Workflowtasknode workflowtasknode;
	DiagramBuilder diagrambuilderexample;
	MenuItem menuItem;
	Widget ender_view_proxy = new Label(); // 用于区分，是否为终止节点的颜色label

	public SetFinishCommand(Widget w, DiagramBuilder d, MenuItem menuItem) {
		workflowtasknode = (Workflowtasknode) w;
		diagrambuilderexample = d;
		this.menuItem = menuItem;
	}

	public void execute() {
		// TODO Auto-generated method stub
		setFinisher();
		workflowtasknode.cmg.popupPanel.hide();
	}

	public void setFinisher() {

		if (menuItem.getText().equals("notFinish")) {
			workflowtasknode.setIsfinished(false);
			diagrambuilderexample.lastNode = null;
			menuItem.setText("isFinish");
			ender_view_proxy.removeFromParent();
		} else {
			MyPanel out = workflowtasknode.getPanel3();
			if (out != null && !out.isAttached()) {
				System.out.println("不能把没有出口节点的task设置为finished");
				return;
			}
			workflowtasknode.setIsfinished(true);
			diagrambuilderexample.lastNode = workflowtasknode;
			menuItem.setText("notFinish");
			Label display_label = new Label();
			display_label.setSize("15", "15");
			display_label.addStyleName("end_port");
			out.add(display_label, 0, 0);
			ender_view_proxy = display_label;
		}

	}

}
