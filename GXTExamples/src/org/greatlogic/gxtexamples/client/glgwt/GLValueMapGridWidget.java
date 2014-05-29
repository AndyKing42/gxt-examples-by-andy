package org.greatlogic.gxtexamples.client.glgwt;

import java.util.ArrayList;
import java.util.Collection;
import org.greatlogic.gxtexamples.client.glgwt.GLValueProviderClasses.GLIntegerValueProvider;
import org.greatlogic.gxtexamples.client.glgwt.GLValueProviderClasses.GLStringValueProvider;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.NumberCell;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.widget.core.client.event.RefreshEvent;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.grid.editing.GridEditing;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;

public abstract class GLValueMapGridWidget implements IsWidget {
//--------------------------------------------------------------------------------------------------
private Grid<GLValueMap>             _grid;
protected ArrayList<GLGridColumnDef> _gridColumnDefList;
protected ListStore<GLValueMap>      _listStore;
private final String                 _noRowsMessage;
//--------------------------------------------------------------------------------------------------
protected GLValueMapGridWidget(final String noRowsMessage, final IGLColumn keyColumn) {
  super();
  _noRowsMessage = noRowsMessage == null ? "There are no results to display" : noRowsMessage;
  createStore(keyColumn);
  _gridColumnDefList = new ArrayList<GLGridColumnDef>();
  loadGridColumnDefList();
  createGrid();
}
//--------------------------------------------------------------------------------------------------
@Override
public Widget asWidget() {
  return _grid;
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
private ColumnConfig<GLValueMap, Integer> createColumnConfigInteger(final GLGridColumnDef gridColumnDef) {
  final ColumnConfig<GLValueMap, Integer> result;
  final ValueProvider<GLValueMap, Integer> valueProvider;
  valueProvider = new GLIntegerValueProvider(gridColumnDef.getColumn());
  result = new ColumnConfig<GLValueMap, Integer>(valueProvider, gridColumnDef.getWidth(), //
                                                 gridColumnDef.getColumn().getTitle());
  result.setHorizontalAlignment(gridColumnDef.getHorizontalAlignment());
  result.setCell(new NumberCell<Integer>());
  return result;
}
//--------------------------------------------------------------------------------------------------
private ColumnConfig<GLValueMap, String> createColumnConfigString(final GLGridColumnDef gridColumnDef) {
  final ColumnConfig<GLValueMap, String> result;
  final ValueProvider<GLValueMap, String> valueProvider;
  valueProvider = new GLStringValueProvider(gridColumnDef.getColumn());
  result = new ColumnConfig<GLValueMap, String>(valueProvider, gridColumnDef.getWidth(), //
                                                gridColumnDef.getColumn().getTitle());
  result.setHorizontalAlignment(gridColumnDef.getHorizontalAlignment());
  result.setCell(new TextCell());
  return result;
}
//--------------------------------------------------------------------------------------------------
private ColumnModel<GLValueMap> createColumnModel(final CheckBoxSelectionModel<GLValueMap> selectionModel) {
  final ArrayList<ColumnConfig<GLValueMap, ?>> columnConfigList;
  columnConfigList = new ArrayList<ColumnConfig<GLValueMap, ?>>();
  columnConfigList.add(selectionModel.getColumn());
  for (final GLGridColumnDef gridColumnDef : _gridColumnDefList) {
    ColumnConfig<GLValueMap, ?> columnConfig = null;
    switch (gridColumnDef.getColumn().getDataType()) {
      case DateTime:
        break;
      case Decimal:
        break;
      case Int:
        columnConfig = createColumnConfigInteger(gridColumnDef);
        break;
      case Money:
        break;
      case String:
        columnConfig = createColumnConfigString(gridColumnDef);
        break;
    }
    if (columnConfig != null) {
      gridColumnDef.setColumnConfig(columnConfig);
      columnConfigList.add(columnConfig);
    }
  }
  return new ColumnModel<GLValueMap>(columnConfigList);
}
//--------------------------------------------------------------------------------------------------
@SuppressWarnings("unchecked")
private void createEditors() {
  final GridEditing<GLValueMap> gridEditing = new GridInlineEditing<GLValueMap>(_grid);
  for (final GLGridColumnDef gridColumnDef : _gridColumnDefList) {
    final ColumnConfig<GLValueMap, ?> columnConfig = gridColumnDef.getColumnConfig();
    switch (gridColumnDef.getColumn().getDataType()) {
      case DateTime:
        break;
      case Decimal:
        break;
      case Int:
        gridEditing.addEditor((ColumnConfig<GLValueMap, Integer>)columnConfig, new IntegerField());
        break;
      case Money:
        break;
      case String:
        final TextField field = new TextField();
        field.setName(gridColumnDef.getColumn().toString());
        gridEditing.addEditor((ColumnConfig<GLValueMap, String>)columnConfig, field);
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
private void createStore(final IGLColumn keyColumn) {
  final ModelKeyProvider<GLValueMap> modelKeyProvider = new ModelKeyProvider<GLValueMap>() {
    @Override
    public String getKey(final GLValueMap valueMap) {
      return valueMap.asString(keyColumn);
    }
  };
  _listStore = new ListStore<GLValueMap>(modelKeyProvider);
}
//--------------------------------------------------------------------------------------------------
public ListStore<GLValueMap> getListStore() {
  return _listStore;
}
//--------------------------------------------------------------------------------------------------
protected abstract void loadGridColumnDefList();
//--------------------------------------------------------------------------------------------------
}