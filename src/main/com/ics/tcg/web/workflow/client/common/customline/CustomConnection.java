package com.ics.tcg.web.workflow.client.common.customline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.balon.gwt.diagrams.client.connection.AbstractConnection;
import pl.balon.gwt.diagrams.client.connection.calculator.ConnectionDataCalculator;
import pl.balon.gwt.diagrams.client.connection.data.ConnectionData;
import pl.balon.gwt.diagrams.client.connector.Connector;

@SuppressWarnings("unchecked")
public class CustomConnection extends AbstractConnection {

	private List connected = new ArrayList();

	public List getConnected() {
		return connected;
	}

	public void setConnected(List connected) {
		this.connected = connected;
	}

	public CustomConnection(List toConnect) {
		super(toConnect);
		connected = toConnect;
		// TODO Auto-generated constructor stub
	}

	public CustomConnection(Connector toConnect[]) {
		this(Arrays.asList(toConnect));
	}

	@Override
	protected ConnectionDataCalculator createCalculator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void update(ConnectionData data) {
		// TODO Auto-generated method stub

	}

}
