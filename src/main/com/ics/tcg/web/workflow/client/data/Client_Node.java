package com.ics.tcg.web.workflow.client.data;

public class Client_Node extends Client_Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4003299838181786377L;
	public String comment;
	public String name;
	public Integer x;
	public Integer y;
	public boolean isstart, isfinished;
	
	/* node state represent the state of current node, 0 means ready to be execute, 
	 * 1 means being execute, 2 means been finished successfully, 3 means been finished failed */
	public int nodestate=0;

	public int preNodeNum;
	public int preNodeDoneNum;

	public Client_InputPort inputPort;
	public Client_FaultOutputPort faultOutputPort;

	public boolean isIsstart() {
		return isstart;
	}

	public void setIsstart(boolean isstart) {
		this.isstart = isstart;
	}

	public boolean isIsfinished() {
		return isfinished;
	}

	public void setIsfinished(boolean isfinished) {
		this.isfinished = isfinished;
	}
	
	public int getNodestate() {
		return nodestate;
	}

	public void setNodestate(int nodestate) {
		this.nodestate = nodestate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String value) {
		this.comment = value;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer value) {
		this.x = value;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer value) {
		this.y = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPreNodeNum() {
		return preNodeNum;
	}

	public void setPreNodeNum(int preNodeNum) {
		this.preNodeNum = preNodeNum;
	}

	public int getPreNodeDoneNum() {
		return preNodeDoneNum;
	}

	public void setPreNodeDoneNum(int preNodeDoneNum) {
		this.preNodeDoneNum = preNodeDoneNum;
	}

	public Client_InputPort getInputPort() {
		return inputPort;
	}

	public void setInputPort(Client_InputPort value) {
		this.inputPort = value;
	}

	public Client_FaultOutputPort getFaultOutputPort() {
		return faultOutputPort;
	}

	public void setFaultOutputPort(Client_FaultOutputPort value) {
		this.faultOutputPort = value;
	}
}
