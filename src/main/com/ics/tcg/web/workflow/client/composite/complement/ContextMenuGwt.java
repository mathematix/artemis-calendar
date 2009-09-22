package com.ics.tcg.web.workflow.client.composite.complement;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ContextMenuGwt extends Composite {
	private SimplePanel panel;

	private MenuBar menuBar;

	public PopupPanel popupPanel;

	public ContextMenuGwt(Widget w, MenuBar menuBar) {
		super();
		panel = new SimplePanel();
		panel.setWidget(w);
		initWidget(panel);
		this.menuBar = menuBar;
		DomEx.disableContextMenu(panel.getElement());
		unsinkEvents(-1);
		sinkEvents(Event.ONMOUSEDOWN);
	}

	@Override
	protected void onAttach() {
		super.onAttach();

		DOM.setEventListener(getElement(), this);
	}

	public static Widget addContextMenu(Widget w, MenuBar menuBarIn) {
		ContextMenuGwt c = new ContextMenuGwt(w, menuBarIn);
		return c;
	}

	private void popupMenu(Event event, MenuBar menuBarIn) {
		final int x = DOM.eventGetClientX(event);
		final int y = DOM.eventGetClientY(event);
		popupPanel = new PopupPanel(true) {
			@Override
			public void onBrowserEvent(Event event) {
				if (DOM.eventGetType(event) == Event.ONCLICK) {
					// this.hide();
				}
				super.onBrowserEvent(event);
			}
		};
		popupPanel.sinkEvents(Event.ONCLICK);
		popupPanel.setWidget(menuBarIn);
		popupPanel.setPopupPosition(x, y);
		DomEx.disableContextMenu(popupPanel.getElement());// 阻止原右击菜单的弹出
		popupPanel.show();

	}

	@Override
	public void onBrowserEvent(Event event) {
		if (DOM.eventGetType(event) == Event.ONMOUSEDOWN) {
			if (DOM.eventGetButton(event) == NativeEvent.BUTTON_RIGHT) {
				popupMenu(event, this.menuBar);
				return;
			}
		}
		super.onBrowserEvent(event);
	}
}