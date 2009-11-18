package com.ics.tcg.web.workflow.client.service;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Yhe type of the parameter.
 * 
 * @author lms
 * 
 */
public class ParamInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1795203474696239798L;

	/** The parameter name */
	private String paramName;
	/** The parameter type name */
	private String paramTypeName;
	/** The parameter type full name if complex */
	private String paramFullTypeName;
	/** Is the parameter type primitive */
	private boolean isPrimitive;
	/** Is the parameter array type */
	private boolean isArray;
	/** Is the parameter filled by user */
	private boolean isUserFilled;
	/** The parameter value */
	private String paramValue;
	/** The path of the parameter if not user filled */
	private String contentPath;
	/** May have deeper complex parameters */
	private ArrayList<ParamInfo> fieldInfoArray;
	/** The enumeration of the string type */
	private String paramEnums;

	public ParamInfo() {
		paramName = new String();
		paramTypeName = new String();
		fieldInfoArray = new ArrayList<ParamInfo>();
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamTypeName() {
		return paramTypeName;
	}

	public void setParamTypeName(String paramTypeName) {
		this.paramTypeName = paramTypeName;
	}

	public boolean isPrimitive() {
		return isPrimitive;
	}

	public void setPrimitive(boolean isPrimitive) {
		this.isPrimitive = isPrimitive;
	}

	public boolean isArray() {
		return isArray;
	}

	public void setArray(boolean isArray) {
		this.isArray = isArray;
	}

	public ArrayList<ParamInfo> getFieldInfoArray() {
		return fieldInfoArray;
	}

	public void setFieldInfoArray(ArrayList<ParamInfo> fieldInfoArray) {
		this.fieldInfoArray = fieldInfoArray;
	}

	public String getParamFullTypeName() {
		return paramFullTypeName;
	}

	public void setParamFullTypeName(String paramFullTypeName) {
		this.paramFullTypeName = paramFullTypeName;
	}

	public boolean getIsUserFilled() {
		return isUserFilled;
	}

	public void setIsUserFilled(boolean isUserFilled) {
		this.isUserFilled = isUserFilled;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getContentPath() {
		return contentPath;
	}

	public void setContentPath(String contentPath) {
		this.contentPath = contentPath;
	}

	public String getParamEnums() {
		return paramEnums;
	}

	public void setParamEnums(String paramEnums) {
		this.paramEnums = paramEnums;
	}

}
