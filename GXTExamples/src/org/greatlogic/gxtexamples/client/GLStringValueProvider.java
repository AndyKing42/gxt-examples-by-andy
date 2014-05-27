package org.greatlogic.gxtexamples.client;

import org.greatlogic.gxtexamples.client.glgwt.GLValueMap;
import org.greatlogic.gxtexamples.client.glgwt.IGLColumn;
import com.sencha.gxt.core.client.ValueProvider;

public class GLStringValueProvider implements ValueProvider<GLValueMap, String> {
//--------------------------------------------------------------------------------------------------
private final IGLColumn _column;
//--------------------------------------------------------------------------------------------------
public GLStringValueProvider(final IGLColumn column) {
  _column = column;
}
//--------------------------------------------------------------------------------------------------
@Override
public String getValue(final GLValueMap valueMap) {
  return valueMap.asString(_column);
}
//--------------------------------------------------------------------------------------------------
@Override
public void setValue(final GLValueMap valueMap, final String value) {
  valueMap.put(_column, value);
}
//--------------------------------------------------------------------------------------------------
@Override
public String getPath() {
  return null;
}
//--------------------------------------------------------------------------------------------------
}