/*
 * Copyright 2007 Michał Baliński
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.ics.tcg.web.workflow.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.DialogBox;

/**
 * Base class for entry point
 * 
 * @author Michał Baliński (michal.balinski@gmail.com)
 */
public abstract class BaseEntryPoint {

	/**
	 * Catches non handled exceptions and presents them in dialog box.
	 * 
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		// set uncaught exception handler
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			public void onUncaughtException(Throwable throwable) {
				String text = "Uncaught exception: ";
				while (throwable != null) {
					StackTraceElement[] stackTraceElements = throwable
							.getStackTrace();
					text += new String(throwable.toString() + "\n");
					for (int i = 0; i < stackTraceElements.length; i++) {
						text += "    at " + stackTraceElements[i] + "\n";
					}
					throwable = throwable.getCause();
					if (throwable != null) {
						text += "Caused by: ";
					}
				}
				DialogBox dialogBox = new DialogBox(true);
				DOM.setStyleAttribute(dialogBox.getElement(),
						"backgroundColor", "#ABCDEF");
				System.err.print(text);
				text = text.replaceAll(" ", "&nbsp;");
				dialogBox.setHTML("<pre>" + text + "</pre>");
				dialogBox.center();
			}
		});

		// use a deferred command so that the handler catches onLoad()
		// exceptions
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				onLoad();
			}
		});
	}

	/**
	 * To be implemented by child classes.
	 */
	protected abstract void onLoad();

	protected native String getUrlParam(String name)/*-{
													name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
													var regexS = "[\\?&]"+name+"=([^&#]*)";
													var regex = new RegExp( regexS );
													var results = regex.exec( $wnd.location.href );
													if( results == null )
													return "";
													else
													return results[1];
													}-*/;

}
