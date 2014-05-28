package org.greatlogic.gxtexamples.client;

import org.greatlogic.gxtexamples.client.glgwt.GLUtil;
import org.greatlogic.gxtexamples.client.glgwt.GLValueMap;
import org.greatlogic.gxtexamples.shared.IDBEnums.Person;
import com.sencha.gxt.data.shared.ListStore;

public class TestData {
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
public static void loadPersonTestData(final ListStore<GLValueMap> listStore,
                                      final int numberOfRecords) {
  listStore.clear();
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
    listStore.add(valueMap);
  }
}
//--------------------------------------------------------------------------------------------------
}