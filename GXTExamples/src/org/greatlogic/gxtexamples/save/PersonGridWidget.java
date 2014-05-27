package org.greatlogic.gxtexamples.save;

import java.util.ArrayList;
import org.greatlogic.gxtexamples.client.PersonPropertyAccess;
import org.greatlogic.gxtexamples.client.glgwt.GLUtil;
import org.greatlogic.gxtexamples.shared.dto.Person;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
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
import com.sencha.gxt.widget.core.client.grid.editing.GridRowEditing;

public class PersonGridWidget extends Composite {
//--------------------------------------------------------------------------------------------------
@UiField(provided = true)
CheckBoxSelectionModel<Person> checkBoxSelectionModel;
@UiField(provided = true)
ColumnModel<Person>            columnModel;
@UiField
Grid<Person>                   grid;
@UiField
GridView<Person>               gridView;
@UiField(provided = true)
ListStore<Person>              listStore;
//==================================================================================================
interface PersonGridWidgetUiBinder extends UiBinder<Widget, PersonGridWidget> { //
}
//==================================================================================================
public PersonGridWidget() {
  super();
  configureGrid();
  loadTestData(100);
  final PersonGridWidgetUiBinder uiBinder = GWT.create(PersonGridWidgetUiBinder.class);
  initWidget(uiBinder.createAndBindUi(this));
}
//--------------------------------------------------------------------------------------------------
private void configureGrid() {
  final ModelKeyProvider<Person> modelKeyProvider = new ModelKeyProvider<Person>() {
    @Override
    public String getKey(final Person person) {
      return "" + person.getPersonId();
    }
  };
  listStore = new ListStore<Person>(modelKeyProvider);
  final IdentityValueProvider<Person> identityValueProvider = new IdentityValueProvider<Person>();
  checkBoxSelectionModel = new CheckBoxSelectionModel<Person>(identityValueProvider) {
    @Override
    protected void onRefresh(final RefreshEvent event) {
      if (isSelectAllChecked()) {
        selectAll();
      }
      super.onRefresh(event);
    }
  };
  final ArrayList<ColumnConfig<Person, ?>> columnConfigList =
                                                              new ArrayList<ColumnConfig<Person, ?>>();
  columnConfigList.add(checkBoxSelectionModel.getColumn());
  final GridEditing<Person> gridEditing = new GridRowEditing<Person>(grid);
  final PersonPropertyAccess personPropertyAccess = GWT.create(PersonPropertyAccess.class);
  configureGridColumnString(columnConfigList, gridEditing, personPropertyAccess.displayName(), 150,
                            "Display Name");
  configureGridColumnString(columnConfigList, gridEditing, personPropertyAccess.loginName(), 150,
                            "Login Name");
  configureGridColumnString(columnConfigList, gridEditing, personPropertyAccess.firstName(), 150,
                            "First Name");
  configureGridColumnString(columnConfigList, gridEditing, personPropertyAccess.lastName(), 150,
                            "Last Name");
  columnModel = new ColumnModel<Person>(columnConfigList);
}
//--------------------------------------------------------------------------------------------------
private void configureGridColumnString(final ArrayList<ColumnConfig<Person, ?>> columnConfigList,
                                       final GridEditing<Person> gridEditing,
                                       final ValueProvider<Person, String> valueProvider,
                                       final int width, final String header) {
  final ColumnConfig<Person, String> columnConfig =
                                                    new ColumnConfig<Person, String>(valueProvider,
                                                                                     width, header);
  columnConfigList.add(columnConfig);
  gridEditing.addEditor(columnConfig, new TextField());
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
  listStore.clear();
  for (int recordCount = 0; recordCount < numberOfRecords; ++recordCount) {
    final String firstName = FirstNames[GLUtil.getRandomInt(FirstNames.length)];
    final String lastName = LastNames[GLUtil.getRandomInt(LastNames.length)];
    listStore.add(new Person("", 1, "",
                             firstName + " " + lastName, //
                             firstName + "-" + lastName + "@gmail.com", firstName, lastName,
                             firstName + "." + lastName, "", recordCount + 1, "", "", "", "1"));
  }
}
//--------------------------------------------------------------------------------------------------
}