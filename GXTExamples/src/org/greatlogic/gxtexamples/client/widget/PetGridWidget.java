package org.greatlogic.gxtexamples.client.widget;
/*
 * Copyright 2006-2014 Andy King (GreatLogic.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
import org.greatlogic.gxtexamples.client.GXTExamplesCache;
import org.greatlogic.gxtexamples.client.IGXTExamplesEnums.ELookupListKey;
import org.greatlogic.gxtexamples.client.glgwt.GLGridColumnDef;
import org.greatlogic.gxtexamples.client.glgwt.GLGridWidget;
import org.greatlogic.gxtexamples.client.glgwt.GLListStore;
import org.greatlogic.gxtexamples.client.glgwt.GLRecord;
import org.greatlogic.gxtexamples.client.glgwt.IGLLookupListStoreKey;
import org.greatlogic.gxtexamples.shared.IDBEnums.Pet;

public class PetGridWidget extends GLGridWidget {
//--------------------------------------------------------------------------------------------------
public PetGridWidget() {
  super(null, null);
}
//--------------------------------------------------------------------------------------------------
@Override
public GLListStore getLookupListStore(final IGLLookupListStoreKey lookupListStoreKey) {
  if (lookupListStoreKey == ELookupListKey.PetTypes) {
    return GXTExamplesCache.getPetTypeListStore();
  }
  return null;
}
//--------------------------------------------------------------------------------------------------
@Override
public GLRecord getRecordForLookupValue(final IGLLookupListStoreKey lookupListStoreKey,
                                        final String value) {
  return GXTExamplesCache.getPetTypeRecordUsingPetTypeShortDesc(value);
}
//--------------------------------------------------------------------------------------------------
@Override
protected void loadGridColumnDefList() {
  _gridColumnDefList.add(new GLGridColumnDef(Pet.PetName));
  _gridColumnDefList.add(new GLGridColumnDef(Pet.PetTypeId, ELookupListKey.PetTypes));
  _gridColumnDefList.add(new GLGridColumnDef(Pet.Sex));
  _gridColumnDefList.add(new GLGridColumnDef(Pet.IntakeDate));
  _gridColumnDefList.add(new GLGridColumnDef(Pet.TrainedFlag));
  _gridColumnDefList.add(new GLGridColumnDef(Pet.AdoptionFee));
  _gridColumnDefList.add(new GLGridColumnDef(Pet.FosterDate));
}
//--------------------------------------------------------------------------------------------------
}