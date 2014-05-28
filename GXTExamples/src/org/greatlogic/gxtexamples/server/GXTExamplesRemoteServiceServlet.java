package org.greatlogic.gxtexamples.server;

import org.greatlogic.gxtexamples.shared.IRemoteService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.greatlogic.glbase.gldb.GLDBException;
import com.greatlogic.glbase.gldb.GLSQL;
import com.greatlogic.glbase.gllib.GLLog;
import com.greatlogic.glbase.gllib.IGLLibEnums.EGLLogLevel;
import com.greatlogic.glbase.glxml.GLXML;
import com.greatlogic.glbase.glxml.GLXMLException;

@SuppressWarnings("serial")
public class GXTExamplesRemoteServiceServlet extends RemoteServiceServlet implements IRemoteService {
//--------------------------------------------------------------------------------------------------
@Override
public void log(final int logLevelId, final String location, final String message) {
  GLLog.log(EGLLogLevel.lookupUsingPriority(logLevelId * 10), location + "=>" + message);
} // log()
//--------------------------------------------------------------------------------------------------
/**
 * Attempts to log in using the supplied login name and password.
 * @param loginName The login name that will be used for the login attempt.
 * @param password The password that will be used for the login attempt (this is the plain text
 * password, not the encrypted hash value).
 * @return The id of the Person row, or zero if the login request fails.
 */
@Override
public Integer login(final String loginName, final String password) {
  // find the user using the loginName and password
  // if the user isn't found {
  //    GLLog.infoSummary("Login failed for login name:" + loginName);
  //    return 0;
  // }
  //  GLLog.infoSummary("Login succeeded for login name:" + user.getLoginName());
  //  getThreadLocalRequest().getSession().setAttribute(ESessionAttribute.LoginUser.name(), user);
  //  return user.getUserId();
  return 0;
}
//--------------------------------------------------------------------------------------------------
@Override
public String select(final String xmlRequest) {
  GLLog.debug(xmlRequest);
  final StringBuilder result = new StringBuilder();
  try {
    final GLXML xml = new GLXML(xmlRequest);
    final GLSQL sql = GLSQL.selectUsingXML(xml);
    sql.open();
    try {
      boolean firstRow = true;
      while (sql.next(false)) {
        if (firstRow) {
          firstRow = false;
        }
        else {
          result.append('\n');
        }
        sql.getRowAsCSV(result);
      }
    }
    finally {
      sql.close();
    }
  }
  catch (final GLDBException dbe) {

  }
  catch (final GLXMLException xmle) {

  }
  return result.toString();
}
//--------------------------------------------------------------------------------------------------
}