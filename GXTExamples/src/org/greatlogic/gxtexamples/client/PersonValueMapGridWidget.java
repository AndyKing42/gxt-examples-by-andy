package org.greatlogic.gxtexamples.client;

import org.greatlogic.gxtexamples.client.glgwt.IGLColumn;
import org.greatlogic.gxtexamples.shared.IDBEnums.Person;

public class PersonValueMapGridWidget extends GLValueMapGridWidget {
//--------------------------------------------------------------------------------------------------
private static PersonValueMapGridWidget _personValueMapGridWidget;
//--------------------------------------------------------------------------------------------------
static PersonValueMapGridWidget getInstance(final boolean loadTestData) {
  if (_personValueMapGridWidget == null) {
    _personValueMapGridWidget = new PersonValueMapGridWidget();
    if (loadTestData) {
      TestData.loadPersonTestData(_personValueMapGridWidget._listStore, 100);
    }
  }
  return _personValueMapGridWidget;
}
//--------------------------------------------------------------------------------------------------
public PersonValueMapGridWidget() {
  super(Person.PersonID);
}
//--------------------------------------------------------------------------------------------------
@Override
protected IGLColumn[] getColumns() {
  return new IGLColumn[] {Person.DisplayName, Person.LoginName, Person.FirstName, Person.LastName};
}
//--------------------------------------------------------------------------------------------------
}