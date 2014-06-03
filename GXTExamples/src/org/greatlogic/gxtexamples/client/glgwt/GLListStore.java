package org.greatlogic.gxtexamples.client.glgwt;

import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
/**
 * A ListStore that contains GLRecord entries.
 */
public class GLListStore extends ListStore<GLRecord> {
//--------------------------------------------------------------------------------------------------
/**
 * Creates a new GLListStore.
 */
public GLListStore() {
  super(new ModelKeyProvider<GLRecord>() {
    @Override
    public String getKey(final GLRecord record) {
      String result;
      try {
        result = record.getKeyValueAsString();
      }
      catch (final GLInvalidFieldOrColumnException ifoce) {
        result = "***error***";
      }
      return result;
    }
  });
}
//--------------------------------------------------------------------------------------------------
}