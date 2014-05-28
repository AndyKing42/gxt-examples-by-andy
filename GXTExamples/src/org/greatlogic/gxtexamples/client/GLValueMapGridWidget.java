package org.greatlogic.gxtexamples.client;

import java.util.ArrayList;
import org.greatlogic.gxtexamples.client.glgwt.GLValueMap;
import org.greatlogic.gxtexamples.client.glgwt.IGLColumn;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.event.RefreshEvent;
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
private Grid<GLValueMap>        _grid;
protected ListStore<GLValueMap> _listStore;
//--------------------------------------------------------------------------------------------------
public GLValueMapGridWidget(final IGLColumn keyColumn) {
  super();
  createStore(keyColumn);
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
private void createColumnConfigString(final ArrayList<ColumnConfig<GLValueMap, ?>> columnConfigList,
                                      final IGLColumn column, final int width) {
  final ValueProvider<GLValueMap, String> valueProvider = new GLStringValueProvider(column);
  final ColumnConfig<GLValueMap, String> columnConfig;
  columnConfig = new ColumnConfig<GLValueMap, String>(valueProvider, width, column.getTitle());
  columnConfig.setCell(new TextCell());
  columnConfigList.add(columnConfig);
}
//--------------------------------------------------------------------------------------------------
private ColumnModel<GLValueMap> createColumnModel(final CheckBoxSelectionModel<GLValueMap> selectionModel) {
  final ArrayList<ColumnConfig<GLValueMap, ?>> columnConfigList;
  columnConfigList = new ArrayList<ColumnConfig<GLValueMap, ?>>();
  columnConfigList.add(selectionModel.getColumn());
  for (final IGLColumn column : getColumns()) {
    createColumnConfigString(columnConfigList, column, 150);
  }
  return new ColumnModel<GLValueMap>(columnConfigList);
}
//--------------------------------------------------------------------------------------------------
@SuppressWarnings("unchecked")
private void createGrid() {
  final CheckBoxSelectionModel<GLValueMap> selectionModel = createCheckBoxSelectionModel();
  final ColumnModel<GLValueMap> columnModel = createColumnModel(selectionModel);
  _grid = new Grid<GLValueMap>(_listStore, columnModel);
  _grid.setBorders(true);
  _grid.setColumnReordering(true);
  _grid.setLoadMask(true);
  _grid.setSelectionModel(selectionModel);
  _grid.setView(createGridView());
  final GridEditing<GLValueMap> gridEditing = new GridInlineEditing<GLValueMap>(_grid);
  for (final ColumnConfig<GLValueMap, ?> columnConfig : columnModel.getColumns()) {
    if (columnConfig.getCell() instanceof TextCell) {
      gridEditing.addEditor((ColumnConfig<GLValueMap, String>)columnConfig, new TextField());
    }
  }
}
//--------------------------------------------------------------------------------------------------
private GridView<GLValueMap> createGridView() {
  final GridView<GLValueMap> result = new GridView<GLValueMap>();
  result.setColumnLines(true);
  result.setEmptyText("There are no people for the requested criteria");
  result.setForceFit(true);
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
protected abstract IGLColumn[] getColumns();
//--------------------------------------------------------------------------------------------------
}