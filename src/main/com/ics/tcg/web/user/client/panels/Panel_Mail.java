package com.ics.tcg.web.user.client.panels;

import java.util.ArrayList;
import java.util.Date;
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
import org.gwtlib.client.table.ui.renderer.CheckBoxRenderer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.ics.tcg.web.user.client.db.Mail_Client;

@SuppressWarnings("deprecation")
public class Panel_Mail extends DockPanel {

	Panel_Mail mailpanel = this;
	Integer userid = -1;

	/** panels */
	Panel_Overview overview_panel;

	/** widget */
	AbsolutePanel grid_content;
	AbsolutePanel detail_content;
	HTML detail_head;
	HTML detail_send;
	HTML detail_body;
	AbsolutePanel detail_body_panel;

	/** data */
	ArrayList<Mail_Client> mailClients = new ArrayList<Mail_Client>();
	DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("MM-dd");
	ArrayList<Integer> selectIds = new ArrayList<Integer>();
	PagingTable table;
	Integer selectid = -1;

	Panel_Mail(Panel_Overview panel_Overview) {

		overview_panel = panel_Overview;

		userid = overview_panel.userid;

		this.setSize("100%", "90%");
		DOM.setStyleAttribute(this.getElement(), "borderLeft",
				"10px solid #C3D9FF");
		DOM.setStyleAttribute(this.getElement(), "borderBottom",
				"10px solid #C3D9FF");

		// add head
		AbsolutePanel head = createNorth();
		this.add(head, DockPanel.NORTH);
		this.setCellHeight(head, "54");

		// add content
		AbsolutePanel content = new AbsolutePanel();
		content.setHeight("100%");
		content.setWidth("100%");
		this.add(content, DockPanel.CENTER);

		// grid content
		grid_content = createGrid();
		grid_content.setWidth("100%");
		grid_content.setHeight("100%");
		content.add(grid_content, 0, 0);

		// detail content
		detail_content = createDetail();
		detail_content.setVisible(false);
		detail_content.setSize("100%", "100%");
		content.add(detail_content, 0, 0);

		// resize
		this.setHeight(Window.getClientHeight() - 90 + "px");
		detail_body_panel.setHeight(Window.getClientHeight() - 220 + "px");
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				int h = event.getHeight();
				if (mailpanel.isVisible() == true) {
					mailpanel.setHeight(h - 90 + "px");
					detail_body_panel.setHeight(h - 220 + "px");
				}
			}
		});
	}

	/** init the panel */
	public void init() {
		show_Grid();
		loadMail(table);
		this.setHeight(Window.getClientHeight() - 90 + "px");
		detail_body_panel.setHeight(Window.getClientHeight() - 220 + "px");
	}

	/** create north */
	AbsolutePanel createNorth() {
		AbsolutePanel absolutePanel = new AbsolutePanel();
		DOM.setStyleAttribute(absolutePanel.getElement(), "background",
				"#C3D9FF");
		absolutePanel.setWidth("100%");
		absolutePanel.setHeight("54");

		Label head = new Label("Mail");
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
		absolutePanel.add(head, 0, 0);

		AbsolutePanel operating = createOperating();
		absolutePanel.add(operating, 0, 25);

		return absolutePanel;
	}

	/** create operating bar */
	AbsolutePanel createOperating() {
		final AbsolutePanel confirm = new AbsolutePanel();
		DOM.setStyleAttribute(confirm.getElement(), "background", "#C3D9FF");
		confirm.setHeight("24");
		confirm.setWidth("100%");
		// back to calendar
		Hyperlink hyperlink = new Hyperlink("« Back to Calendar", "back");
		DOM.setStyleAttribute(hyperlink.getElement(), "fontSize", "10pt");
		hyperlink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				overview_panel.calendar_show();
			}
		});
		// back to grid
		final Hyperlink hyperlink2 = new Hyperlink("« Back to Inbox", "back");
		hyperlink2.setVisible(false);
		DOM.setStyleAttribute(hyperlink2.getElement(), "fontSize", "10pt");
		hyperlink2.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				show_Grid();
			}
		});

		confirm.add(hyperlink, 0, 5);
		confirm.add(hyperlink2, 120, 5);
		return confirm;

	}

	/** create detail content */
	AbsolutePanel createDetail() {
		AbsolutePanel as = new AbsolutePanel();
		DOM.setStyleAttribute(as.getElement(), "border", "5px solid #6694E3");
		as.setSize("100%", "100%");

		DOM
				.setStyleAttribute(as.getElement(), "padding",
						"15px 15px 15px 15px");
		// head
		AbsolutePanel head = new AbsolutePanel();
		head.setWidth("100%");
		head.setHeight("20");
		HTML html = new HTML();
		detail_head = html;
		head.add(html, 0, 0);
		as.add(head, 15, 10);

		// content
		AbsolutePanel content = new AbsolutePanel();
		detail_body_panel = content;
		content.setWidth("100%");
		DOM.setStyleAttribute(content.getElement(), "border",
				"1px solid #BCBCBC");
		AbsolutePanel sendcontent = new AbsolutePanel();
		sendcontent.setWidth("100%");
		sendcontent.setHeight("70");
		DOM
				.setStyleAttribute(sendcontent.getElement(), "background",
						"#EFF5FB");
		HTML send = new HTML("aaa");
		sendcontent.add(send, 10, 5);

		HTML mail = new HTML("aaa");
		detail_send = send;
		detail_body = mail;
		content.add(sendcontent, 0, 0);
		content.add(mail, 50, 90);

		as.add(content, 15, 40);

		return as;
	}

	/** create mailbox grid */
	private AbsolutePanel createGrid() {
		final AbsolutePanel grid = new AbsolutePanel();
		grid.setSize("100%", "100%");
		DOM.setStyleAttribute(grid.getElement(), "border", "5px solid #6694E3");
		table = createTable();
		table.setSize("100%", "100%");
		grid.add(table, 0, 0);
		return grid;
	}

	/** create mailbox table */
	private PagingTable createTable() {
		// Set up the columns we want to be displayed
		// final CheckBox checkAll = new CheckBox();
		final Column[] columns = {
				new Column(0, false, "", "8", new CheckBoxRenderer()),
				new Column(1, false, "Sender", "100"),
				new Column(2, false, "Mail", "500"),
				new Column(3, false, "SentTime", "15"),
				new Column(4, false, "id", "0") };
		// data
		final Row[] rows = new Row[0];
		// Now configure the table
		ColumnLayout layout = new ColumnLayout(columns);
		final PagingBar pagingBar = new PagingBar(0, 0, 5, new int[] { 5, 10,
				20, 50, 100 });
		final PagingTable table = new PagingTable(layout, pagingBar);
		// Create provider
		ContentProvider provider = new ContentProvider() {
			@Override
			public void load(int begin, int end, int sortId, boolean ascending) {
				table.onSuccess(new Rows(rows, 0, -1, false));
			}
		};
		table.setContentProvider(provider);
		table.show(4, false);
		table.update();
		return table;
	}

	/** load data from server */
	void loadMail(final PagingTable table) {
		overview_panel.mail_Service.get_Mail(userid,
				new AsyncCallback<List<Mail_Client>>() {
					@Override
					public void onSuccess(List<Mail_Client> result) {
						// if there's mails
						if (result != null) {
							mailClients = (ArrayList<Mail_Client>) result;
							// row data
							final Row[] rows = new Row[mailClients.size()];
							for (int i = 0; i < mailClients.size(); ++i) {

								Integer id = mailClients.get(i).getId();
								String sender = mailClients.get(i).getSender();
								String head = mailClients.get(i).getHead();
								Date senttime = mailClients.get(i)
										.getSenttime();
								String senttimeString = dateTimeFormat
										.format(senttime);
								rows[i] = new Row(i, new Object[] { false,
										sender, head, senttimeString, id });
							}
							// set provider
							ContentProvider provider = new ContentProvider() {
								@Override
								public void load(int begin, int end,
										int sortId, boolean ascending) {
									table
											.onSuccess(new Rows(rows, 0, -1,
													false));
								}
							};
							table.setContentProvider(provider);

							// // set pagingBar's size
							table.getPagingBar().setSize(mailClients.size());
							if (mailClients.size() < 10) {
								table.getPagingBar().setPageSize(
										mailClients.size());
							} else {
								table.getPagingBar().setPageSize(10);
							}

							// TableListener
							table.addTableListener(new TableListenerAdapter() {

								public void onCellClicked(
										SourcesTableEvents sender, Row row,
										Column column) {
								}

								public void onRowClicked(
										SourcesTableEvents sender, Row row) {
									// for (int i = 0; i < rows.length; ++i)
									// rows[i].setState(Row.State.NONE);
									// row.setState(Row.State.SELECT);
									// table.refreshRowState();
								}

								public void onClick(SourcesTableEvents sender,
										Row row, Column column, Widget widget) {
									if (widget instanceof CheckBox) {
										if (((CheckBox) widget).getValue() == true) {
											// select
											row.setState(Row.State.SELECT);
											table.refreshRowState();
										} else {
											// deselect
											row.setState(Row.State.NONE);
											table.refreshRowState();
										}
									} else {
										Integer id = (Integer) row.getValue(4);
										show_Content(id);
									}
								}

								public void onChange(SourcesTableEvents sender,
										Row row, Column column, Widget widget) {
									if (widget instanceof CheckBox) {
										if (((CheckBox) widget).getValue() == true) {
											Integer id = (Integer) row
													.getValue(4);
											if (selectIds.contains(id) == false) {
												selectIds.add(id);
											}

										} else {
											Integer id = (Integer) row
													.getValue(4);
											if (selectIds.contains(id) == false) {
												selectIds.remove(id);
											}

										}
									}
								}
							});
							table.update();
							table.refreshRowState();
						} else
						// if got no mails
						{
							mailClients = null;

							// data
							final Row[] rows = new Row[0];

							// set pagingBar's size
							table.getPagingBar().setSize(0);

							// Create provider
							ContentProvider provider = new ContentProvider() {
								@Override
								public void load(int begin, int end,
										int sortId, boolean ascending) {
									table
											.onSuccess(new Rows(rows, 0, -1,
													false));
								}
							};
							table.setContentProvider(provider);
							table.update();
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("fail to get mail, try again later");
					}
				});
	}

	/** Show the mail content */
	void show_Content(Integer id) {

		show_Detail();

		overview_panel.mail_Service.getContent(id,
				new AsyncCallback<Mail_Client>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("fail to get mail content");
					}

					@Override
					public void onSuccess(Mail_Client result) {
						if (result != null) {
							detail_head
									.setHTML("<DIV style='font-size:15pt;font-weight:bold'>"
											+ result.getHead() + "</DIV>");
							detail_send
									.setHTML("<DIV style='font-size:11pt;line-height:20px'>From:  <span style='color:00681C'>"
											+ result.getSender()
											+ "</span></DIV><DIV style='font-size:11pt;line-height:20px'>Date:  "
											+ result.getSenttime()
													.toGMTString()
											+ "</DIV><DIV style='font-size:11pt;line-height:20px'>To:  "
											+ overview_panel.userClient
													.getAccount() + "</DIV>");
							detail_body.setHTML("<DIV style='font-size:11pt'>"
									+ result.getContent() + "</DIV>");
							if (result.isUnread() == true) {
								overview_panel.mail_Service.setRead(result
										.getId(), new AsyncCallback<Void>() {

									@Override
									public void onFailure(Throwable caught) {
										Window
												.alert("fail to communicate with server!");
									}

									@Override
									public void onSuccess(Void result) {
										overview_panel.mail_Service
												.getUnreadMailCount(
														userid,
														new AsyncCallback<Integer>() {
															@Override
															public void onFailure(
																	Throwable caught) {
																Window
																		.alert("fail to get mail account");
															}

															@Override
															public void onSuccess(
																	Integer result) {
																if (result != 0) {
																	overview_panel.hypermail
																			.setHTML("<DIV style='font-size:10pt;font-weight:bold;font-family:sans-serif'>Mailbox ("
																					+ result
																					+ " )</DIV>");
																} else {
																	overview_panel.hypermail
																			.setHTML("<DIV style='font-size:10pt;font-family:sans-serif'>Mailbox</DIV>");
																}
															}
														});
									}
								});
							}

						} else {
							Window.alert("no content");
						}
					}
				});
	}

	/** show grid */
	void show_Grid() {
		detail_content.setVisible(false);
		grid_content.setVisible(true);
		((Hyperlink) (((AbsolutePanel) (((AbsolutePanel) (this.getWidget(0)))
				.getWidget(1))).getWidget(1))).setVisible(false);
	}

	/** show detail */
	void show_Detail() {
		grid_content.setVisible(false);
		detail_content.setVisible(true);
		((Hyperlink) (((AbsolutePanel) (((AbsolutePanel) (this.getWidget(0)))
				.getWidget(1))).getWidget(1))).setVisible(true);
	}

}