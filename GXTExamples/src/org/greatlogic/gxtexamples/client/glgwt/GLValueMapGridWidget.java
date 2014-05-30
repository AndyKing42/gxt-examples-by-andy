package org.greatlogic.gxtexamples.client.glgwt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import org.greatlogic.gxtexamples.client.glgwt.GLValueProviderClasses.GLBigDecimalValueProvider;
import org.greatlogic.gxtexamples.client.glgwt.GLValueProviderClasses.GLBooleanValueProvider;
import org.greatlogic.gxtexamples.client.glgwt.GLValueProviderClasses.GLIntegerValueProvider;
import org.greatlogic.gxtexamples.client.glgwt.GLValueProviderClasses.GLStringValueProvider;
import org.greatlogic.gxtexamples.client.glgwt.IGLEnums.EGLColumnDataType;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.NumberCell;
import com.sencha.gxt.cell.core.client.form.CheckBoxCell;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.event.ColumnWidthChangeEvent;
import com.sencha.gxt.widget.core.client.event.ColumnWidthChangeEvent.ColumnWidthChangeHandler;
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

public abstract class GLValueMapGridWidget implements IsWidget {
//--------------------------------------------------------------------------------------------------
private ContentPanel                 _contentPanel;
private Grid<GLValueMap>             _grid;
protected ArrayList<GLGridColumnDef> _gridColumnDefList;
protected ListStore<GLValueMap>      _listStore;
private final String                 _noRowsMessage;
//--------------------------------------------------------------------------------------------------
protected GLValueMapGridWidget(final String headingText, final String noRowsMessage,
                               final IGLColumn keyColumn) {
  super();
  _noRowsMessage = noRowsMessage == null ? "There are no results to display" : noRowsMessage;
  _listStore = GLUtil.createListStore(keyColumn);
  _gridColumnDefList = new ArrayList<GLGridColumnDef>();
  loadGridColumnDefList();
  createContentPanel(headingText);
  createGrid();
  _contentPanel.add(_grid);
}
//--------------------------------------------------------------------------------------------------
@Override
public Widget asWidget() {
  return _contentPanel;
}
//--------------------------------------------------------------------------------------------------
private CheckBoxSelectionModel<GLValueMap> createCheckBoxSelectionModel() {
  CheckBoxSelectionModel<GLValueMap> result;
  final IdentityValueProvider<GLValueMap> identityValueProvider;
  identityValueProvider = new IdentityValueProvider<GLValueMap>();
  result = new CheckBoxSelectionModel<GLValueMap>(identityValueProvider) {
    @Override
    protected void onRefresh(final RefreshEvent event) {
      if (isSelectAllChecked()) {
        selectAll();
      }
      super.onRefresh(event);
    }
  };
  return result;
}
//--------------------------------------------------------------------------------------------------
private ColumnConfig<GLValueMap, Boolean> createColumnConfigBoolean(final GLGridColumnDef gridColumnDef,
                                                                    final IGLColumn column) {
  final ColumnConfig<GLValueMap, Boolean> result;
  final ValueProvider<GLValueMap, Boolean> valueProvider;
  valueProvider = new GLBooleanValueProvider(column);
  result = new ColumnConfig<GLValueMap, Boolean>(valueProvider, gridColumnDef.getWidth(), //
                                                 column.getTitle());
  result.setHorizontalAlignment(gridColumnDef.getHorizontalAlignment());
  result.setCell(new CheckBoxCell());
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
private ColumnModel<GLValueMap> createColumnModel(final GridSelectionModel<GLValueMap> selectionModel) {
  ColumnModel<GLValueMap> result;
  final ArrayList<ColumnConfig<GLValueMap, ?>> columnConfigList;
  columnConfigList = new ArrayList<ColumnConfig<GLValueMap, ?>>();
  if (selectionModel instanceof CheckBoxSelectionModel) {
    columnConfigList.add(((CheckBoxSelectionModel<GLValueMap>)selectionModel).getColumn());
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
        final String styles = "padding: 20px 30px 0px 20px;";
        final SafeStyles textStyles = SafeStylesUtils.fromTrustedString(styles);
        columnConfig.setColumnTextStyle(textStyles);
        _grid.getView().refresh(true);
      }
    }
  });
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
  final CheckBoxSelectionModel<GLValueMap> selectionModel = createCheckBoxSelectionModel();
  final ColumnModel<GLValueMap> columnModel = createColumnModel(selectionModel);
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
  _grid.setSelectionModel(selectionModel);
  _grid.setView(createGridView());
  createEditors();
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
}