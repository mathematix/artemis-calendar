package com.ics.tcg.web.workflow.client.composite.complement;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.MouseWheelListenerCollection;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.Widget;
import com.ics.tcg.web.workflow.client.DiagramBuilder;

@SuppressWarnings( { "deprecation", "unused" })
public class MyPanel extends AbsolutePanel implements SourcesClickEvents {

	private DiagramBuilder dbe = null;
	private ClickListenerCollection clickListeners;
	private MouseListenerCollection mouseListeners;
	private MouseWheelListenerCollection mouseWheelListeners;

	public MyPanel(Widget d) {
		super();
		dbe = (DiagramBuilder) d;
		this.sinkEvents(Event.ONCLICK); // 此句很重要，否则控件不响应onBrowserEvent
	}

	@Override
	public void onBrowserEvent(Event event) {
		if (DOM.eventGetType(event) == Event.ONCLICK
				&& DOM.eventGetCtrlKey(event)) {
			dbe.select(this);
		}

		super.onBrowserEvent(event);
	}

	public void addClickListener(ClickListener listener) {
		// TODO Auto-generated method stub

	}

	public void removeClickListener(ClickListener listener) {
		// TODO Auto-generated method stub

	}
}
