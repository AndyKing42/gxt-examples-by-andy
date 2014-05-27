package org.greatlogic.gxtexamples.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IRemoteServiceAsync {
//--------------------------------------------------------------------------------------------------
//void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
void log(final int logLevel, final String location, final String message,
         final AsyncCallback<Void> callback);
void login(final String loginName, final String password, final AsyncCallback<Integer> callback);
void select(String xml, AsyncCallback<String> asyncCallback);
//--------------------------------------------------------------------------------------------------
}