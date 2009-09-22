package com.ics.tcg.web.workflow.client.common.customline;

import pl.balon.gwt.diagrams.client.connection.data.Point;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;

public class CustomLineVml extends CustomLine {
	private Element div = DOM.createDiv();

	private Element vmlGroup;

	private Element vmlCurve;

	private Element vmlStroke;

	private Element styledDiv = DOM.createDiv();

	private boolean appendedToParent = false; // hack :(

	private static boolean documentInitialized = false;

	public void init() {
		if (!documentInitialized) {
			documentInitialized = initDocument();

		}

		vmlGroup = DOM.createElement("g_vml_:group");
		vmlCurve = DOM.createElement("g_vml_:line");
		vmlStroke = DOM.createElement("g_vml_:stroke");
		DOM.appendChild(vmlGroup, vmlCurve);
		DOM.appendChild(vmlCurve, vmlStroke);

		DOM.setElementAttribute(vmlGroup, "class", "gwt-diagrams-vml-group");
		DOM.setElementAttribute(vmlCurve, "class", "gwt-diagrams-line");
		DOM.appendChild(RootPanel.get().getElement(), vmlGroup); // hack :(

		DOM.appendChild(RootPanel.get().getElement(), styledDiv); // hack :(

		// #13 Bezier/straight connections doesn't work on IE6.0
		// DOM.setStyleAttribute(styledDiv, "display", "none");

		DOM.setElementProperty(styledDiv, "className", "gwt-diagrams-line");

		DOM.setElementAttribute(vmlStroke, "endarrow", "classic");

		DOM.setElementAttribute(vmlCurve, "strokecolor", getComputedStyle(
				styledDiv, "color"));
		DOM.setElementAttribute(vmlCurve, "strokeweight", getComputedStyle(
				styledDiv, "width"));
		DOM.setElementAttribute(vmlCurve, "filled", "false");

		DOM.setStyleAttribute(vmlGroup, "width", "100px");
		DOM.setStyleAttribute(vmlGroup, "height", "100px");
		DOM.setStyleAttribute(vmlGroup, "position", "absolute"); // 源代码为absolute
		DOM.setElementAttribute(vmlGroup, "coordsize", "100,100");

	}

	/**
	 * Private constructor. Do not instantiate directly @see BezierCurve
	 */
	public CustomLineVml() {
		init();
	}

	@Override
	public void draw(Point p1, Point p2, Point c1, Point c2) {

		if (!appendedToParent) {
			DOM.appendChild(DOM.getParent(div), vmlGroup);
			DOM.removeChild(DOM.getParent(div), div);
			div = null;
			appendedToParent = true;
		}

		DOM.setElementAttribute(vmlCurve, "strokecolor", getComputedStyle(
				styledDiv, "color"));
		DOM.setElementAttribute(vmlCurve, "strokeweight", getComputedStyle(
				styledDiv, "width"));

		Point start = new Point(Math.min(p1.left, p2.left), Math.min(p1.top,
				p2.top));

		DOM.setStyleAttribute(vmlGroup, "left", Integer.toString(start.left));
		DOM.setStyleAttribute(vmlGroup, "top", Integer.toString(start.top));

		drawImpl(p1.move(start.negative()), p2.move(start.negative()));
		// c1.move(start.negative()),
		// c2.move(start.negative()));

	}

	private void drawImpl(Point p1, Point p2) {
		DOM.setElementAttribute(vmlCurve, "from", p1.left + "," + p1.top);
		DOM.setElementAttribute(vmlCurve, "to", p2.left + "," + p2.top);
		// DOM.setElementAttribute(vmlCurve, "control1", c1.left + "," +
		// c1.top);
		// DOM.setElementAttribute(vmlCurve, "control2", c2.left + "," +
		// c2.top);
	}

	/**
	 * @see pl.balon.gwt.diagrams.client.common.bezier.BezierCurve#getElement()
	 */
	@Override
	public Element getElement() {
		if (appendedToParent) {
			return vmlCurve;
		} else {
			return div;
		}
	}

	/**
	 * Removes styledDiv from its parent.
	 * 
	 * @see pl.balon.gwt.diagrams.client.common.bezier.BezierCurve#remove()
	 */
	@Override
	public void remove() {
		DOM.removeChild(RootPanel.get().getElement(), styledDiv);
	}

	private native boolean initDocument()/*-{
											if (!$doc.namespaces["g_vml_"]) {
											$doc.namespaces.add("g_vml_", "urn:schemas-microsoft-com:vml");
											}
											
											var ss = $doc.createStyleSheet();
											ss.cssText = "g_vml_\\:*{behavior:url(#default#VML)}";

											return true;
											}-*/;

	private native static String getComputedStyle(Element element,
			String cssRule)/*-{
							if ( element.currentStyle ) {
							return element.currentStyle[ cssRule ];
							} else {
							return null;
							}
							}-*/;
}
