package com.ics.tcg.web.workflow.client.common.customline;

import pl.balon.gwt.diagrams.client.connection.calculator.ConnectionDataCalculator;
import pl.balon.gwt.diagrams.client.connection.data.ConnectionData;
import pl.balon.gwt.diagrams.client.connection.data.Point;
import pl.balon.gwt.diagrams.client.connector.Connector;

import com.google.gwt.user.client.Element;

public class StraightLineConnection extends CustomConnection {

	protected CustomLine curve = new CustomLineVml();

	/**
	 * Constructor
	 * 
	 * @param c1
	 * @param c2
	 */
	public StraightLineConnection(Connector c1, Connector c2) {
		this(new Connector[] { c1, c2 });
	}

	/**
	 * Constructor
	 * 
	 * @param toConnect
	 */
	public StraightLineConnection(Connector[] toConnect) {
		super(toConnect);
		if (toConnect.length != 2) {
			throw new IllegalArgumentException(
					"Need exactly two connectors to connect");
		}
		setElement(curve.getElement());
		addStyleName("gwt-diagrams-connection");
	}

	/**
	 * @see pl.balon.gwt.diagrams.client.connection.BezierTwoEndedConnection#createCalculator()
	 */
	@Override
	protected ConnectionDataCalculator createCalculator() {
		return new CustomStraightConnectionDataCalculator();
	}

	@Override
	public Element getElement() {
		return curve.getElement();
	}

	/**
	 * @see pl.balon.gwt.diagrams.client.connection.BezierTwoEndedConnection#update(pl.balon.gwt.diagrams.client.connection.data.ConnectionData)
	 */
	@Override
	protected void update(ConnectionData data) {
		if (data.getPoints().size() != 2) {
			throw new IllegalArgumentException("Expected two connection points");
		}

		curve.draw((Point) data.getPoints().get(0), (Point) data.getPoints()
				.get(1), (Point) data.getPoints().get(1), (Point) data
				.getPoints().get(0));

	}

}
