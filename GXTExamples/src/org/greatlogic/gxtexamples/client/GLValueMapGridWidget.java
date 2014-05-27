package org.greatlogic.gxtexamples.client;

import java.util.ArrayList;
import org.greatlogic.gxtexamples.client.glgwt.GLUtil;
import org.greatlogic.gxtexamples.client.glgwt.GLValueMap;
import org.greatlogic.gxtexamples.shared.IDBEnums.Person;
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

public class GLValueMapGridWidget implements IsWidget {
//--------------------------------------------------------------------------------------------------
private static GLValueMapGridWidget _valueMapGridWidget;

private Grid<GLValueMap>            _grid;
private ListStore<GLValueMap>       _listStore;
//--------------------------------------------------------------------------------------------------
static GLValueMapGridWidget getInstance(final boolean loadTestData) {
  if (_valueMapGridWidget == null) {
    _valueMapGridWidget = new GLValueMapGridWidget(loadTestData);
  }
  return _valueMapGridWidget;
}
//--------------------------------------------------------------------------------------------------
private GLValueMapGridWidget(final boolean loadTestData) {
  super();
  createStore();
  createGrid();
  if (loadTestData) {
    loadTestData(100);
  }
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
                                      final ValueProvider<GLValueMap, String> valueProvider,
                                      final int width, final String header) {
  final ColumnConfig<GLValueMap, String> columnConfig;
  columnConfig = new ColumnConfig<GLValueMap, String>(valueProvider, width, header);
  columnConfig.setCell(new TextCell());
  columnConfigList.add(columnConfig);
}
//--------------------------------------------------------------------------------------------------
private ColumnModel<GLValueMap> createColumnModel(final CheckBoxSelectionModel<GLValueMap> selectionModel) {
  final ArrayList<ColumnConfig<GLValueMap, ?>> columnConfigList;
  columnConfigList = new ArrayList<ColumnConfig<GLValueMap, ?>>();
  columnConfigList.add(selectionModel.getColumn());
  createColumnConfigString(columnConfigList, new GLStringValueProvider(Person.DisplayName), 150,
                           "Display Name");
  createColumnConfigString(columnConfigList, new GLStringValueProvider(Person.LoginName), 150,
                           "Login Name");
  createColumnConfigString(columnConfigList, new GLStringValueProvider(Person.FirstName), 150,
                           "First Name");
  createColumnConfigString(columnConfigList, new GLStringValueProvider(Person.LastName), 150,
                           "Last Name");
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
private void createStore() {
  final ModelKeyProvider<GLValueMap> modelKeyProvider = new ModelKeyProvider<GLValueMap>() {
    @Override
    public String getKey(final GLValueMap valueMap) {
      return valueMap.asString(Person.PersonID);
    }
  };
  _listStore = new ListStore<GLValueMap>(modelKeyProvider);
}
//--------------------------------------------------------------------------------------------------
private static final String[] FirstNames = new String[] {"Aarav", "Abdiel", "Abrielle", "Addilyn",
    "Adrian", "Albert", "Alyvia", "America", "Amiya", "Andre", "Andy", "Angeline", "Arthur",
    "Augustus", "Aya", "Aylin", "Bailey", "Blake", "Braden", "Camdyn", "Cameron", "Camryn",
    "Celeste", "Christian", "Christine", "Clayton", "Dariel", "Darrell", "Dayton", "Devon",
    "Donovan", "Douglas", "Eden", "Edwin", "Eliza", "Emmy", "Enzo", "Erin", "Finn", "Foster",
    "Franklin", "Gabriela", "Gracelynn", "Hailee", "Hallie", "Hector", "Jaida", "Jaylah",
    "Johnathon", "Josephine", "Journey", "Junior", "Kade", "Kailey", "Kaitlin", "Kaylyn",
    "Kendall", "Kevin", "Khalil", "Kobe", "Kohen", "Konnor", "Kyree", "Kyrie", "Lila", "Lillie",
    "Lionel", "Litzy", "Lorenzo", "Lydia", "Macy", "Madyson", "Matilda", "Mayson", "Melanie",
    "Melody", "Mila", "Nevaeh", "Nicole", "Omar", "Paisley", "Paityn", "Presley", "Quentin",
    "Raven", "Roselyn", "Ryker", "Rylan", "Sadie", "Sidney", "Silas", "Stanley", "Terrell",
    "Tiffany", "Winston", "Yesenia"      };
private static final String[] LastNames  = new String[] {"Adamson", "Anderson", "Atkinson",
    "Ballantyne", "Barclay", "Barton", "Baxter", "Beattie", "Bennett", "Bernard", "Blakey",
    "Booth", "Bowman", "Braithwaite", "Bristow", "Browne", "Bullock", "Byrne", "Carroll", "Clark",
    "Collett", "Crane", "Cummings", "Denton", "Donald", "Dougal", "Fairhurst", "Fenton", "Fraser",
    "Gibbins", "Gillespie", "Gillett", "Grainger", "Greaves", "Hancock", "Harper", "Heath",
    "Hepburn", "Higgins", "Hindle", "Hine", "Hodgson", "Humphreys", "Hyde", "Jacob", "Judge",
    "King", "Kirkpatrick", "Knight", "Kumar", "Lawrence", "Leonard", "Logan", "Lord", "Maher",
    "Major", "Mann", "Marriott", "Martinez", "Moorhouse", "Murray", "Noon", "Norton", "Oconnor",
    "Ogden", "Paine", "Paton", "Peebles", "Pickard", "Rees", "Ricketts", "Rooney", "Rutherford",
    "Simon", "Simons", "Singleton", "Skinner", "Stannard", "Steere", "Stuart", "Sylvester",
    "Tierney", "Tucker", "Vincent", "Wale", "Waters", "Watson", "Watts", "Webb", "Webster",
    "White", "Wicks", "Wilkie", "Wilkins", "Wooldridge"};
private void loadTestData(final int numberOfRecords) {
  _listStore.clear();
  for (int recordCount = 0; recordCount < numberOfRecords; ++recordCount) {
    final String firstName = FirstNames[GLUtil.getRandomInt(FirstNames.length)];
    final String lastName = LastNames[GLUtil.getRandomInt(LastNames.length)];
    final GLValueMap valueMap = new GLValueMap(false);
    valueMap.put(Person.CurrentOrgId, 1);
    valueMap.put(Person.DisplayName, firstName + " " + lastName);
    valueMap.put(Person.EmailAddress, firstName + "-" + lastName + "@gmail.com");
    valueMap.put(Person.FirstName, firstName);
    valueMap.put(Person.LastName, lastName);
    valueMap.put(Person.LoginName, firstName + "." + lastName);
    valueMap.put(Person.PersonID, recordCount + 1);
    valueMap.put(Person.Version, "1");
    _listStore.add(valueMap);
  }
}
//--------------------------------------------------------------------------------------------------
}