package org.greatlogic.gxtexamples.client.widget;

import org.greatlogic.gxtexamples.client.glgwt.GLGridColumnDef;
import org.greatlogic.gxtexamples.client.glgwt.GLValueMapGridWidget;
import org.greatlogic.gxtexamples.shared.IDBEnums.Pet;

public class PersonGridWidget extends GLValueMapGridWidget {
//--------------------------------------------------------------------------------------------------
public PersonGridWidget() {
  super(null, Pet.PetID);
}
//--------------------------------------------------------------------------------------------------
@Override
protected void loadGridColumnDefList() {
  _gridColumnDefList.add(new GLGridColumnDef(Pet.DateOfBirth));
  _gridColumnDefList.add(new GLGridColumnDef(Pet.LoginName));
  _gridColumnDefList.add(new GLGridColumnDef(Pet.PetName));
  _gridColumnDefList.add(new GLGridColumnDef(Pet.LastName));
  _gridColumnDefList.add(new GLGridColumnDef(Pet.PetID));
}
//--------------------------------------------------------------------------------------------------
}