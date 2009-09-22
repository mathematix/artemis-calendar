package com.ics.tcg.web.workflow.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ics.tcg.web.user.client.panels.Panel_Overview;

@SuppressWarnings( { "deprecation" })
public class WorkflowEditModule extends BaseEntryPoint {

	public ArrayList<String> serviceNameList;
	public HashMap<String, String> service_name_to_id;
	public Panel_Overview overview;
	public boolean checkvalue = true;
	public int pre_selecttab;

	static boolean state = true; // 表示当前显示的是workflow还是subworkflow
	public final TabPanel tabs = new TabPanel();

	public DiagramBuilder diagramBuilderExample;

	public static boolean isState() {
		return state;
	}

	public WorkflowEditModule(Panel_Overview panel_Overview) {
		overview = panel_Overview;
	}

	@Override
	public void onLoad() {

		service_name_to_id = new HashMap<String, String>();

		serviceNameList = new ArrayList<String>();
		getServiceNameFromServer();

		diagramBuilderExample = new DiagramBuilder(this, overview);
		tabs.add(diagramBuilderExample, "Diagram builder");

		tabs.removeStyleName("gwt-TabPanel");
		tabs.addStyleName("w-TabPanel");
		tabs.getTabBar().removeStyleName("gwt-TabBar");
		tabs.getTabBar().addStyleName("w-TabBar");
		tabs.getDeckPanel().removeStyleName("gwt-TabPanelBottom");
		tabs.getDeckPanel().addStyleName("w-TabPanelBottom");

		tabs.addBeforeSelectionHandler(new BeforeSelectionHandler<Integer>() {
			@Override
			public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
				// 将被选择的工作流编辑页面中容器的子工作流先做保存
				int count = tabs.getWidgetCount();
				pre_selecttab = count;
				checkvalue = true;
				for (int current = count - 1; current > event.getItem(); current--) {
					SubDiagram subDiagram = (SubDiagram) tabs
							.getWidget(current);
					subDiagram.save_workflow_in_workflowmode();
					if (subDiagram.check == false) {
						checkvalue = false;
						System.out.println(checkvalue);
					}
				}
			}
		});

		tabs.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				AbstractConnections e = (AbstractConnections) tabs
						.getWidget(event.getSelectedItem());
				e.update();
				int count = tabs.getWidgetCount();
				System.out.println(checkvalue);
				if (checkvalue == true) {
					for (int current = count - 1; current > event
							.getSelectedItem(); current--) {
						tabs.remove(current);
					}
				} else {
					tabs.selectTab(pre_selecttab - 1);
				}
			}
		});
		// tabs.addTabListener(new TabListener() {
		// public boolean onBeforeTabSelected(SourcesTabEvents sender,
		// int tabIndex) {
		// //将被选择的工作流编辑页面中容器的子工作流先做保存
		// int count = tabs.getWidgetCount();
		// for(int current = count-1;current>tabIndex;current--){
		// SubDiagram subDiagram = (SubDiagram)tabs.getWidget(current);
		// subDiagram.save_workflow_in_workflowmode();
		// }
		// return true;
		// }
		//
		// public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
		// AbstractConnectionsExample e =
		// (AbstractConnectionsExample)tabs.getWidget(tabIndex);
		// e.update();
		// int count = tabs.getWidgetCount();
		// for(int current = count-1;current>tabIndex;current--){
		// tabs.remove(current);
		// }
		// }
		// });

		tabs.selectTab(0);
	}

	public void getServiceNameFromServer() {

		// 假设初始化服务列表已知
		// serviceNameList.add("BookAirTicketService");
		// serviceNameList.add("BookBusTicketService");
		// serviceNameList.add("BookTrainTicketService");
		// serviceNameList.add("BookRoomService");
		// serviceNameList.add("EmailService");
		// serviceNameList.add("SMS");
		// serviceNameList.add("WeatherForecastService");

		final DialogBox dialogBox = new DialogBox();
		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		final HTML serverResponseLabel = new HTML();
		horizontalPanel.add(serverResponseLabel);
		final Button button = new Button("close");
		button.removeStyleName("gwt-Button");
		DOM.setStyleAttribute(button.getElement(), "fontSize", "10pt");
		button.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {

				dialogBox.hide();
			}

		});
		horizontalPanel.add(button);
		dialogBox.setWidget(horizontalPanel);

	}

	public void clearpanel() {
		if (diagramBuilderExample != null) {
			diagramBuilderExample.getPanel().clear();
		}
	}
}
