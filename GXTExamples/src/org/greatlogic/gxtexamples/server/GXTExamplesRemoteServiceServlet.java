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
//
//private static final StringBuilder _tempSB = new StringBuilder(500);
//
////--------------------------------------------------------------------------------------------------
//@Override
//public String sql(final GLGWTSQL gwtSQL) {
//  String result;
//  GLLog.debug("" + gwtSQL);
//  synchronized (_tempSB) {
//    _tempSB.setLength(0);
//    GLSQL sql;
//    try {
//      sql = FAPUtil.convertToSQL(gwtSQL);
//      sql.open();
//      try {
//        _tempSB.append(sql.getColumnNameIterable());
//        while (sql.next()) {
//          _tempSB.append(GLGWTUtil.RecordSeparator);
//          ForeignKeys.resolveReferencesToShortDesc(sql);
//          sql.getRowAsCSV(_tempSB);
//        }
//      }
//      finally {
//        sql.close();
//      }
//    }
//    catch (final GLDBException dbe) {
//    }
//    result = _tempSB.toString();
//  }
//  return result;
//} // sql()
//--------------------------------------------------------------------------------------------------
//@Override
//public String greetServer(final String input) throws IllegalArgumentException {
//  // Verify that the input is valid. 
//  if (!FieldVerifier.isValidName(input)) {
//    // If the input is not valid, throw an IllegalArgumentException back to
//    // the client.
//    throw new IllegalArgumentException("Name must be at least 4 characters long");
//  }
//
//  final String serverInfo = getServletContext().getServerInfo();
//  String userAgent = getThreadLocalRequest().getHeader("User-Agent");
//
//  // Escape data from the client to avoid cross-site script vulnerabilities.
//  userAgent = escapeHtml(userAgent);
//
//  return "Hello, " + escapeHtml(input) + "!<br><br>I am running " + serverInfo +
//         ".<br><br>It looks like you are using:<br>" + userAgent;
//}
/**
 * Escape an html string. Escaping data received from the client helps to
 * prevent cross-site script vulnerabilities.
 * 
 * @param html the html string to escape
 * @return the escaped string
 */
private String escapeHtml(final String html) {
  if (html == null) {
    return null;
  }
  return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
}
}