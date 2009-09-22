package com.ics.tcg.web.user.client.widgets;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.UIObject;

/**
 * @author NickC
 */
@SuppressWarnings("unused")
public class YellowFadeHandler {
	private final int transparent_color = 17;
	private static final String[] yftStyles = { "yft-00", "yft-01", "yft-02",
			"yft-03", "yft-04", "yft-05", "yft-06", "yft-07", "yft-08",
			"yft-09", "yft-10", "yft-11", "yft-12", "yft-13", "yft-14",
			"yft-15", "yft-16", "yft-transparent" };

	private List<FadeObject> fadeObjectList = new LinkedList<FadeObject>();

	public YellowFadeHandler() {

		Timer t = new Timer() {
			@Override
			public void run() {
				if (fadeObjectList.isEmpty())
					return;
				for (Iterator<FadeObject> iter = fadeObjectList.iterator(); iter
						.hasNext();) {
					FadeObject fObj = iter.next();
					fObj.nextColor();
					if (fObj.isTransparent()) {// if it hits the transparent
						// color then fading is done so
						// remove it.
						iter.remove();
						fObj.uiObj.setVisible(false);
						continue;
					}
				}
			}
		};
		t.scheduleRepeating(250);
	}

	public void add(UIObject obj) {
		if (obj == null)
			return;
		obj.setVisible(true);
		FadeObject fo = new FadeObject(obj);
		fadeObjectList.add(0, fo);
		GWT.log("fade object added", null);
	}

	private class FadeObject {
		private UIObject uiObj = null;
		private int color = 0;

		public FadeObject(UIObject uiObj) {
			this.uiObj = uiObj;
			this.uiObj.addStyleName(yftStyles[color]);
		}

		public void nextColor() {
			if (color == transparent_color)
				return;
			uiObj.removeStyleName(yftStyles[color]);
			color++;
			uiObj.addStyleName(yftStyles[color]);
		}

		public int getColor() {
			return color;
		}

		public boolean isTransparent() {
			if (color == transparent_color)
				return true;
			return false;
		}
	}// end class FadeObject
}// end class YellowFadeHandler

