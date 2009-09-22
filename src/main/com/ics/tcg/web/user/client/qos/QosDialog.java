package com.ics.tcg.web.user.client.qos;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.ics.tcg.web.user.client.db.User_Service_Client;
import com.ics.tcg.web.user.client.remote.Service_Service;
import com.ics.tcg.web.user.client.remote.Service_ServiceAsync;

public class QosDialog extends DialogBox implements KeyPressHandler,
		ClickHandler {

	int userid = -1;

	User_Service_Client user_service = new User_Service_Client();
	//
	public UIContent content = new UIContent();

	ServiceQosRequirement serviceQosRequirement = new ServiceQosRequirement();
	// part 1
	ServiceRank serviceRank = new ServiceRank();
	RequirementItem requirementItem_ServiceRank = new RequirementItem();

	// part 2
	NoStandardsRequirement noStandardsRequirement = new NoStandardsRequirement();
	ArrayList<RequirementItem> requirementItems_NS = new ArrayList<RequirementItem>();

	// part 3
	UserCustomStandards userCustomStandards = new UserCustomStandards();
	ArrayList<RequirementItem> requirementItems_Standard = new ArrayList<RequirementItem>();

	public int radio_button_group = 0;

	private final Service_ServiceAsync getService = GWT
			.create(Service_Service.class);

	public QosDialog(User_Service_Client selected_service) {

		// set style
		DOM.setStyleAttribute(this.getElement(), "border", "0");
		addStyleName("g-DialogBox");

		this.user_service = selected_service;
		this.userid = selected_service.userid;
		// find the service xml file and parse it
		load();
	}

	public void load() {
		getService.getContent(user_service, new AsyncCallback<UIContent>() {

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(UIContent result) {
				content = result;
				Init();
			}
		});
	}

	// create the widgets
	public void Init() {

		final AbsolutePanel main_Panel = new AbsolutePanel();
		super.add(main_Panel);

		main_Panel.setSize("600px", "400px");

		//
		final ScrollPanel scrollPanel = new ScrollPanel();
		main_Panel.add(scrollPanel, 10, 10);
		scrollPanel.setSize("580px", "350px");

		// block include 3 part
		final VerticalPanel block_Panel = new VerticalPanel();
		block_Panel.setSpacing(5);
		scrollPanel.add(block_Panel);

		// part 1
		{

			DisclosurePanel service_rank_panel = new DisclosurePanel(
					"Service_Rank");
			service_rank_panel.removeStyleName("gwt-DisclosurePanel");
			service_rank_panel.addStyleName("g-DisclosurePanel");
			service_rank_panel.getHeader().setWidth("546");
			service_rank_panel.setWidth("550px");
			service_rank_panel.setOpen(true);
			FlexTable service_rank_item_table = new FlexTable();
			service_rank_item_table.setWidth("550px");
			block_Panel.add(service_rank_panel);

			// service rank has just one contentitem
			final ContentItem contentItem_service_rank = content
					.getContentBlocksList().get(0).getContentItemsList().get(0);

			for (int i = 0; i < contentItem_service_rank.getValueList().size(); i++) {
				String value = contentItem_service_rank.getValueList().get(i);
				// add radiobutton
				final RadioButton radioButton = new RadioButton("group"
						+ radio_button_group);
				radioButton.setText(value);
				if (i == 0 && user_service.qos == null) {
					radioButton.setValue(true);
					requirementItem_ServiceRank.setItemValue(radioButton
							.getText());
				}
				// load value
				if (user_service.qos != null
						&& user_service.qos.getServiceRank().getItem()
								.getItemValue().equals(value)) {

					radioButton.setValue(true);
					requirementItem_ServiceRank.setItemValue(radioButton
							.getText());
				}
				service_rank_item_table.setWidget(1, i + 1, radioButton);
				radioButton
						.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
							@Override
							public void onValueChange(
									ValueChangeEvent<Boolean> event) {
								// set the value of the item
								if (radioButton.getValue() == true) {
									requirementItem_ServiceRank
											.setItemValue(radioButton.getText());
								}
							}
						});
			}
			radio_button_group++;
			service_rank_panel.setContent(service_rank_item_table);

			// save the data
			requirementItem_ServiceRank.setItemName(contentItem_service_rank
					.getItemName());
			requirementItem_ServiceRank.setMetric(contentItem_service_rank
					.getMetric());
			requirementItem_ServiceRank.setMoreSelect(contentItem_service_rank
					.isMoreSelect());
			requirementItem_ServiceRank.setRange(contentItem_service_rank
					.isRange());

		}

		// part 2
		{
			final DisclosurePanel nostandardsrequirementitems_panel = new DisclosurePanel(
					"NoStandardsRequirementItems");
			nostandardsrequirementitems_panel
					.removeStyleName("gwt-DisclosurePanel");
			nostandardsrequirementitems_panel.addStyleName("g-DisclosurePanel");
			nostandardsrequirementitems_panel.getHeader().setWidth("546");
			nostandardsrequirementitems_panel.setWidth("550px");
			block_Panel.add(nostandardsrequirementitems_panel);

			// hold disclosure 2 's content
			VerticalPanel verticalPanel = new VerticalPanel();
			verticalPanel.setWidth("550px");

			ContentBlock NoStandards_contentBlock = content
					.getContentBlocksList().get(1);
			for (int i = 0; i < NoStandards_contentBlock.getContentItemsList()
					.size(); i++) {
				final RequirementItem requirementItem = new RequirementItem();
				requirementItems_NS.add(i, requirementItem);

				ContentItem ns_contentItem = NoStandards_contentBlock
						.getContentItemsList().get(i);
				String name = ns_contentItem.getItemName();

				final FlexTable ns_flexTable = new FlexTable();
				FlexCellFormatter flexCellFormatter = ns_flexTable
						.getFlexCellFormatter();

				// ns_flexTable.setWidth("400px");
				ns_flexTable.setHTML(0, 0, name);
				flexCellFormatter.setColSpan(0, 0, 3);
				flexCellFormatter.setWidth(0, 0, "545");
				DOM.setStyleAttribute(flexCellFormatter.getElement(0, 0),
						"background", "#EEEEEE");

				String itemDisplayMode = ns_contentItem.getItemDisplayMode();

				if (itemDisplayMode.equals("select")) {
					if (ns_contentItem.isMoreSelect == true) {
						final ArrayList<String> arrayList = new ArrayList<String>();
						for (int j = 0; j < ns_contentItem.getValueList()
								.size(); j++) {
							String item_value = ns_contentItem.getValueList()
									.get(j);
							final CheckBox radioButton = new CheckBox("group"
									+ radio_button_group);
							radioButton.setText(item_value);
							ns_flexTable.setWidget(j / 3 + 1, j % 3,
									radioButton);
							// cell width
							flexCellFormatter.setWidth(j / 3 + 1, j % 3, "180");
							radioButton
									.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

										@Override
										public void onValueChange(
												ValueChangeEvent<Boolean> event) {
											if (radioButton.getValue() == true) {
												arrayList.add(radioButton
														.getText());
											} else {
												arrayList.remove(radioButton
														.getText());
											}
											requirementItem
													.setSelectedValues(arrayList);

										}
									});
							// load value
							if (user_service.qos != null
									&& user_service.qos
											.getNoStandardsRequirement()
											.getItems().get(i)
											.getSelectedValues().contains(
													item_value)) {
								radioButton.setValue(true);
							}
							//
						}
						radio_button_group++;
					}
					// single select
					else {
						for (int j = 0; j < ns_contentItem.getValueList()
								.size(); j++) {
							String item_value = ns_contentItem.getValueList()
									.get(j);
							final RadioButton radioButton = new RadioButton(
									"group" + radio_button_group);
							radioButton.setText(item_value);

							ns_flexTable.setWidget(j / 3 + 1, j % 3,
									radioButton);
							// cell width
							flexCellFormatter.setWidth(j / 3 + 1, j % 3, "180");
							radioButton.addClickHandler(new ClickHandler() {
								@Override
								public void onClick(ClickEvent event) {
									requirementItem.setItemValue(radioButton
											.getText());
								}
							});
							// load value
							if (j == 0 && user_service.qos == null) {
								radioButton.setValue(true);
								requirementItem.setItemValue(radioButton
										.getText());
							}
							if (user_service.qos != null
									&& user_service.qos
											.getNoStandardsRequirement()
											.getItems().get(i).getItemValue()
											.equals(item_value)) {
								radioButton.setValue(true);
								requirementItem.setItemValue(radioButton
										.getText());
							}
							//
						}
						radio_button_group++;
					}

				} // input
				else {
					// 2 values
					if (ns_contentItem.isRange() == true) {
						// hold min or max value
						final HorizontalPanel item_panel_min = new HorizontalPanel();
						final HorizontalPanel item_panel_max = new HorizontalPanel();
						item_panel_min.setSize("200px", "24px");
						item_panel_max.setSize("200px", "24px");
						final Label label_min;
						final Label label_max;
						if (ns_contentItem.getMetric() == null)
							label_min = new Label("Min Value");
						else {
							label_min = new Label("Min Value("
									+ ns_contentItem.getMetric() + ")");
						}
						if (ns_contentItem.getMetric() == null)
							label_max = new Label("Max Value");
						else {
							label_max = new Label("Max Value("
									+ ns_contentItem.getMetric() + ")");
						}

						final TextBox textBox_min = new TextBox();
						final TextBox textBox_max = new TextBox();
						textBox_min.setWidth("60");
						textBox_max.setWidth("60");
						textBox_min
								.addValueChangeHandler(new ValueChangeHandler<String>() {
									@Override
									public void onValueChange(
											ValueChangeEvent<String> event) {
										requirementItem
												.setLowerBoundValue(textBox_min
														.getText());

									}
								});
						textBox_max
								.addValueChangeHandler(new ValueChangeHandler<String>() {
									@Override
									public void onValueChange(
											ValueChangeEvent<String> event) {
										requirementItem
												.setUpperBoundValue(textBox_max
														.getText());

									}
								});

						// load value
						if (user_service.qos != null
								&& user_service.qos.getNoStandardsRequirement()
										.getItems().get(i).getLowerBoundValue() != null) {
							textBox_min.setText(user_service.qos
									.getNoStandardsRequirement().getItems()
									.get(i).getLowerBoundValue());
						}
						if (user_service.qos != null
								&& user_service.qos.getNoStandardsRequirement()
										.getItems().get(i).getUpperBoundValue() != null) {
							textBox_max.setText(user_service.qos
									.getNoStandardsRequirement().getItems()
									.get(i).getUpperBoundValue());
						}
						//

						item_panel_min.add(label_min);
						item_panel_max.add(label_max);
						item_panel_min.add(textBox_min);
						item_panel_max.add(textBox_max);

						ns_flexTable.setWidget(1, 0, item_panel_min);
						ns_flexTable.setWidget(1, 1, item_panel_max);
						// set width
						flexCellFormatter.setWidth(1, 0, "50%");
						flexCellFormatter.setWidth(1, 1, "50%");

					}
					// 1 value
					else {
						final HorizontalPanel item_horizontalPanel = new HorizontalPanel();
						item_horizontalPanel.setSize("200px", "24px");
						final Label label_value;
						if (ns_contentItem.getMetric() == null) {
							label_value = new Label("Value:");
						} else {
							label_value = new Label("Value("
									+ ns_contentItem.getMetric() + "):");
						}
						final TextBox textBox = new TextBox();
						textBox
								.addValueChangeHandler(new ValueChangeHandler<String>() {
									@Override
									public void onValueChange(
											ValueChangeEvent<String> event) {
										requirementItem.setItemValue(textBox
												.getText());
									}
								});

						// load value
						if (user_service.qos != null
								&& user_service.qos.getNoStandardsRequirement()
										.getItems().get(i).getItemValue() != null) {
							textBox.setText(user_service.qos
									.getNoStandardsRequirement().getItems()
									.get(i).getItemValue());
						}

						item_horizontalPanel.add(label_value);
						item_horizontalPanel.add(textBox);

						textBox.setWidth("100");
						ns_flexTable.setWidget(1, 0, item_horizontalPanel);
					}
				}
				verticalPanel.add(ns_flexTable);
				// save
				requirementItem.setItemName(name);
				requirementItem.setMetric(ns_contentItem.getMetric());
				requirementItem.setMoreSelect(ns_contentItem.isMoreSelect());
				requirementItem.setRange(ns_contentItem.isRange());
			}

			nostandardsrequirementitems_panel.setContent(verticalPanel);
		}

		// part 3
		{
			final DisclosurePanel usercustomstandardsitems_panel = new DisclosurePanel(
					"UserCustomStandardsItems");
			usercustomstandardsitems_panel
					.removeStyleName("gwt-DisclosurePanel");
			usercustomstandardsitems_panel.addStyleName("g-DisclosurePanel");
			usercustomstandardsitems_panel.getHeader().setWidth("546");
			usercustomstandardsitems_panel.setWidth("550px");

			usercustomstandardsitems_panel.setOpen(false);
			usercustomstandardsitems_panel.setWidth("550px");
			block_Panel.add(usercustomstandardsitems_panel);

			VerticalPanel verticalPanel = new VerticalPanel();
			verticalPanel.setWidth("550px");

			ContentBlock standard_contentBlock = content.getContentBlocksList()
					.get(2);
			for (int i = 0; i < standard_contentBlock.getContentItemsList()
					.size(); i++) {

				final RequirementItem requirementItem = new RequirementItem();
				requirementItems_Standard.add(i, requirementItem);

				ContentItem standard_contentItem = standard_contentBlock
						.getContentItemsList().get(i);
				String name = standard_contentItem.getItemName();
				final FlexTable standard_flexTable = new FlexTable();
				FlexCellFormatter flexCellFormatter = standard_flexTable
						.getFlexCellFormatter();
				standard_flexTable.setHTML(0, 0, name);
				flexCellFormatter.setColSpan(0, 0, 3);
				flexCellFormatter.setWidth(0, 0, "545");
				DOM.setStyleAttribute(flexCellFormatter.getElement(0, 0),
						"background", "#EEEEEE");

				String itemDisplayMode = standard_contentItem
						.getItemDisplayMode();
				// select
				if (itemDisplayMode.equals("select")) {
					// more select
					if (standard_contentItem.isMoreSelect == true) {
						final ArrayList<String> arrayList = new ArrayList<String>();
						for (int j = 0; j < standard_contentItem.getValueList()
								.size(); j++) {
							String item_value = standard_contentItem
									.getValueList().get(j);
							final CheckBox radioButton = new CheckBox("group"
									+ radio_button_group);
							radioButton.setText(item_value);
							standard_flexTable.setWidget(j / 2 + 1, j % 2,
									radioButton);
							radioButton
									.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

										@Override
										public void onValueChange(
												ValueChangeEvent<Boolean> event) {
											if (radioButton.getValue() == true) {
												arrayList.add(radioButton
														.getText());
											} else {
												arrayList.remove(radioButton
														.getText());
											}
											requirementItem
													.setSelectedValues(arrayList);

										}
									});
							// load value
							if (user_service.qos != null
									&& user_service.qos
											.getUserCustomStandards()
											.getItems().get(i)
											.getSelectedValues().contains(
													item_value)) {
								radioButton.setValue(true);
							}
							//
						}
						radio_button_group++;
					}
					// single select
					else {
						for (int j = 0; j < standard_contentItem.getValueList()
								.size(); j++) {
							String item_value = standard_contentItem
									.getValueList().get(j);
							final RadioButton radioButton = new RadioButton(
									"group" + radio_button_group);
							radioButton.setText(item_value);
							standard_flexTable.setWidget(j / 2 + 1, j % 2,
									radioButton);

							radioButton
									.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
										@Override
										public void onValueChange(
												ValueChangeEvent<Boolean> event) {
											// set the value of the item
											if (radioButton.getValue() == true) {
												requirementItem
														.setItemValue(radioButton
																.getText());
											}
										}
									});

							// load value
							if (j == 0 && user_service.qos == null) {
								radioButton.setValue(true);
								requirementItem.setItemValue(radioButton
										.getText());
							}
							if (user_service.qos != null
									&& user_service.qos
											.getUserCustomStandards()
											.getItems().get(i).getItemValue()
											.equals(item_value)) {
								System.out.println(user_service.qos
										.getServiceRank().getItem()
										.getItemValue());
								radioButton.setValue(true);
								requirementItem.setItemValue(radioButton
										.getText());
							}
							//
						}
						radio_button_group++;
					}

				}
				// input
				else {
					// 2 values
					if (standard_contentItem.isRange() == true) {
						// hold min or max value
						final HorizontalPanel item_panel_min = new HorizontalPanel();
						final HorizontalPanel item_panel_max = new HorizontalPanel();
						item_panel_min.setSize("200px", "24px");
						item_panel_max.setSize("200px", "24px");
						final Label label_min;
						final Label label_max;
						if (standard_contentItem.getMetric() == null)
							label_min = new Label("Min Value");
						else {
							label_min = new Label(

							"Min Value(" + standard_contentItem.getMetric()
									+ ")");
						}
						if (standard_contentItem.getMetric() == null)
							label_max = new Label("Max Value");
						else {
							label_max = new Label("Max Value("
									+ standard_contentItem.getMetric() + ")");
						}

						final TextBox textBox_min = new TextBox();
						final TextBox textBox_max = new TextBox();
						textBox_min.setWidth("60");
						textBox_max.setWidth("60");
						textBox_min
								.addValueChangeHandler(new ValueChangeHandler<String>() {
									@Override
									public void onValueChange(
											ValueChangeEvent<String> event) {
										requirementItem
												.setLowerBoundValue(textBox_min
														.getText());
									}
								});
						textBox_max
								.addValueChangeHandler(new ValueChangeHandler<String>() {
									@Override
									public void onValueChange(
											ValueChangeEvent<String> event) {
										requirementItem
												.setUpperBoundValue(textBox_max
														.getText());
									}
								});

						item_panel_min.add(label_min);
						item_panel_max.add(label_max);
						item_panel_min.add(textBox_min);
						item_panel_max.add(textBox_max);

						standard_flexTable.setWidget(1, 0, item_panel_min);
						standard_flexTable.setWidget(1, 1, item_panel_max);

						// load value
						if (user_service.qos != null
								&& user_service.qos.getUserCustomStandards()
										.getItems().get(i).getLowerBoundValue() != null) {
							textBox_min.setText(user_service.qos
									.getUserCustomStandards().getItems().get(i)
									.getLowerBoundValue());
						}
						if (user_service.qos != null
								&& user_service.qos.getUserCustomStandards()
										.getItems().get(i).getUpperBoundValue() != null) {
							textBox_max.setText(user_service.qos
									.getUserCustomStandards().getItems().get(i)
									.getUpperBoundValue());
						}
						//
					}
					// 1 value
					else {
						final HorizontalPanel item_horizontalPanel = new HorizontalPanel();
						item_horizontalPanel.setSize("200px", "24px");
						final Label label_value;
						if (standard_contentItem.getMetric() == null) {
							label_value = new Label("Value:");
						} else {
							label_value = new Label("Value("
									+ standard_contentItem.getMetric() + "):");
						}
						final TextBox textBox = new TextBox();
						textBox
								.addValueChangeHandler(new ValueChangeHandler<String>() {
									@Override
									public void onValueChange(
											ValueChangeEvent<String> event) {
										requirementItem.setItemValue(textBox
												.getText());
									}
								});

						item_horizontalPanel.add(label_value);
						item_horizontalPanel.add(textBox);

						textBox.setWidth("100");

						standard_flexTable
								.setWidget(1, 0, item_horizontalPanel);

						// load value
						if (user_service.qos != null
								&& user_service.qos.getUserCustomStandards()
										.getItems().get(i).getItemValue() != null) {
							textBox.setText(user_service.qos
									.getUserCustomStandards().getItems().get(i)
									.getItemValue());
						}
					}
				}
				verticalPanel.add(standard_flexTable);
				// save
				requirementItem.setItemName(name);
				requirementItem.setMetric(standard_contentItem.getMetric());
				requirementItem.setMoreSelect(standard_contentItem
						.isMoreSelect());
				requirementItem.setRange(standard_contentItem.isRange());

			}

			usercustomstandardsitems_panel.setContent(verticalPanel);
		}

		// OK,Cancel buttons
		HorizontalPanel confirm_button = new HorizontalPanel();
		Button button1 = new Button("Save");
		button1.removeStyleName("gwt-Button");
		DOM.setStyleAttribute(button1.getElement(), "fontSize", "9pt");
		button1.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				save();

				User_Service_Client user_ServiceC = new User_Service_Client();

				user_ServiceC.id = user_service.id;
				user_ServiceC.userid = user_service.userid;
				user_ServiceC.abserviceid = user_service.abserviceid;
				user_ServiceC.abservicename = user_service.abservicename;
				user_ServiceC.qos = serviceQosRequirement;

				user_service.qos = serviceQosRequirement;

				getService.saveQos(user_ServiceC, new AsyncCallback<String>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("fail");
					}

					@Override
					public void onSuccess(String result) {
						Window.alert("success");
						hide();
					}
				});
			}
		});
		Button button2 = new Button("Cancel");
		button2.removeStyleName("gwt-Button");
		DOM.setStyleAttribute(button2.getElement(), "fontSize", "9pt");
		button2.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		confirm_button.setSpacing(10);
		confirm_button.add(button1);
		confirm_button.add(button2);
		main_Panel.add(confirm_button, 50, 360);
	}

	public void save() {
		// part 1
		serviceRank.setItem(requirementItem_ServiceRank);
		// part 2
		userCustomStandards.setItems(requirementItems_Standard);
		// part 3
		noStandardsRequirement.setItems(requirementItems_NS);

		serviceQosRequirement.setServiceName(content.getServiceName());
		serviceQosRequirement.setServiceRank(serviceRank);
		serviceQosRequirement.setUserCustomStandards(userCustomStandards);
		serviceQosRequirement.setNoStandardsRequirement(noStandardsRequirement);
		System.out.println("save" + requirementItem_ServiceRank.getItemValue());
	}

	@Override
	public void onKeyPress(KeyPressEvent event) {

	}

	@Override
	public void onClick(ClickEvent event) {

	}

}
