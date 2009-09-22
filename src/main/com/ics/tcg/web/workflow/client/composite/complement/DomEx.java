package com.ics.tcg.web.workflow.client.composite.complement;

import com.google.gwt.user.client.Element;

public class DomEx {
	public static native void setStyleAttributeEx(Element elem, String jsStyle,
			String value)
	/*-{
	     if(jsStyle=="float" || jsStyle == "styleFloat" || jsStyle =="cssFloat")
	     {
	     jsStyle = (elem.style.styleFloat || elem.style.styleFloat=="")  ? "styleFloat":"cssFloat";
	     }
	     elem.style[jsStyle]=value;
	 }-*/;

	/**
	 * @param elem
	 *            element
	 */
	public static native void disableContextMenu(Element elem)
	/*-{
	    elem.oncontextmenu = function() { return false; };
	}-*/;
}