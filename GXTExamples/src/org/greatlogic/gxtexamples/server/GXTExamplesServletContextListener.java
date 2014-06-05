package org.greatlogic.gxtexamples.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.lang3.StringUtils;
import org.greatlogic.gxtexamples.shared.IDBEnums.EGXTExamplesTable;
import com.greatlogic.glbase.gldb.GLColumnMetadata;
import com.greatlogic.glbase.gldb.GLDBException;
import com.greatlogic.glbase.gldb.GLDataSource;
import com.greatlogic.glbase.gldb.GLResultSetMetadata;
import com.greatlogic.glbase.gllib.GLLog;
import com.greatlogic.glbase.gllib.GLUtil;
import com.greatlogic.glbase.gllib.IGLProgram;

public class GXTExamplesServletContextListener implements ServletContextListener {
//==================================================================================================
private static class GXTExamplesProgram implements IGLProgram {
@Override
public boolean displayCommandLineHelp() {
  return false;
}
}
//==================================================================================================
@Override
public void contextDestroyed(final ServletContextEvent event) {
  //
}
//--------------------------------------------------------------------------------------------------
@Override
public void contextInitialized(final ServletContextEvent event) {
  String configFilename = System.getenv("GXTExamplesConfigFilename");
  if (StringUtils.isEmpty(configFilename)) {
    configFilename = event.getServletContext().getInitParameter("ConfigFilename");
  }
  GLUtil.initializeProgram(new GXTExamplesProgram(), null, null, true, //
                           "<args ConfigFilename='" + configFilename + "'/>");
  //-------------------------
  try {
    final GLResultSetMetadata metadata;
    metadata = GLDataSource.getDefaultDataSource() //
                           .getTableMetadata(EGXTExamplesTable.Pet.toString());
    for (final GLColumnMetadata columnMetadata : metadata.getColumnMetadataList()) {
      GLLog.debug(columnMetadata.toString());
    }
  }
  catch (final GLDBException dbe) {
    GLLog.major("Error getting table meta data", dbe);
  }
  //-------------------------
}
//--------------------------------------------------------------------------------------------------
}