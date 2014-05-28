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
CurrentOrgId(EGLColumnDataType.Int, "Current Organization"),
DisplayName(EGLColumnDataType.String, "Display Name"),
EmailAddress(EGLColumnDataType.String, "Email Address"),
FirstName(EGLColumnDataType.String, "First Name"),
LastName(EGLColumnDataType.String, "Last Name"),
LoginName(EGLColumnDataType.String, "Login Name"),
PersonID(EGLColumnDataType.Int, "Person Id"),
Version(EGLColumnDataType.String, "Version");
private final EGLColumnDataType _dataType;
private final String            _title;
private Person(final EGLColumnDataType dataType, final String title) {
  _dataType = dataType;
  _title = title;
}
@Override
public EGLColumnDataType getDataType() {
  return _dataType;
}
@Override
public String getTitle() {
  return _title;
}
}
//--------------------------------------------------------------------------------------------------
}