package com.ics.tcg.web.user.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.ics.tcg.web.user.client.db.User_Client;
import com.ics.tcg.web.user.client.panels.Panel_Login;
import com.ics.tcg.web.user.client.panels.Panel_Overview;
import com.ics.tcg.web.user.client.remote.Login_Service;
import com.ics.tcg.web.user.client.remote.Login_ServiceAsync;
import com.ics.tcg.web.user.client.remote.User_Service;
import com.ics.tcg.web.user.client.remote.User_ServiceAsync;

public class User_Main implements EntryPoint, ValueChangeHandler<String> {

	/** History Tokens */
	public String LOGIN_STATE = "login";
	public String WELCOME_STATE = "welcome";

	/** Remote */
	public User_ServiceAsync user_ServiceAsync = GWT.create(User_Service.class);
	public Login_ServiceAsync login_ServiceAsync = GWT
			.create(Login_Service.class);

	/** data */
	public Integer userid = -1;
	public User_Client userClient;

	/** Panels */
	private Panel_Login loginPanel;
	private Panel_Overview overviewPanel;
	private VerticalPanel regPanel;

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		// initialize
		loginPanel = new Panel_Login(this);

		History.addValueChangeHandler(this);
		History.newItem(LOGIN_STATE);
		History.fireCurrentHistoryState();
	}

	/**
	 * before loggin
	 */
	private void loadLoginView() {
		RootPanel.get().clear();

		final VerticalPanel leftpanel = createLeftPanel();

		loginPanel.setWidth("250");

		// Register
		regPanel = createRegTable();

		int width = Window.getClientWidth() - 275;
		RootPanel.get().add(leftpanel, 20, 20);
		RootPanel.get().add(loginPanel, width, 95);
		RootPanel.get().add(regPanel, width, 240);

		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				if (RootPanel.get().getWidgetIndex(loginPanel) >= 0) {

					((HTML) leftpanel.getWidget(1)).setWidth(Integer
							.toString(Window.getClientWidth() - 320));
					((Image) leftpanel.getWidget(2))
							.setWidth(Integer.toString(Window
									.getClientWidth() - 320 > 1250 ? 1250
									: Window.getClientWidth() - 320));
					((Image) leftpanel.getWidget(2)).setHeight(Integer
							.toString((int) (((Image) leftpanel.getWidget(2))
									.getOffsetWidth() * 0.4644)));
					int width = Window.getClientWidth() - 275;
					RootPanel.get().setWidgetPosition(loginPanel, width, 95);
					RootPanel.get().setWidgetPosition(regPanel, width, 240);
				}
			}
		});
		DeferredCommand.addCommand(new Command() {
			@Override
			public void execute() {
				((Image) leftpanel.getWidget(2)).setHeight(Integer
						.toString((int) (((Image) leftpanel.getWidget(2))
								.getOffsetWidth() * 0.4644)));
			}
		});
	}

	/**
	 * after loggin
	 */
	public void loadMainView() {

		RootPanel.get().clear();
		overviewPanel = new Panel_Overview(this);

		final HTML html = new HTML("<hr>");
		html.setWidth("100%");
		RootPanel.get().add(html, 0, 15);

		Integer width = Window.getClientWidth() - 20;
		overviewPanel.setWidth(width.toString());

		RootPanel.get().add(overviewPanel, 10, 3);

		/** resize the dock panel */
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				if (RootPanel.get().getWidgetIndex(overviewPanel) != -1) {
					Integer width = Window.getClientWidth() - 20;
					overviewPanel.setWidth(width.toString());
				}
			}
		});
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		if (LOGIN_STATE.equals(event.getValue())) {
			loadLoginView();
		} else if (WELCOME_STATE.equals(event.getValue())) {
			loadMainView();
		}
	}

	public VerticalPanel createRegTable() {
		{
			VerticalPanel verticalPanel = new VerticalPanel();
			verticalPanel.setWidth("250");
			DOM.setStyleAttribute(verticalPanel.getElement(), "border",
					"1px solid #C3D9FF");
			DOM.setStyleAttribute(verticalPanel.getElement(), "padding", "3px");
			// flextable
			FlexTable regTable = new FlexTable();
			regTable.setWidth("100%");
			FlexCellFormatter flexCellFormatter = regTable
					.getFlexCellFormatter();
			flexCellFormatter.setAlignment(0, 0,
					HasHorizontalAlignment.ALIGN_CENTER,
					HasVerticalAlignment.ALIGN_TOP);
			flexCellFormatter.setHeight(0, 0, "50");
			flexCellFormatter.setAlignment(1, 0,
					HasHorizontalAlignment.ALIGN_CENTER,
					HasVerticalAlignment.ALIGN_MIDDLE);
			DOM.setStyleAttribute(regTable.getElement(), "backgroundColor",
					"#E8EEFA");
			// add html
			HTML htmlreg = new HTML();
			htmlreg
					.setHTML("<DIV style='font-size:11pt;font-weight:bold;'>Don't have an account?</DIV>");
			DOM.setStyleAttribute(htmlreg.getElement(), "paddingTop", "10px");
			regTable.setWidget(0, 0, htmlreg);
			// add image
			Image image = new Image();
			image.setUrl("img/create.png");
			DOM.setStyleAttribute(image.getElement(), "cursor", "hand");
			image.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					Window
							.open("../reg.html", "_self",
									"menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=yes");
				}
			});
			regTable.setWidget(1, 0, image);

			verticalPanel.add(regTable);
			return verticalPanel;
		}
	}

	public VerticalPanel createLeftPanel() {
		final VerticalPanel leftpanel = new VerticalPanel();
		// add head image
		AbsolutePanel leftcontent1 = new AbsolutePanel();
		leftcontent1.setSize("400", "75");
		Image image1 = new Image();
		image1.setUrl("img/logo.png");
		leftcontent1.add(image1);
		leftpanel.add(leftcontent1);
		// add html
		HTML html = new HTML();
		html.setWidth(Integer.toString(Window.getClientWidth() - 320));
		String htmlString = "<Div style='font-size:15pt;font-weight:bold'>What's ICS schedule calender?</Div>"
				+ "<div style='font:10pt; word-wrap:break-word'>ICS schedule calendar is a free online calendar."
				+ " You can organize your schedule and keep track of the important things."
				+ "<br>With the workflow editor, you can arrenge a sort of things in a schedule workflow so as "
				+ "to make these things done sequentially and automatically.</div>";
		html.setHTML(htmlString);
		leftpanel.add(html);
		// add image
		Image image2 = new Image();
		image2.setUrl("img/beforelog.png");
		image2.setWidth(Integer
				.toString(Window.getClientWidth() - 320 > 1250 ? 1250
						: Window.getClientWidth() - 320));
		leftpanel.add(image2);

		return leftpanel;
	}
}
