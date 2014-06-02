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
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.event.ColumnWidthChangeEvent;
import com.sencha.gxt.widget.core.client.event.ColumnWidthChangeEvent.ColumnWidthChangeHandler;
import com.sencha.gxt.widget.core.client.event.HeaderContextMenuEvent;
import com.sencha.gxt.widget.core.client.event.HeaderContextMenuEvent.HeaderContextMenuHandler;
import com.sencha.gxt.widget.core.client.event.HeaderDoubleClickEvent;
import com.sencha.gxt.widget.core.client.event.HeaderDoubleClickEvent.HeaderDoubleClickHandler;
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

public abstract class GLValueMapGridWidget implements IsWidget {
//--------------------------------------------------------------------------------------------------
private final ArrayList<ColumnConfig<GLValueMap, ?>> _checkBoxList;
private ContentPanel                                 _contentPanel;
private Grid<GLValueMap>                             _grid;
protected ArrayList<GLGridColumnDef>                 _gridColumnDefList;
private TreeMap<String, GLGridColumnDef>             _gridColumnDefMap;
protected ListStore<GLValueMap>                      _listStore;
private final String                                 _noRowsMessage;
private GridSelectionModel<GLValueMap>               _selectionModel;
//--------------------------------------------------------------------------------------------------
protected GLValueMapGridWidget(final String headingText, final String noRowsMessage,
                               final IGLColumn keyColumn) {
  super();
  _noRowsMessage = noRowsMessage == null ? "There are no results to display" : noRowsMessage;
  _listStore = GLUtil.createListStore(keyColumn);
  _checkBoxList = new ArrayList<ColumnConfig<GLValueMap, ?>>();
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
private void addHeaderDoubleClickHandler() {
  final HeaderDoubleClickHandler headerDoubleClickHandler = new HeaderDoubleClickHandler() {
    @Override
    public void onHeaderDoubleClick(final HeaderDoubleClickEvent event) {
      GLUtil.info(10, "Double click");
      resizeColumnToFit(event.getColumnIndex());
      _grid.getView().refresh(true);
    }
  };
  _grid.addHeaderDoubleClickHandler(headerDoubleClickHandler);
}
//--------------------------------------------------------------------------------------------------
@Override
public Widget asWidget() {
  return _contentPanel;
}
//--------------------------------------------------------------------------------------------------
private void centerCheckBox(final ColumnConfig<GLValueMap, ?> columnConfig) {
  final int leftPadding = (columnConfig.getWidth() - 12) / 2;
  final String styles = "padding: 3px 0px 0px " + leftPadding + "px;";
  final SafeStyles textStyles = SafeStylesUtils.fromTrustedString(styles);
  columnConfig.setColumnTextStyle(textStyles);
}
//--------------------------------------------------------------------------------------------------
private void createCheckBoxSelectionModel() {
  final IdentityValueProvider<GLValueMap> identityValueProvider;
  identityValueProvider = new IdentityValueProvider<GLValueMap>();
  _selectionModel = new CheckBoxSelectionModel<GLValueMap>(identityValueProvider) {
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
private ColumnConfig<GLValueMap, Boolean> createColumnConfigBoolean(final GLGridColumnDef gridColumnDef,
                                                                    final IGLColumn column) {
  final ColumnConfig<GLValueMap, Boolean> result;
  final ValueProvider<GLValueMap, Boolean> valueProvider;
  valueProvider = new GLBooleanValueProvider(column);
  result = new ColumnConfig<GLValueMap, Boolean>(valueProvider, gridColumnDef.getWidth(),
                                                 column.getTitle());
  result.setHorizontalAlignment(gridColumnDef.getHorizontalAlignment());
  result.setCell(new CheckBoxCell());
  result.setSortable(false);
  _checkBoxList.add(result);
  return result;
}
//--------------------------------------------------------------------------------------------------
private ColumnConfig<GLValueMap, BigDecimal> createColumnConfigBigDecimal(final GLGridColumnDef gridColumnDef,
                                                                          final IGLColumn column) {
  final ColumnConfig<GLValueMap, BigDecimal> result;
  final ValueProvider<GLValueMap, BigDecimal> valueProvider;
  valueProvider = new GLBigDecimalValueProvider(column, column.getNumberOfDecimalPlaces());
  result = new ColumnConfig<GLValueMap, BigDecimal>(valueProvider, gridColumnDef.getWidth(), //
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
private ColumnConfig<GLValueMap, Integer> createColumnConfigInteger(final GLGridColumnDef gridColumnDef,
                                                                    final IGLColumn column) {
  final ColumnConfig<GLValueMap, Integer> result;
  final ValueProvider<GLValueMap, Integer> valueProvider;
  valueProvider = new GLIntegerValueProvider(column);
  result = new ColumnConfig<GLValueMap, Integer>(valueProvider, gridColumnDef.getWidth(), //
                                                 column.getTitle());
  result.setHorizontalAlignment(gridColumnDef.getHorizontalAlignment());
  result.setCell(new NumberCell<Integer>());
  return result;
}
//--------------------------------------------------------------------------------------------------
private ColumnConfig<GLValueMap, String> createColumnConfigString(final GLGridColumnDef gridColumnDef,
                                                                  final IGLColumn column) {
  final ColumnConfig<GLValueMap, String> result;
  final ValueProvider<GLValueMap, String> valueProvider;
  valueProvider = new GLStringValueProvider(column);
  result = new ColumnConfig<GLValueMap, String>(valueProvider, gridColumnDef.getWidth(), //
                                                column.getTitle());
  result.setHorizontalAlignment(gridColumnDef.getHorizontalAlignment());
  result.setCell(new TextCell());
  return result;
}
//--------------------------------------------------------------------------------------------------
private ColumnModel<GLValueMap> createColumnModel() {
  ColumnModel<GLValueMap> result;
  final ArrayList<ColumnConfig<GLValueMap, ?>> columnConfigList;
  columnConfigList = new ArrayList<ColumnConfig<GLValueMap, ?>>();
  if (_selectionModel instanceof CheckBoxSelectionModel) {
    columnConfigList.add(((CheckBoxSelectionModel<GLValueMap>)_selectionModel).getColumn());
  }
  for (final GLGridColumnDef gridColumnDef : _gridColumnDefList) {
    ColumnConfig<GLValueMap, ?> columnConfig = null;
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
  result = new ColumnModel<GLValueMap>(columnConfigList);
  result.addColumnWidthChangeHandler(new ColumnWidthChangeHandler() {
    @Override
    public void onColumnWidthChange(final ColumnWidthChangeEvent event) {
      final ColumnConfig<GLValueMap, ?> columnConfig = columnConfigList.get(event.getIndex());
      if (columnConfig.getCell() instanceof CheckBoxCell) {
        centerCheckBox(columnConfig);
        _grid.getView().refresh(true);
      }
    }
  });
  for (final ColumnConfig<GLValueMap, ?> columnConfig : _checkBoxList) {
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
  final GridEditing<GLValueMap> gridEditing = new GridInlineEditing<GLValueMap>(_grid);
  for (final GLGridColumnDef gridColumnDef : _gridColumnDefList) {
    final ColumnConfig<GLValueMap, ?> columnConfig = gridColumnDef.getColumnConfig();
    switch (gridColumnDef.getColumn().getDataType()) {
      case Boolean:
        break;
      case Currency:
        gridEditing.addEditor((ColumnConfig<GLValueMap, BigDecimal>)columnConfig,
                              new BigDecimalField());
        break;
      case Date:
        break;
      case DateTime:
        break;
      case Decimal:
        gridEditing.addEditor((ColumnConfig<GLValueMap, BigDecimal>)columnConfig,
                              new BigDecimalField());
        break;
      case Int:
        gridEditing.addEditor((ColumnConfig<GLValueMap, Integer>)columnConfig, new IntegerField());
        break;
      case Money:
        break;
      case String:
        gridEditing.addEditor((ColumnConfig<GLValueMap, String>)columnConfig, new TextField());
        break;
    }
  }
}
//--------------------------------------------------------------------------------------------------
private void createGrid() {
  createCheckBoxSelectionModel();
  final ColumnModel<GLValueMap> columnModel = createColumnModel();
  _grid = new Grid<GLValueMap>(_listStore, columnModel);
  _grid.addRowClickHandler(new RowClickEvent.RowClickHandler() {
    @Override
    public void onRowClick(final RowClickEvent event) {
      final Collection<Store<GLValueMap>.Record> records = _listStore.getModifiedRecords();
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
  addHeaderDoubleClickHandler();
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
private GridView<GLValueMap> createGridView() {
  final GridView<GLValueMap> result = new GridView<GLValueMap>();
  result.setColumnLines(true);
  result.setEmptyText(_noRowsMessage);
  result.setForceFit(false);
  result.setStripeRows(true);
  return result;
}
//--------------------------------------------------------------------------------------------------
public ListStore<GLValueMap> getListStore() {
  return _listStore;
}
//--------------------------------------------------------------------------------------------------
protected abstract void loadGridColumnDefList();
//--------------------------------------------------------------------------------------------------
private void resizeColumnToFit(final int columnIndex) {
  final ColumnConfig<GLValueMap, ?> columnConfig = _grid.getColumnModel().getColumn(columnIndex);
  final GLGridColumnDef gridColumnDef = _gridColumnDefMap.get(columnConfig.getPath());
  final TextMetrics textMetrics = TextMetrics.get();
  textMetrics.bind(_grid.getView().getHeader().getAppearance().styles().head());
  int maxWidth = textMetrics.getWidth(gridColumnDef.getHeader()) + 15; // extra is for the dropdown arrow
  final IGLColumn column = gridColumnDef.getColumn();
  for (final GLValueMap valueMap : _listStore.getAll()) {
    final int width = textMetrics.getWidth(valueMap.asString(column)) - 20; // adjust for overage
    maxWidth = width > maxWidth ? width : maxWidth;
  }
  columnConfig.setWidth(maxWidth);
  if (columnConfig.getCell() instanceof CheckBoxCell) {
    centerCheckBox(columnConfig);
  }
}
//--------------------------------------------------------------------------------------------------
}