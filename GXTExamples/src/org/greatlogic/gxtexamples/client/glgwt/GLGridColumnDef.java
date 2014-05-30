package org.greatlogic.gxtexamples.client.glgwt;

import org.greatlogic.gxtexamples.client.glgwt.IGLEnums.EGLColumnDataType;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;

public class GLGridColumnDef {
//--------------------------------------------------------------------------------------------------
private final IGLColumn                   _column;
private ColumnConfig<GLValueMap, ?>       _columnConfig;
private int                               _columnIndex;
private final String                      _header;
private final HorizontalAlignmentConstant _horizontalAlignment;
private final int                         _width;
//--------------------------------------------------------------------------------------------------
public GLGridColumnDef(final IGLColumn column) {
  this(column, null, -1);
}
//--------------------------------------------------------------------------------------------------
public GLGridColumnDef(final IGLColumn column, final String header, final int width) {
  _column = column;
  _header = header == null ? _column.getTitle() : header;
  _width = width < 0 ? column.getDefaultGridColumnWidth() : width;
  if (_column.getDataType() == EGLColumnDataType.Boolean) {
    _horizontalAlignment = HasHorizontalAlignment.ALIGN_CENTER;
  }
  else {
    _horizontalAlignment = _column.getDataType().getNumeric() ? HasHorizontalAlignment.ALIGN_RIGHT //
                                                             : HasHorizontalAlignment.ALIGN_LEFT;
  }
}
//--------------------------------------------------------------------------------------------------
public IGLColumn getColumn() {
  return _column;
}
//--------------------------------------------------------------------------------------------------
public ColumnConfig<GLValueMap, ?> getColumnConfig() {
  return _columnConfig;
}
//--------------------------------------------------------------------------------------------------
public int getColumnIndex() {
  return _columnIndex;
}
//--------------------------------------------------------------------------------------------------
public String getHeader() {
  return _header;
}
//--------------------------------------------------------------------------------------------------
public HorizontalAlignmentConstant getHorizontalAlignment() {
  return _horizontalAlignment;
}
//--------------------------------------------------------------------------------------------------
public int getWidth() {
  return _width;
}
//--------------------------------------------------------------------------------------------------
public void setColumnConfig(final ColumnConfig<GLValueMap, ?> columnConfig, final int columnIndex) {
  _columnConfig = columnConfig;
  _columnIndex = columnIndex;
}
//--------------------------------------------------------------------------------------------------
@Override
public String toString() {
  return _column.toString() + " (" + _header + ")";
}
//--------------------------------------------------------------------------------------------------
}