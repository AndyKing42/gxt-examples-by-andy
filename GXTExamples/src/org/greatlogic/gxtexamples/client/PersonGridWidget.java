package org.greatlogic.gxtexamples.client;

import java.util.ArrayList;
import org.greatlogic.gxtexamples.client.glgwt.GLUtil;
import org.greatlogic.gxtexamples.shared.dto.Person;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
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

public class PersonGridWidget implements IsWidget {
//--------------------------------------------------------------------------------------------------
private static PersonGridWidget _personGridWidget;

private Grid<Person>            _grid;
private ListStore<Person>       _listStore;
//--------------------------------------------------------------------------------------------------
static PersonGridWidget getInstance(final boolean loadTestData) {
  if (_personGridWidget == null) {
    _personGridWidget = new PersonGridWidget(loadTestData);
  }
  return _personGridWidget;
}
//--------------------------------------------------------------------------------------------------
private PersonGridWidget(final boolean loadTestData) {
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
private CheckBoxSelectionModel<Person> createCheckBoxSelectionModel() {
  CheckBoxSelectionModel<Person> result;
  final IdentityValueProvider<Person> identityValueProvider = new IdentityValueProvider<Person>();
  result = new CheckBoxSelectionModel<Person>(identityValueProvider) {
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
private void createColumnConfigString(final ArrayList<ColumnConfig<Person, ?>> columnConfigList,
                                      final ValueProvider<Person, String> valueProvider,
                                      final int width, final String header) {
  final ColumnConfig<Person, String> columnConfig =
                                                    new ColumnConfig<Person, String>(valueProvider,
                                                                                     width, header);
  columnConfig.setCell(new TextCell());
  columnConfigList.add(columnConfig);
}
//--------------------------------------------------------------------------------------------------
private ColumnModel<Person> createColumnModel(final CheckBoxSelectionModel<Person> selectionModel) {
  final ArrayList<ColumnConfig<Person, ?>> columnConfigList =
                                                              new ArrayList<ColumnConfig<Person, ?>>();
  columnConfigList.add(selectionModel.getColumn());
  final PersonPropertyAccess personPropertyAccess = GWT.create(PersonPropertyAccess.class);
  createColumnConfigString(columnConfigList, personPropertyAccess.displayName(), 150,
                           "Display Name");
  createColumnConfigString(columnConfigList, personPropertyAccess.loginName(), 150, "Login Name");
  createColumnConfigString(columnConfigList, personPropertyAccess.firstName(), 150, "First Name");
  createColumnConfigString(columnConfigList, personPropertyAccess.lastName(), 150, "Last Name");
  return new ColumnModel<Person>(columnConfigList);
}
//--------------------------------------------------------------------------------------------------
@SuppressWarnings("unchecked")
private void createGrid() {
  final CheckBoxSelectionModel<Person> selectionModel = createCheckBoxSelectionModel();
  final ColumnModel<Person> columnModel = createColumnModel(selectionModel);
  _grid = new Grid<Person>(_listStore, columnModel);
  _grid.setBorders(true);
  _grid.setColumnReordering(true);
  _grid.setLoadMask(true);
  _grid.setSelectionModel(selectionModel);
  _grid.setView(createGridView());
  final GridEditing<Person> gridEditing = new GridInlineEditing<Person>(_grid);
  for (final ColumnConfig<Person, ?> columnConfig : columnModel.getColumns()) {
    if (columnConfig.getCell() instanceof TextCell) {
      gridEditing.addEditor((ColumnConfig<Person, String>)columnConfig, new TextField());
    }
  }
}
//--------------------------------------------------------------------------------------------------
private GridView<Person> createGridView() {
  final GridView<Person> result = new GridView<Person>();
  result.setColumnLines(true);
  result.setEmptyText("There are no people for the requested criteria");
  result.setForceFit(true);
  result.setStripeRows(true);
  return result;
}
//--------------------------------------------------------------------------------------------------
private void createStore() {
  final ModelKeyProvider<Person> modelKeyProvider = new ModelKeyProvider<Person>() {
    @Override
    public String getKey(final Person person) {
      return "" + person.getPersonId();
    }
  };
  _listStore = new ListStore<Person>(modelKeyProvider);
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
    _listStore.add(new Person("", 1, "",
                              firstName + " " + lastName, //
                              firstName + "-" + lastName + "@gmail.com", firstName, lastName,
                              firstName + "." + lastName, "", recordCount + 1, "", "", "", "1"));
  }
}
//--------------------------------------------------------------------------------------------------
}