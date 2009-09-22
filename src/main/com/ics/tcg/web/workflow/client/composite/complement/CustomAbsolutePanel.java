package com.ics.tcg.web.workflow.client.composite.complement;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.HasWordWrap;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.MouseWheelListener;
import com.google.gwt.user.client.ui.MouseWheelListenerCollection;
import com.google.gwt.user.client.ui.SourcesChangeEvents;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.SourcesMouseWheelEvents;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;

@SuppressWarnings( { "deprecation", "unused" })
public class CustomAbsolutePanel extends AbsolutePanel implements HasWordWrap,
		SourcesClickEvents, SourcesChangeEvents, SourcesMouseEvents,
		SourcesMouseWheelEvents {

	private String text;

	private ClickListenerCollection clickListeners;
	private HorizontalAlignmentConstant horzAlign;
	private MouseListenerCollection mouseListeners;
	private MouseWheelListenerCollection mouseWheelListeners;
	private ChangeListenerCollection changeListeners;

	public CustomAbsolutePanel() {
		super();
	}

	public CustomAbsolutePanel(String label) {
		super();
		text = label;
	}

	public void addClickListener(ClickListener listener) {
		if (clickListeners == null) {
			clickListeners = new ClickListenerCollection();
			sinkEvents(Event.ONCLICK);
		}
		clickListeners.add(listener);
	}

	public void addMouseListener(MouseListener listener) {
		if (mouseListeners == null) {
			mouseListeners = new MouseListenerCollection();
			sinkEvents(Event.MOUSEEVENTS);
		}
		mouseListeners.add(listener);
	}

	public void addMouseWheelListener(MouseWheelListener listener) {
		if (mouseWheelListeners == null) {
			mouseWheelListeners = new MouseWheelListenerCollection();
			sinkEvents(Event.ONMOUSEWHEEL);
		}
		mouseWheelListeners.add(listener);
	}

	public void removeClickListener(ClickListener listener) {
		if (clickListeners != null) {
			clickListeners.remove(listener);
		}
	}

	public void removeMouseListener(MouseListener listener) {
		if (mouseListeners != null) {
			mouseListeners.remove(listener);
		}
	}

	public void removeMouseWheelListener(MouseWheelListener listener) {
		if (mouseWheelListeners != null) {
			mouseWheelListeners.remove(listener);
		}
	}

	public void addChangeListener(ChangeListener listener) {
		// TODO Auto-generated method stub
		if (changeListeners == null) {
			changeListeners = new ChangeListenerCollection();
		}
		changeListeners.add(listener);
	}

	public void removeChangeListener(ChangeListener listener) {
		// TODO Auto-generated method stub
		if (changeListeners != null) {
			changeListeners.remove(listener);
		}
	}

	@Override
	public void onBrowserEvent(Event event) {
		switch (event.getTypeInt()) {
		case Event.ONCLICK:
			if (clickListeners != null) {
				clickListeners.fireClick(this);
			}
			break;

		case Event.ONMOUSEDOWN:
		case Event.ONMOUSEUP:
		case Event.ONMOUSEMOVE:
		case Event.ONMOUSEOVER:
		case Event.ONMOUSEOUT:
			if (mouseListeners != null) {
				mouseListeners.fireMouseEvent(this, event);
			}
			break;

		case Event.ONMOUSEWHEEL:
			if (mouseWheelListeners != null) {
				mouseWheelListeners.fireMouseWheelEvent(this, event);
			}
			break;
		}
	}

	public boolean getWordWrap() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setWordWrap(boolean wrap) {
		// TODO Auto-generated method stub

	}
}
