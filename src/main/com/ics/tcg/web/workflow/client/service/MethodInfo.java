package com.ics.tcg.web.workflow.client.service;

import java.io.Serializable;

/**
 * The type of the method.
 * 
 * @author Administrator
 * 
 */
public class MethodInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1031660862791249924L;

	/** The method name. */
	private String methodName;
	/** The input info of the method */
	private InputInfo inputInfo;
	/** The output info of the method */
	private OutputInfo outputInfo;

	public MethodInfo() {
		methodName = new String();
		inputInfo = new InputInfo();
		outputInfo = new OutputInfo();
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public InputInfo getInputInfo() {
		return inputInfo;
	}

	public void setInputInfo(InputInfo inputInfo) {
		this.inputInfo = inputInfo;
	}

	public OutputInfo getOutputInfo() {
		return outputInfo;
	}

	public void setOutputInfo(OutputInfo outputInfo) {
		this.outputInfo = outputInfo;
	}
}
