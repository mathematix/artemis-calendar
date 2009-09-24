package com.ics.tcg.web.reg.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

public class Reg implements EntryPoint {

	public final Reg_ServiceAsync reg_Service = GWT.create(Reg_Service.class);

	User_Client temp = new User_Client();
	DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
	String y = "";
	String m = "";
	String d = "";

	public void onModuleLoad() {

		RootPanel rootPanel = RootPanel.get();
		AbsolutePanel headPanel = createHead();
		rootPanel.add(headPanel, 20, 10);

		HTML html_create = new HTML("", true);
		html_create.setSize("675px", "88px");
		String htmlString = "<Div style='font-size:15pt;font-weight:bold'>Create an Account</Div>"
				+ "<div style='font:10pt; word-wrap:break-word'><br>Register an Account to ICS Calendar"
				+ ". If you already have a ICS Account, you can <a href='User.html' target='_self'>sign in</a> here.</div>";
		html_create.setHTML(htmlString);
		rootPanel.add(html_create, 20, 100);
		{
			AbsolutePanel absolutePanel = new AbsolutePanel();
			DOM.setStyleAttribute(absolutePanel.getElement(), "border",
					"2 solid #C3D9FF");
			rootPanel.add(absolutePanel, 20, 193);
			absolutePanel.setSize("650px", "653px");

			FlexTable accountTable = createAccount();
			FlexTable sexTable = createSex();
			FlexTable passTable = createPass();
			FlexTable ageTable = createAge();
			FlexTable birthTable = createBirthday();
			FlexTable telTable = createTel();
			FlexTable emailTable = createEmail();
			FlexTable notiTable = createNoti();

			absolutePanel.add(accountTable, 10, 10);
			absolutePanel.add(passTable, 10, 60);

			HTML html1 = new HTML();
			html1.setWidth("630");
			html1.setHTML("<hr>");
			absolutePanel.add(html1, 10, 140);

			absolutePanel.add(sexTable, 10, 160);
			absolutePanel.add(ageTable, 10, 230);
			absolutePanel.add(birthTable, 10, 280);
			absolutePanel.add(emailTable, 10, 330);
			absolutePanel.add(telTable, 10, 380);

			HTML html2 = new HTML();
			html2.setHTML("<hr>");
			html2.setWidth("630");
			absolutePanel.add(html2, 10, 425);

			absolutePanel.add(notiTable, 10, 451);

			Button button = new Button("     I accept. Create My Account.     ");
			DOM.setStyleAttribute(button.getElement(), "fontSize", "10pt");
			button.removeStyleName("gwt-Button");
			absolutePanel.add(button, 295, 542);

			button.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {

					//parse birthday
					if (y.equals("Year") == false && m.equals("Month") == false
							&& d.equals("Day") == false) {
						String date = y + "-" + m + "-" + d;
						temp.birthday = dateTimeFormat.parse(date);
					} else {
						temp.birthday = null;
					}

					//check and save
					if (temp.account != null && temp.age != null
							&& temp.birthday != null && temp.tel != null
							&& temp.email != null && temp.password != null
							&& temp.account.equals("") != true && temp.age != 0
							&& temp.birthday.equals(new Date()) != true
							&& temp.tel.equals("") != true
							&& temp.email.equals("") != true
							&& temp.password.equals("") != true) {
						reg_Service.check(temp.account,
								new AsyncCallback<Integer>() {
									@Override
									public void onFailure(Throwable caught) {
									}

									@Override
									public void onSuccess(Integer result) {
										if (result != -1) {
											Window.scrollTo(0, 0);
											Window
													.alert("You can't use this Account");
										} else {
											saveUser();
										}
									}
								});
					} else {
						Window.scrollTo(0, 0);
						Window.alert("Something Wrong, Check plz");
					}
				}
			});
		}
	}

	/**save user*/
	void saveUser() {
		reg_Service.saveUser(temp, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(String result) {
				createFinish();
			}
		});
	}

	/**create head*/
	AbsolutePanel createHead() {
		AbsolutePanel absolutePanel = new AbsolutePanel();
		absolutePanel.setSize("400", "75");
		Image image = new Image();
		image.setUrl("img/logo.png");
		absolutePanel.add(image);
		return absolutePanel;
	}

	/**create account*/
	FlexTable createAccount() {
		FlexTable flexTable = new FlexTable();
		FlexCellFormatter flexCellFormatter = flexTable.getFlexCellFormatter();
		flexCellFormatter.setWidth(0, 0, "180");

		Label lblEnterAnAccount = new Label("Enter an Account:");
		DOM.setStyleAttribute(lblEnterAnAccount.getElement(), "fontSize",
				"10pt");
		flexTable.setWidget(0, 0, lblEnterAnAccount);

		final TextBox textBox = new TextBox();
		DOM.setStyleAttribute(lblEnterAnAccount.getElement(), "fontSize",
				"10pt");
		textBox.setWidth("180");
		textBox.removeStyleName("gwt-TextBox");
		flexTable.setWidget(0, 1, textBox);

		Image image = new Image();
		image.setVisible(false);

		flexTable.setWidget(0, 2, image);

		textBox.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				temp.account = textBox.getText();
			}
		});

		return flexTable;
	}

	/**create password*/
	FlexTable createPass() {
		FlexTable flexTable = new FlexTable();
		FlexCellFormatter flexCellFormatter = flexTable.getFlexCellFormatter();
		Label label = new Label("Choose a password: ");
		Label label2 = new Label("Re-enter password:");
		DOM.setStyleAttribute(label.getElement(), "fontSize", "10pt");
		DOM.setStyleAttribute(label2.getElement(), "fontSize", "10pt");

		final PasswordTextBox textBox = new PasswordTextBox();
		final PasswordTextBox textBox2 = new PasswordTextBox();
		textBox.setWidth("180");
		textBox2.setWidth("180");
		textBox.removeStyleName("gwt-TextBox");
		textBox2.removeStyleName("gwt-TextBox");
		DOM.setStyleAttribute(textBox.getElement(), "fontSize", "10pt");
		DOM.setStyleAttribute(textBox2.getElement(), "fontSize", "10pt");

		flexCellFormatter.setWidth(0, 0, "180px");
		flexCellFormatter.setWidth(1, 0, "180px");

		DOM.setStyleAttribute(label.getElement(), "marginBottom", "10px");
		DOM.setStyleAttribute(textBox.getElement(), "marginBottom", "10px");

		flexTable.setWidget(0, 0, label);
		flexTable.setWidget(0, 1, textBox);
		flexTable.setWidget(1, 0, label2);
		flexTable.setWidget(1, 1, textBox2);

		final Image image = new Image();
		image.setVisible(false);

		flexTable.setWidget(1, 2, image);

		textBox.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				if (textBox2.getText().equals("") == false) {
					if (textBox2.getText().equals(textBox.getText())) {
						image.setVisible(true);
						image.setUrl("img/checkok.gif");
						temp.password = textBox2.getText();
					} else {
						image.setVisible(true);
						image.setUrl("img/checkwrong.gif");
					}
				}
			}
		});

		textBox2.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				if (textBox2.getText().equals("") == false
						&& textBox2.getText().equals(textBox.getText())) {
					image.setVisible(true);
					image.setUrl("img/checkok.gif");
					temp.password = textBox2.getText();
				} else {
					image.setVisible(true);
					image.setUrl("img/checkwrong.gif");
				}
			}
		});

		return flexTable;
	}

	/**create sex radio buttons*/
	FlexTable createSex() {
		FlexTable flexTable = new FlexTable();
		FlexCellFormatter flexCellFormatter = flexTable.getFlexCellFormatter();
		final RadioButton radioButton_Male = new RadioButton("radiosex");
		final RadioButton radioButton_Female = new RadioButton("radiosex");
		DOM
				.setStyleAttribute(radioButton_Male.getElement(), "fontSize",
						"10pt");
		DOM.setStyleAttribute(radioButton_Female.getElement(), "fontSize",
				"10pt");
		flexTable.setHeight("50");
		Label label_1 = new Label("Sex:");
		DOM.setStyleAttribute(label_1.getElement(), "fontSize", "10pt");
		radioButton_Male.setText("Male");
		radioButton_Female.setText("Female");
		flexTable.setWidget(0, 0, label_1);
		flexCellFormatter.setWidth(0, 0, "180");
		flexTable.setWidget(0, 1, radioButton_Male);
		flexTable.setWidget(1, 1, radioButton_Female);

		final Image image = new Image();
		image.setVisible(false);
		flexTable.setWidget(1, 2, image);

		radioButton_Male
				.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
					@Override
					public void onValueChange(ValueChangeEvent<Boolean> event) {
						image.setVisible(true);
						image.setUrl("img/checkok.gif");
						temp.sex = radioButton_Male.getValue();
					}
				});

		return flexTable;
	}

	/**create age*/
	FlexTable createAge() {
		FlexTable flexTable = new FlexTable();
		FlexCellFormatter flexCellFormatter = flexTable.getFlexCellFormatter();
		flexCellFormatter.setWidth(0, 0, "180");
		Label label = new Label("Age:");
		DOM.setStyleAttribute(label.getElement(), "fontSize", "10pt");
		final TextBox textBox = new TextBox();
		DOM.setStyleAttribute(textBox.getElement(), "fontSize", "10pt");
		flexTable.setWidget(0, 0, label);
		flexTable.setWidget(0, 1, textBox);

		final Image image = new Image();
		image.setVisible(false);
		flexTable.setWidget(0, 2, image);

		textBox.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				char keyCode = event.getCharCode();
				if (Character.isDigit((char) keyCode) == false) {
					textBox.cancelKey();
				}
			}
		});

		textBox.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				image.setVisible(true);
				image.setUrl("img/checkok.gif");
				temp.age = Integer.parseInt(textBox.getText());
			}
		});

		return flexTable;
	}

	/**create birthday*/
	FlexTable createBirthday() {

		FlexTable flexTable = new FlexTable();
		FlexCellFormatter flexCellFormatter = flexTable.getFlexCellFormatter();
		flexCellFormatter.setWidth(0, 0, "180");
		final ListBox year = new ListBox();
		final ListBox month = new ListBox();
		final ListBox day = new ListBox();

		Label label = new Label("Birthday:");
		DOM.setStyleAttribute(label.getElement(), "fontSize", "10pt");

		HorizontalPanel horizontalPanel = new HorizontalPanel();

		year.setWidth("100");
		DOM.setStyleAttribute(year.getElement(), "fontSize", "10pt");
		year.addItem("Year");
		for (Integer i = 50; i < 100; i++) {
			year.addItem("19" + i.toString());
		}
		for (Integer i = 0; i <= 9; i++) {
			year.addItem("200" + i.toString());
		}

		horizontalPanel.add(year);
		horizontalPanel.add(month);
		horizontalPanel.add(day);

		flexTable.setWidget(0, 0, label);
		flexTable.setWidget(0, 1, horizontalPanel);

		final Image image = new Image();
		image.setVisible(false);
		flexTable.setWidget(0, 2, image);

		year.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				y = year.getValue(year.getSelectedIndex());
				image.setVisible(true);
				if (y.equals("Year") || m.equals("Month") || d.equals("Day")
						|| y.equals("") || m.equals("") || d.equals("")) {
					image.setUrl("img/checkwrong.gif");
				} else {
					image.setUrl("img/checkok.gif");
				}
			}
		});
		DOM.setStyleAttribute(month.getElement(), "fontSize", "10pt");
		month.addItem("Month");
		month.setWidth("80");
		for (Integer i = 1; i < 13; i++) {
			month.addItem(i.toString());
		}
		month.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				m = month.getValue(month.getSelectedIndex());
				image.setVisible(true);
				if (y.equals("Year") || m.equals("Month") || d.equals("Day")
						|| y.equals("") || m.equals("") || d.equals("")) {
					image.setUrl("img/checkwrong.gif");
				} else {
					image.setUrl("img/checkok.gif");
				}
			}
		});
		DOM.setStyleAttribute(day.getElement(), "fontSize", "10pt");
		day.addItem("Day");
		day.setWidth("80");
		for (Integer i = 1; i < 32; i++) {
			day.addItem(i.toString());
		}
		day.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				d = day.getValue(day.getSelectedIndex());
				image.setVisible(true);
				if (y.equals("Year") || m.equals("Month") || d.equals("Day")
						|| y.equals("") || m.equals("") || d.equals("")) {
					image.setUrl("img/checkwrong.gif");
				} else {
					image.setUrl("img/checkok.gif");
				}
			}
		});

		return flexTable;
	}

	/**create email*/
	FlexTable createEmail() {
		FlexTable flexTable = new FlexTable();
		FlexCellFormatter flexCellFormatter = flexTable.getFlexCellFormatter();
		flexCellFormatter.setWidth(0, 0, "180");
		Label label = new Label("Email:");
		DOM.setStyleAttribute(label.getElement(), "fontSize", "10pt");
		final TextBox textBox = new TextBox();
		textBox.removeStyleName("gwt-TextBox");
		textBox.setWidth("180");
		DOM.setStyleAttribute(textBox.getElement(), "fontSize", "10pt");
		flexTable.setWidget(0, 0, label);
		flexTable.setWidget(0, 1, textBox);

		final Image image = new Image();
		image.setVisible(false);
		flexTable.setWidget(0, 2, image);

		textBox.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				image.setVisible(true);
				if (textBox.getText().contains("@")
						&& textBox.getText().lastIndexOf("@") != (textBox
								.getText().length() - 1)) {
					image.setUrl("img/checkok.gif");
					temp.email = textBox.getText();
				} else {
					image.setUrl("img/checkwrong.gif");
					temp.email = null;

				}
			}
		});

		return flexTable;
	}

	/**create telephone*/
	FlexTable createTel() {
		FlexTable flexTable = new FlexTable();
		FlexCellFormatter flexCellFormatter = flexTable.getFlexCellFormatter();
		flexCellFormatter.setWidth(0, 0, "180");
		Label label = new Label("Tel:");
		DOM.setStyleAttribute(label.getElement(), "fontSize", "10pt");
		final TextBox textBox = new TextBox();
		textBox.removeStyleName("gwt-TextBox");
		textBox.setWidth("180");
		DOM.setStyleAttribute(textBox.getElement(), "fontSize", "10pt");
		flexTable.setWidget(0, 0, label);
		flexTable.setWidget(0, 1, textBox);

		final Image image = new Image();
		image.setVisible(false);
		flexTable.setWidget(0, 2, image);

		textBox.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				image.setVisible(true);
				image.setUrl("img/checkok.gif");
				temp.tel = textBox.getText();
			}
		});

		return flexTable;
	}

	/**create notified method*/
	FlexTable createNoti() {
		FlexTable flexTable = new FlexTable();
		FlexCellFormatter flexCellFormatter = flexTable.getFlexCellFormatter();
		flexCellFormatter.setWidth(0, 0, "180");
		final CheckBox check1 = new CheckBox();
		final CheckBox check2 = new CheckBox();
		DOM.setStyleAttribute(check1.getElement(), "fontSize", "10pt");
		DOM.setStyleAttribute(check2.getElement(), "fontSize", "10pt");
		flexTable.setHeight("50");
		Label label = new Label("Notify by:");
		DOM.setStyleAttribute(label.getElement(), "fontSize", "10pt");
		check1.setText("Email");
		check2.setText("Mobile");
		flexTable.setWidget(0, 0, label);
		flexTable.setWidget(0, 1, check1);
		flexTable.setWidget(1, 1, check2);

		check1.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				temp.bymail = check1.getValue();
			}
		});
		check2.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				temp.bymobile = check2.getValue();
			}
		});

		return flexTable;
	}

	/**create finish reg*/
	void createFinish() {
		RootPanel.get().clear();

		AbsolutePanel headPanel = createHead();
		RootPanel.get().add(headPanel, 20, 10);

		HTML html_finish = new HTML("", true);
		html_finish.setSize("675px", "88px");
		String htmlString = "<Div style='font-size:15pt;font-weight:bold'>Thanks for Register</Div>"
				+ "<div style='font:10pt; word-wrap:break-word'><br>You can sign in "
				+ "<a href='http://rock.njuftp.org:8080/user/User.html' target='_self'>here</a>.</div>";
		html_finish.setHTML(htmlString);
		RootPanel.get().add(html_finish, 20, 100);
	}
}
