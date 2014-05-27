package org.greatlogic.gxtexamples.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("GXTExamplesRemoteServiceServlet")
public interface IRemoteService extends RemoteService {
//--------------------------------------------------------------------------------------------------
void log(final int logLevel, final String location, final String message);
Integer login(final String loginName, final String password);
String select(String xml);
//--------------------------------------------------------------------------------------------------
}