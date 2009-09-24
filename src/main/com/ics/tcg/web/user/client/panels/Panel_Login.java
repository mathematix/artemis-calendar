package com.ics.tcg.web.user.client.panels;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.ics.tcg.web.user.client.User_Main;
import com.ics.tcg.web.user.client.db.User_Client;

public class Panel_Login extends VerticalPanel {

	private TextBox textBoxNameBox;
	private PasswordTextBox textBoxPaBox;
	private Button button;

	public Panel_Login(final User_Main user_Main) {

		// Create a table to layout the form options
		FlexTable layout = new FlexTable();
		layout.setCellSpacing(2);
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

		// Add a title
		layout.setHTML(0, 0, "Please Sign in");
		DOM.setStyleAttribute(layout.getFlexCellFormatter().getElement(0, 0),
				"paddingTop", "10px");
		DOM.setStyleAttribute(layout.getFlexCellFormatter().getElement(0, 0),
				"paddingBottom", "5px");
		DOM.setStyleAttribute(layout.getFlexCellFormatter().getElement(0, 0),
				"fontSize", "11pt");
		DOM.setStyleAttribute(layout.getFlexCellFormatter().getElement(0, 0),
				"fontWeight", "bold");

		cellFormatter.setColSpan(0, 0, 2);
		cellFormatter.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

		// add Account TextBox
		layout.setHTML(1, 0, "Account:");
		DOM.setStyleAttribute(layout.getFlexCellFormatter().getElement(1, 0),
				"fontSize", "10pt");
		cellFormatter.setAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT,
				HasVerticalAlignment.ALIGN_MIDDLE);

		textBoxNameBox = new TextBox();
		textBoxNameBox.setWidth("140");
		DOM.setStyleAttribute(textBoxNameBox.getElement(), "fontSize", "10pt");
		textBoxNameBox.setName("account");
		layout.setWidget(1, 1, textBoxNameBox);

		// add Password TextBox
		layout.setHTML(2, 0, "Password:");
		cellFormatter.setAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT,
				HasVerticalAlignment.ALIGN_MIDDLE);
		DOM.setStyleAttribute(layout.getFlexCellFormatter().getElement(2, 0),
				"fontSize", "10pt");

		textBoxPaBox = new PasswordTextBox();
		DOM.setStyleAttribute(textBoxPaBox.getElement(), "fontSize", "10pt");
		textBoxPaBox.setWidth("140");
		textBoxPaBox.setName("password");
		layout.setWidget(2, 1, textBoxPaBox);
		textBoxPaBox.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				char keyCode = event.getCharCode();
				if (keyCode == KeyCodes.KEY_ENTER) {
					button.click();
				}
			}
		});

		// add LoginButton
		button = new Button("Sign in");
		DOM.setStyleAttribute(button.getElement(), "fontSize", "10pt");
		button.removeStyleName("gwt-Button");
		layout.setWidget(3, 1, button);
		button.setFocus(true);

		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				user_Main.login_ServiceAsync.Valid_Login(textBoxNameBox
						.getText(), textBoxPaBox.getText(),
						new AsyncCallback<Integer>() {
							@Override
							public void onFailure(Throwable caught) {
								Window.alert("fail to communicate with server");
							}

							@Override
							public void onSuccess(Integer result) {
								int valid = result;
								if (valid != -1) {
									user_Main.userid = valid;
									user_Main.user_ServiceAsync.getUser(valid,
											new AsyncCallback<User_Client>() {

												@Override
												public void onFailure(
														Throwable caught) {
													Window
															.alert("fail to geti user");
												}

												@Override
												public void onSuccess(
														User_Client result) {
													user_Main.userClient = result;
													History
															.newItem(user_Main.WELCOME_STATE);
												}
											});

								} else {
									Window
											.alert("Your Account or PassWord is not Valid");
								}
							}
						});
			}
		});

		// Wrap the content in a DecoratorPanel
		DOM.setStyleAttribute(this.getElement(), "border", "1px solid #C3D9FF");
		DOM.setStyleAttribute(this.getElement(), "padding", "3px");
		DOM
				.setStyleAttribute(layout.getElement(), "backgroundColor",
						"#E8EEFA");
		layout.setWidth("100%");

		add(layout);
	}
}