package com.ics.tcg.web.user.client.panels;

import java.util.Date;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ics.tcg.web.user.client.db.User_Client;

@SuppressWarnings("deprecation")
public class Panel_Setting extends DockPanel {

	User_Client temp = new User_Client();

	Panel_Setting setting_panel = this;
	Integer userid = -1;

	/** panels */
	Panel_Overview overview_panel;

	/** widget */
	Label lblaccount = new Label();
	RadioButton radioButton_Male = new RadioButton("radiosex");
	RadioButton radioButton_Female = new RadioButton("radiosex");

	TextBox textBox_Age = new TextBox();
	ListBox year = new ListBox();
	ListBox month = new ListBox();
	ListBox day = new ListBox();
	TextBox textBox_Email = new TextBox();
	TextBox textBox_Tel = new TextBox();
	CheckBox checkBox_mail = new CheckBox();
	CheckBox checkBox_mobile = new CheckBox();

	Panel_Setting(Panel_Overview panel_Overview) {

		overview_panel = panel_Overview;

		userid = overview_panel.userid;

		this.setSize("100%", "90%");

		DOM.setStyleAttribute(this.getElement(), "borderLeft",
				"10px solid #C3D9FF");
		DOM.setStyleAttribute(this.getElement(), "borderBottom",
				"10px solid #C3D9FF");

		// create head
		Label head = createNorth();
		add(head, DockPanel.NORTH);
		setCellHeight(head, "30");

		// create tab
		TabPanel tabPanel = new TabPanel();
		tabPanel.removeStyleName("gwt-TabPanel");
		tabPanel.addStyleName("g-TabPanel");
		tabPanel.getTabBar().removeStyleName("gwt-TabBar");
		tabPanel.getTabBar().addStyleName("g-TabBar");
		tabPanel.getDeckPanel().removeStyleName("gwt-TabPanelBottom");
		tabPanel.getDeckPanel().addStyleName("g-TabPanelBottom");

		tabPanel.setSize("100%", "100%");

		add(tabPanel, DockPanel.CENTER);

		// tab1
		VerticalPanel tab1 = createTab1();
		tabPanel.add(tab1, "General", true);

		// tab2
		VerticalPanel tab2 = createTab2();
		tabPanel.add(tab2, "Calendars", true);

		// tab3
		VerticalPanel tab3 = createTab3();
		tabPanel.add(tab3, "Password", true);

		tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				init();
			}
		});

		tabPanel.selectTab(0);

		this.setHeight(Window.getClientHeight() - 90 + "px");
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				int h = event.getHeight();
				if (setting_panel.isVisible() == true) {
					setting_panel.setHeight(h - 90 + "px");
				}
			}
		});
	}

	public void init() {
		User_Client u = overview_panel.userMain.userClient;

		temp.setAccount(u.getAccount());
		temp.setAge(u.getAge());
		temp.setBirthday((Date) u.getBirthday().clone());
		temp.setBymail(u.isBymail());
		temp.setBymobile(u.isBymobile());
		temp.setEmail(u.getEmail());
		temp.setPassword(u.getPassword());
		temp.setSex(u.getSex());
		temp.setTel(u.getTel());
		temp.setUserid(u.getUserid());

		lblaccount.setText(u.getAccount());
		if (u.getSex() == true) {
			radioButton_Male.setValue(true);
		} else {
			radioButton_Female.setValue(true);
		}
		textBox_Age.setText(Integer.toString(u.getAge()));
		year.setItemSelected(u.getBirthday().getYear() - 49, true);
		month.setItemSelected(u.getBirthday().getMonth() + 1, true);
		day.setItemSelected(u.getBirthday().getDate(), true);
		month.setMultipleSelect(false);
		textBox_Email.setText(u.getEmail());
		textBox_Tel.setText(u.getTel());
		checkBox_mail.setValue(u.isBymail());
		checkBox_mobile.setValue(u.isBymobile());

		this.setHeight(Window.getClientHeight() - 90 + "px");

	}

	/** create north */
	Label createNorth() {
		Label head = new Label("Settings");
		{
			head.setSize("100%", "30");
			DOM.setStyleAttribute(head.getElement(), "paddingTop", "6px");
			DOM.setStyleAttribute(head.getElement(), "paddingBottom", "3px");
			DOM.setStyleAttribute(head.getElement(), "fontFamily",
					"Arial, sans-serif");
			DOM.setStyleAttribute(head.getElement(), "fontSize", "13pt");
			DOM.setStyleAttribute(head.getElement(), "fontWeight", "bold");
			DOM.setStyleAttribute(head.getElement(), "backgroundColor",
					"#C3D9FF");
		}
		return head;
	}

	/** create tab1 */
	VerticalPanel createTab1() {

		final AbsolutePanel content1 = new AbsolutePanel();
		VerticalPanel tab1 = new VerticalPanel();
		tab1.setSize("100%", "100%");
		DOM.setStyleAttribute(tab1.getElement(), "color", "#92c1f0");

		// confirm
		final AbsolutePanel confirm = new AbsolutePanel();
		{
			confirm.setHeight("24");
			confirm.setWidth("100%");
			Hyperlink hyperlink = new Hyperlink("« Back to Calendar", "back");
			DOM.setStyleAttribute(hyperlink.getElement(), "fontSize", "10pt");
			hyperlink.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					overview_panel.calendar_show();
				}
			});
			Button ok = new Button("Save");
			Button cancel = new Button("Cancel");
			DOM.setStyleAttribute(ok.getElement(), "fontSize", "8pt");
			DOM.setStyleAttribute(cancel.getElement(), "fontSize", "8pt");
			ok.removeStyleName("gwt-Button");
			cancel.removeStyleName("gwt-Button");
			ok.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (radioButton_Female.getValue() == true) {
						temp.setSex(false);
					} else {
						temp.setSex(true);
					}
					if (temp.equals(overview_panel.userMain.userClient) == false) {
						overview_panel.user_Service.saveUser(temp,
								new AsyncCallback<String>() {
									@Override
									public void onFailure(Throwable caught) {
										Window.alert("fail to save");
									}

									@Override
									public void onSuccess(String result) {
										overview_panel.userMain.userClient = new User_Client(
												temp);
										overview_panel.calendar_show();
									}
								});
					}
				}
			});
			cancel.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					overview_panel.calendar_show();
				}

			});

			confirm.add(hyperlink, 0, 5);
			confirm.add(ok, 120, 0);
			confirm.add(cancel, 160, 0);

			content1.add(confirm);
			content1.setWidgetPosition(confirm, 10, 5);
		}

		// hr
		final HTMLPanel html = new HTMLPanel("<hr>");
		content1.add(html, 10, 25);

		// content1 account
		FlexTable flexTable_1 = new FlexTable();
		{
			flexTable_1.setHeight("25");
			Label label_1 = new Label("Your Account:");
			label_1.setStyleName("InfoLabel");
			lblaccount.setStyleName("InfoLabel");
			flexTable_1.setWidget(0, 0, label_1);
			flexTable_1.setWidget(0, 1, lblaccount);
			content1.add(flexTable_1, 10, 50);
		}

		final HTMLPanel html_1 = new HTMLPanel("<hr>");
		content1.add(html_1, 10, 75);

		// content2 sex
		FlexTable flexTable_2 = new FlexTable();
		{
			flexTable_2.setHeight("50");
			Label label_1 = new Label("Sex:");
			label_1.addStyleName("InfoLabel");
			DOM.setStyleAttribute(radioButton_Male.getElement(), "fontSize",
					"10pt");
			radioButton_Male.setText("Male");
			DOM.setStyleAttribute(radioButton_Female.getElement(), "fontSize",
					"10pt");
			radioButton_Female.setText("Female");

			flexTable_2.setWidget(0, 0, label_1);
			flexTable_2.setWidget(0, 1, radioButton_Male);
			flexTable_2.setWidget(1, 1, radioButton_Female);
			content1.add(flexTable_2, 10, 85);
		}

		final HTMLPanel html_2 = new HTMLPanel("<hr>");
		content1.add(html_2, 10, 135);

		// content3 age
		FlexTable flexTable_3 = new FlexTable();
		{
			flexTable_3.setHeight("25");
			Label label_3 = new Label("Age:");
			label_3.setStyleName("InfoLabel");
			textBox_Age.setWidth("50");
			DOM.setStyleAttribute(textBox_Age.getElement(), "fontSize", "10pt");
			textBox_Age.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent arg0) {
					temp.setAge(Integer.parseInt(textBox_Age.getValue()));
				}
			});

			flexTable_3.setWidget(0, 0, label_3);
			flexTable_3.setWidget(0, 1, textBox_Age);
			content1.add(flexTable_3, 10, 145);
		}

		final HTMLPanel html_3 = new HTMLPanel("<hr>");
		content1.add(html_3, 10, 170);

		// birthday
		FlexTable flexTable_4 = new FlexTable();
		{
			flexTable_4.setHeight("25");
			Label label_4 = new Label("Birthday:");
			label_4.setStyleName("InfoLabel");

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
			year.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					temp.getBirthday().setYear(
							Integer.parseInt(year.getValue(year
									.getSelectedIndex())) - 1900);
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
					// /////////////////////////////////////////////////////
					temp.getBirthday().setMonth(
							Integer.parseInt(month.getValue(month
									.getSelectedIndex())) - 1);
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
					temp.getBirthday().setDate(
							Integer.parseInt(day.getValue(day
									.getSelectedIndex())));
				}
			});

			horizontalPanel.add(year);
			horizontalPanel.add(month);
			horizontalPanel.add(day);

			flexTable_4.setWidget(0, 0, label_4);
			flexTable_4.setWidget(0, 1, horizontalPanel);
			content1.add(flexTable_4, 10, 180);
		}

		final HTMLPanel html_4 = new HTMLPanel("<hr>");
		content1.add(html_4, 10, 205);

		// content email
		FlexTable flexTable_5 = new FlexTable();
		{
			flexTable_5.setHeight("25");
			Label label_5 = new Label("Email:");
			label_5.setStyleName("InfoLabel");
			textBox_Email.setWidth("200");
			DOM.setStyleAttribute(textBox_Email.getElement(), "fontSize",
					"10pt");
			textBox_Email.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent arg0) {
					temp.setEmail(textBox_Email.getValue());
				}
			});

			flexTable_5.setWidget(0, 0, label_5);
			flexTable_5.setWidget(0, 1, textBox_Email);
			content1.add(flexTable_5, 10, 215);
		}

		final HTMLPanel html_5 = new HTMLPanel("<hr>");
		content1.add(html_5, 10, 240);

		// content telephone
		FlexTable flexTable_6 = new FlexTable();
		{
			flexTable_6.setHeight("25");
			Label label_6 = new Label("Tel:");
			label_6.setStyleName("InfoLabel");
			textBox_Tel.setWidth("200");
			DOM.setStyleAttribute(textBox_Tel.getElement(), "fontSize", "10pt");
			textBox_Tel.addValueChangeHandler(new ValueChangeHandler<String>() {
				@Override
				public void onValueChange(ValueChangeEvent<String> event) {
					temp.setTel(textBox_Tel.getValue());
				}
			});

			flexTable_6.setWidget(0, 0, label_6);
			flexTable_6.setWidget(0, 1, textBox_Tel);
			content1.add(flexTable_6, 10, 250);
		}

		final HTMLPanel html_6 = new HTMLPanel("<hr>");
		content1.add(html_6, 10, 275);

		tab1.add(content1);

		return tab1;
	}

	/** create tab2 */
	VerticalPanel createTab2() {
		VerticalPanel tab2 = new VerticalPanel();
		final AbsolutePanel content2 = new AbsolutePanel();
		tab2.setSize("100%", "100%");
		tab2.addStyleName("TabContent");

		// confirm
		final AbsolutePanel confirm = new AbsolutePanel();
		{
			confirm.setHeight("24");
			confirm.setWidth("100%");
			Hyperlink hyperlink = new Hyperlink("« Back to Calendar", "back");
			DOM.setStyleAttribute(hyperlink.getElement(), "fontSize", "10pt");
			hyperlink.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					overview_panel.calendar_show();
				}

			});
			Button ok = new Button("Save");
			DOM.setStyleAttribute(ok.getElement(), "fontSize", "8pt");
			Button cancel = new Button("Cancel");
			DOM.setStyleAttribute(cancel.getElement(), "fontSize", "8pt");
			ok.removeStyleName("gwt-Button");
			cancel.removeStyleName("gwt-Button");
			ok.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (temp.equals(overview_panel.userMain.userClient) == false) {
						overview_panel.user_Service.saveUser(temp,
								new AsyncCallback<String>() {
									@Override
									public void onFailure(Throwable caught) {
										Window.alert("fail to save");
									}

									@Override
									public void onSuccess(String result) {
										User_Client u = overview_panel.userMain.userClient;
										u.setAccount(temp.getAccount());
										u.setAge(temp.getAge());
										//
										u.setBirthday((Date) temp.getBirthday()
												.clone());
										u.setBymail(temp.isBymail());
										u.setBymobile(temp.isBymobile());
										u.setEmail(temp.getEmail());
										u.setPassword(temp.getPassword());
										u.setSex(temp.getSex());
										u.setTel(temp.getTel());
										u.setUserid(temp.getUserid());

										overview_panel.calendar_show();
									}
								});
					}
				}
			});
			cancel.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					overview_panel.calendar_show();
				}

			});

			confirm.add(hyperlink, 0, 5);
			confirm.add(ok, 120, 0);
			confirm.add(cancel, 160, 0);

			content2.add(confirm);
			content2.setWidgetPosition(confirm, 10, 5);
		}

		final HTMLPanel html = new HTMLPanel("<hr>");
		content2.add(html, 10, 25);

		// notify by
		FlexTable flexTable = new FlexTable();
		{
			Label label = new Label("Notify By:");
			label.setStyleName("InfoLabel");
			DOM.setStyleAttribute(checkBox_mail.getElement(), "fontSize",
					"10pt");
			DOM.setStyleAttribute(checkBox_mobile.getElement(), "fontSize",
					"10pt");
			checkBox_mail.setText("Email");
			checkBox_mobile.setText("Mobile");
			checkBox_mail
					.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
						@Override
						public void onValueChange(
								ValueChangeEvent<Boolean> event) {
							temp.setBymail(checkBox_mail.getValue());
						}
					});
			checkBox_mobile
					.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
						@Override
						public void onValueChange(
								ValueChangeEvent<Boolean> event) {
							temp.setBymobile(checkBox_mobile.getValue());
						}
					});
			flexTable.setWidget(0, 0, label);
			flexTable.setWidget(0, 1, checkBox_mail);
			flexTable.setWidget(1, 1, checkBox_mobile);
			content2.add(flexTable, 10, 50);

		}
		tab2.add(content2);

		return tab2;
	}

	/** create Tab3 */
	VerticalPanel createTab3() {
		VerticalPanel tab3 = new VerticalPanel();
		final AbsolutePanel content3 = new AbsolutePanel();
		tab3.setSize("100%", "100%");
		tab3.addStyleName("TabContent");

		final HTMLPanel html = new HTMLPanel("<hr>");
		content3.add(html, 10, 10);

		FlexTable flexTable = new FlexTable();
		{
			flexTable.setHeight("25");
			Label label = new Label("Password:");
			label.setStyleName("InfoLabel");
			Button button = new Button("Change");
			button.removeStyleName("gwt-Button");
			DOM.setStyleAttribute(button.getElement(), "fontSize", "10pt");

			final TextBox textBox1 = new TextBox();
			final TextBox textBox2 = new TextBox();
			final TextBox textBox3 = new TextBox();
			textBox1.setStyleName("InfoLabel");
			textBox2.setStyleName("InfoLabel");
			textBox3.setStyleName("InfoLabel");
			textBox1.setWidth("100");
			textBox2.setWidth("100");
			textBox3.setWidth("100");
			flexTable.setWidget(1, 1, textBox1);
			flexTable.setWidget(2, 1, textBox2);
			flexTable.setWidget(3, 1, textBox3);

			final Label label1 = new Label("Old Password:");
			final Label label2 = new Label("New Password:");
			final Label label3 = new Label("Confirm:");
			label1.setStyleName("InfoLabel");
			label2.setStyleName("InfoLabel");
			label3.setStyleName("InfoLabel");
			flexTable.setWidget(1, 0, label1);
			flexTable.setWidget(2, 0, label2);
			flexTable.setWidget(3, 0, label3);

			label1.setVisible(false);
			label2.setVisible(false);
			label3.setVisible(false);
			textBox1.setVisible(false);
			textBox2.setVisible(false);
			textBox3.setVisible(false);

			final HorizontalPanel confirm = new HorizontalPanel();
			confirm.setSpacing(10);
			Button ok = new Button("Ok   ");
			Button cancel = new Button("Cancel");
			ok.removeStyleName("gwt-Button");
			cancel.removeStyleName("gwt-Button");
			DOM.setStyleAttribute(ok.getElement(), "fontSize", "10pt");
			DOM.setStyleAttribute(cancel.getElement(), "fontSize", "10pt");

			confirm.add(ok);
			confirm.add(cancel);
			content3.add(confirm, 10, 120);
			confirm.setVisible(false);

			button.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					label1.setVisible(true);
					label2.setVisible(true);
					label3.setVisible(true);
					textBox1.setVisible(true);
					textBox2.setVisible(true);
					textBox3.setVisible(true);
					confirm.setVisible(true);

				}
			});

			flexTable.setWidget(0, 0, label);
			flexTable.setWidget(0, 1, button);
			content3.add(flexTable, 10, 20);
		}

		tab3.add(content3);

		return tab3;
	}

}