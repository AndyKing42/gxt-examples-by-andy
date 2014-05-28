package org.greatlogic.gxtexamples.shared;

import org.greatlogic.gxtexamples.client.glgwt.IGLColumn;
import org.greatlogic.gxtexamples.client.glgwt.IGLEnums.EGLColumnDataType;
import org.greatlogic.gxtexamples.client.glgwt.IGLTable;

public interface IDBEnums {
//--------------------------------------------------------------------------------------------------
public enum EGXTExamplesTable implements IGLTable {
Person
}
//--------------------------------------------------------------------------------------------------
public enum Person implements IGLColumn {
CurrentOrgId(EGLColumnDataType.Int, "Current Org", 50),
DisplayName(EGLColumnDataType.String, "Display Name", 100),
EmailAddress(EGLColumnDataType.String, "Email Address", 100),
FirstName(EGLColumnDataType.String, "First Name", 80),
LastName(EGLColumnDataType.String, "Last Name", 80),
LoginName(EGLColumnDataType.String, "Login Name", 80),
PersonID(EGLColumnDataType.Int, "Id", 50),
Version(EGLColumnDataType.String, "Version", 50);
private final EGLColumnDataType _dataType;
private final int               _defaultGridColumnWidth;
private final String            _title;
private Person(final EGLColumnDataType dataType, final String title,
               final int defaultGridColumnWidth) {
  _dataType = dataType;
  _title = title;
  _defaultGridColumnWidth = defaultGridColumnWidth;
}
@Override
public EGLColumnDataType getDataType() {
  return _dataType;
}
@Override
public int getDefaultGridColumnWidth() {
  return _defaultGridColumnWidth;
}
@Override
public String getTitle() {
  return _title;
}
}
//--------------------------------------------------------------------------------------------------
}