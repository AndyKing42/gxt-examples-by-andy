package org.greatlogic.gxtexamples.client.widget;

import org.greatlogic.gxtexamples.client.glgwt.GLGridColumnDef;
import org.greatlogic.gxtexamples.client.glgwt.GLValueMapGridWidget;
import org.greatlogic.gxtexamples.shared.IDBEnums.Pet;

public class PetGridWidget extends GLValueMapGridWidget {
//--------------------------------------------------------------------------------------------------
public PetGridWidget() {
  super(null, Pet.PetId);
}
//--------------------------------------------------------------------------------------------------
@Override
protected void loadGridColumnDefList() {
  _gridColumnDefList.add(new GLGridColumnDef(Pet.PetName));
  _gridColumnDefList.add(new GLGridColumnDef(Pet.Sex));
  _gridColumnDefList.add(new GLGridColumnDef(Pet.IntakeDate));
  _gridColumnDefList.add(new GLGridColumnDef(Pet.TrainedFlag));
  _gridColumnDefList.add(new GLGridColumnDef(Pet.AdoptionFee));
  _gridColumnDefList.add(new GLGridColumnDef(Pet.FosterDate));
}
//--------------------------------------------------------------------------------------------------
}