package org.greatlogic.gxtexamples.client;

import java.util.TreeMap;
import org.greatlogic.gxtexamples.client.glgwt.GLDBException;
import org.greatlogic.gxtexamples.client.glgwt.GLInvalidFieldOrColumnException;
import org.greatlogic.gxtexamples.client.glgwt.GLListStore;
import org.greatlogic.gxtexamples.client.glgwt.GLRecord;
import org.greatlogic.gxtexamples.client.glgwt.GLSQL;
import org.greatlogic.gxtexamples.client.glgwt.GLUtil;
import org.greatlogic.gxtexamples.client.glgwt.IGLColumn;
import org.greatlogic.gxtexamples.client.glgwt.IGLSQLSelectCallback;
import org.greatlogic.gxtexamples.shared.IDBEnums.EGXTExamplesTable;
import org.greatlogic.gxtexamples.shared.IDBEnums.PetType;

public class GXTExamplesCache {
//--------------------------------------------------------------------------------------------------
private static GLListStore               _petTypeListStore;
private static TreeMap<String, GLRecord> _petTypeRecordByPetTypeShortDescMap;
//--------------------------------------------------------------------------------------------------
private static TreeMap<String, GLRecord> buildLookupMap(final GLListStore listStore,
                                                        final IGLColumn lookupColumn) {
  final TreeMap<String, GLRecord> result = new TreeMap<String, GLRecord>();
  for (int recordIndex = 0; recordIndex < listStore.size(); ++recordIndex) {
    final GLRecord record = listStore.get(recordIndex);
    try {
      result.put(record.asString(lookupColumn), record);
    }
    catch (final GLInvalidFieldOrColumnException e) {

    }
  }
  return result;
}
//--------------------------------------------------------------------------------------------------
public static GLListStore getPetTypeListStore() {
  return _petTypeListStore;
}
//--------------------------------------------------------------------------------------------------
public static GLRecord getPetTypeRecordUsingPetTypeShortDesc(final String value) {
  if (_petTypeRecordByPetTypeShortDescMap == null) {
    _petTypeRecordByPetTypeShortDescMap = buildLookupMap(_petTypeListStore, //
                                                         PetType.PetTypeShortDesc);
  }
  return _petTypeRecordByPetTypeShortDescMap.get(value);
}
//--------------------------------------------------------------------------------------------------
public static void load() {
  loadPetTypes();
}
//--------------------------------------------------------------------------------------------------
private static void loadPetTypes() {
  _petTypeListStore = new GLListStore();
  try {
    final GLSQL petTypeSQL = GLSQL.select();
    petTypeSQL.from(EGXTExamplesTable.PetType);
    petTypeSQL.orderBy(EGXTExamplesTable.PetType, PetType.PetTypeShortDesc, true);
    petTypeSQL.execute(_petTypeListStore, new IGLSQLSelectCallback() {
      @Override
      public void onFailure(final Throwable t) {
        GLUtil.info(30, "Pet type loading failed: " + t.getMessage());
      }
      @Override
      public void onSuccess(final GLListStore listStore) {
        GLUtil.info(5, "Pet types loaded successfully");
      }
    });
  }
  catch (final GLDBException dbe) {

  }
}
//--------------------------------------------------------------------------------------------------
}