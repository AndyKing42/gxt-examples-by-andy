package org.greatlogic.gxtexamples.shared;

import org.greatlogic.gxtexamples.client.glgwt.IGLColumn;
import org.greatlogic.gxtexamples.client.glgwt.IGLEnums.EGLColumnDataType;
import org.greatlogic.gxtexamples.client.glgwt.IGLTable;

public interface IDBEnums {
//--------------------------------------------------------------------------------------------------
public enum EGXTExamplesTable implements IGLTable {
Pet(IDBEnums.Pet.PetId),
PetType(IDBEnums.PetType.PetTypeId);
private final IGLColumn _primaryKeyColumn;
private EGXTExamplesTable(final IGLColumn primaryKeyColumn) {
  _primaryKeyColumn = primaryKeyColumn;
}
@Override
public IGLColumn getPrimaryKeyColumn() {
  return _primaryKeyColumn;
}
}
//--------------------------------------------------------------------------------------------------
public enum Pet implements IGLColumn {
AdoptionFee(EGLColumnDataType.Currency, 2, "Adoption Fee", 100),
FosterDate(EGLColumnDataType.Date, 0, "Foster Date", 100),
IntakeDate(EGLColumnDataType.DateTime, 0, "Intake Date/Time", 125),
NumberOfFosters(EGLColumnDataType.Int, 0, "Number Of Fosters", 20),
PetId(EGLColumnDataType.Int, 0, "Id", 50),
PetName(EGLColumnDataType.String, 0, "Pet Name", 80),
PetTypeId(EGLColumnDataType.Int, 0, "Pet Type", 80),
Sex(EGLColumnDataType.String, 0, "Sex", 50, new String[] {"F", "M", "U"}),
TrainedFlag(EGLColumnDataType.Boolean, 0, "Trained?", 80);
private final String[]          _choices;
private final EGLColumnDataType _dataType;
private final int               _defaultGridColumnWidth;
private final int               _numberOfDecimalPlaces;
private final String            _title;
private Pet(final EGLColumnDataType dataType, final int numberOfDecimalPlaces, final String title,
            final int defaultGridColumnWidth) {
  this(dataType, numberOfDecimalPlaces, title, defaultGridColumnWidth, null);
}
private Pet(final EGLColumnDataType dataType, final int numberOfDecimalPlaces, final String title,
            final int defaultGridColumnWidth, final String[] choices) {
  _dataType = dataType;
  _numberOfDecimalPlaces = numberOfDecimalPlaces;
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
public int getNumberOfDecimalPlaces() {
  return _numberOfDecimalPlaces;
}
@Override
public String getTitle() {
  return _title;
}
}
//--------------------------------------------------------------------------------------------------
public enum PetType implements IGLColumn {
PetTypeCode(EGLColumnDataType.String, 0, "Pet Type Code", 50),
PetTypeDesc(EGLColumnDataType.String, 0, "Pet Type Desc", 100),
PetTypeId(EGLColumnDataType.Int, 0, "Id", 50);
private final EGLColumnDataType _dataType;
private final int               _defaultGridColumnWidth;
private final int               _numberOfDecimalPlaces;
private final String            _title;
private PetType(final EGLColumnDataType dataType, final int numberOfDecimalPlaces,
                final String title, final int defaultGridColumnWidth) {
  _dataType = dataType;
  _numberOfDecimalPlaces = numberOfDecimalPlaces;
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
public int getNumberOfDecimalPlaces() {
  return _numberOfDecimalPlaces;
}
@Override
public String getTitle() {
  return _title;
}
}
//--------------------------------------------------------------------------------------------------
}