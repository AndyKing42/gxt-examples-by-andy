package org.greatlogic.gxtexamples.shared;

import org.greatlogic.gxtexamples.client.glgwt.IGLColumn;
import org.greatlogic.gxtexamples.client.glgwt.IGLEnums.EGLColumnDataType;
import org.greatlogic.gxtexamples.client.glgwt.IGLTable;

public interface IDBEnums {
//--------------------------------------------------------------------------------------------------
public enum EGXTExamplesTable implements IGLTable {
Pet,
PetType
}
//--------------------------------------------------------------------------------------------------
public enum Pet implements IGLColumn {
AdoptionFee(EGLColumnDataType.Currency, "Adoption Fee", 50),
FosterDate(EGLColumnDataType.Date, "Foster Date", 100),
IntakeDate(EGLColumnDataType.DateTime, "Intake Date/Time", 100),
NumberOfFosters(EGLColumnDataType.Int, "Number Of Fosters", 20),
PetId(EGLColumnDataType.Int, "Id", 50),
PetName(EGLColumnDataType.String, "Pet Name", 80),
PetTypeId(EGLColumnDataType.Int, "Pet Type", 80),
Sex(EGLColumnDataType.String, "Sex", 50, new String[] {"F", "M", "U"}),
TrainedFlag(EGLColumnDataType.Boolean, "Trained?", 50);
private final String[]          _choices;
private final EGLColumnDataType _dataType;
private final int               _defaultGridColumnWidth;
private final String            _title;
private Pet(final EGLColumnDataType dataType, final String title, final int defaultGridColumnWidth) {
  this(dataType, title, defaultGridColumnWidth, null);
}
private Pet(final EGLColumnDataType dataType, final String title, final int defaultGridColumnWidth,
            final String[] choices) {
  _dataType = dataType;
  _title = title;
  _defaultGridColumnWidth = defaultGridColumnWidth;
  _choices = choices;
}
@Override
public String[] getChoices() {
  return _choices;
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
public enum PetType implements IGLColumn {
PetTypeCode(EGLColumnDataType.String, "Pet Type Code", 50),
PetTypeDesc(EGLColumnDataType.String, "Pet Type Desc", 100),
PetTypeId(EGLColumnDataType.Int, "Id", 50);
private final EGLColumnDataType _dataType;
private final int               _defaultGridColumnWidth;
private final String            _title;
private PetType(final EGLColumnDataType dataType, final String title,
                final int defaultGridColumnWidth) {
  _dataType = dataType;
  _title = title;
  _defaultGridColumnWidth = defaultGridColumnWidth;
}
@Override
public String[] getChoices() {
  return null;
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