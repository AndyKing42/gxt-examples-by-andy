package org.greatlogic.gxtexamples.client.glgwt;
/*
 * Copyright 2006-2014 Andy King (GreatLogic.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import org.greatlogic.gxtexamples.client.glgwt.GLValueProviderClasses.GLBigDecimalValueProvider;
import org.greatlogic.gxtexamples.client.glgwt.GLValueProviderClasses.GLBooleanValueProvider;
import org.greatlogic.gxtexamples.client.glgwt.GLValueProviderClasses.GLIntegerValueProvider;
import org.greatlogic.gxtexamples.client.glgwt.GLValueProviderClasses.GLStringValueProvider;
import org.greatlogic.gxtexamples.client.glgwt.IGLEnums.EGLColumnDataType;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.NumberCell;
import com.sencha.gxt.cell.core.client.form.CheckBoxCell;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.TextMetrics;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.event.ColumnWidthChangeEvent;
import com.sencha.gxt.widget.core.client.event.ColumnWidthChangeEvent.ColumnWidthChangeHandler;
import com.sencha.gxt.widget.core.client.event.HeaderContextMenuEvent;
import com.sencha.gxt.widget.core.client.event.HeaderContextMenuEvent.HeaderContextMenuHandler;
import com.sencha.gxt.widget.core.client.event.RefreshEvent;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.BigDecimalField;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridSelectionModel;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.grid.editing.GridEditing;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

public abstract class GLGridWidget implements IsWidget {
//--------------------------------------------------------------------------------------------------
private final ArrayList<ColumnConfig<GLRecord, ?>> _checkBoxList;
private ContentPanel                               _contentPanel;
private Grid<GLRecord>                             _grid;
protected ArrayList<GLGridColumnDef>               _gridColumnDefList;
private TreeMap<String, GLGridColumnDef>           _gridColumnDefMap;
protected GLListStore                              _listStore;
private final String                               _noRowsMessage;
private GridSelectionModel<GLRecord>               _selectionModel;
//--------------------------------------------------------------------------------------------------
protected GLGridWidget(final String headingText, final String noRowsMessage) {
  super();
  _noRowsMessage = noRowsMessage == null ? "There are no results to display" : noRowsMessage;
  _listStore = new GLListStore();
  _checkBoxList = new ArrayList<ColumnConfig<GLRecord, ?>>();
  _gridColumnDefList = new ArrayList<GLGridColumnDef>();
  loadGridColumnDefList();
  createGridColumnDefMap();
  createContentPanel(headingText);
  createGrid();
  _contentPanel.add(_grid);
}
//--------------------------------------------------------------------------------------------------
private void addHeaderContextMenuHandler() {
  final HeaderContextMenuHandler headerContextMenuHandler = new HeaderContextMenuHandler() {
    @Override
    public void onHeaderContextMenu(final HeaderContextMenuEvent headerContextMenuEvent) {
      MenuItem menuItem = new MenuItem("Size To Fit");
      menuItem.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(final SelectionEvent<Item> selectionEvent) {
          resizeColumnToFit(headerContextMenuEvent.getColumnIndex());
          _grid.getView().refresh(true);
        }
      });
      headerContextMenuEvent.getMenu().add(menuItem);
      menuItem = new MenuItem("Size All To Fit");
      menuItem.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(final SelectionEvent<Item> selectionEvent) {
          final int startIndex = _selectionModel instanceof CheckBoxSelectionModel ? 1 : 0;
          for (int columnIndex = startIndex; columnIndex < _grid.getColumnModel().getColumnCount(); ++columnIndex) {
            resizeColumnToFit(columnIndex);
          }
          _grid.getView().refresh(true);
        }
      });
      headerContextMenuEvent.getMenu().add(menuItem);
    }
  };
  _grid.addHeaderContextMenuHandler(headerContextMenuHandler);
}
//--------------------------------------------------------------------------------------------------
@Override
public Widget asWidget() {
  return _contentPanel;
}
//--------------------------------------------------------------------------------------------------
private void centerCheckBox(final ColumnConfig<GLRecord, ?> columnConfig) {
  final int leftPadding = (columnConfig.getWidth() - 12) / 2;
  final String styles = "padding: 3px 0px 0px " + leftPadding + "px;";
  final SafeStyles textStyles = SafeStylesUtils.fromTrustedString(styles);
  columnConfig.setColumnTextStyle(textStyles);
}
//--------------------------------------------------------------------------------------------------
private void createCheckBoxSelectionModel() {
  final IdentityValueProvider<GLRecord> identityValueProvider;
  identityValueProvider = new IdentityValueProvider<GLRecord>();
  _selectionModel = new CheckBoxSelectionModel<GLRecord>(identityValueProvider) {
    @Override
    protected void onRefresh(final RefreshEvent event) {
      if (isSelectAllChecked()) {
        selectAll();
      }
      super.onRefresh(event);
    }
  };
}
//--------------------------------------------------------------------------------------------------
private ColumnConfig<GLRecord, Boolean> createColumnConfigBoolean(final GLGridColumnDef gridColumnDef,
                                                                  final IGLColumn column) {
  final ColumnConfig<GLRecord, Boolean> result;
  final ValueProvider<GLRecord, Boolean> valueProvider = new GLBooleanValueProvider(column);
  result = new ColumnConfig<GLRecord, Boolean>(valueProvider, gridColumnDef.getWidth(), //
                                               column.getTitle());
  result.setHorizontalAlignment(gridColumnDef.getHorizontalAlignment());
  result.setCell(new CheckBoxCell());
  result.setSortable(false);
  _checkBoxList.add(result);
  return result;
}
//--------------------------------------------------------------------------------------------------
private ColumnConfig<GLRecord, BigDecimal> createColumnConfigBigDecimal(final GLGridColumnDef gridColumnDef,
                                                                        final IGLColumn column) {
  final ColumnConfig<GLRecord, BigDecimal> result;
  final ValueProvider<GLRecord, BigDecimal> valueProvider;
  valueProvider = new GLBigDecimalValueProvider(column, column.getNumberOfDecimalPlaces());
  result = new ColumnConfig<GLRecord, BigDecimal>(valueProvider, gridColumnDef.getWidth(), //
                                                  column.getTitle());
  result.setHorizontalAlignment(gridColumnDef.getHorizontalAlignment());
  NumberFormat numberFormat;
  if (column.getDataType() == EGLColumnDataType.Currency) {
    numberFormat = NumberFormat.getSimpleCurrencyFormat();
  }
  else {
    numberFormat = NumberFormat.getDecimalFormat();
  }
  result.setCell(new NumberCell<BigDecimal>(numberFormat));
  return result;
}
//--------------------------------------------------------------------------------------------------
private ColumnConfig<GLRecord, Integer> createColumnConfigInteger(final GLGridColumnDef gridColumnDef,
                                                                  final IGLColumn column) {
  final ColumnConfig<GLRecord, Integer> result;
  final ValueProvider<GLRecord, Integer> valueProvider = new GLIntegerValueProvider(column);
  result = new ColumnConfig<GLRecord, Integer>(valueProvider, gridColumnDef.getWidth(), //
                                               column.getTitle());
  result.setHorizontalAlignment(gridColumnDef.getHorizontalAlignment());
  result.setCell(new NumberCell<Integer>());
  return result;
}
//--------------------------------------------------------------------------------------------------
private ColumnConfig<GLRecord, String> createColumnConfigString(final GLGridColumnDef gridColumnDef,
                                                                final IGLColumn column) {
  final ColumnConfig<GLRecord, String> result;
  final ValueProvider<GLRecord, String> valueProvider = new GLStringValueProvider(column);
  result = new ColumnConfig<GLRecord, String>(valueProvider, gridColumnDef.getWidth(), //
                                              column.getTitle());
  result.setHorizontalAlignment(gridColumnDef.getHorizontalAlignment());
  result.setCell(new TextCell());
  return result;
}
//--------------------------------------------------------------------------------------------------
private ColumnModel<GLRecord> createColumnModel() {
  ColumnModel<GLRecord> result;
  final ArrayList<ColumnConfig<GLRecord, ?>> columnConfigList;
  columnConfigList = new ArrayList<ColumnConfig<GLRecord, ?>>();
  if (_selectionModel instanceof CheckBoxSelectionModel) {
    columnConfigList.add(((CheckBoxSelectionModel<GLRecord>)_selectionModel).getColumn());
  }
  for (final GLGridColumnDef gridColumnDef : _gridColumnDefList) {
    ColumnConfig<GLRecord, ?> columnConfig = null;
    final IGLColumn column = gridColumnDef.getColumn();
    switch (column.getDataType()) {
      case Boolean:
        columnConfig = createColumnConfigBoolean(gridColumnDef, column);
        break;
      case Currency:
        columnConfig = createColumnConfigBigDecimal(gridColumnDef, column);
        break;
      case Date:
        break;
      case DateTime:
        break;
      case Decimal:
        columnConfig = createColumnConfigBigDecimal(gridColumnDef, column);
        break;
      case Int:
        columnConfig = createColumnConfigInteger(gridColumnDef, column);
        break;
      case Money:
        break;
      case String:
        columnConfig = createColumnConfigString(gridColumnDef, column);
        break;
    }
    if (columnConfig != null) {
      gridColumnDef.setColumnConfig(columnConfig, columnConfigList.size());
      columnConfigList.add(columnConfig);
    }
  }
  result = new ColumnModel<GLRecord>(columnConfigList);
  result.addColumnWidthChangeHandler(new ColumnWidthChangeHandler() {
    @Override
    public void onColumnWidthChange(final ColumnWidthChangeEvent event) {
      final ColumnConfig<GLRecord, ?> columnConfig = columnConfigList.get(event.getIndex());
      if (columnConfig.getCell() instanceof CheckBoxCell) {
        centerCheckBox(columnConfig);
        _grid.getView().refresh(true);
      }
    }
  });
  for (final ColumnConfig<GLRecord, ?> columnConfig : _checkBoxList) {
    centerCheckBox(columnConfig);
  }
  return result;
}
//--------------------------------------------------------------------------------------------------
private void createContentPanel(final String headingText) {
  _contentPanel = new ContentPanel();
  if (GLUtil.isBlank(headingText)) {
    _contentPanel.setHeaderVisible(false);
  }
  else {
    _contentPanel.setHeaderVisible(true);
    _contentPanel.setHeadingText(headingText);
  }
  _contentPanel.setButtonAlign(BoxLayoutPack.START);
  _contentPanel.addButton(new TextButton("Reset", new SelectHandler() {
    @Override
    public void onSelect(final SelectEvent event) {
      _listStore.rejectChanges();
    }
  }));
  _contentPanel.addButton(new TextButton("Save", new SelectHandler() {
    @Override
    public void onSelect(final SelectEvent event) {
      _listStore.commitChanges();
    }
  }));
}
//--------------------------------------------------------------------------------------------------
@SuppressWarnings("unchecked")
private void createEditors() {
  final GridEditing<GLRecord> gridEditing = new GridInlineEditing<GLRecord>(_grid);
  for (final GLGridColumnDef gridColumnDef : _gridColumnDefList) {
    final ColumnConfig<GLRecord, ?> columnConfig = gridColumnDef.getColumnConfig();
    switch (gridColumnDef.getColumn().getDataType()) {
      case Boolean:
        break;
      case Currency:
        gridEditing.addEditor((ColumnConfig<GLRecord, BigDecimal>)columnConfig,
                              new BigDecimalField());
        break;
      case Date:
        break;
      case DateTime:
        break;
      case Decimal:
        gridEditing.addEditor((ColumnConfig<GLRecord, BigDecimal>)columnConfig,
                              new BigDecimalField());
        break;
      case Int:
        gridEditing.addEditor((ColumnConfig<GLRecord, Integer>)columnConfig, new IntegerField());
        break;
      case Money:
        break;
      case String:
        gridEditing.addEditor((ColumnConfig<GLRecord, String>)columnConfig, new TextField());
        break;
    }
  }
}
//--------------------------------------------------------------------------------------------------
private void createGrid() {
  createCheckBoxSelectionModel();
  final ColumnModel<GLRecord> columnModel = createColumnModel();
  _grid = new Grid<GLRecord>(_listStore, columnModel);
  _grid.addRowClickHandler(new RowClickEvent.RowClickHandler() {
    @Override
    public void onRowClick(final RowClickEvent event) {
      final Collection<Store<GLRecord>.Record> records = _listStore.getModifiedRecords();
      if (records.size() > 0) {
        _grid.setBorders(false);
      }
    }
  });
  _grid.setBorders(true);
  _grid.setColumnReordering(true);
  _grid.setLoadMask(true);
  _grid.setSelectionModel(_selectionModel);
  _grid.setView(createGridView());
  addHeaderContextMenuHandler();
  createEditors();
}
//--------------------------------------------------------------------------------------------------
private void createGridColumnDefMap() {
  _gridColumnDefMap = new TreeMap<String, GLGridColumnDef>();
  for (final GLGridColumnDef gridColumnDef : _gridColumnDefList) {
    _gridColumnDefMap.put(gridColumnDef.getColumn().toString(), gridColumnDef);
  }
}
//--------------------------------------------------------------------------------------------------
private GridView<GLRecord> createGridView() {
  final GridView<GLRecord> result = new GridView<GLRecord>();
  result.setColumnLines(true);
  result.setEmptyText(_noRowsMessage);
  result.setForceFit(false);
  result.setStripeRows(true);
  return result;
}
//--------------------------------------------------------------------------------------------------
public GLListStore getListStore() {
  return _listStore;
}
//--------------------------------------------------------------------------------------------------
protected abstract void loadGridColumnDefList();
//--------------------------------------------------------------------------------------------------
private void resizeColumnToFit(final int columnIndex) {
  final ColumnConfig<GLRecord, ?> columnConfig = _grid.getColumnModel().getColumn(columnIndex);
  final GLGridColumnDef gridColumnDef = _gridColumnDefMap.get(columnConfig.getPath());
  final TextMetrics textMetrics = TextMetrics.get();
  textMetrics.bind(_grid.getView().getHeader().getAppearance().styles().head());
  int maxWidth = textMetrics.getWidth(columnConfig.getHeader().asString()) + 15; // extra is for the dropdown arrow
  textMetrics.bind(_grid.getView().getCell(0, 1));
  final IGLColumn column = gridColumnDef.getColumn();
  try {
    for (final GLRecord record : _listStore.getAll()) {
      int width;
      width = textMetrics.getWidth(record.asString(column));
      maxWidth = width > maxWidth ? width : maxWidth;
    }
  }
  catch (final GLInvalidFieldOrColumnException ifoce) {
    //
  }
  for (final Store<GLRecord>.Record record : _listStore.getModifiedRecords()) {
    final int width = textMetrics.getWidth(record.getValue(columnConfig.getValueProvider()) //
                                                 .toString());
    maxWidth = width > maxWidth ? width : maxWidth;
  }
  columnConfig.setWidth(maxWidth);
  if (columnConfig.getCell() instanceof CheckBoxCell) {
    centerCheckBox(columnConfig);
  }
}
//--------------------------------------------------------------------------------------------------
}