package com.ics.tcg.web.user.client.panels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.gwtlib.client.table.ColumnLayout;
import org.gwtlib.client.table.ContentProvider;
import org.gwtlib.client.table.Row;
import org.gwtlib.client.table.Rows;
import org.gwtlib.client.table.ui.Column;
import org.gwtlib.client.table.ui.PagingBar;
import org.gwtlib.client.table.ui.PagingTable;
import org.gwtlib.client.table.ui.SourcesTableEvents;
import org.gwtlib.client.table.ui.TableListenerAdapter;
import org.gwtlib.client.table.ui.renderer.HyperlinkRenderer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.ics.tcg.web.user.client.db.AppointmentMod;
import com.ics.tcg.web.user.client.db.Calendar_Client;
import com.ics.tcg.web.user.client.db.Records_Client;

import de.techbits.gwt.widgets.client.StarRating;

public class Panel_Rating extends AbsolutePanel {

	/** data */
	ArrayList<Calendar_Client> undoneList = new ArrayList<Calendar_Client>();
	ArrayList<Calendar_Client> doneList = new ArrayList<Calendar_Client>();
	StarRating sr;
	Integer selectrecordid;
	Records_Client selectRecord;

	/** panels */
	public TabPanel tabPanel;
	Panel_Overview panel_Overview;
	public Panel_Rating panel_Rating = this;
	AbsolutePanel content_undone;
	AbsolutePanel grid_panel_undone;
	AbsolutePanel content_done;
	AbsolutePanel grid_panel_done;
	AbsolutePanel scorePanel;
	Hyperlink hyperlink_submit;
	PopupPanel popupPanel;

