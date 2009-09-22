package com.ics.tcg.web.workflow.client.common.customline;

import java.util.List;

import pl.balon.gwt.diagrams.client.connection.calculator.ConnectionDataCalculator;
import pl.balon.gwt.diagrams.client.connection.data.ConnectionData;
import pl.balon.gwt.diagrams.client.connection.data.Point;
import pl.balon.gwt.diagrams.client.connector.Connector;

@SuppressWarnings( { "unchecked" })
public class CustomStraightConnectionDataCalculator implements
		ConnectionDataCalculator {

	public ConnectionData calculateConnectionData(List connectors) {
		if (connectors.size() != 2) {
			throw new IllegalArgumentException("Unsupported connectors count");
		}

		Connector c1 = (Connector) connectors.get(0);
		Connector c2 = (Connector) connectors.get(1);

		ConnectionData data = new ConnectionData();

		Point center1 = new Point(c1.getLeft() + c1.getWidth(), c1.getTop()
				+ c1.getHeight() / 2);
		Point center2 = new Point(c2.getLeft(), c2.getTop() + c2.getHeight()
				/ 2);

		data.getPoints().add(new Point(center1.left, center1.top));

		data.getPoints().add(new Point(center2.left, center2.top));

		return data;
	}

}
