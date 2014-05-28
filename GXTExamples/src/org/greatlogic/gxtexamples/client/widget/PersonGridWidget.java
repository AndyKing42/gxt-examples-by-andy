package org.greatlogic.gxtexamples.client.widget;

import org.greatlogic.gxtexamples.client.glgwt.GLGridColumnDef;
import org.greatlogic.gxtexamples.client.glgwt.GLValueMapGridWidget;
import org.greatlogic.gxtexamples.shared.IDBEnums.Person;

public class PersonGridWidget extends GLValueMapGridWidget {
//--------------------------------------------------------------------------------------------------
public PersonGridWidget() {
  super(null, Person.PersonID);
}
//--------------------------------------------------------------------------------------------------
@Override
protected void loadGridColumnDefList() {
  _gridColumnDefList.add(new GLGridColumnDef(Person.DisplayName));
  _gridColumnDefList.add(new GLGridColumnDef(Person.LoginName));
  _gridColumnDefList.add(new GLGridColumnDef(Person.FirstName));
  _gridColumnDefList.add(new GLGridColumnDef(Person.LastName));
  _gridColumnDefList.add(new GLGridColumnDef(Person.PersonID));
}
//--------------------------------------------------------------------------------------------------
}