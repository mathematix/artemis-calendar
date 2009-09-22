package com.ics.tcg.web.workflow.client.common.customline;

import java.util.HashMap;
import java.util.Map;

import pl.balon.gwt.diagrams.client.connector.AbstractConnector;
import pl.balon.gwt.diagrams.client.connector.Direction;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.UIObject;

@SuppressWarnings( { "unchecked" })
public class CustomUIObjectConnector extends AbstractConnector {
	/**
	 * Static registry of all wrapped UIObjects
	 */
	public static final Map wrappersMap = new HashMap();

	/**
	 * Wrapped UIObject
	 */
	public UIObject wrapped;

	/**
	 * Static factory method, decorates UIObject with Connector functionality.
	 */
	public static CustomUIObjectConnector wrap(UIObject wrapped) {
		if (wrappersMap.containsKey(wrapped)) {
			return (CustomUIObjectConnector) wrappersMap.get(wrapped);
		} else {
			CustomUIObjectConnector c = new CustomUIObjectConnector(wrapped);
			wrappersMap.put(wrapped, c);
			return c;
		}
	}

	/**
	 * Static factory method, decorates UIObject with Connector functionality.
	 * 
	 * @param wrapped
	 *            UIObject to be wrapped
	 * @param directions
	 *            directions in which connector allow connections
	 * @return Connector
	 */
	public static CustomUIObjectConnector wrap(UIObject wrapped,
			final Direction[] directions) {
		if (wrappersMap.containsKey(wrapped)) {
			return (CustomUIObjectConnector) wrappersMap.get(wrapped);
		} else {
			CustomUIObjectConnector c = new CustomUIObjectConnector(wrapped) {
				@Override
				public Direction[] getDirections() {
					return directions;
				}
			};
			wrappersMap.put(wrapped, c);
			return c;
		}
	}

	/**
	 * Finds Connector for UIObject (in static registry)
	 */
	public static CustomUIObjectConnector getWrapper(UIObject uio) {
		return (CustomUIObjectConnector) wrappersMap.get(uio);
	}

	/**
	 * Unwrapps specified UIObject (removes wrapper from static registry)
	 * 
	 * @param wrapped
	 */
	public static void unwrap(UIObject wrapped) {
		if (wrappersMap.containsKey(wrapped)) {
			getWrapper(wrapped).unwrap();
		}
	}

	/**
	 * Unwrapps wrapped UIObject (removes wrapper from static registry)
	 */
	public void unwrap() {
		wrappersMap.remove(wrapped);
		wrapped = null;
	}

	/**
	 * Disconnects from all connections and unwraps associated widget
	 */
	public void remove() {
		disconnect();
		unwrap();
	}

	/**
	 * Private constructor
	 * 
	 * @param wrapped
	 */
	public CustomUIObjectConnector(UIObject wrapped) {
		this.wrapped = wrapped;
	}

	/**
	 * @see pl.balon.gwt.diagrams.client.connector.Connector#getHeight()
	 */
	public int getHeight() {
		if (wrapped == null) {
			throw new IllegalStateException("Wrapped object is null.");
		}

		return wrapped.getOffsetHeight();
	}

	/**
	 * @see pl.balon.gwt.diagrams.client.connector.Connector#getWidth()
	 */
	public int getWidth() {
		if (wrapped == null) {
			throw new IllegalStateException("Wrapped object is null.");
		}

		return wrapped.getOffsetWidth();
	}

	/**
	 * @see pl.balon.gwt.diagrams.client.connector.Connector#getLeft()
	 */
	public int getLeft() {
		if (wrapped == null) {
			throw new IllegalStateException("Wrapped object is null.");
		}

		int containerOffset = 0;
		Element parent = DOM.getParent(wrapped.getElement());
		while (parent != null) {
			if ("relative".equals(DOM.getStyleAttribute(parent, "position"))) {
				containerOffset = DOM.getAbsoluteLeft(parent);
				break;
			}
			parent = DOM.getParent(parent);
		}
		return wrapped.getAbsoluteLeft() - containerOffset;
	}

	/**
	 * @see pl.balon.gwt.diagrams.client.connector.Connector#getTop()
	 */
	public int getTop() {
		if (wrapped == null) {
			throw new IllegalStateException("Wrapped object is null.");
		}

		int containerOffset = 0;
		Element parent = DOM.getParent(wrapped.getElement());
		while (parent != null) {
			if ("relative".equals(DOM.getStyleAttribute(parent, "position"))) {
				containerOffset = DOM.getAbsoluteTop(parent);
				break;
			}
			parent = DOM.getParent(parent);
		}
		return wrapped.getAbsoluteTop() - containerOffset;
	}

}