	/** main */
	public Panel_Rating(Panel_Overview overviewPanel) {

		panel_Overview = overviewPanel;

		// set Head
		Label head = new Label("Events");
		{
			head.setSize("100%", "30");
			DOM.setStyleAttribute(head.getElement(), "paddingTop", "6px");
			DOM.setStyleAttribute(head.getElement(), "paddingBottom", "3px");
			DOM.setStyleAttribute(head.getElement(), "fontSize", "13pt");
			DOM.setStyleAttribute(head.getElement(), "fontWeight", "bold");
			DOM.setStyleAttribute(head.getElement(), "backgroundColor",
					"#C3D9FF");
		}
		DOM.setStyleAttribute(this.getElement(), "borderLeft",
				"10px solid #C3D9FF");
		DOM.setStyleAttribute(this.getElement(), "borderBottom",
				"10px solid #C3D9FF");
		add(head, 0, 0);

		// set table panel
		tabPanel = new TabPanel();
		tabPanel.removeStyleName("gwt-TabPanel");
		tabPanel.addStyleName("g-TabPanel");
		tabPanel.getTabBar().removeStyleName("gwt-TabBar");
		tabPanel.getTabBar().addStyleName("g-TabBar");
		tabPanel.getDeckPanel().removeStyleName("gwt-TabPanelBottom");
		tabPanel.getDeckPanel().addStyleName("g-TabPanelBottom");
		tabPanel.setWidth("800");
		tabPanel.setHeight("600");
		add(tabPanel, 0, 30);

		// set first tab
		content_undone = new AbsolutePanel();
		content_undone.setSize("800", "600");
		tabPanel.add(content_undone, "Undone", true);
		{
			// hyperlink
			Hyperlink hyperlink = new Hyperlink("« Back to Calendar", "back");
			hyperlink.setHeight("20");
			DOM.setStyleAttribute(hyperlink.getElement(), "fontSize", "10pt");
			hyperlink.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					tabPanel.selectTab(0);
					panel_Overview.calendar_show();
				}
			});
			content_undone.add(hyperlink, 10, 5);
			// hr
			final HTMLPanel html = new HTMLPanel("<hr>");
			content_undone.add(html, 10, 18);
			// grid
			grid_panel_undone = createUndoneGrid();
			content_undone.add(grid_panel_undone, 10, 30);
			// ////
			grid_panel_undone.setHeight("400");
		}

		// set second tab
		content_done = new AbsolutePanel();
		content_done.setSize("800", "600");
		tabPanel.add(content_done, "Done", true);
		{
			// hyperlink
			Hyperlink hyperlink = new Hyperlink("« Back to Calendar", "back");
			hyperlink.setHeight("20");
			DOM.setStyleAttribute(hyperlink.getElement(), "fontSize", "10pt");
			hyperlink.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					tabPanel.selectTab(0);
					panel_Overview.calendar_show();
				}
			});
			content_done.add(hyperlink, 10, 5);
			// hr
			final HTMLPanel html = new HTMLPanel("<hr>");
			content_done.add(html, 10, 18);
			// grid
			grid_panel_done = createDoneGrid();
			content_done.add(grid_panel_done, 10, 30);
			grid_panel_done.setSize("720", "400");
			// create score
			scorePanel = createScorePanel();
			// Create popup
			popupPanel = new PopupPanel(true, false);
			popupPanel.setWidget(scorePanel);
			popupPanel.removeStyleName("gwt-PopupPanel");
			DOM.setStyleAttribute(popupPanel.getElement(), "border", "0px");
		}

		// end
		tabPanel.selectTab(0);
		setHeight(Window.getClientHeight() - 90 + "px");
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				int h = event.getHeight();
				if (panel_Rating.isVisible() == true) {
					panel_Rating.setHeight(h - 90 + "px");
					tabPanel.setWidth("100%");
					tabPanel.setHeight(Window.getClientHeight() - 130 + "px");

					if (tabPanel.getTabBar().getSelectedTab() == 0) {
						// set tab1 content
						content_undone.setSize(Integer.toString(Window
								.getClientWidth() - 210), Window
								.getClientHeight()
								- 155 + "px");
						// set grid in tab1 content
						// grid_panel_undone.setHeight(Window.getClientHeight()
						// - 200 + "px");
						grid_panel_undone.setWidth(Integer.toString(Window
								.getClientWidth() - 250 > 740 ? (Window
								.getClientWidth() - 250) : 740));
						// ((PagingTable) grid_panel_undone.getWidget(0))
						// .setHeight(Window.getClientHeight() - 220
						// + "px");
						((PagingTable) grid_panel_undone.getWidget(0))
								.setWidth("100%");
						if (((PagingTable) grid_panel_undone.getWidget(0))
								.getPagingBar().getSize() != 0) {
							((PagingTable) grid_panel_undone.getWidget(0))
									.update();
						}
					}
					if (tabPanel.getTabBar().getSelectedTab() == 1) {
						content_done.setSize(Integer.toString(tabPanel
								.getOffsetWidth()), Integer.toString(tabPanel
								.getOffsetHeight()));
					}

				}
			}
		});
		tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {

			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				if (event.getSelectedItem() == 0) {
					content_undone.setSize(Integer.toString(Window
							.getClientWidth() - 200), Integer.toString(tabPanel
							.getOffsetHeight()));
					// grid_panel_undone.setHeight(Window.getClientHeight() -
					// 200
					// + "px");
					// ((PagingTable) grid_panel_undone.getWidget(0))
					// .setHeight(Window.getClientHeight() - 220 + "px");
					if (((PagingTable) (grid_panel_undone.getWidget(0)))
							.getPagingBar().getSize() != 0) {
						((PagingTable) grid_panel_undone.getWidget(0)).update();
					}
				}

				if (event.getSelectedItem() == 1) {

					content_done.setSize(Integer.toString(tabPanel
							.getOffsetWidth()), Integer.toString(tabPanel
							.getOffsetHeight()));
					// grid_panel_done.setHeight(Window.getClientHeight() - 200
					// + "px");
					// ((PagingTable) grid_panel_done.getWidget(0))
					// .setHeight(Integer.toString(grid_panel_done
					// .getOffsetHeight() - 20));
					if (((PagingTable) (grid_panel_done.getWidget(0)))
							.getPagingBar().getSize() != 0) {
						((PagingTable) grid_panel_done.getWidget(0)).update();
					}

				}
			}
		});

	}

	/** create undone grid */
	private AbsolutePanel createUndoneGrid() {
		final AbsolutePanel grid = new AbsolutePanel();
		grid.setSize("95%", "400");
		DOM
				.setStyleAttribute(grid.getElement(), "border",
						"10px solid #1ba823");
		PagingTable table_undone = createUndoneTable();
		table_undone.setSize("700", "380");
		grid.add(table_undone, 0, 0);
		return grid;
	}

	/** create done grid */
	private AbsolutePanel createDoneGrid() {
		final AbsolutePanel grid = new AbsolutePanel();
		grid.setSize("95%", "400");
		DOM
				.setStyleAttribute(grid.getElement(), "border",
						"10px solid #1ba823");
		PagingTable table_done = createDoneTable();
		table_done.setSize("700", "380");
		grid.add(table_done, 0, 0);
		return grid;
	}

	/** create undone Table */
	private PagingTable createUndoneTable() {
		// Set up the columns we want to be displayed
		final Column[] columns = {
				new Column(0, true, "Start", "20%"),
				new Column(1, true, "End", "20%"),
				new Column(2, true, "What", "20%"),
				new Column(3, false, "Description", "20%"),
				new Column(4, false, "Link", "19%", new HyperlinkRenderer(
						"find detail")), new Column(5, false, "id", "1%") };
		// Now configure the table
		ColumnLayout layout = new ColumnLayout(columns);
		final PagingBar pagingBar = new PagingBar(0, 0, 10, new int[] { 5, 10,
				20, 50, 100 });
		final PagingTable table = new PagingTable(layout, pagingBar);
		table.show(5, false);
		return table;
	}

	/** create done Table */
	private PagingTable createDoneTable() {
		// Set up the columns we want to be displayed
		final Column[] columns = {
				new Column(0, true, "Start", "15%"),
				new Column(1, true, "End", "15%"),
				new Column(2, true, "What", "25%"),
				new Column(3, false, "Description", "30%"),
				new Column(4, false, "Link", "15%", new HyperlinkRenderer(
						"find detail")), new Column(5, false, "id", "0%") };
		// Now configure the table
		ColumnLayout layout = new ColumnLayout(columns);
		final PagingBar pagingBar = new PagingBar(0, 0, 10, new int[] { 5, 10,
				20, 50, 100 });
		final PagingTable table = new PagingTable(layout, pagingBar);
		table.show(5, false);
		return table;
	}

	/** create undone data */
	void createUndoData(final PagingTable table) {
		if (table.getPagingBar().getSize() != 0) {
			table.reset();
		}
		// Generate some semi-random data for our example
		panel_Overview.calendar_Service.getUndoneCalendar(
				panel_Overview.userid,
				new AsyncCallback<List<Calendar_Client>>() {
					@Override
					public void onFailure(Throwable caught) {
						table.reset();
						Window.alert("fail to get undone calendars");
					}

					@Override
					public void onSuccess(List<Calendar_Client> result) {
						if (result != null) {
							undoneList = (ArrayList<Calendar_Client>) result;
							// row data
							final Row[] rows = new Row[undoneList.size()];
							for (int i = 0; i < rows.length; ++i) {
								Date startime = undoneList.get(i)
										.getStartTime();
								Date endtime = undoneList.get(i).getEndTime();
								String what = undoneList.get(i).getEventname();
								String des = undoneList.get(i).getDes();
								Integer id = undoneList.get(i).getCalendarid();
								rows[i] = new Row(i, new Object[] { startime,
										endtime, what, des, "Goto", id });
							}

							// set pagingBar's size
							table.getPagingBar().setSize(undoneList.size());

							// ContentProvider
							ContentProvider provider = new ContentProvider() {
								// Simulate retrieval of sample data, in
								// requested sort order
								public void load(int begin, int end,
										final int sortId, boolean ascending) {
									final int sign = ascending ? 1 : -1;
									Row[] tmp = new Row[rows.length];
									for (int i = 0; i < rows.length; ++i)
										tmp[i] = rows[i];
									switch (sortId) {
									case 0:
										Arrays.sort(tmp, new Comparator<Row>() {
											public int compare(Row o1, Row o2) {
												Date v1 = (Date) o1
														.getValue(sortId);
												Date v2 = (Date) o2
														.getValue(sortId);
												return sign
														* (v1.compareTo(v2));
											}
										});
										break;
									case 1:
										Arrays.sort(tmp, new Comparator<Row>() {
											public int compare(Row o1, Row o2) {
												Date v1 = (Date) o1
														.getValue(sortId);
												Date v2 = (Date) o2
														.getValue(sortId);
												return sign
														* (v1.compareTo(v2));
											}
										});
										break;
									case 2:
										Arrays.sort(tmp, new Comparator<Row>() {
											public int compare(Row o1, Row o2) {
												String v1 = ((String) o1
														.getValue(sortId));
												String v2 = ((String) o2
														.getValue(sortId));
												return sign
														* (v1.compareTo(v2));
											}
										});
										break;
									default:
										break;
									}
									Row[] srows = new Row[Math.min(end - begin,
											tmp.length - begin)];
									for (int i = 0; i < srows.length; ++i)
										srows[i] = tmp[begin + i];
									table.onSuccess(new Rows(srows, begin,
											sortId, ascending));
								}
							};
							table.setContentProvider(provider);

							// TableListener
							table.addTableListener(new TableListenerAdapter() {

								public void onCellClicked(
										SourcesTableEvents sender, Row row,
										Column column) {
									// for(int i = 0; i < columns.length; ++i)
									// columns[i].setState(Column.State.NONE);
									// column.setState(Column.State.SELECT);
								}

								public void onRowClicked(
										SourcesTableEvents sender, Row row) {
									GWT.log("Row clicked (id " + row.getId()
											+ ")", null);
									for (int i = 0; i < rows.length; ++i)
										rows[i].setState(Row.State.NONE);
									row.setState(Row.State.SELECT);
									table.refreshRowState();
								}

								public void onClick(SourcesTableEvents sender,
										Row row, Column column, Widget widget) {
									GWT.log("Renderer widget clicked", null);
									if (widget instanceof Button) {
										Window.alert(((Button) widget)
												.getHTML());
									} else if (widget instanceof Hyperlink) {
										for (int i = 0; i < panel_Overview.calendarPanel.dayView
												.getAppointmentCount(); i++) {
											AppointmentMod temp = (AppointmentMod) panel_Overview.calendarPanel.dayView
													.getAppointmentAtIndex(i);
											if (temp.calendarClient
													.getCalendarid().equals(
															row.getValue(5))) {
												panel_Overview.calendarPanel.workflowApp = temp;
												panel_Overview.workflow_show();
												panel_Overview.workflowpanel
														.onShow();
												break;
											}
										}
									} else if (widget instanceof Image) {
										Window.alert(((Image) widget).getUrl());
									}
								}

								public void onChange(SourcesTableEvents sender,
										Row row, Column column, Widget widget) {
									GWT.log("Renderer widget changed", null);
									if (widget instanceof ListBox) {
										ListBox listBox = (ListBox) widget;
										row.setValue(6, listBox
												.getValue(listBox
														.getSelectedIndex()));
									} else if (widget instanceof TextBox) {
										row.setValue(8, ((TextBox) widget)
												.getText());
									}
								}
							});
							table.update();
						}
					}
				});
	}

	/** create done data */
	void createDoneData(final PagingTable table) {
		if (table.getPagingBar().getSize() != 0) {
			table.reset();
		}
		// Generate some semi-random data for our example
		panel_Overview.calendar_Service.getDoneCalendar(panel_Overview.userid,
				new AsyncCallback<List<Calendar_Client>>() {
					@Override
					public void onFailure(Throwable caught) {
						table.reset();
						Window.alert("fail to get undone calendars");
					}

					@Override
					public void onSuccess(List<Calendar_Client> result) {
						if (result != null) {
							doneList = (ArrayList<Calendar_Client>) result;
							// row data
							final Row[] rows = new Row[doneList.size()];
							for (int i = 0; i < rows.length; ++i) {
								Date startime = doneList.get(i).getStartTime();
								Date endtime = doneList.get(i).getEndTime();
								String what = doneList.get(i).getEventname();
								String des = doneList.get(i).getDes();
								Integer id = doneList.get(i).getCalendarid();
								rows[i] = new Row(i, new Object[] { startime,
										endtime, what, des, "Goto", id });
							}

							// set pagingBar's size
							table.getPagingBar().setSize(doneList.size());

							// ContentProvider
							ContentProvider provider = new ContentProvider() {
								// Simulate retrieval of sample data, in
								// requested sort order
								public void load(int begin, int end,
										final int sortId, boolean ascending) {
									final int sign = ascending ? 1 : -1;
									Row[] tmp = new Row[rows.length];
									for (int i = 0; i < rows.length; ++i)
										tmp[i] = rows[i];
									switch (sortId) {
									case 0:
										Arrays.sort(tmp, new Comparator<Row>() {
											public int compare(Row o1, Row o2) {
												Date v1 = (Date) o1
														.getValue(sortId);
												Date v2 = (Date) o2
														.getValue(sortId);
												return sign
														* (v1.compareTo(v2));
											}
										});
										break;
									case 1:
										Arrays.sort(tmp, new Comparator<Row>() {
											public int compare(Row o1, Row o2) {
												Date v1 = (Date) o1
														.getValue(sortId);
												Date v2 = (Date) o2
														.getValue(sortId);
												return sign
														* (v1.compareTo(v2));
											}
										});
										break;
									case 2:
										Arrays.sort(tmp, new Comparator<Row>() {
											public int compare(Row o1, Row o2) {
												String v1 = ((String) o1
														.getValue(sortId));
												String v2 = ((String) o2
														.getValue(sortId));
												return sign
														* (v1.compareTo(v2));
											}
										});
										break;
									default:
										break;
									}
									Row[] srows = new Row[Math.min(end - begin,
											tmp.length - begin)];
									for (int i = 0; i < srows.length; ++i)
										srows[i] = tmp[begin + i];
									table.onSuccess(new Rows(srows, begin,
											sortId, ascending));
								}
							};
							table.setContentProvider(provider);

							// TableListener
							table.addTableListener(new TableListenerAdapter() {

								public void onCellClicked(
										SourcesTableEvents sender, Row row,
										Column column) {
									// for(int i = 0; i < columns.length; ++i)
									// columns[i].setState(Column.State.NONE);
									// column.setState(Column.State.SELECT);
								}

								public void onRowClicked(
										SourcesTableEvents sender, Row row) {
									GWT.log("Row clicked (id " + row.getId()
											+ ")", null);
									for (int i = 0; i < rows.length; ++i)
										rows[i].setState(Row.State.NONE);
									row.setState(Row.State.SELECT);
									table.refreshRowState();
								}

								public void onClick(SourcesTableEvents sender,
										Row row, Column column, Widget widget) {
									GWT.log("Renderer widget clicked", null);
									if (widget instanceof Button) {
										Window.alert(((Button) widget)
												.getHTML());
									} else if (widget instanceof Hyperlink) {
										Integer calendarid = (Integer) row
												.getValue(5);
										loadScore(calendarid);
										popupPanel.setPopupPosition(widget
												.getAbsoluteLeft()
												+ -600,
												widget.getAbsoluteTop() + 25);
										popupPanel.show();

									} else if (widget instanceof Image) {
										Window.alert(((Image) widget).getUrl());
									}
								}

								public void onChange(SourcesTableEvents sender,
										Row row, Column column, Widget widget) {
									GWT.log("Renderer widget changed", null);
									if (widget instanceof ListBox) {
										ListBox listBox = (ListBox) widget;
										row.setValue(6, listBox
												.getValue(listBox
														.getSelectedIndex()));
									} else if (widget instanceof TextBox) {
										row.setValue(8, ((TextBox) widget)
												.getText());
									}
								}
							});
							table.update();
							table.refreshRowState();
						}
					}
				});
	}

	/** create score panel */

	void loadScore(int calendarid) {
		panel_Overview.calendar_Service.getRecords(calendarid,
				new AsyncCallback<List<Records_Client>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("fail");
					}

					@Override
					public void onSuccess(List<Records_Client> result) {
						if (result != null) {
							final HashMap<Integer, ArrayList<Records_Client>> hm = sortServices(result);
							final ListBox listBox = (ListBox) (scorePanel
									.getWidget(1));
							listBox.clear();
							final VerticalPanel left = (VerticalPanel) (scorePanel
									.getWidget(2));
							left.clear();
							{
								Label label_abservice = new Label("");
								label_abservice
										.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
								label_abservice.setSize("145", "18");
								DOM.setStyleAttribute(label_abservice
										.getElement(), "fontSize", "10pt");
								DOM.setStyleAttribute(label_abservice
										.getElement(), "background", "#DDDDDD");
								DOM.setStyleAttribute(label_abservice
										.getElement(), "padding", "2px");
								left.add(label_abservice);

							}
							// flextable
							final FlexTable flexTable = (FlexTable) (scorePanel
									.getWidget(0));
							// clear
							{
								((Label) (flexTable.getWidget(0, 1)))
										.setText("");
								((Label) (flexTable.getWidget(1, 1)))
										.setText("");
								((Label) (flexTable.getWidget(3, 0)))
										.setText("");
								((Label) (flexTable.getWidget(3, 1)))
										.setText("");
							}
							java.util.Iterator<ArrayList<Records_Client>> iterator = hm
									.values().iterator();
							while (iterator.hasNext()) {
								Records_Client rClient = iterator.next().get(0);
								listBox.addItem(rClient.getAbservicename(),
										Integer.toString(rClient.getCid()));
							}
							listBox.setSelectedIndex(-1);
							listBox.addChangeHandler(new ChangeHandler() {
								@Override
								public void onChange(ChangeEvent event) {
									final ArrayList<Records_Client> list = hm
											.get(Integer
													.parseInt((listBox
															.getValue(listBox
																	.getSelectedIndex()))));
									left.clear();
									Label label_abservice = new Label((listBox
											.getItemText(listBox
													.getSelectedIndex())));
									label_abservice
											.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
									label_abservice.setSize("145", "18");
									DOM.setStyleAttribute(label_abservice
											.getElement(), "fontSize", "10pt");
									DOM.setStyleAttribute(label_abservice
											.getElement(), "background",
											"#DDDDDD");
									DOM.setStyleAttribute(label_abservice
											.getElement(), "padding", "2px");
									left.add(label_abservice);
									for (int i = 0; i < list.size(); i++) {

										final Label label = new Label(list.get(
												i).getCompanyname());
										DOM.setStyleAttribute(label
												.getElement(), "fontSize",
												"10pt");
										DOM
												.setStyleAttribute(label
														.getElement(),
														"padding", "2px");
										DOM.setStyleAttribute(label
												.getElement(), "background",
												"#EFF5FF");
										label.setWidth("145");
										left.add(label);
										final int j = i;
										label
												.addClickHandler(new ClickHandler() {
													@Override
													public void onClick(
															ClickEvent event) {
														for (int k = 0; k < list
																.size(); k++) {
															DOM
																	.setStyleAttribute(
																			left
																					.getWidget(
																							k + 1)
																					.getElement(),
																			"background",
																			"#EFF5FF");
															;
														}
														DOM
																.setStyleAttribute(
																		label
																				.getElement(),
																		"background",
																		"#C3D9FF");
														selectRecord = list
																.get(j);
														selectrecordid = list
																.get(j)
																.getRecordid();
														if (list.get(j)
																.getRating() == null) {
															hyperlink_submit
																	.setVisible(true);
														} else {
															hyperlink_submit
																	.setVisible(false);
														}
														((Label) (flexTable
																.getWidget(0, 1)))
																.setText(list
																		.get(j)
																		.getServicename());
														((Label) (flexTable
																.getWidget(1, 1)))
																.setText(list
																		.get(j)
																		.getCompanyname());
														((Label) (flexTable
																.getWidget(3, 0)))
																.setText(list
																		.get(j)
																		.isSuccess() == true ? "Y"
																		: "N");
														((Label) (flexTable
																.getWidget(3, 1)))
																.setText(Double
																		.toString(list
																				.get(
																						j)
																				.getRestime()));
														sr
																.setRating(list
																		.get(j)
																		.getRating() == null ? 0
																		: list
																				.get(
																						j)
																				.getRating());

													}
												});
									}
								}
							});
						}
					}
				});
	}

	HashMap<Integer, ArrayList<Records_Client>> sortServices(
			List<Records_Client> list) {
		HashMap<Integer, ArrayList<Records_Client>> hm = new HashMap<Integer, ArrayList<Records_Client>>();
		for (int i = 0; i < list.size(); i++) {
			if (hm.containsKey(list.get(i).getCid())) {
				hm.get(list.get(i).getCid()).add(list.get(i));
			} else {
				hm.put(list.get(i).getCid(), new ArrayList<Records_Client>());
				hm.get(list.get(i).getCid()).add(list.get(i));
			}
		}
		return hm;
	}

	/** create score panel */
	/** create the score panel */
	AbsolutePanel createScorePanel() {
		AbsolutePanel absolutePanel = new AbsolutePanel();
		absolutePanel.setSize("700px", "205px");
		DOM.setStyleAttribute(absolutePanel.getElement(), "border",
				"10px solid #DDDDDD");
		DOM
				.setStyleAttribute(absolutePanel.getElement(), "background",
						"white");
		FlexTable flexTable = new FlexTable();
		absolutePanel.add(flexTable, 148, 25);// 0
		flexTable.setSize("550", "150");
		{
			Label lblService = new Label("Service");
			flexTable.setWidget(0, 0, lblService);// 00
			lblService.setSize("100", "30");
			DOM.setStyleAttribute(lblService.getElement(), "paddingTop", "5");
			DOM.setStyleAttribute(lblService.getElement(), "paddingLeft", "5");
		}
		{
			Label lblService_name = new Label("");
			flexTable.setWidget(0, 1, lblService_name);// 01
			lblService_name.setSize("400px", "30px");
			DOM.setStyleAttribute(lblService_name.getElement(), "paddingTop",
					"5");

		}
		{
			Label lblCompany = new Label("Company");
			flexTable.setWidget(1, 0, lblCompany);// 02
			lblCompany.setSize("100", "30");
			DOM.setStyleAttribute(lblCompany.getElement(), "paddingTop", "5");
			DOM.setStyleAttribute(lblCompany.getElement(), "paddingLeft", "5");

		}
		{
			Label lblCompany_name = new Label("");
			flexTable.setWidget(1, 1, lblCompany_name);// 03
			lblCompany_name.setSize("400", "30");
			DOM.setStyleAttribute(lblCompany_name.getElement(), "paddingTop",
					"5");
		}
		{
			Label lblAspect = new Label("Aspect");
			flexTable.setWidget(2, 0, lblAspect);// 04
			lblAspect.setSize("100", "60");
			DOM.setStyleAttribute(lblAspect.getElement(), "paddingTop", "15");
			DOM.setStyleAttribute(lblAspect.getElement(), "paddingLeft", "5");

		}
		{
			Label lblSuccess = new Label("Success");
			flexTable.setWidget(2, 1, lblSuccess);// 05
			lblSuccess.setSize("150", "20");
			DOM.setStyleAttribute(lblSuccess.getElement(), "paddingTop", "2");
		}
		{
			Label lblResponseTime = new Label("Response Time (ms)");
			flexTable.setWidget(2, 2, lblResponseTime);// 06
			lblResponseTime.setSize("250", "20");
			DOM.setStyleAttribute(lblResponseTime.getElement(), "paddingTop",
					"2");
		}
		{
			Label lblSuccess_score = new Label("");
			flexTable.setWidget(3, 1, lblSuccess_score);// 07
			lblSuccess_score.setSize("150", "40");
			DOM.setStyleAttribute(lblSuccess_score.getElement(), "paddingTop",
					"10");
		}
		{
			Label lblResponseTime_score = new Label("");
			flexTable.setWidget(3, 2, lblResponseTime_score);// 08
			lblResponseTime_score.setSize("250", "40");
			DOM.setStyleAttribute(lblResponseTime_score.getElement(),
					"paddingTop", "10");

		}
		{
			Label lblRating = new Label("Rating");
			flexTable.setWidget(4, 0, lblRating);// 09
			lblRating.setSize("100", "30");
			DOM.setStyleAttribute(lblRating.getElement(), "paddingTop", "5");
			DOM.setStyleAttribute(lblRating.getElement(), "paddingLeft", "5");

		}
		// rating
		{
			AbsolutePanel absolute_rating = new AbsolutePanel();
			absolute_rating.setSize("400", "30");
			flexTable.setWidget(4, 1, absolute_rating);// 010
			{
				// Display some example ratings...
				sr = new StarRating(6);
				sr.setRating(0);
				AbsolutePanel ab = new AbsolutePanel();
				ab.setSize("200", "25");
				ab.add(sr, 0, 0);
				absolute_rating.add(ab, 10, 8);
				hyperlink_submit = new Hyperlink("Submit", false, "");
				DOM.setStyleAttribute(hyperlink_submit.getElement(),
						"fontSize", "9pt");
				hyperlink_submit.setSize("50", "20");
				hyperlink_submit.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						selectRecord.setRating(sr.getRating());
						panel_Overview.calendar_Service.updateRecords(
								selectRecord, new AsyncCallback<String>() {
									@Override
									public void onFailure(Throwable caught) {
										selectRecord.setRating(null);
									}

									@Override
									public void onSuccess(String result) {
										hyperlink_submit.setVisible(false);
									}
								});
					}
				});
				absolute_rating.add(hyperlink_submit, 170, 8);
			}
		}
		// FlexTableHelper.fixRowSpan(flexTable);

		flexTable.setBorderWidth(1);
		DOM.setStyleAttribute(flexTable.getElement(), "border", "0px");
		flexTable.setCellSpacing(0);
		flexTable.setCellPadding(0);
		DOM.setStyleAttribute(flexTable.getElement(), "background", "#C3D9FF");
		flexTable.getFlexCellFormatter().setRowSpan(2, 0, 2);
		flexTable.getFlexCellFormatter().setColSpan(0, 1, 2);
		flexTable.getFlexCellFormatter().setColSpan(1, 1, 2);
		flexTable.getFlexCellFormatter().setColSpan(4, 1, 2);
		flexTable.getCellFormatter().setHorizontalAlignment(3, 2,
				HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setHorizontalAlignment(3, 1,
				HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setVerticalAlignment(3, 1,
				HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable.getCellFormatter().setVerticalAlignment(3, 2,
				HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable.getCellFormatter().setVerticalAlignment(2, 0,
				HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 2,
				HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setVerticalAlignment(2, 2,
				HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 1,
				HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setVerticalAlignment(2, 1,
				HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 1,
				HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setVerticalAlignment(0, 1,
				HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable.getCellFormatter().setHorizontalAlignment(1, 1,
				HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setVerticalAlignment(1, 1,
				HasVerticalAlignment.ALIGN_MIDDLE);
		FlexTableHelper.fixRowSpan(flexTable);
		FlexCellFormatter flexCellFormatter = flexTable.getFlexCellFormatter();
		DOM.setStyleAttribute(flexCellFormatter.getElement(0, 0), "borderLeft",
				"0px");
		DOM.setStyleAttribute(flexCellFormatter.getElement(1, 0), "borderLeft",
				"0px");
		DOM.setStyleAttribute(flexCellFormatter.getElement(2, 0), "borderLeft",
				"0px");
		DOM.setStyleAttribute(flexCellFormatter.getElement(4, 0), "borderLeft",
				"0px");

		{
			ListBox comboBox = new ListBox();
			absolutePanel.add(comboBox, 0, 0);// 1
			comboBox.setSize("153px", "24px");
		}
		{
			VerticalPanel leftPanel = new VerticalPanel();
			absolutePanel.add(leftPanel, 0, 25);// 2
			leftPanel.setWidth("150px");
			leftPanel.setSpacing(3);
			{
				Label label_abservice = new Label("service");
				DOM.setStyleAttribute(label_abservice.getElement(), "padding",
						"2px");
				label_abservice
						.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
				label_abservice.setSize("145", "18");
				DOM.setStyleAttribute(label_abservice.getElement(), "fontSize",
						"10pt");
				DOM.setStyleAttribute(label_abservice.getElement(),
						"background", "#DDDDDD");
				leftPanel.add(label_abservice);
			}
		}
		return absolutePanel;
	}

	/** show when load */
	public void showTable() {
		setHeight(Window.getClientHeight() - 90 + "px");
		tabPanel.setSize("100%", "100%");
		grid_panel_undone.setWidth("720");
		// grid_panel_undone.setHeight(Window.getClientHeight() - 200 + "px");
		// ((PagingTable) grid_panel_undone.getWidget(0)).setHeight(Window
		// .getClientHeight()
		// - 200 + "px");
		content_undone.setSize("100%", "100%");
		tabPanel.selectTab(0);

		DeferredCommand.addCommand(new Command() {
			@Override
			public void execute() {
				createUndoData(((PagingTable) grid_panel_undone.getWidget(0)));
				createDoneData(((PagingTable) grid_panel_done.getWidget(0)));
			}
		});

	}
}
