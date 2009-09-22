package com.ics.tcg.web.workflow.client.common.customline;

import pl.balon.gwt.diagrams.client.connection.data.Point;

import com.google.gwt.user.client.Element;

public class CustomLine {
	/**
	 * Redraws this bezier curve instance.
	 * 
	 * @param p1
	 *            stat point
	 * @param p2
	 *            end point
	 * @param c1
	 *            first control point
	 * @param c2
	 *            second control point
	 */
	public void draw(Point p1, Point p2, Point c1, Point c2) {
		throw new UnsupportedOperationException("Unsupported browser");
	}

	/**
	 * @return dom element representing bezier curve (it contains curve)
	 */
	public Element getElement() {
		throw new UnsupportedOperationException("Unsupported browser");
	}

	/**
	 * Performs additional operations (if needed) during remove.
	 */
	public void remove() {
		// NOP
	}
}
